package com.ulist.ulist;

import android.content.Context;
import android.media.Rating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Matt on 4/18/2018.
 * This class allows the Ratings to be listed in a list view format that is more pleasing to the eye. It allows the listview to show the review as a combination
 * of text and rating bar.
 */

public class ReviewsAdapter extends ArrayAdapter<Review> {

    private ArrayList<Review> reviews;
    private Context bContext;

    public ReviewsAdapter(Context context, ArrayList<Review> reviews) {
        super(context, R.layout.activity_ratingreview_listview, reviews);
        bContext = context;
        this.reviews = reviews;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(bContext).inflate(R.layout.activity_ratingreview_listview,parent,false);

        Review currentReview = reviews.get(position);

        TextView author = (TextView) listItem.findViewById(R.id.reviewrateAuthor);
        author.setText("Reviewed By: " + currentReview.getReviewername());

        RatingBar rating = (RatingBar) listItem.findViewById(R.id.reviewrateRatingBar);
        rating.setRating(currentReview.getRating());

        TextView comment = (TextView) listItem.findViewById(R.id.reviewrateComment);
        comment.setText(currentReview.getComment());

        return listItem;
    }
}
