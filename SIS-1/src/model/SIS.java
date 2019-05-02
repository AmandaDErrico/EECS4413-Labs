package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.HashMap;

import bean.EnrollmentBean;
import bean.StudentBean;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import DAO.StudentDAO;
import DAO.EnrollmentDAO;

public class SIS {
	private StudentDAO students;
	private EnrollmentDAO enrolled;
	private String namePrefix;
	private int creditTaken;
	
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
	
	private void checkInputs(String name_prefix, String credit_taken) throws SQLException {

		try {
			int taken = Integer.parseInt(credit_taken);
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		try {
			String query = "select * from students where surname like ? and credit_taken >= ?";
			Connection con = this.students.getDS().getConnection();
			PreparedStatement inputValidation = con.prepareStatement(query);
			inputValidation.setString(1, name_prefix);
			inputValidation.setInt(2, Integer.parseInt(credit_taken));
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

}
