package cs5530;

import java.sql.Statement;

public class RegisterUser {
	
public RegisterUser() {}
	
	public boolean registerUser(String uLogin, String fullName, String uPass, String address, String phoneNum, String uType, Statement statement) {
		
		String mysql="INSERT INTO users (login, uname, usertype, upassword, address, phone) "
				+  "VALUES ('" + uLogin +"','" + fullName +"','" + uType +"','" + uPass + "','" + address + "','" + phoneNum + "')";
		
		int rowsAffected = 0;
		
	 	try {
	 		
	 		rowsAffected = statement.executeUpdate(mysql);
	 		
	 		if(rowsAffected > 0) {
	 			return true;
	 		}
	 		else {
	 			return false;
	 		}
	 	}
	 	catch(Exception e) {
	 		System.out.println(e.getLocalizedMessage());
	 		System.out.println("Login name already exists.");
	 	}
		return false;
	}

}
