package bean;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder={"cid", "students", "credit"})
public class EnrollmentBean {

	private String cid;
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


	public void setStudents(List<String> sids) {
		this.students = sids;
	}


	public int getCredit() {
		return credit;
	}


	public void setCredit(int credit) {
		this.credit = credit;
	}

	
}
