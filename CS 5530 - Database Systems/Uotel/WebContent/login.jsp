<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
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
input[type=text], input[type=password] {
    padding:5px; 
    border:2px solid #ccc; 
    -webkit-border-radius: 5px;
    border-radius: 5px;
}
input[type=text]:focus, , input[type=password]:focus {
    border-color:#333;
}
</style>
<h1>Please login to the Uotel system.</h1>

<form action="confirmLogin.jsp">
	<input type="text" name="username" placeholder="username"><br>
	<input type="password" name="password" placeholder="password"><br>
	<input type="submit" /><br>
	<p>A successful login will transfer you to our menu.</p>
</form>

<a href="index.html">Return to Previous Menu</a> <br>

</body>
</html>