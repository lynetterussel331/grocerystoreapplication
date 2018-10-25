package com.titusgt.grocerystoreapplication.utils;

public class Validation {

	public static boolean isNumeric(String str)  
	{  
		try {  
			Integer.parseInt(str);  
		}  
		catch(NumberFormatException nfe) {
			return false;  
		}
		return true;  
	}
	
	public static boolean isDecimal(String str)  
	{  
		try {  
			Double.parseDouble(str);  
		}  
		catch(NumberFormatException nfe) {
			return false;  
		}
		return true;  
	}
}
