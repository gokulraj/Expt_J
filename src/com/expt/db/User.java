//$Id$
package com.expt.db;


import java.sql.SQLException;
import java.util.HashMap;
import java.sql.PreparedStatement;
import java.util.Map;

public class User {
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public HashMap<String, String> getUsermap() {
		return usermap;
	}
	public void setUsermap(HashMap<String, String> usermap) {
		if ( usermap == null ){
			usermap.put("username", this.username );
			usermap.put("firstname", this.firstname );
			usermap.put("lastname", this.lastname );
			usermap.put("email", this.email );
			usermap.put("userid", this.userid );
		} 
		this.usermap = usermap;
	}
	
	public String getQuery(){
		String query = "insert into user(username,email,firstname,lastname) values(?, ?,?,?)";
		return query;
	}
	
	public void createUser(HashMap<String, String> usermap) throws Exception{
		
		Connect c = new Connect();
		PreparedStatement stmt = null;
			
		try{
			
			c.getCon().setAutoCommit(false);
			stmt = c.getCon().prepareStatement(getQuery());
			stmt.setString(1, usermap.get("username"));
			stmt.setString(2, usermap.get("email"));
			stmt.setString(3, usermap.get("firstname"));
			stmt.setString(4, usermap.get("lastname"));
			stmt.executeUpdate();
            c.getCon().commit();
        
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
				if(c.getCon()!=null)
					c.getCon().close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
		
	}
	
	String username;
	String firstname;
	String lastname;
	String email;
	String userid;
	HashMap<String, String> usermap;
	
}
