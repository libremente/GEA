package com.sourcesense.crl.business.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sourcesense.crl.business.model.Legislatura;
import com.sourcesense.crl.business.model.OrganismoStatutario;
import com.sourcesense.crl.business.service.rest.OrganismoStatutarioService;
import com.sourcesense.crl.business.service.rest.TipoAttoService;
import com.sourcesense.crl.util.URLBuilder;

@Service("organismoStatutarioServiceManager")
public class OrganismoStatutarioServiceManager implements ServiceManager{
	
	@Autowired
	private transient URLBuilder urlBuilder;

	@Autowired
	private OrganismoStatutarioService organismoStatutarioService;

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

	public Map<String, String> findAll() {
		Map<String, String> organismoStatutari = new HashMap<String, String>();

		List<OrganismoStatutario> listOrganismiStatutari = organismoStatutarioService.getAllOrganismoStatutario(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_list_organismi_statutari",null));

		for (OrganismoStatutario organismoStatutario : listOrganismiStatutari) {

			organismoStatutari.put(organismoStatutario.getDescrizione(), organismoStatutario.getDescrizione());

		}
		return organismoStatutari;
	}

	@Override
	public Object findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}


}
