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
		// TODO Auto-generated method stub
		String selectedStockExchange = request.getParameter("exchange");
		PrintWriter out = response.getWriter();
		
		// generate HTML header
		generateHTMLHeader(out);
		out.println("<h1>"+selectedStockExchange+"</h1>");
		
		// get all stocks of selected stock exchange and output them to a list
		UddiManager uddiManager = UddiManager.getInstance();
		
		String accessPoint = uddiManager.getPublishedAccessPointFor(selectedStockExchange);
		ExchangeServiceService ss = new ExchangeServiceService(new URL(accessPoint), SERVICE_NAME);
        ExchangeService port = ss.getExchangeServicePort();  
        
        out.println("<form action='BuyStock' method='post'>");
        out.println("<ul>");
        for(Stock s : port.getTradedStocks()){
        	//out.println("<li>"+ s.getName()+"</li>");
        	out.println("<li><input type='radio' name='stock' value='" + s.getIsin() + "'>" + s.getName() + "</li>");
        }
        out.println("</ul>");
        out.println("<input type='hidden' name='stockExchange' value='" + selectedStockExchange + "'/>");
        out.println("<input type='submit' value='buy selected stock'>");
        out.println("</form>");
      
        // store stocks in session for further use
        HttpSession session = request.getSession();
        session.setAttribute("currentStocks", port.getTradedStocks());
        
        //retrieve objects from session
        List<Stock> currentStocks = (List<Stock>) session.getAttribute("currentStocks");
        
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
