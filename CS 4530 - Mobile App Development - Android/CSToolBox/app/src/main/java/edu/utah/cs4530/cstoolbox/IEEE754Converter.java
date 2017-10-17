package edu.utah.cs4530.cstoolbox;

import android.util.Log;

/**
 * Created by Robert on 12/2/2016.
 */

public class IEEE754Converter {
    public IEEE754Converter(){

    }

    public double convertFromIEEE(String sign, String exp, String mantissa){

        int signBit;
        double fraction = 0.0;

        if(sign.equals("0")){
            signBit = 0;
        }
        else{
            signBit = 1;
        }

        int exponentValue = Integer.parseInt(exp, 2);

        Log.i("Exponent Value", "Answer: " + exponentValue);

        int exponent = exponentValue - 127;

        Log.i("Exponent", "Answer: " + exponent);

        for(int i = 1; i < 23; i++){
            if(mantissa.charAt(i - 1) == '1'){
                fraction += Math.pow(2, i * -1);
            }
        }

        Log.i("Sign Bit", "Answer: " + Math.pow(-1, signBit));

        Log.i("Fraction", "Answer: " + (1 + fraction));


        double answer = Math.pow(-1, signBit) * (1 + fraction) * Math.pow(2, exponent);

        Log.i("Answer", "Answer: " + answer);

        return answer;
    }
}
