package explosion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/users")
public class Users {

	private String topOfPage = "<!DOCTYPE html>\n<html>\n<head>\n"
			+ "<style>\n"
			+ "a:link, a:visited {color:black; text-decoration: none}\n"
			+ "a:hover, a:active {text-decoration: underline}\n"
			+ "</style>\n"
			+ "</head>\n<body>\n"
			+ "<table>\n";

	private String bottomOfPage= "</table>\n</body>\n</html>";
	
	
	@Path("/tavg")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String usersTotalAvg() {
		StringBuilder page = new StringBuilder(topOfPage);
		page.append("<tr><th>User</th><th>Avg Total Score</th></tr>\n");
		
		DBConnection con = new DBConnection();
		con.openConnection();

		try(Statement stmt = con.createStatement()){
			ResultSet results = stmt.executeQuery("SELECT uname, AVG(score) FROM games GROUP BY uname;");

			while(results.next()){
				String uname = results.getString("uname");
				String score = results.getString("avg(score)");
				page.append("<tr><td>"+ uname +"</td>"
						+ "<td>"+ score +"</td></tr>\n");
			}

		} catch (SQLException e) {
			page.append("Error: " + e.getMessage());
		} finally {
			con.closeConnection();
		}
		
		page.append(bottomOfPage);
		return page.toString();
	}
	
	
	@Path("/tgames")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String usersTotalGames() {
		StringBuilder page = new StringBuilder(topOfPage);
		page.append("<tr><th>User</th><th>Total Games Played</th></tr>\n");
		
		DBConnection con = new DBConnection();
		con.openConnection();

		try(Statement stmt = con.createStatement()){
			ResultSet results = stmt.executeQuery("SELECT uname, count(*) FROM games GROUP BY uname;");

			while(results.next()){
				String uname = results.getString("uname");
				String count = results.getString("count(*)");
				page.append("<tr><td>"+ uname +"</td>"
						+ "<td>"+ count +"</td></tr>\n");
			}

		} catch (SQLException e) {
			page.append("Error: " + e.getMessage());
		} finally {
			con.closeConnection();
		}

		page.append(bottomOfPage);
		return page.toString();
	}
	
	@Path("/lavg")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String usersAverageByLevel() {
		StringBuilder page = new StringBuilder(topOfPage);
		page.append("<tr><th>User</th><th>Lvl 1</th><th>Lvl 2</th><th>Lvl 3</th>"
				+ "<th>Lvl 4</th><th>Lvl 5</th></tr>\n");
		
		DBConnection con = new DBConnection();
		con.openConnection();

		try(Statement stmt = con.createStatement()){

			ResultSet results = stmt.executeQuery("SELECT g1.uname, g1.level, avg(g1.score) " 
					+ "FROM games g1, games g2 " 
					+ "WHERE g1.uname=g2.uname AND g1.level=g2.level "
					+ "GROUP BY g1.uname, g1.level;");
			DecimalFormat df = new DecimalFormat("#.00");
			String current = null;
				
				
			while(results.next()){
				if(current == null){ //run once, start first row
					current = results.getString("uname");
					page.append("<tr><td>"+ current +"</td>");
				}
				
				String uname = results.getString("uname");
				String score = df.format(results.getDouble("avg(g1.score)"));
				 
				if(uname.equals(current)){ //keep appending scores if on the same user
					page.append("<td>"+ score +"</td>");
				}
				else { //close the last row, start a new row and append level 1 score
					current = uname;
					page.append("</tr>\n"
							+ "<tr><td>"+ current +"</td>"
							+ "<td>"+ score +"</td>");
				}
			}

		} catch (SQLException e) {
			page.append("Error: " + e.getMessage());
		} finally {
			con.closeConnection();
		}

		page.append(bottomOfPage);
		return page.toString();
	}
	
	@Path("/lvltot")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String usersTotalAttemptsPerLevel() {
		StringBuilder page = new StringBuilder(topOfPage);
		page.append("<tr><th>User</th><th>Lvl 1</th><th>Lvl 2</th><th>Lvl 3</th>"
				+ "<th>Lvl 4</th><th>Lvl 5</th></tr>\n");
		
		DBConnection con = new DBConnection();
		con.openConnection();

		try(Statement stmt = con.createStatement()){

			ResultSet results = stmt.executeQuery("SELECT uname, level, count(level) "
					+ "FROM games "
					+ "GROUP BY uname, level ");	
			String current = null;
				
			while(results.next()){
				if(current == null){ //run once, start first row
					current = results.getString("uname");
					page.append("<tr><td>"+ current +"</td>");
				}
				
				String uname = results.getString("uname");
				int level = results.getInt("level");
				 
				if(uname.equals(current)){ //keep appending scores if on the same user
					page.append("<td>"+ level +"</td>");
				}
				else { //close the last row, start a new row and append level 1 score
					current = uname;
					page.append("</tr>\n"
							+ "<tr><td>"+ current +"</td>"
							+ "<td>"+ level +"</td>");
				}
			}

		} catch (SQLException e) {
			page.append("Error: " + e.getMessage());
		} finally {
			con.closeConnection();
		}

		page.append(bottomOfPage);
		return page.toString();
	}
	
}
