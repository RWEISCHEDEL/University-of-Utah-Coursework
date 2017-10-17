<%@ page language="java" import="cs5530.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.Serializable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New Feedback</title>
</head>
<body>
<h1>New Feedback</h1>
<%	Connector connection = new Connector();

	FeedbackUser f = new FeedbackUser();
	System.out.println(request.getParameter("n_thid"));
	boolean fEntered = f.newFeedback(request.getParameter("n_thid"), session.getAttribute("username").toString(), request.getParameter("n_score"), request.getParameter("n_comment"), connection.stmt);
	
	if (fEntered) {
		%> <p>You have successfully added feedback.</p> <%
	} else {%>
	<p>You have failed to add feedback! Please try again.</p>
	<%}
	connection.closeConnection();
	%>
<a href="menu.jsp">Return to Menu</a> <br>
</body>
</html>

