package edu.utah.cs4530.cstoolbox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListAdapter adapter;
    private ArrayList<String> optionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        optionsList = new ArrayList<String>();
        optionsList.add("Binary, Hexadecimal, Decimal, Octal, and ASCII Converter");
        optionsList.add("IEEE 754 Converter");
        optionsList.add("Base 64 Encoder/Decoder");
        optionsList.add("Bit Shift Converter");
        optionsList.add("URL Encoder/Decoder");
        optionsList.add("Valid CSV Checker");
        optionsList.add("Valid JSON Checker");
        optionsList.add("Valid XML Checker");
        optionsList.add("CSV to JSON Converter");
        optionsList.add("CSV to XML Converter");
        optionsList.add("Convert Between XML and JSON");



        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);

        ListView options = new ListView(this);

        options.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                chooseActivity(i);
            }
        });

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, optionsList);

        options.setAdapter(adapter);

        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int height = metrics.heightPixels;

        rootLayout.addView(options, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        setContentView(rootLayout);
    }

    public void chooseActivity(int position){
        if(position == 0) {
            Intent intent = new Intent(this, MainConverterActivity.class);
            startActivity(intent);
        }
        else if(position == 1){
            Intent intent = new Intent(this, IEEE754ConverterActivity.class);
            startActivity(intent);
        }
        else if(position == 2){
            Intent intent = new Intent(this, Base64ConverterActivity.class);
            startActivity(intent);
        }
        else if(position == 3){
            Intent intent = new Intent(this, BitShiftingActivity.class);
            startActivity(intent);
        }
        else if(position == 4){
            Intent intent = new Intent(this, URLConverterActivity.class);
            startActivity(intent);
        }
        else if(position == 5){
            Intent intent = new Intent(this, ValidateCSVActivity.class);
            startActivity(intent);
        }
        else if(position == 6){
            Intent intent = new Intent(this, ValidateJSONActivity.class);
            startActivity(intent);
        }
        else if(position == 7){
            Intent intent = new Intent(this, ValidateXMLActivity.class);
            startActivity(intent);
        }
        else if(position == 8){
            Intent intent = new Intent(this, CSVToJSONConverterActivity.class);
            startActivity(intent);
        }
        else if(position == 9){
            Intent intent = new Intent(this, CSVToXMLConverterActivity.class);
            startActivity(intent);
        }
        else if(position == 10){
            Intent intent = new Intent(this, XMLAndJSONConverter.class);
            startActivity(intent);
        }


    }
}
