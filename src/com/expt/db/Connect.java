//$Id$
package com.expt.db;

import java.sql.*;

public class Connect {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/expense";

	static final String USER = "root";
	static final String PASS = "";
	private Connection con = null;
	Statement state = null;
	
	public Connect(){
		try{
			Class.forName(JDBC_DRIVER);
			setCon(DriverManager.getConnection(DB_URL,USER,PASS));
		}catch(SQLException e ){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		
	}
	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}
}	
