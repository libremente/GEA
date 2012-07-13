package com.sourcesense.crl.business.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sourcesense.crl.business.model.CommissioneConsultiva;
import com.sourcesense.crl.business.model.CommissioneReferente;
import com.sourcesense.crl.business.model.Legislatura;
import com.sourcesense.crl.business.service.rest.CommissioneService;
import com.sourcesense.crl.util.URLBuilder;

@Service("commissioneServiceManager")
public class CommissioneServiceManager implements ServiceManager{

	@Autowired
	private transient URLBuilder urlBuilder;

	@Autowired
	private CommissioneService commissioneService;

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

	public Map<String, String> findAllCommissioneReferente() {
		Map<String, String> commissioniReferenti = new HashMap<String, String>();

		List<CommissioneReferente> listCommissioniReferenti = commissioneService.getAllCommissioneReferente(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_list_commissioni_referenti",null));

		for (CommissioneReferente commissioneReferente : listCommissioniReferenti) {

			commissioniReferenti.put(commissioneReferente.getDescrizione(), commissioneReferente.getDescrizione());

		}
		return commissioniReferenti;
	}


	public Map<String, String> findAllCommissioneConsultiva() {
		Map<String, String> commissioniConsultive = new HashMap<String, String>();
		
		List<CommissioneConsultiva> listCommissioniConsultive = commissioneService.getAllCommissioneConsultiva(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_list_commissioni_consultive", null));
	
		for (CommissioneConsultiva commissioneConsultiva : listCommissioniConsultive)
			
			commissioniConsultive.put(commissioneConsultiva.getDescrizione(), commissioneConsultiva.getDescrizione());
		
		return commissioniConsultive;
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
