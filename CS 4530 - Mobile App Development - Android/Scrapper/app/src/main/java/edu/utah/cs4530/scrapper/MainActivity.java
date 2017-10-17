package edu.utah.cs4530.scrapper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    URL url = new URL("http://www.nytimes.com");
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    InputStream inputStream = connection.getInputStream();
                    Scanner scanner = new Scanner(inputStream);
                    StringBuilder stringBuilder = new StringBuilder();



                    while(scanner.hasNext()){
                        stringBuilder.append(scanner.nextLine());
                    }

                    String document = stringBuilder.toString();
                    Log.i("Document", document);

                    while(true) {
                        int imageTagStart = document.indexOf("<img src=\"");
                        if (imageTagStart < 0) {
                            break;
                        }

                        int imageURLStart = imageTagStart + "<img src=\"".length();
                        int imageURLEnd = document.indexOf("\"", imageTagStart);
                        if(imageURLEnd < 0){
                            break;
                        }
                        String imageURLString = document.substring(imageURLStart, imageURLEnd);
                        Log.i("Image URL", imageURLString);
                        document = document.substring(imageURLEnd);

                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();



    }
}
