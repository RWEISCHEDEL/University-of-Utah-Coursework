<%@ page language="java" import="cs5530.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.Serializable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Expensive THs</title>
</head>
<body>
<h1>Expensive THs by category</h1>
<%	Connector connection = new Connector();
	TH f = new TH();
	String fEntered = f.viewExpensiveTH(Integer.parseInt(request.getParameter("e_limit")), connection.stmt);
	
	String[] aw = fEntered.split("\n");
	
	if (!fEntered.isEmpty()) {
		for(int i = 0; i < aw.length; i++){ %>
		<p><%=aw[i]%></p><br> <%
	}%>
	<%} else {%>
		<p>THs could not be received.</p>
	<%} %>
<a href="statisticsMenu.jsp">Return to Menu</a> <br>
</body>
</html>

