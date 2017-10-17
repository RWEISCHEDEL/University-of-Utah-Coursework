<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<title>Visits Menu</title>
</head>
<h1>Visits Menu</h1>
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
<p>Hello, <%=session.getAttribute("username") %> <%=session.getAttribute("password") %> <%=session.getAttribute("usertype") %>!</p><br>
<form action="confirmListVisits.jsp">
	<input type="submit" value="View all of your visits" /><br>
</form>
<form action="confirmMakeVisit.jsp">
	<h4>Record a Visit</h4>
	<p>Enter the Reservation rid for the reservation to record a visit of. You can get the rid from viewing your reservations, column 2.</p>
	<input type="text" name="rid" placeholder="rid"><br>
	<p>Enter the from date for the visit. Enter t dates in the following format: YYYY-MM-DD: Example: 2017-03-25</p>
	<input type="text" name="f_date" placeholder="YYYY-MM-DD"><br>
	<p>Enter the to date for the visit. Enter t dates in the following format: YYYY-MM-DD: Example: 2017-03-25</p>
	<input type="text" name="t_date" placeholder="YYYY-MM-DD"><br>
	<input type="submit"/><br>
</form>
<a href="menu.jsp">Return to Previous Menu</a> <br>
</body>
</html>