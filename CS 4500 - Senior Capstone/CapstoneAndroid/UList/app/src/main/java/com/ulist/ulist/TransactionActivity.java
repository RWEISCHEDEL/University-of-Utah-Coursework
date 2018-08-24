package com.ulist.ulist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * This class creates the TransactionActivity which displays all the information about a single transaction.
 */
public class TransactionActivity extends AppCompatActivity {

    Button ImHere;
    Button Pay;
    Button Cancel;
    Button leaveReview;
    Toolbar toolbar;

    TextView title, seller, date;

    TextView warningMessage;

    Transaction transaction;

    LinearLayout paymentOptions, reviewOptions;

    /**
     * This function creates the activity, its toolbar, widgets and listeners.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        transaction = (Transaction) this.getIntent().getExtras().get("transaction");

        paymentOptions = (LinearLayout)findViewById(R.id.TransactionAcceptCancelLayout);

        reviewOptions = (LinearLayout)findViewById(R.id.TransactionLeaveReview);

        toolbar = (Toolbar) findViewById(R.id.mainToolbarTrans);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ImHere = (Button) findViewById(R.id.bTransactionIHere);
        Pay = (Button) findViewById(R.id.bTransactionPay);
        Cancel = (Button) findViewById(R.id.bTransactionCancel);
        leaveReview = (Button)findViewById(R.id.bLeaveReview);

        title = (TextView) findViewById(R.id.txtTransactionItemName);
        title.setText(transaction.getTitle());
        seller = (TextView) findViewById(R.id.txtTransactionItemOwner);
        seller.setText(transaction.getUsernameSeller());
        date = (TextView) findViewById(R.id.txtTransactionTimeLoc);
        String rawDate = transaction.getDate();
        String[] dateSplit = rawDate.split("T");
        String dateT = dateSplit[0];
        String time = dateSplit[1].substring(0,5);
        date.setText("At: " + dateT + "  " + time);

        RestRequest rr = new RestRequest();
        StringBuffer sb;
        Book currentBook = null;

        try {
            sb = rr.execute("shop/books?book_id=" + transaction.getProductId(), "GET").get();
            currentBook = new Book(new JSONObject(sb.toString()));
            Bitmap bitmap = null;
            if (currentBook.getImagePaths() != null) {
                ImageRequest ir = new ImageRequest();
                bitmap = ir.execute(currentBook.getImagePaths()).get();
                if (bitmap != null)
                    currentBook.setBitmap(bitmap);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ImageView productImage = (ImageView) findViewById(R.id.transProductImage);
        productImage.setImageResource(R.drawable.book_small);
        if (currentBook != null && currentBook.getBitmap() != null) {
            productImage.setImageBitmap(currentBook.getBitmap());
        }

        ImHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImHere.setVisibility(View.GONE);

                paymentOptions.setVisibility(View.VISIBLE);

                Pay.setVisibility(View.VISIBLE);

                Cancel.setVisibility(View.VISIBLE);

                warningMessage.setVisibility(View.VISIBLE);

            }
        });

        Pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If paypal go to paypal site

                // If payment goes through

                paymentOptions.setVisibility(View.GONE);

                Pay.setVisibility(View.GONE);

                Cancel.setVisibility(View.GONE);

                warningMessage.setVisibility(View.GONE);

            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TransactionActivity.this, UserTransactionsActivity.class));
            }
        });

        leaveReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TransactionActivity.this, UserTransactionsActivity.class));
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
                startActivity(new Intent(TransactionActivity.this, SearchActivity.class));
                return true;

            case R.id.action_sell:
                Intent intent = new Intent();
                Bundle b = new Bundle();
                //b.putSerializable("username", e_uName);
                intent.putExtras(b);

                intent.setClass(TransactionActivity.this, SellActivity.class);

                startActivity(intent);
                return true;


            case R.id.action_transactions:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                startActivity(new Intent(TransactionActivity.this, UserTransactionsActivity.class));
                return true;

            case R.id.action_profile:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                startActivity(new Intent(TransactionActivity.this, UserProfileActivity.class));
                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
