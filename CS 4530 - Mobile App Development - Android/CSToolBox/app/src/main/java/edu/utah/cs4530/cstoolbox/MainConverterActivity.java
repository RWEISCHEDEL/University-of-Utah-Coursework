package edu.utah.cs4530.cstoolbox;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Robert on 11/30/2016.
 */

public class MainConverterActivity extends AppCompatActivity implements Button.OnClickListener{

    EditText decimalInput;
    EditText binaryInput;
    EditText hexInput;
    EditText octInput;
    EditText asciiInput;
    EditText unicodeInput;

    MainConverter converter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        converter = new MainConverter();

        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);

        TextView mainView = new TextView(this);
        mainView.append("Decimal, Binary, Hexadecimal, Octal, ASCII Converter");
        mainView.setTextSize(20.0f);

        // Layout for Decimal Input
        LinearLayout decimalLayout = new LinearLayout(this);
        decimalLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView decimalView = new TextView(this);
        decimalView.append("Decimal: ");
        decimalView.setTextSize(20.0f);
        decimalInput = new EditText(this);
        //decimalLayout.addView(decimalView);
        decimalLayout.addView(decimalInput);


        // Layout for Binary Input
        LinearLayout binaryLayout = new LinearLayout(this);
        binaryLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView binaryView = new TextView(this);
        binaryView.append("Binary: ");
        binaryView.setTextSize(20.0f);
        binaryInput = new EditText(this);
        //binaryLayout.addView(binaryView);
        binaryLayout.addView(binaryInput);


        // Layout for Hexidecimal Input
        LinearLayout hexLayout = new LinearLayout(this);
        hexLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView hexView = new TextView(this);
        hexView.append("Hexadecimal: ");
        hexView.setTextSize(20.0f);
        hexInput = new EditText(this);
        //hexLayout.addView(hexView);
        hexLayout.addView(hexInput);


        // Layout for Octal Input
        LinearLayout octLayout = new LinearLayout(this);
        octLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView octView = new TextView(this);
        octView.append("Octal: ");
        octView.setTextSize(20.0f);
        octInput = new EditText(this);
        //octLayout.addView(octView);
        octLayout.addView(octInput);


        // Layout for ASCII Input
        LinearLayout asciiLayout = new LinearLayout(this);
        asciiLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView asciiView = new TextView(this);
        asciiView.append("ASCII: ");
        asciiView.setTextSize(20.0f);
        asciiInput = new EditText(this);
        //asciiLayout.addView(asciiView);
        asciiLayout.addView(asciiInput);


        // Layout for Unicode Input
        LinearLayout unicodeLayout = new LinearLayout(this);
        unicodeLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView unicodeView = new TextView(this);
        unicodeView.append("UNICODE: ");
        unicodeView.setTextSize(20.0f);
        unicodeInput = new EditText(this);
        //unicodeLayout.addView(unicodeView);
        unicodeLayout.addView(unicodeInput);


        // Button Layout
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
        Button helpButton = new Button(this);
        helpButton.setOnClickListener(this);
        helpButton.setTag("help");
        helpButton.setText("HELP");
        buttonLayout.addView(convertButton);
        buttonLayout.addView(clearButton);
        buttonLayout.addView(helpButton);


        // Add layouts to main layout
        rootLayout.addView(mainView);
        rootLayout.addView(decimalView);
        rootLayout.addView(decimalLayout);
        rootLayout.addView(binaryView);
        rootLayout.addView(binaryLayout);
        rootLayout.addView(hexView);
        rootLayout.addView(hexLayout);
        rootLayout.addView(octView);
        rootLayout.addView(octLayout);
        rootLayout.addView(asciiView);
        rootLayout.addView(asciiLayout);
        //rootLayout.addView(unicodeView);
        //rootLayout.addView(unicodeLayout);
        rootLayout.addView(buttonLayout);

        setContentView(rootLayout);
    }

    @Override
    public void onClick(View view) {
        if(view.getTag().equals("clear")){
            decimalInput.setText("");
            binaryInput.setText("");
            hexInput.setText("");
            octInput.setText("");
            asciiInput.setText("");
            unicodeInput.setText("");
        }
        else if(view.getTag().toString().equals("help")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("To perform a conversion enter the desired value into its appropriate input field. And then " +
                    "press CONVERT. The rest of the fields will appropriately filled with the correct converted value. The " +
                    "CLEAR button clears all text from all input fields.");
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
            if(!decimalInput.getText().toString().equals("")){
                ArrayList<String> answers = converter.convertDecimalInput(decimalInput.getText().toString());
                if(answers.get(0).equals("ERROR")){
                    decimalInput.setText("Incorrect Input in the Decimal Field.");
                    return;
                }
                decimalInput.setText(answers.get(0));
                binaryInput.setText(answers.get(1));
                hexInput.setText(answers.get(2));
                octInput.setText(answers.get(3));
                asciiInput.setText(answers.get(4));
            }
            else if(!binaryInput.getText().toString().equals("")){
                ArrayList<String> answers = converter.convertBinaryInput(binaryInput.getText().toString());
                if(answers.get(0).equals("ERROR")){
                    binaryInput.setText("Incorrect Input in the Binary Field.");
                    return;
                }
                decimalInput.setText(answers.get(0));
                binaryInput.setText(answers.get(1));
                hexInput.setText(answers.get(2));
                octInput.setText(answers.get(3));
                asciiInput.setText(answers.get(4));
            }
            else if(!hexInput.getText().toString().equals("")){
                ArrayList<String> answers = converter.convertHexInput(hexInput.getText().toString());
                if(answers.get(0).equals("ERROR")){
                    hexInput.setText("Incorrect Input in the Hexadecimal Field.");
                    return;
                }
                decimalInput.setText(answers.get(0));
                binaryInput.setText(answers.get(1));
                hexInput.setText(answers.get(2));
                octInput.setText(answers.get(3));
                asciiInput.setText(answers.get(4));
            }
            else if(!octInput.getText().toString().equals("")){
                ArrayList<String> answers = converter.convertOctalInput(octInput.getText().toString());
                if(answers.get(0).equals("ERROR")){
                    octInput.setText("Incorrect Input in the Octal Field.");
                    return;
                }
                decimalInput.setText(answers.get(0));
                binaryInput.setText(answers.get(1));
                hexInput.setText(answers.get(2));
                octInput.setText(answers.get(3));
                asciiInput.setText(answers.get(4));
            }
            else if(!asciiInput.getText().toString().equals("")){
                ArrayList<String> answers = converter.convertAsciiInput(asciiInput.getText().toString());
                if(answers.get(0).equals("ERROR")){
                    asciiInput.setText("Incorrect Input in the ASCII Field.");
                    return;
                }
                decimalInput.setText(answers.get(0));
                binaryInput.setText(answers.get(1));
                hexInput.setText(answers.get(2));
                octInput.setText(answers.get(3));
                asciiInput.setText(answers.get(4));
            }
            /*else if(!unicodeInput.getText().toString().equals("")){
                int decimalNumber = Integer.parseInt(unicodeInput.getText().toString());
                String symbol =  "\\ue" + decimalNumber;
                unicodeInput.setText("&#xe601");
            }*/
        }

    }
}
