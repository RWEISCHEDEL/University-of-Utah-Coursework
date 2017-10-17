package edu.utah.cs4530.cstoolbox;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by Robert on 12/11/2016.
 */

public class URLConverter {
    public URLConverter(){

    }

    public String encodeURL(String url, int encoding){
        String answer = "";

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

        try {
            answer = URLEncoder.encode(url, encodingChoice);
        } catch (UnsupportedEncodingException e) {
            answer = "ERROR";
            e.printStackTrace();
        }

        return answer;
    }

    public String decodeURL(String url, int encoding){
        String answer = "";

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

        try {
            answer = URLDecoder.decode(url, encodingChoice);
        } catch (UnsupportedEncodingException e) {
            answer = "ERROR";
            e.printStackTrace();
        }

        return answer;
    }
}
