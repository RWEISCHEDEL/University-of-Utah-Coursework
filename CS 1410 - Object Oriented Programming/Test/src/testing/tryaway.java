package testing;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class tryaway {
	public static void main (String[] args){

		boolean a = true;
		boolean b = false;
		
		if(a || b){
			System.out.println("Case 1");
		}
		else if(true){
			System.out.println("Case 2");
		}
		else{
			System.out.println("Case 3");
		}
		
		int sum = 0;
		for(int i = 1; i <= 1000; i++){
			sum+=i;
		}
		System.out.println(sum);
		
		sum = 0;
		for(int i = 1; i < 10; i++){
			for(int j = 1; j < 10; j++){
				for(int k = 1; k < 10; k++){
					sum++;
				}
			}
		}
		System.out.println(sum);
	}	
	
	
}
	
	


