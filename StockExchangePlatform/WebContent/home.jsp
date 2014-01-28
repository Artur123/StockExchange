<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>Stock exchange platform group L3</h1>
		
	<%//if user is unregistered, there is no depot
		String user = (String) session.getAttribute("user");
		if(user != ""){
			out.println("<p> <a href='depotOverview.jsp'>show depot</a>");
			out.println("<p> <a href='getExchanges.jsp'>trade stocks</a>");
		}else{
			out.println("unregistered user");
		}
	 %>
	<!-- link to list of stocks  -->
	<p> <a href='listStocksSelectStockExchange.jsp'>show stocks</a>
	
	<!-- link for select price for certain stock -->		
	<p> <a href='getStockPrice.jsp'>get price for stock</a>
</body>
</html>