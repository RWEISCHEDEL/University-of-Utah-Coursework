<%@ page language="java" import="cs5530.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.Serializable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Your Visits</title>
</head>
<body>
<h1>Your Visits</h1>
<%	Connector connection = new Connector();
	VisitsUser f = new VisitsUser();
	System.out.println(request.getParameter("username"));
	String fEntered = f.viewVisits(session.getAttribute("username").toString(), connection.stmt);
	
	String[] aw = fEntered.split("\n");
	
	if (!fEntered.isEmpty()) {
		for(int i = 0; i < aw.length; i++){ %>
		<p><%=aw[i]%></p><br> <%
	}%>
	<%} else {%>
		<p>Visits could not be retrieved. It is possible that you don't have any.</p>
	<%} %>
<a href="visitsMenu.jsp">Return to Menu</a> <br>
</body>
</html>

