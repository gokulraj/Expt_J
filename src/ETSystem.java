import java.util.HashMap;
import java.util.Iterator;

import com.expt.db.DBUtil;
import com.expt_j.utils.CurrencyConversion;

//$Id$

public class ETSystem {

	public static void runDB() throws Exception{

		DBUtil db = new DBUtil();
		db.createTables();
	}

	public static void report1() throws Exception{

		// Creating Report 1;
		Reports r = new Reports();
		String reportid = r.createReport("Report1");

		Expense e = new Expense();	
		String expenseid = e.expenseReport(reportid, "55.00", "Lunch at Wangs", "LUNCH", "RAM", "2014-01-24");
		if ( expenseid != null ){
			e.deleteExpense(expenseid);
		}

		expenseid = e.expenseReport(reportid, "60.00", "COFFEE at Coffee Day", "COFFEE", "RAM,RAVI", "2014-01-24");
		expenseid = e.expenseReport(reportid, "240.60", "Lunch at Wangs", "LUNCH", "RAM", "2014-01-24");
		
		if ( e.generateReport(reportid) ){
			CurrencyConversion cc = new CurrencyConversion();
			System.out.println( "Expense Total INR = " + e.expense + " ( USD " +  + cc.convertINRToUSD(e.expense) +") ");

		}
	}
	
	public static void report2() throws Exception{

		// Creating Report 1;
		Reports r = new Reports();
		String reportid = r.createReport("Report2");

		Expense e = new Expense();	
		String expenseid = e.expenseReport(reportid, "400.00", "DINNER at Wangs", "DINNER", "RAM,RAVI,KRISHNA,AKIL,ANU", "2014-01-24");
		expenseid = e.expenseReport(reportid, "100.10", "Lunch at Wangs", "LUNCH", "RAM", "2014-01-24");
		
		if ( e.generateReport(reportid) ){
			CurrencyConversion cc = new CurrencyConversion();
			System.out.println( "Expense Total INR = " + e.expense + " ( USD " +  + cc.convertINRToUSD(e.expense) +") ");

		}
	}
	
	public static void report3() throws Exception{

		// Creating Report 1;
		Reports r = new Reports();
		String reportid = r.createReport("Report3");

		Expense e = new Expense();	
		String expenseid = e.expenseReport(reportid, "", "DINNER at Wangs", "DINNER", "RAM,RAVI", "2013-12-24");
	
		if ( expenseid != null && e.generateReport(reportid) ){
			CurrencyConversion cc = new CurrencyConversion();
			System.out.println( "Expense Total INR = " + e.expense + " ( USD " +  + cc.convertINRToUSD(e.expense) +") ");

		}
	}

	public static void report4() throws Exception{

		// Creating Report 1;
		Reports r = new Reports();
		String reportid = r.createReport("Report3");

		Expense e = new Expense();	
		String expenseid = e.expenseReport(reportid, "400.00", "DINNER at Wangs", "DINNER", "RAM,RAVI", "2013-12-24");
		expenseid = e.expenseReport(reportid, "100.10", "Lunch at Wangs", "LUNCH", "RAM", "");
		
		if ( e.generateReport(reportid) ){
			CurrencyConversion cc = new CurrencyConversion();
			System.out.println( "Expense Total INR = " + e.expense + " ( USD " +  + cc.convertINRToUSD(e.expense) +") ");

		}
	}

	public static void report5() throws Exception{

		// Creating Report 1;
		Reports r = new Reports();
		String reportid = r.getReportID("Report1");

		Expense e = new Expense();
		HashMap hm = e.getResult(reportid);
		Iterator itr = hm.keySet().iterator();
		HashMap expenseMap = null;
		Double amt = 0.00;
		CurrencyConversion cc = new CurrencyConversion();
		
		while(itr.hasNext()){
			expenseMap = (HashMap)hm.get(itr.next());
			amt = (Double) expenseMap.get("amount");
			e.setExpense(amt);
			System.out.print( "INR " + amt + " (USD " +  + cc.convertINRToUSD(e.expense) + ")");
			System.out.print( " " + expenseMap.get("category" ));
			System.out.print( " " + expenseMap.get("users" ));
			System.out.print( " " + expenseMap.get("expensedate" ) + "\n");
		}
	}

	public static void report6() throws Exception{

		Expense e = new Expense();
		HashMap hm = e.getResultByUsers("RAVI");
		Iterator itr = hm.keySet().iterator();
		HashMap expenseMap = null;
		Double amt = 0.00;
		CurrencyConversion cc = new CurrencyConversion();
		
		while(itr.hasNext()){
			expenseMap = (HashMap)hm.get(itr.next());
			amt = (Double) expenseMap.get("amount");
			e.setExpense(amt);
			System.out.print( "INR " + amt + " (USD " +  + cc.convertINRToUSD(e.expense) + ")");
			System.out.print( " " + expenseMap.get("category" ));
			System.out.print( " " + expenseMap.get("users" ));
			System.out.print( " " + expenseMap.get("expensedate" ) + "\n");
		}
		
	}

	public static void report7() throws Exception{

		Expense e = new Expense();
		HashMap hm = e.getResultByMonth("JAN-2014");
		Iterator itr = hm.keySet().iterator();
		HashMap expenseMap = null;
		Double amt = 0.00;
		CurrencyConversion cc = new CurrencyConversion();
		
		while(itr.hasNext()){
			expenseMap = (HashMap)hm.get(itr.next());
			amt = (Double) expenseMap.get("amount");
			e.setExpense(amt);
			System.out.print( "INR " + amt + " (USD " +  + cc.convertINRToUSD(e.expense) + ")");
			System.out.print( " " + expenseMap.get("category" ));
			System.out.print( " " + expenseMap.get("users" ));
			System.out.print( " " + expenseMap.get("expensedate" ) + "\n");
		}
		
	}
	public static void main(String[] args) throws Exception {

		runDB(); // Run this for first time
		//report1();
		//report2();
		//report3();
		//report4();
		//report5();
		//report6();
		//report7();
	}

}
