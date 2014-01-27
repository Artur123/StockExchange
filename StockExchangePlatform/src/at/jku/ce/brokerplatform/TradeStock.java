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
			String selectedStock = request.getParameter("selectedStock");
			//String selectedStockExchange = (String) request.getParameter("selectedStockExchange");
			HttpSession session = request.getSession();
			
			String selectedStockExchange = (String)  session.getAttribute("selectedStockExchange");
			session.setAttribute("selectedStock", selectedStock);
			
			
			
			// generate HTML header
			out.println(HTMLHelper.generateHTMLHeader());
			out.println("<h1>Transaction for "+selectedStockExchange+ ": Stock " + selectedStock + "</h1>");
			
	        //input field for quantity
	        out.println("<form action='ExecuteTransaction' method='post'>");
	        out.println("<input type='text' name='quantity'/>");
	        out.println("(enter negative number to sell stocks)");
	        out.println("<input type='submit' value='transact!'>");
	        out.println("</form>");
	        // generate HTML footer
	        out.println(HTMLHelper.generateHTMLFooter());
		}catch(Exception e){
			out.println(e.toString());
		}finally{
			out.close();
		}
	}

}
