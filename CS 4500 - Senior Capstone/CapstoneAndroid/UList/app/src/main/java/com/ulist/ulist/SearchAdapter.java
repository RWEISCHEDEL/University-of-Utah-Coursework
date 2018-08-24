package com.ulist.ulist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Matt on 2/8/2018.
 * This class creates an adapter for the search list view in order to show more information about the project, like an image, cost, etc.
 */
public class SearchAdapter extends ArrayAdapter<Book> {
    private ArrayList<Book> books;
    private Context bContext;

    public SearchAdapter(Context context, ArrayList<Book> books) {
        super(context, R.layout.activity_search_listview, books);
        bContext = context;
        this.books = books;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(bContext).inflate(R.layout.activity_search_listview,parent,false);

        Book currentBook = books.get(position);

        ImageView image = (ImageView)listItem.findViewById(R.id.imageView_poster);
        image.setImageResource(R.drawable.book_small);
        if (currentBook.getBitmap() != null) {
            image.setImageBitmap(currentBook.getBitmap());
        }

        TextView title = (TextView) listItem.findViewById(R.id.textView_reviewer);
        title.setText(currentBook.getTitle());

        TextView author = (TextView) listItem.findViewById(R.id.textView_rating);
        author.setText("Written By: " + currentBook.getAuthor());

        TextView seller = (TextView) listItem.findViewById(R.id.textView_seller);
        seller.setText("Sold By: " + currentBook.getUsername());

        return listItem;
    }
}
