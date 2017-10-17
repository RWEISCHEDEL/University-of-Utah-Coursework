<%@ page language="java" import="cs5530.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.Serializable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Periods</title>
</head>
<body>
<h1>Periods</h1>
<%	Connector connection = new Connector();
	AvailableTH f = new AvailableTH();
	String fEntered = f.viewPeriods(connection.stmt);
	
	String[] aw = fEntered.split("\n");
	
	if (!fEntered.isEmpty()) {
		for(int i = 0; i < aw.length; i++){ %>
		<p><%=aw[i]%></p><br> <%
	}%>
	<%} else {%>
		<p>Periods could not be retrieved.</p>
	<%} %>
<a href="ownedTHAvailabilityMenu.jsp">Return to Menu</a> <br>
</body>
</html>

