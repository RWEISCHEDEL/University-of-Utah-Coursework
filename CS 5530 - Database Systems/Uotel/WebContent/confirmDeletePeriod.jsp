<%@ page language="java" import="cs5530.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.Serializable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Delete Period</title>
</head>
<body>
<h1>Delete Period</h1>
<%	Connector connection = new Connector();

	AvailableTH f = new AvailableTH();
	boolean fEntered = f.deletePeriod(request.getParameter("d_pid"), connection.stmt);
	
	if (fEntered) {
		%> <p>You have successfully deleted a period.</p> <%
	} else {%>
	<p>You have failed to delete a period!</p>
	<%}
	connection.closeConnection();
	%>
<a href="ownedTHAvailabilityMenu.jsp">Return to Menu</a> <br>
</body>
</html>

