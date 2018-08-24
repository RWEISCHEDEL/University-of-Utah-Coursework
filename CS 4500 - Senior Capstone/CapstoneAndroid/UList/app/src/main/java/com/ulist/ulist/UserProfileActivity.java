package com.ulist.ulist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.sendbird.android.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * This class creates the UserProfileActivity which displays all the information about the user.
 */
public class UserProfileActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    TextView username, realname;
    ImageView imageViewUser;
    Button userReviewsButton;
    RatingBar userRatingBar;
    Toolbar toolbar;
    ListView reviewsView;
    ReviewsAdapter adapter;

    /**
     * This creates the activity, the toolbar, widgets, and listeners.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        toolbar = (Toolbar) findViewById(R.id.mainToolbarProfile);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        realname = (TextView)findViewById(R.id.txtProfilePageRealName);
        username = (TextView)findViewById(R.id.txtProfilePageUserName);
        imageViewUser = (ImageView)findViewById(R.id.imageViewUser);

        RestRequest rr = new RestRequest();
        StringBuffer sb = null;
        Profile profile = null;

        String username2 = UserSession.getInstance().user;

        try {
            sb = rr.execute("users/profile?username=" + username2, "GET").get();
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
                imageViewUser = (ImageView) findViewById(R.id.imageViewUser);
                imageViewUser.setImageBitmap(bitmap);
            }

        }

        username.setText("Username: " + profile.getUsername());
        realname.setText("Fullname: " + profile.getFullname());

        reviewsView = (ListView)findViewById(R.id.userReviews);

        ArrayList<Review> reviewList = updateReviews(profile.getUsername());

        adapter = new ReviewsAdapter(UserProfileActivity.this, reviewList);
        reviewsView.setAdapter(adapter);



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

    private void configureToolbar() {
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setHomeAsUpIndicator(R.drawable.ic_drawer2);
        actionbar.setDisplayHomeAsUpEnabled(true);*/
    }

   /* private void configureNavigationDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.navigation);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Fragment f = null;
                int itemId = menuItem.getItemId();

                if (itemId == R.id.action_search) {
                    //f = new RefreshFragment();
                    System.out.println("Go to Search");
                    drawerLayout.closeDrawers();
                    startActivity(new Intent(UserProfileActivity.this, SearchActivity.class));
                    //return true;
                }
                else if (itemId == R.id.action_sell) {
                    //f = new StopFragment();
                    System.out.println("Go to sell");
                    drawerLayout.closeDrawers();
                    startActivity(new Intent(UserProfileActivity.this, SellActivity.class));
                    //return true;
                }
                else if(itemId == R.id.action_profile){
                    System.out.println("Go to sell");
                    drawerLayout.closeDrawers();
                    startActivity(new Intent(UserProfileActivity.this, UserProfileActivity.class));
                    return true;
                }
                else{
                    //drawerLayout.closeDrawers();
                    //return true;
                }

                /*if (f != null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame, f);
                    transaction.commit();
                    drawerLayout.closeDrawers();
                    return true;
                }*/

                //drawerLayout.closeDrawers();
                //return false;
          //  }
        //});
   // }


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
                startActivity(new Intent(UserProfileActivity.this, SearchActivity.class));
                return true;

            case R.id.action_sell:
                Intent intent = new Intent();
                Bundle b = new Bundle();
                //b.putSerializable("username", e_uName);
                //intent.putExtras(b);

                intent.setClass(UserProfileActivity.this, SellActivity.class);

                startActivity(intent);
                return true;

            case R.id.action_transactions:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                startActivity(new Intent(UserProfileActivity.this, UserTransactionsActivity.class));
                return true;

            case R.id.action_profile:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                //startActivity(new Intent(UserProfileActivity.this, UserProfileActivity.class));
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
