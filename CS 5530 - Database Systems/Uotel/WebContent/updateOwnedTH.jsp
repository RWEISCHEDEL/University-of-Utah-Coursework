<%@ page language="java" import="cs5530.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update a Owned Temporary Housing</title>
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


<h1>Please enter the information necessary to update a owned Temporary Housing:</h1>

<form action="confirmUpdateOwnedTH.jsp">
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
	<p>Enter TH hid to update: </p><br>
	<input type="text" name="thhid" placeholder="TH HID"><br>
	<p>Choose field to update: (Enter the desired number)</p>
	<p>1. TH Category</p>
	<p>2. TH Url</p>
	<p>3. TH Address</p>
	<p>4. TH Name</p>
	<p>5. TH Phone</p>
	<input type="text" name="thupdatecategory" placeholder="TH Update Category"><br>
	<p>Enter new information:</p> <br>
	<input type="text" name="thnewinfo" placeholder="TH New Info"><br>
	<input type="submit" /><br>
</form>



<a href="menu.jsp">Return to Main Menu</a> <br>

</body>
</html>