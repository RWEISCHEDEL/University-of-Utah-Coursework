<%@ page language="java" import="cs5530.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.Serializable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Rate Feedback</title>
</head>
<body>
<h1>Rate Feedback</h1>
<%	Connector connection = new Connector();

	FeedbackUser f = new FeedbackUser();
	System.out.println(request.getParameter("n_thid"));
	boolean fEntered = f.rateUserFeedback(session.getAttribute("username").toString(), request.getParameter("ru_thid"), request.getParameter("ru_rating"), connection.stmt);
	
	if (fEntered) {
		%> <p>You have successfully added feedback.</p> <%
	} else {%>
	<p>You have failed to add feedback! Please try again.</p>
	<%}
	connection.closeConnection();
	%>
<a href="feedbackMenu.jsp">Return to Menu</a> <br>
</body>
</html>

