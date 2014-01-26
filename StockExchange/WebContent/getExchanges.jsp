<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="at.jku.ce.juddi.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Available Stock Exchanges</title>
</head>
<body>
<% 	UddiManager uddiManager = UddiManager.getInstance();
	List<String> allBusinesses = uddiManager.getAllPublishedBusinesses(); %>

<form action="GetStocks" method="post">
	<p>Please select one of the available stock exchanges:</p>
	<p>
	<% for (String exchange : allBusinesses) {
			out.println("<input type='radio' name='exchange' value='"+exchange+"'/>"+exchange+"<br/>");
		}
	%>
	</p>
	<input type="submit" value="Get Stocks"/>
</form>

</body>
</html>