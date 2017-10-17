package edu.utah.cs4530.cstoolbox;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by Robert on 12/2/2016.
 */

public class Base64ConverterActivity extends AppCompatActivity implements Button.OnClickListener{

    EditText inputField;
    EditText outputField;

    Button optionButton;

    RadioButton utf8;
    RadioButton utf16;
    RadioButton ascii;

    int optionSelected;
    boolean decode;

    Base64Converter converter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        converter = new Base64Converter();

        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);

        TextView mainView = new TextView(this);
        mainView.append("Base 64 Encoder/Decoder");
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
        buttonLayout.addView(convertButton);
        buttonLayout.addView(clearButton);
        buttonLayout.addView(optionButton);

        rootLayout.addView(mainView);
        rootLayout.addView(inputView);
        rootLayout.addView(inputLayout);
        rootLayout.addView(outputView);
        rootLayout.addView(outputLayout);
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
                answer = converter.decodeFromBase64(inputField.getText().toString(), optionSelected);
            }
            else{
                answer = converter.encodeToBase64(inputField.getText().toString(), optionSelected);
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
