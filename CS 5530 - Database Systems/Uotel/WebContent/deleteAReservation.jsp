<%@ page language="java" import="cs5530.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Delete a Owned Temporary Housing</title>
</head>
<body>
<style>
input[type=submit] {
    padding:5px 15px; 
    background:#ccc; 
    border:0 none;
    cursor:pointer;
    -webkit-border-radius: 5px;
    border-radius: 5px; 
}
input[type=text] {
    padding:5px; 
    border:2px solid #ccc; 
    -webkit-border-radius: 5px;
    border-radius: 5px;
}
input[type=text]:focus {
    border-color:#333;
}
</style>

<h1>Please enter the information necessary to Delete a Reservation:</h1>

<form action="confirmDeleteAReservation.jsp">
	<p>You can get the rid from viewing your Reservations, column 2. </p>
	<p>Enter the Reservation rid: </p><br>
	<input type="text" name="rid" placeholder="Reservation RID"><br>
	<input type="submit" /><br>
</form>



<a href="menu.jsp">Return to Main Menu</a> <br>

</body>
</html>