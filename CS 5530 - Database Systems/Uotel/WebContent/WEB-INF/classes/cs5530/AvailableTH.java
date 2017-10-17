package cs5530;

import java.sql.ResultSet;
import java.sql.Statement;

public class AvailableTH {
	
	public AvailableTH() {}
	
	public boolean newPeriod(String dFrom, String dTo, Statement statement){
		
		String mysql = "insert into period values(null, '" + dFrom + "', '" + dTo + "')";
		
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
				System.out.println("Cannot execute the query.");
				System.err.println(e.getMessage());
			}
		
		return false;
		
	}
	
	public String viewPeriods(Statement statement){
		
		String mysql = "SELECT * FROM period;";

		String output = "";
		ResultSet resultSet = null;

		try {
			resultSet = statement.executeQuery(mysql);
			
			while (resultSet.next()) {
				output += resultSet.getString("pid") + "   "
						+ resultSet.getString("pfrom") + "   "
						+ resultSet.getString("pto") + "\n";
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
	
	public boolean deletePeriod(String pid, Statement statement) {

		String mysql = "delete from period where pid = " + pid;
		int rowsAffected = 0;

		try {
			rowsAffected = statement.executeUpdate(mysql);

			if (rowsAffected > 0) {
				return true;
			} 
			else {
				return false;
			}
		} catch (Exception e) {
			System.out.println("Cannot execute the query.");
			System.err.println(e.getMessage());
		}
		return false;
	}
	
	public boolean addAvailability(String hid, String pid, String price, Statement statement){
		
		String pFrom = "";
		String pTo = "";
		
		String mysql1 = "select pfrom, pto from period where pid = " + pid;
		
		String mysql3 = "insert into available values(" + price + "," + hid +"," + pid + ")";
		
		int rowsAffected = 0;
		
		ResultSet resultSet = null;
		boolean newAvail = true;
		
		try {
			resultSet = statement.executeQuery(mysql1);

			while (resultSet.next()) {
				pFrom = resultSet.getString("pfrom");
			    pTo = resultSet.getString("pto");
			}

			resultSet.close();
		} catch (Exception e) {
			System.out.println("Cannot execute the query.");
		} finally {
			try {
				if (resultSet != null && !resultSet.isClosed())
					resultSet.close();
			} catch (Exception e) {
				System.out.println("Cannot close ResultSet.");
			}
		}
		
		String mysql2 = "select * from available A inner join (select pid  "
				+ "from period where pfrom <= '" + pFrom + "' and pto >= '" + pTo + "') "
				+ "as x on A.apid = x.pid where ahid = " + hid;
		
		resultSet = null;

		try {
			resultSet = statement.executeQuery(mysql2);
			
			while (resultSet.next()) {
				newAvail = false;
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
		
		if(newAvail){
			try {
				
	 		rowsAffected = statement.executeUpdate(mysql3);
	 		
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
	
	public String viewAvailablityTH(String hid, Statement statement){
		
		String mysql = "select * from available where ahid = " + hid;

		String output = "";
		ResultSet resultSet = null;

		try {
			resultSet = statement.executeQuery(mysql);
			
			while (resultSet.next()) {
				output += resultSet.getString("price_per_night") + "   "
						+ resultSet.getString("ahid") + "   "
						+ resultSet.getString("apid") + "\n";
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

		return output;
	}
	
	public boolean deleteAvailabilty(String hid, String pid, Statement statement) {

		String mysql = "delete from available where (ahid, apid) = (" + hid + "," + pid + ")";
		int rowsAffected = 0;

		try {
			rowsAffected = statement.executeUpdate(mysql);

			if (rowsAffected > 0) {
				return true;
			} 
			else {
				return false;
			}
		} catch (Exception e) {
			System.out.println("Cannot execute the query.");
			System.err.println(e.getMessage());
		}
		return false;
	}
	
}
