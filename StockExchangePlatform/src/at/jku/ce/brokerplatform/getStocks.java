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
 * Servlet implementation class getStocks
 */
public class getStocks extends HttpServlet {
	private static final long serialVersionUID = 1L;

    private static final QName SERVICE_NAME = new QName("http://service.stockexchange.ce.jku.at/", "ExchangeServiceService");

    /**
     * @see HttpServlet#HttpServlet()
     */
    public getStocks() {
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
			String selectedStockExchange = request.getParameter("exchange");
			
			// generate HTML header
			out.println(HTMLHelper.generateHTMLHeader());
			out.println("<h1>"+selectedStockExchange+": select stock</h1>");
			
			// get all stocks of selected stock exchange and output them to a list
			UddiManager uddiManager = UddiManager.getInstance();
			
			String accessPoint = uddiManager.getPublishedAccessPointFor(selectedStockExchange);
			ExchangeServiceService ss = new ExchangeServiceService(new URL(accessPoint), SERVICE_NAME);
	        ExchangeService port = ss.getExchangeServicePort();
	        
	        //print form with all stocks
	        out.println("<form action='TradeStock' method='post'>");
	        out.println("<ul>");
	        for(Stock s : port.getTradedStocks()){
	        	out.println("<li><input type='radio' name='selectedISIN' value='" + s.getIsin() + "'>" + s.getName() + "(" + s.getAvailability() + ")</li>");
	        }
	        out.println("</ul>");
	        out.println("<input type='submit' value='trade selected stock'>");
	        out.println("</form>");
	      
	        // store stockexchange in session for further use
	        HttpSession session = request.getSession();
	        session.setAttribute("selectedStockExchange", selectedStockExchange);
	        
	        out.println("<a href='home.jsp'>home</a>");
	        
	        // generate HTML footer
			out.println(HTMLHelper.generateHTMLFooter());
		}catch(Exception e){
			out.println(e.toString());
		}
		out.close();
	}
}
