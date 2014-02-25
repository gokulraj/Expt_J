package com.expt_j.utils;

import java.util.*;
import java.text.*;

public class DateTimeFormat {

	SimpleDateFormat ft;
	public static final String DB_FORMAT="yyyy-MM-dd";
	public static final String DB_DATETIEM_FORMAT="yyyy-MM-dd hh:mm:ss";

	public void setFormat(String f){
		this.ft = new SimpleDateFormat(f);
	}
	public DateTimeFormat(String f){
		setFormat(f);
	}

	public String convertDate(Date d){
		return ft.format(d);

	}

	public String convertToGMT(Date d){
		TimeZone gmtTime = TimeZone.getTimeZone("GMT");
		ft.setTimeZone(gmtTime);
		return ft.format(d);
	}

	public String getCurrentTimeInGMT(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		return convertToGMT(calendar.getTime());
	}

	public Date parseDate(String d) throws ParseException{
		return ft.parse(d); 
	}

	public static boolean compareWithDate(String d ) throws ParseException{

		Calendar current = Calendar.getInstance();
		Calendar compare = Calendar.getInstance();
		SimpleDateFormat sf = new SimpleDateFormat(DB_FORMAT);
		compare.setTime(sf.parse(d));
		int m1 = current.get(Calendar.YEAR) * 12 + current.get(Calendar.MONTH);
		int m2 =  compare.get(Calendar.YEAR) * 12 + compare.get(Calendar.MONTH);
		if ( m2 - m1 + 1 < 0 )
			return false;
		
		return true;
	}
}