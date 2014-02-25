//$Id$


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import com.expt.db.Connect;

public class Reports {

	public String createReport(String reportname) throws Exception{

		Connect c = new Connect();
		PreparedStatement stmt = null;
		Connection con = c.getCon();
		String reportid = null;
		try{
			
			String query = "select * from reports where reportname='" + reportname +"'";
			Statement smt = con.createStatement();
			ResultSet rs =  smt.executeQuery(query);
			System.out.println( " rs.getFetchSize() = " + rs.getFetchSize() );
			if ( rs.getFetchSize() == 0 )
			{
				query = "insert into reports(reportname) values(?)";
				con.setAutoCommit(false);
				stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, reportname);

				stmt.executeUpdate();
				rs = stmt.getGeneratedKeys();
				if (rs.next()){
					reportid = rs.getString(1);
				}

				con.commit();
			}
			

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){
			}// nothing we can do
			try{
				if(con!=null)
					con.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}

		return reportid;
	}
	
	public HashMap getReports() throws Exception{
		
		Connect c = new Connect();
		Statement stmt = null;
		Connection con = c.getCon();
		HashMap reportMap = new HashMap();

		try{

			String query = "select * from reports";
			stmt = con.createStatement();
			ResultSet rs =  stmt.executeQuery(query);
			while(rs.next()){
				reportMap.put("reportid", rs.getInt("reportid"));
				reportMap.put("reportname", rs.getString("reportname"));
			}

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){
			}// nothing we can do
			try{
				if(con!=null)
					con.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}

		return reportMap;
	}
	
	public String getReportID(String reportname) throws Exception{
		
		Connect c = new Connect();
		Statement stmt = null;
		Connection con = c.getCon();
		String reportid = null;

		try{

			String query = "select * from reports where reportname='" + reportname +"'";
			stmt = con.createStatement();
			ResultSet rs =  stmt.executeQuery(query);
			while(rs.next()){
				reportid = Integer.valueOf(rs.getInt("reportid")).toString();
			}

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){
			}// nothing we can do
			try{
				if(con!=null)
					con.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}

		return reportid;
	}

}
