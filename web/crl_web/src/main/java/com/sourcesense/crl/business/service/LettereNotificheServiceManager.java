package com.sourcesense.crl.business.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sourcesense.crl.business.service.rest.LegislaturaService;
import com.sourcesense.crl.business.service.rest.LettereNotificheService;
import com.sourcesense.crl.util.URLBuilder;


@Service("lettereNotificheServiceManager")
public class LettereNotificheServiceManager implements ServiceManager {
	
	@Autowired
	private URLBuilder urlBuilder;
	
	
	@Autowired
	private LettereNotificheService lettereNotificheService;


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
