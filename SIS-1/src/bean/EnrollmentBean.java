package bean;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentBean {

	private String cid;
//	private String sid;
	private List<String> students = new ArrayList<String>();
	private int credit;

	
	public EnrollmentBean() {
		
	}


	public String getCid() {
		return cid;
	}


	public void setCid(String cid) {
		this.cid = cid;
	}

	public List<String> getStudents() {
		return students;
	}


	public void setStudents(String sid) {
		this.students.add(sid);
	}


	public int getCredit() {
		return credit;
	}


	public void setCredit(int credit) {
		this.credit = credit;
	}

	
}
