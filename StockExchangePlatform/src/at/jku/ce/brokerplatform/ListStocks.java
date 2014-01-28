package at.jku.ce.brokerplatform;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;

import at.jku.ce.juddi.UddiManager;
import at.jku.ce.stockexchange.service.ExchangeService;
import at.jku.ce.stockexchange.service.ExchangeServiceService;
import at.jku.ce.stockexchange.service.Stock;

/**
 * Servlet implementation class ListStocks
 */
public class ListStocks extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	final QName SERVICE_NAME = new QName("http://service.stockexchange.ce.jku.at/", "ExchangeServiceService");
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListStocks() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String selectedStockExchange = request.getParameter("selectedStockExchange");
		
		UddiManager uddiManager = UddiManager.getInstance();
		
		String accessPoint = uddiManager.getPublishedAccessPointFor(selectedStockExchange);
		ExchangeServiceService ss = new ExchangeServiceService(new URL(accessPoint), SERVICE_NAME);
	    ExchangeService port = ss.getExchangeServicePort();
	    
	    PrintWriter out = response.getWriter();
	    
	    //print header
	    out.println(HTMLHelper.generateHTMLHeader());
	    out.println("<h1>All stocks for " + selectedStockExchange + "</h1>");
	    
	    //print all stocks in unordered list with name, isin and availability
	    out.println("<ul>");
		for(Stock s : port.getTradedStocks()){
	    	out.println("<li>" + s.getName() + ";ISIN: " + s.getIsin() + ";Availability: " + s.getAvailability() + "</li>");
	    }
		
		out.println("</ul><p><a href='home.jsp'>home</a>");
		
		//print footer
		out.println(HTMLHelper.generateHTMLFooter());
		out.close();
	}

}
