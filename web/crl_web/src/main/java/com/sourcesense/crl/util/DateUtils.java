package com.sourcesense.crl.util;

import java.text.ParseException;
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

					Date dataCalc = sdf.parse("01/10/"
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

	public static Date getDataScadenzaPar(Date dataPresaInCarico, boolean isRegolamento, boolean sospensioneFeriale) {
		if (isRegolamento) {
			return generateDataScadenzaParRegolamento(dataPresaInCarico, sospensioneFeriale);
		} else {
			return generateDataScadenzaParDgr(dataPresaInCarico, sospensioneFeriale);
		}
	}

    private static Date generateDataScadenzaParRegolamento(Date dateIn, boolean sospensioneFeriale) {
        return generateDataScadenzaPar(60, dateIn, sospensioneFeriale);
    }

    private static Date generateDataScadenzaParDgr(Date dateIn, boolean sospensioneFeriale) {
        return generateDataScadenzaPar(30, dateIn, sospensioneFeriale);
    }

    private static Date generateDataScadenzaPar(int DELAY, Date dateIn, boolean sospensioneFeriale) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(dateIn);
        if (sospensioneFeriale) {
            final Date PRIMO_AGOSTO = primoAgostoDate(dateIn);
            final Date SEDICI_SETTEMBRE = sediciSettembreDate(dateIn);
            final boolean before = dateIn.before(PRIMO_AGOSTO);
            final boolean between = !before && dateIn.before(SEDICI_SETTEMBRE);
            final boolean after = !before && !between;
            if (before) {
                final int delta = numDaysBeetwen(dateIn, PRIMO_AGOSTO);
                if (delta > DELAY) {
                    calendar.add(Calendar.DAY_OF_YEAR, DELAY);
                } else if (delta > 0 && delta <= DELAY) {
                    calendar.add(Calendar.DAY_OF_YEAR, DELAY + 46);
                } else if (delta == 0) {
                    calendar.add(Calendar.DAY_OF_YEAR, DELAY);
                }
            } else
            if (between) {
                calendar.setTime(SEDICI_SETTEMBRE);
                calendar.add(Calendar.DAY_OF_YEAR, DELAY);
            } else {
                calendar.add(Calendar.DAY_OF_YEAR, DELAY);
            }
        } else {
            calendar.add(Calendar.DAY_OF_YEAR, DELAY);
        }
        return calendar.getTime();
    }

    private static int numDaysBeetwen(Date dateIn, Date startDate) {
        int numDays = 0;
        Calendar calendarDateIn = new GregorianCalendar();
        calendarDateIn.setTime(dateIn);

        while (calendarDateIn.getTime().before(startDate)) {
            calendarDateIn.add(Calendar.DAY_OF_YEAR, 1);
            numDays++;
        }

        return numDays;
    }

    private static Date primoAgostoDate(Date dateIn) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(dateIn);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dataPrimoAgosto;
        try {
            dataPrimoAgosto = sdf.parse("01/08/" + calendar.get(Calendar.YEAR));
        } catch (ParseException e) {
            throw new IllegalArgumentException("Unparsable Date");
        }
        return dataPrimoAgosto;
    }

    private static Date sediciSettembreDate(Date dateIn) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(dateIn);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dataSediciSettembre;
        try {
            dataSediciSettembre = sdf.parse("16/09/" + calendar.get(Calendar.YEAR));
        } catch (ParseException e) {
            throw new IllegalArgumentException("Unparsable Date");
        }
        return dataSediciSettembre;
    }

//    public static Date VgetDataScadenzaPar(Date dataPresaInCarico, boolean isRegolamento, boolean sospensioneFeriale) {
//        if (isRegolamento) {
//            return VgenerateDataScadenzaParRegolamento(dataPresaInCarico, sospensioneFeriale);
//        } else {
//            return VgenerateDataScadenzaParDgr(dataPresaInCarico, sospensioneFeriale);
//        }
//    }
//
//    private static Date VgenerateDataScadenzaParDgr(Date dateIn,
//			boolean sospensioneFeriale) {
//		try {
//
//			Calendar calendar = new GregorianCalendar();
//			calendar.setTime(dateIn);
//			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//
//			if (sospensioneFeriale) {
//
//				Calendar calendarAppo = new GregorianCalendar();
//				calendarAppo.setTime(dateIn);
//
//				Date dataStart = sdf.parse("01/08/"
//						+ calendar.get(Calendar.YEAR));
//
//				int contaMargin = 0;
//
//				while (calendarAppo.getTime().before(dataStart)) {
//					calendarAppo.add(Calendar.DAY_OF_YEAR, 1);
//					contaMargin++;
//				}
//
//				if (contaMargin >= 30) {
//
//					calendar.add(Calendar.DAY_OF_YEAR, 31);
//
//				} else if (contaMargin == 0) {
//
//					Date dataCalc = sdf.parse("16/10/"+ calendar.get(Calendar.YEAR));
//
//					calendar.setTime(dataCalc);
//
//				} else {
//
//					calendar.add(Calendar.DAY_OF_YEAR, 76);
//				}
//
//			} else {
//				calendar.add(Calendar.DAY_OF_YEAR, 31);
//			}
//
//			return calendar.getTime();
//
//		} catch (Exception e) {
//
//		}
//
//		return null;
//
//	}
//
//	private static Date VgenerateDataScadenzaParRegolamento(Date dateIn,
//			boolean sospensioneFeriale) {
//
//		try {
//
//			Calendar calendar = new GregorianCalendar();
//			calendar.setTime(dateIn);
//			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//
//			if (sospensioneFeriale) {
//
//				Calendar calendarAppo = new GregorianCalendar();
//				calendarAppo.setTime(dateIn);
//
//				Date dataStart = sdf.parse("01/08/"
//						+ calendar.get(Calendar.YEAR));
//
//				int contaMargin = 0;
//
//				while (calendarAppo.getTime().before(dataStart)) {
//					calendarAppo.add(Calendar.DAY_OF_YEAR, 1);
//					contaMargin++;
//				}
//
//				if (contaMargin >= 60) {
//
//					calendar.add(Calendar.DAY_OF_YEAR, 61);
//				} else if (contaMargin == 0) {
//
//					Date dataCalc = sdf.parse("15/11/"+ calendar.get(Calendar.YEAR));
//
//					calendar.setTime(dataCalc);
//                } else {
//                    calendar.add(Calendar.DAY_OF_YEAR, 106);
//				}
//
//			} else {
//
//				calendar.add(Calendar.DAY_OF_YEAR, 61);
//
//			}
//
//			return calendar.getTime();
//
//		} catch (Exception e) {
//
//		}
//
//		return null;
//	}
//}
}


