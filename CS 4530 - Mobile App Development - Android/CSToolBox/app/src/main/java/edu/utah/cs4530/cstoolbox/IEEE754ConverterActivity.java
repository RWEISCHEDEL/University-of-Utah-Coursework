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
import android.widget.TextView;

/**
 * Created by Robert on 12/2/2016.
 */

public class IEEE754ConverterActivity extends AppCompatActivity implements Button.OnClickListener{

    IEEE754Converter converter;
    EditText signInput;
    EditText expInput;
    EditText manInput;
    EditText answerInput;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        converter = new IEEE754Converter();

        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);

        TextView mainView = new TextView(this);
        mainView.append("IEEE 754 Converter");
        mainView.setTextSize(20.0f);

        LinearLayout signLayout = new LinearLayout(this);
        signLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView signView = new TextView(this);
        signView.append("Sign: ");
        signView.setTextSize(20.0f);
        signInput = new EditText(this);
        //signLayout.addView(signView);
        signLayout.addView(signInput);

        LinearLayout expLayout = new LinearLayout(this);
        expLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView expView = new TextView(this);
        expView.append("Exponent: ");
        expView.setTextSize(20.0f);
        expInput = new EditText(this);
        //expLayout.addView(expView);
        expLayout.addView(expInput);

        LinearLayout manLayout = new LinearLayout(this);
        manLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView manView = new TextView(this);
        manView.append("Mantissa: ");
        manView.setTextSize(20.0f);
        manInput = new EditText(this);
        //manLayout.addView(manView);
        manLayout.addView(manInput);

        LinearLayout answerLayout = new LinearLayout(this);
        answerLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView answerView = new TextView(this);
        answerView.append("Answer: ");
        answerView.setTextSize(20.0f);
        answerInput = new EditText(this);
        //answerLayout.addView(answerView);
        answerLayout.addView(answerInput);

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

        rootLayout.addView(mainView);
        rootLayout.addView(signView);
        rootLayout.addView(signLayout);
        rootLayout.addView(expView);
        rootLayout.addView(expLayout);
        rootLayout.addView(manView);
        rootLayout.addView(manLayout);
        rootLayout.addView(answerView);
        rootLayout.addView(answerLayout);
        rootLayout.addView(buttonLayout);

        setContentView(rootLayout);
    }

    @Override
    public void onClick(View view) {
        if(view.getTag().equals("convert")){
            Log.i("Clicked Button", "Convert");
            Log.i("Clicked Button", signInput.getText().toString());
            Log.i("Clicked Button", expInput.getText().toString());
            Log.i("Clicked Button", manInput.getText().toString());

            if(expInput.getText().toString().equals("11111111")){
                if(manInput.getText().toString().contains("1")){
                    answerInput.setText("NaN");
                }
                else{
                    answerInput.setText("Infinity");
                }
            }
            else{

                if(signInput.getText().toString().equals("") || expInput.getText().toString().equals("") || manInput.getText().toString().equals("")){
                    answerInput.setText("One of the input fields is empty.");
                    return;
                }

                if(signInput.getText().toString().length() < 1 || signInput.getText().toString().length() > 1){
                    answerInput.setText("Only one integer can be placed in the sign place.");
                    return;
                }

                if(!signInput.getText().toString().equals("0") && !signInput.getText().toString().equals("1")){
                    answerInput.setText("The Sign Bit can only contain a 0 or 1.");
                    return;
                }

                if(expInput.getText().toString().length() < 8 || expInput.getText().toString().length() > 8){
                    answerInput.setText("Only eight integers can be placed in the exponent place.");
                    return;
                }

                if(manInput.getText().toString().length() < 23 || manInput.getText().toString().length() > 23){
                    answerInput.setText("Only twenty-three integers can be placed in the mantissa place.");
                    return;
                }

                try{
                    int exponentValue = Integer.parseInt(expInput.getText().toString(), 2);
                }
                catch(Exception e){
                    answerInput.setText("There is a invalid character in the exponent field. Only 1's or 0's are allowed");
                    return;
                }

                try{
                    int mantissaValue = Integer.parseInt(manInput.getText().toString(), 2);
                }
                catch(Exception e){
                    answerInput.setText("There is a invalid character in the manitssa field. Only 1's or 0's are allowed");
                    return;
                }

                double answer = converter.convertFromIEEE(signInput.getText().toString(), expInput.getText().toString(), manInput.getText().toString());

                answerInput.setText(answer + "");
            }

        }
        else if(view.getTag().toString().equals("help")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Enter the desired 32-Bit value broken up into the correct parts. After the value is entered, " +
                    "click the CONVERT button to perform the calculation and display the answer. The CLEAR button will clear out " +
                    "all input and output fields.");
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
            Log.i("Clicked Button", "Clear");
            signInput.setText("");
            expInput.setText("");
            manInput.setText("");
            answerInput.setText("");
        }
    }
}
