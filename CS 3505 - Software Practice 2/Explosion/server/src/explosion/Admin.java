package explosion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/admin")
public class Admin {

	private String topOfPage = "<!DOCTYPE html>\n<html>\n<head>\n"
			+ "<style>\n"
			+ "a:link, a:visited {color:black; text-decoration: none}\n "
			+ "a:hover, a:active {text-decoration: underline}\n "
			+ "</style>\n"
			+ "</head>\n<body>\n"
			+ "<table>\n";

	private String bottomOfPage= "</table>\n</body>\n</html>";

	@Path("/users")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String listUsers() {
		StringBuilder page = new StringBuilder(topOfPage);
		buildUsersPageBody(page);
		page.append(bottomOfPage);
		return page.toString();
	}

	private void buildUsersPageBody(StringBuilder page) {
		DBConnection con = new DBConnection();
		con.openConnection();

		try(Statement stmt = con.createStatement()){
			ResultSet results = stmt.executeQuery("SELECT uname FROM users;");

			while(results.next()){
				String uname = results.getString("uname");
				page.append("<tr><td class=\"name\"><a href=\"http://www.pichunts.com:8080/Explosion/explosion/admin/details?username="+ uname +"\"><b>"+ uname +" </b></a></td>"
						+ "<td><a href=\"http://www.pichunts.com:8080/Explosion/explosion/admin/remove?username="+ uname +"\">Remove</a></td>"
						+ "<td><a href=\"http://www.pichunts.com:8080/Explosion/explosion/admin/promote?username="+ uname +"\">Make Admin</a></td></tr>");
			}

		} catch (SQLException e) {
			page.append("Error: " + e.getMessage());
		} finally {
			con.closeConnection();
		}
	}

	@Path("/details")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String userDetails(@QueryParam("username") String uname) {
		StringBuilder page = new StringBuilder(topOfPage);
		buildUserDetailsPageBody(page, uname);
		page.append(bottomOfPage);
		return page.toString();
	}
	
	private void buildUserDetailsPageBody(StringBuilder page, String uname) {
		DBConnection con = new DBConnection();
		con.openConnection();

		try(Statement stmt = con.createStatement()){
			ResultSet results = stmt.executeQuery("SELECT * FROM games WHERE uname='"+ uname +"';");
			
			//Table header
			page.append("<tr><th>User: "+ uname +"</th></tr>\n");
			
			//Column Headers
			page.append("<tr><th>Timestamp</th><th>Level</th><th>Score</th><th>Won</th></tr>\n");
			
			while(results.next()){
				Time datetime = results.getTime("datetime");
				String level = results.getString("level");
				int score = results.getInt("score");
				boolean won = results.getBoolean("won");
					
				page.append("<tr><td>"+ datetime +"</td>"
						+ "<td>"+ level +"</td>"
						+ "<td>"+ score +"</td>"
						+ "<td>"+ won +"</td></tr>\n");
			}

		} catch (SQLException e) {
			page.append("Error: " + e.getMessage());
		} finally {
			con.closeConnection();
		}
	}
	
	@Path("/remove")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String removeUser(@QueryParam("username") String uname) {
		StringBuilder page = new StringBuilder(topOfPage);
		
		DBConnection con = new DBConnection();
		con.openConnection();
		
		try(PreparedStatement stmt = con.prepareStatement("DELETE FROM users WHERE uname= ? ;")){
			
			
			stmt.setString(1, uname);
			//page.append(stmt.toString());

			if(stmt.executeUpdate() == 1) {
				page.append("<tr><td>"+ uname +" removed from users table</td></td>\n");
			}
			
			PreparedStatement pStmt = con.prepareStatement("DELETE FROM games WHERE uname = ? ;");
			pStmt.setString(1, uname);
			//page.append(pStmt.toString());
			
			if(pStmt.executeUpdate() >= 1) {
				page.append("<tr><td>"+ uname +" removed from games table</td></td>\n");
			}
			
		} catch (SQLException e) {
			page.append("Error: " + e.getMessage());
		} finally {
			con.closeConnection();
		}

		page.append(bottomOfPage);
		return page.toString();
	}
	
	@Path("/promote")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String promoteUser(@QueryParam("username") String uname) {
		StringBuilder page = new StringBuilder(topOfPage);
		
		DBConnection con = new DBConnection();
		con.openConnection();
		
		try(PreparedStatement stmt = con.prepareStatement("UPDATE users SET isAdmin=true WHERE uname= ?;")){
			
			
			stmt.setString(1, uname);
			page.append(stmt.toString());

			if(stmt.executeUpdate() == 1) {
				page.append("<tr><td>"+ uname +" is now an admin</td></td>\n");
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
