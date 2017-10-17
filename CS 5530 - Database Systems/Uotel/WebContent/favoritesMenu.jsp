<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<title>Favorites Menu</title>
</head>
<h1>Favorites Menu</h1>
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
<p>Hello, <%=session.getAttribute("username") %> <%=session.getAttribute("password") %> <%=session.getAttribute("usertype") %>!</p><br>
<form action="confirmListFavorites.jsp">
	<h4>See your favorites</h4>
	<input type="submit" value="List all of your favorites." /><br>
</form>
<form action="confirmMakeFavorite.jsp">
	<h4>List an hid as your favorite.</h4>
	<p>Enter TH hid to make a personal favorite. You can get the hid from browsing THs, column 1.</p>
	<input type="text" name="hid" placeholder="hid"><br>
	<input type="submit"/><br>
</form>
<a href="menu.jsp">Return to Previous Menu</a> <br>
</body>
</html>