//$Id$
package com.expt_j.utils;

import java.text.DecimalFormat;

public class CurrencyConversion {

	public static final double usdtoinr = 62.13;
	public static final double poundstoinr = 103.33;
	public static final String decimalformat = "###.##";
	String currency;
	DecimalFormat df;
	
	
	public CurrencyConversion(){
		this.currency = "USD";
		setDecimalFormat(decimalformat);
	}
	
	public String getDecimalFormat(){
		return this.df.toPattern();
	}
	
	public void setDecimalFormat(String sdf){
		this.df = new DecimalFormat(sdf);
	}
	
	public CurrencyConversion(String currency){
		this.currency = currency;
	}
	
	public void setCurrency(String currency){
		this.currency = currency;
	}
	
	public String getCurrency(){
		return this.currency;
	}
	
	public double convertUSDToINR(double v){
		return Double.valueOf(this.df.format(v * usdtoinr));
	}
	
	public double convertINRToUSD(double v){
		return Double.valueOf(this.df.format(v / usdtoinr ));
	}
	
	public double convertINRToGBP(double v){
		return Double.valueOf(this.df.format(v * poundstoinr));
	}
	
	public double convertGBPToGBP(double v){
		return Double.valueOf(this.df.format(v / poundstoinr));
	}
	
}
