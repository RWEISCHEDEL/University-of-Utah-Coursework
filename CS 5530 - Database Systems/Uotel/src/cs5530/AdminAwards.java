package cs5530;

import java.sql.*;

public class AdminAwards {
	public AdminAwards() {}

	public String viewMostTrustedUsers(int uLimit, Statement statement) {
		
		String mysql =
		  "SELECT distinct login, users.uname,"
		+ " SUM(CASE WHEN is_trusted = 'trusted' THEN 1 END)"
		+ " - COUNT(CASE WHEN is_trusted = 'not-trusted' THEN 1 END)"
		+ " AS trustcount FROM trust LEFT JOIN users"
		+ " ON trust.login_trustornot = users.login GROUP BY login, uname"
		+ " ORDER BY trustcount DESC LIMIT " + uLimit;

		ResultSet resultSet = null;
		String output = "";

	 	try {
	 		resultSet = statement.executeQuery(mysql);
	 		while (resultSet.next()) {
	 			
	 			output += resultSet.getString("login") + "   "
						+ resultSet.getString("uname") + "   "
						+ resultSet.getString("trustcount") + "\n";
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

	public String viewMostUsefulUsers(int uLimit, Statement statement) {

		String mysql =
				"SELECT login, uname, round(avg(rate.rating), 2) AS average FROM rate"
				+" LEFT JOIN feedback ON rate.rafid = feedback.fid"
				+" LEFT JOIN users ON users.login = feedback.flogin"
				+" group by login, uname"
				+" order by average desc"
				+" LIMIT " + uLimit;

		ResultSet resultSet = null;
		String output = "";

	 	try {
	 		resultSet = statement.executeQuery(mysql);
	 		while (resultSet.next()) {
	 			
	 			output += resultSet.getString("login") + "   "
						+ resultSet.getString("uname") + "   "
						+ resultSet.getString("average") + "\n";
	 		}
	 		
	 	}
	 	catch(Exception e) {
	 		System.out.println("Cannot execute the query.");
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
}
