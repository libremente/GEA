package com.sourcesense.crl.business.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sourcesense.crl.business.model.Firmatario;
import com.sourcesense.crl.business.model.StatoAtto;
import com.sourcesense.crl.business.service.rest.StatoAttoService;
import com.sourcesense.crl.util.URLBuilder;

@Service("statoAttoServiceManager")
public class StatoAttoServiceManager implements ServiceManager{
	
	@Autowired
	private  URLBuilder urlBuilder;	

	@Autowired
	private StatoAttoService statoAttoService;

	@Override
	public StatoAtto persist(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatoAtto merge(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object object) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public Map<String, String> findAll() {
		Map<String, String> stati = new HashMap<String, String>();
        
		stati.put(StatoAtto.PROTOCOLLATO, StatoAtto.PROTOCOLLATO);
		stati.put(StatoAtto.PRESO_CARICO_SC, StatoAtto.PRESO_CARICO_SC);
		stati.put(StatoAtto.VERIFICATA_AMMISSIBILITA, StatoAtto.VERIFICATA_AMMISSIBILITA);
		stati.put(StatoAtto.PROPOSTA_ASSEGNAZIONE, StatoAtto.PROPOSTA_ASSEGNAZIONE);
		stati.put(StatoAtto.ASSEGNATO_COMMISSIONE, StatoAtto.ASSEGNATO_COMMISSIONE);
		stati.put(StatoAtto.PRESO_CARICO_COMMISSIONE, StatoAtto.PRESO_CARICO_COMMISSIONE);
		stati.put(StatoAtto.NOMINATO_RELATORE, StatoAtto.NOMINATO_RELATORE);
		stati.put(StatoAtto.VOTATO_COMMISSIONE, StatoAtto.VOTATO_COMMISSIONE);
		stati.put(StatoAtto.TRASMESSO_COMMISSIONE, StatoAtto.TRASMESSO_COMMISSIONE);
		stati.put(StatoAtto.LAVORI_COMITATO_RISTRETTO, StatoAtto.LAVORI_COMITATO_RISTRETTO);
		stati.put(StatoAtto.TRASMESSO_AULA, StatoAtto.TRASMESSO_AULA);
		stati.put(StatoAtto.PRESO_CARICO_AULA, StatoAtto.PRESO_CARICO_AULA);
		stati.put(StatoAtto.VOTATO_AULA, StatoAtto.VOTATO_AULA);
		stati.put(StatoAtto.PUBBLICATO, StatoAtto.PUBBLICATO);
		stati.put(StatoAtto.CHIUSO, StatoAtto.CHIUSO);
		
		return stati;
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
