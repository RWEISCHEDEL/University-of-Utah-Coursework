<%@ page language="java" import="cs5530.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.Serializable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>
</head>
<body>
<%	Connector connection = new Connector();

	ReservationUser reserve = new ReservationUser();
	
	String reservations = reserve.viewReservations(session.getAttribute("username").toString(), connection.stmt);
	
	String[] reservationsArray = reservations.split("\n");
	
	if (reservations.isEmpty()) {
		%> <p>You haven't made any reservations at this time.</p> <%
	} else {%>
	<p>All reservations you have made: (The RID is the second value)</p><br>
	<% for(int i = 0; i < reservationsArray.length; i++){ %>
		<p><%=reservationsArray[i]%></p><br> <%
	}%>
	<%} 
	connection.closeConnection(); 
	%>
	
	<a href="menu.jsp">Return to Main Menu</a> <br>

