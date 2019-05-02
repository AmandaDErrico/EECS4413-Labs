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
		String query= "select * from students where surname like '%"+ namePrefix+"%' and credit_taken >= "+ credit_taken;
		Map<String, bean.StudentBean> rv = new HashMap<String, bean.StudentBean>();
		Connection con= this.ds.getConnection();
		PreparedStatement p= con.prepareStatement(query);
		ResultSet r= p.executeQuery();

		while(r.next()) {
			String sid = (r.getString("SID")).trim();
			String name= (r.getString("GIVENNAME") + ", "+ r.getString("SURNAME")).trim();
			String creditGrad = r.getString("CREDIT_GRADUATE");
			String creditTaken = r.getString("CREDIT_TAKEN");
			StudentBean sb = new StudentBean();		
			sb.setCredit_taken(Integer.parseInt(creditTaken));
			sb.setCredit_grad(Integer.parseInt(creditGrad));
			sb.setName(name);
			sb.setSid(sid);
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
