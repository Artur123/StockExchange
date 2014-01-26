package at.jku.ce.brokerplatform;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		
//		//TODO: ändern
//		WebUser webuser = new WebUser("test");

		PrintWriter out = response.getWriter();
		
		// generate HTML header
		out.println(HTMLHelper.generateHTMLHeader());
		out.println("Welcome user '" + user + "'");
		out.println("<p> <a href='depotOverview.jsp'>show depot</a>");
	
        // generate HTML footer
		out.println(HTMLHelper.generateHTMLFooter());
		out.close();
	}

	
}
