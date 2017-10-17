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

<h1>Please enter the information necessary to create a new owned Temporary Housing:</h1>

<form action="confirmAddNewTH.jsp">
	<p>Enter TH Name: </p>
	<input type="text" name="thname" placeholder="TH Name"> <br><br>
	<p>Enter TH full address. Example: 123 Uotel Street Midvale UT 84047 </p>
	<p>Ensure that you enter the state as it's two letter abreviation. Example: Utah = UT </p>
	<input type="text" name="thaddress" placeholder="TH Address"><br><br>
	<p>Enter TH URL Example: www.thdomain.com </p>
	<input type="text" name="thurl" placeholder="TH Url"><br><br>
	<p>Enter TH telephone with no spaces, or other characters. Example: 8011234567: </p>
	<input type="text" name="thphonenumber" placeholder="TH Phone Number"><br><br>
	<p>Enter year that TH was built. Example: 2017 </p>
	<input type="text" name="thyear" placeholder="TH Year"><br><br>
	<p>Enter single word for the TH category. Example: House, Apartment, Condo, ...</p>
	<input type="text" name="thcategory" placeholder="TH Category"><br><br>
	<input type="submit" /><br>
</form>

<a href="menu.jsp">Return to Previous Menu</a> <br>

</body>
</html>