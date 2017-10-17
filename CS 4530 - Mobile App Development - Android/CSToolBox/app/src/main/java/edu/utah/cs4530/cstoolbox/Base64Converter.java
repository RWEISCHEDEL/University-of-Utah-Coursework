package edu.utah.cs4530.cstoolbox;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

/**
 * Created by Robert on 12/2/2016.
 */

public class Base64Converter {
    public Base64Converter(){

    }

    public String encodeToBase64(String input, int encoding){
        String encodingChoice;
        if(encoding == 1){
            encodingChoice = "UTF-8";
        }
        else if(encoding == 2){
            encodingChoice = "UTF-16";
        }
        else{
            encodingChoice = "ASCII";
        }

        String answer = "";

        try {
            byte[] data = input.getBytes(encodingChoice);
            answer = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            answer = "ERROR";
            e.printStackTrace();
        }

        return answer;
    }

    public String decodeFromBase64(String input, int encoding){

        String encodingChoice;

        if(encoding == 1){
            encodingChoice = "UTF-8";
        }
        else if(encoding == 2){
            encodingChoice = "UTF-16";
        }
        else{
            encodingChoice = "ASCII";
        }

        String answer = "";

        byte[] data = Base64.decode(input, Base64.DEFAULT);

        try {
            answer = new String(data, encodingChoice);
        } catch (UnsupportedEncodingException e) {
            answer = "ERROR";
            e.printStackTrace();
        }

        return answer;
    }

}
