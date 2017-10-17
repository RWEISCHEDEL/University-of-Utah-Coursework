<%@ page language="java" import="cs5530.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.Serializable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Confirm Deletion of New TH</title>
</head>
<body>
<%	Connector connection = new Connector();

	ReservationUser reserve = new ReservationUser();
	
	boolean deleteRes = reserve.deleteReservation(request.getParameter("rid"), connection.stmt);
	
	if (deleteRes) {
		%> <p>You have successfully delete your reservation!</p> <%
	} else {%>
	<p>You have failed to delete your reservation! Please try again.</p>
	<%} 
	connection.closeConnection(); 
	%>
	
	<a href="menu.jsp">Return to Main Menu</a> <br>

