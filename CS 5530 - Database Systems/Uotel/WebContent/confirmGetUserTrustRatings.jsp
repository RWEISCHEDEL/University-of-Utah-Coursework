<%@ page language="java" import="cs5530.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.Serializable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Trust Ratings of User</title>
</head>
<body>
<h1>Trust Ratings of User</h1>
<%	Connector connection = new Connector();
	TrustUser f = new TrustUser();
	String fEntered = f.viewUserTrust(request.getParameter("u_username"), connection.stmt);
	
	String[] aw = fEntered.split("\n");
	
	if (!fEntered.isEmpty()) {
		for(int i = 0; i < aw.length; i++){ %>
		<p><%=aw[i]%></p><br> <%
	}%>
	<%} else {%>
		out.println("Feedbacks could not be retrieved. It is possible this TH doesn't have any.");
	<%} %>
<a href="trustMenu.jsp">Return to Menu</a> <br>
</body>
</html>

