package listener;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * Application Lifecycle Listener implementation class MaxPrincipal
 *
 */
@WebListener
public class MaxPrincipal implements HttpSessionAttributeListener {
//	int mpCounter = 0;
//	boolean added=false;
	public static double maxP=0;

	/**
	 * Default constructor.
	 */
	public MaxPrincipal() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpSessionAttributeListener#attributeAdded(HttpSessionBindingEvent)
	 */
	public void attributeAdded(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		attributeReplaced(arg0);
	}

	/**
	 * @see HttpSessionAttributeListener#attributeRemoved(HttpSessionBindingEvent)
	 */
	public void attributeRemoved(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		// never reaches this case bc always a default value
	}

	/**
	 * @see HttpSessionAttributeListener#attributeReplaced(HttpSessionBindingEvent)
	 */
	public void attributeReplaced(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		// arg0 is the value you want to replace
		if (arg0.getName().equals("principal")) { // if principal was updated after change
			double tempMp = Double.parseDouble(arg0.getValue().toString()); // get the value for the new principal
			if (tempMp > maxP) { // see if the new prin value is greater than previous max, if it is replace it and update new max
				maxP = tempMp; // can access maxP in another java class, but if you want to access in html, do setAttribute
			}
		}
	}

}
