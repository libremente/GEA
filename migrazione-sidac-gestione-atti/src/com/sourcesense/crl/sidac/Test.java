package com.sourcesense.crl.sidac;

import java.util.regex.*;
import java.util.ArrayList;
import java.util.Hashtable;

public class Test {

	public static Hashtable<String, ArrayList<Integer>> extractingNumbers(String s) {

		String tipoAtto = "";

		Hashtable<String, ArrayList<Integer>> hash = new Hashtable<String, ArrayList<Integer>>();

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

		ArrayList<Integer> numbers = new ArrayList<Integer>();
		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(s);

		while (m.find()) {
			numbers.add(Integer.parseInt(m.group()));
		}

		if (numbers.size() == 0) {
			
			
			return null;
		}
			
		hash.put(tipoAtto, numbers)	;
		return hash;	

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Hashtable<String, ArrayList<Integer>> arr = extractingNumbers("Abbinato ai PDL nn. 1/28/46/92/127.");

		String tipoAtto="";
		
		for (String string : arr.keySet()) {
		
			tipoAtto =string;
		}
		
		System.out.println(tipoAtto);
		
        		  
		for (Integer integ : arr.get(tipoAtto)) {
			System.out.println(integ);
		}

		

	}

}
