package edu.utah.cs4530.cstoolbox;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Robert on 11/30/2016.
 */

public class MainConverter {

    private HashMap<Integer, String> asciiMappings;

    public MainConverter(){

        asciiMappings = new HashMap<Integer, String>();
        asciiMappings.put(0, "NULL");
        asciiMappings.put(1, "SOH");
        asciiMappings.put(2, "STX");
        asciiMappings.put(3, "ETX");
        asciiMappings.put(4, "EOT");
        asciiMappings.put(5, "ENQ");
        asciiMappings.put(6, "ACK");
        asciiMappings.put(7, "BEL");
        asciiMappings.put(8, "BS");
        asciiMappings.put(9, "TAB");
        asciiMappings.put(10, "LF");
        asciiMappings.put(11, "VT");
        asciiMappings.put(12, "FF");
        asciiMappings.put(13, "CR");
        asciiMappings.put(14, "SO");
        asciiMappings.put(15, "SI");
        asciiMappings.put(16, "DLE");
        asciiMappings.put(17, "DC1");
        asciiMappings.put(18, "DC2");
        asciiMappings.put(19, "DC3");
        asciiMappings.put(20, "DC4");
        asciiMappings.put(21, "NAK");
        asciiMappings.put(22, "SYN");
        asciiMappings.put(23, "ETB");
        asciiMappings.put(24, "CAN");
        asciiMappings.put(25, "EM");
        asciiMappings.put(26, "SUB");
        asciiMappings.put(27, "ESC");
        asciiMappings.put(28, "FS");
        asciiMappings.put(29, "GS");
        asciiMappings.put(30, "RS");
        asciiMappings.put(31, "US");
        asciiMappings.put(32, "SPACE");
        asciiMappings.put(127, "DEL");



    }

    public ArrayList<String> convertDecimalInput(String input){

        ArrayList<String> answers = new ArrayList<String>();



        int decimalNumber = 0;

        try{
            decimalNumber = Integer.parseInt(input);
        }
        catch (Exception e){
            answers.add("ERROR");
            return answers;
        }

        answers.add(input);

        String binary = Integer.toBinaryString(decimalNumber);

        answers.add(binary);

        Log.i("MainConverter", "Binary String: " + binary);

        String hex = Integer.toHexString(decimalNumber);

        answers.add(hex);

        Log.i("MainConverter", "Hex String: " + hex);

        String oct = Integer.toOctalString(decimalNumber);

        answers.add(oct);

        answers.add(getASCIIValue(decimalNumber));

        // Convert to UNICODE

        return answers;
    }

    public ArrayList<String> convertBinaryInput(String input){
        ArrayList<String> answers = new ArrayList<String>();

        int decimalValue = 0;

        try{
            decimalValue = Integer.parseInt(input, 2);
        }
        catch (Exception e){
            answers.add("ERROR");
            return answers;
        }

        answers.add(decimalValue + "");

        Log.i("ConvertBin", "Decimal Output: " + decimalValue + "");

        answers.add(input);

        String hex = Integer.toHexString(decimalValue);

        answers.add(hex);

        String oct = Integer.toOctalString(decimalValue);

        answers.add(oct);

        answers.add(getASCIIValue(decimalValue));

        // Convert to UNICODE

        return answers;
    }

    public ArrayList<String> convertHexInput(String input){
        ArrayList<String> answers = new ArrayList<String>();

        if(input.charAt(0) == '0' && input.charAt(1) == 'x'){
            input = input.substring(2);
        }

        int decimalValue = 0;

        try{
            decimalValue = Integer.parseInt(input, 16);
        }
        catch (Exception e){
            answers.add("ERROR");
            return answers;
        }

        answers.add(decimalValue + "");

        String binary = Integer.toBinaryString(decimalValue);

        answers.add(binary);

        answers.add(input);

        String oct = Integer.toOctalString(decimalValue);

        answers.add(oct);

        answers.add(getASCIIValue(decimalValue));

        // CONVERT TO UNICODE

        return answers;
    }

    public ArrayList<String> convertOctalInput(String input){
        ArrayList<String> answers = new ArrayList<String>();

        int decimalValue = 0;

        try{
            decimalValue = Integer.parseInt(input, 8);
        }
        catch (Exception e){
            answers.add("ERROR");
            return answers;
        }

        answers.add(decimalValue + "");

        String binary = Integer.toBinaryString(decimalValue);

        answers.add(binary);

        String hex = Integer.toHexString(decimalValue);

        answers.add(hex);

        answers.add(input);

        answers.add(getASCIIValue(decimalValue));

        // CONVERT TO UNICODE

        return answers;
    }

    public ArrayList<String> convertAsciiInput(String input){
        ArrayList<String> answers = new ArrayList<String>();

        int decimalValue = -1;
        if(input.length() == 1){
            char asciiChar = input.charAt(0);
            decimalValue = (int) asciiChar;
            Log.i("Convert ASCII", "Dec Value: " + decimalValue + "");
            answers.add(decimalValue + "");
        }
        else{
            if(asciiMappings.containsValue(input)){
                for (Integer i: asciiMappings.keySet()) {
                    if(asciiMappings.get(i).equals(input)){
                        decimalValue = i;
                        break;
                    }
                }
                Log.i("Convert ASCII", "Dec Value: " + decimalValue + "");
                answers.add(decimalValue + "");
            }
            else{
                answers.add("ERROR");
            }
        }

        String binary = Integer.toBinaryString(decimalValue);

        answers.add(binary);

        String hex = Integer.toHexString(decimalValue);

        answers.add(hex);

        String oct = Integer.toOctalString(decimalValue);

        answers.add(oct);

        answers.add(input);


        return answers;
    }

    public ArrayList<String> convertUnicodeInput(String input){
        ArrayList<String> answers = new ArrayList<String>();
        return answers;
    }

    public String getASCIIValue(int decimal) {
        if (decimal <= 127) {
            if (asciiMappings.containsKey(decimal)) {
                //answers.add(asciiMappings.get(decimal));
                Log.i("MainConverter", "ASCII String: " + asciiMappings.get(decimal));
                return asciiMappings.get(decimal);
            } else {
                char asciiValue = (char) decimal;
                Log.i("MainConverter", "ASCII String: " + asciiValue);
                //answers.add(asciiValue + "");
                return asciiValue + "";
            }

        } else {
            //answers.add("OUTSIDE ASCII TABLE RANGE");
            return "OUTSIDE ASCII TABLE RANGE";
        }

    }
}
