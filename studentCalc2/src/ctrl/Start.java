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
		
		//This is a test
		/**
		 * Writing to the servlet to reply to the client with a message 
		 */	

		
		String sub = request.getParameter("submit");
		if (sub != null) {
			String targetRes = "/Results.jspx";
			
			String A = request.getParameter("principal"); 
			String n = request.getParameter("payment");
			String r = request.getParameter("interest");
			// Add fixed Interest and grace period
			String gPeriod = request.getParameter("gracePeriod");
			String fixedR = request.getParameter("fixedInterest");
			// set new double total interest
			Double totalInterest = (double) 0;
			
			Double principal = Double.parseDouble(this.getServletContext().getInitParameter("principal"));

			Double period = Double.parseDouble(this.getServletContext().getInitParameter("period"));
			Double interest = Double.parseDouble(this.getServletContext().getInitParameter("interest"));
			
			Double gracePeriod = Double.parseDouble(this.getServletContext().getInitParameter("gracePeriod"));;
			Double fixedInterest = Double.parseDouble(this.getServletContext().getInitParameter("fixedInterest"));	
			
			if (A != null && A != "") {
				principal = Double.parseDouble(A);
			}

			if (n != null && n != "") {
				period = Double.parseDouble(n);			
			}

			if (r != null && r != "") {
				interest = Double.parseDouble(r);

			}

			totalInterest = fixedInterest + interest;
	
			Double osapFormula = ((interest/12)*principal)/(1 - Math.pow(1 + interest/12, -period));
			Double graceInterest = (principal*(((totalInterest)/12)*gracePeriod));
			Double osapWithGrace = osapFormula + (graceInterest / gracePeriod);
			
			DecimalFormat dfOsap = new DecimalFormat("#.##");
			String roundedOsap = dfOsap.format(osapFormula);
			
			DecimalFormat dfGraceOsap = new DecimalFormat("#.##");
			String roundedOsapGrace = dfGraceOsap.format(osapWithGrace); 
			
			System.out.println(gracePeriod);
			if (request.getParameter("grace") != null) {
				DecimalFormat dfGrace = new DecimalFormat("#.##");
				String roundedGrace = dfGrace.format(graceInterest);
				
				request.setAttribute("interest",roundedGrace); 
				request.setAttribute("mPayment",roundedOsapGrace);				
			}
			else {
				graceInterest = (double) 0;
				DecimalFormat dfGrace = new DecimalFormat("#.##");
				String roundedGrace = dfGrace.format(graceInterest);
				
				request.setAttribute("interest",roundedGrace);
				request.setAttribute("mPayment",roundedOsap);
			}
			
			request.getRequestDispatcher(targetRes).forward(request, response);	
			
			// If action is Lassonde website in Results.jspx, gets redirected after restarting
			
			//response.setContentType("text/plain");

		}
		else {
			String target = "/UI.jspx";
			request.getRequestDispatcher(target).forward(request, response);		
		}
		
	}
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
