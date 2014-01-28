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
public class ExecuteTransaction extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private static final QName SERVICE_NAME = new QName("http://service.stockexchange.ce.jku.at/", "ExchangeServiceService");
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExecuteTransaction() {
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
		PrintWriter out = response.getWriter();
		try{
			int quantity = Integer.parseInt(request.getParameter("quantity"));
			
			HttpSession session = request.getSession();
			String selectedStockExchange = (String) session.getAttribute("selectedStockExchange");
			String selectedISIN = (String) session.getAttribute("selectedISIN");
			String user = (String) session.getAttribute("user");
			
			// generate HTML header
			out.println(HTMLHelper.generateHTMLHeader());
			out.println("<h1>Transaction result for " + selectedISIN + "</h1>");
			
			UddiManager uddiManager = UddiManager.getInstance();
			
			String accessPoint = uddiManager.getPublishedAccessPointFor(selectedStockExchange);
			ExchangeServiceService ss = new ExchangeServiceService(new URL(accessPoint), SERVICE_NAME);
	        ExchangeService port = ss.getExchangeServicePort();
			
	        //if quantity is positve: buy, otherwise sell
			if(quantity == 0)
				out.println("<p>Transaction failed: enter a value higher or lower than 0");
			else if(quantity < 0){
	        	executeSellStock(quantity, selectedStockExchange, selectedISIN, user,
	        			out, port);
	        }else{
	        	executeBuyStock(quantity, selectedStockExchange, selectedISIN, user, out, port);
	        }
	        
	        out.println("<p><a href='depotOverview.jsp'>show depot overview</a>");
	        out.println("<p><a href='home.jsp'>home</a>");
	        // generate HTML footer
	        out.println(HTMLHelper.generateHTMLFooter());
		}catch(Exception e){
			out.println(e.toString());
		}finally{
			out.close();
		}
	}

	private void executeBuyStock(int quantity, String selectedStockExchange, String selectedISIN,
			String user, PrintWriter out, ExchangeService port) {
		try{
			//execute buy
			//some stockExchanges return null if stock is not available!
			Exchange result = port.buyStock(selectedISIN, quantity);
			
			out.println("<p>Execution " + result.getExecution() + "<p>Order: " + result.getOrder());
			//update depot if execution is greater 0
			if(result.getExecution() > 0){
				StockDepotElement s = new StockDepotElement();
				s.setIsin(result.getStock().getIsin());
				s.setName(result.getStock().getName());
				s.setCurrency(result.getStock().getCurrency());
				s.setStockExchange(selectedStockExchange);
				s.setQuantity(result.getExecution());
				StockDepotManager.getInstance().addStock(user, s);
			}
		}catch(Exception e){
			out.println("Exception while buying: " + e.toString());
		}
	}

	private void executeSellStock(int quantity, String selectedStockExchange,
			String selectedISIN, String user, PrintWriter out,
			ExchangeService port) {
		try{
			//check if user owns stock and has enough to sell
			int availableStocks = StockDepotManager.getInstance().getStock(user, selectedISIN, selectedStockExchange).getQuantity();
	        int execution;
	        //set execution amount, has to be a positive number
	        if(availableStocks + quantity >= 0){
	        	execution = -quantity;
	        }else{
	        	execution = availableStocks;
	        }
	        //execute selling, no return value
	        port.sellStock(selectedISIN, execution);
	        out.println("<p>Execution " + -execution + "<p>Order: " + quantity);
	        
	        //update depot
	        StockDepotElement st = new StockDepotElement();
	        st.setIsin(selectedISIN);
	        st.setStockExchange(selectedStockExchange);
	        st.setQuantity(execution);
	        StockDepotManager.getInstance().removeStock(user, st);
        }catch(NullPointerException e){
        	out.println("<p>Transaction failed: stock not available for selling");
        }catch(Exception e){
        	out.println("<p>Excception while selling: " + e.toString());
        }
	}

}
