<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<title>Statistics Menu</title>
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
<h1>Statistics Menu</h1>
<body>
<p>Hello, <%=session.getAttribute("username") %> <%=session.getAttribute("password") %> <%=session.getAttribute("usertype") %>!</p><br>
<form action="confirmStatisticsPop.jsp">
	<p>Enter the limit of how many results you want as an integer.</p>
	<input type="text" name="p_limit" placeholder="limit"><br>
	<input type="submit" value="View Most Popular THs By Category" /><br>
</form>
<form action="confirmStatisticsExp.jsp">
	<p>Enter the limit of how many results you want as an integer.</p>
	<input type="text" name="e_limit" placeholder="limit"><br>
	<input type="submit" value="View Most Expensive THs By Category" /><br>
</form>
<form action="confirmStatisticsHig.jsp">
	<p>Enter the limit of how many results you want as an integer.</p>
	<input type="text" name="h_limit" placeholder="limit"><br>
	<input type="submit" value="View Most Highly Rated THs By Category" /><br>
</form>
<a href="menu.jsp">Return to Previous Menu</a> <br>
</body>
</html>