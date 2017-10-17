<%@ page language="java" import="cs5530.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.Serializable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Confirm Login</title>
</head>
<body>
<%	Connector connection = new Connector();
	ReservationUser reserve = new ReservationUser();

	class AttemptReservation implements Serializable {
		public String thHid;
		public String fromDate;
		public String toDate;
		public String price;
		public AttemptReservation(String _thHid, String _fromDate, String _toDate, String _price) {
			thHid = _thHid;
			fromDate = _fromDate;
			toDate = _toDate;
			price =_price;
		}
		
		public String getTHHid() {
			return thHid;
		}
		
		public String getFromDate() {
			return fromDate;
		}
		
		public String getToDate() {
			return toDate;
		}
		
		public String getPrice() {
			return price;
		}
	}
	
	AttemptReservation attempt = new AttemptReservation(request.getParameter("thhid"), request.getParameter("fromdate"), request.getParameter("todate"), request.getParameter("rprice"));
	
	session.setAttribute("arhid", attempt.getTHHid());
	session.setAttribute("arfdate", attempt.getFromDate());
	session.setAttribute("artdate", attempt.getToDate());
	session.setAttribute("arprice", attempt.getPrice());
	
	connection.closeConnection();
%>

<h1>Are you sure you want to make this reservation?</h1>

<form action="attemptAddNewReservation.jsp">
	<p>To confirm type 1 for YES or 0 for NO.</p> <br>
	<input type="text" name="confirm" placeholder="Confirm"><br>
	<input type="submit" /><br>
</form>

