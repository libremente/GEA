package com.sourcesense.crl.business.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.model.Commissione;
import com.sourcesense.crl.business.model.CommissioneConsultiva;
import com.sourcesense.crl.business.model.CommissioneReferente;
import com.sourcesense.crl.business.model.EsameCommissione;
import com.sourcesense.crl.business.model.Legislatura;
import com.sourcesense.crl.business.service.rest.CommissioneService;
import com.sourcesense.crl.util.URLBuilder;

@Service("commissioneServiceManager")
public class CommissioneServiceManager implements ServiceManager{

	@Autowired
	private  URLBuilder urlBuilder;

	@Autowired
	private CommissioneService commissioneService;

	@Override
	public Commissione persist(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Commissione merge(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object object) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void salvaPresaInCaricoEsameCommissioni(EsameCommissione esameCommissione) {
		commissioneService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_presa_in_carico_esame_commissioni", null), esameCommissione);
	}
	
	public void salvaRelatoriEsameCommissioni(EsameCommissione esameCommissione) {		
		commissioneService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_relatori_esame_commissioni", null), esameCommissione);
	}
	
	public void salvaComitatoRistrettoEsameCommissioni(EsameCommissione esameCommissione) {
		commissioneService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_comitato_ristretto_esame_commissioni", null), esameCommissione);
	}
	
	public void salvaFineLavoriEsameCommissioni(EsameCommissione esameCommissione) {
		commissioneService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_fine_lavori_esame_commissioni", null), esameCommissione);
	}
	

    public List <Commissione> findCommissioniByAtto(String idAtto) {
		
    	List <Commissione> commissioni = new ArrayList<Commissione>();
    	
    	List <CommissioneReferente> commissioniRef =  commissioneService.retrieveCommissioniReferenteByAtto(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_new_atto", new String [] {idAtto}));
		
    	List <CommissioneConsultiva> commissioniCons =  commissioneService.retrieveCommissioniConsultiveByAtto(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_new_atto", new String [] {idAtto}));
		
    	commissioni.addAll(commissioniCons);
    	commissioni.addAll(commissioniRef);
    	
    	return commissioni;
		

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

	@Override
	public List<Object> retrieveAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
