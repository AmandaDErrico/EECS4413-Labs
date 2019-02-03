/**
 * 
 */
package model;


/**
 * @author Amanda D'Errico
 *
 */
public class Loan {
	public static final String PRINCIPAL_ERROR = "Principal must be greater than 0";
	public static final String PERIOD_ERROR = "Period must be greater than 0";
	public static final String INTEREST_ERROR = "Interest must be greater than 0";

	public Loan() {

	}

	public double computePayment(double p, double a, double i, double gp, double fi, boolean g) throws Exception {

		double dPrincipal;
		double dInterest;
		double dPeriod;
		double grace = 0;
		double dGrace_period = 0;
		double dFixed_interest = 0;
		
		// check if all values are valid, otherwise throw exception
		if (p > 0) {
			dPrincipal = p;
		}
		else {
			throw new Exception(PRINCIPAL_ERROR);
		}
		if (i > 0) {
			dInterest = i;
		}
		else {
			throw new Exception(INTEREST_ERROR);
		}
		if (a > 0) {
			dPeriod = a;
		}
		else {
			throw new Exception(PERIOD_ERROR);
		}	

		dFixed_interest = fi;

		// check if grace exists, then compute graceInterest. If not, return osapFormula ONLY.
		if (g) {
			dGrace_period = gp;
		}

		double interestPerMonth = dInterest / 1200;
		Double osapFormula = (interestPerMonth * dPrincipal) / (1 - (Math.pow(1 + interestPerMonth, -dPeriod)));

		grace = computeGraceInterest(dPrincipal, dGrace_period, dInterest, dFixed_interest);

		if (dGrace_period != 0) {
			double osapWithGrace = osapFormula + (grace / dGrace_period);
			return osapWithGrace;
		}
		else {
			return osapFormula;
		}

	}

	public double computeGraceInterest(double p, double gp, double i, double fi) throws Exception {

		double pDouble = 0;
		double iDouble = 0;

		// extra error handling
		if (p > 0) {
			pDouble = p;
		}
		else {
			throw new Exception(PRINCIPAL_ERROR);
		}

		if (i > 0) {
			iDouble = i;
		}
		else {
			throw new Exception(INTEREST_ERROR);
		}
		
		double totalInterest = fi + iDouble;
		double graceInterest = (pDouble * (((totalInterest) / 12f) * gp));


		return graceInterest;

	}
}
