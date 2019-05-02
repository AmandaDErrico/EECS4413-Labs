package bean;

public class StudentBean {

	private String sid;
	private String name;
	private int credit_taken;
	private int credit_grad;
	
	public StudentBean() {
		
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCredit_taken() {
		return credit_taken;
	}

	public void setCredit_taken(int credit_taken) {
		this.credit_taken = credit_taken;
	}

	public int getCredit_grad() {
		return credit_grad;
	}

	public void setCredit_grad(int credit_grad) {
		this.credit_grad = credit_grad;
	}
	
}
