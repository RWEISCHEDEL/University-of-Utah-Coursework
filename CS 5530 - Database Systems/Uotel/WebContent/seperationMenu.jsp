<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<title>Separation Menu</title>
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
<h1>Separation Menu</h1>
<body>
<p>Hello, <%=session.getAttribute("username") %> <%=session.getAttribute("password") %> <%=session.getAttribute("usertype") %>!</p><br>
<form action="confirmViewDegree1.jsp">
	<input type="submit" value="View users who are one 1 Degree Of Separation from me." /><br>
</form>

<form action="confirmViewDegree2.jsp">
	<input type="submit" value="View users who are one 2 Degree Of Separation from me." /><br>
</form>
<a href="menu.jsp">Return to Previous Menu</a> <br>
</body>
</html>