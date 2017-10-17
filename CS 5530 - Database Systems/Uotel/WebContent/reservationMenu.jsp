<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<title>Reservation Menu</title>
</head>
<h1>Reservation Menu</h1>
<body>
<p>Hello, <%=session.getAttribute("username") %> <%=session.getAttribute("password") %> <%=session.getAttribute("usertype") %>!</p><br>
<a href="addNewReservation.jsp">Add a new Reservation</a> <br>
<a href="viewYourReservations.jsp">View all your Reservations</a> <br>
<a href="deleteAReservation.jsp">Delete a Reservation</a> <br>
<a href="menu.jsp">Return to Previous Menu</a> <br>
</body>
</html>