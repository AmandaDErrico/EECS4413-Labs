package ctrl;

import java.io.IOException;
import java.io.Writer;
import java.text.DecimalFormat;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Start
 */
@WebServlet(urlPatterns={"/Start", "/Startup/", "/Startup/*"})
public class Start extends HttpServlet {
	private static final long serialVersionUID = 1L;

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Start() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("Hello, Got a GET request!");
		
		
		/**
		 * Writing to the servlet to reply to the client with a message 
		 */
		response.setContentType("text/plain");
		Writer resOut = response.getWriter();
		
		// Part B
		resOut.write("\nHello, World!\n");
		
		
		String clientIP = request.getRemoteAddr();
		resOut.write("Client IP: " + clientIP + "\n");
		int portNum = request.getRemotePort();
		resOut.write("Port Num: " + portNum + "\n");
		String protocol = request.getProtocol();
		resOut.write("Protocol: " + protocol + "\n");
		String method = request.getMethod();
		resOut.write("Method: " + method + "\n");
		
		String clientQueryString = request.getQueryString();
		String p = request.getParameter("protocol");
		resOut.write("Query Param Protocol = " + p + "\n");
		String pn = request.getParameter("portNum");
		resOut.write("Query Param PortNum = " + pn + "\n");
		String m = request.getParameter("method");
		resOut.write("Query Param Method = " + m + "\n");
		
		String url = this.getServletContext().getContextPath() + "/Start";
		StringBuffer URL = request.getRequestURL();
		String URI = request.getRequestURI();
		String context = request.getServletContext().getContextPath();
		String servlet = request.getServletPath();
		
		resOut.write("URL: " + URL + "\n");
		resOut.write("URI: " + URI + "\n");
		resOut.write("Context: " + context + "\n");
		resOut.write("Servlet: " + servlet + "\n");		

		// get end of string to redirect back to Start 
		// StartUp/YorkBank
		String StartUpYorkBank = "/Startup/YorkBank";
		if (URI.endsWith(StartUpYorkBank)) {
			response.sendRedirect(url);
		}
		
		
		// Part C to print principal
		Double principalC = Double.parseDouble(this.getServletContext().getInitParameter("principal"));
		resOut.write("Principal Part C: " + principalC + "\n");
		
		
		// Part C - generate Exception and 404 error
		// For exception, try to parse a double as a string: Double except = Double.parseDouble("amanda");
		// For 404 error, type garbage: http://localhost:8080/studentCalc1/Startjhmbjhb
		
		// Part D
		resOut.write("---- Monthly payments ----\n");
		String A = request.getParameter("principal"); 
		String n = request.getParameter("period");
		String r = request.getParameter("interest");
		Double principal = Double.parseDouble(this.getServletContext().getInitParameter("principal"));
		Double period = Double.parseDouble(this.getServletContext().getInitParameter("period"));
		Double interest = Double.parseDouble(this.getServletContext().getInitParameter("interest"));
		
		if (A != null) {
			principal = Double.parseDouble(A);
		}
		
		if (n != null) {
			period = Double.parseDouble(n);			
		}
		
		if (r != null) {
			interest = Double.parseDouble(r);			
		}
		
		resOut.write("Based on Principal=" + principal + " ");
		resOut.write("Period=" + period + " ");
		resOut.write("Interest=" + interest + "\n");
		
		Double osapFormula = ((interest/12)*principal)/(1 - Math.pow(1 + interest/12, -period));
		
		DecimalFormat df = new DecimalFormat("#.##");
		String roundedOsap = df.format(osapFormula); 
		resOut.write("Monthly Payments: " + roundedOsap + "\n");
	}
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
