<%@ page language="java" import="cs5530.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.Serializable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Your Favorites</title>
</head>
<body>
<h1>Your Favorites</h1>
<%	Connector connection = new Connector();
	FavoriteUser f = new FavoriteUser();
	System.out.println(request.getParameter("username"));
	String fEntered = f.viewFavorites(session.getAttribute("username").toString(), connection.stmt);
	
	String[] aw = fEntered.split("\n");
	
	if (!fEntered.isEmpty()) {
		for(int i = 0; i < aw.length; i++){ %>
		<p><%=aw[i]%></p><br> <%
	}%>
	<%} else {%>
		<p>Favorites could not be retrieved. It is possible that you don't have any.</p>
	<%} %>
<a href="menu.jsp">Return to Menu</a> <br>
</body>
</html>

