package DAO;

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
import bean.StudentBean;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class EnrollmentDAO {
	private DataSource ds;
	
	public EnrollmentDAO() throws ClassNotFoundException {
	try {
		ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
	} catch (NamingException e) {
		e.printStackTrace();
	}
}

	public Map<String, bean.EnrollmentBean> retrieve(String name_prefix, int credits) throws SQLException {
		String query= "select * from enrollment where sid in (select sid from students "
				+ "where surname like '%"+ name_prefix + "%' and credit_taken <= " + credits+")";
		Map<String, bean.EnrollmentBean> rv = new HashMap<String, bean.EnrollmentBean>();
		Connection con= this.ds.getConnection();
		PreparedStatement p= con.prepareStatement(query);
		ResultSet r= p.executeQuery();

		String cid = r.getString("CID");
		String sid = r.getString("SID");
		int credit = r.getInt("CREDIT");
		while(r.next()){
			// add the student if the course exists already
			if (rv.get(cid) != null){
				rv.get(cid).getStudents().add(sid);
			} else {
						
				// add a new eb with the cid
				List <String> sids = new ArrayList<String>();
				sids.add(sid);
				
				EnrollmentBean eb = new EnrollmentBean();
				
				eb.setCredit(credit);
				eb.setStudents(sids);
				eb.setCid(cid);
				
				rv.put(cid, eb);
			}		
		}
		r.close();
		p.close();
		con.close();
		return rv;
}

}
