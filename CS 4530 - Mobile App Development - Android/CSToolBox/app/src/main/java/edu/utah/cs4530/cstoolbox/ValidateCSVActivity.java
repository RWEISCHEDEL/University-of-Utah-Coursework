package edu.utah.cs4530.cstoolbox;

import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaCodec;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by Robert on 12/3/2016.
 */

public class ValidateCSVActivity extends AppCompatActivity implements Button.OnClickListener {

    EditText input;
    EditText output;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);

        TextView mainView = new TextView(this);
        mainView.append("CSV Validator");
        mainView.setTextSize(20.0f);

        LinearLayout inputLayout = new LinearLayout(this);
        inputLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView inputView = new TextView(this);
        inputView.append("Input: ");
        inputView.setTextSize(20.0f);
        input = new EditText(this);
        //inputLayout.addView(inputView);
        inputLayout.addView(input);

        LinearLayout outputLayout = new LinearLayout(this);
        outputLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView outputView = new TextView(this);
        outputView.append("Output: ");
        outputView.setTextSize(20.0f);
        output = new EditText(this);
        //outputLayout.addView(outputView);
        outputLayout.addView(output);

        LinearLayout buttonLayout = new LinearLayout(this);
        buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
        Button clearButton = new Button(this);
        clearButton.setOnClickListener(this);
        clearButton.setTag("clear");
        clearButton.setText("CLEAR");
        Button convertButton = new Button(this);
        convertButton.setOnClickListener(this);
        convertButton.setTag("validate");
        convertButton.setText("VALIDATE");
        Button helpButton = new Button(this);
        helpButton.setOnClickListener(this);
        helpButton.setTag("help");
        helpButton.setText("HELP");
        buttonLayout.addView(convertButton);
        buttonLayout.addView(clearButton);
        buttonLayout.addView(helpButton);


        rootLayout.addView(mainView);
        rootLayout.addView(inputView);
        rootLayout.addView(inputLayout);
        rootLayout.addView(outputView);
        rootLayout.addView(outputLayout);
        rootLayout.addView(buttonLayout);

        setContentView(rootLayout);
    }

    @Override
    public void onClick(View view) {
        if(view.getTag().toString().equals("validate")){
            if(validateCSV()){
                output.setText("Valid CSV");
            }
            else{
                output.setText("Not Valid CSV");
            }

        }
        else if(view.getTag().toString().equals("clear")){
            input.setText("");
            output.setText("");
        }
        else if(view.getTag().toString().equals("help")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Enter the CSV string you wish to validate beneath the Input label, and then " +
                    "press the VALIDATE button. NOTE: The CSV must be entered with new line characters after each line, " +
                    "see the following example.\n" + "Example:\n firstname,lastname,job\\n \n" +
                    " Luke,Skywalker,Jedi\\n \n Leia,Organa,Princess\\n \n The CLEAR button will clear all text from both " +
                    "the input and output fields.");
            builder.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            builder.setCancelable(true);
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    public boolean validateCSV(){
        boolean valid = true;

        if(!input.getText().toString().contains("\\n")){
            valid = false;
            return valid;
        }

        String[] lines = input.getText().toString().split("\n");

        for (String l : lines) {
            Log.i("Validate CSV lines: ", l);
            if(!l.contains("\\n")){
                Log.i("Failed CSV string: ", l);
                valid = false;
                return valid;
            }
        }

        for(int i = 0; i < lines.length; i++){
            lines[i] = lines[i].substring(0,lines[i].length()-2);
        }

        String[] header = lines[0].split(",");
        int headerSize = header.length;

        for(String s : lines){
            Log.i("onclick", s);
            String[] inputs = s.split(",");
            Log.i("onclick", inputs[0]);
            Log.i("onclick", inputs[1]);
            Log.i("onclick", inputs[2]);
            if(inputs.length != headerSize){
                valid = false;
            }
        }

        return valid;
    }
}
