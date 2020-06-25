/* 
 * Copyright (C) 2019 Consiglio Regionale della Lombardia
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.sourcesense.crl.business.service;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sourcesense.crl.business.model.Allegato;
import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.model.AttoEAC;
import com.sourcesense.crl.business.model.AttoMIS;
import com.sourcesense.crl.business.model.AttoSearch;
import com.sourcesense.crl.business.model.Collegamento;
import com.sourcesense.crl.business.model.CollegamentoAttiSindacato;
import com.sourcesense.crl.business.model.ConsultazioneParere;
import com.sourcesense.crl.business.model.Firmatario;
import com.sourcesense.crl.business.model.TestoAtto;
import com.sourcesense.crl.business.service.rest.AttoService;
import com.sourcesense.crl.util.URLBuilder;

/**
 * Contiene le operazioni degli atti
 * 
 * @author sourcesense
 *
 */
@Service("attoServiceManager")
public class AttoServiceManager implements ServiceManager {

	@Autowired
	private URLBuilder urlBuilder;

	@Autowired
	private AttoService attoService;

	/**
	 * 
	 * @param atto
	 * @return
	 */
	public List<Atto> searchAtti(Atto atto) {
		return attoService.parametricSearch(atto,
				urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_list_atto_ricerca_avanzata", null));
	}

	/**
	 * 
	 * @return
	 */
	public List<Atto> initListAtti() {

		AttoSearch attoInit = new AttoSearch();
		attoInit.setDataIniziativaA(new Date());
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -2);
		attoInit.setDataIniziativaDa(calendar.getTime());
		return attoService.parametricSearch(attoInit,
				urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_list_atto_ricerca_avanzata", null));

	}

	/**
	 * 
	 * @param firmatario
	 */
	public void removeFirmatario(Firmatario firmatario) {

		attoService.removeFirmatario(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_remove_firmatario",
				new String[] { firmatario.getId() }));
	}

	/**
	 * 
	 * @param atto
	 */
	public void removeAtto(Atto atto) {

		attoService.removeFirmatario(
				urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_remove_atto", new String[] { atto.getId() }));
	}

	/**
	 * 
	 * @param atto
	 * @param stream
	 * @param testoAtto
	 * @return
	 */
	public TestoAtto uploadTestoAttoPresentazioneAssegnazione(Atto atto, InputStream stream, TestoAtto testoAtto) {

		return attoService.uploadTestoAtto(
				urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_upload_testo", new String[] { atto.getId() }),
				atto, stream, testoAtto, TestoAtto.TESTO_PRESENTAZIONE_ASSEGNAZIONE);
	}

	/**
	 * 
	 * @param atto
	 * @param stream
	 * @param allegato
	 * @return
	 */
	public Allegato uploadAllegatoNoteAllegatiPresentazioneAssegnazione(Atto atto, InputStream stream,
			Allegato allegato) {

		return attoService.uploadAllegato(
				urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_upload_allegato",
						new String[] { atto.getId() }),
				atto, stream, allegato, Allegato.TIPO_PRESENTAZIONE_ASSEGNAZIONE);
	}

	/**
	 * 
	 * @param atto
	 * @param stream
	 * @param allegato
	 * @return
	 */
	public Allegato uploadAllegatoMIS(AttoMIS atto, InputStream stream, Allegato allegato) {

		return attoService.uploadAllegato(
				urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_upload_allegato",
						new String[] { atto.getId() }),
				atto, stream, allegato, Allegato.TIPO_PRESENTAZIONE_ASSEGNAZIONE);
	}

	/**
	 * 
	 * @param atto
	 * @param stream
	 * @param allegato
	 * @return
	 */
	public Allegato uploadAllegatoEAC(AttoEAC atto, InputStream stream, Allegato allegato) {

		return attoService.uploadAllegato(
				urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_upload_allegato",
						new String[] { atto.getId() }),
				atto, stream, allegato, Allegato.TIPO_PRESENTAZIONE_ASSEGNAZIONE);
	}

	/**
	 * 
	 * @param atto
	 * @param stream
	 * @param allegato
	 * @return
	 */
	public Allegato uploadAllegatoConsultazioni(Atto atto, InputStream stream, Allegato allegato) {

		return attoService.uploadAllegatoConsultazioni(urlBuilder.buildAlfrescoURL("alfresco_context_url",
				"alf_upload_allegato_consultazioni", new String[] { atto.getId() }), atto, stream, allegato,
				Allegato.TIPO_CONSULTAZIONE);
	}

	/**
	 * 
	 * @param atto
	 * @param stream
	 * @param allegato
	 * @return
	 */
	public Allegato uploadAllegatoPareri(Atto atto, InputStream stream, Allegato allegato) {

		return attoService.uploadAllegatoParere(urlBuilder.buildAlfrescoURL("alfresco_context_url",
				"alf_upload_allegato_pareri", new String[] { atto.getId() }), atto, stream, allegato,
				Allegato.TIPO_PARERE);
	}

	/**
	 * 
	 * @param allegato
	 * @return
	 */
	public TestoAtto changeAllegatoPresentazioneAssegnazione(Allegato allegato) {

		return attoService.changeAllegato(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_change_allegato",
				new String[] { allegato.getId() }));

	}

	/**
	 * 
	 * @param atto
	 * @return
	 */
	public Atto updateAtto(Atto atto) {
		return attoService.updateAtto(
				urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_update_atto", new String[] { atto.getId() }),
				atto);

	}

	/**
	 * 
	 * @param atto
	 */
	public void deleteAtto(Atto atto) {
		attoService.deleteAtto(
				urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_delete_atto", new String[] { atto.getId() }));

	}

	/**
	 * 
	 * @param object
	 * @return
	 */
	public AttoMIS persistMIS(Object object) {
		return attoService.createMIS(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_new_atto", null),
				(AttoMIS) object);

	}

	/**
	 * 
	 * @param object
	 * @return
	 */
	public AttoEAC persistEAC(Object object) {
		return attoService.createEAC(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_new_atto", null),
				(AttoEAC) object);

	}

	@Override
	public Atto persist(Object object) {
		return attoService.create(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_new_atto", null),
				(Atto) object);

	}

	/**
	 * 
	 */
	public Atto findById(String id) {
		return attoService
				.findById(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_atto_by_id", new String[] { id }));
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public AttoEAC findEACById(String id) {
		return attoService.findEACById(
				urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_atto_by_id", new String[] { id }));
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public AttoMIS findMISById(String id) {
		return attoService.findMISById(
				urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_atto_by_id", new String[] { id }));
	}

	/**
	 * 
	 * @param tipoAtto
	 * @param dataCreazioneDa
	 * @param dataCreazioneA
	 * @return
	 */
	public List<CollegamentoAttiSindacato> findAllAttiSindacato(String tipoAtto, String dataCreazioneDa,
			String dataCreazioneA) {
		return attoService.findAllAttiSindacato(urlBuilder.buildAlfrescoURL("alfresco_context_url",
				"alf_retrieve_atti_indirizzo", new String[] { tipoAtto, dataCreazioneDa, dataCreazioneA }));
	}

	/**
	 * 
	 * @param idAtto
	 * @return
	 */
	public List<CollegamentoAttiSindacato> findAttiSindacatoById(String idAtto) {
		return attoService.findAllAttiSindacato(urlBuilder.buildAlfrescoURL("alfresco_context_url",
				"alf_retrieve_atti_indirizzo_atto", new String[] { idAtto }));
	}

	/**
	 * 
	 * @param idAtto
	 * @return
	 */
	public List<Collegamento> findAttiCollegatiById(String idAtto) {
		return attoService.findCollegamentiAttoById(
				urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_collegamenti_atto", new String[] { idAtto }));
	}

	/**
	 * 
	 * @return
	 */
	public List<String> findTipoAttiSindacato() {
		return attoService.findTipoAttiSindacato(
				urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_retrieve_tipo_atti_indirizzo", null));
	}

	/**
	 * 
	 * @param atto
	 */
	public void presaInCaricoSC(Atto atto) {

		attoService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url",
				"alf_presa_carico_presentazione_assegnazione", new String[] { atto.getId() }), atto);

	}

	/**
	 * 
	 * @param atto
	 */
	public void salvaInfoGeneraliPresentazione(Atto atto) {
		attoService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url",
				"alf_salva_info_generali_presentazione_assegnazione", new String[] { atto.getId() }), atto);
	}

	/**
	 * 
	 * @param atto
	 */
	public void salvaAmmissibilitaPresentazione(Atto atto) {
		attoService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url",
				"alf_salva_ammissibilita_presentazione_assegnazione", null), atto);
	}

	/**
	 * 
	 * @param atto
	 */
	public void salvaAssegnazionePresentazione(Atto atto) {
		attoService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url",
				"alf_salva_assegnazione_presentazione_assegnazione", null), atto);
	}

	/**
	 * 
	 * @param atto
	 */
	public void salvaNoteAllegatiPresentazione(Atto atto) {
		attoService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url",
				"alf_salva_note_allegati_presentazione_assegnazione", null), atto);
	}

	/**
	 * 
	 * @param atto
	 */
	public void salvaConsultazioni(Atto atto) {
		attoService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_consultazioni", null), atto);
	}

	/**
	 * 
	 * @param consultazioneParere
	 */
	public void salvaPareri(ConsultazioneParere consultazioneParere) {
		attoService.salvaPareri(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_pareri", null),
				consultazioneParere);
	}

	/**
	 * 
	 * @param atto
	 */
	public void salvaCollegamenti(Atto atto) {
		attoService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_collegamenti", null), atto);
	}

	/**
	 * 
	 * @param atto
	 */
	public void salvaCollegamentiAttiSindacato(Atto atto) {
		attoService.merge(
				urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_collegamenti_atto_sindacato", null),
				atto);
	}

	/**
	 * 
	 * @param atto
	 */
	public void salvaRelatoriAula(Atto atto) {
		attoService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_relatori_aula", null), atto);
	}

	/**
	 * 
	 * @param atto
	 */
	public void chiusuraAtto(Atto atto) {
		attoService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_chiusura_atto", null), atto);
	}

	/**
	 * 
	 * @param data
	 * @param numero
	 * @return
	 */
	public String regioniUrl(String data, String numero) {
		return urlBuilder.buildSimpleURL("leggi_regionali_link", new String[] { data, numero });
	}

	@Override
	public Atto merge(Object object) {
		return null;
	}

	@Override
	public boolean remove(Object object) {
		return false;
	}

	@Override
	public List<Object> retrieveAll() {
		return null;
	}

	@Override
	public Map<String, String> findAll() {
		return null;
	}

}
