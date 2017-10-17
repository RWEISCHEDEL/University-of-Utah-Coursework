<%@ page language="java" import="cs5530.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.Serializable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New Visit</title>
</head>
<body>
<h1>New Visit</h1>
<%	Connector connection = new Connector();

	VisitsUser f = new VisitsUser();
	boolean fEntered = f.newVisit(request.getParameter("rid"), request.getParameter("f_date"), request.getParameter("t_date"), connection.stmt);
	
	if (fEntered) {
		%> <p>You have successfully added a visit.</p> <%
	} else {%>
	<p>You have failed to add a visit! Please try again, it's possible that you don't have a reservation for this visit.</p>
	<%}
	connection.closeConnection();
	%>
<a href="visitsMenu.jsp">Return to Menu</a> <br>
</body>
</html>

