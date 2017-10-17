<%@ page language="java" import="cs5530.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.Serializable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Confirm Login</title>
</head>
<body>
<%	Connector connection = new Connector();
	LoginUser login = new LoginUser();
%>
<% 
	class User implements Serializable {
		public String username;
		public String password;
		public String usertype;
		public User(String _username, String _password, String _usertype) {
			username = _username;
			password = _password;
			usertype = _usertype;
		}
		
		public String toString() {
			return "username: " + username + " password: " + password;
		}
		
		public String getUsername() {
			return username;
		}
		
		public String getPassword() {
			return password;
		}
		
		public String getUserType() {
			return usertype;
		}
	}

	String loginReturnString = login.validateUserLogin(request.getParameter("username"), request.getParameter("password"), connection.stmt);
	
	if (!(loginReturnString.equals("uPassInvalid") || loginReturnString.equals("uNameInvalid") || loginReturnString.isEmpty())) {
		User user = new User(request.getParameter("username"), request.getParameter("password"), loginReturnString);
		session.setAttribute("user", user);
		session.setAttribute("username", user.getUsername());
		session.setAttribute("password", user.getPassword());
		session.setAttribute("usertype", user.getUserType());
		%>
		<META http-equiv="refresh" content="0; url=menu.jsp" />

	<%} else {%>
	<META http-equiv="refresh" content="0; url=loginFail.jsp" />
	<%} 
	connection.closeConnection(); %>

