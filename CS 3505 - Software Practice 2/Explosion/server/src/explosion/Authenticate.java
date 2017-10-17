package explosion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.json.Json;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


@Path("/auth")
public class Authenticate {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String login(@QueryParam("username") String uname,
			@QueryParam("password") String pword) {
		
		boolean isValid = false;
		String reason = "";
		boolean isAdmin = false;
		
		DBConnection con = new DBConnection();
		con.openConnection();
		
		try(Statement stmt = con.createStatement()){
			ResultSet results = stmt.executeQuery("SELECT * FROM users WHERE uname='" + uname + "';");
			
			if(!results.next()){
				reason = "Invalid Username";
			}
			else if(!results.getString("pword").equals(pword)){
				reason = "Invalid Password";
			}
			else{
				isValid = true;
				isAdmin = results.getBoolean("isAdmin");
			}
			
		} catch (SQLException e) {
			System.err.println("SQL Error creating a statement:");
			System.err.println(e.getMessage());
			//e.printStackTrace();
		} finally {
			con.closeConnection();
		}
		
		return Json.createObjectBuilder()
				.add("isValid", isValid)
				.add("reason", reason)
				.add("isAdmin", isAdmin)
				.build().toString();
	}
	
	@Path("/reg")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String register(@QueryParam("username") String uname,
			@QueryParam("password") String pword) {
		
		boolean isTaken = true;
		String reason = "";
		
		DBConnection con = new DBConnection();
		con.openConnection();
		
		try(PreparedStatement stmt = con.prepareStatement("INSERT INTO users (uname, pword) VALUES(?, ?);")){
			
			stmt.setString(1, uname);
			stmt.setString(2, pword);
			
			if(stmt.executeUpdate() == 1) {
				isTaken = false;
				reason = "User " + uname + " successfully added!";
			} 
		} catch (SQLException e) {
			reason = "User " + uname + " already exists!";
			System.err.println("SQL Error creating a statement:");
			System.err.println(e.getMessage());
			//e.printStackTrace();
		} finally {
			con.closeConnection();
		}
		
		return Json.createObjectBuilder()
				.add("isTaken", isTaken)
				.add("reason", reason)
				.build().toString();
	}
}
