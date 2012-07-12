package com.sourcesense.crl.business.service;

import java.io.Serializable;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sourcesense.crl.business.service.rest.PersonaleService;

@Service("personaleServiceManager")
public class PersonaleServiceManager implements ServiceManager, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	PersonaleService personaleService;

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
		return personaleService.getAllFirmatario();
	}
	
	public Map<String, String> findAllPrimoFirmatario() {
		return personaleService.getAllPrimoFirmatario();
	}
	
	public Map<String, String> findAllRelatore() {
		return personaleService.getAllRelatore();
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
