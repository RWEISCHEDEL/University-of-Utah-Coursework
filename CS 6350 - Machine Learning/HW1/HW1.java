import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;

public class HW1 {
	
	public static void main(String[] args){
        
		// Training Data Structures
        ArrayList<Boolean> inputDataResultTraining = new ArrayList<Boolean>();
        ArrayList<String> inputDataNameTraining = new ArrayList<String>();
        ArrayList<ArrayList<Integer>> tableTraining = new ArrayList<ArrayList<Integer>>();
        
        // Test Data Structures
        ArrayList<Boolean> inputDataResultTest = new ArrayList<Boolean>();
        ArrayList<String> inputDataNameTest = new ArrayList<String>();
        ArrayList<ArrayList<Integer>> tableTest = new ArrayList<ArrayList<Integer>>();
        
        // Read in both the text file data.
        readFile("./Updated_Dataset/updated_train.txt", inputDataNameTraining, inputDataResultTraining, tableTraining);
        
        readFile("./Updated_Dataset/updated_test.txt", inputDataNameTest, inputDataResultTest, tableTest);
        
        ArrayList<Integer> features = new ArrayList<Integer>();
        
        resetFeatures(features);
        
       fillTable(inputDataNameTraining, inputDataResultTraining, tableTraining, features);
       
       fillTable(inputDataNameTest, inputDataResultTest, tableTest, features);
       
       Node tree = ID3Algorithm(tableTraining, features, inputDataResultTraining, 20);
       
       double trainingPercent = calculateAccuracy(tree, tableTraining, inputDataResultTraining);
       
       double testPercent = calculateAccuracy(tree, tableTest, inputDataResultTest);
       
       System.out.println("Part 1");
       
       System.out.println("Percent Accurate Using Training Data - Depth 20: " + trainingPercent);
       
       System.out.println("Percent Accurate Using Test Data - Depth 20: " +testPercent + "\n");
       
       calculateCrossValidation(features);
       
       resetFeatures(features);
       
       Node tree1 = ID3Algorithm(tableTraining, features, inputDataResultTraining, 5);
       
       double trainingPercent1 = calculateAccuracy(tree1, tableTraining, inputDataResultTraining);
       
       double testPercent1 = calculateAccuracy(tree1, tableTest, inputDataResultTest);
       
       System.out.println("Rerun Training Data With Best Depth Value");
      
       System.out.println("Percent Accurate Using Training Data - Depth 5: " + trainingPercent);
       
       System.out.println("Percent Accurate Using Test Data - Depth 5: " +testPercent + "\n");
        
	}
	
	public static void calculateCrossValidation(ArrayList<Integer> features){
		System.out.println("Part 2");
		System.out.println("Calculate Cross Validation");
		
		// Training Data Structures For Training 00
        ArrayList<Boolean> inputDataResultTraining00 = new ArrayList<Boolean>();
        ArrayList<String> inputDataNameTraining00 = new ArrayList<String>();
        ArrayList<ArrayList<Integer>> tableTraining00 = new ArrayList<ArrayList<Integer>>();
        
        readFile("./Updated_Dataset/Updated_CVSplits/updated_training00.txt", inputDataNameTraining00, inputDataResultTraining00, tableTraining00);
        
        fillTable(inputDataNameTraining00, inputDataResultTraining00, tableTraining00, features);
        
        
        // Training Data Structures For Training 01
        ArrayList<Boolean> inputDataResultTraining01 = new ArrayList<Boolean>();
        ArrayList<String> inputDataNameTraining01 = new ArrayList<String>();
        ArrayList<ArrayList<Integer>> tableTraining01 = new ArrayList<ArrayList<Integer>>();
        
        readFile("./Updated_Dataset/Updated_CVSplits/updated_training01.txt", inputDataNameTraining01, inputDataResultTraining01, tableTraining01);
        
        fillTable(inputDataNameTraining01, inputDataResultTraining01, tableTraining01, features);
        
        // Training Data Structures For Training 02
        ArrayList<Boolean> inputDataResultTraining02 = new ArrayList<Boolean>();
        ArrayList<String> inputDataNameTraining02 = new ArrayList<String>();
        ArrayList<ArrayList<Integer>> tableTraining02 = new ArrayList<ArrayList<Integer>>();
        
        readFile("./Updated_Dataset/Updated_CVSplits/updated_training02.txt", inputDataNameTraining02, inputDataResultTraining02, tableTraining02);
        
        fillTable(inputDataNameTraining02, inputDataResultTraining02, tableTraining02, features);
        
        // Training Data Structures For Training 03
        ArrayList<Boolean> inputDataResultTraining03 = new ArrayList<Boolean>();
        ArrayList<String> inputDataNameTraining03 = new ArrayList<String>();
        ArrayList<ArrayList<Integer>> tableTraining03 = new ArrayList<ArrayList<Integer>>();
        
        readFile("./Updated_Dataset/Updated_CVSplits/updated_training03.txt", inputDataNameTraining03, inputDataResultTraining03, tableTraining03);
        
        fillTable(inputDataNameTraining03, inputDataResultTraining03, tableTraining03, features);
        
        // Combine Table Combinations For 1 2 3
        ArrayList<ArrayList<Integer>> combinedTableTraining123 = new ArrayList<ArrayList<Integer>>();
        ArrayList<Boolean> combinedInputDataResultTraining123 = new ArrayList<Boolean>();
        
        combinedTableTraining123 = combineTables(tableTraining00, tableTraining01, tableTraining02);
        combinedInputDataResultTraining123 = combineInputDataResults(inputDataResultTraining00, inputDataResultTraining01, inputDataResultTraining02);
        
        // Combine Table Combinations for 2 3 4
        ArrayList<ArrayList<Integer>> combinedTableTraining234 = new ArrayList<ArrayList<Integer>>();
        ArrayList<Boolean> combinedInputDataResultTraining234 = new ArrayList<Boolean>();
        
        combinedTableTraining234 = combineTables(tableTraining01, tableTraining02, tableTraining03);
        combinedInputDataResultTraining234 = combineInputDataResults(inputDataResultTraining01, inputDataResultTraining02, inputDataResultTraining03);
        
        // Combine Table Combinataions for 1 3 4
        ArrayList<ArrayList<Integer>> combinedTableTraining134 = new ArrayList<ArrayList<Integer>>();
        ArrayList<Boolean> combinedInputDataResultTraining134 = new ArrayList<Boolean>();
        
        combinedTableTraining134 = combineTables(tableTraining00, tableTraining02, tableTraining03);
        combinedInputDataResultTraining134 = combineInputDataResults(inputDataResultTraining00, inputDataResultTraining02, inputDataResultTraining03);
        
        // Combine Table Combinations for 1 2 4
        ArrayList<ArrayList<Integer>> combinedTableTraining124 = new ArrayList<ArrayList<Integer>>();
        ArrayList<Boolean> combinedInputDataResultTraining124 = new ArrayList<Boolean>();
        
        combinedTableTraining124 = combineTables(tableTraining00, tableTraining01, tableTraining03);
        combinedInputDataResultTraining124 = combineInputDataResults(inputDataResultTraining00, inputDataResultTraining01, inputDataResultTraining03);
        
        ArrayList<Integer> depths = new ArrayList<Integer>();
        
        depths.add(1);
        depths.add(5);
        depths.add(10);
        depths.add(15);
        depths.add(20);
        
        for(int i = 0; i < depths.size(); i++){
        	
        	
        	System.out.println("Calcualte Cross Validation For Depth " + depths.get(i));
        	
        	Node tree1 = ID3Algorithm(combinedTableTraining123, features, combinedInputDataResultTraining123, depths.get(i));
        	
        	double trainingPercent1 = calculateAccuracy(tree1, tableTraining03, inputDataResultTraining03);
        	
        	resetFeatures(features);
        	
        	System.out.println("Accuracy for Table Training Combinations 1 2 3 - Testing 4: " + trainingPercent1);
        	
        	
        	Node tree2 = ID3Algorithm(combinedTableTraining234, features, combinedInputDataResultTraining234, depths.get(i));
        	
        	double trainingPercent2 = calculateAccuracy(tree2, tableTraining00, inputDataResultTraining00);
        	
        	resetFeatures(features);
        	
        	System.out.println("Accuracy for Table Training Combinations 2 3 4 - Testing 1: " + trainingPercent2);
        	
        	
        	Node tree3 = ID3Algorithm(combinedTableTraining134, features, combinedInputDataResultTraining134, depths.get(i));
        	
        	double trainingPercent3 = calculateAccuracy(tree3, tableTraining01, inputDataResultTraining01);
        	
        	resetFeatures(features);
        	
        	System.out.println("Accuracy for Table Training Combinations 1 3 4 - Testing 2: " + trainingPercent3);
        	
        	
        	Node tree4 = ID3Algorithm(combinedTableTraining124, features, combinedInputDataResultTraining124, depths.get(i));
        	
        	double trainingPercent4 = calculateAccuracy(tree4, tableTraining02, inputDataResultTraining02);
        	
        	resetFeatures(features);
        	
        	System.out.println("Accuracy for Table Training Combinations 1 2 4 - Testing 3: " + trainingPercent4);
        	
        	double avgAccuracy = (trainingPercent1 + trainingPercent2 + trainingPercent3 + trainingPercent4) / (double)4;
        	
        	System.out.println("Average Accuracy for Depth " + depths.get(i) + " : " + avgAccuracy);
        	
        	
        	double stdDeviation = Math.pow((trainingPercent1 - avgAccuracy), 2) + Math.pow((trainingPercent2 - avgAccuracy), 2) + Math.pow((trainingPercent3 - avgAccuracy), 2) + Math.pow((trainingPercent4 - avgAccuracy), 2);
        	
        	stdDeviation /= (double)4;
        	
        	stdDeviation = Math.sqrt(stdDeviation);
        	
        	System.out.println("Standard Deviation for Depth " + depths.get(i) + " : " + stdDeviation + "\n");
        }
        
        
	}
	
	public static double calculateAccuracy(Node tree, ArrayList<ArrayList<Integer>> table, ArrayList<Boolean> inputDataResult){
		
		ArrayList<Integer> correct = new ArrayList<Integer>();
		
		
		for(int i = 0; i < table.size(); i++){
			
			boolean exampleAnswer = inputDataResult.get(i);
			
			while(tree.getNodeLeft() != null && tree.getNodeRight() != null){
				if(table.get(i).get(tree.getFeature()) == 0){
					tree = tree.getNodeLeft();
				}
				else{
					tree = tree.getNodeRight();
				}
			}
			
			if(tree.getFeature() == 20){
				if(exampleAnswer){
					correct.add(1);
				}
			}
			else{
				if(!exampleAnswer){
					correct.add(1);
				}

			}
		}
		
		//System.out.println("Val value: " + val);
		
		return correct.size() / (double)table.size();
	}
	
	public static Node ID3Algorithm(ArrayList<ArrayList<Integer>> table, ArrayList<Integer> features, ArrayList<Boolean> inputDataResult, int depth){
		
		if(depth == 0){
			int pos = 0, neg = 0;
			
			for(int c = 0; c < inputDataResult.size(); c++){
				if(inputDataResult.get(c)){
					pos++;
				}
				else{
					neg++;
				}
			}
			
			if(pos > neg){
				//System.out.println("Add a pos");
				return new Node(20, null, null);
			}
			else{
				//System.out.println("Add a neg");
				return new Node(21, null, null);
			}
		}
		else{
			depth--;
		}
		
		Node root = new Node(-1, null, null);
		
		boolean allPositives = true;
		for(int p = 0; p < inputDataResult.size(); p++){
			if(inputDataResult.get(p) == false){
				allPositives = false;
				break;
			}
		}
		
		
		// Check if All is the Same
		// Check for positives
		if(allPositives){
			//System.out.println("Added a Pos");
			return new Node(20, null, null);
		}
		
		// Check for all negatives
		boolean allNegatives = true;
		for(int n = 0; n < inputDataResult.size(); n++){
			if(inputDataResult.get(n) == true){
				allNegatives = false;
				break;
			}
		}
		
		if(allNegatives){
			//System.out.println("Added a Neg");
			return new Node(21, null, null);
		}
		
		// Check If All Features Used
		boolean allFeaturesUsed = true;
		for(int f = 0; f < features.size(); f++){
			if(features.get(f) != -1){
				allFeaturesUsed = false;
				break;
			}
		}
		
		if(allFeaturesUsed){
			int pos = 0, neg = 0;
			for(int i = 0; i < inputDataResult.size(); i++){
				if(inputDataResult.get(i)){
					pos++;
				}
				else{
					neg++;
				}
			}
			
			if(pos > neg){
				//System.out.println("Added a Pos");
				return new Node(20, null, null);
			}
			else{
				//System.out.println("Added a Neg");
				return new Node(21, null, null);
			}
			
		}
		
		
		
		// Look at the Two Paths of the Node
		 int featureIndex = calculateInfoGain(table, inputDataResult, features);
		 //System.out.println("\nFeature Index: " + featureIndex);
		 root.setFeature(featureIndex);
		 
		 // Look at the Left Path
		 ArrayList<ArrayList<Integer>> negTable = new ArrayList<ArrayList<Integer>>(table);
		 ArrayList<Boolean> inputDataResultNeg = new ArrayList<Boolean>(inputDataResult);
		 
		 for(int i = 0; i < negTable.size(); i++){
			 if(negTable.get(i).get(featureIndex) == 1){
				 negTable.remove(i);
				 inputDataResultNeg.remove(i);
			 }
		 }
		 
		 if(negTable.size() == 0){
			 int pos = 0, neg = 0;
				for(int i = 0; i < inputDataResult.size(); i++){
					if(inputDataResult.get(i)){
						pos++;
					}
					else{
						neg++;
					}
				}
				
				if(pos > neg){
					//System.out.println("Added a Pos");
					root.setNodeLeft(new Node(20, null, null));
				}
				else{
					//System.out.println("Added a Neg");
					root.setNodeLeft(new Node(21, null, null));
				}
		 }
		 
		// Look at the Right Path
		 ArrayList<ArrayList<Integer>> posTable = new ArrayList<ArrayList<Integer>>(table);
		 ArrayList<Boolean> inputDataResultPos = new ArrayList<Boolean>(inputDataResult);
		 
		 for(int j = 0; j < posTable.size(); j++){
			 if(posTable.get(j).get(featureIndex) == 0){
				 posTable.remove(j);
				 inputDataResultPos.remove(j);
				 }
			 }
		 
		 if(posTable.size() == 0){
			 int pos = 0, neg = 0;
				for(int i = 0; i < inputDataResult.size(); i++){
					if(inputDataResult.get(i)){
						pos++;
					}
					else{
						neg++;
					}
				}
				
				if(pos > neg){
					//System.out.println("Added a Pos");
					root.setNodeRight(new Node(20, null, null));
				}
				else{
					//System.out.println("Added a Neg");
					root.setNodeRight(new Node(21, null, null));
				}
		 }
		 
		 ArrayList<Integer> newFeatures = new ArrayList<Integer>(features);
		 
		 newFeatures.set(featureIndex, -1);
		 
		 root.setNodeLeft(ID3Algorithm(negTable, newFeatures, inputDataResultNeg, depth));
		 
		 root.setNodeRight(ID3Algorithm(posTable, newFeatures, inputDataResultPos, depth));
		
		return root;
	}
	
	/***
	 * Read the file
	 * @param filePath
	 * @param inputDataName
	 * @param inputDataResult
	 * @param table
	 */
	public static void readFile(String filePath, ArrayList<String> inputDataName, ArrayList<Boolean> inputDataResult, ArrayList<ArrayList<Integer>> table){
		FileInputStream stream = null;
        BufferedReader reader = null;
		
		try {
            stream = new FileInputStream(filePath);
            reader = new BufferedReader(new InputStreamReader(stream));
        
          
            String line = "";
            while(line != null){
                line = reader.readLine();
                if(line.charAt(0) == '+'){
                	inputDataResult.add(true);
                }
                else{
                	inputDataResult.add(false);
                }
                inputDataName.add(line.substring(2));
            }           
          
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
          
        } finally {
            try {
                reader.close();
                stream.close();
            } catch (Exception e) {
            }
        }
        
        for(int i = 0; i < inputDataName.size(); i++){
        	table.add(new ArrayList<Integer>());
        }
	}
	
	/***
	 * Fill up the table according to the 6 features, and start to calculate the Info Gain.
	 * @param inputDataName
	 * @param inputDataResult
	 * @param table
	 * @return
	 */
	public static void fillTable(ArrayList<String> inputDataName, ArrayList<Boolean> inputDataResult, ArrayList<ArrayList<Integer>> table, ArrayList<Integer> features){
		for(int i = 0; i < inputDataName.size(); i++){
        	String[] name = inputDataName.get(i).split(" ");
        	
        	// Feature 1 - Is First Name Longer Than Last Name?
        	if(name[0].length() > name[name.length - 1].length()){
        		table.get(i).add(1);
        	}
        	else{
        		table.get(i).add(0);
        	}
        	
        	// Feature 2 - Do they have a middle name?
        	if(name.length > 2){
        		table.get(i).add(1);
        	}
        	else{
        		table.get(i).add(0);
        	}
        	
        	// Feature 3 - Does their first name start and end with the same letter? (ie "Ada")
        	if(name[0].toLowerCase().charAt(0) == name[0].toLowerCase().charAt(name[0].length() - 1)){
        		table.get(i).add(1);
        	}
        	else{
        		table.get(i).add(0);
        	}
        	
        	// Feature 4 - Does their first name come alphabetically before their last name? (ie "Dan Klein" because "d" comes before "k")
        	if(name[0].charAt(0) < name[name.length - 1].charAt(0)){
        		table.get(i).add(1);
        	}
        	else{
        		table.get(i).add(0);
        	}
        	
        	// Feature 5 - Is the second letter of their first name a vowel (a,e,i,o,u)?
        	if(name[0].length() > 2){
        		if(name[0].charAt(1) == 'a' || name[0].charAt(1) == 'e' || name[0].charAt(1) == 'i' || name[0].charAt(1) == 'o' || name[0].charAt(1) == 'u'){
            		table.get(i).add(1);
            	}
            	else{
            		table.get(i).add(0);
            	}
        	}
        	else{
        		table.get(i).add(0);
        	}
        	
        	
        	
        	// Feature 6 - Is the number of letters in their last name even?
        	if(name[name.length - 1].length() % 2 == 0){
        		table.get(i).add(1);
        	}
        	else{
        		table.get(i).add(0);
        	}
        	
        	// Feature 7 - Is the first letter of their first name a vowel (a,e,i,o,u)?
        	if(name[0].toLowerCase().charAt(0) == 'a' || name[0].toLowerCase().charAt(0) == 'e' || name[0].toLowerCase().charAt(0) == 'i' || name[0].toLowerCase().charAt(0) == 'o' || name[0].toLowerCase().charAt(0) == 'u'){
        		table.get(i).add(1);
        	}
        	else{
        		table.get(i).add(0);
        	}
        	
        	// Feature 8 - Is the number of letters in their first name even?
        	if(name[0].length() % 2 == 0){
        		table.get(i).add(1);
        	}
        	else{
        		table.get(i).add(0);
        	}
        	
        	// Feature 9 - Is the first letter of their last name a vowel (a,e,i,o,u)?
        	if(name[name.length - 1].toLowerCase().charAt(0) == 'a' || name[name.length - 1].toLowerCase().charAt(0) == 'e' || name[name.length - 1].toLowerCase().charAt(0) == 'i' || name[name.length - 1].toLowerCase().charAt(0) == 'o' || name[name.length - 1].toLowerCase().charAt(0) == 'u'){
        		table.get(i).add(1);
        	}
        	else{
        		table.get(i).add(0);
        	}
        	
        	// Feature 10 - Is the second letter of their last name a vowel (a,e,i,o,u)?
        	if(name[name.length - 1].charAt(1) == 'a' || name[name.length - 1].charAt(1) == 'e' || name[name.length - 1].charAt(1) == 'i' || name[name.length - 1].charAt(1) == 'o' || name[name.length - 1].charAt(1) == 'u'){
        		table.get(i).add(1);
        	}
        	else{
        		table.get(i).add(0);
        	}
        	
        	// Feature 11 - Does their last name start and end with the same letter? (ie "Level")
        	if(name[name.length - 1].toLowerCase().charAt(0) == name[name.length - 1].toLowerCase().charAt(name[name.length - 1].length() - 1)){
        		table.get(i).add(1);
        	}
        	else{
        		table.get(i).add(0);
        	}
        	
        	// Feature 12 - Is their first name the same length as their last name?
        	if(name[0].length() == name[name.length - 1].length()){
        		table.get(i).add(1);
        	}
        	else{
        		table.get(i).add(0);
        	}
        	
        	// Feature 13 - Is the last letter of thier first name a vowel (a,e,i,o,u)?
        	if(name[0].toLowerCase().charAt(name[0].length() - 1) == 'a' || name[0].toLowerCase().charAt(name[0].length() - 1) == 'e' || name[0].toLowerCase().charAt(name[0].length() - 1) == 'i' || name[0].toLowerCase().charAt(name[0].length() - 1) == 'o' || name[0].toLowerCase().charAt(name[0].length() - 1) == 'u'){
        		table.get(i).add(1);
        	}
        	else{
        		table.get(i).add(0);
        	}
        	
        	// Feature 14 - Is the last letter of their last name a vowel (a,e,i,o,u)?
        	if(name[name.length - 1].toLowerCase().charAt(name[name.length - 1].length() - 1) == 'a' || name[name.length - 1].toLowerCase().charAt(name[name.length - 1].length() - 1) == 'e' || name[name.length - 1].toLowerCase().charAt(name[name.length - 1].length() - 1) == 'i' || name[name.length - 1].toLowerCase().charAt(name[name.length - 1].length() - 1) == 'o' || name[name.length - 1].toLowerCase().charAt(name[name.length - 1].length() - 1) == 'u'){
        		table.get(i).add(1);
        	}
        	else{
        		table.get(i).add(0);
        	}
        	
        	// Feature 15 - Is the number of letters in their last name even?
        	if(name[name.length - 1].length() % 2 == 0){
        		table.get(i).add(1);
        	}
        	else{
        		table.get(i).add(0);
        	}
        	
        	// Feature 16 - Does the last letter of their first name come alphabetically before the last letter of their last name? (ie "Dan Klein" because "d" comes before "k")
        	if(name[0].toLowerCase().charAt(name[0].length() - 1) < name[name.length - 1].toLowerCase().charAt(name[name.length - 1].length() - 1)){
        		table.get(i).add(1);
        	}
        	else{
        		table.get(i).add(0);
        	}
        	
        	// Feature 17 - Is the number of letters in their last name divisible by 5?
        	if(name[name.length - 1].length() % 5 == 0){
        		table.get(i).add(1);
        	}
        	else{
        		table.get(i).add(0);
        	}
        	
        	// Feature 18 - Is the number of letters in their first name divisible by 5?
        	if(name[0].length() % 5 == 0){
        		table.get(i).add(1);
        	}
        	else{
        		table.get(i).add(0);
        	}
        	
        	// Feature 19 - Is the second to last value in their first name a vowel (a,e,i,o,u)?
        	if(name[0].length() > 2){
        		if(name[0].charAt(name[0].length() - 2) == 'a' || name[0].charAt(name[0].length() - 2) == 'e' || name[0].charAt(name[0].length() - 2) == 'i' || name[0].charAt(name[0].length() - 2) == 'o' || name[0].charAt(name[0].length() - 2) == 'u'){
            		table.get(i).add(1);
            	}
            	else{
            		table.get(i).add(0);
            	}
        	}
        	else{
        		table.get(i).add(0);
        	}
        	
        	
        	// Feature 20 - Is the second to last value in their last name a vowel (a,e,i,o,u)?
        	if(name[name.length - 1].charAt(name[name.length - 1].length() - 2) == 'a' || name[name.length - 1].charAt(name[name.length - 1].length() - 2) == 'e' || name[name.length - 1].charAt(name[name.length - 1].length() - 2) == 'i' || name[name.length - 1].charAt(name[name.length - 1].length() - 2) == 'o' || name[name.length - 1].charAt(name[name.length - 1].length() - 2) == 'u'){
        		table.get(i).add(1);
        	}
        	else{
        		table.get(i).add(0);
        	}
        	
        	
        	
        }
        
       
	}
	
	/***
	 * This method calcualtes the desired entropy from the P+ and P- values.
	 * @param pos
	 * @param neg
	 * @return
	 */
	public static double calculateEntropy(double pos, double neg){
		
		return (-pos * (Math.log(pos) / Math.log(2))) - (neg * (Math.log(neg)/Math.log(2)));
		
	}
	
	/**
	 * Get Info Gains for the whole Table
	 * @param table
	 * @param inputDataResult
	 * @return
	 */
	public static int calculateInfoGain(ArrayList<ArrayList<Integer>> table, ArrayList<Boolean> inputDataResult, ArrayList<Integer> features){
		
		// Get Label (+ or -) Entropy
		int size = inputDataResult.size();
		
		int pos = 0, neg = 0;
		
		for(int i = 0; i < inputDataResult.size(); i++){
			if(inputDataResult.get(i)){
				pos++;
			}
			else{
				neg++;
			}
		}
		
		double labelEntropy = calculateEntropy(pos / (double)size, neg / (double)size);
		
		//System.out.println("\nLabel Entropy: " + labelEntropy);
		
		ArrayList<Double> infoGains = new ArrayList<Double>();
		
		for(int i = 0; i < features.size(); i++){
			
			if(features.get(i) == -1){
				infoGains.add(-1.0);
				continue;
			}
			infoGains.add(calculateFeatureInfoGain(table, inputDataResult, features.get(i), labelEntropy));
			
		}
		
		double max = infoGains.get(0);
        int index = 0;
		
		for(int i = 1; i < infoGains.size(); i++){
	    	   if(infoGains.get(i) > max){
	    		   max = infoGains.get(i);
	    		   index = i;
	    	   }
	    }
		
		
		//System.out.println("\nInfo Gains");
		for(int i = 0; i < infoGains.size(); i++){
			//System.out.println(infoGains.get(i));
		}
		
		
		return index;
	}
	
	
	/***
	 * Calculates the Info Gain for each Feature in the Table.
	 * @param table
	 * @param inputDataResult
	 * @param feature
	 * @param labelEntropy
	 * @return
	 */
	public static double calculateFeatureInfoGain(ArrayList<ArrayList<Integer>> table, ArrayList<Boolean> inputDataResult, int feature, double labelEntropy){
		
		int pospos = 0, posneg = 0, negpos = 0, negneg = 0;
				
		for(int i = 0; i < table.size(); i++){
			if(table.get(i).get(feature) == 1){
				if(inputDataResult.get(i)){
					pospos++;
					}
				else{
					posneg++;
					}
				}
			else{
				if(inputDataResult.get(i)){
					negpos++;
					}
				else{
					negneg++;
					}
				}
			}
				
				double posEntropy, negEntropy;
				if(pospos == 0 || posneg == 0){
					posEntropy = 0;
				}
				else{
					posEntropy = calculateEntropy(pospos / (double)(pospos + posneg), posneg / (double)(pospos + posneg));
				}
				
				if(negpos == 0 || negneg == 0){
					negEntropy = 0;
				}
				else{
					negEntropy = calculateEntropy(negpos / (double)(negpos + negneg), negneg / (double)(negpos + negneg));
				}
				
				double value = (((pospos + posneg) / (double)table.size()) * posEntropy) + (((negpos + negneg) / (double)table.size()) * negEntropy);
				
				return labelEntropy - value;
				
	}
	
	public static void resetFeatures(ArrayList<Integer> features){
		features.clear();
		
		for(int f = 0; f < 20; f++){
        	features.add(f);
        }
	}
	
	public static ArrayList<ArrayList<Integer>> combineTables(ArrayList<ArrayList<Integer>> first, ArrayList<ArrayList<Integer>> second, ArrayList<ArrayList<Integer>> third){
		
		for(int i = 0; i < second.size(); i++){
			first.add(second.get(i));
		}
		
		for(int j = 0; j < third.size(); j++){
			first.add(third.get(j));
		}
		
		return first;
	}
	
	public static ArrayList<Boolean> combineInputDataResults(ArrayList<Boolean> first, ArrayList<Boolean> second, ArrayList<Boolean> third){
		for(int i = 0; i < second.size(); i++){
			first.add(second.get(i));
		}
		
		for(int j = 0; j < third.size(); j++){
			first.add(third.get(j));
		}
		
		return first;
	}
	
	
	
}
