<%@ page language="java" import="cs5530.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.Serializable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New Period</title>
</head>
<body>
<h1>New Period</h1>
<%	Connector connection = new Connector();

	AvailableTH f = new AvailableTH();
	boolean fEntered = f.newPeriod(request.getParameter("af_date"), request.getParameter("at_date"), connection.stmt);
	
	if (fEntered) {
		%> <p>You have successfully added a period.</p> <%
	} else {%>
	<p>You have failed to add a period!</p>
	<%}
	connection.closeConnection();
	%>
<a href="ownedTHAvailabilityMenu.jsp">Return to Menu</a> <br>
</body>
</html>

