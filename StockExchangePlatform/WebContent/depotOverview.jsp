<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="at.jku.ce.brokerplatform.StockDepotElement"%>
<%@ page import="at.jku.ce.brokerplatform.StockDepotManager"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Depot overview</title>
</head>
<body>
	<% String user = (String) request.getSession().getAttribute("user"); %>
	<h1>Depot for user <% out.println(user); %> </h1>
	<br>
	
	<table border="1">
		<tr>
			<th>Name</th>
			<th>ISIN</th>
			<th>StockExchange</th>
			<th>Quantity</th>
		</tr>
		<!-- Display a table with all stocks of user  -->
		<%
			ArrayList<StockDepotElement> list = StockDepotManager.getInstance().getStocks(user);
			if(list != null){
			    for (int i=0;i<list.size(); i++) {
					out.println("<tr><td>" + list.get(i).getName() + "</td>" + 
			    			"<td>" + list.get(i).getIsin() + "</td>" +
			    			"<td>" + list.get(i).getStockExchange() + "</td>" +
			    			"<td>" + list.get(i).getQuantity() + "</td>" +
			    			"<td><a href='TradeStock?selectedStockExchange=" + list.get(i).getStockExchange() + 
			    			"&selectedISIN=" + list.get(i).getIsin() + "'>trade</a></td>" +
			    			"</tr>");
				}
			}
		%>
	</table>
	
	<br>
	<a href="getExchanges.jsp">Trade stocks</a><p>
	<a href='home.jsp'>Home</a>
</body>
</html>