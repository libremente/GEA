package com.sourcesense.crl.business.service;

import java.util.List;
import java.util.Map;

public interface ServiceManager {

	
	public Object persist(Object object);
	public Object merge(Object object);
	public boolean remove(Object object);
	public List<Object> retrieveAll();
	public Map<String,String> findAll();
	public Object findById(String id);
	
}
