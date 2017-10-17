package edu.utah.cs4530.cstoolbox;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.opencsv.CSVReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by Robert on 12/12/2016.
 */

public class CSVToJSONConverterActivity extends AppCompatActivity implements Button.OnClickListener{

    EditText input;
    EditText output;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);

        TextView mainView = new TextView(this);
        mainView.append("CSV To JSON Converter");
        mainView.setTextSize(20.0f);

        LinearLayout inputLayout = new LinearLayout(this);
        inputLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView inputView = new TextView(this);
        inputView.append("Input: ");
        inputView.setTextSize(20.0f);
        input = new EditText(this);
        //inputLayout.addView(inputView);
        inputLayout.addView(input);

        LinearLayout outputLayout = new LinearLayout(this);
        outputLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView outputView = new TextView(this);
        outputView.append("Output: ");
        outputView.setTextSize(20.0f);
        output = new EditText(this);
        //outputLayout.addView(outputView);
        outputLayout.addView(output);

        LinearLayout buttonLayout = new LinearLayout(this);
        buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
        Button clearButton = new Button(this);
        clearButton.setOnClickListener(this);
        clearButton.setTag("clear");
        clearButton.setText("CLEAR");
        Button convertButton = new Button(this);
        convertButton.setOnClickListener(this);
        convertButton.setTag("convert");
        convertButton.setText("CONVERT");
        Button helpButton = new Button(this);
        helpButton.setOnClickListener(this);
        helpButton.setTag("help");
        helpButton.setText("HELP");
        buttonLayout.addView(convertButton);
        buttonLayout.addView(clearButton);
        buttonLayout.addView(helpButton);

        rootLayout.addView(mainView);
        rootLayout.addView(inputView);
        rootLayout.addView(inputLayout);
        rootLayout.addView(outputView);
        rootLayout.addView(outputLayout);
        rootLayout.addView(buttonLayout);

        setContentView(rootLayout);
    }

    @Override
    public void onClick(View view) {
        if(view.getTag().toString().equals("convert")){

            if(!createCSVFile()){
                output.setText("CSV not in valid format.");
                return;
            }

            DocumentBuilderFactory documentFactory = null;
            DocumentBuilder documentBuilder = null;

            try {
                documentFactory = DocumentBuilderFactory.newInstance();
                documentBuilder = documentFactory.newDocumentBuilder();
            } catch (FactoryConfigurationError exp) {
                System.err.println(exp.toString());
                output.setText("The Document Factory Failed to create a new instance.");
                return;
            } catch (ParserConfigurationException exp) {
                System.err.println(exp.toString());
                output.setText("The Parser failed to configure correctly.");
                return;
            } catch (Exception exp) {
                System.err.println(exp.toString());
                output.setText("Failed due to some unknown error.");
                return;
            }

            int rowsCount = -1;
            try {
                Document newDocument = documentBuilder.newDocument();
                Element rootElement = newDocument.createElement("JSON");
                newDocument.appendChild(rootElement);

                Log.i("onclick", "before reader");
                FileReader fileReader = new FileReader(new File(getFilesDir(), "convert-input-json.csv"));
                CSVReader reader = new CSVReader(fileReader);
                Log.i("onclick", "after reader");
                String[] nextLine;
                int line = 0;
                List<String> headers = new ArrayList<String>(5);
                while ((nextLine = reader.readNext()) != null) {

                    if (line == 0) {
                        for (String col : nextLine) {
                            headers.add(col);
                        }
                    } else {
                        Element rowElement = newDocument.createElement("row");
                        rootElement.appendChild(rowElement);

                        int col = 0;
                        for (String value : nextLine) {
                            String header = headers.get(col);

                            Element curElement = newDocument.createElement(header);
                            curElement.appendChild(newDocument.createTextNode(value.trim()));
                            rowElement.appendChild(curElement);

                            col++;
                        }
                    }
                    line++;
                }

                FileWriter writer = null;

                try {

                    Log.i("onclick", "before writer");
                    writer = new FileWriter(new File(getFilesDir(), "convert-output-json.xml"));
                    Log.i("onclick", "after writer");

                    Log.i("onclick", "before trans");
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
                    Log.i("onclick", "after trans");

                    Source src = new DOMSource(newDocument);
                    Result result = new StreamResult(writer);
                    transformer.transform(src, result);

                    Log.i("onclick - trans", result.toString());
                    writer.flush();

                } catch (Exception exp) {
                    exp.printStackTrace();
                    output.setText("There was a issue when creating the XML file.");
                    return;
                } finally {
                    try {
                        writer.close();
                    } catch (Exception e) {
                        output.setText("The XML failed to close");
                        return;
                    }
                }

            } catch (IOException exp) {
                System.err.println(exp.toString());
                output.setText("Failed to read in .csv file.");
                return;
            } catch (Exception exp) {
                System.err.println(exp.toString());
                output.setText("A error occured when reading in the .csv file into a XML file.");
                return;
            }

            Log.i("onclick", rowsCount + "");

            convertXMLFileToJSONString();

        }
        else if(view.getTag().toString().equals("clear")){
            input.setText("");
            output.setText("");
        }
        else if(view.getTag().toString().equals("help")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Enter the CSV string you wish to convert to JSON beneath the Input label, and then " +
                    "press the VALIDATE button. NOTE: The CSV must be entered with new line characters after each line, " +
                    "see the following example. \n" + "Example:\n firstname,lastname,job\\n \n" +
                    " Luke,Skywalker,Jedi\\n \n Leia,Organa,Princess\\n \nThe CLEAR button will clear all text from both " +
                    "the input and output fields.");
            builder.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            builder.setCancelable(true);
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    public boolean createCSVFile(){
        boolean valid = true;

        if(!input.getText().toString().contains("\\n")){
            valid = false;
            return valid;
        }

        String[] lines = input.getText().toString().split("\n");

        for (String l : lines) {
            Log.i("Validate CSV lines: ", l);
            if(!l.contains("\\n")){
                Log.i("Failed CSV string: ", l);
                valid = false;
                return valid;
            }
        }

        for(int i = 0; i < lines.length; i++){
            lines[i] = lines[i].substring(0,lines[i].length()-2);
        }

        for(String s : lines){
            Log.i("onclick", s);
        }

        try {
            Log.i("createFile", "before");
            File file = new File("convert-input-json.csv");
            if(file.exists()){
                file.delete();
            }
            Log.i("createFile", "after");

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.openFileOutput("convert-input-json.csv", Context.MODE_PRIVATE));
            for(String s : lines){
                outputStreamWriter.write(s + "\r\n");
            }
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
            valid = false;
        }

        return valid;
    }

    public void convertXMLFileToJSONString(){
        String answer = "";

        try {
            InputStream inputStream = this.openFileInput("convert-output-json.xml");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                answer = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
            output.setText("Cannot find JSON file");
            return;
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
            output.setText("There was a issue reading the JSON file.");
            return;
        }

        JSONObject obj = null;
        try {
            obj = XML.toJSONObject(answer);
            Log.i("onclick", "made it here 2 ");
            answer = obj.toString(4);
            output.setText(answer);
        } catch (JSONException e) {
            output.setText("Inputed Invalid CSV");
            e.printStackTrace();
        }

    }
}
