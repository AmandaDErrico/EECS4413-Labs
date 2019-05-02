package bean;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="sisReport")
@XmlAccessorType(XmlAccessType.FIELD)
public class ListWrapper {

	@XmlAttribute
	private String namePrefix;
	@XmlAttribute(name="creditTaken")
	private int credit_taken;
	@XmlElement(name="studentList")
	private List<StudentBean> list = new ArrayList<StudentBean>();
	
	public ListWrapper() {
	
	}
	
	public String getNamePrefix() {
		return namePrefix;
	}
	public void setNamePrefix(String namePrefix) {
		this.namePrefix = namePrefix;
	}
	public int getCredit_taken() {
		return credit_taken;
	}
	public void setCredit_taken(int credit_taken) {
		this.credit_taken = credit_taken;
	}
	public List<StudentBean> getList() {
		return list;
	}
	public void setList(List<StudentBean> list) {
		this.list = list;
	}
	
	public int getListSize() {
		return list.size();
	}
	
	
	
}
