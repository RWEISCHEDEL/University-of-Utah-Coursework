<%@ page language="java" import="cs5530.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<title>Feedback Menu</title>
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
<h1>Feedback Menu</h1>
<body><%//******************* CHECK LEAVING A FEEDBACK AND VIEWING TOP USEFUL FEEDBACKS************ %>
<p>Hello, <%=session.getAttribute("username") %> <%=session.getAttribute("password") %> <%=session.getAttribute("usertype") %>!</p><br>
<form action="confirmLeaveNewFeedback.jsp">
	<h4>New Feedback, it is required that you have stayed there.</h4>
	<p>Enter TH hid to leave a feedback for. You can get the hid from browsing THs, column 1.</p>
	<input type="text" name="n_thid" placeholder="hid"><br>
	<p>Enter the score you wish to give this TH: (Scale 1 -10)</p>
	<input type="text" name="n_score" placeholder="Score"><br>
	<p>Enter the feedback comment you wish to give this TH</p>
	<input type="text" name="n_comment" placeholder="Comment"><br>
	<input type="submit" /><br>
</form>
<form action="confirmViewAllFeedback.jsp">
	<h4>View feedbacks of a TH.</h4>
	<p>Enter TH hid to view all feedbacks of. You can get the hid from browsing THs, column 1.</p>
	<input type="text" name="v_thid" placeholder="hid"><br>
	<input type="submit" /><br>
</form>
<form action="confirmRateUserFeedback.jsp">
	<h4>Rate a user feedback on a TH.</h4>
	<p>Enter Feedback id to rate the user feedback of. You can get the feedback id from browsing all feedback of a given TH, column 1.</p>
	<input type="text" name="ru_thid" placeholder="fid"><br>
	<p>Enter desired rating of feedback as a integer, use the following scale: Scale: 0 = 'useless', 1 = 'useful', 2 = 'very-useful'</p>
	<input type="text" name="ru_rating" placeholder="rating"><br>
	<input type="submit" /><br>
</form>
<form action="confirmViewFeedbackRatings.jsp">
	<h4>View ratings of a feedback</h4>
	<p>Enter Feedback id to rate the user feedback of. You can get the feedback id from browsing all feedback of a given TH, column 1.</p>
	<input type="text" name="r_thid" placeholder="fid"><br>
	<input type="submit" /><br>
</form>

<form action="confirmViewTopUsefulFeedbacks.jsp">
	<h4>View topmost useful feedbacks of a th</h4>
	<p>Enter the hid of the TH you want to view the top feedbacks of. You can get the hid from browsing THs, column 1.</p>
	<input type="text" name="t_thid" placeholder="hid"><br>
	<p>Enter the limit of how many result you want as an integer.</p>
	<input type="text" name="t_limit" placeholder="limit"><br>
	<input type="submit" /><br>
</form>
<a href="menu.jsp">Return to Previous Menu</a> <br>
</body>
</html>