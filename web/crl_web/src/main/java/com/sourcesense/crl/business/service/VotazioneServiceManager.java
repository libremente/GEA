package com.sourcesense.crl.business.service;

import java.io.Serializable;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sourcesense.crl.business.service.rest.VotazioneService;

@Service("votazioneServiceManager")
public class VotazioneServiceManager implements ServiceManager{

	
	
	@Autowired
	VotazioneService votazioneService;

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
	
	//TODO
	public Map<String, String> findAllEsitoVotoAula() {
		//return votazioneService.getAllEsitoVotoAula();
		return null;
	}
	
	//TODO
	public Map<String, String> findAllEsitoVotoCommissioneReferente() {
		//return votazioneService.getAllEsitoVotoCommissioneReferente();
		return null;
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
