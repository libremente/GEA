/* 
 * Copyright (C) 2019 Consiglio Regionale della Lombardia
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.sourcesense.crl.business.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.model.Firmatario;
import com.sourcesense.crl.business.model.GruppoConsiliare;
import com.sourcesense.crl.business.model.Legislatura;
import com.sourcesense.crl.business.model.Personale;
import com.sourcesense.crl.business.model.Relatore;
import com.sourcesense.crl.business.service.rest.PersonaleService;
import com.sourcesense.crl.util.URLBuilder;

@Service("personaleServiceManager")
public class PersonaleServiceManager implements ServiceManager{

	@Autowired
	private  URLBuilder urlBuilder;	

	@Autowired
	private PersonaleService personaleService;

	@Override
	public Personale persist(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Personale merge(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object object) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	public List<GruppoConsiliare> getGruppiConsiliari(){

		List<GruppoConsiliare> listGruppiConsiliari = personaleService.getListGruppiConsiliari(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_list_gruppi_consiliari",null));
		return listGruppiConsiliari;
		
	}
	
	
	public List<String> findGruppiConsiliari(){
        List<String> gruppi = new ArrayList<String>();
		
		List<GruppoConsiliare> listGruppiConsiliari = personaleService.getListGruppiConsiliari(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_list_gruppi_consiliari",null));
		for (GruppoConsiliare gruppoConsiliare : listGruppiConsiliari) {
			
			
			gruppi.add(gruppoConsiliare.getDescrizione());
		}
		
		return gruppi;
		
	}

	public Map<String, String> findAllFirmatario() {
		Map<String, String> firmatari = new HashMap<String, String>();

		List<Firmatario> listFirmatari = personaleService.getAllFirmatario(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_list_firmatari",null));

		for (Firmatario firmatario : listFirmatari) {

			firmatari.put(firmatario.getDescrizione(), firmatario.getDescrizione());

		}
		return firmatari;
	}
     
	
	public Map<String, String> findAllRelatore() {
		Map<String, String> relatori = new HashMap<String, String>();

		List<Relatore> listRelatori = personaleService.getAllRelatore(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_list_relatori",null));

		for (Relatore relatore : listRelatori) {

			relatori.put(relatore.getDescrizione(), relatore.getDescrizione());

		}
		return relatori;
	}
	
	
	
	public List<String> getAllFirmatario() {
		List<String> firmatari = new ArrayList<String>();

		List<Firmatario> listFirmatari = personaleService.getAllFirmatario(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_list_firmatari",null));

		for (Firmatario firmatario : listFirmatari) {

			firmatari.add(firmatario.getDescrizione());

		}
		return firmatari;
	}
	
	
	public List<String> getAllFirmatariStorici(String legislatura) {
		List<String> firmatari = new ArrayList<String>();

		List<Firmatario> listFirmatari = personaleService.getAllFirmatario(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_list_firmatari_all",new String[]{legislatura}));

		for (Firmatario firmatario : listFirmatari) {

			firmatari.add(firmatario.getDescrizione());

		}
		return firmatari;
	}

	
	public List<String>  getAllRelatore() {
		List<String> relatori = new ArrayList<String>();

		List<Relatore> listRelatori = personaleService.getAllRelatore(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_list_relatori",null));

		for (Relatore relatore : listRelatori) {

			relatori.add(relatore.getDescrizione());

		}
		return relatori;
	}
	
	
	
	
	public Map<String, String> findAllMembriComitato() {
		Map<String, String> relatori = new HashMap<String, String>();

		List<Relatore> listRelatori = personaleService.getAllRelatore(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_list_relatori",null));

		for (Relatore relatore : listRelatori) {

			relatori.put(relatore.getDescrizione(), relatore.getDescrizione());

		}
		return relatori;
	}
	
	
	public List<Relatore> getAllRelatori() {
		return personaleService.getAllRelatore(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_list_relatori",null));
	}
	
	public List<Relatore> getAllMembriComitato() {
		return personaleService.getAllRelatore(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_list_relatori",null));
	}
	
	public List<Firmatario> getAllFirmatari() {
		return personaleService.getAllFirmatario(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_list_firmatari",null));
	}
	
	public List<Firmatario> findFirmatariByAtto(Atto atto) {
		return personaleService.findFirmatariById(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_list_firmatari_atto", new String[] { atto.getId() }));
	}
	
	@Override
	public Object findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> retrieveAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
