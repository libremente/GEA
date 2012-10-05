package com.sourcesense.crl.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class DataExtractor {

    private static Log logger = LogFactory.getLog(DataExtractor.class);

    private DataSource dataSource;


    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Legislature getCurrentLegislature() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(Constant.QUERY_CURRENT_LEGISLATURE);
            ResultSet rs = ps.executeQuery();
            Legislature legislature = null;
            if (rs.next()) {
                legislature = new Legislature();
                legislature.setId(rs.getInt(Constant.COLUMN_LEGISLATURE_ID));
                legislature.setNumber(rs.getString(Constant.COLUMN_LEGISLATURE_NUMBER));
                legislature.setFrom(rs.getDate(Constant.COLUMN_LEGISLATURE_FROM));
                legislature.setTo(rs.getDate(Constant.COLUMN_LEGISLATURE_TO));
            }
            rs.close();
            ps.close();
            return legislature;
        } catch (SQLException e) {
            logger.error("Cannot get current legislature id", e);
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    // Do nothing...
                }
            }
        }
    }

//    public Legislature getCurrentLegislature() {
//        Legislature legislature = new Legislature();
//        legislature.setId(1);
//        legislature.setNumber("IX");
//        legislature.setFrom(new Date());
//        legislature.setTo(null);
//        return legislature;
//    }

    public List<Councilor> getCouncilors(int idCurrentLegislature) {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(Constant.QUERY_ALL_COUNCILORS_FILTERED_BY_LEGISLATURE);
            ps.setInt(1, idCurrentLegislature);
            ps.setInt(2, idCurrentLegislature);
            List<Councilor> councilors = new ArrayList<Councilor>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Councilor councilor = new Councilor();
                councilor.setFirstName(rs.getString(Constant.COLUMN_NOME));
                councilor.setLastName(rs.getString(Constant.COLUMN_COGNOME));
                councilor.setGroupName(rs.getString(Constant.COLUMN_NOME_GRUPPO));
                councilor.setLegislatureNumber(rs.getString(Constant.COLUMN_NUMERO_LEGISLATURA));
                String committes = rs.getString(Constant.COLUMN_ORGANI);
                if (committes != null && !"".equals(committes) && !",".equals(committes)) {
                    StringTokenizer st = new StringTokenizer(committes, ",");
                    while (st.hasMoreTokens()) {
                        councilor.addCommittee(st.nextToken());
                    }
                }
                councilors.add(councilor);
            }
            rs.close();
            ps.close();
            return councilors;
        } catch (SQLException e) {
            logger.error("Cannot get all councilors of current legislature", e);
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    // Do nothing...
                }
            }
        }
    }

//    public List<Councilor> getCouncilors(int legislatureId) {
//        logger.debug("Start of method 'extract'");
//        List<Councilor> councilors = new ArrayList<Councilor>();
//
//        Councilor c0 = new Councilor("GAROFALO", "COSIMO", "IX", "PDL");
//        c0.addCommittee("COMMISSIONE CACCIA E PESCA");
//        c0.addCommittee("COMMISSIONE TURISMO");
//        Councilor c1 = new Councilor("BALDELLI", "MARCO", "IX", "PD");
//        c1.addCommittee("COMMISSIONE CACCIA E PESCA");
//        c1.addCommittee("COMMISSIONE TURISMO");
//        Councilor c2 = new Councilor("MENCONI", "GABRIELE", "IX", "IDV");
//        c2.addCommittee("COMMISSIONE ECONOMIA");
//        c2.addCommittee("COMMISSIONE TURISMO");
//        Councilor c3 = new Councilor("CONTI", "STEFANO", "IX", "PDL");
//        c3.addCommittee("COMMISSIONE ECONOMIA");
//        c3.addCommittee("COMMISSIONE TURISMO");
//        Councilor c4 = new Councilor("TREDENTI", "NONNO", "IX", "IDV");
//        c4.addCommittee("COMMISSIONE SALUTE");
//        c4.addCommittee("COMMISSIONE POLITICHE GIOVANILI");
//        Councilor c5 = new Councilor("DEFERRO", "ARTO", "IX", "MOVIMENTO 5 STELLE");
//        c5.addCommittee("COMMISSIONE CACCIA E PESCA");
//        c5.addCommittee("COMMISSIONE POLITICHE GIOVANILI");
//        Councilor c6 = new Councilor("PALO", "ALDO", "IX", "VERDI");
//        c6.addCommittee("COMMISSIONE ECONOMIA");
//        c6.addCommittee("COMMISSIONE POLITICHE GIOVANILI");
//        Councilor c7 = new Councilor("CAMMARANO", "PASQUALE", "IX", "LEGA NORD");
//        c7.addCommittee("COMMISSIONE SALUTE");
//        c7.addCommittee("COMMISSIONE POLITICHE GIOVANILI");
//        Councilor c8 = new Councilor("RATTO", "SABINA", "IX", "LEGA NORD");
//        c8.addCommittee("COMMISSIONE SALUTE");
//        c8.addCommittee("COMMISSIONE POLITICHE GIOVANILI");
//        Councilor c9 = new Councilor("LUZZO", "ANGELO", "IX", "PARTITO V");
//        c8.addCommittee("COMMISSIONE AGRICOLTURA");
//        c8.addCommittee("COMMISSIONE SPORT");
//
//        councilors.add(c0);
//        councilors.add(c1);
//        councilors.add(c2);
//        councilors.add(c3);
//        councilors.add(c4);
//        councilors.add(c5);
//        councilors.add(c6);
//        councilors.add(c7);
//        councilors.add(c8);
//        councilors.add(c9);
//
//        logger.debug("End of method 'extract'");
//        return councilors;
//    }


}
