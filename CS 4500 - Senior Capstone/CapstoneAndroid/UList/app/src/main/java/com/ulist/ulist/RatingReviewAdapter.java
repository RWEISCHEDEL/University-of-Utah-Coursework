package com.ulist.ulist;

import android.content.Context;
import android.media.Rating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Robert on 3/1/2018.
 * This class allows the Ratings to be listed in a list view format that is more pleasing to the eye. It allows the listview to show the review as a combination
 * of text and rating bar.
 */

public class RatingReviewAdapter extends ArrayAdapter<RatingReviews> {

    private ArrayList<RatingReviews> rr;
    private Context bContext;

    public RatingReviewAdapter(Context context, ArrayList<RatingReviews> rr) {
        super(context, R.layout.activity_ratingreview_listview, rr);
        bContext = context;
        this.rr = rr;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(bContext).inflate(R.layout.activity_search_listview,parent,false);

        RatingReviews currentRR = rr.get(position);

        TextView rater = (TextView) listItem.findViewById(R.id.reviewrateAuthor);
        rater.setText(currentRR.getRater());

        RatingBar rBar = (RatingBar) listItem.findViewById(R.id.reviewrateRatingBar);
        rBar.setRating(currentRR.getRatingValue());

        TextView review = (TextView) listItem.findViewById(R.id.reviewrateComment);
        review.setText(currentRR.getReview());

        return listItem;
    }
}
