package com.sourcesense.crl.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collection;

public class DataExtractor {

    private static Log logger = LogFactory.getLog(DataExtractor.class);

    public Collection<Councilor> getCouncilors() {
        logger.debug("Start of method 'extract'");
        Collection<Councilor> councilors = new ArrayList<Councilor>();

        Councilor c0 = new Councilor("GAROFALO", "COSIMO", "19", "PDL");
        c0.addCommittee("COMMISSIONE CACCIA E PESCA");
        c0.addCommittee("COMMISSIONE TURISMO");
        Councilor c1 = new Councilor("BALDELLI", "MARCCO", "19", "PD");
        c1.addCommittee("COMMISSIONE CACCIA E PESCA");
        c1.addCommittee("COMMISSIONE TURISMO");
        Councilor c2 = new Councilor("MENCONI", "GABRIELE", "19", "IDV");
        c2.addCommittee("COMMISSIONE ECONOMIA");
        c2.addCommittee("COMMISSIONE TURISMO");
        Councilor c3 = new Councilor("CONTI", "STEFANO", "19", "PDL");
        c3.addCommittee("COMMISSIONE ECONOMIA");
        c3.addCommittee("COMMISSIONE TURISMO");
        Councilor c4 = new Councilor("TREDENTI", "NONNO", "19", "IDV");
        c4.addCommittee("COMMISSIONE SALUTE");
        c4.addCommittee("COMMISSIONE POLITICHE GIOVANILI");
        Councilor c5 = new Councilor("DEFERRO", "ARTO", "19", "MOVIMENTO 5 STELLE");
        c5.addCommittee("COMMISSIONE CACCIA E PESCA");
        c5.addCommittee("COMMISSIONE POLITICHE GIOVANILI");
        Councilor c6 = new Councilor("PALO", "ALDO", "19", "VERDI");
        c6.addCommittee("COMMISSIONE ECONOMIA");
        c6.addCommittee("COMMISSIONE POLITICHE GIOVANILI");
        Councilor c7 = new Councilor("CAMMARANO", "PASQUALE", "19", "LEGA NORD");
        c7.addCommittee("COMMISSIONE SALUTE");
        c7.addCommittee("COMMISSIONE POLITICHE GIOVANILI");
        Councilor c8 = new Councilor("RATTO", "SABINA", "19", "LEGA NORD");
        c8.addCommittee("COMMISSIONE SALUTE");
        c8.addCommittee("COMMISSIONE POLITICHE GIOVANILI");
        Councilor c9 = new Councilor("LUZZO", "ANGELO", "19", "PARTITO V");
        c8.addCommittee("COMMISSIONE AGRICOLTURA");
        c8.addCommittee("COMMISSIONE SPORT");

        councilors.add(c0);
        councilors.add(c1);
        councilors.add(c2);
        councilors.add(c3);
        councilors.add(c4);
        councilors.add(c5);
        councilors.add(c6);
        councilors.add(c7);
        councilors.add(c8);
        councilors.add(c9);

        logger.debug("End of method 'extract'");
        return councilors;
    }

}
