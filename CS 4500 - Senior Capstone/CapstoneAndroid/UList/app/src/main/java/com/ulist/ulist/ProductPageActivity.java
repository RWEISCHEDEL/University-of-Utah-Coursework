package com.ulist.ulist;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * This class creates the ProductPageActivity which enables the user to see all the information there is about a given product.
 */
public class ProductPageActivity extends AppCompatActivity {

    Button messageSeller, buyProduct, sellerProfile, favorite, submitQuestion;

    TextView title, price, seller, description, author;

    EditText question;

    ImageView image;

    Toolbar toolbar;

    ListView FAQView;

    FAQAdapter adapter;

    private String m_Text = "";

    /**
     * Create the activity, all its widgets and event listeners.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);

        toolbar = (Toolbar) findViewById(R.id.mainToolbarProduct);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle b = this.getIntent().getExtras();

        final Book book = (Book) b.get("book");

        title = (TextView) findViewById(R.id.txtProductTitle);
        title.setText(book.getTitle());
        author = (TextView) findViewById(R.id.txtProductAuthor);
        author.setText("By: " + book.getAuthor());
        price = (TextView) findViewById(R.id.txtProductPrice);
        price.setText("Price: $" + Float.toString(book.getPrice()));
        seller = (TextView) findViewById(R.id.txtProductSeller);
        seller.setText("Seller: " + book.getUsername());
        description = (TextView) findViewById(R.id.txtProductDescription);
        description.setText(book.getDescription());

        Bitmap bitmap = null;

        if (book.getImagePaths() != null) {
            ImageRequest ir = new ImageRequest();
            try {
                bitmap = ir.execute(book.getImagePaths()).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            if (bitmap != null)
                book.setBitmap(bitmap);
        }


        image = (ImageView) findViewById(R.id.productImageView);
        if (book.getBitmap() != null)
            image.setImageBitmap(book.getBitmap());


        messageSeller = (Button)findViewById(R.id.bMessageSeller);

        buyProduct = (Button)findViewById(R.id.bPurchaseItem);

        sellerProfile = (Button)findViewById(R.id.bViewSellerProfile);

        favorite = (Button)findViewById(R.id.bAddFavorites);

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(favorite.getText().toString().equals("Add To Favorites")){
                    favorite.setText("Remove From Favorites");
                    // Send Data to DB
                }
                else{
                    favorite.setText("Add To Favorites");
                    // Remove Data to DB
                }
            }
        });

        messageSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle b = new Bundle();

                ArrayList<String> user_ids = new ArrayList<>();

                user_ids.add(UserSession.getInstance().user);
                user_ids.add(book.getUsername());

                b.putSerializable("user_ids", user_ids);
                intent.putExtras(b);

                intent.setClass(ProductPageActivity.this, MessageListActivity.class);

                startActivity(intent);
            }
        });

        sellerProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle b = new Bundle();
                b.putSerializable("username_seller", (Serializable) book.getUsername());
                intent.putExtras(b);

                intent.setClass(ProductPageActivity.this, SellerProfileActivity.class);

                startActivity(intent);
            }
        });

       buyProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle b = new Bundle();
                b.putSerializable("username_seller", (Serializable) book.getUsername());
                b.putSerializable("product_id", (Serializable) book.getBookId());
                intent.putExtras(b);

                intent.setClass(ProductPageActivity.this, PurchaseProductActivity.class);

                startActivity(intent);
            }
        });

        final ArrayList<FAQEntry> faqList = updateFaq(book.getBookId());

        FAQView = (ListView)findViewById(R.id.FAQListView);

        adapter = new FAQAdapter(ProductPageActivity.this, faqList);

        FAQView.setAdapter(adapter);

        FAQView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        FAQView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get Selected Item
                FAQEntry faq = faqList.get(position);

                // Check if this person is the seller as well
                if(!faq.isAnswered()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProductPageActivity.this);
                    builder.setTitle("Title");

                    final EditText input = new EditText(ProductPageActivity.this);

                    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    builder.setView(input);

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            m_Text = input.getText().toString();

                            // Send m_text to answer question
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                }
            }
        });


        question = (EditText)findViewById(R.id.editTextAskAQuestion);
        submitQuestion = (Button)findViewById(R.id.bSubmitQuestion);

        submitQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                startActivity(new Intent(ProductPageActivity.this, SearchActivity.class));
                return true;

            case R.id.action_sell:
                startActivity(new Intent(ProductPageActivity.this, SellActivity.class));
                return true;

            case R.id.action_transactions:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                startActivity(new Intent(ProductPageActivity.this, UserTransactionsActivity.class));
                return true;

            case R.id.action_profile:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                startActivity(new Intent(ProductPageActivity.this, UserProfileActivity.class));
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    /**
     * Allow the users to ask questions on the product page.
     * @param bookId
     * @return
     */
    public ArrayList<FAQEntry> updateFaq(int bookId) {
        RestRequest rr = new RestRequest();
        StringBuffer sb;
        JSONArray faqs;
        final ArrayList<FAQEntry> faqList = new ArrayList<FAQEntry>();

        try {
            sb = rr.execute("faqs/retrieve?product_id=" + bookId, "GET").get();
            if (sb == null || sb.toString().compareTo("ERROR") == 0) {
                Snackbar.make(findViewById(android.R.id.content), "Connection Error", Snackbar.LENGTH_SHORT).show();
                return faqList;
            }

            faqs = new JSONArray(sb.toString());
            faqList.clear();
            for (int i = 0; i < faqs.length(); i++) {
                FAQEntry faqEntry = new FAQEntry(faqs.getJSONObject(i));
                faqList.add(faqEntry);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return faqList;
    }
}
