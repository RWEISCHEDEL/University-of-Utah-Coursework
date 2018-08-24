package com.ulist.ulist;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * This class creates the SellActivity which allows the user to place their desired item for sale.
 */
public class SellActivity extends AppCompatActivity {

    String e_uName;
    EditText itemName, payment, tags, price, author, isbn, description;
    Spinner category;
    Button uploadImage, listItem;
    ImageView productImg;
    int RESULT_LOAD_IMG = 1;
    Toolbar toolbar;
    int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 12552345;
    Uri uri = null;
    Activity activity;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * This function creates the activity along with all its widgets and listeners.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        activity = this;

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        toolbar = (Toolbar) findViewById(R.id.mainToolbarSell);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle b = this.getIntent().getExtras();

        e_uName = (String) b.get("username");

        itemName = (EditText)findViewById(R.id.txtItemName);
        payment = (EditText)findViewById(R.id.txtPayment);
        tags = (EditText)findViewById(R.id.txtTags);
        price = (EditText)findViewById(R.id.txtPrice);
        author = (EditText)findViewById(R.id.txtAuthor);
        isbn = (EditText)findViewById(R.id.txtISBN);
        description = (EditText)findViewById(R.id.txtItemDescription);

        uploadImage = (Button)findViewById(R.id.bUploadIMG);
        listItem = (Button)findViewById(R.id.bListItem);

        productImg = (ImageView)findViewById(R.id.sellImageView);

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        });

        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // All Edit Text Box information
                String itemNameValue = itemName.getText().toString();
                String paymentValue = payment.getText().toString();
                String tagValue = tags.getText().toString();
                String priceValue = price.getText().toString();
                String authorValue = author.getText().toString();
                String isbnValue = isbn.getText().toString();
                String descriptionValue = description.getText().toString();

                Service service = new Retrofit.Builder().baseUrl("http://52.53.155.198:3001/").build().create(Service.class);

                if (uri == null) {
                    Snackbar.make(findViewById(android.R.id.content), "Please select an image for your product.", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                File file = new File(getRealPathFromUri(getApplicationContext(), uri));

                RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("images", file.getName(), reqFile);
                RequestBody username = RequestBody.create(MediaType.parse("text/plain"), e_uName);
                RequestBody price = RequestBody.create(MediaType.parse("text/plain"), priceValue);
                RequestBody desc = RequestBody.create(MediaType.parse("text/plain"), descriptionValue);
                RequestBody payment = RequestBody.create(MediaType.parse("text/plain"), paymentValue);
                RequestBody title = RequestBody.create(MediaType.parse("text/plain"), itemNameValue);
                RequestBody author = RequestBody.create(MediaType.parse("text/plain"), authorValue);
                RequestBody isbn = RequestBody.create(MediaType.parse("text/plain"), isbnValue);

                retrofit2.Call<okhttp3.ResponseBody> req = service.postImage(body, username, price, desc, payment, title, author, isbn);
                System.out.print(req.toString());
                req.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Intent intent = new Intent(SellActivity.this, SearchActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

//                try {
//
//
//                    sb = rr.execute("uploads/product", "POST",
//                            "title", itemNameValue,
//                            "author", authorValue,
//                            "desc", descriptionValue,
//                            "payment", paymentValue,
//                            "isbn", isbnValue,
//                            "username", e_uName,
//                            "price", priceValue,
//                            "images", image_str).get();
//                    if (sb == null || sb.toString().compareTo("ERROR") == 0) {
//                        Snackbar.make(findViewById(android.R.id.content), "Connection Error", Snackbar.LENGTH_SHORT).show();
//                        return;
//                    }
//                    JSONObject list = new JSONObject(sb.toString());
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

}
        });
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        verifyStoragePermissions(activity);
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                productImg.setImageBitmap(selectedImage);
                productImg.buildDrawingCache();
                uri = imageUri;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                //Toast.makeText(PostImage.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else {
            //Toast.makeText(PostImage.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
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
                startActivity(new Intent(SellActivity.this, SearchActivity.class));
                return true;

            case R.id.action_sell:
                //startActivity(new Intent(SearchActivity.this, SellActivity.class));
                return true;

            case R.id.action_transactions:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                startActivity(new Intent(SellActivity.this, UserTransactionsActivity.class));
                return true;

            case R.id.action_profile:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                startActivity(new Intent(SellActivity.this, UserProfileActivity.class));
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    interface Service {
        @Multipart
        @POST("uploads/product")
        Call<ResponseBody> postImage(
                @Part MultipartBody.Part images,
                @Part("username") RequestBody username,
                @Part("price") RequestBody price,
                @Part("desc") RequestBody description,
                @Part("payment") RequestBody preferred_payment_method,
                @Part("title") RequestBody title,
                @Part("author") RequestBody author,
                @Part("isbn") RequestBody isbn
        );
    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
