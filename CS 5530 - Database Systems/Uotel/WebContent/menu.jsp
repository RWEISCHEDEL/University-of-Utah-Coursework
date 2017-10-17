<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<title>Uotel Menu</title>
</head>
<h1>Main Menu</h1>
<body>
<p>Hello, <%=session.getAttribute("username") %> <%=session.getAttribute("password") %> <%=session.getAttribute("usertype") %>!</p><br>
<a href="viewOwnedTHs.jsp">View owned temporary housing</a> <br>
<a href="addNewTH.jsp">Add a new owned temporary housing</a> <br>
<a href="updateOwnedTH.jsp">Update owned temporary housing</a> <br>
<a href="deleteOwnedTH.jsp">Delete owned temporary housing</a> <br>
<a href="browseTH.jsp">Browse temporary housing</a> <br>
<a href="reservationMenu.jsp">Reservations of a TH</a> <br>
<a href="ownedTHAvailabilityMenu.jsp">Temporary Housing Availability and Periods</a> <br>
<a href="visitsMenu.jsp">Visits</a> <br>
<a href="favoritesMenu.jsp">Favorites</a> <br>
<a href="trustMenu.jsp">Trust</a> <br>
<a href="statisticsMenu.jsp">Statistics</a> <br>
<a href="seperationMenu.jsp">Show Degrees of Separation</a> <br>
<a href="feedbackMenu.jsp">Feedback</a> <br>
<% if (session.getAttribute("usertype").equals("1")) {%>
	<a href="awardMenu.jsp">User Awards</a> <br>
<%}%>
</body>
</html>