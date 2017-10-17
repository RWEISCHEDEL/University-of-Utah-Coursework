<%@ page language="java" import="cs5530.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.Serializable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New Favorite</title>
</head>
<body>
<h1>New Favorite</h1>
<%	Connector connection = new Connector();

	FavoriteUser f = new FavoriteUser();
	System.out.println(request.getParameter("n_thid"));
	boolean fEntered = f.newFavorite(session.getAttribute("username").toString(), request.getParameter("hid"), connection.stmt);
	
	if (fEntered) {
		%> <p>You have successfully added a favorite.</p> <%
	} else {%>
	<p>You have failed to add favorite! Please try again.</p>
	<%}
	connection.closeConnection();
	%>
<a href="menu.jsp">Return to Menu</a> <br>
</body>
</html>

