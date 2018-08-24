package com.ulist.ulist;

import java.io.Serializable;

/**
 * Created by Robert on 3/1/2018.
 * This class serves as an abstraction for the ratings users can leave.
 */

public class RatingReviews implements Serializable {

private int ratingValue;

private String rater;

private String review;

public RatingReviews(){
    // Fill in with JSON OBJ DATA
}

public int getRatingValue() {return ratingValue; }

public String getRater() {return rater; }

public String getReview() {return review; }

}
