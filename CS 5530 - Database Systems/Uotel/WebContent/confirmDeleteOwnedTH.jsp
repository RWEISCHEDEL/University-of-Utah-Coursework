<%@ page language="java" import="cs5530.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.Serializable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Confirm Deletion of New TH</title>
</head>
<body>
<%	Connector connection = new Connector();

	TH th = new TH();
	
	boolean deleteTH = th.deleteTH(session.getAttribute("username").toString(), request.getParameter("thhid"), connection.stmt);
	
	if (deleteTH) {
		%> <p>You have successfully delete your owned TH!</p> <%
	} else {%>
	<p>You have failed to delete your new owned TH! Please try again.</p>
	<%} 
	connection.closeConnection(); 
	%>
	
	<a href="menu.jsp">Return to Main Menu</a> <br>

