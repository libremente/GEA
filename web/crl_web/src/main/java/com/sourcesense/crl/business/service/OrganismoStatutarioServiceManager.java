package com.sourcesense.crl.business.service;

import java.io.Serializable;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sourcesense.crl.business.service.rest.OrganismoStatutarioService;
import com.sourcesense.crl.business.service.rest.TipoAttoService;

@Service("organismoStatutarioServiceManager")
public class OrganismoStatutarioServiceManager implements ServiceManager, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	OrganismoStatutarioService organismoStatutarioService;

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

	@Override
	public Map<String, String> findAll() {
		return organismoStatutarioService.getAllOrganismoStatutario();
	}

	@Override
	public Object findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
