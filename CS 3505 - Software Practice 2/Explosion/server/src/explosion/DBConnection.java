package explosion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.Properties;


public class DBConnection {
	/**
	 * DB username
	 */
	private static final String userName = "pickles_cs3505";

	/**
	 * DB password
	 */
	private static final String password = "explosion_again!";

	/**
	 * DB url
	 */
	private static final String url = "jdbc:mysql://localhost/pickles_cs3505";

	/**
	 * DB connection
	 */
	private Connection con = null;
	
	/**
	 * Creates an SQL Statement
	 */
	public Statement createStatement() throws SQLException {
		return con.createStatement();
	}
	
	public PreparedStatement prepareStatement(String query) throws SQLException {
		return con.prepareStatement(query);
	}
	
	/**
	 * Establishes the DB connection
	 */
	public void openConnection() {
		Properties connectionProps = new Properties();
		connectionProps.put("user", userName);
		connectionProps.put("password", password);

		try {
			Class.forName ("com.mysql.jdbc.Driver").newInstance ();
			con = DriverManager.getConnection(url, connectionProps);
			System.out.println("Connected to database");
		} catch (Exception e) {
			System.err.println("Unable to connect to database: ");
			System.err.println(e.getMessage());
			//e.printStackTrace();
		} 

	}

	/**
	 * Closes the DB connection
	 */
	public void closeConnection() {
		try {
			con.close();
			System.out.println("Connection closed");
		} catch (SQLException e) {
			System.err.println("Unable to connect to database: ");
			System.err.println(e.getMessage());
			//e.printStackTrace();
		}

	}
}
