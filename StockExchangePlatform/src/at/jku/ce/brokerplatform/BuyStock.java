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
 * Servlet implementation class BuyStock
 */
public class BuyStock extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	 private static final QName SERVICE_NAME = new QName("http://service.stockexchange.ce.jku.at/", "ExchangeServiceService");
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuyStock() {
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
		String selectedStock = request.getParameter("stock");
		
		HttpSession session = request.getSession();
		//String selectedStockExchange = request.getParameter("stockExchange");
		String selectedStockExchange = (String) session.getAttribute("selectedStockExchange");
		session.setAttribute("selectedStock", selectedStock);
		PrintWriter out = response.getWriter();
		
		// generate HTML header
		generateHTMLHeader(out);
		out.println("<h1>Buy from "+selectedStockExchange+ ": Stock " + selectedStock + "</h1>");
		
		UddiManager uddiManager = UddiManager.getInstance();
		
		String accessPoint = uddiManager.getPublishedAccessPointFor(selectedStockExchange);
		ExchangeServiceService ss = new ExchangeServiceService(new URL(accessPoint), SERVICE_NAME);
        ExchangeService port = ss.getExchangeServicePort();  
		
        //input field for quantity
        out.println("<form action='#' method='post'>");
        out.println("<input type='text' name='quantity'/>");
        out.println("<input type='submit' value='buy!'>");
        out.println("</form>");
        // generate HTML footer
		generateHTMLFooter(out);
		out.close();
	}

	private void generateHTMLHeader(PrintWriter out) {
		out.println("<html>");
		out.println("<head>");
		out.println("</head>");
		out.println("<body>");
	}

	private void generateHTMLFooter(PrintWriter out) {
		out.println("</body>");
		out.println("</html>");
	}

}
