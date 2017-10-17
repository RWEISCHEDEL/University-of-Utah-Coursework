package edu.utah.cs4530.networkbattleship;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;


/**
 * Created by Robert on 10/30/2016.
 */

public class Network extends AsyncTask<NetworkManager, Void, Void> {

    private Context context;
    private NetworkManager manager;

    private String getContent(HttpEntity entity) throws IllegalStateException, IOException {

        StringBuffer outputBuffer = new StringBuffer();
        InputStream inputStream = entity.getContent();

        int size = (int)entity.getContentLength();
        int n = 1;

        while (n > 0) {

            byte[] b = new byte[size];
            n = inputStream.read(b);

            if (n > 0){
                outputBuffer.append(new String(b, 0, n));
            }
        }

        return outputBuffer.toString();
    }

    private void joinGame(NetworkManager help) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        String url = "http://battleship.pixio.com/api/v2/lobby/" + help.getGameID();
        HttpPut put = new HttpPut(url);
        put.setHeader("Content-Type", "application/json");
        try {
            StringEntity entity = new StringEntity(help.getPayload());
            put.setEntity(entity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String text = null;

        try {
            HttpResponse response = httpClient.execute(put, localContext);
            HttpEntity entity = response.getEntity();
            text = getContent(entity);
        } catch (Exception e) {
            text = "BAD";
            Log.e("EXCEPTION", e.getMessage());
        }

        help.setReturnInfo(text);
    }

    private void getGames(NetworkManager help) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        String url = "http://battleship.pixio.com/api/v2/lobby";
        HttpGet httpGet = new HttpGet(url);
        String text = null;

        try {
            HttpResponse response = httpClient.execute(httpGet, localContext);
            HttpEntity entity = response.getEntity();
            text = getContent(entity);
        } catch (Exception e) {
            text = "BAD";
            Log.e("EXCEPTION", e.getMessage());
        }

        help.setReturnInfo(text);
    }

    private void makeGuess(NetworkManager help) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        String url = "http://battleship.pixio.com/api/v2/games/" + help.getGameID();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");
        String text = null;

        try {
            StringEntity ent = new StringEntity(help.getPayload());
            httpPost.setEntity(ent);
            HttpResponse response = httpClient.execute(httpPost, localContext);
            HttpEntity entity = response.getEntity();
            text = getContent(entity);
        } catch (Exception e) {
            text = "BAD";
            Log.e("EXCEPTION", e.getMessage());
        }

        help.setReturnInfo(text);
    }

    private void whoseTurnIsIt(NetworkManager help) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        String url = "http://battleship.pixio.com/api/v2/games/" + help.getGameID();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-Type", "application/json");
        String text = null;

        try {
            //StringEntity ent = new StringEntity(help.getPacked());
            HttpResponse response = httpClient.execute(httpGet, localContext);
            HttpEntity entity = response.getEntity();
            text = getContent(entity);
        } catch (Exception e) {
            text = "BAD";
            Log.e("EXCEPTION", e.getMessage());
        }

        help.setReturnInfo(text);
    }

    private void getBoard(NetworkManager help) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        Log.i("getBoard Network GUID:", help.getGameID());
        String url = "http://battleship.pixio.com/api/v2/games" + "/"  + help.getGameID() + "/boards?playerId=" + help.getPayload();
        Log.i("getBoard URL:", url);
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-Type", "application/json");

        String text = null;

        try {
            HttpResponse response = httpClient.execute(httpGet, localContext);
            Log.i("getBoard URL:", response.toString());
            HttpEntity entity = response.getEntity();
            text = getContent(entity);
        } catch (Exception e) {
            text = "BAD";
            Log.e("EXCEPTION", e.getMessage());
        }
        Log.i("getBoard - Network :", text);
        help.setReturnInfo(text);
    }

    private void newGame(NetworkManager help) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        String url =  "http://battleship.pixio.com/api/v2/lobby";
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");
        try {
            StringEntity entity = new StringEntity(help.getPayload());
            httpPost.setEntity(entity);
            Log.i("newGame - Network", httpPost.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String text = null;

        try {
            HttpResponse response = httpClient.execute(httpPost, localContext);
            HttpEntity entity = response.getEntity();
            text = getContent(entity);
        } catch (Exception e) {
            text = "BAD";
            Log.e("EXCEPTION", e.getMessage());
        }

        help.setReturnInfo(text);
    }

    private void getGameDetails(NetworkManager help) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        String url = "http://battleship.pixio.com/api/v2/lobby/" + help.getGameID();
        HttpGet httpGet = new HttpGet(url);
        String text = null;

        try {
            HttpResponse response = httpClient.execute(httpGet, localContext);
            HttpEntity entity = response.getEntity();
            text = getContent(entity);
        } catch (Exception e) {
            text = "BAD";
            Log.e("EXCEPTION", e.getMessage());
        }

        help.setReturnInfo(text);
    }

    @Override
    protected Void doInBackground(NetworkManager... params) {

        NetworkManager backgroundManager = params[0];
        this.context = backgroundManager.getContext();

        switch (backgroundManager.getRequestCode()) {
            case 0:     joinGame(backgroundManager);
                break;
            case 1:     getGames(backgroundManager);
                break;
            case 2:     makeGuess(backgroundManager);
                break;
            case 3:     whoseTurnIsIt(backgroundManager);
                break;
            case 4:     getBoard(backgroundManager);
                break;
            case 5:     newGame(backgroundManager);
                break;
            case 6:     getGameDetails(backgroundManager);
                break;
            default:    backgroundManager.setReturnInfo("BAD");
                break;
        }

        this.manager = backgroundManager;

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        DataModel.getInstance().returnFromServer(manager);
    }
}
