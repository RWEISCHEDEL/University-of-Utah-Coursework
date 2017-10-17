<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<title>Trust Menu</title>
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
<h1>Trust Menu</h1>
<body>
<p>Hello, <%=session.getAttribute("username") %> <%=session.getAttribute("password") %> <%=session.getAttribute("usertype") %>!</p><br>
<form action="confirmRateUserTrusted.jsp">
	<h4>Rate User as Trusted/Non-Trusted</h4>
	<p>Enter the username of the user to rate as trusted or not. You can view their feedbacks in the feedback menu to get their usernames.</p>
	<input type="text" name="t_username" placeholder="username"><br>
	<p>Enter the trust rating for the user: Example: trusted or non-trusted (only enter one of these two options)</p>
	<input type="text" name="t_trust" placeholder="trusted or non-trusted"><br>
	<input type="submit" /><br>
</form>
<form action="confirmGetUserTrustRatings.jsp">
	<h4>Rate User as Trusted/Non-Trusted</h4>
	<p>Enter the username of the person you want to trust ratings of. You can view their feedbacks in the feedback menu to get their usernames.</p>
	<input type="text" name="u_username" placeholder="username"><br>
	<input type="submit" /><br>
</form>
<a href="menu.jsp">Return to Previous Menu</a> <br>
</body>
</html>