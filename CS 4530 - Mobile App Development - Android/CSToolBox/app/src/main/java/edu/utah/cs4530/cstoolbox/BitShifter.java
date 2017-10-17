package edu.utah.cs4530.cstoolbox;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Robert on 12/11/2016.
 */

public class BitShifter {
    public BitShifter(){

    }

    public ArrayList<String> convertDecimalInput(String input, int shiftDirection, String times){
        ArrayList<String> answers = new ArrayList<String>();

        Log.i("Bitshifter", input);
        int decimalNumber = Integer.parseInt(input);
        int numberOfshifts = Integer.parseInt(times);

        if(shiftDirection == 1){
            for(int i = 0; i < numberOfshifts; i++){
                decimalNumber *= 2;
            }
        }
        else{
            for(int i = 0; i < numberOfshifts; i++){
                decimalNumber *= 2;
            }
        }

        answers.add(decimalNumber + "");

        String binary = Integer.toBinaryString(decimalNumber);

        answers.add(binary);

        String hex = Integer.toHexString(decimalNumber);

        answers.add(hex);

        String oct = Integer.toOctalString(decimalNumber);

        answers.add(oct);

        return answers;
    }

    public ArrayList<String> convertBinaryInput(String input, int shiftDirection, String times){
        ArrayList<String> answers = new ArrayList<String>();

        int decimalNumber = Integer.parseInt(input, 2);

        int numberOfshifts = Integer.parseInt(times);

        if(shiftDirection == 1){
            for(int i = 0; i < numberOfshifts; i++){
                decimalNumber *= 2;
            }
        }
        else{
            for(int i = 0; i < numberOfshifts; i++){
                decimalNumber *= 2;
            }
        }

        answers.add(decimalNumber + "");

        String binary = Integer.toBinaryString(decimalNumber);

        answers.add(binary);

        String hex = Integer.toHexString(decimalNumber);

        answers.add(hex);

        String oct = Integer.toOctalString(decimalNumber);

        answers.add(oct);

        return answers;
    }

    public ArrayList<String> convertHexInput(String input, int shiftDirection, String times){
        ArrayList<String> answers = new ArrayList<String>();

        int decimalNumber = Integer.parseInt(input, 16);

        int numberOfshifts = Integer.parseInt(times);

        if(shiftDirection == 1){
            for(int i = 0; i < numberOfshifts; i++){
                decimalNumber *= 2;
            }
        }
        else{
            for(int i = 0; i < numberOfshifts; i++){
                decimalNumber *= 2;
            }
        }

        answers.add(decimalNumber + "");

        String binary = Integer.toBinaryString(decimalNumber);

        answers.add(binary);

        String hex = Integer.toHexString(decimalNumber);

        answers.add(hex);

        String oct = Integer.toOctalString(decimalNumber);

        answers.add(oct);

        return answers;
    }

    public ArrayList<String> convertOctalInput(String input, int shiftDirection, String times){
        ArrayList<String> answers = new ArrayList<String>();

        int decimalNumber = Integer.parseInt(input, 8);

        int numberOfshifts = Integer.parseInt(times);

        if(shiftDirection == 1){
            for(int i = 0; i < numberOfshifts; i++){
                decimalNumber *= 2;
            }
        }
        else{
            for(int i = 0; i < numberOfshifts; i++){
                decimalNumber *= 2;
            }
        }

        answers.add(decimalNumber + "");

        String binary = Integer.toBinaryString(decimalNumber);

        answers.add(binary);

        String hex = Integer.toHexString(decimalNumber);

        answers.add(hex);

        String oct = Integer.toOctalString(decimalNumber);

        answers.add(oct);

        return answers;
    }
}
