package com.ulist.ulist;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * This class creates the UserTransactionsActivity which allows the user to see and interact with all the transactions they have created or are apart of.
 */
public class UserTransactionsActivity extends AppCompatActivity {

    TransactionAdapter adapter;
    ListView transactionViewList;
    Toolbar toolbar;

    /**
     * This function creates the activity, toolbar, widgets, and listeners.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_transactions);

        toolbar = (Toolbar) findViewById(R.id.mainToolbarUserTrans);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        transactionViewList = (ListView)findViewById(R.id.transactionListView);

        final ArrayList<Transaction> transactionsList = new ArrayList<Transaction>();

        adapter = new TransactionAdapter(UserTransactionsActivity.this, transactionsList);

        transactionViewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                Bundle b = new Bundle();
                b.putSerializable("transaction", (Serializable) transactionsList.get(i));
                intent.putExtras(b);

                intent.setClass(UserTransactionsActivity.this, TransactionActivity.class);

                startActivity(intent);
            }
        });

        RestRequest rr = new RestRequest();
        StringBuffer sb;
        JSONArray transactionsJson = new JSONArray();

        try {
            sb = rr.execute("transactions/requests?username_buyer=" + UserSession.getInstance().user, "GET").get();
            if (sb == null || sb.toString().compareTo("ERROR") == 0) {
                Snackbar.make(findViewById(android.R.id.content), "Connection Error", Snackbar.LENGTH_SHORT).show();
                return;
            }
            transactionsJson = new JSONArray(sb.toString());
            transactionsList.clear();
            for (int i = 0; i < transactionsJson.length(); i++) {
                Transaction transaction = new Transaction(transactionsJson.getJSONObject(i));
                transactionsList.add(transaction);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String[] names = new String[transactionsJson.length()];

        for (int i = 0; i < names.length; i++) {
            names[i] = transactionsList.get(i).getTitle();
        }

        adapter = new TransactionAdapter(UserTransactionsActivity.this, transactionsList);
        transactionViewList.setAdapter(adapter);
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
        switch (item.getItemId()) {
            case R.id.action_search:
                startActivity(new Intent(UserTransactionsActivity.this, SearchActivity.class));
                return true;

            case R.id.action_sell:
                Intent intent = new Intent();
                Bundle b = new Bundle();
                //b.putSerializable("username", e_uName);
                //intent.putExtras(b);

                intent.setClass(UserTransactionsActivity.this, SellActivity.class);

                startActivity(intent);
                return true;

            case R.id.action_transactions:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                //startActivity(new Intent(UserTransactionsActivity.this, UserTransactionsActivity.class));
                return true;

            case R.id.action_profile:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                startActivity(new Intent(UserTransactionsActivity.this, UserProfileActivity.class));
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
