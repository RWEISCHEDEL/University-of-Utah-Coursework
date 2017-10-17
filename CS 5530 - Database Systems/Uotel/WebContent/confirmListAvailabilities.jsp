<%@ page language="java" import="cs5530.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.Serializable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TH Availabilities</title>
</head>
<body>
<h1>TH Availabilities</h1>
<%	Connector connection = new Connector();
	AvailableTH f = new AvailableTH();
	System.out.println(request.getParameter("username"));
	String fEntered = f.viewAvailablityTH(request.getParameter("v_hid"), connection.stmt);
	String[] aw = fEntered.split("\n");
	%><h4>The pid is the third column, you can look up the dates by viewing the periods and looking at this pid in periods, column 1.</h4>
	<%
	if (!fEntered.isEmpty()) {
		for(int i = 0; i < aw.length; i++){ %>
		<p><%=aw[i]%></p><br> <%
	}%>
	<%} else {%>
		<p>Availabilities could not be retrieved. It is possible that you don't have any.</p>
	<%} %>
<a href="ownedTHAvailabilityMenu.jsp">Return to Menu</a> <br>
</body>
</html>

