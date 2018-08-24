package com.ulist.ulist;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Matt on 4/18/2018.
 * This class serves as an abstraction for the ratings users can leave.
 */

class Review {

    private final String username;
    private final String reviewername;
    private final int rating;
    private final String comment;

    public Review(JSONObject jsonObject) throws JSONException {
        username = jsonObject.getString("username");
        reviewername = jsonObject.getString("reviewername");
        rating = jsonObject.getInt("rating");
        comment = jsonObject.getString("comment");
    }

    public String getUsername() {
        return username;
    }

    public String getReviewername() {
        return reviewername;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }
}
