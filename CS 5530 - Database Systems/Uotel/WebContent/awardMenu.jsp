<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<title>Award Menu</title>
</head>
<h1>Award Menu</h1>
<body>
<p>Hello, <%=session.getAttribute("username") %> <%=session.getAttribute("password") %> <%=session.getAttribute("usertype") %>!</p><br>
<h1>To view the users who deserve awards, look below and enter the required information.</h1>
<form action="confirmAwardMenu.jsp">
	<p>Enter a amount of users to display and click submit to get most trusted users</p>
	<input type="text" name="trustedamount" placeholder="Amount to display"><br>
	<input type="submit" /><br>
</form>
<form action="confirmAwardMenuUseful.jsp">
	<p>Enter a amount of users to display and click submit to get most useful users</p>
	<input type="text" name="usefulamount" placeholder="Amount to display"><br>
	<input type="submit" /><br>
</form>
<a href="menu.jsp">Return to Previous Menu</a> <br>
</body>
</html>