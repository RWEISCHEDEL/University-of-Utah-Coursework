package com.ulist.ulist;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Matt on 12/7/2017.
 * This Class servers as an abstraction for the Book objects that users can buy and sell. It stores all the needed data for a particular book for sale.
 */
public class Book implements Serializable {
    public int getBookId() {return bookId;}

    public String getUsername() {return username;}

    public int getUniId() {return uniId;}

    public float getPrice() {return price;}

    public String getDescription() {return description;}

    public String getPreferredPayment() {return preferredPayment;}

    public String getTitle() {return title;}

    public String getAuthor() {return author;}

    public String getIsbn() {return isbn;}

    public String getImagePaths() {return imagePaths;}

    private int bookId;
    private String username;
    private int uniId;
    private float price;
    private String description;
    private String preferredPayment;
    private String title;
    private String author;
    private String isbn;
    private String imagePaths;
    private Bitmap bitmap;

    public Book(JSONObject jsonObject) throws JSONException {
        bookId = (int) jsonObject.get("book_id");
        username = (String) jsonObject.get("username");
        uniId = (int) jsonObject.getInt("uni_id");
        price = (float) jsonObject.getDouble("price");
        description = jsonObject.getString("description");
        preferredPayment = jsonObject.getString("preferred_payment_method");
        title = jsonObject.getString("title");
        author = jsonObject.getString("author");
        isbn = jsonObject.getString("isbn");
        imagePaths = jsonObject.getString("image_paths");
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
