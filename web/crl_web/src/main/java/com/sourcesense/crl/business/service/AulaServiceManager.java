package com.sourcesense.crl.business.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sourcesense.crl.business.model.Allegato;
import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.model.EsameAula;
import com.sourcesense.crl.business.model.Passaggio;
import com.sourcesense.crl.business.model.TestoAtto;
import com.sourcesense.crl.business.service.rest.AttoService;
import com.sourcesense.crl.business.service.rest.AulaService;
import com.sourcesense.crl.util.URLBuilder;
              

@Service("aulaServiceManager")
public class AulaServiceManager implements ServiceManager {

	@Autowired
	private AulaService aulaService;
	
	@Autowired
	private  URLBuilder urlBuilder;
	
	
    public void presaInCarico(EsameAula esameAula) {
		
    	aulaService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_presa_carico_aula", 
				new String[] { esameAula.getAtto().getId() }), esameAula);
	
	}
	
    public void salvaVotazioneEsameAula(EsameAula esameAula) {
    	aulaService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_votazione_esame_aula", null), esameAula);
	}
	
    
    
    public TestoAtto uploadTestoAttoVotatoEsameAula(Atto atto, InputStream stream, TestoAtto testoAtto) {

		return aulaService.uploadTestoAtto(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_upload_testo_atto_aula", new String[] { atto.getId() }),atto, stream, testoAtto, TestoAtto.TESTO_ESAME_AULA_VOTAZIONE);
	}	
    
    
    public Allegato uploadAllegatoNoteAllegatiEsameAula(Atto atto, InputStream stream, Allegato allegato) {

		return aulaService.uploadAllegato(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_upload_allegato_aula", new String[] { atto.getId() }),atto, stream, allegato, Allegato.TIPO_ESAME_AULA_ALLEGATO);
	}	
    
    public Allegato uploadEmendamentoEsameAula(Atto atto, InputStream stream, Allegato allegato) {

		return aulaService.uploadAllegato(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_upload_emendamento_aula", new String[] { atto.getId() }),atto, stream, allegato, Allegato.TESTO_ESAME_AULA_EMENDAMENTO);
	}	
    
    public void salvaEmendamentiEsameAula(EsameAula esameAula) {
    	aulaService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_emendamenti_esame_aula", null), esameAula);
	}
	
	public Passaggio salvaRinvioEsameEsameAula(EsameAula esameAula) {
		return aulaService.rinvioEsame(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_rinvio_esame_esame_aula", null), esameAula);
	}
	
	public void salvaStralciEsameAula(EsameAula esameAula) {
		aulaService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_stralci_esame_aula", null), esameAula);
	}
	
	public void salvaNoteAllegatiEsameAula(EsameAula esameAula) {
		aulaService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_note_allegati_esame_aula", null), esameAula);
	}	
	
	
    
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
