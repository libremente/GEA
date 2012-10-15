package com.sourcesense.crl.business.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sourcesense.crl.business.model.Lettera;
import com.sourcesense.crl.business.service.rest.LegislaturaService;
import com.sourcesense.crl.business.service.rest.LettereNotificheService;
import com.sourcesense.crl.util.URLBuilder;


@Service("lettereNotificheServiceManager")
public class LettereNotificheServiceManager implements ServiceManager {
	
	@Autowired
	private URLBuilder urlBuilder;
	
	
	@Autowired
	private LettereNotificheService lettereNotificheService;


 	public List<Lettera>  retrieveLettereServizioCommissioni(){
 		
 		return lettereNotificheService.getLettere(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_lettere_servizio_commissioni", null));
 	}
 	
 	public List<Lettera>  retrieveLettereCommissioni(){
 		
 		return lettereNotificheService.getLettere(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_lettere_commissioni", null));
 		
 	}
 	
 	
 	public List<Lettera>  retrieveLettereAula(){
 		return lettereNotificheService.getLettere(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_lettere_aula", null));
 	}
 	
 	public void  updateLettera(Lettera lettera){
 		 lettereNotificheService.merge(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_lettere_aula", null),lettera);
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
