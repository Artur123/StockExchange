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

/**
 * Servlet implementation class SellStock
 */
public class SellStock extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private static final QName SERVICE_NAME = new QName("http://service.stockexchange.ce.jku.at/", "ExchangeServiceService");
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SellStock() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String selectedStock = request.getParameter("selectedStock");
		String selectedStockExchange = (String) request.getParameter("selectedStockExchange");
		HttpSession session = request.getSession();
		
		session.setAttribute("selectedStockExchange", selectedStockExchange);
		session.setAttribute("selectedStock", selectedStock);
		
		PrintWriter out = response.getWriter();
		
		// generate HTML header
		out.println(HTMLHelper.generateHTMLHeader());
		out.println("<h1>Sell from "+selectedStockExchange+ ": Stock " + selectedStock + "</h1>");
		
        //input field for quantity
        out.println("<form action='ExecuteSellStock' method='post'>");
        out.println("<input type='text' name='quantity'/>");
        out.println("<input type='submit' value='sell!'>");
        out.println("</form>");
        // generate HTML footer
        out.println(HTMLHelper.generateHTMLFooter());
		out.close();
	}

}
