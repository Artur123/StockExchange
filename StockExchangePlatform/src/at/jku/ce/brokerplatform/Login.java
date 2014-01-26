package at.jku.ce.brokerplatform;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
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
		String user = request.getParameter("user");
		
		PrintWriter out = response.getWriter();
		
		// generate HTML header
		generateHTMLHeader(out);
		out.println("login successful for user " + user);
		out.println("<br />Click <a href=\"depotOverview\">here</a> to get to your depot overview.<br />");

	
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
