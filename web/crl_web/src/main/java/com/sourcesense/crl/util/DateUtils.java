package com.sourcesense.crl.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {

	public static Date generateDataScadenzaParDgrInterruzione(Date dateIn,
			boolean sospensioneFeriale) {

		try {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(dateIn);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			if (sospensioneFeriale) {

				Calendar calendarAppo = new GregorianCalendar();
				calendarAppo.setTime(dateIn);

				Date dataStart = sdf.parse("01/08/"
						+ calendar.get(Calendar.YEAR));

				int contaMargin = 0;

				while (calendarAppo.getTime().before(dataStart)) {
					calendarAppo.add(Calendar.DAY_OF_YEAR, 1);
					contaMargin++;
				}

				if (contaMargin >= 14) {

					calendar.add(Calendar.DAY_OF_YEAR, 15);

				} else if (contaMargin == 0) {

					Date dataCalc = sdf.parse("30/09/"
							+ calendar.get(Calendar.YEAR));
					calendar.setTime(dataCalc);

				} else {

					calendar.add(Calendar.DAY_OF_YEAR, 60);
				}

			} else {

				calendar.add(Calendar.DAY_OF_YEAR, 15);

			}

			return calendar.getTime();

		} catch (Exception e) {

		}

		return null;

	}

	public static Date getDataScadenzaPar(Date dataPresaInCarico,
			boolean isRegolamento, boolean sospensioneFeriale) {

		if (isRegolamento) {

			return generateDataScadenzaParRegolamento(dataPresaInCarico,
					sospensioneFeriale);

		} else {

			return generateDataScadenzaParDgr(dataPresaInCarico,
					sospensioneFeriale);

		}

	}

	private static Date generateDataScadenzaParDgr(Date dateIn,
			boolean sospensioneFeriale) {
		try {

			Calendar calendar = new GregorianCalendar();
			calendar.setTime(dateIn);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			if (sospensioneFeriale) {

				Calendar calendarAppo = new GregorianCalendar();
				calendarAppo.setTime(dateIn);

				Date dataStart = sdf.parse("01/08/"
						+ calendar.get(Calendar.YEAR));

				int contaMargin = 0;

				while (calendarAppo.getTime().before(dataStart)) {
					calendarAppo.add(Calendar.DAY_OF_YEAR, 1);
					contaMargin++;
				}

				if (contaMargin >= 29) {

					calendar.add(Calendar.DAY_OF_YEAR, 30);

				} else if (contaMargin == 0) {

					Date dataCalc = sdf.parse("15/10/"+ calendar.get(Calendar.YEAR));
					
					calendar.setTime(dataCalc);

				} else {

					calendar.add(Calendar.DAY_OF_YEAR, 75);
				}

			} else {

				calendar.add(Calendar.DAY_OF_YEAR, 30);

			}

			return calendar.getTime();

		} catch (Exception e) {

		}

		return null;

	}

	private static Date generateDataScadenzaParRegolamento(Date dateIn,
			boolean sospensioneFeriale) {

		try {

			Calendar calendar = new GregorianCalendar();
			calendar.setTime(dateIn);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			if (sospensioneFeriale) {

				Calendar calendarAppo = new GregorianCalendar();
				calendarAppo.setTime(dateIn);

				Date dataStart = sdf.parse("01/08/"
						+ calendar.get(Calendar.YEAR));

				int contaMargin = 0;

				while (calendarAppo.getTime().before(dataStart)) {
					calendarAppo.add(Calendar.DAY_OF_YEAR, 1);
					contaMargin++;
				}

				if (contaMargin >= 59) {

					calendar.add(Calendar.DAY_OF_YEAR, 60);
				} else if (contaMargin == 0) {

					Date dataCalc = sdf.parse("14/11/"+ calendar.get(Calendar.YEAR));
					
					calendar.setTime(dataCalc);	

				} else {

					calendar.add(Calendar.DAY_OF_YEAR, 105);
				}

			} else {

				calendar.add(Calendar.DAY_OF_YEAR, 60);

			}

			return calendar.getTime();

		} catch (Exception e) {

		}

		return null;
	}
}
