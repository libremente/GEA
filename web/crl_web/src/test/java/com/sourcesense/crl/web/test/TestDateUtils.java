package com.sourcesense.crl.web.test;


import com.sourcesense.crl.util.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TestDateUtils {
    /*static String[] sDate          = new String[]{"30/12/2013", "31/12/2013", "31/01/2014", "01/03/2014", "29/06/2014", "30/06/2014", "01/07/2014", "02/07/2014", "03/07/2014", "30/07/2014", "31/07/2014", "16/09/2014", "16/10/2014", "01/12/2014", "02/12/2014"};
    static String[] expSDate_30wsf = new String[]{"29/01/2014", "30/01/2014", "02/03/2014", "31/03/2014", "29/07/2014", "30/07/2014", "31/07/2014", "16/09/2014", "17/09/2014", "14/10/2014", "15/10/2014", "16/10/2014", "15/11/2014", "31/12/2014", "01/01/2015"};
    static String[] expSDate_30nsf = new String[]{"29/01/2014", "30/01/2014", "02/03/2014", "31/03/2014", "29/07/2014", "30/07/2014", "31/07/2014", "01/08/2014", "02/08/2014", "29/08/2014", "30/08/2014", "16/10/2014", "15/11/2014", "31/12/2014", "01/01/2015"};
    static String[] expSDate_60wsf = new String[]{"28/02/2014", "01/03/2014", "01/04/2014", "30/04/2014", "13/10/2014", "14/10/2014", "15/10/2014", "16/10/2014", "17/10/2014", "13/11/2014", "14/11/2014", "15/11/2014", "15/12/2014", "30/01/2015", "31/01/2015"};
    static String[] expSDate_60nsf = new String[]{"28/02/2014", "01/03/2014", "01/04/2014", "30/04/2014", "28/08/2014", "29/08/2014", "30/08/2014", "31/08/2014", "01/09/2014", "28/09/2014", "29/09/2014", "15/11/2014", "15/12/2014", "30/01/2015", "31/01/2015"};

    static boolean isScadenza60gg = true;
    static boolean isSospensioneFeriale = true;

    public static void main(String[] args) {
        System.out.println("-----------------------------Vecchia Procedura-----------------------------");
        System.out.println("Tests [30gg con sospensione]:");

        for (int i = 0; i < sDate.length; i++) {
            Date date = DateUtils.getDataScadenzaPar(getDate(sDate[i]), !isScadenza60gg, isSospensioneFeriale);
            System.out.println("Input: " + sDate[i] + " Expected: " + expSDate_30wsf[i] + " Obtained: " + getSDate(date) + " Test " + (expSDate_30wsf[i].equals(getSDate(date)) ? "OK" : "KO --"));
        }

        System.out.println();
        System.out.println("Tests [30gg  no sospensione]:");

        for (int i = 0; i < sDate.length; i++) {
            Date date = DateUtils.getDataScadenzaPar(getDate(sDate[i]), !isScadenza60gg, !isSospensioneFeriale);
            System.out.println("Input: " + sDate[i] + " Expected: " + expSDate_30nsf[i] + " Obtained: " + getSDate(date) + " Test " + (expSDate_30nsf[i].equals(getSDate(date)) ? "OK" : "KO --"));
        }

        System.out.println();
        System.out.println("Tests [60gg con sospensione]:");

        for (int i = 0; i < sDate.length; i++) {
            Date date = DateUtils.getDataScadenzaPar(getDate(sDate[i]), isScadenza60gg, isSospensioneFeriale);
            System.out.println("Input: " + sDate[i] + " Expected: " + expSDate_60wsf[i] + " Obtained: " + getSDate(date) + " Test " + (expSDate_60wsf[i].equals(getSDate(date)) ? "OK" : "KO --"));
        }

        System.out.println();
        System.out.println("Tests [60gg  no sospensione]:");

        for (int i = 0; i < sDate.length; i++) {
            Date date = DateUtils.getDataScadenzaPar(getDate(sDate[i]), isScadenza60gg, !isSospensioneFeriale);
            System.out.println("Input: " + sDate[i] + " Expected: " + expSDate_60nsf[i] + " Obtained: " + getSDate(date) + " Test " + (expSDate_60nsf[i].equals(getSDate(date)) ? "OK" : "KO --"));
        }
        System.out.println("-----------------------------Vecchia Procedura-----------------------------");

        System.out.println("-----------------------------Nuova   Procedura-----------------------------");
        System.out.println("Tests [30gg con sospensione]:");

        for (int i = 0; i < sDate.length; i++) {
            Date date = getDataScadenzaPar(getDate(sDate[i]), !isScadenza60gg, isSospensioneFeriale);
            System.out.println("Input: " + sDate[i] + " Expected: " + expSDate_30wsf[i] + " Obtained: " + getSDate(date) + " Test " + (expSDate_30wsf[i].equals(getSDate(date)) ? "OK" : "KO --"));
        }

        System.out.println();
        System.out.println("Tests [30gg  no sospensione]:");

        for (int i = 0; i < sDate.length; i++) {
            Date date = getDataScadenzaPar(getDate(sDate[i]), !isScadenza60gg, !isSospensioneFeriale);
            System.out.println("Input: " + sDate[i] + " Expected: " + expSDate_30nsf[i] + " Obtained: " + getSDate(date) + " Test " + (expSDate_30nsf[i].equals(getSDate(date)) ? "OK" : "KO --"));
        }

        System.out.println();
        System.out.println("Tests [60gg con sospensione]:");

        for (int i = 0; i < sDate.length; i++) {
            Date date = getDataScadenzaPar(getDate(sDate[i]), isScadenza60gg, isSospensioneFeriale);
            System.out.println("Input: " + sDate[i] + " Expected: " + expSDate_60wsf[i] + " Obtained: " + getSDate(date) + " Test " + (expSDate_60wsf[i].equals(getSDate(date)) ? "OK" : "KO --"));
        }

        System.out.println();
        System.out.println("Tests [60gg  no sospensione]:");

        for (int i = 0; i < sDate.length; i++) {
            Date date = getDataScadenzaPar(getDate(sDate[i]), isScadenza60gg, !isSospensioneFeriale);
            System.out.println("Input: " + sDate[i] + " Expected: " + expSDate_60nsf[i] + " Obtained: " + getSDate(date) + " Test " + (expSDate_60nsf[i].equals(getSDate(date)) ? "OK" : "KO --"));
        }
        System.out.println("-----------------------------Nuova   Procedura-----------------------------");

    }

    private static Date getDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("dd/MM/yyyy");
        Date parsedDate;
        try {
            parsedDate = sdf.parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Not a parsable date: " + date);
        }
        return parsedDate;
    }

    private static String getSDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("dd/MM/yyyy");
        String sDate = sdf.format(date);
        return sDate;
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
            final boolean before = dateIn.before(PRIMO_AGOSTO);
            final int delta = numDaysBeetwen(dateIn, PRIMO_AGOSTO);
            if (delta > DELAY) {
                calendar.add(Calendar.DAY_OF_YEAR, DELAY);
            } else if (delta > 0 && delta <= DELAY) {
                calendar.add(Calendar.DAY_OF_YEAR, DELAY + 46);
            } else if (delta == 0) {
                int delay = DELAY;
                if (before) delay += 46;
                calendar.add(Calendar.DAY_OF_YEAR, delay);
            }
        } else {
            calendar.add(Calendar.DAY_OF_YEAR, DELAY);
        }
        return calendar.getTime();
    }


    // Scadenza a 60 giorni
//    private static Date generateDataScadenzaParRegolamento(Date dateIn, boolean sospensioneFeriale) {
//        final int DELAY = 60;
//        Calendar calendar = new GregorianCalendar();
//        calendar.setTime(dateIn);
//        if (sospensioneFeriale) {
//            final Date PRIMO_AGOSTO = primoAgostoDate(dateIn);
//            final boolean before = dateIn.before(PRIMO_AGOSTO);
//            final int delta = numDaysBeetwen(dateIn, PRIMO_AGOSTO);
//            if (delta > DELAY) {
//                calendar.add(Calendar.DAY_OF_YEAR, DELAY);
//            } else if (delta > 0 && delta <= DELAY) {
//                calendar.add(Calendar.DAY_OF_YEAR, DELAY + 46);
//            } else if (delta == 0) {
//                int delay = DELAY;
//                if (before) delay += 46;
//                calendar.add(Calendar.DAY_OF_YEAR, delay);
//            }
//        } else {
//            calendar.add(Calendar.DAY_OF_YEAR, DELAY);
//        }
//        return calendar.getTime();
//    }

    // Scadenza a 30 giorni
//    private static Date generateDataScadenzaParDgr(Date dateIn, boolean sospensioneFeriale) {
//        final int DELAY = 30;
//        Calendar calendar = new GregorianCalendar();
//        calendar.setTime(dateIn);
//        if (sospensioneFeriale) {
//            final Date PRIMO_AGOSTO = primoAgostoDate(dateIn);
//            final boolean before = dateIn.before(PRIMO_AGOSTO);
//            final int delta = numDaysBeetwen(dateIn, PRIMO_AGOSTO);
//            if (delta > DELAY) {
//                calendar.add(Calendar.DAY_OF_YEAR, DELAY);
//            } else if (delta > 0 && delta <= DELAY) {
//                calendar.add(Calendar.DAY_OF_YEAR, DELAY + 46);
//            } else if (delta == 0) {
//                int delay = DELAY;
//                if (before) delay += 46;
//                calendar.add(Calendar.DAY_OF_YEAR, delay);
//            }
//        } else {
//            calendar.add(Calendar.DAY_OF_YEAR, DELAY);
//        }
//        return calendar.getTime();
//    }

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
*/
}
