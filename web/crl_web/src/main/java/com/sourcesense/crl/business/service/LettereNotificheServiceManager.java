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
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sourcesense.crl.business.model.Lettera;
import com.sourcesense.crl.business.service.rest.LettereNotificheService;
import com.sourcesense.crl.util.URLBuilder;

/**
 * Servizio per la gestione delle lettere
 * 
 * @author sourcesense
 *
 */
@Service("lettereNotificheServiceManager")
public class LettereNotificheServiceManager implements ServiceManager {

	@Autowired
	private URLBuilder urlBuilder;

	@Autowired
	private LettereNotificheService lettereNotificheService;

	/**
	 * Ritorna la lettera completa secondo le informazioni parziali fornite
	 * 
	 * @param lettera
	 * @return
	 */
	public Lettera getLettera(Lettera lettera) {

		return lettereNotificheService.getLettera(urlBuilder.buildAlfrescoURL("alfresco_context_url",
				"alf_retrieve_lettera", new String[] { lettera.getTipoTemplate() }));
	}

	/**
	 * Aggiorna la lettera
	 * 
	 * @param lettera
	 */
	public void updateLettera(Lettera lettera) {
		lettereNotificheService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_update_lettera", null),
				lettera);
	}

	/**
	 * Ritorna il contenuto della lettera di un atto
	 * 
	 * @param lettera
	 * @param idAtto
	 * @param gruppo
	 * @return
	 */
	public InputStream getLetteraFile(Lettera lettera, String idAtto, String gruppo) {

		return lettereNotificheService.getFile(urlBuilder.buildAlfrescoURL("alfresco_context_url",
				"alf_retrieve_lettera_bin", new String[] { idAtto, lettera.getTipoTemplate(), gruppo }));
	}

	@Override
	public Object persist(Object object) {
		return null;
	}

	@Override
	public Object merge(Object object) {
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

	@Override
	public Object findById(String id) {
		return null;
	}

}
