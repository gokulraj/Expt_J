//$Id$
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;

import com.expt.db.Connect;
import com.expt.db.User;
import com.expt_j.utils.CurrencyConversion;
import com.expt_j.utils.DateTimeFormat;
public class Expense_new {

	Double amount;

	public Expense_new(){

	}

	public void expenseReport1() throws Exception{
		HashMap<String, String> expenseMap = new HashMap<String, String>();
		expenseMap.put("amount","55.00");
		expenseMap.put("notes","Lunch at wangs");
		expenseMap.put("category","LUNCH");
		expenseMap.put("users","RAM,RAVI,KRISHNA,AKIL,ANJU");
		expenseMap.put("currency", "INR");
		DateTimeFormat df = new DateTimeFormat(DateTimeFormat.DB_DATETIEM_FORMAT);
		expenseMap.put("createdtime", df.getCurrentTimeInGMT());
		expenseMap.put("modifiedtime", df.getCurrentTimeInGMT());
		df.setFormat(DateTimeFormat.DB_FORMAT);
		expenseMap.put("expensedate" , df.convertDate(df.parseDate("2013-12-24")).toString());
		expenseMap.put("reportname", "Report 1");

		if ( validate(expenseMap) ){
			String expenseid =  addExpense(expenseMap);

//			if ( expenseid != null ){
//				deleteExpense(expenseid);
//			}
		}
	}
	
	
	public boolean generateReport(String reportname) throws ParseException{
		HashMap reportsMap = getResult(reportname);
		Iterator it = (Iterator) reportsMap.keySet().iterator();
		HashMap expensemap = null;
		double expenseAmount  = 0;
		while(it.hasNext()){
			expensemap = (HashMap)reportsMap.get(it.next());
			if(!validateReport(expensemap)){
				return false;
			}
			else{
				expenseAmount = expenseAmount + Double.parseDouble(expensemap.get("amount").toString());
			}
		}
		this.expense = expenseAmount;
		return true;
	}

	public HashMap<String, HashMap<String, String>> getResult(String reportname){

		Connect c = new Connect();
		PreparedStatement stmt = null;
		Connection con = c.getCon();
		HashMap expenseMap = null;
		HashMap reportMap = new HashMap();

		try{

			String query = "select * from expense where reportname= ? ";
			stmt = con.prepareStatement(query);
			stmt.setString(1, reportname);
			ResultSet rs =  stmt.executeQuery();
			while(rs.next()){
				expenseMap = new HashMap<String, String>();
				expenseMap.put("amount", rs.getDouble("amount"));
				expenseMap.put("users", rs.getString("users"));
				expenseMap.put("expensedate", rs.getString("expensedate"));
				expenseMap.put("category", rs.getString("category"));
				reportMap.put( "row" + rs.getRow() , expenseMap );
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

	public boolean validate(HashMap<String, String> expenseMap) throws ParseException{
		if ( Double.parseDouble(expenseMap.get("amount")) < 0 ){
			System.out.println(" Amount can not be negative ");
			return false;
		}
		return true;
	}

	public boolean validateReport(HashMap expenseMap) throws ParseException{

		Double amount = (Double)expenseMap.get("amount");
		String[] users = expenseMap.get("users").toString().split(",");
		if ( amount.doubleValue() < 0 ){
			System.out.println(" Amount can not be negative ");
			return false;
		}
		else if (!DateTimeFormat.compareWithDate(expenseMap.get("expensedate").toString())){
			System.out.println(" Report can not be generate for past month");
			return false;
		}
		else if ( users.length > 4 ){
			System.out.println(" For More than 4 users reports can not be generated");
			return false;
		}
		return true;

	}

	public void updateExpense(HashMap<String, String> expenseMap) throws Exception{

		Connect c = new Connect();
		PreparedStatement stmt = null;
		Connection con = c.getCon();
		try{

			String query = " delete from expense where expenseid=?";
			System.out.println ( expenseMap );
			con.setAutoCommit(false);
			//stmt = con.prepareStatement(getQuery());
			stmt.setDouble(1, Double.parseDouble(expenseMap.get("expenseid")));
			stmt.executeUpdate();
			con.commit();

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

	}

	public void deleteExpense(String expenseid) throws Exception{

		Connect c = new Connect();
		PreparedStatement stmt = null;
		Connection con = c.getCon();
		try{

			String query = " delete from expense where expenseid=?";
			con.setAutoCommit(false);
			stmt = con.prepareStatement(query);
			stmt.setInt(1, Integer.parseInt(expenseid));
			stmt.executeUpdate();
			con.commit();

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

	}

	public void deleteExpense(HashMap<String, String> expenseMap) throws Exception{

		Connect c = new Connect();
		PreparedStatement stmt = null;
		Connection con = c.getCon();
		try{

			String query = " delete from expense where amount=? and users=?"
					+ "values(?,?,?,?,?,?,?,?)";
			System.out.println ( expenseMap );
			con.setAutoCommit(false);
			//stmt = con.prepareStatement(getQuery());
			stmt.setDouble(1, Double.parseDouble(expenseMap.get("amount")));
			stmt.setString(2, expenseMap.get("users"));
			stmt.executeUpdate();
			con.commit();

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

	}

	public String addExpense(HashMap<String, String> expenseMap) throws Exception{

		Connect c = new Connect();
		PreparedStatement stmt = null;
		Connection con = c.getCon();
		String expenseid = null;
		try{
			System.out.println ( expenseMap );
			String query = "insert into expense(amount,notes,category,users, currency,expensedate,createdtime,modifiedtime,reportname)"
					+ "values(?,?,?,?,?,?,?,?,?)";
			con.setAutoCommit(false);
			stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmt.setDouble(1, Double.parseDouble(expenseMap.get("amount")));
			stmt.setString(2, expenseMap.get("notes"));
			stmt.setString(3, expenseMap.get("category"));
			stmt.setString(4, expenseMap.get("users"));
			stmt.setString(5, expenseMap.get("currency"));
			stmt.setString(6, expenseMap.get("expensedate"));
			stmt.setString(7, expenseMap.get("createdtime"));
			stmt.setString(8, expenseMap.get("modifiedtime"));
			stmt.setString(9, expenseMap.get("reportname"));

			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()){
				expenseid = rs.getString(1);
			}

			con.commit();

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

		return expenseid;
	}

	double expense;

	public double getExpense() {
		return expense;
	}

	public void setExpense(double expense) {
		this.expense = expense;
	} 


}
