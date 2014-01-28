<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>

<%@ page import="at.jku.ce.juddi.*"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Select stock exchange</title>
</head>
<body>
	<!-- list all stockexchanges as radio-buttons -->
	<% UddiManager uddiManager = UddiManager.getInstance();
	   List<String> allBusinesses = uddiManager.getAllPublishedBusinesses();
	%>
	<form action="getStocks" method="post">
		<p>Please select one of the available stock exchanges:</p>
		<p>
		<%	
			String exchange;
		    for (Iterator<String> i = allBusinesses.iterator(); i.hasNext();) {
				exchange = i.next();
				out.println("<input type='radio' name='exchange' value='"+exchange+"'/>"+exchange+"<br/>");
			}
		%>
		</p>
		<input type="submit" value="trade stock">		
	</form>
	<p>
	<a href='home.jsp'>home</a>
</body>
</html>