<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration</title>
</head>
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
<body>

<h1>Please enter the information necessary to create a user.</h1>

<form action="confirmRegistration.jsp">
	<p>Enter your desired login: </p> <br>
	<input type="text" name="username" placeholder="username"><br>
	<p>Enter your first and last name: </p> <br>
	<input type="text" name="name" placeholder="first and last Name"><br>
	<p>Enter your desired password: </p> <br>
	<input type="text" name="password" placeholder="password"><br>
	<p>Enter your full address. Example: 123 Uotel Street Midvale UT 84047 </p> <br>
	<p>Ensure that you enter the state as it's two letter abbreviation. Example: Utah = UT"</p> <br>
	<input type="text" name="address" placeholder="address"><br>
	<p>Enter your telephone number with no spaces, or other characters. Example: 8011234567: </p> <br>
	<input type="text" name="phonenumber" placeholder="phone number"><br>
	<p>Enter your user type as 0 or 1. Example: admin (1) or user (0) (Be HONEST!)</p> <br>
	<input type="text" name="admin" placeholder="user type"><br>
	<input type="submit" /><br>
</form>

<a href="index.html">Return to Previous Menu</a> <br>

</body>
</html>