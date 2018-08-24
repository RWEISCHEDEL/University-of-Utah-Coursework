package com.ulist.ulist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TimePicker;
import java.util.Calendar;

import com.braintreepayments.api.BraintreeFragment;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.exceptions.InvalidArgumentException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

import static com.paypal.android.sdk.onetouch.core.metadata.ah.i;

/**
 * This class creates the PurchaseProductActivity which allows users to fill out all the information to purchase a product.
 */
public class PurchaseProductActivity extends AppCompatActivity {

    int REQUEST_CODE;
    Spinner location;
    Button requestButton, payPalB, venmoB, cashB, mapButton, dataPickerB;
    String locationValue, hourValue, minValue, dateYear, dateMonth, dateDay;
    String coordinates;
    String username_seller;
    int product_id;
    String paymentMethod;
    Toolbar toolbar;
    private double lat, lng;
    private Bundle b;

    TimePicker timePicker;
    DatePickerDialog datePickerDialog;

    /**
     * Create the Activity with the toolbar and all its features.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_purchase_product);

        toolbar = (Toolbar) findViewById(R.id.mainToolbarPurchaseProduct);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        timePicker = (TimePicker) findViewById(R.id.timePickerPurchase);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int min) {
                hourValue = hour + "";
                minValue = min + "";
                System.out.println(hourValue + " " + minValue);
            }
        });

        dataPickerB = (Button) findViewById(R.id.dataPickerButton);

        dataPickerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(PurchaseProductActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                //date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                //System.out.println(year + " " + monthOfYear + " " + dayOfMonth);
                                dateYear = year + "";
                                dateMonth = monthOfYear + "";
                                dateDay = dayOfMonth + "";

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        // Transfer Information From the Map Activity

        b = this.getIntent().getExtras();

        if(b.containsKey("lat")) {
            lat = (double) b.get("lat");
            lng = (double) b.get("lng");
        }

        if(b.containsKey("username_seller")) {
            username_seller = (String) b.get("username_seller");
            product_id = (Integer) b.get("product_id");
        }

        requestButton = (Button)findViewById(R.id.requestBuyButton);
        mapButton = (Button)findViewById(R.id.googleMapButton);

        cashB = (Button)findViewById(R.id.cashButton);

        cashB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentMethod = "Cash";
            }
        });

        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RestRequest rr = new RestRequest();
                StringBuffer sb = null;
                JSONObject transaction = new JSONObject();

                try {
                    String date;
                    if (timePicker.getHour() < 10)
                        date = dateYear + "-" +
                            dateMonth + "-" + dateDay + " 0" + timePicker.getHour() + ":" + timePicker.getMinute();
                    else
                        date = dateYear + "-" +
                                dateMonth + "-" + dateDay + " " + timePicker.getHour() + ":" + timePicker.getMinute();

                    sb = rr.execute("transactions/create/request", "POST",
                            "username_seller", username_seller,
                            "username_buyer", UserSession.getInstance().user,
                            "date", date,
                            "longitude", Double.toString(lng),
                            "latitude", Double.toString(lat),
                            "comments", ((EditText) findViewById(R.id.editText)).getText().toString(),
                            "product_id", Integer.toString(product_id)).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent();

                intent.setClass(PurchaseProductActivity.this, UserTransactionsActivity.class);

                startActivity(intent);
            }
        });

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtras(b);

                intent.setClass(PurchaseProductActivity.this, MeetingMapSelectionActivity.class);

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
                startActivity(new Intent(PurchaseProductActivity.this, SearchActivity.class));
                return true;

            case R.id.action_sell:
                Intent intent = new Intent();
                Bundle b = new Bundle();
                //b.putSerializable("username", e_uName);
                //intent.putExtras(b);

                intent.setClass(PurchaseProductActivity.this, SellActivity.class);

                startActivity(intent);
                return true;

            case R.id.action_transactions:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                startActivity(new Intent(PurchaseProductActivity.this, UserTransactionsActivity.class));
                return true;

            case R.id.action_profile:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                startActivity(new Intent(PurchaseProductActivity.this, UserProfileActivity.class));
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void onBraintreeSubmit(View view) {
        DropInRequest dropInRequest = new DropInRequest()
                .clientToken("sandbox_nc8fqqrh_2rgqj5jgrv33xnzm");
        startActivityForResult(dropInRequest.getIntent(this), REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                Snackbar.make(findViewById(android.R.id.content), "Payment Information Saved Successfully", Snackbar.LENGTH_SHORT).show();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // the user canceled
            } else {
                // handle errors here, an exception may be available in
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
            }
        }
    }
}
