package com.sourcesense.crl.business.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sourcesense.crl.business.model.Firmatario;
import com.sourcesense.crl.business.model.Legislatura;
import com.sourcesense.crl.business.model.Relatore;
import com.sourcesense.crl.business.service.rest.PersonaleService;
import com.sourcesense.crl.util.URLBuilder;

@Service("personaleServiceManager")
public class PersonaleServiceManager implements ServiceManager{

	@Autowired
	private transient URLBuilder urlBuilder;	

	@Autowired
	private PersonaleService personaleService;

	@Override
	public boolean persist(Object object) {
		// TODO Auto-generated method stub
		return false;
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

}
