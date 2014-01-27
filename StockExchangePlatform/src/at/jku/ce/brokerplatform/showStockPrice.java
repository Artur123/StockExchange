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
import at.jku.ce.stockexchange.service.ExchangeService;
import at.jku.ce.stockexchange.service.ExchangeServiceService;
import at.jku.ce.stockexchange.service.Stock;

/**
 * Servlet implementation class showStockPrice
 */
public class showStockPrice extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final QName SERVICE_NAME = new QName("http://service.stockexchange.ce.jku.at/", "ExchangeServiceService");
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public showStockPrice() {
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
			String selectedStockExchange = request.getParameter("selectedStockExchange");
			String selectedStock = request.getParameter("selectedStock");
			
			HttpSession session = request.getSession();
			String user = request.getParameter("user");
			// generate HTML header
			out.println(HTMLHelper.generateHTMLHeader());
			out.println("<h1>Pricequery</h1>");
			
			// get all stocks of selected stock exchange and output them to a list
			UddiManager uddiManager = UddiManager.getInstance();
			
			String accessPoint = uddiManager.getPublishedAccessPointFor(selectedStockExchange);
			ExchangeServiceService ss = new ExchangeServiceService(new URL(accessPoint), SERVICE_NAME);
	        ExchangeService port = ss.getExchangeServicePort();  
	        
	        Stock result = null;
	        if(user!=""){
		        result = port.getStock(selectedStock);
	        }else{
		        //TODO: 10 minutes
	        	result = port.getStock(selectedStock);
	        }
	        out.println("price for " + selectedStock +
	        		" from Stockexchange " + selectedStockExchange + ": "
	        		+ result.getPrice() + result.getCurrency());
	        
	        out.println("<p><a href='Login'>Back</a>");
		}catch(NullPointerException e){
			out.println("Stock does not exist");
		}catch(Exception e){
			out.println(e.toString());
		}
		// generate HTML footer
		out.println(HTMLHelper.generateHTMLFooter());
		out.close();
	}

}
