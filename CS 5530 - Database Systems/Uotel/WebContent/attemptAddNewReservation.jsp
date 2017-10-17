<%@ page language="java" import="cs5530.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.Serializable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Confirm Adding of New TH</title>
</head>
<body>
<%	Connector connection = new Connector();
	
	ReservationUser reserve = new ReservationUser();
	
	if(!request.getParameter("confirm").equals("1")){
		%><META http-equiv="refresh" content="0; url=menu.jsp" /><%
	}
	
	boolean addedReservation = reserve.newReservation(session.getAttribute("username").toString(), session.getAttribute("arhid").toString(), session.getAttribute("arfdate").toString(), session.getAttribute("artdate").toString(), session.getAttribute("arprice").toString(), connection.stmt);
	
	if (addedReservation) {
		%> <p>You have successfully made your reservation!</p> <%
	} else {%>
	<p>You have failed to add a reservation! Please try again.</p>
	<%}  
	%>
	
	<p>Here is a list of other places you might want to stay:</p>
	
	<%
	
	String suggested = reserve.viewReservationSuggestion(session.getAttribute("username").toString(), session.getAttribute("arhid").toString(), connection.stmt);
	
	String[] suggestedArray = suggested.split("\n");
			
	if (suggested.isEmpty()) {
		%> <p>You don't own any Temporary Housings at this time.</p> <%
	} else {%>
		<p>Here are some suggestions:</p><br>
		<% for(int i = 0; i < suggestedArray.length; i++){ %>
			<p><%=suggestedArray[i]%></p><br> <%
		}%>
	<%} 
	
	connection.closeConnection();
	%>
	
	<a href="menu.jsp">Return to Main Menu</a> <br>

