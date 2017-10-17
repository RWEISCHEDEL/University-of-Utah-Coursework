package edu.utah.cs4530.cstoolbox;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;
import org.json.XML;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Robert on 12/11/2016.
 */

public class XMLAndJSONConverter extends AppCompatActivity implements Button.OnClickListener{

    EditText input;
    EditText output;

    RadioButton json;
    RadioButton xml;

    int optionSelected;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);

        TextView mainView = new TextView(this);
        mainView.append("XML And JSON Converter");
        mainView.setTextSize(20.0f);

        LinearLayout inputLayout = new LinearLayout(this);
        inputLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView inputView = new TextView(this);
        inputView.append("Input: ");
        inputView.setTextSize(20.0f);
        input = new EditText(this);
        //inputLayout.addView(inputView);
        inputLayout.addView(input);

        TextView radioInputView = new TextView(this);
        radioInputView.append("Choose Input Type:");
        radioInputView.setTextSize(20.0f);

        LinearLayout radioButtonLayout = new LinearLayout(this);
        radioButtonLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView jsonView = new TextView(this);
        jsonView.append("JSON: ");
        jsonView.setTextSize(20.0f);
        json = new RadioButton(this);
        json.setOnClickListener(this);
        json.setChecked(true);
        optionSelected = 1;
        json.setTag("json");
        radioButtonLayout.addView(jsonView);
        radioButtonLayout.addView(json);

        TextView xmlView = new TextView(this);
        xmlView.append("XML: ");
        xmlView.setTextSize(20.0f);
        xml = new RadioButton(this);
        xml.setOnClickListener(this);
        xml.setTag("xml");
        radioButtonLayout.addView(xmlView);
        radioButtonLayout.addView(xml);

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
        rootLayout.addView(radioInputView);
        rootLayout.addView(radioButtonLayout);
        rootLayout.addView(outputView);
        rootLayout.addView(outputLayout);
        rootLayout.addView(buttonLayout);

        setContentView(rootLayout);
    }

    @Override
    public void onClick(View view) {
        if(view.getTag().toString().equals("convert")){
            Log.i("OnClick", "Convert");
            String answer = "";
            if(optionSelected == 1){
                boolean valid = false;
                try {
                    final JsonParser parser = new ObjectMapper().getJsonFactory().createJsonParser(input.getText().toString());
                    while (parser.nextToken() != null) {
                    }
                    valid = true;
                } catch (JsonParseException jpe) {
                    output.setText("Inputed JSON is invalid");
                    jpe.printStackTrace();
                } catch (IOException ioe) {
                    output.setText("Inputed JSON is invalid");
                    ioe.printStackTrace();
                }

                if(true){
                    try {
                        JSONObject jsonObject = new JSONObject(input.getText().toString());
                        answer = XML.toString(jsonObject);
                        output.setText(answer);
                    } catch (JSONException e) {
                        output.setText("Inputed JSON is invalid");
                        e.printStackTrace();
                    }
                }
                else{
                    output.setText("Inputed JSON is invalid");
                }


            }
            else{

                boolean valid = true;
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder;
                try
                {
                    builder = factory.newDocumentBuilder();
                    Document document = builder.parse( new InputSource( new StringReader( input.getText().toString() ) ) );
                } catch (Exception e) {
                    e.printStackTrace();
                    valid = false;
                }

                if(valid){
                    try {
                        Log.i("onclick", "made it here 1");
                        Log.i("onclick", input.getText().toString());
                        JSONObject obj = XML.toJSONObject(input.getText().toString());
                        Log.i("onclick", "made it here 2 ");
                        answer = obj.toString(4);
                        output.setText(answer);
                    } catch (JSONException e) {
                        output.setText("Inputed XML is invalid");
                        e.printStackTrace();
                    }
                }
                else{
                    output.setText("Inputed XML is invalid");
                }

            }
        }
        else if(view.getTag().toString().equals("help")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Input the JSON or XML into the text field below the Input label and then press the VALIDATE button. " +
                    "Space and tabs will not effect " +
                    "the validity of the inputed JSON or XML. To switch between which input you which to convert from " +
                    "simply select the correct radio button. The CLEAR button will clear all text from both the input and output fields.");
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
            Log.i("OnClick", "Clear");
            input.setText("");
            output.setText("");
        }
        else if(view.getTag().toString().equals("json")){
            Log.i("OnClick", "8");
            json.setChecked(true);
            xml.setChecked(false);
            optionSelected = 1;
        }
        else if(view.getTag().toString().equals("xml")){
            Log.i("OnClick", "16");
            json.setChecked(false);
            xml.setChecked(true);
            optionSelected = 2;
        }
    }
}
