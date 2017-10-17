<%@ page language="java" import="cs5530.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
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
<head>
<title>Availability Menu</title>
</head>
<h1>TH Availability Menu</h1>
<body>
<p>Hello, <%=session.getAttribute("username") %> <%=session.getAttribute("password") %> <%=session.getAttribute("usertype") %>!</p><br>
<form action="confirmMakePeriod.jsp">
	<h4>Add a new Period</h4>
	<p>Enter the from date for the period. Enter t dates in the following format: YYYY-MM-DD: Example: 2017-03-25</p>
	<input type="text" name="af_date" placeholder="YYYY-MM-DD"><br>
	<p>Enter the to date for the period. Enter t dates in the following format: YYYY-MM-DD: Example: 2017-03-25</p>
	<input type="text" name="at_date" placeholder="YYYY-MM-DD"><br>
	<input type="submit"/><br>
</form>

<form action="confirmListPeriods.jsp">
	<h4>All periods</h4>
	<input type="submit" value="List all periods." /><br>
</form>

<form action="confirmDeletePeriod.jsp">
	<h4>Delete a period.</h4>
	<p>Enter period pid to delete. You can get the pid from browsing periods, column 1.</p>
	<input type="text" name="d_pid" placeholder="pid">
	<input type="submit"/><br>
</form>

<form action="confirmAddAvailability.jsp">
	<h4>Add a new Availability for a TH you own</h4>
	<%Connector connection = new Connector();

	TH th = new TH();
	
	String ownedTH = th.ownedTH(session.getAttribute("username").toString(), connection.stmt);
	
	String[] ownedTHArray = ownedTH.split("\n");
	
	if (ownedTH.isEmpty()) {
		%> <p>You don't own any Temporary Housings at this time.</p> <%
	} else {%>
	<h5>All your owned Temporary Housings:</h5>
	<% for(int i = 0; i < ownedTHArray.length; i++){ %>
		<h5><%=ownedTHArray[i]%></h5><br> <%
	}%>
	<%} 
	connection.closeConnection(); 
	%>
	<p>Enter TH hid for this TH availability.</p>
	<input type="text" name="a_hid" placeholder="hid">
	<p>Enter period pid for this TH availability. You can get the pid from browsing Periods, column 1.</p>
	<input type="text" name="a_pid" placeholder="pid">
	<p>Enter the price for this availability without extra characters. Example: For $100.00, just enter 100</p>
	<input type="text" name="a_price" placeholder="price"><br>
	<input type="submit"/><br>
</form>

<form action="confirmListAvailabilities.jsp">
	<h4>View all Availabilities For a TH</h4>
	<p>Enter TH hid. You can get the hid from browsing THs, column 1.</p>
	<input type="text" name="v_hid" placeholder="hid">
	<input type="submit"/><br>
</form>

<form action="confirmDeleteAvailability.jsp">
	<h4>Delete the Availability of a TH</h4>
	<p>Enter the TH hid that you own. You can get the hid from the list of your th's above.</p>
	<input type="text" name="de_hid" placeholder="hid"><br>
	<p>Enter period pid for this TH availability. You can get the pid from browsing the availabilities of your TH, column 3.</p>
	<input type="text" name="de_pid" placeholder="pid">
	<input type="submit"/><br>
</form>

<a href="menu.jsp">Return to Previous Menu</a> <br>
</body>
</html>