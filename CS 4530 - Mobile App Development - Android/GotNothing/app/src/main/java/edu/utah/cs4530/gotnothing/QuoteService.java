package edu.utah.cs4530.gotnothing;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by Robert on 10/24/2016.
 */

public class QuoteService {

    private static QuoteService _Instance = null;

    public static QuoteService getInstance(){
        if(_Instance == null){
            _Instance = new QuoteService();
            return _Instance;
        }
        else{
            return _Instance;
        }
    }

    private QuoteService(){

    }

    public interface QuoteListener{
       // void quoteRecieved(Quote quote);
    }

    //List<QuoteListener, Request> _quoteListener = null;

    public interface QuoteSummaryListener{
        void quoteSummaryRecieved(Set<String> quoteIndentifiers);
        Gson son = new Gson();
    }

    public Set<String> getQuoteSummaries(QuoteSummaryListener listener){
        new AsyncTask<URL, Integer, Set<String>>() {
            @Override
            protected Set<String> doInBackground(URL... urls) {
                return null;
            }
        }.execute();

        try {
            URL quoteListURL = new URL("http://localhost:8888/quotes");
            HttpURLConnection connection = (HttpURLConnection)quoteListURL.openConnection();
            InputStream inputStream = connection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            StringBuilder builder = new StringBuilder();
            while(scanner.hasNext()){
                builder.append(scanner.nextLine());
            }
            String responseBody = builder.toString();
            Log.i("Response", responseBody);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void getQuotes(String uniqueIdentifier, QuoteListener callback){

    }
}
