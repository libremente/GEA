/*
 * Copyright Shragger sarl. All Rights Reserved.
 *
 *
 * The copyright to the computer program(s) herein is the property of
 * Shragger sarl., France. The program(s) may be used and/or copied only
 * with the written permission of Shragger sarl.. or in accordance with the
 * terms and conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied. This copyright notice must not be removed.
 */
package com.sourcesense.crl.business.service;

import com.sourcesense.crl.business.model.Atto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author uji
 */
public class AttoService implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Atto> data;
    
    private void initDummyData()
    {
        data = new ArrayList<Atto>();
        
        data.add(getDummyDatum("121", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "Pubblicato", "PDL"));
        data.add(getDummyDatum("122", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "In ODG generale dell’Aula", "PDL"));
        data.add(getDummyDatum("123", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "Trasmesso dalla Commissione", "PDL"));
        data.add(getDummyDatum("124", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "Pubblicato", "PDL"));
        data.add(getDummyDatum("125", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "In ODG generale dell’Aula", "PDL"));
        data.add(getDummyDatum("126", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "Trasmesso dalla Commissione", "PDL"));
        data.add(getDummyDatum("127", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "Pubblicato", "PDL"));
        data.add(getDummyDatum("128", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "In ODG generale dell’Aula", "PDL"));
        data.add(getDummyDatum("129", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "Trasmesso dalla Commissione", "PDL"));
        data.add(getDummyDatum("130", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "Pubblicato", "PDL"));
        data.add(getDummyDatum("131", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "In ODG generale dell’Aula", "PDL"));
        data.add(getDummyDatum("132", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "Trasmesso dalla Commissione", "PDL"));
        data.add(getDummyDatum("133", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "Pubblicato", "PDL"));
        data.add(getDummyDatum("134", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "In ODG generale dell’Aula", "PDL"));
        data.add(getDummyDatum("135", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "Trasmesso dalla Commissione", "PDL"));
        data.add(getDummyDatum("136", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "In ODG generale dell’Aula", "PDL"));
        data.add(getDummyDatum("137", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "Pubblicato", "PDL"));
        data.add(getDummyDatum("138", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "In ODG generale dell’Aula", "PDL"));
        data.add(getDummyDatum("139", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "Trasmesso dalla Commissione", "PDL"));
        data.add(getDummyDatum("140", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "Pubblicato", "PDL"));
        data.add(getDummyDatum("141", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "In ODG generale dell’Aula", "PDL"));
        data.add(getDummyDatum("142", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "Pubblicato", "PDL"));
        data.add(getDummyDatum("143", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "Trasmesso dalla Commissione", "PDL"));
        data.add(getDummyDatum("144", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "In ODG generale dell’Aula", "PDL"));
        data.add(getDummyDatum("145", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "Pubblicato", "PDL"));
        data.add(getDummyDatum("146", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "Trasmesso dalla Commissione", "PDL"));
        data.add(getDummyDatum("147", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "In ODG generale dell’Aula", "PDL"));
        data.add(getDummyDatum("148", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "Pubblicato", "PDL"));
        data.add(getDummyDatum("149", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "Trasmesso dalla Commissione", "PDL"));
        data.add(getDummyDatum("150", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "In ODG generale dell’Aula", "PDL"));
        data.add(getDummyDatum("151", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "Pubblicato", "PDL"));
        data.add(getDummyDatum("152", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "Trasmesso dalla Commissione", "PDL"));
        data.add(getDummyDatum("153", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "In ODG generale dell’Aula", "PDL"));
        data.add(getDummyDatum("154", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "Pubblicato", "PDL"));
        data.add(getDummyDatum("155", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "In ODG generale dell’Aula", "PDL"));
        data.add(getDummyDatum("156", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "Pubblicato", "PDL"));
        data.add(getDummyDatum("157", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "Trasmesso dalla Commissione", "PDL"));
        data.add(getDummyDatum("158", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "In ODG generale dell’Aula", "PDL"));
        data.add(getDummyDatum("159", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "Pubblicato", "PDL"));
        data.add(getDummyDatum("160", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "Trasmesso dalla Commissione", "PDL"));
        data.add(getDummyDatum("161", "Approvazione piano dei trasporti pubblici in Milano", "Mario Rossi", "In ODG generale dell’Aula", "PDL"));
    }
    
    public AttoService()
    {
        initDummyData();
    }
    
    private Atto getDummyDatum(String cod, String oggetto, String primoFirmatario, String stato, String tipo)
    {
        Atto atto = new Atto();
        
        atto.setCodice(cod);
        atto.setDataPresentazione(new Date());
        atto.setOggetto(oggetto);
        atto.setPrimoFirmatario(primoFirmatario);
        atto.setStato(stato);
        atto.setTipo(tipo);
        
        return atto;
    }
    
    public ArrayList<Atto> find(int first, int pageSize)
    {
        if (data == null)
            return null;
        
        ArrayList<Atto> ret = new ArrayList<Atto>();
        
        int max = count();
        
        for (int cont = first; cont < first + pageSize && cont < max ; cont++)
        {
            ret.add(data.get(cont));
        }
       
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "ret: {0}", ret.size());
        
        return ret;
    }
    
    public int count()
    {
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "count: {0}", data.size());
        return data.size();
    }
    
    public Atto get(String cod)
    {
        if (data == null)
            return null;
        
        
        for (Atto atto: data)
        {
            if (atto.getCodice() != null && atto.getCodice().equals(cod))
            {
                return atto;
            }
        }
        
        return null;
    }
}
