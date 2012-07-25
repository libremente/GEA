package com.sourcesense.crl.business.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sourcesense.crl.business.model.TipoAtto;
import com.sourcesense.crl.business.model.TipologiaAtto;
import com.sourcesense.crl.business.service.rest.TipoAttoService;
import com.sourcesense.crl.util.URLBuilder;

@Service("tipoAttoServiceManager")
public class TipoAttoServiceManager implements ServiceManager {


	
	
	
	@Autowired
	private  URLBuilder urlBuilder;
	
	@Autowired
	private TipoAttoService tipoAttoService;
	
	
	@Override
	public TipoAtto persist(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TipoAtto merge(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<String, String> findAll() {
		
		
		Map<String,String> tipiAtto = new HashMap<String, String>();
		
		List<TipoAtto> listTipiAtto = tipoAttoService.getAllTipoAtto(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_list_tipi_atto",null));
		
        for (TipoAtto tipoAtto : listTipiAtto) {
			
        	tipiAtto.put(tipoAtto.getDescrizione(),tipoAtto.getCodice() );
        	
		}
		return tipiAtto;
	}
	
    
	public Map<String, String> findTipologieByTipoAtto(String tipoAtto) {
        
		Map<String,String> tipologieAtto = new HashMap<String, String>();
		
		List<TipologiaAtto> listTipologieAtto = tipoAttoService.getTipologieByTipoAtto(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_tipologie_atto_by_tipo_atto",new String [] {tipoAtto}));
		
        for (TipologiaAtto tipologiaAtto : listTipologieAtto) {
			
        	tipologieAtto.put(tipologiaAtto.getDescrizione(),tipologiaAtto.getDescrizione() );
        	
		}
		return tipologieAtto;
		
		
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
