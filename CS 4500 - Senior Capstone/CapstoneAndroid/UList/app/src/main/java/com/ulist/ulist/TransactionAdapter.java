package com.ulist.ulist;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Robert on 3/2/2018.
 * This class is an adapter for the list view for Transactions, it allows the list view to show more information about the transaction.
 */
public class TransactionAdapter extends ArrayAdapter<Transaction> {

    private ArrayList<Transaction> transactions;
    private Context bContext;

    public TransactionAdapter(Context context, ArrayList<Transaction> transactions) {
        super(context, R.layout.activity_usertransactions_listview, transactions);
        bContext = context;
        this.transactions = transactions;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(bContext).inflate(R.layout.activity_usertransactions_listview,parent,false);

        Transaction currentT = transactions.get(position);

        RestRequest rr = new RestRequest();
        StringBuffer sb;
        Book currentBook = null;

        try {
            sb = rr.execute("shop/books?book_id=" + currentT.getProductId(), "GET").get();
            currentBook = new Book(new JSONObject(sb.toString()));
            Bitmap bitmap = null;
            if (currentBook.getImagePaths() != null) {
                ImageRequest ir = new ImageRequest();
                bitmap = ir.execute(currentBook.getImagePaths()).get();
                if (bitmap != null)
                    currentBook.setBitmap(bitmap);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ImageView productImage = (ImageView) listItem.findViewById(R.id.userTransImageListView);
        productImage.setImageResource(R.drawable.book_small);
        if (currentBook != null && currentBook.getBitmap() != null) {
            productImage.setImageBitmap(currentBook.getBitmap());
        }

        TextView productName = (TextView) listItem.findViewById(R.id.userTransProductNameListView);
        productName.setText(currentT.getTitle());

        String rawDate = currentT.getDate();
        String[] dateSplit = rawDate.split("T");
        String date = dateSplit[0];
        String time = dateSplit[1].substring(0,5);

        TextView sellerName = (TextView) listItem.findViewById(R.id.userTransSellerListView);
        sellerName.setText("Seller: " + currentT.getUsernameSeller());

        return listItem;
    }

}
