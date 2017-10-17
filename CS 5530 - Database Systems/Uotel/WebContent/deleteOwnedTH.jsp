<%@ page language="java" import="cs5530.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Delete a Owned Temporary Housing</title>
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
<h1>Please enter the information necessary to Delete a owned Temporary Housing:</h1>

<form action="confirmDeleteOwnedTH.jsp">
	<p>All your owned Temporary Housings:</p><br>
	<%
		Connector connection = new Connector();
	
		TH th = new TH();
		
		String ownedTH = th.ownedTH(session.getAttribute("username").toString(), connection.stmt);
		
		String[] ownedTHArray = ownedTH.split("\n");
		
		for(int i = 0; i < ownedTHArray.length; i++){ %>
		<p><%=ownedTHArray[i]%></p> <%
		}
		
		connection.closeConnection();
		
	%>
	<p>You can get the hid from viewing column 1. </p>
	<p>Enter TH hid to delete: </p><br>
	<input type="text" name="thhid" placeholder="TH HID"><br>
	<input type="submit" /><br>
</form>



<a href="menu.jsp">Return to Main Menu</a> <br>

</body>
</html>