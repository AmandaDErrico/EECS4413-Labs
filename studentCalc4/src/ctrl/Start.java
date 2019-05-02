package ctrl;

import java.io.IOException;
import java.io.Writer;
import java.text.DecimalFormat;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import listener.MaxPrincipal;
import model.Loan;

/**
 * Servlet implementation class Start
 */
@WebServlet(urlPatterns = { "/Start", "/Startup/", "/Startup/*" })
public class Start extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Loan L;
	private static final String PRINCIPAL = "principal";
	private static final String INTEREST = "interest";
	private static final String PERIOD = "payment";
	private Double principal;
	private Double interest;
	private Double period;
	private static final String COMM="comm";
	private static final String AJAX="ajax";
	private static final String FPAGE = "formPage";
	private static final String APAGE = "ajaxPage";
 
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Start() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	@Override
	public void init() throws ServletException {
		L = new Loan();
		getServletContext().setAttribute("model", L);
		
	}

	protected void updateValues(HttpServletRequest request, Double principal, Double interest, Double period) {
		// update the values of the servlet with the new ones the client provided
		request.getServletContext().setAttribute(PRINCIPAL, principal);
		request.getServletContext().setAttribute(INTEREST, interest);
		request.getServletContext().setAttribute(PERIOD, period);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String sAppText = this.getServletContext().getInitParameter("sApp");
		request.getServletContext().setAttribute("sAppTitle", sAppText);
		// comment

//		response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("Hello, Got a GET request!");
		//response.getWriter().flush();
		

		

		

		// This is a test
		/**
		 * Writing to the servlet to reply to the client with a message
		 */
		
		String targetStartPage = "/UI.jspx";
		
		String res = request.getParameter("recompute");
		String sub = request.getParameter("submit");
		String aj = request.getParameter("ajax");
		if (res == null && sub == null) {
			principal = Double.parseDouble(this.getServletContext().getInitParameter("principal"));
			period = Double.parseDouble(this.getServletContext().getInitParameter("period"));
			interest = Double.parseDouble(this.getServletContext().getInitParameter("interest"));
		}
		
		Double gracePeriod = Double.parseDouble(this.getServletContext().getInitParameter("gracePeriod"));
		Double fixedInterest = Double.parseDouble(this.getServletContext().getInitParameter("fixedInterest"));

		// reset the error messages once you start the servlet and once you go back to UI page
		request.getServletContext().setAttribute("principalE", false);
		request.getServletContext().setAttribute("interestE", false);
		request.getServletContext().setAttribute("periodE", false);
		
		
		request.getServletContext().setAttribute("graceI", false);
		request.getServletContext().setAttribute("monthlyP", false);	
		

		if (sub != null || aj != null) {
			String targetRes = "/Results.jspx";

			String A = request.getParameter("principal");
			String n = request.getParameter("payment");
			String r = request.getParameter("interest");

			if (A != null && A != "") {
				principal = Double.parseDouble(A);
			}

			if (n != null && n != "") {
				period = Double.parseDouble(n);
			}

			if (r != null && r != "") {
				interest = Double.parseDouble(r);

			}


			// update the values for the next session
			this.updateValues(request, principal, interest, period);



			// check for general error message
			String error = null;


			Double monthlyPayment = null;
			Double monthlyPaymentNoGrace = null;
			Double graceInterest = null;
			Double noGraceInterest = null;
			try {
				// at least one of these values may be null -- must see which ones -- in order
				// of principal, period, interest
				
				// gets all possibly computations and calls them if servlet gets redirected to results page
				monthlyPayment = L.computePayment(principal, period, interest, gracePeriod, fixedInterest, true);
				monthlyPaymentNoGrace = L.computePayment(principal, period, interest, gracePeriod, fixedInterest, false);
				graceInterest = L.computeGraceInterest(principal, gracePeriod, interest, fixedInterest);
				noGraceInterest = L.computeGraceInterest(principal, 0.0, interest, fixedInterest);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				error = e1.getMessage();

				if (error == Loan.PRINCIPAL_ERROR) {
					request.getServletContext().setAttribute("principalE", true);
				}
				else if (error == Loan.INTEREST_ERROR) {
					request.getServletContext().setAttribute("interestE", true);
				}
				else if (error == Loan.PERIOD_ERROR) {
					request.getServletContext().setAttribute("periodE", true);
				}
				// redirect to UI page again
				request.getRequestDispatcher(targetStartPage).forward(request, response);
			} // if no catch for exception, the values are defined

			if (error == null) {
				// reset all values to be false bc youre being redirected to the results page
				request.getServletContext().setAttribute("principalE", false);
				request.getServletContext().setAttribute("interestE", false);
				request.getServletContext().setAttribute("periodE", false);

				if (request.getParameter("grace") != null) {
					request.setAttribute("interestR", graceInterest);
					request.setAttribute("mPayment", monthlyPayment);
					// use a variable to see if grace is checked, then save it to session unless
					// unchecked
					request.getServletContext().setAttribute("checkedTrue", true);
				} else {
					 // reset checkbox back to false
					request.getServletContext().setAttribute("checkedTrue", false);
					request.setAttribute("interestR", noGraceInterest);
					request.setAttribute("mPayment", monthlyPaymentNoGrace);
				}

				if (sub != null) {
					request.getRequestDispatcher(targetRes).forward(request, response);				
				}
				
				else if (aj!=null) {
					request.getServletContext().setAttribute("graceI", true);
					request.getServletContext().setAttribute("monthlyP", true);	
					request.getRequestDispatcher(targetStartPage).forward(request, response);	
				}

			}
			// If action is Lassonde website in Results.jspx, gets redirected after
			// restarting
			// If testing in different browsers, gets affected bc scope in the same session
		}

		else {
			request.getRequestDispatcher(targetStartPage).forward(request, response);
			request.getServletContext().setAttribute(PRINCIPAL, principal);
			request.getServletContext().setAttribute(INTEREST, interest);
			request.getServletContext().setAttribute(PERIOD, period);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
