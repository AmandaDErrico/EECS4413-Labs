package ctrl;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.EnrollmentDAO;
import DAO.StudentDAO;
import bean.StudentBean;
import model.SIS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servlet implementation class Test
 */
@WebServlet({ "/Start", "/Start/*", "/start", "/start/*" })
public class Start extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SIS sisModel;
	private StudentDAO sd;
	private EnrollmentDAO ed;
	private String form = "/formUI.jspx";
	private String done = "/Done.jspx";
	private String errorMsg = "";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Start() {
		super();
	}

	public void init() throws ServletException {
		// ServletContext context = getServletContext();

		try {
			sisModel = new SIS();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		getServletContext().setAttribute("sis", sisModel);

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<StudentBean> results = new ArrayList<StudentBean>();
		Map<String, StudentBean> tempRes = new HashMap<String, StudentBean>();
		int errorPresent = 0;
		int numberOfResults = 0;
		
		
		String reportParam = request.getParameter("report");
		String xmlParam = request.getParameter("xml");

		// if you clicked on report or xml
		if (reportParam != null || xmlParam != null) {
			// call other parameters that are now available in the url
			String prefix = request.getParameter("prefix");
			String credits = request.getParameter("cTaken");
		
			System.out.println("Name is: " + prefix);
			System.out.println("Min credits are: " + credits);
			
			
			if (!prefix.isEmpty() && !credits.isEmpty() && !credits.equals("")) {
				try {
					// call instance of sisModel and retrive the student
					sisModel = (SIS) getServletContext().getAttribute("sis");
					if (reportParam != null) {
						tempRes = sisModel.retrieveStudent(prefix, credits);
						// go through the hashmap and retrieve data in the set for the StudentBean
						for (String s : tempRes.keySet()) {
							results.add(tempRes.get(s));
						}
						numberOfResults = tempRes.size();
						request.setAttribute("numberOfResults", numberOfResults);
						// getting array to call its attributes in formUI
						request.setAttribute("resultMap", results.toArray());
						request.getRequestDispatcher(form).forward(request, response);
					}
					else if (xmlParam != null) {
						// export it to xml
						String f= "export/"+ request.getSession().getId()+".xml";
						String filename= this.getServletContext().getRealPath("/"+ f);
						System.out.println(filename);
						sisModel.export(prefix, credits, filename);
						if (sisModel.getListWrapperSize() == 0) {
							// assign the list to the list of the Student bean values
							setErrMsg("Cannot make XML if there's 0 entries, try again.");
							System.out.println(errorMsg);
							errorPresent = 3;
							request.setAttribute("errorValue", errorPresent);
							String errorMessage = errorMsg; 
							request.setAttribute("errMsgXML", errorMessage);
							request.getRequestDispatcher(form).forward(request, response);
						}
						else {
							// export is done here though the form
							
							request.setAttribute("link", f);
							request.setAttribute("anchor", filename);
							request.getRequestDispatcher(done).forward(request, response);
						}
					}


				} catch (Exception e) { // any errors happening. Called in SIS.
					// this could be weird characters in min credits, etc (checkInputs)

					errorPresent = 2;
					request.setAttribute("errorValue", errorPresent);
					String errorMessage = e.getMessage(); 
					request.setAttribute("errMsg", errorMessage);
					request.getRequestDispatcher(form).forward(request, response);					
				}

			} else if (prefix.isEmpty() || credits.isEmpty()) { // if any fields are empty
				errorPresent = 1;
				request.setAttribute("errorValue", errorPresent);
				request.getRequestDispatcher(form).forward(request, response);
			}
			
		}
		else { // when you first start the servlet
			request.getRequestDispatcher(form).forward(request, response);			
		}

	
	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	
	public void setErrMsg(String s) {
		this.errorMsg = s;
	}
	
	public String getErrMsg() {
		return errorMsg;
	}

	
	//public void 
}
