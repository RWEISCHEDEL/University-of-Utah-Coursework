package edu.utah.cs4530.cstoolbox;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Robert on 12/11/2016.
 */

public class BitShiftingActivity extends AppCompatActivity implements Button.OnClickListener{

    EditText decimalOutput;
    EditText binaryOutput;
    EditText hexOutput;
    EditText octOutput;
    EditText inputField;
    EditText timesField;

    RadioButton left;
    RadioButton right;
    RadioButton decimal;
    RadioButton binary;
    RadioButton hex;
    RadioButton octal;

    int shiftOptionSelected, inputOptionSelected;

    BitShifter converter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);

        TextView mainView = new TextView(this);
        mainView.append("Bit Shift Calculator");
        mainView.setTextSize(20.0f);

        converter = new BitShifter();

        LinearLayout inputLayout = new LinearLayout(this);
        inputLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView inputView = new TextView(this);
        inputView.append("Input: ");
        inputView.setTextSize(20.0f);
        inputField = new EditText(this);
        //inputLayout.addView(inputView);
        inputLayout.addView(inputField);

        TextView inputRadioView = new TextView(this);
        inputRadioView.append("Select Input Type:");
        inputRadioView.setTextSize(20.0f);

        LinearLayout timesLayout = new LinearLayout(this);
        timesLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView timesView = new TextView(this);
        timesView.append("How many times to shift: ");
        timesView.setTextSize(20.0f);
        timesField = new EditText(this);
        //inputLayout.addView(inputView);
        timesLayout.addView(timesField);

        LinearLayout radioButtonInputLayout = new LinearLayout(this);
        radioButtonInputLayout.setOrientation(LinearLayout.HORIZONTAL);

        TextView decimalInputView = new TextView(this);
        decimalInputView.append("Decimal: ");
        decimalInputView.setTextSize(20.0f);
        decimal = new RadioButton(this);
        decimal.setOnClickListener(this);
        decimal.setChecked(true);
        inputOptionSelected = 1;
        decimal.setTag("decimal");
        radioButtonInputLayout.addView(decimalInputView);
        radioButtonInputLayout.addView(decimal);

        TextView binaryInputView = new TextView(this);
        binaryInputView.append("Binary: ");
        binaryInputView.setTextSize(20.0f);
        binary = new RadioButton(this);
        binary.setOnClickListener(this);
        binary.setTag("binary");
        radioButtonInputLayout.addView(binaryInputView);
        radioButtonInputLayout.addView(binary);

        TextView hexInputView = new TextView(this);
        hexInputView.append("Hex: ");
        hexInputView.setTextSize(20.0f);
        hex = new RadioButton(this);
        hex.setOnClickListener(this);
        hex.setTag("hex");
        radioButtonInputLayout.addView(hexInputView);
        radioButtonInputLayout.addView(hex);

        TextView octalView = new TextView(this);
        octalView.append("Octal: ");
        octalView.setTextSize(20.0f);
        octal = new RadioButton(this);
        octal.setOnClickListener(this);
        octal.setTag("octal");
        radioButtonInputLayout.addView(octalView);
        radioButtonInputLayout.addView(octal);

        // Layout for Decimal Input
        LinearLayout decimalLayout = new LinearLayout(this);
        decimalLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView decimalView = new TextView(this);
        decimalView.append("Decimal: ");
        decimalView.setTextSize(20.0f);
        decimalOutput = new EditText(this);
        //decimalLayout.addView(decimalView);
        decimalLayout.addView(decimalOutput);


        // Layout for Binary Input
        LinearLayout binaryLayout = new LinearLayout(this);
        binaryLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView binaryView = new TextView(this);
        binaryView.append("Binary: ");
        binaryView.setTextSize(20.0f);
        binaryOutput = new EditText(this);
        //binaryLayout.addView(binaryView);
        binaryLayout.addView(binaryOutput);


        // Layout for Hexidecimal Input
        LinearLayout hexLayout = new LinearLayout(this);
        hexLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView hexView = new TextView(this);
        hexView.append("Hexidecimal: ");
        hexView.setTextSize(20.0f);
        hexOutput = new EditText(this);
        //hexLayout.addView(hexView);
        hexLayout.addView(hexOutput);


        // Layout for Octal Input
        LinearLayout octLayout = new LinearLayout(this);
        octLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView octView = new TextView(this);
        octView.append("Octal: ");
        octView.setTextSize(20.0f);
        octOutput = new EditText(this);
        //octLayout.addView(octView);
        octLayout.addView(octOutput);

        TextView shiftRadioView = new TextView(this);
        shiftRadioView.append("Select Shift Direction:");
        shiftRadioView.setTextSize(20.0f);

        LinearLayout radioButtonLayout = new LinearLayout(this);
        radioButtonLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView leftView = new TextView(this);
        leftView.append("Shift Left: ");
        leftView.setTextSize(20.0f);
        left = new RadioButton(this);
        left.setOnClickListener(this);
        left.setChecked(true);
        shiftOptionSelected = 1;
        left.setTag("left");
        radioButtonLayout.addView(leftView);
        radioButtonLayout.addView(left);

        TextView rightView = new TextView(this);
        rightView.append("Shift Right: ");
        rightView.setTextSize(20.0f);
        right = new RadioButton(this);
        right.setOnClickListener(this);
        right.setTag("right");
        radioButtonLayout.addView(rightView);
        radioButtonLayout.addView(right);

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
        rootLayout.addView(inputView);
        rootLayout.addView(inputLayout);
        rootLayout.addView(radioButtonInputLayout);
        rootLayout.addView(timesView);
        rootLayout.addView(timesLayout);
        rootLayout.addView(decimalView);
        rootLayout.addView(decimalLayout);
        rootLayout.addView(binaryView);
        rootLayout.addView(binaryLayout);
        rootLayout.addView(hexView);
        rootLayout.addView(hexLayout);
        rootLayout.addView(octView);
        rootLayout.addView(octLayout);
        rootLayout.addView(radioButtonLayout);
        rootLayout.addView(buttonLayout);

        setContentView(rootLayout);
    }

    @Override
    public void onClick(View view) {

        if (view.getTag().toString().equals("convert")) {
            Log.i("onclick", "Convert Button Clicked");

            if(inputField.getText().toString().equals("")){
                decimalOutput.setText("No input value, please enter a value to perform a shift on.");
                return;
            }

            try{
                int times = Integer.parseInt(timesField.getText().toString());
                if(times <= 0 ){
                    decimalOutput.setText("Value in times to shift field must be greater than 0.");
                    return;
                }
            }
            catch (Exception e){
                decimalOutput.setText("Invalid character in times to shift field.");
                return;
            }

            if (inputOptionSelected == 1) {
                Log.i("onclick", "input option 1");
                try{
                    int times = Integer.parseInt(inputField.getText().toString());
                }
                catch (Exception e){
                    decimalOutput.setText("Invalid character in input field.");
                    return;
                }
                ArrayList<String> answers = converter.convertDecimalInput(inputField.getText().toString(), shiftOptionSelected, timesField.getText().toString());
                decimalOutput.setText(answers.get(0));
                binaryOutput.setText(answers.get(1));
                hexOutput.setText(answers.get(2));
                octOutput.setText(answers.get(3));
            } else if (inputOptionSelected == 2) {
                try{
                    int times = Integer.parseInt(inputField.getText().toString(), 2);
                }
                catch (Exception e){
                    decimalOutput.setText("Invalid character in input field.");
                    return;
                }
                ArrayList<String> answers = converter.convertBinaryInput(inputField.getText().toString(), shiftOptionSelected, timesField.getText().toString());
                decimalOutput.setText(answers.get(0));
                binaryOutput.setText(answers.get(1));
                hexOutput.setText(answers.get(2));
                octOutput.setText(answers.get(3));
            } else if (inputOptionSelected == 3) {
                try{
                    int times = Integer.parseInt(inputField.getText().toString(), 16);
                }
                catch (Exception e){
                    decimalOutput.setText("Invalid character in input field.");
                    return;
                }
                ArrayList<String> answers = converter.convertHexInput(inputField.getText().toString(), shiftOptionSelected, timesField.getText().toString());
                decimalOutput.setText(answers.get(0));
                binaryOutput.setText(answers.get(1));
                hexOutput.setText(answers.get(2));
                octOutput.setText(answers.get(3));
            } else if (inputOptionSelected == 4) {
                try{
                    int times = Integer.parseInt(inputField.getText().toString(), 8);
                }
                catch (Exception e){
                    decimalOutput.setText("Invalid character in input field.");
                    return;
                }
                ArrayList<String> answers = converter.convertOctalInput(inputField.getText().toString(), shiftOptionSelected, timesField.getText().toString());
                decimalOutput.setText(answers.get(0));
                binaryOutput.setText(answers.get(1));
                hexOutput.setText(answers.get(2));
                octOutput.setText(answers.get(3));
            }
        }
        else if(view.getTag().toString().equals("help")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Enter in the value you wish to perform a bit shift on below the Input label, and then " +
                    "select the correct input type on the radio buttons just below the input area. Next, input a value for how " +
                    "many times you wish to shift left or right into the field just below the radio buttons. Finally just before " +
                    "press the CONVERT button make sure you select which direction you which perform the shift by using the radio " +
                    "buttons above the CONVERT button. The CLEAR button will clear all the input from the activity.");
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
        else if(view.getTag().toString().equals("clear")){
            Log.i("onclick", "Clear Button Clicked");
            decimalOutput.setText("");
            binaryOutput.setText("");
            hexOutput.setText("");
            octOutput.setText("");
            timesField.setText("");
            inputField.setText("");
        }
        else if(view.getTag().toString().equals("decimal")){
            decimal.setChecked(true);
            binary.setChecked(false);
            hex.setChecked(false);
            octal.setChecked(false);
            inputOptionSelected = 1;
            Log.i("onclick", "change to decimal");
        }
        else if(view.getTag().toString().equals("binary")){
            decimal.setChecked(false);
            binary.setChecked(true);
            hex.setChecked(false);
            octal.setChecked(false);
            inputOptionSelected = 2;
            Log.i("onclick", "change to binary");
        }
        else if(view.getTag().toString().equals("hex")){
            decimal.setChecked(false);
            binary.setChecked(false);
            hex.setChecked(true);
            octal.setChecked(false);
            inputOptionSelected = 3;
            Log.i("onclick", "change to hex");
        }
        else if(view.getTag().toString().equals("octal")){
            decimal.setChecked(false);
            binary.setChecked(false);
            hex.setChecked(false);
            octal.setChecked(true);
            inputOptionSelected = 4;
            Log.i("onclick", "change to octal");
        }
        else if(view.getTag().toString().equals("left")){
            shiftOptionSelected = 1;
            left.setChecked(true);
            right.setChecked(false);
            Log.i("onclick", "change to left");
        }
        else if(view.getTag().toString().equals("right")){
            shiftOptionSelected = 2;
            left.setChecked(false);
            right.setChecked(true);
            Log.i("onclick", "change to right");
        }
    }
}
