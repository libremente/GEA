package com.sourcesense.crl.business.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sourcesense.crl.business.model.Firmatario;
import com.sourcesense.crl.business.model.StatoAtto;
import com.sourcesense.crl.business.service.rest.StatoAttoService;
import com.sourcesense.crl.util.URLBuilder;

@Service("statoAttoServiceManager")
public class StatoAttoServiceManager implements ServiceManager{
	
	@Autowired
	private transient URLBuilder urlBuilder;	

	@Autowired
	private StatoAttoService statoAttoService;

	@Override
	public StatoAtto persist(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatoAtto merge(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object object) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public Map<String, String> findAll() {
		Map<String, String> stati = new HashMap<String, String>();

		List<StatoAtto> listStati = statoAttoService.getAllStato(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_list_stati_atto",null));

		for (StatoAtto stato : listStati) {

			stati.put(stato.getDescrizione(), stato.getDescrizione());

		}
		return stati;
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
