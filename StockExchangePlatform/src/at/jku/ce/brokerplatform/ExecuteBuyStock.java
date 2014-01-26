package at.jku.ce.brokerplatform;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;

import at.jku.ce.juddi.UddiManager;
import at.jku.ce.stockexchange.service.Exchange;
import at.jku.ce.stockexchange.service.ExchangeService;
import at.jku.ce.stockexchange.service.ExchangeServiceService;

/**
 * Servlet implementation class ExecuteBuyStock
 */
public class ExecuteBuyStock extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private static final QName SERVICE_NAME = new QName("http://service.stockexchange.ce.jku.at/", "ExchangeServiceService");
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExecuteBuyStock() {
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
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		
		HttpSession session = request.getSession();
		String selectedStockExchange = (String) session.getAttribute("selectedStockExchange");
		String selectedStock = (String) session.getAttribute("selectedStock");
		String user = (String) session.getAttribute("user");
		
		PrintWriter out = response.getWriter();
		
		// generate HTML header
		out.println(HTMLHelper.generateHTMLHeader());
		out.println("<h1>Finished transaction for buying " + selectedStock + "</h1>");
		
		UddiManager uddiManager = UddiManager.getInstance();
		
		String accessPoint = uddiManager.getPublishedAccessPointFor(selectedStockExchange);
		ExchangeServiceService ss = new ExchangeServiceService(new URL(accessPoint), SERVICE_NAME);
        ExchangeService port = ss.getExchangeServicePort();  
		
        Exchange result = port.buyStock(selectedStock, quantity);
        
        out.println("<p>Execution " + result.getExecution() + "<p>Order: " + result.getOrder());
        
        //add to depot
        StockDepotElement s = new StockDepotElement();
        s.setIsin(result.getStock().getIsin());
        s.setName(result.getStock().getName());
        s.setCurrency(result.getStock().getCurrency());
        s.setMic(result.getStockExchange().getMic());
        s.setQuantity(result.getExecution());
        
        StockDepotManager.getInstance().addStock(user, s);
        
        out.println("<p><a href='depotOverview.jsp'>Back to depot overview</a>");
        
        // generate HTML footer
        out.println(HTMLHelper.generateHTMLFooter());
		out.close();
	}

}
