<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert stockexchange and isin</title>
</head>
<body>
	<h1>Insert stockexchange and isin</h1>
	<form action="ShowStockPrice" method="post">
		<p>(no difference between registered and unregistered user yet, everyone gets the latest prices)<p>
		Stockexchange:<input type="text" name="selectedStockExchange"><br>
		Isin:<input type="text" name="selectedISIN"><br>
		<input type="submit" value="get price">
	</form>
	<a href="home.jsp">home</a>
</body>
</html>