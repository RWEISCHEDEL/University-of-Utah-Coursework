<%@ page language="java" import="cs5530.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Browse Temporary Housing</title>
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

<h1>Please enter the information necessary to browse Temporary Housing by Location:</h1>

<form action="confirmBrowseTHLocation.jsp">
	<p>Please enter city or state (2 char state) to search by:</p>
	<input type="text" name="thsearchlocation" placeholder="TH Search Location"><br>
	<p>Choose how to sort the TH: (Enter the desired number)</p>
	<p>1. TH by Price</p>
	<p>2. Average Feedback Score</p>
	<p>3. Average Trusted User Score</p>
	<input type="text" name="thsortlocation" placeholder="TH Sort Location"><br>
	<input type="submit" /><br>
</form>



<a href="menu.jsp">Return to Main Menu</a> <br>

</body>
</html>