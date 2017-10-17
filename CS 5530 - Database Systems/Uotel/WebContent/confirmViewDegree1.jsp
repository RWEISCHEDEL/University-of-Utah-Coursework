<%@ page language="java" import="cs5530.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.Serializable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>1 Degree</title>
</head>
<body>
<h1>One degree of separation users</h1>
<%	Connector connection = new Connector();
	SeperationUsers f = new SeperationUsers();
	System.out.println(request.getParameter("username"));
	String fEntered = f.viewSeperation1(session.getAttribute("username").toString(), connection.stmt);
	
	String[] aw = fEntered.split("\n");
	
	if (!fEntered.isEmpty()) {
		for(int i = 0; i < aw.length; i++){ %>
		<p><%=aw[i]%></p><br> <%
	}%>
	<%} else {%>
		<p>Users could not be retrieved. It is possible this user doesn't have reservations.</p>
	<%} %>
<a href="menu.jsp">Return to Menu</a> <br>
</body>
</html>

