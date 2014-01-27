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
			//String selectedStockExchange = request.getParameter("stockExchange");
			String selectedStockExchange = (String) session.getAttribute("selectedStockExchange");
			String selectedStock = (String) session.getAttribute("selectedStock");
			String user = (String) session.getAttribute("user");
			
			// generate HTML header
			out.println(HTMLHelper.generateHTMLHeader());
			out.println("<h1>Finished transaction for " + selectedStock + "</h1>");
			
			UddiManager uddiManager = UddiManager.getInstance();
			
			String accessPoint = uddiManager.getPublishedAccessPointFor(selectedStockExchange);
			ExchangeServiceService ss = new ExchangeServiceService(new URL(accessPoint), SERVICE_NAME);
	        ExchangeService port = ss.getExchangeServicePort();
	        
			if(quantity == 0)
				out.println("<p>Transaction failed: enter a value higher or lower than 0");
			else if(quantity < 0){
	        	executeSellStock(quantity, selectedStockExchange, selectedStock, user,
	        			out, port);
	        }else{
	        	executeBuyStock(quantity, selectedStock, user, out, port);
	        }
	        
	        out.println("<p><a href='depotOverview.jsp'>Back to depot overview</a>");
	        
	        // generate HTML footer
	        out.println(HTMLHelper.generateHTMLFooter());
		}catch(Exception e){
			out.println(e.toString());
		}finally{
			out.close();
		}
	}

	private void executeBuyStock(int quantity, String selectedStock,
			String user, PrintWriter out, ExchangeService port) {
		try{
			Exchange result = port.buyStock(selectedStock, quantity);
			
			out.println("<p>Execution " + result.getExecution() + "<p>Order: " + result.getOrder());
			if(result.getExecution() > 0){
				//update depot
				StockDepotElement s = new StockDepotElement();
				s.setIsin(result.getStock().getIsin());
				s.setName(result.getStock().getName());
				s.setCurrency(result.getStock().getCurrency());
				s.setMic(result.getStockExchange().getMic());
				s.setQuantity(result.getExecution());
				StockDepotManager.getInstance().addStock(user, s);
			}
		}catch(Exception e){
			out.println("Exception while buying: " + e.toString());
		}
	}

	private void executeSellStock(int quantity, String selectedStockExchange,
			String selectedStock, String user, PrintWriter out,
			ExchangeService port) {
		try{
			//check if there are enough stocks for sale
			int availableStocks = StockDepotManager.getInstance().getStock(user, selectedStock, selectedStockExchange).getQuantity();
	        int execution;
	        if(availableStocks + quantity >= 0){
	        	execution = -quantity;
	        }else{
	        	execution = availableStocks;
	        }
	        
	        port.sellStock(selectedStock, execution);
	        out.println("<p>Execution " + -execution + "<p>Order: " + quantity);
	        
	        //update depot
	        StockDepotElement st = new StockDepotElement();
	        st.setIsin(selectedStock);
	        st.setMic(selectedStockExchange);
	        st.setQuantity(execution);
	        StockDepotManager.getInstance().removeStock(user, st);
        }catch(NullPointerException e){
        	out.println("<p>Transaction failed: stock not available for selling");
        }catch(Exception e){
        	out.println("<p>Excception while selling: " + e.toString());
        }
	}

}
