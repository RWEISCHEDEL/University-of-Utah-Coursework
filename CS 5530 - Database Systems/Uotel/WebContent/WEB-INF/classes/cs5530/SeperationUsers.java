package cs5530;

import java.sql.*;
import java.util.ArrayList;

public class SeperationUsers {
	public SeperationUsers() {}
	
	public String viewSeperation1(String uLogin, Statement statement){
		
		String output = "";
		
		String mysql = "SELECT login, users.uname FROM favorites LEFT JOIN users "
				+ "ON favorites.favlogin = users.login WHERE favorites.favhid IN "
				+ "(SELECT f1.favhid FROM favorites f1, favorites f2 "
				+ "WHERE f1.favlogin != f2.favlogin AND f1.favlogin = '" + uLogin + "' "
				+ "AND f1.favhid = f2.favhid) AND favorites.favlogin != '" + uLogin + "'";
		
		ResultSet resultSet = null;
		
	 	try {
	 		
	 		resultSet = statement.executeQuery(mysql);
	 		
	 		while (resultSet.next()) {
	 			output += resultSet.getString("login") + "   "
						+ resultSet.getString("uname") + "\n";
	 		}
	 		
	 		resultSet.close();
	 	}
	 	catch(Exception e) {
	 		System.out.println("Cannot execute the query.");
	 		System.err.println(e.getMessage());
	 	}
	 	finally {
 			try {
	 			if (resultSet != null && !resultSet.isClosed()){
	 				resultSet.close();
	 			}
	 		}
	 		catch(Exception e) {
	 			System.out.println("Can not close ResultSet.");
	 		}
	 	}
		return output;
	}
	
	public String viewSeperation2(String uLogin, Statement statement){
		
		ArrayList<String> oneDegreeList = new ArrayList<String>();
		
		String mysql1 = "SELECT login, users.uname FROM favorites LEFT JOIN users "
				+ "ON favorites.favlogin = users.login WHERE favorites.favhid IN "
				+ "(SELECT f1.favhid FROM favorites f1, favorites f2 "
				+ "WHERE f1.favlogin != f2.favlogin AND f1.favlogin = '" + uLogin + "' "
				+ "AND f1.favhid = f2.favhid) AND favorites.favlogin != '" + uLogin + "'";
		
		ResultSet resultSet = null;
		
	 	try {
	 		
	 		resultSet = statement.executeQuery(mysql1);
	 		
	 		while (resultSet.next()) {
	 			oneDegreeList.add(resultSet.getString("login"));
	 		}	 		

	 		if (oneDegreeList.isEmpty()) {    
	 			 return "";
	 		}
	 		
	 		resultSet.close();
	 	}
	 	catch(Exception e) {
	 		System.out.println("Cannot execute the query.");
	 		System.err.println(e.getMessage());
	 	}
	 	
	 	resultSet = null;
	 	String output = "";
	 	
	 	for (String login2 : oneDegreeList){
	 		String mysql2 = "SELECT login, users.uname FROM favorites LEFT JOIN users "
					+ "ON favorites.favlogin = users.login WHERE favorites.favhid IN "
					+ "(SELECT f1.favhid FROM favorites f1, favorites f2 "
					+ "WHERE f1.favlogin != f2.favlogin AND f1.favlogin = '" + login2 + "' "
					+ "AND f1.favhid = f2.favhid) AND favorites.favlogin != '" + login2 + "'";
	 	
	 	try {
	 		resultSet = statement.executeQuery(mysql2);
	 		while (resultSet.next()) {
	 			if(!resultSet.getString("login").equals(uLogin)){
	 				output += resultSet.getString("login") + "   "
							+ resultSet.getString("uname") + "\n";
	 			}
	 		}
	 		
	 		resultSet.close();
	 	}
	 	catch(Exception e) {
	 		System.out.println("Cannot execute the query.");
	 		System.err.println(e.getMessage());
	 	}
	 	
	 	finally {
 			try {
	 			if (resultSet != null && !resultSet.isClosed()){
	 				resultSet.close();
	 			}
	 		}
	 		catch(Exception e) {
	 			System.out.println("Can not close ResultSet.");
	 		}
	 	}
	 	}
	 	
		return output;
	}
}
