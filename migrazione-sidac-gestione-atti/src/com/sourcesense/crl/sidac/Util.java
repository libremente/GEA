package com.sourcesense.crl.sidac;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Util {

	
   
	
	
	
    public static boolean getBooleanFromChar(String input){
    	
    	if(input!=null && input!="" && (input.equals("N") || input.equals("Y") )){
    		
    		if(input.equals("Y")){
    			return true;
    		}else{
    			return false;
    		}
    				
    	}else{
    		return false;
    	}
    	
    }
    
    
    public static Hashtable<String, ArrayList<String>> extractingNumbers(String s) {

		String tipoAtto = "";

		Hashtable<String, ArrayList<String>> hash = new Hashtable<String, ArrayList<String>>();

		if (s.toUpperCase().contains("PDL")) {
			tipoAtto = "PDL";
		} else if (s.toUpperCase().contains("PLP")) {
			tipoAtto = "PLP";
		} else if (s.toUpperCase().contains("PRE")) {
			tipoAtto = "PRE";
		} else if (s.toUpperCase().contains("PDA")) {
			tipoAtto = "PDA";
		} else if (s.toUpperCase().contains("PAR")) {
			tipoAtto = "PAR";
		} else if (s.toUpperCase().contains("DOC")) {
			tipoAtto = "DOC";
		} else if (s.toUpperCase().contains("INP")) {
			tipoAtto = "INP";
		} else if (s.toUpperCase().contains("REF")) {
			tipoAtto = "REF";
		} else if (s.toUpperCase().contains("REL")) {
			tipoAtto = "REL";
		} else {
			return null;
		}

		ArrayList<String> numbers = new ArrayList<String>();
		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(s);
        
		while (m.find()) {
			numbers.add(m.group());
		}

		if (numbers.size() == 0) {
			return null;
		}
			
		hash.put(tipoAtto, numbers)	;
		return hash;	

	}


   
}
