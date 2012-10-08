package com.sourcesense.crl.business.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sourcesense.crl.business.model.Commissione;
import com.sourcesense.crl.business.model.CommissioneReferente;
import com.sourcesense.crl.business.model.EsameAula;
import com.sourcesense.crl.business.model.GestioneSedute;
import com.sourcesense.crl.business.model.Seduta;
import com.sourcesense.crl.business.service.rest.AulaService;
import com.sourcesense.crl.business.service.rest.SeduteService;
import com.sourcesense.crl.util.URLBuilder;


@Service("seduteServiceManager")
public class SeduteServiceManager implements ServiceManager{

	@Autowired
	private SeduteService seduteService;
	
	@Autowired
	private  URLBuilder urlBuilder;
	
	public void deleteSeduta(String idSeduta) {
		 seduteService.delete(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_sedute", new String[]{idSeduta}));
	}
	
	public Seduta salvaSeduta(GestioneSedute gestioneSedute) {
		return seduteService.create(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_sedute", null), gestioneSedute);
	}
	
	public void updateSeduta(GestioneSedute gestioneSedute) {
		seduteService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_sedute", null), gestioneSedute);
	}
	
	public List<Seduta>  getSedute(String gruppo) {
		return seduteService.findByGroup(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_sedute", new String[]{gruppo}));
	}
	
	public void salvaOdg(GestioneSedute gestioneSedute) {
		seduteService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_odg", null), gestioneSedute);
	}
	
	
	@Override
	public Object persist(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object merge(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Object> retrieveAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
