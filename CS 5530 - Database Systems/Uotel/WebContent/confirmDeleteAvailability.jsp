<%@ page language="java" import="cs5530.*"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"
	import="java.io.Serializable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Deletion of TH Availability</title>
</head>
<body>
	<h1>Deletion of TH Availability</h1>
	<%
		Connector connection = new Connector();

		AvailableTH th = new AvailableTH();

		boolean deleteTH = th.deleteAvailabilty(request.getParameter("de_hid"), request.getParameter("de_pid"),
				connection.stmt);

		if (deleteTH) {
	%>
	<p>You have successfully deleted this availability for your owned
		TH!</p>
	<%
		} else {
	%>
	<p>You have failed to delete your availability for your owned TH!
		Please try again.</p>
	<%
		}
		connection.closeConnection();
	%>

	<a href="ownedAvailabilityMenu.jsp">Return to Main Menu</a>
	<br>