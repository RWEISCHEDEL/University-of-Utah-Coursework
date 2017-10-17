<%@ page language="java" import="cs5530.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.Serializable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Most Trusted Users</title>
</head>
<body>
<h1>Most Trusted Users</h1>
<%	Connector connection = new Connector();

	AdminAwards awards = new AdminAwards();
	
	String a = awards.viewMostTrustedUsers(Integer.parseInt(request.getParameter("trustedamount")), connection.stmt);
	
	String[] aw = a.split("\n");
	
	if (!a.isEmpty()) {
		for(int i = 0; i < aw.length; i++){ %>
		<p><%=aw[i]%></p><br> <%
	}%>
	<%} else {%>
		out.println("error");
	<%} 
	connection.closeConnection();
	%>
<a href="menu.jsp">Return to Menu</a> <br>
</body>
</html>

