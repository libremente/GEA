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

				if (contaMargin >= 15) {

					calendar.add(Calendar.DAY_OF_YEAR, 16);

				} else if (contaMargin == 0) {

					Date dataCalc = sdf.parse("30/09/"
							+ calendar.get(Calendar.YEAR));
					calendar.setTime(dataCalc);

				} else {

					calendar.add(Calendar.DAY_OF_YEAR, 61);
				}

			} else {

				calendar.add(Calendar.DAY_OF_YEAR, 16);

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

				if (contaMargin >= 30) {

					calendar.add(Calendar.DAY_OF_YEAR, 31);

				} else if (contaMargin == 0) {

					Date dataCalc = sdf.parse("15/10/"+ calendar.get(Calendar.YEAR));
					
					calendar.setTime(dataCalc);

				} else {

					calendar.add(Calendar.DAY_OF_YEAR, 76);
				}

			} else {

				calendar.add(Calendar.DAY_OF_YEAR, 31);

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

				if (contaMargin >= 60) {

					calendar.add(Calendar.DAY_OF_YEAR, 61);
				} else if (contaMargin == 0) {

					Date dataCalc = sdf.parse("14/11/"+ calendar.get(Calendar.YEAR));
					
					calendar.setTime(dataCalc);	

				} else {

					calendar.add(Calendar.DAY_OF_YEAR, 106);
				}

			} else {

				calendar.add(Calendar.DAY_OF_YEAR, 61);

			}

			return calendar.getTime();

		} catch (Exception e) {

		}

		return null;
	}
}
