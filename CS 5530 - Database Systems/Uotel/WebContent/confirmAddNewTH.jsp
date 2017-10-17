<%@ page language="java" import="cs5530.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.Serializable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Confirm Adding of New TH</title>
</head>
<body>
<%	Connector connection = new Connector();

	TH th = new TH();
	
	boolean addedTH = th.newTH(session.getAttribute("username").toString(), request.getParameter("thcategory"), request.getParameter("thurl"), request.getParameter("thaddress"), request.getParameter("thname"), request.getParameter("thphonenumber"), request.getParameter("thyear"), connection.stmt);
	
	if (addedTH) {
		%> <p>You have successfully added your new owned TH!</p> <%
	} else {%>
	<p>You have failed to add your new owned TH! Please try again.</p>
	<%} 
	connection.closeConnection(); 
	%>
	
	<a href="menu.jsp">Return to Main Menu</a> <br>

