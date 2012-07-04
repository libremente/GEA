package com.sourcesense.crl.business.service;

import java.util.Map;

public interface ServiceManager {

	
	public boolean persist(Object object);
	public boolean merge(Object object);
	public boolean remove(Object object);
	public Map<String,String> findAll();
	public Object findById(String id);
	
}
