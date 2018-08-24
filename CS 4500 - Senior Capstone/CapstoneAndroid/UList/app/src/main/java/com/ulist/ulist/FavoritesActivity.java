package com.ulist.ulist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * This class creates the Favorites Activity, where users can see the items that are currently for sale, they have favorited.
 */
public class FavoritesActivity extends AppCompatActivity {

    Toolbar toolbar;
    SearchAdapter adapter;
    ListView favoritesView;

    /**
     * Create the Favorites Activity with the toolbar and all its features.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        toolbar = (Toolbar) findViewById(R.id.mainToolbarFavorites);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final ArrayList<Book> favsList = new ArrayList<Book>();

        favoritesView = (ListView)findViewById(R.id.favoritesListView);

        adapter = new SearchAdapter(FavoritesActivity.this, favsList);

        favoritesView.setAdapter(adapter);


        favoritesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get Selected Item
            }
        });
    }

    /**
     * Creates the toolbar menu options from the menu layout described in R.menu.toolbar
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
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
     * This is the Listener options when the user selects a option from the Toolbar. It will then direct them to the correct page.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_search:
                startActivity(new Intent(FavoritesActivity.this, SearchActivity.class));
                return true;

            case R.id.action_sell:
                Intent intent = new Intent();
                Bundle b = new Bundle();
                //b.putSerializable("username", e_uName);
                //intent.putExtras(b);

                intent.setClass(FavoritesActivity.this, SellActivity.class);

                startActivity(intent);
                return true;

            case R.id.action_transactions:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                startActivity(new Intent(FavoritesActivity.this, UserTransactionsActivity.class));
                return true;

            case R.id.action_profile:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                startActivity(new Intent(FavoritesActivity.this, UserProfileActivity.class));
                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }

    }
}
