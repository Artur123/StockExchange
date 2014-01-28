<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="at.jku.ce.juddi.*"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Select stockexchange</title>
</head>
<body>
	<% UddiManager uddiManager = UddiManager.getInstance();
	   List<String> allBusinesses = uddiManager.getAllPublishedBusinesses();
	%>
	<!-- all stockexchanges in combobox -->
	<form action="ListStocks" method="post">
		<p>Please select one of the available stock exchanges:</p>
		<p>
		<select name="selectedStockExchange" size="1">
		<%	
			String exchange;
		    for (Iterator<String> i = allBusinesses.iterator(); i.hasNext();) {
				exchange = i.next();
				out.println("<option value='"+exchange+"'/>"+exchange+"<br/>");
			}
		%>
		</select>
		</p>
		<input type="submit" value="list stocks">
	</form>
	<p>
	<a href='home.jsp'>home</a>
</body>
</html>