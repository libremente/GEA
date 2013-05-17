package com.sourcesense.crl.business.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sourcesense.crl.business.model.StatoAtto;
import com.sourcesense.crl.business.model.TipoChiusura;
import com.sourcesense.crl.business.service.rest.TipoChiusuraService;
import com.sourcesense.crl.util.URLBuilder;

@Service("tipoChiusuraServiceManager")
public class TipoChiusuraServiceManager implements ServiceManager{

	@Autowired
	private  URLBuilder urlBuilder;
	
	@Autowired
	private TipoChiusuraService tipoChiusuraService;

	@Override
	public TipoChiusura persist(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TipoChiusura merge(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object object) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public Map<String, String> findAll() {
		Map<String, String> tipiChiusura = new HashMap<String, String>();

		List<TipoChiusura> listTipiChiusura = tipoChiusuraService.getAllTipoChiusura(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_list_tipi_chiusura",null));

		for (TipoChiusura tipoChiusura : listTipiChiusura) {

			tipiChiusura.put(tipoChiusura.getDescrizione(), tipoChiusura.getDescrizione());

		}
		return tipiChiusura;
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
