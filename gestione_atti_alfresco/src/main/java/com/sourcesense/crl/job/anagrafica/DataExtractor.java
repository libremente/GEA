package com.sourcesense.crl.job.anagrafica;

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

    
    public List<Legislature> getLegislatures() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(Constant.QUERY_LEGISLATURE);
            ResultSet rs = ps.executeQuery();
            Legislature legislature = null;
            List<Legislature> legislatures = new ArrayList<Legislature>();
            while (rs.next()) {
                legislature = new Legislature();
                legislature.setId(rs.getInt(Constant.COLUMN_LEGISLATURE_ID));
                legislature.setNumber(rs.getString(Constant.COLUMN_LEGISLATURE_NUMBER));
                legislature.setFrom(rs.getDate(Constant.COLUMN_LEGISLATURE_FROM));
                legislature.setTo(rs.getDate(Constant.COLUMN_LEGISLATURE_TO));
                legislatures.add(legislature);
            }
            rs.close();
            ps.close();
            return legislatures;
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
                councilor.setId(rs.getInt(Constant.COLUMN_ID_PERSONA));
                councilor.setFirstName(rs.getString(Constant.COLUMN_NOME));
                councilor.setLastName(rs.getString(Constant.COLUMN_COGNOME));
                councilor.setGroupName(rs.getString(Constant.COLUMN_NOME_GRUPPO));
                councilor.setCodeGroupName(rs.getString(Constant.COLUMN_CODICE_GRUPPO));
                councilor.setLegislatureNumber(rs.getString(Constant.COLUMN_NUMERO_LEGISLATURA));
                String committes = rs.getString(Constant.COLUMN_ORGANI);
                if (committes != null && !"".equals(committes) && !"#".equals(committes)) {
                    StringTokenizer st = new StringTokenizer(committes, "#");
                    while (st.hasMoreTokens()) {
                    	String comm = st.nextToken();
                    	Committee c = new Committee();
                    	
                    	String[] strArray = comm.split("!");
                    	if(strArray.length>0){
                    		c.setName(strArray[0]);
                    		if(strArray.length>1){
                        		c.setOrder(Integer.valueOf(comm.split("!")[1]));
                    		}else{
                        		c.setOrder(1000);
                        	}
                    	}
                    
                        councilor.addCommittee(c);
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
    
  
    
    
    public List<Group> getGroups() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(Constant.QUERY_CURRENT_GRUPPI_CONSILIARI);
            ResultSet rs = ps.executeQuery();
            Group group = null;
            List<Group> groups = new ArrayList<Group>();
            while (rs.next()) {
                group = new Group();
                group.setId(rs.getInt(Constant.COLUMN_GRUPPI_CONSILIARI_ID));
                group.setName(rs.getString(Constant.COLUMN_GRUPPI_CONSILIARI_NOME));
                group.setCode(rs.getString(Constant.COLUMN_GRUPPI_CONSILIARI_CODICE));
                groups.add(group);
            }
            rs.close();
            ps.close();
            return groups;
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



}
