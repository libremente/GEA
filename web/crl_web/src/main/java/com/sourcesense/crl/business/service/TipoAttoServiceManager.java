package com.sourcesense.crl.business.service;

import java.io.Serializable;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sourcesense.crl.business.service.rest.TipoAttoService;

@Service("tipoAttoServiceManager")
public class TipoAttoServiceManager implements ServiceManager, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Autowired
	TipoAttoService tipoAttoService;
	
	
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
		
		return tipoAttoService.getAllTipoAtto();
	}
	
    
	public Map<String, String> findTipologieByTipoAtto(String tipoAtto) {
		
		return tipoAttoService.getTipologieByTipoAtto(tipoAtto);
	}
	

	@Override
	public Object findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
