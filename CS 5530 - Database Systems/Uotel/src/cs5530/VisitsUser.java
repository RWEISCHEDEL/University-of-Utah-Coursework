package cs5530;

import java.sql.*;

public class VisitsUser {
	public VisitsUser() {}
	
	public boolean newVisit(String rid, String rFrom, String rTo, Statement statement){
		
		String mysql1 = "select * from reserve "
				+ "where rfrom = '" + rFrom + "' and rto = '" + rTo + "'";
		
		String mysql2 = "insert into visit "
				+ "values(null, " + rid + ", '" + rFrom + "', '" + rTo + "')";
		
		ResultSet resultSet = null;
		boolean addVisit = false;
		
		int rowsAffected = 0;

		try {
			resultSet = statement.executeQuery(mysql1);
			
			while (resultSet.next()) {
				addVisit = true;
			}

			resultSet.close();
		} catch (Exception e) {
			System.out.println("Cannot execute the query.");
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
		
		if(addVisit){
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
	
	public String viewVisits(String uLogin, Statement statement){
		
		String mysql = "select * from visit inner join "
				+ "(select * from reserve where rlogin = '" + uLogin + "') "
				+ "as r on vrid = r.rid";

		String output = "";
		ResultSet resultSet = null;

		try {
			resultSet = statement.executeQuery(mysql);
			
			while (resultSet.next()) {
				output += resultSet.getString("vid") + "   "
						+ resultSet.getString("vrid") + "   "
						+ resultSet.getString("vfrom") + "   "
						+ resultSet.getString("vto") + "   "
						+ resultSet.getString("pricepernight") + "\n";
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
