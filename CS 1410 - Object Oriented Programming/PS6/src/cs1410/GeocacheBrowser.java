package cs1410;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.event.*;

/**
 * A GUI window that allows the user to browse through a list of geocaches.
 */
@SuppressWarnings("serial")
public class GeocacheBrowser extends JFrame
{
    /**
     * Starts up a Geocache Browser
     */
    public static void main (String[] args)
    {
        // Create a GeocacheBrowser and make it visible
        GeocacheBrowser browser = new GeocacheBrowser();
        browser.setVisible(true);
    }

    // Keeps track of all the geocaches and imposes search constraints
    private CacheList allCaches;

    /**
     * Create a GeocacheBrowser window.
     */
    public GeocacheBrowser()
    {
        // Configure the window
        try
        {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setTitle("Geocache Browser");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
        }

        // Get the cache file
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        chooser.setDialogTitle("Choose Cache Information File");
        int result = chooser.showOpenDialog(null);
        if (result != JFileChooser.APPROVE_OPTION)
        {
            System.exit(-1);
        }
        File cacheFile = chooser.getSelectedFile();
        
        // Get the cache list
        try (Scanner s = new Scanner(cacheFile))
        {
            allCaches = new CacheList(s);
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null,
                    "Error when reading cache file " + cacheFile);
            System.exit(-1);
        }
        catch (IllegalArgumentException e)
        {
            JOptionPane.showMessageDialog(null,
                    "Error when reading line " + e.getMessage() + " of cache file: " + cacheFile);
            System.exit(-1);
        }

        // Create the three main components of the user interface:
        // the list of caches, the searching area, and the results area.
        JList<Cache> items = new JList<Cache>();
        SearchPane search = new SearchPane(items, allCaches);
        ResultsPane results = new ResultsPane(items);

        // Set up the scrollable list of items
        JScrollPane cacheListPane = new JScrollPane(items);
        items.setListData(allCaches.select().toArray(new Cache[0]));
        items.addListSelectionListener(results);

        // Arrange the three panes
        JSplitPane topPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        setContentPane(topPane);
        topPane.add(cacheListPane, JSplitPane.LEFT);
        JSplitPane rightPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        rightPane.add(search, JSplitPane.TOP);
        rightPane.add(results, JSplitPane.BOTTOM);
        rightPane.setResizeWeight(0);
        topPane.add(rightPane, JSplitPane.RIGHT);

        // Arrange everything
        pack();
        items.setSelectedIndex(0);
    }
}

/**
 * A JPanel that displays information about a single cache
 */
@SuppressWarnings("serial")
class ResultsPane extends JPanel implements ListSelectionListener
{
    // Labels in which cache information is displayed
    private JLabel title;
    private JLabel owner;
    private JLabel difficulty;
    private JLabel terrain;
    private JLabel location;

    // List of all currently displayed caches
    JList<Cache> cacheList;

    /**
     * Lays out a display area for cache information
     */
    public ResultsPane(JList<Cache> caches)
    {
        cacheList = caches;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        title = new JLabel("  ");
        owner = new JLabel("  ");
        difficulty = new JLabel("  ");
        terrain = new JLabel("  ");
        location = new JLabel("  ");
        add(new JLabel("  "));
        add(title);
        title.setAlignmentX(CENTER_ALIGNMENT);
        add(owner);
        owner.setAlignmentX(CENTER_ALIGNMENT);
        add(difficulty);
        difficulty.setAlignmentX(CENTER_ALIGNMENT);
        add(terrain);
        terrain.setAlignmentX(CENTER_ALIGNMENT);
        add(location);
        location.setAlignmentX(CENTER_ALIGNMENT);
        add(new JLabel("  "));
    }

    /**
     * Sets the cache to be displayed. If c is null, no information is
     * displayed.
     */
    public void setCache (Cache c)
    {
        if (c == null)
        {
            title.setText(" ");
            owner.setText(" ");
            difficulty.setText(" ");
            terrain.setText(" ");
            location.setText(" ");
        }
        else
        {
            title.setText(c.getGcCode() + ": " + c.getTitle());
            owner.setText("by " + c.getOwner());
            difficulty.setText("Difficulty = " + c.getDifficulty());
            terrain.setText("Terrain = " + c.getTerrain());
            location.setText(c.getLatitude() + " " + c.getLongitude());
        }
    }

    /**
     * When this is called, the selected element is to be displayed.
     */
    public void valueChanged (ListSelectionEvent e)
    {
        setCache(cacheList.getSelectedValue());
    }
}

/**
 * A JPanel where it is possible to specify search criteria for geocaches
 */
@SuppressWarnings("serial")
class SearchPane extends JPanel implements ActionListener, ItemListener,
        DocumentListener
{
    private JList<Cache> currentCaches;
    private CacheList allCaches;
    private JTextField title;
    private JComboBox<String> owners;
    private JComboBox<String> lowDiff;
    private JComboBox<String> highDiff;
    private JComboBox<String> lowTerr;
    private JComboBox<String> highTerr;

    /**
     * Creates a SearchPane where geocache search criteria can be specified.
     */
    public SearchPane(JList<Cache> currentCaches, CacheList allCaches)
    {
        // Keep track of the currently-displayed caches and the list of all
        // caches
        this.currentCaches = currentCaches;
        this.allCaches = allCaches;

        // Lay out vertically
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // Create the title selection box
        JPanel titlePane = new JPanel();
        titlePane.add(new JLabel("Cache title contains"));
        titlePane.add(title = new JTextField(""));
        title.setPreferredSize(new Dimension(200, 25));
        title.getDocument().addDocumentListener(this);
        add(titlePane);

        // Create the owner selection box
        JPanel hidden = new JPanel();
        hidden.add(new JLabel("Owned by"));
        owners = new JComboBox<String>();
        owners.addItem("");
        for (String s : allCaches.getOwners())
        {
            owners.addItem(s);
        }
        hidden.add(owners);
        add(hidden);
        owners.setEditable(false);
        owners.setSelectedIndex(0);
        owners.addItemListener(this);

        // Create the difficulty selection area
        JPanel diff = new JPanel();
        diff.add(new JLabel("Difficulty is between"));
        lowDiff = new JComboBox<String>();
        lowDiff.addItem("1.0");
        lowDiff.addItem("1.5");
        lowDiff.addItem("2.0");
        lowDiff.addItem("2.5");
        lowDiff.addItem("3.0");
        lowDiff.addItem("3.5");
        lowDiff.addItem("4.0");
        lowDiff.addItem("4.5");
        lowDiff.addItem("5.0");
        lowDiff.setEditable(false);
        lowDiff.setSelectedItem("1.0");
        diff.add(lowDiff);
        diff.add(new JLabel("and"));
        highDiff = new JComboBox<String>();
        highDiff.addItem("1.0");
        highDiff.addItem("1.5");
        highDiff.addItem("2.0");
        highDiff.addItem("2.5");
        highDiff.addItem("3.0");
        highDiff.addItem("3.5");
        highDiff.addItem("4.0");
        highDiff.addItem("4.5");
        highDiff.addItem("5.0");
        highDiff.setEditable(false);
        highDiff.setSelectedItem("5.0");
        diff.add(highDiff);
        add(diff);
        lowDiff.addItemListener(this);
        highDiff.addItemListener(this);

        // Create the terrain selection area
        JPanel terr = new JPanel();
        terr.add(new JLabel("Terrain is between"));
        lowTerr = new JComboBox<String>();
        lowTerr.addItem("1.0");
        lowTerr.addItem("1.5");
        lowTerr.addItem("2.0");
        lowTerr.addItem("2.5");
        lowTerr.addItem("3.0");
        lowTerr.addItem("3.5");
        lowTerr.addItem("4.0");
        lowTerr.addItem("4.5");
        lowTerr.addItem("5.0");
        lowTerr.setEditable(false);
        lowTerr.setSelectedItem("1.0");
        terr.add(lowTerr);
        terr.add(new JLabel("and"));
        highTerr = new JComboBox<String>();
        highTerr.addItem("1.0");
        highTerr.addItem("1.5");
        highTerr.addItem("2.0");
        highTerr.addItem("2.5");
        highTerr.addItem("3.0");
        highTerr.addItem("3.5");
        highTerr.addItem("4.0");
        highTerr.addItem("4.5");
        highTerr.addItem("5.0");
        highTerr.setEditable(false);
        highTerr.setSelectedItem("5.0");
        terr.add(highTerr);
        lowTerr.addItemListener(this);
        highTerr.addItemListener(this);
        add(terr);
    }

    /**
     * This method is called when one of the controls is manipulated. We notify
     * the cache list of the change and then update the list of current caches.
     */
    public void actionPerformed (ActionEvent e)
    {
        update();
    }

    /**
     * Called when the title box is changed.
     */
    public void changedUpdate (DocumentEvent e)
    {
        update();
    }

    /**
     * Called when the title box is changed.
     */
    public void insertUpdate (DocumentEvent e)
    {
        update();
    }

    /**
     * Called when the title box is changed.
     */
    public void removeUpdate (DocumentEvent e)
    {
        update();
    }

    public void itemStateChanged (ItemEvent e)
    {
        if (e.getStateChange() == ItemEvent.SELECTED && e.getItem() != null)
        {
            update();
        }
    }

    // Helper for the above four methods.
    private void update ()
    {
        allCaches.setTitleConstraint(title.getText());
        allCaches.setOwnerConstraint((String) owners.getSelectedItem());
        allCaches.setDifficultyConstraints(
                Double.parseDouble((String) lowDiff.getSelectedItem()),
                Double.parseDouble((String) highDiff.getSelectedItem()));
        allCaches.setTerrainConstraints(
                Double.parseDouble((String) lowTerr.getSelectedItem()),
                Double.parseDouble((String) highTerr.getSelectedItem()));
        currentCaches.setListData(allCaches.select().toArray(new Cache[0]));
        currentCaches.setSelectedIndex(0);
    }
}