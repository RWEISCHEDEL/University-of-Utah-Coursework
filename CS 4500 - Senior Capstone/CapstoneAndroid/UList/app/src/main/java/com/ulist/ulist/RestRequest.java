package com.ulist.ulist;

import android.icu.util.Output;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matt on 12/7/2017.
 * This class is the heart of the REST portion of the Android app, it services the requests we need to make.
 */

public class RestRequest extends AsyncTask<String, Void, StringBuffer> {
    protected StringBuffer doInBackground(String... params) {
        String endOf = (String) params[0];
        String request = (String) params[1];
        HashMap<String, String> toSend = new HashMap<>();

        HttpURLConnection co = null;
        InputStream is = null;
        OutputStream os = null;

        try {
            URL url = new URL("http://52.53.155.198:3001/" + endOf);
            co = (HttpURLConnection) url.openConnection();
            co.setConnectTimeout(5000);
            co.setRequestMethod(request);

            if (request.compareTo("POST") == 0)
                co.setDoOutput(true);
            co.setDoInput(true);

            if (request.compareTo("POST") == 0) {
                for (int i = 2; i < params.length; i+=2) {
                    toSend.put(params[i], params[i+1]);
                }

                os = co.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(toSend));

                writer.flush();
                writer.close();
                os.close();
            }

            co.connect();

            int responseCode = co.getResponseCode();
            if (responseCode >= 400 && responseCode <= 499) {
                is = co.getErrorStream();
            }
            else {
                is = co.getInputStream();
            }

            StringBuffer sb = new StringBuffer();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while((line = br.readLine()) != null) {
                sb.append(line);
            }
            co.disconnect();

            return sb;
        } catch (MalformedURLException e) {
            co.disconnect();
            e.printStackTrace();
            StringBuffer sb = new StringBuffer();
            sb.append("ERROR");
        } catch (IOException e) {
            co.disconnect();
            e.printStackTrace();
            StringBuffer sb = new StringBuffer();
            sb.append("ERROR");
        }
        return null;
    }

    @NonNull
    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}
