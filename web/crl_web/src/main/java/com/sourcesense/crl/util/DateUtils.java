package com.sourcesense.crl.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {

	public static Date getDataScadenzaPar(Date dataPresaInCarico,
			boolean isRegolamento) {

		if (isRegolamento) {

			return generateDataScadenzaParRegolamento(dataPresaInCarico);	

		} else {

			return generateDataScadenzaParDgr(dataPresaInCarico);
		
		}

	}

	private static Date generateDataScadenzaParDgr(Date dateIn) {
		try {

			Calendar calendar = new GregorianCalendar();
			calendar.setTime(dateIn);
			calendar.add(Calendar.DAY_OF_YEAR, 30);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			
			Date dataStart = sdf.parse("01/08/" + dateIn.getYear());
			
			Date dataEnd = sdf.parse("15/09/" + dateIn.getYear());
			
			while (calendar.getTime().after(dataStart)
					&& calendar.getTime().before(dataEnd)) {

				calendar.add(Calendar.DAY_OF_YEAR, 1);
			}	
				

			return calendar.getTime();


		} catch (Exception e) {

		}

		return null;

	}

	private static Date generateDataScadenzaParRegolamento(Date dateIn) {

		try {

			Calendar calendar = new GregorianCalendar();
			calendar.setTime(dateIn);
			calendar.add(Calendar.DAY_OF_YEAR, 60);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			
			Date dataStart = sdf.parse("01/08/" + dateIn.getYear());
			
			Date dataEnd = sdf.parse("15/09/" + dateIn.getYear());
			
			while (calendar.getTime().after(dataStart)
					&& calendar.getTime().before(dataEnd)) {

				calendar.add(Calendar.DAY_OF_YEAR, 1);
			}	
				

			return calendar.getTime();


		} catch (Exception e) {

		}
		return null;
	}
}
