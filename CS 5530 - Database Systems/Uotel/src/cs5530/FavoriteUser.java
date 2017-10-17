package cs5530;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class FavoriteUser {
	
	public FavoriteUser() {}
	
	public boolean newFavorite(String uLogin, String favhid, Statement statement) {
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String fDate = format.format(cal.getTime());
		
	  String mysql="insert into favorites values('" + fDate + "', " + favhid + " , '" + uLogin + "')";

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
	    System.out.println("Error, favorite not added.");
	    System.out.println(e.getMessage());
	  }
	  return false;
	}
	
	public String viewFavorites(String uLogin, Statement statement) {
		
		  String mysql = "select distinct th.hid, th.category, th.url, th.haddress, th.hname, th.hphone, th.yr_built from th inner join (select * from favorites where favlogin = '" + uLogin + "') as x on hid = favhid";
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
