package com.sourcesense.crl.business.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.model.EsameAula;
import com.sourcesense.crl.business.service.rest.AttoService;
import com.sourcesense.crl.business.service.rest.AulaService;
import com.sourcesense.crl.util.URLBuilder;
              

@Service("aulaServiceManager")
public class AulaServiceManager implements ServiceManager {

	@Autowired
	private AulaService aulaService;
	
	@Autowired
	private  URLBuilder urlBuilder;
	
	
    public void presaInCarico(EsameAula esameAula) {
		
    	aulaService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_presa_carico_aula", 
				new String[] { esameAula.getAtto().getId() }), esameAula);
	
	}
	
    public void salvaVotazioneEsameAula(EsameAula esameAula) {
    	aulaService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_votazione_esame_aula", null), esameAula);
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
