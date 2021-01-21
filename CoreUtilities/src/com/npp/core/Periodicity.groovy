package com.pabslabs.core;

/**
 * User: jtanner
 * Date: Apr 17, 2009
 */
public enum Periodicity
{
	DAY ("Day"), WEEK("Week"), MONTH("Month");
  
	private String display;
	  
	Periodicity(String display) {
		this.display = display;		
	}
  
	public String toDisplayString() {
		return display;
	}    
}
