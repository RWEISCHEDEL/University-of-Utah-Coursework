<%@ page language="java" import="cs5530.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.Serializable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Confirm Adding of New TH</title>
</head>
<body>
<%	Connector connection = new Connector();

	TH th = new TH();
	
	String updateChoice = "";
	
	if(request.getParameter("thupdatecategory").equals("1")){
		updateChoice = "category";
	}
	else if(request.getParameter("thupdatecategory").equals("2")){
		updateChoice = "url";
	}
	else if(request.getParameter("thupdatecategory").equals("3")){
		updateChoice = "haddress";
	}
	else if(request.getParameter("thupdatecategory").equals("4")){
		updateChoice = "hname";
	}
	else if(request.getParameter("thupdatecategory").equals("5")){
		updateChoice = "hphone";
	}
	
	
	boolean updatedTH = th.updateTH(session.getAttribute("username").toString(), request.getParameter("thhid"), updateChoice, request.getParameter("thnewinfo"), connection.stmt);
	
	if (updatedTH) {
		%> <p>You have successfully updated your owned TH!</p> <%
	} else {%>
	<p>You have failed to add your new owned TH! Please try again.</p>
	<%} 
	connection.closeConnection(); 
	%>
	
	<a href="menu.jsp">Return to Main Menu</a> <br>

