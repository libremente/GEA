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
import com.sourcesense.crl.business.model.AttoEAC;
import com.sourcesense.crl.business.model.AttoMIS;
import com.sourcesense.crl.business.model.AttoSearch;
import com.sourcesense.crl.business.model.Collegamento;
import com.sourcesense.crl.business.model.CollegamentoAttiSindacato;
import com.sourcesense.crl.business.model.Commissione;
import com.sourcesense.crl.business.model.ConsultazioneParere;
import com.sourcesense.crl.business.model.Firmatario;
import com.sourcesense.crl.business.model.TestoAtto;
import com.sourcesense.crl.business.service.rest.AttoService;
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
						"alf_list_atto_ricerca_avanzata", null));
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
						"alf_list_atto_ricerca_avanzata", null));

	}
	
	
	public void removeFirmatario(Firmatario firmatario){
		
		attoService.removeFirmatario(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_remove_firmatario", new String[]{firmatario.getId()}));
	}
	
	
	public TestoAtto uploadTestoAttoPresentazioneAssegnazione(Atto atto, InputStream stream, TestoAtto testoAtto) {

		return attoService.uploadTestoAtto(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_upload_testo", new String[] { atto.getId() }),atto, stream, testoAtto, TestoAtto.TESTO_PRESENTAZIONE_ASSEGNAZIONE);
	}
	
	public Allegato uploadAllegatoNoteAllegatiPresentazioneAssegnazione(Atto atto, InputStream stream, Allegato allegato) {

		return attoService.uploadAllegato(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_upload_allegato", new String[] { atto.getId() }),atto, stream, allegato, Allegato.TIPO_PRESENTAZIONE_ASSEGNAZIONE);
	}
	
	public Allegato uploadAllegatoMIS(AttoMIS atto, InputStream stream, Allegato allegato) {

		return attoService.uploadAllegato(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_upload_allegato", new String[] { atto.getId() }),atto, stream, allegato, Allegato.TIPO_PRESENTAZIONE_ASSEGNAZIONE);
	}
	
	public Allegato uploadAllegatoEAC(AttoEAC atto, InputStream stream, Allegato allegato) {

		return attoService.uploadAllegato(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_upload_allegato", new String[] { atto.getId() }),atto, stream, allegato, Allegato.TIPO_PRESENTAZIONE_ASSEGNAZIONE);
	}
	
	
	public Allegato  uploadAllegatoConsultazioni(Atto atto, InputStream stream, Allegato allegato) {

		return attoService.uploadAllegatoConsultazioni(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_upload_allegato_consultazioni", new String[] { atto.getId() }),atto, stream, allegato, Allegato.TIPO_CONSULTAZIONE);
	}
	
	
	
	public Allegato uploadAllegatoPareri(Atto atto, InputStream stream, Allegato allegato) {

		return attoService.uploadAllegatoParere(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_upload_allegato_pareri", new String[] { atto.getId() }),atto, stream, allegato, Allegato.TIPO_PARERE);
	}
	
	
	public TestoAtto changeAllegatoPresentazioneAssegnazione (Allegato allegato){
		
		
		return attoService.changeAllegato(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_change_allegato", new String[] { allegato.getId() }));
		
	} 
	
	
	public AttoMIS persistMIS(Object object) {
		return attoService.createMIS(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_new_atto", null), (AttoMIS) object);

	}
	
	public AttoEAC persistEAC(Object object) {
		return attoService.createEAC(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_new_atto", null), (AttoEAC) object);

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
    
	
	public AttoEAC findEACById(String id) {
		return attoService.findEACById(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_atto_by_id", new String[] { id }));
	}
	
	
	public AttoMIS findMISById(String id) {
		return attoService.findMISById(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_atto_by_id", new String[] { id }));
	}
	
	
	public List<CollegamentoAttiSindacato> findAllAttiSindacato() {
		return attoService.findAllAttiSindacato(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_retrieve_atti_indirizzo",null));
	}
	
	
	public List<CollegamentoAttiSindacato> findAttiSindacatoById(String idAtto) {
		return attoService.findAllAttiSindacato(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_retrieve_atti_indirizzo_atto",new String[]{idAtto}));
	}
	
	
	public List<Collegamento> findAttiCollegatiById(String idAtto) {
		return attoService.findCollegamentiAttoById(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_collegamenti_atto",new String[]{idAtto}));
	}
	
	
	

	public List<String> findTipoAttiSindacato() {
		return attoService.findTipoAttiSindacato(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_retrieve_tipo_atti_indirizzo",null));
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
	
	
	
	

	public void salvaConsultazioni(Atto atto) {
		attoService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_consultazioni", null), atto);
	}
		
	public void salvaPareri(ConsultazioneParere consultazioneParere) {
		attoService.salvaPareri(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_pareri", null), consultazioneParere);
	}
	
	public void salvaCollegamenti(Atto atto) {
		attoService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_collegamenti", null), atto);
	}
	
	public void salvaCollegamentiAttiSindacato(Atto atto) {
		attoService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_collegamenti_atto_sindacato", null), atto);
	}
	
	
	
	public void salvaRelatoriAula(Atto atto) {
		attoService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_relatori_aula", null), atto);
	}
	
	public void chiusuraAtto(Atto atto) {
		attoService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_chiusura_atto", null), atto);
	}
	
	public String regioniUrl(String data,String numero){
	return urlBuilder.buildSimpleURL("leggi_regionali_link", new String[]{data,numero});
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
