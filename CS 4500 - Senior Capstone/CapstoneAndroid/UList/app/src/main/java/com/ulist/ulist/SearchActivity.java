package com.ulist.ulist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.ImageReader;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * This class creates the SearchActivity which allows the user to find the desired product they want.
 */
public class SearchActivity extends AppCompatActivity {

    ListView searchView;
    Button searchButton;
    EditText e_search;
    Spinner spinner;

    SearchAdapter adapter;
    Toolbar toolbar;
    String e_uName;

    /**
     * Create the activity, along with the widgets, and listeners.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        e_uName = UserSession.getInstance().user;
        e_search = (EditText)findViewById(R.id.txtSearchBar);
        spinner = (Spinner)findViewById(R.id.searchSpinner);
        spinner.setSelection(1);

        toolbar = (Toolbar) findViewById(R.id.mainToolbarSearch);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final ArrayList<Book> booksList = new ArrayList<Book>();

        searchView = (ListView)findViewById(R.id.searchListView);

        searchButton = (Button)findViewById(R.id.bSearch);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // On button Click call database and populate the list View
                RestRequest rr = new RestRequest();
                StringBuffer sb;
                JSONArray books = new JSONArray();

                String text  = e_search.getText().toString();
                text = text.replaceAll(" ", "%25");

                try {
                    sb = rr.execute("shop/books?search=" + text, "GET").get();
                    if (sb == null || sb.toString().compareTo("ERROR") == 0) {
                        Snackbar.make(findViewById(android.R.id.content), "Connection Error", Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                    books = new JSONArray(sb.toString());
                    booksList.clear();
                    for (int i = 0; i < books.length(); i++) {
                        Book book = new Book(books.getJSONObject(i));
                        Bitmap bitmap = null;
                        if (book.getImagePaths() != null) {
                            ImageRequest ir = new ImageRequest();
                            bitmap = ir.execute(book.getImagePaths()).get();
                            if (bitmap != null)
                                book.setBitmap(bitmap);
                        }
                        booksList.add(book);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapter = new SearchAdapter(SearchActivity.this, booksList);
                searchView.setAdapter(adapter);
            }
        });

        searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                RestRequest rr = new RestRequest();
                StringBuffer sb;
                JSONObject bookJson = new JSONObject();

                Book book = booksList.get(i);

//                try {
//                    sb = rr.execute("shop/books?book_id=" + booksList.get(i).getBookId(), "GET").get();
//                    bookJson = new JSONObject(sb.toString());
//                    book = new Book(bookJson);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

                book.setBitmap(null);

                System.out.println("Selected Search View Item");
                String s = searchView.getItemAtPosition(i).toString();

                Intent intent = new Intent();
                Bundle b = new Bundle();
                b.putSerializable("book",book);
                intent.putExtras(b);

                intent.setClass(SearchActivity.this, ProductPageActivity.class);

                startActivity(intent);
            }
        });
    }

    /**
     * Allows the toolbar to have a back navigation button
     * @return
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     * Creates the toolbar menu options from the menu layout described in R.menu.toolbar
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }


    /**
     * This is the Listener options when the user selects a option from the Toolbar. It will then direct them to the correct page.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                //startActivity(new Intent(SearchActivity.this, SellActivity.class));
                return true;

            case R.id.action_sell:
                Intent intent = new Intent();
                Bundle b = new Bundle();
                b.putSerializable("username", e_uName);
                intent.putExtras(b);

                intent.setClass(SearchActivity.this, SellActivity.class);

                startActivity(intent);
                return true;

            case R.id.action_transactions:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                startActivity(new Intent(SearchActivity.this, UserTransactionsActivity.class));
                return true;

            case R.id.action_profile:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                startActivity(new Intent(SearchActivity.this, UserProfileActivity.class));
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
