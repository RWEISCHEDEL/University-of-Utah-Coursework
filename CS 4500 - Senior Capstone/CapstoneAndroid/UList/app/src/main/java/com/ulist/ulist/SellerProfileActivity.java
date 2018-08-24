package com.ulist.ulist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * This class creates the SellerProfileActivity, which contains all the information about the seller of a particular product.
 */
public class SellerProfileActivity extends AppCompatActivity {

    Button sellerReviewsButton;
    RatingBar sellerRatingBar;
    ImageView profilePicture;
    TextView username;
    TextView realname;
    Toolbar toolbar;
    ListView reviewsView;
    ReviewsAdapter adapter;

    /**
     * Create the activity, its toolbar, widgets and event handlers.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_profile);

        toolbar = (Toolbar) findViewById(R.id.mainToolbarSellerProfile);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        RestRequest rr = new RestRequest();
        StringBuffer sb = null;
        Profile profile = null;

        Bundle b = this.getIntent().getExtras();

        try {
            sb = rr.execute("users/profile?username=" + b.get("username_seller"), "GET").get();
            JSONArray jsonArray = new JSONArray(sb.toString());
            profile = new Profile(jsonArray.getJSONObject(0));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Bitmap bitmap = null;

        if (profile.getImage_names() != null) {
            ImageRequest ir = new ImageRequest();
            try {
                bitmap = ir.execute(profile.getImage_names()).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            if (bitmap != null) {
                profilePicture = (ImageView) findViewById(R.id.imageView6);
                    profilePicture.setImageBitmap(bitmap);
            }

        }

        username = (TextView) findViewById(R.id.textView2);
        username.setText("Username: " + profile.getUsername());

        realname = (TextView) findViewById(R.id.txtSellerProfilePageRealName);
        realname.setText("Fullname: " + profile.getFullname());

        reviewsView = (ListView)findViewById(R.id.listViewRatings);

        ArrayList<Review> reviewList = updateReviews(profile.getUsername());

        adapter = new ReviewsAdapter(SellerProfileActivity.this, reviewList);
        reviewsView.setAdapter(adapter);

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
                startActivity(new Intent(SellerProfileActivity.this, SearchActivity.class));
                return true;

            case R.id.action_sell:
                Intent intent = new Intent();
                Bundle b = new Bundle();
                //b.putSerializable("username", e_uName);
                //intent.putExtras(b);

                intent.setClass(SellerProfileActivity.this, SellActivity.class);

                startActivity(intent);
                return true;

            case R.id.action_transactions:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                startActivity(new Intent(SellerProfileActivity.this, UserTransactionsActivity.class));
                return true;

            case R.id.action_profile:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                startActivity(new Intent(SellerProfileActivity.this, UserProfileActivity.class));
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public ArrayList<Review> updateReviews(String username) {
        RestRequest rr = new RestRequest();
        StringBuffer sb;
        JSONArray reviews;
        final ArrayList<Review> reviewList = new ArrayList<Review>();

        try {
            sb = rr.execute("review?username=" + username, "GET").get();
            if (sb == null || sb.toString().compareTo("ERROR") == 0) {
                Snackbar.make(findViewById(android.R.id.content), "Connection Error", Snackbar.LENGTH_SHORT).show();
                return reviewList;
            }

            reviews = new JSONArray(sb.toString());
            reviewList.clear();
            for (int i = 0; i < reviews.length(); i++) {
                Review review = new Review(reviews.getJSONObject(i));
                reviewList.add(review);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return reviewList;
    }
}
