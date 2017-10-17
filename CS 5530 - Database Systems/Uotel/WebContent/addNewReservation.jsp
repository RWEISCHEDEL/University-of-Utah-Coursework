<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration</title>
</head>
<body>

<h1>Please enter the information necessary to add a new Reservation:</h1>

<form action="confirmAddNewReservation.jsp">
	<p>You can get the hid from browsing THs, column 1.</p> <br>
	<p>Enter the TH hid for the TH to reserve:</p> <br>
	<input type="text" name="thhid" placeholder="TH HID"><br>
	<p>Enter all dates in the following format: YYYY-MM-DD: Example: 2017-03-25</p> <br>
	<p>Enter the from date for the new reservation:</p> <br>
	<input type="text" name="fromdate" placeholder="From Date"><br>
	<p>Enter the to date for the new reservation:</p> <br>
	<input type="text" name="todate" placeholder="To Date"><br>
	<p>Enter the price for this availbility with not extra characters:</p> <br>
	<p>Example: For $100.00, just enter 100</p> <br>
	<input type="text" name="rprice" placeholder="Reservation Price"><br>
	<input type="submit" /><br>
</form>

<a href="menu.jsp">Return to Main Menu</a> <br>

</body>
</html>