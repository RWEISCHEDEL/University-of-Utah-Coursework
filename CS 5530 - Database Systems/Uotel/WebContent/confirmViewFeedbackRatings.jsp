<%@ page language="java" import="cs5530.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.Serializable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New Feedback</title>
</head>
<body>
<h1>Ratings of this feedback</h1>
<%	Connector connection = new Connector();
	FeedbackUser f = new FeedbackUser();
	String fEntered = f.viewRatingForFeedback(request.getParameter("r_thid"), connection.stmt);
	
	String[] aw = fEntered.split("\n");
	
	if (!fEntered.isEmpty()) {
		for(int i = 0; i < aw.length; i++){ %>
		<p><%=aw[i]%></p><br> <%
	}%>
	<%} else {%>
		out.println("Ratings could not be retrieved. It is possible this feedback id doesn't exist.");
	<%} %>
<a href="feedbackMenu.jsp">Return to Menu</a> <br>
</body>
</html>

