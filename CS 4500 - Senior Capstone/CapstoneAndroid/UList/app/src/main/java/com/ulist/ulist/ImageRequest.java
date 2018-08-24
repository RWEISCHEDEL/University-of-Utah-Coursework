package com.ulist.ulist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Matt on 12/7/2017.
 * This class requests the image and decodes it for various Activities such as Search, and the Buyer/Seller Profiles.
 */

public class ImageRequest extends AsyncTask<String, Void, Bitmap> {
    protected Bitmap doInBackground(String... params) {
        try {
            if (params[0].split(" ").length > 1) {
                params[0] = params[0].split(" ")[0];
            }
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL("http://52.53.155.198:3001/images/" + params[0]).getContent());

            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}