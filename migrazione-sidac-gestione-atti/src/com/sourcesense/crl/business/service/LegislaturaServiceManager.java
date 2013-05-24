package com.sourcesense.crl.business.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.sourcesense.crl.business.model.Legislatura;
import com.sourcesense.crl.business.service.rest.LegislaturaService;
import com.sourcesense.crl.util.URLBuilder;

@Service("legislaturaServiceManager")
public class LegislaturaServiceManager implements ServiceManager {

	

	
	@Autowired
	private URLBuilder urlBuilder;
	
	
	@Autowired
	private LegislaturaService legislaturaService;
	
	
	
	
	@Override
	public Legislatura persist(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Legislatura merge(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object object) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public List<Legislatura> getAll(){
		
		List<Legislatura> listLegislature = legislaturaService.getAllLegislatura(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_list_legislature",null));
		return listLegislature;
	}
	
	

	@Override
	public Map<String, String> findAll() {
		
		Map<String, String> legislature = new HashMap<String, String>();
		
		List<Legislatura> listLegislature = legislaturaService.getAllLegislatura(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_list_legislature",null));
		
		for (Legislatura legislatura : listLegislature) {

			legislature.put(legislatura.getNome(), legislatura.getNome());

		}
		return legislature;
	}

	
	
	public List<String> list() {
		
		List<String> legislature = new ArrayList<String>();
		
		List<Legislatura> listLegislature = legislaturaService.getAllLegislatura(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_list_legislature",null));
		
		for (Legislatura legislatura : listLegislature) {

			legislature.add(legislatura.getNome());

		}
		return legislature;
	}
	
	
    public Map<String, String> findAnniByLegislatura(String legislatura) {
		
		return legislaturaService.getAnniByLegislatura(urlBuilder.buildAlfrescoURL("alfresco_context_url", "pathPropertyName",null),legislatura);
	}
	
	
	@Override
	public Object findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> retrieveAll() {
		// TODO Auto-generated method stub
		return null;
	}

}