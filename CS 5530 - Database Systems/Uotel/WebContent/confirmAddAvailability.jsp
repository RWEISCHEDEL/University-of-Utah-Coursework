<%@ page language="java" import="cs5530.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.Serializable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New Availability</title>
</head>
<body>
<h1>New Availability</h1>
<%	Connector connection = new Connector();

	AvailableTH f = new AvailableTH();
	boolean fEntered = f.addAvailability(request.getParameter("a_hid"), request.getParameter("a_pid"), request.getParameter("a_price"), connection.stmt);
	
	if (fEntered) {
		%> <p>You have successfully added availability for your th.</p> <%
	} else {%>
	<p>You have failed to add availability! It's possible the pid or the th you entered doesn't exist.</p>
	<%}
	connection.closeConnection();
	%>
<a href="ownedTHAvailabilityMenu.jsp">Return to Menu</a> <br>
</body>
</html>

