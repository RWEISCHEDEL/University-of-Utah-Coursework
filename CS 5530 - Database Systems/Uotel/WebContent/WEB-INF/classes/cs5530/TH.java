package cs5530;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class TH {
	public TH() {
	}

	public String ownedTH(String hLogin, Statement statement) {

		String mysql = "select * from th where hlogin = '" + hLogin + "'";

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

	public boolean newTH(String hLogin, String category, String url,
			String hAddress, String hName, String hPhone, String hYear,
			Statement statement) {

		String mysql = "insert into th values(null," + "'" + hLogin + "','" + category + "','" + url + "','" + hAddress + "','" + hName + "','" + hPhone + "','" + hYear + "')";

		int rowsAffected = 0;

		try {
			rowsAffected = statement.executeUpdate(mysql);

			if (rowsAffected > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			System.out.println("Cannot execute the query.");
			System.err.println(e.getMessage());
		}
		return false;
	}

	public boolean updateTH(String hLogin, String hid, String updateChoice, String updateInfo, Statement statement) {

		String mysql = "";
		
		if(updateChoice.equals("hphone")){
			mysql = "UPDATE th SET " + updateChoice + "=" + updateInfo + " WHERE th.hlogin = '"+ hLogin + "' and hid = " + hid;
		}
		else{
			mysql = "UPDATE th SET " + updateChoice + "='" + updateInfo + "' WHERE th.hlogin = '"+ hLogin + "' and hid = " + hid;
		}

		int rowsAffected = 0;

		try {
			rowsAffected = statement.executeUpdate(mysql);

			if (rowsAffected > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			System.out.println("Cannot execute the query.");
			System.err.println(e.getMessage());
		}
		return false;
	}

	public boolean deleteTH(String hLogin, String hid, Statement statement) {

		String mysql = "delete from th where hlogin = '" + hLogin +"' and hid = "+ hid;
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
	
	public String browseTHPrice(int sortBy, int priceLowPrice, int priceHighPrice, Statement statement){
		
		String mysql = "";
		
		if(sortBy == 1){
			// Price
			mysql = "SELECT distinct th.hid, th.category, th.url, th.haddress, th.hname, th.hphone, th.yr_built, ave_price.ave "
					+ "FROM (SELECT ahid, price_per_night "
					+ "FROM available where price_per_night >= " + priceLowPrice + " and price_per_night <= " + priceHighPrice + ") "
					+ "AS th_names INNER JOIN (SELECT th.hid, avg(reserve.pricepernight)"
					+ "AS ave FROM visit LEFT JOIN reserve ON visit.vrid = reserve.rid "
					+ "LEFT Join th ON th.hid = reserve.rhid group by hname, hid) "
					+ "AS ave_price ON th_names"
					+ ".ahid = ave_price.hid "
					+ "LEFT JOIN th ON th.hid = th_names.ahid order by ave desc;";
		}
		else if(sortBy == 2){
			// Avg Feedback
			mysql = "SELECT distinct th.hid, th.category, th.url, th.haddress, th.hname, th.hphone, th.yr_built, ave_feeds.feed_scores "
					+ "FROM (SELECT ahid, price_per_night FROM available where price_per_night >= " + priceLowPrice + " and price_per_night <= " + priceHighPrice + ") "
					+ "AS th_names INNER JOIN (SELECT th.hid, avg(feedback.score) "
					+ "AS feed_scores FROM feedback LEFT Join th "
					+ "ON th.hid = feedback.fhid group by hname, hid) "
					+ "AS ave_feeds ON th_names.ahid = ave_feeds.hid "
					+ "LEFT JOIN th ON th.hid = th_names.ahid "
					+ "order by feed_scores desc";
		}
		else{
			// Trusted User
			mysql = "SELECT distinct th.hid, th.category, th.url, th.haddress, th.hname, th.hphone, th.yr_built, ave_trusted.trustcount "
					+ "FROM (SELECT ahid, price_per_night FROM available where price_per_night >= " + priceLowPrice + " and price_per_night <= " + priceHighPrice + ") "
					+ "AS th_names INNER JOIN (select th.hid, trustcount from th left join "
					+ "(SELECT login, uname, SUM(case when is_trusted = 'trusted' then 1 end) - count(case when is_trusted = 'not-trusted' then 1 end) "
					+ "AS trustcount FROM trust LEFT JOIN users ON trust.login_trustornot = users.login group by login, uname) as x on x.login = th.hlogin) "
					+ "AS ave_trusted ON th_names.ahid = ave_trusted.hid "
					+ "LEFT JOIN th ON th.hid = th_names.ahid order by trustcount desc";
		}
		
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
						+ resultSet.getString("yr_built") + "   ";
				
				if(sortBy == 1){
					output += resultSet.getString("ave") + "\n";
				}
				else if(sortBy == 2){
					output += resultSet.getString("feed_scores") + "\n";
				}
				else{
					output += resultSet.getString("trustcount") + "\n";
				}
				
			}

			resultSet.close();
		} catch (Exception e) {
			System.out.println("Cannot execute the query.");
			System.err.println(e.getMessage());
		} finally {
			try {
				if (resultSet != null && !resultSet.isClosed())
					resultSet.close();
			} catch (Exception e) {
				System.out.println("Cannot close ResultSet.");
			}
		}
		
		if(output.isEmpty()){
			System.out.println("OUTPUT IS EMPTY");
		}

		return output;
		
	}
	
	public String browseTHCS(int sortBy, String location, Statement statement){
		
		String mysql = "";
		
		if(sortBy == 1){
			// Price
			mysql = "SELECT distinct th.hid, th.category, th.url, th.haddress, th.hname, th.hphone, th.yr_built, ave_price.ave "
					+ "FROM (SELECT hid FROM th where haddress like '%" + location + "%') "
					+ "AS th_names INNER JOIN (SELECT th.hid, avg(reserve.pricepernight) "
					+ "AS ave FROM visit LEFT JOIN reserve ON visit.vrid = reserve.rid "
					+ "LEFT Join th ON th.hid = reserve.rhid group by hname, hid) "
					+ "AS ave_price ON th_names.hid = ave_price.hid LEFT JOIN th "
					+ "ON th.hid = th_names.hid order by ave desc";
		}
		else if(sortBy == 2){
			// Avg Feedback
			mysql = "SELECT distinct th.hid, th.category, th.url, th.haddress, th.hname, th.hphone, th.yr_built, ave_feeds.feed_scores "
					+ "FROM (SELECT hid FROM th where haddress like '%" + location + "%') "
					+ "AS th_names INNER JOIN (SELECT th.hid, avg(feedback.score) "
					+ "AS feed_scores FROM feedback LEFT Join th ON th.hid = feedback.fhid group by hname, hid) "
					+ "AS ave_feeds ON th_names.hid = ave_feeds.hid LEFT JOIN th "
					+ "ON th.hid = th_names.hid order by feed_scores desc";
		}
		else{
			// Trusted User
			mysql = "SELECT distinct th.hid, th.category, th.url, th.haddress, th.hname, th.hphone, th.yr_built, ave_trusted.trustcount "
					+ "FROM (SELECT hid FROM th where haddress like '%" + location + "%') "
					+ "AS th_names INNER JOIN (select th.hid, trustcount from th left join (SELECT login, uname, SUM(case when is_trusted = 'trusted' then 1 end) - count(case when is_trusted = 'not-trusted' then 1 end) "
					+ "AS trustcount FROM trust LEFT JOIN users ON trust.login_trustornot = users.login group by login, uname) as x on x.login = th.hlogin) "
					+ "AS ave_trusted ON th_names.hid = ave_trusted.hid LEFT JOIN th "
					+ "ON th.hid = th_names.hid order by trustcount desc";
		}
		
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
						+ resultSet.getString("yr_built") + "   ";
				
				if(sortBy == 1){
					output += resultSet.getString("ave") + "\n";
				}
				else if(sortBy == 2){
					output += resultSet.getString("feed_scores") + "\n";
				}
				else{
					output += resultSet.getString("trustcount") + "\n";
				}
				
			}

			resultSet.close();
		} catch (Exception e) {
			System.out.println("Cannot execute the query.");
			System.err.println(e.getMessage());
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
	
	public String browseTHKeyword(int sortBy, String uKeyword, Statement statement){
		
		String mysql = "";
		
		if(sortBy == 1){
			// Price
			mysql = "SELECT distinct th.hid, th.category, th.url, th.haddress, th.hname, th.hphone, th.yr_built, ave_price.ave "
					+ "FROM (SELECT th.hid FROM has_keywords LEFT JOIN keywords ON keywords.wid = has_keywords.kwid "
					+ "LEFT JOIN th ON th.hid = has_keywords.khid WHERE word like '%" + uKeyword + "%') "
					+ "AS th_names INNER JOIN (SELECT th.hid, avg(reserve.pricepernight) "
					+ "AS ave FROM visit LEFT JOIN reserve ON visit.vrid = reserve.rid "
					+ "LEFT Join th ON th.hid = reserve.rhid group by hname, hid) "
					+ "AS ave_price ON th_names.hid = ave_price.hid "
					+ "LEFT JOIN th ON th.hid = th_names.hid order by ave desc";
		}
		else if(sortBy == 2){
			// Avg Feedback
			mysql = "SELECT distinct th.hid, th.category, th.url, th.haddress, th.hname, th.hphone, th.yr_built, ave_feeds.feed_scores "
					+ "FROM (SELECT th.hid FROM has_keywords LEFT JOIN keywords ON keywords.wid = has_keywords.kwid "
					+ "LEFT JOIN th ON th.hid = has_keywords.khid WHERE word like '%" + uKeyword + "%') "
					+ "AS th_names INNER JOIN (SELECT th.hid, avg(feedback.score) "
					+ "AS feed_scores FROM feedback LEFT Join th ON th.hid = feedback.fhid group by hname, hid) "
					+ "AS ave_feeds ON th_names.hid = ave_feeds.hid "
					+ "LEFT JOIN th ON th.hid = th_names.hid order by feed_scores desc";
		}
		else{
			// Trusted User
			mysql = "SELECT distinct th.hid, th.category, th.url, th.haddress, th.hname, th.hphone, th.yr_built, trustcount "
					+ "FROM (SELECT th.hid FROM has_keywords LEFT JOIN keywords ON keywords.wid = has_keywords.kwid "
					+ "LEFT JOIN th ON th.hid = has_keywords.khid WHERE word like '%" + uKeyword + "%') "
					+ "AS th_names INNER JOIN (select th.hid, trustcount from th "
					+ "left join (SELECT login, uname, SUM(case when is_trusted = 'trusted' then 1 end) - count(case when is_trusted = 'not-trusted' then 1 end) "
					+ "AS trustcount FROM trust LEFT JOIN users ON trust.login_trustornot = users.login group by login, uname) as x on x.login = th.hlogin) "
					+ "AS ave_trusted ON th_names.hid = ave_trusted.hid LEFT JOIN th ON th.hid = th_names.hid order by trustcount desc";
		}
		
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
						+ resultSet.getString("yr_built") + "   ";
				
				if(sortBy == 1){
					output += resultSet.getString("ave") + "\n";
				}
				else if(sortBy == 2){
					output += resultSet.getString("feed_scores") + "\n";
				}
				else{
					output += resultSet.getString("trustcount") + "\n";
				}
				
			}

			resultSet.close();
		} catch (Exception e) {
			System.out.println("Cannot execute the query.");
			System.err.println(e.getMessage());
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
	
	public String browseTHCategory(int sortBy, String uCategory, Statement statement){
		
		String mysql = "";
		
		if(sortBy == 1){
			// Price
			mysql = "SELECT distinct th.hid, th.category, th.url, th.haddress, th.hname, th.hphone, th.yr_built, ave_price.ave "
					+ "FROM (SELECT hid FROM th where category like '%" + uCategory + "%') "
					+ "AS th_names INNER JOIN (SELECT th.hid, avg(reserve.pricepernight) "
					+ "AS ave FROM visit LEFT JOIN reserve ON visit.vrid = reserve.rid "
					+ "LEFT Join th ON th.hid = reserve.rhid group by hname, hid) "
					+ "AS ave_price ON th_names.hid = ave_price.hid LEFT JOIN th ON th.hid = th_names.hid "
					+ "order by ave desc";
		}
		else if(sortBy == 2){
			// Avg Feedback
			mysql = "SELECT distinct th.hid, th.category, th.url, th.haddress, th.hname, th.hphone, th.yr_built, ave_feeds.feed_scores "
					+ "FROM (SELECT hid FROM th where category like '%" + uCategory + "%') "
					+ "AS th_names INNER JOIN (SELECT th.hid, avg(feedback.score) "
					+ "AS feed_scores FROM feedback LEFT Join th ON th.hid = feedback.fhid group by hname, hid) "
					+ "AS ave_feeds ON th_names.hid = ave_feeds.hid LEFT JOIN th ON th.hid = th_names.hid "
					+ "order by feed_scores desc";
		}
		else{
			// Trusted User
			mysql = "SELECT distinct th.hid, th.category, th.url, th.haddress, th.hname, th.hphone, th.yr_built, ave_trusted.trustcount "
					+ "FROM (SELECT hid FROM th where category like '%" + uCategory + "%') "
					+ "AS th_names INNER JOIN (select th.hid, trustcount from th "
					+ "left join (SELECT login, uname, SUM(case when is_trusted = 'trusted' then 1 end) - count(case when is_trusted = 'not-trusted' then 1 end) "
					+ "AS trustcount FROM trust LEFT JOIN users ON trust.login_trustornot = users.login group by login, uname) as x on x.login = th.hlogin) "
					+ "AS ave_trusted ON th_names.hid = ave_trusted.hid LEFT JOIN th ON th.hid = th_names.hid "
					+ "order by trustcount desc";
		}
		
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
						+ resultSet.getString("yr_built") + "   ";
				
				if(sortBy == 1){
					output += resultSet.getString("ave") + "\n";
				}
				else if(sortBy == 2){
					output += resultSet.getString("feed_scores") + "\n";
				}
				else{
					output += resultSet.getString("trustcount") + "\n";
				}
				
			}

			resultSet.close();
		} catch (Exception e) {
			System.out.println("Cannot execute the query.");
			System.err.println(e.getMessage());
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

	public String getPid(String name, Statement stmt) {
		String pid = "";
		String sql = "SELECT pid FROM POI " + "WHERE name ='" + name + "'";
		ResultSet rs = null;
		int count = 0;

		// System.out.println("executing "+sql);
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				pid = rs.getString("pid");
				count++;
			}
			// Check to see if query was empty
			if (count < 1) {
				return "";
			}
			rs.close();
		} catch (Exception e) {
			System.out
					.println("Database error. Please contact System Administrator");
			System.err.println(e.getMessage());
		} finally {
			try {
				if (rs != null && !rs.isClosed())
					rs.close();
			} catch (Exception e) {
				System.out.println("Can not close resultset");
			}
		}
		return pid;
	}

	public ArrayList<String> getCategories(Statement stmt) {
		ArrayList<String> categories = new ArrayList<String>();

		ResultSet rs = null;
		String sql = "SELECT category FROM POI" + " group by category";

		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				categories.add(rs.getString("category"));
			}
		}

		catch (Exception e) {
			System.out
					.println("Database error. Please contact System Administrator");
			System.err.println(e.getMessage());
		}
		return categories;
	}
	
	public String viewPopularTH(int uLimit, Statement statement) {
		  
		  ArrayList<String> categories = new ArrayList<String>();
		  String output = "";
		  ResultSet resultSet = null;

		  String mysql = "select distinct category from th";

		  try {
		    resultSet = statement.executeQuery(mysql);
		    while (resultSet.next()) {
		      categories.add(resultSet.getString("category"));
		    }
		  }

		  catch(Exception e) {
		      System.out.println("Cannot execute the query.");
		      System.err.println(e.getMessage());
		  }

		  for(String category : categories) {
		    mysql = "SELECT th.hid, th.category, th.url, th.haddress, th.hname, th.hphone, th.yr_built, "
		        + "count(*) AS visit_count FROM visit LEFT JOIN reserve ON visit.vrid = reserve.rid "
		        + "LEFT Join th ON th.hid = reserve.rhid WHERE th.category = '"+ category + "' group by "
		        + "th.hid, th.category, th.url, th.haddress, th.hname, "
		        + "th.hphone, th.yr_built order by th.category, visit_count desc LIMIT " + uLimit;

		    resultSet = null;

		    try {
		      resultSet = statement.executeQuery(mysql);
		      while (resultSet.next()) {
		        output += resultSet.getString("hid") + "   "
		            + resultSet.getString("category") + "   "
		            + resultSet.getString("url") + "   "
		            + resultSet.getString("haddress") + "   "
		            + resultSet.getString("hname") + "   "
		            + resultSet.getString("hphone") + "   "
		            + resultSet.getString("yr_built") + "   "
		            + resultSet.getString("visit_count") + "\n";
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
		        System.out.println("Cannot close ResultSet");
		      }
		    }
		  }
		  return output;
		}

		public String viewExpensiveTH(int uLimit, Statement statement) {

		  ArrayList<String> categories = new ArrayList<String>();
		  String output = "";
		  ResultSet resultSet = null;

		  String mysql = "select distinct category from th";

		  try {
		    resultSet = statement.executeQuery(mysql);
		    while (resultSet.next()) {
		      categories.add(resultSet.getString("category"));
		    }
		  }

		  catch(Exception e) {
		      System.out.println("Cannot execute the query.");
		      System.err.println(e.getMessage());
		  }

		  for(String category : categories) {
		    mysql = "SELECT th.hid, th.category, th.url, th.haddress, th.hname, th.hphone, th.yr_built, "
		        + "avg(reserve.pricepernight) AS ave FROM visit LEFT JOIN reserve ON visit.vrid = reserve.rid "
		        + "LEFT Join th ON th.hid = reserve.rhid WHERE th.category = '" + category + "' group by  th.hid, th.category, "
		        + "th.url, th.haddress, th.hname, th.hphone, th.yr_built order by th.category, ave desc LIMIT " + uLimit;

		    resultSet = null;

		    try {
		      resultSet = statement.executeQuery(mysql);
		      while (resultSet.next()) {
		        output += resultSet.getString("hid") + "   "
		            + resultSet.getString("category") + "   "
		            + resultSet.getString("url") + "   "
		            + resultSet.getString("haddress") + "   "
		            + resultSet.getString("hname") + "   "
		            + resultSet.getString("hphone") + "   "
		            + resultSet.getString("yr_built") + "   "
		            + resultSet.getString("ave") + "\n";
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
		        System.out.println("Cannot close ResultSet");
		      }
		    }
		  }
		  return output;
		}

		public String viewHighestRatedTH(int uLimit, Statement statement) {
		  
			ArrayList<String> categories = new ArrayList<String>();
		  String output = "";
		  ResultSet resultSet = null;

		  String mysql = "select distinct category from th";

		  try {
		    resultSet = statement.executeQuery(mysql);
		    while (resultSet.next()) {
		      categories.add(resultSet.getString("category"));
		    }
		  }

		  catch(Exception e) {
		      System.out.println("Cannot execute the query.");
		      System.err.println(e.getMessage());
		  }

		  for(String category : categories) {
		    mysql = "SELECT th.hid, th.category, th.url, th.haddress, th.hname, th.hphone, th.yr_built, avg(rate.rating) "
		        + "AS rating FROM rate LEFT JOIN feedback ON rate.rafid = feedback.fid LEFT Join th "
		        + "ON th.hid = feedback.fhid WHERE th.category = '" + category + "' group by th.hid, th.category, th.url, th.haddress, "
		        + "th.hname, th.hphone, th.yr_built order by th.category, rating desc LIMIT " + uLimit;

		    resultSet = null;

		    try {
		      resultSet = statement.executeQuery(mysql);
		      while (resultSet.next()) {
		        output += resultSet.getString("hid") + "   "
		            + resultSet.getString("category") + "   "
		            + resultSet.getString("url") + "   "
		            + resultSet.getString("haddress") + "   "
		            + resultSet.getString("hname") + "   "
		            + resultSet.getString("hphone") + "   "
		            + resultSet.getString("yr_built") + "   "
		            + resultSet.getString("rating") + "\n";
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
		        System.out.println("Cannot close ResultSet");
		      }
		    }
		  }
		  return output;
		}
	

	
}
