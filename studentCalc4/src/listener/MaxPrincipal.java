package listener;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class MaxPrincipal
 *
 */
@WebListener
public class MaxPrincipal implements ServletContextAttributeListener {
	public static double maxP = 0;

	/**
	 * Default constructor.
	 */
	public MaxPrincipal() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see ServletContextAttributeListener#attributeAdded(ServletContextAttributeEvent)
	 */
	public void attributeAdded(ServletContextAttributeEvent arg0) {
		// TODO Auto-generated method stub
		attributeReplaced(arg0);
	}

	/**
	 * @see ServletContextAttributeListener#attributeRemoved(ServletContextAttributeEvent)
	 */
	public void attributeRemoved(ServletContextAttributeEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see ServletContextAttributeListener#attributeReplaced(ServletContextAttributeEvent)
	 */
	public void attributeReplaced(ServletContextAttributeEvent arg0) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		System.out.println("Value is: " + arg0.getName());
		if (arg0.getName().equals("principal")) {
			Double tempMaxVal = Double.parseDouble(arg0.getValue().toString());
			// print(maxP);
			if (tempMaxVal > maxP) {
				maxP = tempMaxVal;
				// System.out.println("MaxP is: " + maxP);
				// arg0.getSession().setAttribute("te", maxP);
				// System.out.println("MaxP is: " + maxP);
			}
		}
	}

}
