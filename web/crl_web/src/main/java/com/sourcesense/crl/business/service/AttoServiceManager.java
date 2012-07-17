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
import com.sourcesense.crl.business.model.AttoSearch;
import com.sourcesense.crl.business.service.rest.AttoService;
import com.sourcesense.crl.util.URLBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

/**
 * 
 * @author uji
 */

@Service("attoServiceManager")
public class AttoServiceManager implements ServiceManager { 



	@Autowired
	private transient URLBuilder urlBuilder;

	@Autowired
	private AttoService attoService;

	public List<Atto> searchAtti(Atto atto){
		return attoService.parametricSearch(atto, urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_list_atto_ricerca_semplice", null));
	}
	
	public List<Atto> initListAtti() {
		AttoSearch attoInit = new AttoSearch();
		attoInit.setDataIniziativaA(new Date());
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -2);
		attoInit.setDataIniziativaDa(calendar.getTime());
		
		return searchAtti(attoInit);
		
	}


	@Override
	public boolean persist(Object object) {
		// TODO Auto-generated method stub

		return attoService.create(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_new_atto",null), (Atto)object);

	}
	
	public Atto findById(String id) {
		return attoService.findById(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_atto_by_id",new String [] {id}));
	}


	@Override
	public boolean merge(Object object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Object object) {
		// TODO Auto-generated method stub
		return false;
	}

	/* MOCKUP */

	//	private ArrayList<Atto> listAtti;
	//
//	public Atto get(String cod) {
//		if (listAtti == null)
//			return null;
//
//		for (Atto atto : listAtti) {
//			if (atto.getNumeroAtto() != null
//					&& atto.getNumeroAtto().equals(cod)) {
//				return atto;
//			}
//		}
//
//		return null;
//	}
	//	
	//	public ArrayList<Atto> find(int first, int pageSize) {
	//		if (listAtti == null)
	//			return null;
	//
	//		ArrayList<Atto> ret = new ArrayList<Atto>();
	//
	//		int max = count();
	//
	//		for (int cont = first; cont < first + pageSize && cont < max; cont++) {
	//			ret.add(listAtti.get(cont));
	//		}
	//
	//		Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
	//				"ret: {0}", ret.size());
	//
	//		return ret;
	//	}
	//
	//	public int count() {
	//		Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
	//				"count: {0}", listAtti.size());
	//		return listAtti.size();
	//	}
	//
	//	private void initDummyData() {
	//		listAtti = new ArrayList<Atto>();
	//
	//		listAtti.add(getDummyDatum("121",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "Pubblicato", "PDL"));
	//		listAtti.add(getDummyDatum("122",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "In ODG generale dell’Aula", "PDL"));
	//		listAtti.add(getDummyDatum("123",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "Trasmesso dalla Commissione", "PDL"));
	//		listAtti.add(getDummyDatum("124",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "Pubblicato", "PDL"));
	//		listAtti.add(getDummyDatum("125",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "In ODG generale dell’Aula", "PDL"));
	//		listAtti.add(getDummyDatum("126",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "Trasmesso dalla Commissione", "PDL"));
	//		listAtti.add(getDummyDatum("127",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "Pubblicato", "PDL"));
	//		listAtti.add(getDummyDatum("128",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "In ODG generale dell’Aula", "PDL"));
	//		listAtti.add(getDummyDatum("129",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "Trasmesso dalla Commissione", "PDL"));
	//		listAtti.add(getDummyDatum("130",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "Pubblicato", "PDL"));
	//		listAtti.add(getDummyDatum("131",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "In ODG generale dell’Aula", "PDL"));
	//		listAtti.add(getDummyDatum("132",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "Trasmesso dalla Commissione", "PDL"));
	//		listAtti.add(getDummyDatum("133",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "Pubblicato", "PDL"));
	//		listAtti.add(getDummyDatum("134",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "In ODG generale dell’Aula", "PDL"));
	//		listAtti.add(getDummyDatum("135",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "Trasmesso dalla Commissione", "PDL"));
	//		listAtti.add(getDummyDatum("136",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "In ODG generale dell’Aula", "PDL"));
	//		listAtti.add(getDummyDatum("137",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "Pubblicato", "PDL"));
	//		listAtti.add(getDummyDatum("138",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "In ODG generale dell’Aula", "PDL"));
	//		listAtti.add(getDummyDatum("139",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "Trasmesso dalla Commissione", "PDL"));
	//		listAtti.add(getDummyDatum("140",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "Pubblicato", "PDL"));
	//		listAtti.add(getDummyDatum("141",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "In ODG generale dell’Aula", "PDL"));
	//		listAtti.add(getDummyDatum("142",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "Pubblicato", "PDL"));
	//		listAtti.add(getDummyDatum("143",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "Trasmesso dalla Commissione", "PDL"));
	//		listAtti.add(getDummyDatum("144",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "In ODG generale dell’Aula", "PDL"));
	//		listAtti.add(getDummyDatum("145",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "Pubblicato", "PDL"));
	//		listAtti.add(getDummyDatum("146",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "Trasmesso dalla Commissione", "PDL"));
	//		listAtti.add(getDummyDatum("147",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "In ODG generale dell’Aula", "PDL"));
	//		listAtti.add(getDummyDatum("148",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "Pubblicato", "PDL"));
	//		listAtti.add(getDummyDatum("149",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "Trasmesso dalla Commissione", "PDL"));
	//		listAtti.add(getDummyDatum("150",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "In ODG generale dell’Aula", "PDL"));
	//		listAtti.add(getDummyDatum("151",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "Pubblicato", "PDL"));
	//		listAtti.add(getDummyDatum("152",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "Trasmesso dalla Commissione", "PDL"));
	//		listAtti.add(getDummyDatum("153",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "In ODG generale dell’Aula", "PDL"));
	//		listAtti.add(getDummyDatum("154",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "Pubblicato", "PDL"));
	//		listAtti.add(getDummyDatum("155",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "In ODG generale dell’Aula", "PDL"));
	//		listAtti.add(getDummyDatum("156",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "Pubblicato", "PDL"));
	//		listAtti.add(getDummyDatum("157",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "Trasmesso dalla Commissione", "PDL"));
	//		listAtti.add(getDummyDatum("158",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "In ODG generale dell’Aula", "PDL"));
	//		listAtti.add(getDummyDatum("159",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "Pubblicato", "PDL"));
	//		listAtti.add(getDummyDatum("160",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "Trasmesso dalla Commissione", "PDL"));
	//		listAtti.add(getDummyDatum("161",
	//				"Approvazione piano dei trasporti pubblici in Milano",
	//				"Mario Rossi", "In ODG generale dell’Aula", "PDL"));
	//	}
	//
	//	public AttoServiceManager() {
	//		initDummyData();
	//	}
	//
	//	private Atto getDummyDatum(String cod, String oggetto,
	//			String primoFirmatario, String stato, String tipo) {
	//		Atto atto = new Atto();
	//
	//		atto.setNumeroAtto(cod);
	//		atto.setDataPresentazione(new Date());
	//		atto.setOggetto(oggetto);
	//		atto.setPrimoFirmatario(primoFirmatario);
	//		atto.setStato(stato);
	//		atto.setTipo(tipo);
	//
	//		return atto;
	//	}

	@Override
	public Map<String, String> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
