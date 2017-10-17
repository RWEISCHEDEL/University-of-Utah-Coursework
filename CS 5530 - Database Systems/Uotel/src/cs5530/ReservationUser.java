package cs5530;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReservationUser {
	
	public ReservationUser() {}
	
	public boolean newReservation(String uLogin, String hid, String rFrom, String rTo, String ppNight, Statement statement){
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String fDate = format.format(cal.getTime());
		
		String mysql1 = "select * from available A inner join (select pid, pfrom, pto  "
				+ "from period where pfrom <= '"  + rFrom + "' and pto >= '" + rTo + "') "
				+ "as aDates on A.apid = aDates.pid where ahid = " + hid;
		
		String mysql2 = "select * from reserve where rhid = " + hid + " "
				+ "and reserve.rfrom <= '" + rFrom + "' and reserve.rto >= '" + rTo+ "'";
		
		String mysql3 = "insert into reserve "
				+ "values(null, '" + uLogin + "', " + hid + ", '" + rFrom + "', '" + rTo + "', " + ppNight + ", '" + fDate + "')";
		
		int rowsAffected = 0;
		
		boolean newReservation = false;
		
		ResultSet resultSet = null;
		
		try {
			resultSet = statement.executeQuery(mysql1);
			
			while (resultSet.next()) {
				newReservation = true;
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
		
		if(newReservation){
			
			try {
				resultSet = statement.executeQuery(mysql2);
				
				while (resultSet.next()) {
					newReservation = false;
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
			
		}
		else{
			return false;
		}
		
		if(newReservation){
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
	
	public String viewReservations(String uLogin, Statement statement){
		
		String mysql = "select distinct th.hid, th.category, th.url, th.haddress, th.hname, th.hphone, th.yr_built, r.rid, r.rfrom, r.rto, r.pricepernight, r.dateofreserve from th "
				+ "inner join (select * from reserve where rlogin = '" + uLogin + "') "
				+ "as r on th.hid = r.rhid;";

		String output = "";
		ResultSet resultSet = null;

		try {
			resultSet = statement.executeQuery(mysql);
			
			while (resultSet.next()) {
				output += resultSet.getString("hid") + "   "
						+ resultSet.getString("rid") + "   "
						+ resultSet.getString("rfrom") + "   "
						+ resultSet.getString("rto") + "   "
						+ resultSet.getString("pricepernight") + "   "
						+ resultSet.getString("dateofreserve") + "   "
						+ resultSet.getString("category") + "   "
						+ resultSet.getString("url") + "   "
						+ resultSet.getString("haddress") + "   "
						+ resultSet.getString("hname") + "   "
						+ resultSet.getString("hphone") + "   "
						+ resultSet.getString("yr_built") + "\n";
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
	
	public boolean deleteReservation(String rid, Statement statement) {

		String mysql1 = "select * from visit inner join "
				+ "reserve on visit.vrid = " + rid + " and reserve.rid = " + rid;
		
		String mysql2 = "delete from reserve where rid = " + rid;
		
		ResultSet resultSet = null;
		boolean deleteRes = true;
		
		int rowsAffected = 0;

		try {
			resultSet = statement.executeQuery(mysql1);
			
			while (resultSet.next()) {
				deleteRes = false;
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
		
		if(deleteRes){
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
	
	public String showReservationInfo(String hid, Statement statement){
		
		String mysql = "select distinct th.hid, th.category, th.url, th.haddress, th.hname, th.hphone, th.yr_built "
				+ "from th where hid = " + hid;

		String output = "";
		ResultSet resultSet = null;

		try {
			resultSet = statement.executeQuery(mysql);

			while (resultSet.next()) {
				output += resultSet.getString("hid") + "   "
						+ resultSet.getString("category") + "   "
						+ resultSet.getString("url") + "   "
						+ resultSet.getString("haddress") + "   "
						+ resultSet.getString("hname") + "   "
						+ resultSet.getString("hphone") + "   "
						+ resultSet.getString("yr_built") + "\n";
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

		return output;
	}
	
	public String viewReservationSuggestion(String uLogin, String hid, Statement statement){
		
		String mysql = "SELECT distinct th.hid, th.category, th.url, th.haddress, th.hname, th.hphone, th.yr_built, count(*) "
				+ "AS reserves FROM reserve LEFT JOIN th ON reserve.rhid = th.hid "
				+ "WHERE rlogin IN (SELECT distinct rlogin from reserve WHERE hid != " + hid + " "
				+ "and rlogin != '" + uLogin + "') group by hid, rlogin order by reserves desc;";

		String output = "";
		ResultSet resultSet = null;

		try {
			resultSet = statement.executeQuery(mysql);

			while (resultSet.next()) {
				output += resultSet.getString("hid") + "   "
						+ resultSet.getString("category") + "   "
						+ resultSet.getString("url") + "   "
						+ resultSet.getString("haddress") + "   "
						+ resultSet.getString("hname") + "   "
						+ resultSet.getString("hphone") + "   "
						+ resultSet.getString("yr_built") + "\n";
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

		return output;
	}
}
