<%@ page language="java" import="cs5530.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.Serializable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Browse Temporary Housing By Price</title>
</head>
<body>
<%	Connector connection = new Connector();

	TH th = new TH();
	
	int sortValue = 0;
	
	if(request.getParameter("thsortprice").equals("1")){
		sortValue = 1;
	}
	else if(request.getParameter("thsortprice").equals("2")){
		sortValue = 2;
	}
	else if(request.getParameter("thsortprice").equals("3")){
		sortValue = 3;
	}
	
	int lowPrice = Integer.parseInt(request.getParameter("thlowprice"));
	
	int highPrice = Integer.parseInt(request.getParameter("thhighprice"));
	
	String priceSearch = th.browseTHPrice(sortValue, lowPrice, highPrice, connection.stmt);
	
	String[] priceSearchArray = priceSearch.split("\n");
	
	if (priceSearch.isEmpty()) {
		%> <p>No Temporary Housing Matches your description.</p> <%
	} else {%>
	<p>All matching Temporary Housings by Price:</p><br>
	<% for(int i = 0; i < priceSearchArray.length; i++){ %>
		<p><%=priceSearchArray[i]%></p><br> <%
	}%>
	<%}
	
	connection.closeConnection(); 
	%>
	
	<a href="menu.jsp">Return to Main Menu</a> <br>

