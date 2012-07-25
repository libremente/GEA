package com.sourcesense.crl.business.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sourcesense.crl.business.model.TipoChiusura;
import com.sourcesense.crl.business.model.TipoIniziativa;
import com.sourcesense.crl.business.service.rest.TipoIniziativaService;
import com.sourcesense.crl.util.URLBuilder;

@Service("tipoIniziativaServiceManager")
public class TipoIniziativaServiceManager implements ServiceManager{

	@Autowired
	private transient URLBuilder urlBuilder;
	
	@Autowired
	private TipoIniziativaService tipoIniziativaService;

	@Override
	public TipoIniziativa persist(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TipoIniziativa merge(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object object) {
		// TODO Auto-generated method stub
		return false;
	}

	public Map<String, String> findAll() {
		Map<String, String> tipiIniziative = new HashMap<String, String>();

		List<TipoIniziativa> listTipiIniziative = tipoIniziativaService.getAllTipoIniziativa(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_list_tipi_iniziative",null));

		for (TipoIniziativa tipoIniziativa : listTipiIniziative) {

			tipiIniziative.put(tipoIniziativa.getDescrizione(), tipoIniziativa.getDescrizione());

		}
		return tipiIniziative;
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
