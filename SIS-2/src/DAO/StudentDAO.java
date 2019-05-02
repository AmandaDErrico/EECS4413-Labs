package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.HashMap;
import bean.StudentBean;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class StudentDAO {
	private DataSource ds;

	public StudentDAO() throws ClassNotFoundException {
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public Map<String, bean.StudentBean> retrieve (String namePrefix, int credit_taken) throws SQLException {
		// must change query from lab 6 to include credit_taking, the credits that the student is currently taking as well
		// must list ALL attributes bc including credit_taking, which is in table E
		// table E includes all the credits the student is currently taking
		String query= String.format("select S.SID, S.GIVENNAME, S.SURNAME, S.CREDIT_TAKEN, S.CREDIT_GRADUATE, E.CREDIT_TAKING"
				+ " from STUDENTS as S, (select SID, SUM(CREDIT) as CREDIT_TAKING from ENROLLMENT group by SID) as E"
				+ " where S.SID = E.SID and S.SURNAME like '%%%s%%' and S.CREDIT_TAKEN >= %d", namePrefix, credit_taken);
		
		Map<String, bean.StudentBean> rv = new HashMap<String, bean.StudentBean>();
		Connection con= this.ds.getConnection();
		PreparedStatement p= con.prepareStatement(query);
		ResultSet r= p.executeQuery();

		while(r.next()) {
			// set all attr
			String sid = r.getString("SID");
			String name= r.getString("GIVENNAME") + ", "+ r.getString("SURNAME");
			String creditGrad = r.getString("CREDIT_GRADUATE");
			String creditTaken = r.getString("CREDIT_TAKEN");
			String creditTaking = r.getString("CREDIT_TAKING");
			
			// create new StudentBean to set all attributes
			StudentBean sb = new StudentBean();
			
			sb.setSid(sid);	
			sb.setName(name);
			sb.setCredit_taken(Integer.parseInt(creditTaken));
			sb.setCredit_graduate(Integer.parseInt(creditGrad));
			sb.setCredit_taking(Integer.parseInt(creditTaking));

			// identify with a unique id sid
			rv.put(sid, sb);
		}
		r.close();
		p.close();
		con.close();
		return rv;
		}
	
	public DataSource getDS() {
		return this.ds;
	}

}
