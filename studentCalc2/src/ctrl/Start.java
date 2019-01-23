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

		
		
		// Part C to print principal
//		Double principalC = Double.parseDouble(this.getServletContext().getInitParameter("principal"));
//		resOut.write("Principal Part C: " + principalC + "\n");
		
		
		// Part C - generate Exception and 404 error
		// For exception, try to parse a double as a string: Double except = Double.parseDouble("amanda");
		// For 404 error, type garbage: http://localhost:8080/studentCalc1/Startjhmbjhb

		
		
		String sub = request.getParameter("submit");
		if (sub != null) {
			String targetRes = "/Results.jspx";
			
			String A = request.getParameter("principal"); 
			String n = request.getParameter("period");
			String r = request.getParameter("interest");
			// Add fixed Interest and grace period
			String gPeriod = request.getParameter("gracePeriod");
			String fixedR = request.getParameter("fixedInterest");
			// set new double total interest
			Double totalInterest = (double) 0;
			
			Double principal = Double.parseDouble(this.getServletContext().getInitParameter("principal"));
		//	System.out.println("Value of prin = " + principal);
			Double period = Double.parseDouble(this.getServletContext().getInitParameter("period"));
			Double interest = Double.parseDouble(this.getServletContext().getInitParameter("interest"));
//			Double gracePeriod = Double.parseDouble(this.getServletContext().getInitParameter("gracePeriod"));
			
			Double gracePeriod = Double.parseDouble(this.getServletContext().getInitParameter("gracePeriod"));;
			Double fixedInterest = Double.parseDouble(this.getServletContext().getInitParameter("fixedInterest"));	
			
			if (A != "") {
				principal = Double.parseDouble(A);
			}
		//	System.out.println("Value of prin = " + principal);
			if (n != null) {
				period = Double.parseDouble(n);			
			}
		//	System.out.println("Value of period = " + period);
			if (r != "") {
				interest = Double.parseDouble(r);
				//totalInterest = interest;
			}
		//	System.out.println("Value of interest = " + interest);
			// if grace period changes in the govt (i.e its 0 now)

			//gracePeriod = 

		//	System.out.println("Value of grace period = " + gracePeriod);	
			//fixedInterest = Double.parseDouble(fixedR);		
		//	System.out.println("Value of fixed interest = " + fixedInterest);
			// new total interest
			totalInterest = fixedInterest + interest;
	
			Double osapFormula = ((interest/12)*principal)/(1 - Math.pow(1 + interest/12, -period));
			Double graceInterest = (principal*(((totalInterest)/12)*gracePeriod));
			Double osapWithGrace = osapFormula + (graceInterest / gracePeriod);
			
			DecimalFormat dfOsap = new DecimalFormat("#.##");
			String roundedOsap = dfOsap.format(osapFormula);

//			DecimalFormat dfGrace = new DecimalFormat("#.##");
//			String roundedGrace = dfGrace.format(graceInterest);
			
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
			

	
//			Writer resOut = response.getWriter();
//			String cType = request.getContentType();
//			Long cLength = request.getContentLengthLong();
//			System.out.println("Content length and content Type are: " + cLength + " and " + cType + ", respectively\n");
			
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
