/*
 * Copyright Shragger sarl. All Rights Reserved.
 *
 *
 * The copyright to the computer program(s) herein is the property of
 * Shragger sarl., France. The program(s) may be used and/or copied only
 * with the written permission of Shragger sarl.. or in accordance with the
 * terms and conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied. This copyright notice must not be removed.
 */
package com.sourcesense.crl.business.service;

import com.sourcesense.crl.business.model.Allegato;
import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.model.AttoSearch;
import com.sourcesense.crl.business.model.Commissione;
import com.sourcesense.crl.business.model.Firmatario;
import com.sourcesense.crl.business.model.TestoAtto;
import com.sourcesense.crl.business.service.rest.AttoService;
import com.sourcesense.crl.util.CRLMessage;
import com.sourcesense.crl.util.URLBuilder;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

/**
 * 
 * @author uji
 */

@Service("attoServiceManager")
public class AttoServiceManager implements ServiceManager {

	@Autowired
	private  URLBuilder urlBuilder;

	@Autowired
	private AttoService attoService;

	public List<Atto> searchAtti(Atto atto) {
		return attoService
				.parametricSearch(atto, urlBuilder.buildAlfrescoURL(
						"alfresco_context_url",
						"alf_list_atto_ricerca_semplice", null));
	}

	public List<Atto> initListAtti() {
		
		AttoSearch attoInit = new AttoSearch();
		attoInit.setDataIniziativaA(new Date());
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -2);
		attoInit.setDataIniziativaDa(calendar.getTime());
		return attoService
				.parametricSearch(attoInit, urlBuilder.buildAlfrescoURL(
						"alfresco_context_url",
						"alf_list_atto_ricerca_semplice", null));

	}
	
	
	public void removeFirmatario(Firmatario firmatario){
		
		attoService.removeFirmatario(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_remove_firmatario", new String[]{firmatario.getId()}));
	}
	
	
	public TestoAtto uploadTestoAttoPresentazioneAssegnazione(Atto atto, InputStream stream, TestoAtto testoAtto) {

		return attoService.uploadTestoAtto(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_upload_testo", new String[] { atto.getId() }),atto, stream, testoAtto, TestoAtto.TESTO_PRESENTAZIONE_ASSEGNAZIONE);
	}
	
	public Allegato uploadAllegatoNoteAllegatiPresentazioneAssegnazione(Atto atto, InputStream stream, String nomeFile) {

		return attoService.uploadAllegato(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_upload_allegato", new String[] { atto.getId() }),atto, stream, nomeFile, Allegato.TIPO_PRESENTAZIONE_ASSEGNAZIONE);
	}
	
	public TestoAtto uploadTestoComitatoRistretto(Atto atto, InputStream stream, TestoAtto testoAtto) {

		return attoService.uploadTestoAtto(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_upload_testo", new String[] { atto.getId() }),atto, stream, testoAtto, TestoAtto.TESTO_ESAME_COMMISSIONE_COMITATO);
	}

	public TestoAtto uploadTestoAttoVotatoEsameCommissioni(Atto atto, InputStream stream, TestoAtto testoAtto) {

		return attoService.uploadTestoAtto(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_upload_testo", new String[] { atto.getId() }),atto, stream, testoAtto, TestoAtto.TESTO_ESAME_COMMISSIONE_VOTAZIONE);
	}
	
	public TestoAtto uploadEmendamentoEsameCommissioni(Atto atto, InputStream stream, TestoAtto testoAtto) {

		return attoService.uploadTestoAtto(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_upload_testo", new String[] { atto.getId() }),atto, stream, testoAtto, TestoAtto.TESTO_ESAME_AULA_EMENDAMENTO);
	}
	
	public TestoAtto uploadTestoClausolaEsameCommissioni(Atto atto, InputStream stream, TestoAtto testoAtto) {

		return attoService.uploadTestoAtto(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_upload_testo", new String[] { atto.getId() }),atto, stream, testoAtto, TestoAtto.TESTO_ESAME_COMMISSIONE_CLAUSOLA);
	}	
	
	public Allegato uploadAllegatoNoteAllegatiEsameCommissioni(Atto atto, InputStream stream, String nomeFile) {

		return attoService.uploadAllegato(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_upload_allegato", new String[] { atto.getId() }),atto, stream, nomeFile, Allegato.TIPO_ESAME_COMMISSIONE_ALLEGATO);
	}	
	
	public TestoAtto uploadTestoAttoVotatoEsameAula(Atto atto, InputStream stream, TestoAtto testoAtto) {

		return attoService.uploadTestoAtto(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_upload_testo", new String[] { atto.getId() }),atto, stream, testoAtto, TestoAtto.TESTO_ESAME_AULA_VOTAZIONE);
	}	
	
	public Allegato uploadEmendamentoEsameAula(Atto atto, InputStream stream, Allegato allegato) {

		return attoService.uploadAllegato(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_upload_testo", new String[] { atto.getId() }),atto, stream, allegato.getNome(), TestoAtto.TESTO_ESAME_AULA_EMENDAMENTO);
	}	
	
	public Allegato uploadAllegatoNoteAllegatiEsameAula(Atto atto, InputStream stream, String nomeFile) {

		return attoService.uploadAllegato(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_upload_allegato", new String[] { atto.getId() }),atto, stream, nomeFile, Allegato.TIPO_ESAME_AULA_ALLEGATO);
	}	
	
	
	

	@Override
	public Atto persist(Object object) {
		return attoService.create(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_new_atto", null), (Atto) object);

	}

	public Atto findById(String id) {
		return attoService.findById(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_atto_by_id", new String[] { id }));
	}
    
	

	public void presaInCaricoSC(Atto atto) {
		
		attoService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_presa_carico_presentazione_assegnazione", 
				new String[] { atto.getId() }), atto);
	
	}
	
	public void salvaInfoGeneraliPresentazione(Atto atto) {
		attoService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_info_generali_presentazione_assegnazione", 
				new String[] { atto.getId() }), atto);
	}

	public void salvaAmmissibilitaPresentazione(Atto atto) {
		attoService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_ammissibilita_presentazione_assegnazione", null), atto);
	}

	public void salvaAssegnazionePresentazione(Atto atto) {
		attoService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_assegnazione_presentazione_assegnazione", null), atto);
	}

	public void salvaNoteAllegatiPresentazione(Atto atto) {
		attoService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_note_allegati_presentazione_assegnazione", null), atto);
	}
	
	public void salvaVotazioneEsameCommissioni(Atto atto) {
		attoService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_votazione_esame_commissioni", null), atto);
	}
	
	public void salvaEmendamentiClausoleEsameCommissioni(Atto atto) {
		attoService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_emendamenti_clausole_esame_commissioni", null), atto);
	}
	
	public void salvaNoteAllegatiEsameCommissioni(Atto atto) {
		attoService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_note_allegati_esame_commissioni", null), atto);
	}
	
	
	
	public void salvaEmendamentiEsameAula(Atto atto) {
		attoService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_emendamenti_esame_aula", null), atto);
	}
	
	public void salvaRinvioEsameEsameAula(Atto atto) {
		attoService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_rinvio_esame_esame_aula", null), atto);
	}
	
	public void salvaStralciEsameAula(Atto atto) {
		attoService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_stralci_esame_aula", null), atto);
	}
	
	public void salvaNoteAllegatiEsameAula(Atto atto) {
		attoService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_note_allegati_esame_aula", null), atto);
	}	
	
	public void salvaPareri(Atto atto) {
		attoService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_pareri", null), atto);
	}
	
	
	
	

	@Override
	public Atto merge(Object object) {
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

}
