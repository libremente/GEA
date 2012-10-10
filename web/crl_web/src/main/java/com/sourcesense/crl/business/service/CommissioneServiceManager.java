package com.sourcesense.crl.business.service;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sourcesense.crl.business.model.Allegato;
import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.model.Commissione;
import com.sourcesense.crl.business.model.CommissioneConsultiva;
import com.sourcesense.crl.business.model.CommissioneReferente;
import com.sourcesense.crl.business.model.EsameCommissione;
import com.sourcesense.crl.business.model.Legislatura;
import com.sourcesense.crl.business.model.TestoAtto;
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
	
	public void salvaEmendamentiClausoleEsameCommissioni(EsameCommissione esameCommissione) {
		commissioneService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_emendamenti_clausole_esame_commissioni", null), esameCommissione);
	}
	
	public void salvaVotazioneEsameCommissioni(EsameCommissione esameCommissione) {
		commissioneService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_votazione_esame_commissioni", null), esameCommissione);
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
	
	public Allegato uploadTestoComitatoRistretto(Atto atto, InputStream stream, Allegato testoAtto) {

		return commissioneService.uploadAllegato(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_upload_testo_comitato_ristretto", new String[] { atto.getId() }),atto, stream, testoAtto, Allegato.TESTO_ESAME_COMMISSIONE_COMITATO);
	}
	
	public TestoAtto uploadTestoAttoVotatoEsameCommissioni(Atto atto, InputStream stream, TestoAtto testoAtto) {

		return commissioneService.uploadTestoAtto(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_upload_testo", new String[] { atto.getId() }),atto, stream, testoAtto, TestoAtto.TESTO_ESAME_COMMISSIONE_VOTAZIONE);
	}
	
	public Allegato uploadEmendamentoEsameCommissioni(Atto atto, InputStream stream, Allegato testoAtto) {

		return commissioneService.uploadAllegato(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_upload_allegato_commissione", new String[] { atto.getId() }),atto, stream, testoAtto, Allegato.TESTO_ESAME_AULA_EMENDAMENTO);
	}
	
	
	public Allegato uploadTestoClausolaEsameCommissioni(Atto atto, InputStream stream, Allegato testoAtto) {

		return commissioneService.uploadAllegato(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_upload_allegato_commissione", new String[] { atto.getId() }),atto, stream, testoAtto, Allegato.TESTO_ESAME_COMMISSIONE_CLAUSOLA);
	}	
	
	
	public void salvaCambiaRuoloInReferente(EsameCommissione esameCommissione) {
		commissioneService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_continuazione_commissione_referente", null), esameCommissione);
	}
	
	public void salvaNoteAllegatiEsameCommissioni(EsameCommissione esameCommissione) {
		commissioneService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_note_allegati_esame_commissioni", null), esameCommissione);
	}
	
	
	public void salvaStralci(EsameCommissione esameCommissione) {
		commissioneService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_note_allegati_esame_commissioni", null), esameCommissione);
	}
	
	
	
	public void salvaTrasmissione(EsameCommissione esameCommissione) {
		commissioneService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_trasmissione_commissione", null), esameCommissione);
	}
	
	
	
	public Allegato uploadAllegatoNoteAllegatiEsameCommissioni(Atto atto, InputStream stream, Allegato allegato) {

		return commissioneService.uploadAllegato(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_upload_allegato_commissione", new String[] { atto.getId() }),atto, stream, allegato, Allegato.TIPO_ESAME_COMMISSIONE_ALLEGATO);
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

    
    
    @Override
	public Map<String, String> findAll() {
		Map<String, String> commissioni = new HashMap<String, String>();

		List<Commissione> listCommissioni = commissioneService.getAllCommissioni(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_list_commissioni",null));

		for (Commissione commissione : listCommissioni) {

			commissioni.put(commissione.getDescrizione(), commissione.getDescrizione());

		}
		return commissioni;
		
	}
	
	/*public Map<String, String> findAllCommissioneReferente() {
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
	}*/
	
	
	

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
