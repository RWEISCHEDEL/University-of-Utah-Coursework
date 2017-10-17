package edu.utah.cs4530.cstoolbox;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Robert on 12/11/2016.
 */

public class URLConverterActivity extends AppCompatActivity implements Button.OnClickListener{

    EditText inputField;
    EditText outputField;

    Button optionButton;

    RadioButton utf8;
    RadioButton utf16;
    RadioButton ascii;

    int optionSelected;
    boolean decode;

    URLConverter converter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        converter = new URLConverter();

        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);

        TextView mainView = new TextView(this);
        mainView.append("URL Encoder/Decoder");
        mainView.setTextSize(20.0f);

        LinearLayout inputLayout = new LinearLayout(this);
        inputLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView inputView = new TextView(this);
        inputView.append("Input: ");
        inputView.setTextSize(20.0f);
        inputField = new EditText(this);
        //inputLayout.addView(inputView);
        inputLayout.addView(inputField);

        LinearLayout outputLayout = new LinearLayout(this);
        outputLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView outputView = new TextView(this);
        outputView.append("Output: ");
        outputView.setTextSize(20.0f);
        outputField = new EditText(this);
        //outputLayout.addView(outputView);
        outputLayout.addView(outputField);

        TextView encodingView = new TextView(this);
        encodingView.append("Select Encoding Type:");
        encodingView.setTextSize(20.0f);

        LinearLayout radioButtonLayout = new LinearLayout(this);
        radioButtonLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView utf8View = new TextView(this);
        utf8View.append("UTF-8: ");
        utf8View.setTextSize(20.0f);
        utf8 = new RadioButton(this);
        utf8.setOnClickListener(this);
        utf8.setChecked(true);
        optionSelected = 1;
        utf8.setTag("utf-8");
        radioButtonLayout.addView(utf8View);
        radioButtonLayout.addView(utf8);

        TextView utf16View = new TextView(this);
        utf16View.append("UTF-16: ");
        utf16View.setTextSize(20.0f);
        utf16 = new RadioButton(this);
        utf16.setOnClickListener(this);
        utf16.setTag("utf-16");
        radioButtonLayout.addView(utf16View);
        radioButtonLayout.addView(utf16);

        TextView asciiView = new TextView(this);
        asciiView.append("ASCII: ");
        asciiView.setTextSize(20.0f);
        ascii = new RadioButton(this);
        ascii.setOnClickListener(this);
        ascii.setTag("ascii");
        radioButtonLayout.addView(asciiView);
        radioButtonLayout.addView(ascii);

        LinearLayout buttonLayout = new LinearLayout(this);
        buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
        Button clearButton = new Button(this);
        clearButton.setOnClickListener(this);
        clearButton.setTag("clear");
        clearButton.setText("CLEAR");
        Button convertButton = new Button(this);
        convertButton.setOnClickListener(this);
        convertButton.setTag("convert");
        convertButton.setText("CONVERT");
        optionButton = new Button(this);
        optionButton.setOnClickListener(this);
        optionButton.setTag("option");
        optionButton.setText("DECODE");
        decode = true;
        Button helpButton = new Button(this);
        helpButton.setOnClickListener(this);
        helpButton.setTag("help");
        helpButton.setText("HELP");
        buttonLayout.addView(convertButton);
        buttonLayout.addView(clearButton);
        buttonLayout.addView(optionButton);
        buttonLayout.addView(helpButton);

        rootLayout.addView(mainView);
        rootLayout.addView(inputView);
        rootLayout.addView(inputLayout);
        rootLayout.addView(outputView);
        rootLayout.addView(outputLayout);
        rootLayout.addView(encodingView);
        rootLayout.addView(radioButtonLayout);
        rootLayout.addView(buttonLayout);


        setContentView(rootLayout);
    }

    @Override
    public void onClick(View view) {
        if(view.getTag().toString().equals("convert")){
            Log.i("OnClick", "Convert");
            String answer = "";
            if(decode){
                answer = converter.decodeURL(inputField.getText().toString(), optionSelected);
            }
            else{
                answer = converter.encodeURL(inputField.getText().toString(), optionSelected);
            }
            if(answer.equals("ERROR")){
                outputField.setText("Invalid input.");
            }
            else{
                outputField.setText(answer);
            }

        }
        else if(view.getTag().toString().equals("clear")){
            Log.i("OnClick", "Clear");
            inputField.setText("");
            outputField.setText("");
        }
        else if(view.getTag().toString().equals("utf-8")){
            Log.i("OnClick", "8");
            utf8.setChecked(true);
            utf16.setChecked(false);
            ascii.setChecked(false);
            optionSelected = 1;
        }
        else if(view.getTag().toString().equals("utf-16")){
            Log.i("OnClick", "16");
            utf8.setChecked(false);
            utf16.setChecked(true);
            ascii.setChecked(false);
            optionSelected = 2;
        }
        else if(view.getTag().toString().equals("ascii")){
            Log.i("OnClick", "ascii");
            utf8.setChecked(false);
            utf16.setChecked(false);
            ascii.setChecked(true);
            optionSelected = 3;
        }
        else if(view.getTag().toString().equals("help")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Enter the url you wish to convert beneath the input label. " +
                    "And then press the CONVERT button. If you wish to switch between encoding and decoding, " +
                    "press the DECODE/ENCODE button. What is currently presented on the button, is what action that will take place." +
                    "The CLEAR button will clear all text from both the input and output fields.");
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
        else{
            if(view.getTag().toString().equals("option")){
                if(decode){
                    Log.i("OnClick", "switch to encoding");
                    decode = false;
                    optionButton.setText("ENCODE");
                }
                else{
                    Log.i("OnClick", "switch to decoding");
                    decode = true;
                    optionButton.setText("DECODE");
                }
            }
        }
    }
}
