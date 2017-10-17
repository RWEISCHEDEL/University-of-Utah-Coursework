package cs5530;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FeedbackUser {
	public FeedbackUser() {}
	
	public boolean newFeedback(String fHid, String uLogin, String score, String fText, Statement statement) {
		
		String mysql1 = "select fid from feedback where flogin = '" +uLogin +"' and fhid = " + fHid;
		
		ResultSet resultSet = null;
		
		boolean enterNewFeedback = true;
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String fDate = format.format(cal.getTime());
		
		try {
			
			resultSet = statement.executeQuery(mysql1);
			
			while (resultSet.next()) {
				enterNewFeedback = false;
				}
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
				System.out.println("Can not close ResultSet");
			}
		}
		
		resultSet = null;
		
		if(enterNewFeedback) {
			String mysql2 = "insert into feedback " 
		    + "values(null, '" +fHid +"','" + uLogin +"', " 
		    + score +", '" + fText + "' , '" + fDate + "')";
			
			int updateResultRowAffected = 0;
			
			try {
				updateResultRowAffected = statement.executeUpdate(mysql2);
				
				if(updateResultRowAffected > 0) {
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
	
	public String viewTHFeedback(String hid, Statement statement) {
		
		String mysql= "select * from feedback where fhid = " + hid;
		
		String output = "";
		ResultSet resultSet = null;
		
		try {
			resultSet = statement.executeQuery(mysql);
			while (resultSet.next()) {
				output += resultSet.getString("fid") + "   "
			            + resultSet.getString("fhid") + "   " 
						+ resultSet.getString("flogin") + "   "
			            + resultSet.getString("score") + "   "
						+ resultSet.getString("ftext") + "   "
			            + resultSet.getString("fdate") + "\n";
			}
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
				System.out.println("Can not close ResultSet");
			}
		}
		
		return output;
	}
	
	public boolean rateUserFeedback(String uLogin, String raFid, String rating, Statement statement) {
		
		String mysql="insert into rate value("+ rating + ", '" + uLogin +"', " + raFid + ")";
		
		int resultSet = 0;
		
		try {
			resultSet = statement.executeUpdate(mysql);
			
			if(resultSet > 0) {
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
	
	public String viewRatingForFeedback(String rafid, Statement statement) {
		  
		String mysql= "select * from rate where rafid = " + rafid;
		
		String output = "";
		
		ResultSet resultSet = null;

		try {
			
			resultSet = statement.executeQuery(mysql);
		    
			while (resultSet.next()) {
		      
				output += resultSet.getString("rating") + "   "
		                + resultSet.getString("ralogin") + "\n";
				
		    }
			
		  }
		  catch(Exception e) {
			  
		    System.out.println("Cannot execute the query.");
		    System.err.println(e.getMessage());
		    
		  }
		  finally {
			  
		    try {
		    	
		      if (resultSet!=null && !resultSet.isClosed())
		        resultSet.close();
		      
		    }
		    catch(Exception e) {
		    	
		      System.out.println("Can not close ResultSet");
		      
		    }
		    
		  }
		
		  return output;
		  
		}
	
	public String viewTopUsefulFeedbacksForTH(String uLogin, String uLimit, String fHid, Statement statement) {
		  
		String mysql= "select * from (select rafid, round(avg(r.rating), 2) "
				+ "as usefulness from rate r group by rafid order by usefulness "
				+ "desc) as x inner join feedback on x.rafid = fid and fhid = " + fHid + " limit " + uLimit;

		  String output = "";
		  ResultSet resultSet = null;

		  try {
			  
		    resultSet = statement.executeQuery(mysql);
		    
		    while (resultSet.next()) {
		    	
		      output += resultSet.getString("fid") + "   "
		          + resultSet.getString("flogin") + "   "
		          + resultSet.getString("score") + "   "
		          + resultSet.getString("ftext") + "   "
		          + resultSet.getString("fdate") + "   "
		          + resultSet.getString("rafid") + "   "
		          + resultSet.getString("usefulness") + "\n";
		    }
		    
		  }
		  catch(Exception e) {
			  
		    System.out.println("Cannot execute the query.");
		    System.err.println(e.getMessage());
		    
		  }
		  finally {
			  
		    try {
		    	
		      if (resultSet!=null && !resultSet.isClosed()){
		        resultSet.close();
		      }
		      
		    }
		    catch(Exception e) {
		      System.out.println("Can not close ResultSet");
		    }
		    
		  }
		  return output;
		}
}
