package com.sourcesense.crl.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {

	public Date generateDataScadenzaParDgr(Date date){
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		int i = 0;
		
		Calendar c = new GregorianCalendar();
		c.setTime (date);
				
//		c.set(Calendar.DAY_OF_MONTH, 15);
//		c.set(Calendar.MONTH, 6);
//		c.set(Calendar.YEAR, 2012);
		
		
		while (i<30){
			
			if (c.get(c.DAY_OF_WEEK) != Calendar.SUNDAY && c.get(c.DAY_OF_WEEK) != Calendar.SATURDAY && c.get(c.MONTH) != Calendar.AUGUST){
				if (!(c.get(c.MONTH) == Calendar.SEPTEMBER && c.get(c.DAY_OF_MONTH) < 16)) {
				
		    i ++;
		} }
			
			c.add(Calendar.DAY_OF_YEAR, 1);
			}
		
	
		//System.out.println(sdf.format( c.getTime() ));
		return new Date(sdf.format( c.getTime() ) );
			
	
	}
	
public Date generateDataScadenzaParRegolamento(Date date){
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		int i = 0;
		
		Calendar c = new GregorianCalendar();
		c.setTime (date);
				
//		c.set(Calendar.DAY_OF_MONTH, 15);
//		c.set(Calendar.MONTH, 6);
//		c.set(Calendar.YEAR, 2012);
		
		
		while (i<60){
			
			if (c.get(c.DAY_OF_WEEK) != Calendar.SUNDAY && c.get(c.DAY_OF_WEEK) != Calendar.SATURDAY && c.get(c.MONTH) != Calendar.AUGUST){
				if (!(c.get(c.MONTH) == Calendar.SEPTEMBER && c.get(c.DAY_OF_MONTH) < 16)) {
				
		    i ++;
		} }
			
			c.add(Calendar.DAY_OF_YEAR, 1);
			}
		
	
		//System.out.println(sdf.format( c.getTime() ));
		return new Date(sdf.format( c.getTime() ) );
			
	
	}
	
}
