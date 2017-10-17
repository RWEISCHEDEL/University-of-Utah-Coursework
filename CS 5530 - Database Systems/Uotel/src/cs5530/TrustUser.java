package cs5530;

import java.sql.*;

public class TrustUser {
	
	public TrustUser() {}
	
	public boolean rateTrustUser(String userLogin, String ratedLogin, String rating, Statement statement){
		
		String mysql1 = "select login_1, login_trustornot from trust where login_1 = '" + userLogin + "' and login_trustornot = '" + ratedLogin + "'";
		
		String mysql2 = "insert into trust values('" + rating +  "','" + userLogin + "','" + ratedLogin + "')";
		
		int rowsAffected = 0;
		
		ResultSet resultSet = null;
		boolean newRating = true;

		try {
			resultSet = statement.executeQuery(mysql1);
			
			while (resultSet.next()) {
				newRating = false;
			}

			resultSet.close();
		} catch (Exception e) {
			System.out.println("cannot execute the query");
		} finally {
			try {
				if (resultSet != null && !resultSet.isClosed()){
					resultSet.close();
				}
			} catch (Exception e) {
				System.out.println("Cannot close ResultSet.");
			}
		}
		
		resultSet = null;
		
		if(newRating){
			try {
				
	 		rowsAffected = statement.executeUpdate(mysql2);
	 		
	 		if(rowsAffected > 0) {
	 			return true;
	 		}
	 		else {
	 			return false;
	 			}
	 		}
			catch(Exception e) {
				System.out.println("Cannot execute the query.");
				System.err.println(e.getMessage());
			}
		}
		
		return false;
		
	}
	
	public String viewUserTrust(String trustLogin, Statement statement){
		
		String mysql = "select login_1, is_trusted, login_trustornot from trust where login_trustornot = '" + trustLogin + "'";

		String output = "";
		ResultSet resultSet = null;

		try {
			resultSet = statement.executeQuery(mysql);
			
			while (resultSet.next()) {
				output += resultSet.getString("login_1") + "   "
						+ resultSet.getString("is_trusted") + "   "
						+ resultSet.getString("login_trustornot") + "\n";
			}

			resultSet.close();
		} catch (Exception e) {
			System.out.println("cannot execute the query");
		} finally {
			try {
				if (resultSet != null && !resultSet.isClosed()){
					resultSet.close();
				}
			} catch (Exception e) {
				System.out.println("Cannot close ResultSet.");
			}
		}

		return output;
	}
	
}
