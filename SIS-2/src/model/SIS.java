package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import bean.EnrollmentBean;
import bean.ListWrapper;
import bean.StudentBean;
import ctrl.Start;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import DAO.StudentDAO;
import DAO.EnrollmentDAO;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

public class SIS {
	private StudentDAO students;
	private EnrollmentDAO enrolled;
	private String namePrefix;
	private int creditTaken;
//	private Map<String, StudentBean> sb;
	ListWrapper lw = new ListWrapper();
	private Start sisError;
	
	public SIS() throws ClassNotFoundException {
		this.students = new StudentDAO();
		this.enrolled = new EnrollmentDAO();
	}
	
	public Map<String, StudentBean> retrieveStudent(String name_prefix, String credit_taken) throws Exception {
		checkInputs(name_prefix, credit_taken);

		creditTaken = Integer.parseInt(credit_taken);
		namePrefix = name_prefix;
		return this.students.retrieve(namePrefix, creditTaken);
	}
	
	public Map<String, EnrollmentBean> retriveEnrollment() throws Exception {
		return this.enrolled.retrieve(namePrefix, creditTaken);
		 
	}
	
	public void export(String name_prefix, String credit_taken, String filename) throws Exception {
		
		try {
			checkInputs(name_prefix, credit_taken);
			
			// now must write filename to a file
			
			// make a new mapper for student bean to retrieve the students
			Map<String, StudentBean> sb = this.students.retrieve(name_prefix, Integer.parseInt(credit_taken));
			// make a new arraylist of student beans
			List<StudentBean> sbList = new ArrayList<StudentBean>(sb.values());
			
			lw.setCredit_taken(Integer.parseInt(credit_taken));
			lw.setNamePrefix(name_prefix);
			lw.setList(sbList); // set the list and initialize it with the sb values
			
			JAXBContext jc = JAXBContext.newInstance(lw.getClass());
			Marshaller marshaller = jc.createMarshaller();
			
			// get the relative path
			String path = new File(filename).getParent();
			
			
			SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = sf.newSchema(new File(path+"/SIS.xsd"));
			marshaller.setSchema(schema);
			
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			sw.write("\n");
			

			
			marshaller.marshal(lw, new StreamResult(sw));
			System.out.println(sw.toString()); // for debugging
			FileWriter fw = new FileWriter(filename);
			fw.write(sw.toString());
			fw.close();
			
			// will check if list size == 0 in Start and will generate a new error message for xml exported
			
						
		} catch (Exception e) {
			e.printStackTrace();
			String s = "Cannot be exported, try again.";
			System.out.println(s);
			sisError.setErrMsg(s);
			throw new Exception(s);
		}
	}
	
	public int getListWrapperSize() {
		return lw.getListSize();
	}
	
	
	// ERROR CHECKING STARTS HERE
	private void checkInputs(String name_prefix, String credit_taken) throws Exception {

		// check that credit taken is an integer, can't be garbage
		try {
			int taken = Integer.parseInt(credit_taken);
		} catch (Exception e) {
			String s = "The credit taken is not an integer, try again.";
			System.out.println(s);
			throw new Exception(s);
		}
		
		// check if the minimum credits are less than 0
		int taken = Integer.parseInt(credit_taken);
		if (taken < 0) {
			String s = "Can't have minimum credits TAKEN that are less than 0, try again.";
			System.out.println(s);
			throw new Exception(s);
		}
		
		// prepare query through sql injection
		String query = "select * from students where surname like ? and credit_taken >= ?";
		
		// check that db connection is caught
		Connection con = null;
		try {
			con = this.students.getDS().getConnection();
		} catch (NullPointerException e) {
			String s = "The database connection does not exist, try again.";
			System.out.println(s);
			throw new Exception(s);
		}
		
		// check any errors with preparing the query
		try {
			
			PreparedStatement inputValidation = con.prepareStatement(query);
			inputValidation.setString(1, name_prefix);
			inputValidation.setInt(2, Integer.parseInt(credit_taken));
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

}
