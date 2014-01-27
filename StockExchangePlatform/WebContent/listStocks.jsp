<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import="at.jku.ce.juddi.*"%>
<%@ page import="java.util.*"%>
<%@ page import="at.jku.ce.stockexchange.service.Stock"%>
<%@ page import="at.jku.ce.stockexchange.service.ExchangeServiceService" %>
<%@ page import="at.jku.ce.stockexchange.service.ExchangeService" %>
<%@ page import="java.net.URL" %>
<%@ page import="javax.xml.namespace.QName" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>List stocks</title>
</head>
<body>
	<%
	final QName SERVICE_NAME = new QName("http://service.stockexchange.ce.jku.at/", "ExchangeServiceService");
	String selectedStockExchange = request.getParameter("selectedStockExchange");
	
	UddiManager uddiManager = UddiManager.getInstance();
	
	String accessPoint = uddiManager.getPublishedAccessPointFor(selectedStockExchange);
	ExchangeServiceService ss = new ExchangeServiceService(new URL(accessPoint), SERVICE_NAME);
    ExchangeService port = ss.getExchangeServicePort();
    %>
    <ul>
	<%
	for(Stock s : port.getTradedStocks()){
    	out.println("<li>" + s.getName() + ";ISIN: " + s.getIsin() + "</li>");
    }
	%>
	</ul>
	<a href='Login'>Back</a>
</body>
</html>