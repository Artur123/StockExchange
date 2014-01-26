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
 * Servlet implementation class ExecuteSellStock
 */
public class ExecuteSellStock extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private static final QName SERVICE_NAME = new QName("http://service.stockexchange.ce.jku.at/", "ExchangeServiceService");
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExecuteSellStock() {
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
		//String selectedStockExchange = request.getParameter("stockExchange");
		String selectedStockExchange = (String) session.getAttribute("selectedStockExchange");
		String selectedStock = (String) session.getAttribute("selectedStock");
		String user = (String) session.getAttribute("user");
		
		PrintWriter out = response.getWriter();
		
		// generate HTML header
		out.println(HTMLHelper.generateHTMLHeader());
		out.println("<h1>Finished transaction for selling " + selectedStock + "</h1>");
		
		UddiManager uddiManager = UddiManager.getInstance();
		
		String accessPoint = uddiManager.getPublishedAccessPointFor(selectedStockExchange);
		ExchangeServiceService ss = new ExchangeServiceService(new URL(accessPoint), SERVICE_NAME);
        ExchangeService port = ss.getExchangeServicePort();  
		
      //check if there are enough stocks for sale
        int availableStocks = StockDepotManager.getInstance().getStock(user, selectedStock, selectedStockExchange).getQuantity();
        
        int execution;
        if(availableStocks >= quantity){
        	execution = quantity;
        }else{
        	execution = availableStocks;
        }
        
        port.sellStock(selectedStock, execution);
        
        out.println("<p>Execution " + execution + "<p>Order: " + quantity);
        //update depot
        
        StockDepotElement st = new StockDepotElement();
        st.setIsin(selectedStock);
        st.setMic(selectedStockExchange);
        st.setQuantity(execution);
        StockDepotManager.getInstance().removeStock(user, st);
        
        out.println("<p><a href='depotOverview.jsp'>Back to depot overview</a>");
        
        // generate HTML footer
        out.println(HTMLHelper.generateHTMLFooter());
		out.close();
	}

}
