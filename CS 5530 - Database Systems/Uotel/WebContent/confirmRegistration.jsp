<%@ page language="java" import="cs5530.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.Serializable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>
</head>
<body>
<%	Connector connection = new Connector();

	RegisterUser registerU = new RegisterUser();
	
	boolean registered = registerU.registerUser(request.getParameter("username"), request.getParameter("name"), request.getParameter("password"), request.getParameter("address"), request.getParameter("phonenumber"), request.getParameter("admin"), connection.stmt);
	
	if (registered) {
		%> <META http-equiv="refresh" content="0; url=registerSuccess.jsp" /> <%
	} else {%>
	<META http-equiv="refresh" content="0; url=registerFail.jsp" />
	<%} 
	connection.closeConnection(); 
	%>
