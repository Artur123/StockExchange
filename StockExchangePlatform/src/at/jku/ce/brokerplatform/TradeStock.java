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

/**
 * Servlet implementation class SellStock
 */
public class TradeStock extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private static final QName SERVICE_NAME = new QName("http://service.stockexchange.ce.jku.at/", "ExchangeServiceService");
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TradeStock() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//needed because link from depotOverview.jsp is using get-Parameter
		try{
			String selectedStockExchange = (String) request.getParameter("selectedStockExchange");
			HttpSession session = request.getSession();
			session.setAttribute("selectedStockExchange", selectedStockExchange);
			this.doPost(request, response);
		}catch(Exception e){
			PrintWriter out = response.getWriter();
			out.println(e.toString());
			out.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try{
			//get parameter
			String selectedISIN = request.getParameter("selectedISIN");
			HttpSession session = request.getSession();
			
			//get session attributes and store isin
			String selectedStockExchange = (String)  session.getAttribute("selectedStockExchange");
			session.setAttribute("selectedISIN", selectedISIN);
			
			// generate HTML header
			out.println(HTMLHelper.generateHTMLHeader());
			out.println("<h1>Transaction for "+selectedStockExchange+ ": Stock " + selectedISIN + "</h1>");
			
	        //input field for quantity
	        out.println("<form action='ExecuteTransaction' method='post'>");
	        out.println("<input type='text' name='quantity'/>");
	        out.println("<p>(enter negative number to sell stocks)");
	        out.println("<input type='submit' value='transact!'>");
	        out.println("</form>");
	        
	        out.println("<a href='home.jsp'>home</a>");
	        
	        // generate HTML footer
	        out.println(HTMLHelper.generateHTMLFooter());
		}catch(Exception e){
			out.println(e.toString());
		}finally{
			out.close();
		}
	}

}
