package explosion;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.json.Json;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/score")
public class Scores {
	
	@GET
	public String recordScore(@QueryParam("username") String uname,
			@QueryParam("level") int level,
			@QueryParam("score") int score,
			@QueryParam("won") boolean won) {
	
		boolean isRecorded = false;
		String reason = "";
		
		DBConnection con = new DBConnection();
		con.openConnection();
		
		try(PreparedStatement stmt = con.prepareStatement(
				"INSERT INTO games (uname, level, score, won) VALUES( ?, ?, ?, ?); ")){
			
			stmt.setString(1, uname);
			stmt.setInt(2, level);
			stmt.setInt(3, score);
			stmt.setBoolean(4, won);
			
			if(stmt.executeUpdate() == 1) {
				isRecorded = true;
				reason = "Score for " + uname + " successfully recorded!";
			} 
		} catch (SQLException e) {
			reason = "Unable to record score for " + uname + "! Reason: " + e.getMessage();
		} finally {
			con.closeConnection();
		}
		
		return Json.createObjectBuilder()
				.add("isRecorded", isRecorded)
				.add("reason", reason)
				.build().toString();
	}
}
