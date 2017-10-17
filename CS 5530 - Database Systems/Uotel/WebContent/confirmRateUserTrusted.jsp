<%@ page language="java" import="cs5530.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.Serializable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Trust Rating</title>
</head>
<body>
<h1>Trust Ratings</h1>
<%	Connector connection = new Connector();

	TrustUser f = new TrustUser();
	System.out.println(request.getParameter("n_thid"));
	boolean fEntered = f.rateTrustUser(session.getAttribute("username").toString(), request.getParameter("t_username"), request.getParameter("t_trust"), connection.stmt);
	
	if (fEntered) {
		%> <p>You have successfully added a trust rating.</p> <%
	} else {%>
	<p>You have failed to add feedback! Please try again.</p>
	<%}
	connection.closeConnection();
	%>
<a href="trustMenu.jsp">Return to Menu</a> <br>
</body>
</html>

