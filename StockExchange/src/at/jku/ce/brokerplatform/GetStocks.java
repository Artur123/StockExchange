package at.jku.ce.brokerplatform;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;

import at.jku.ce.juddi.UddiManager;
import at.jku.ce.stockexchange.service.ExchangeService;
import at.jku.ce.stockexchange.service.ExchangeServiceService;
import at.jku.ce.stockexchange.service.Stock;

/**
 * Servlet implementation class GetStocks
 */
public class GetStocks extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private static final QName SERVICE_NAME = new QName("http://service.stockexchange.ce.jku.at/", "ExchangeServiceService");
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetStocks() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String selectedStockExchange = request.getParameter("exchange");
		PrintWriter out = response.getWriter();
		
		//generate HTML header
		generateHTMLHeader(out);
		out.println("<h1>" + selectedStockExchange + "</h1>");
		
		//get all stocks of selected stock exchange and output them to a list
		UddiManager uddiManager = UddiManager.getInstance();
		
		String accessPoint = uddiManager.getPublishedAccessPointFor(selectedStockExchange);
		ExchangeServiceService ss = new ExchangeServiceService(new URL(accessPoint), SERVICE_NAME);
		ExchangeService port = ss.getExchangeServicePort();
		
		out.println("<ul>");
		for(Stock s : port.getTradedStocks()) {
			out.println("<li>" + s.getName() + "</li>");
		}
		out.println("</ul>");
		generateHTMLFooter(out);
		
	}
	
	private void generateHTMLHeader(PrintWriter out) {
		out.println("<html><head></head><body>");
	}
	
	private void generateHTMLFooter(PrintWriter out) {
		out.println("</body></html>");
	}

}
