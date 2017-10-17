package cs5530;

import java.sql.ResultSet;
import java.sql.Statement;

public class LoginUser {
	
	public LoginUser() {}
	
	public String validateUserLogin(String uLogin, String uPass, Statement statement) {
		
		String sql = "select * from users where login = '" + uLogin + "'";
		String output = "";
		ResultSet resultSet = null;
		int count = 0;
		
	 	try {
	 		
	 		resultSet = statement.executeQuery(sql);
	 		
	 		while (resultSet.next()) {
	 			
	 			if(resultSet.getString("upassword").equals(uPass)) {
	 				output = resultSet.getString("usertype");
	 			}
	 			else {
	 				resultSet.close();
	 				return "uPassInvalid";
	 			}
	 			
	 			count++;
	 		}
	 		
	 		if (count < 1) {    
	 			 return "uNameInvalid";
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

}
