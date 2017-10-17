<%@ page language="java" import="cs5530.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.Serializable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Browse Temporary Housing By Location</title>
</head>
<body>
<%	Connector connection = new Connector();

	TH th = new TH();
	
	int sortValue = 0;
	
	if(request.getParameter("thsortlocation").equals("1")){
		sortValue = 1;
	}
	else if(request.getParameter("thsortlocation").equals("2")){
		sortValue = 2;
	}
	else if(request.getParameter("thsortlocation").equals("3")){
		sortValue = 3;
	}
	
	String locationSearch = th.browseTHCS(sortValue, request.getParameter("thsearchlocation"), connection.stmt);
	
	String[] locationSearchArray = locationSearch.split("\n");
	
	if (locationSearch.isEmpty()) {
		%> <p>No Temporary Housing Matches your description.</p> <%
	} else {%>
	<p>All matching Temporary Housings by Location:</p><br>
	<% for(int i = 0; i < locationSearchArray.length; i++){ %>
		<p><%=locationSearchArray[i]%></p><br> <%
	}%>
	<%}
	
	connection.closeConnection(); 
	%>
	
	<a href="menu.jsp">Return to Main Menu</a> <br>

