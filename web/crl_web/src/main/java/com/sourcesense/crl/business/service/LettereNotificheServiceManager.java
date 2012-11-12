package com.sourcesense.crl.business.service;

import java.io.InputStream;
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

	
	

 	public Lettera  getLettera(Lettera lettera){
 		
 		return lettereNotificheService.getLettera(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_retrieve_lettera", new String[]{lettera.getTipoTemplate()}));
 	}
 	
 	public void  updateLettera(Lettera lettera){
 		 lettereNotificheService.merge(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_update_lettera", null),lettera);
 	}
 	
 	public InputStream getLetteraFile(Lettera lettera , String idAtto, String gruppo) {

		return lettereNotificheService.getFile(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_retrieve_lettera_bin", new String[]{idAtto,lettera.getTipoTemplate(),gruppo} ));
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
