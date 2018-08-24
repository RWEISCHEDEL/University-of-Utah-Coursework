package com.ulist.ulist;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Robert on 3/2/2018.
 * This class serves as an abstraction for all the information about a given potential buyer/seller transaction.
 */

public class Transaction implements Serializable {

    private int meetupId;
    private String usernameSeller;
    private String usernameBuyer;
    private int sellerReady;
    private int buyerReady;
    private String date;
    private int accepted;
    private String comments;
    private int pending;
    private int productId;
    private double lng;
    private double lat;
    private String title;

    public Transaction(JSONObject jsonObject) throws JSONException {
        meetupId = jsonObject.getInt("meetup_id");
        usernameSeller = jsonObject.getString("username_seller");
        usernameBuyer = jsonObject.getString("username_buyer");
        sellerReady = jsonObject.getInt("seller_ready");
        buyerReady = jsonObject.getInt("buyer_ready");
        date = jsonObject.getString("date");
        accepted = jsonObject.getInt("accepted");
        comments = jsonObject.getString("comments");
        pending = jsonObject.getInt("pending");
        productId = jsonObject.getInt("product_id");
        lng = jsonObject.getDouble("longitude");
        lat = jsonObject.getDouble("latitude");
        title = jsonObject.getString("title");
    }

    public String getTitle() {
        return title;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public int getPending() {
        return pending;
    }

    public String getComments() {
        return comments;
    }

    public int getAccepted() {
        return accepted;
    }

    public String getDate() {
        return date;
    }

    public int getBuyerReady() {
        return buyerReady;
    }

    public int getSellerReady() {
        return sellerReady;
    }

    public String getUsernameBuyer() {
        return usernameBuyer;
    }

    public String getUsernameSeller() {
        return usernameSeller;
    }

    public int getMeetupId() {
        return meetupId;
    }

    public int getProductId() {
        return productId;
    }
}
