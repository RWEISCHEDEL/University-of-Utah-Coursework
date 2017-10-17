<%@ page language="java" import="cs5530.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.Serializable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>THs</title>
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
<h1>Your THs</h1>
<%	Connector connection = new Connector();

	TH th = new TH();
	
	String ownedTH = th.ownedTH(session.getAttribute("username").toString(), connection.stmt);
	
	String[] ownedTHArray = ownedTH.split("\n");
	
	if (ownedTH.isEmpty()) {
		%> <p>You don't own any Temporary Housings at this time.</p> <%
	} else {%>
	<p>All your owned Temporary Housings:</p><br>
	<% for(int i = 0; i < ownedTHArray.length; i++){ %>
		<p><%=ownedTHArray[i]%></p><br> <%
	}%>
	<%} 
	connection.closeConnection(); 
	%>
	
	<a href="menu.jsp">Return to Main Menu</a> <br>

