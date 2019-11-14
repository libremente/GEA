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

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sourcesense.crl.business.model.Allegato;
import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.model.Commissione;
import com.sourcesense.crl.business.model.CommissioneReferente;
import com.sourcesense.crl.business.model.EsameAula;
import com.sourcesense.crl.business.model.GestioneSedute;
import com.sourcesense.crl.business.model.Report;
import com.sourcesense.crl.business.model.Seduta;
import com.sourcesense.crl.business.service.rest.AulaService;
import com.sourcesense.crl.business.service.rest.SeduteService;
import com.sourcesense.crl.util.URLBuilder;


@Service("seduteServiceManager")
public class SeduteServiceManager implements ServiceManager{

	@Autowired
	private SeduteService seduteService;
	
	@Autowired
	private  URLBuilder urlBuilder;
	
	public void deleteSeduta(String idSeduta) {
		 seduteService.delete(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_elimina_seduta", new String[]{idSeduta}));
	}
	
	public Seduta salvaSeduta(GestioneSedute gestioneSedute) {
		return seduteService.create(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_seduta", null), gestioneSedute);
	}
	
	public Seduta updateSeduta(GestioneSedute gestioneSedute) {
		return seduteService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_seduta", null), gestioneSedute);
	}
	
	public List<Seduta>  getSedute(String gruppo, String legislatura) { 
		return seduteService.findByGroup(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_elenco_sedute", null),gruppo,legislatura);
	}
	
	
	public Seduta  getSeduta(String gruppo, String dataSeduta, String  legislatura) {
	
		return seduteService.findByDate(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_dettaglio_seduta", null),gruppo,dataSeduta, legislatura);
	}
	
	
	public void salvaOdg(Seduta seduta) {
		seduteService.mergeSeduta(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_odg", null), seduta);
	}
	
	
	public Allegato uploadOgg(Seduta seduta, InputStream stream, Allegato testoAtto) {

		return seduteService.uploadOdg(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_upload_odg", null),seduta, stream, testoAtto);
	}
	
	public Allegato uploadVerbale(Seduta seduta, InputStream stream, Allegato testoAtto) {

		return seduteService.uploadVerbale(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_upload_verbale", null),seduta, stream, testoAtto);
	}
	
	public InputStream getODGFile(String tipoTemplate,String idSeduta,String gruppo ) { 
		return seduteService.getFile(urlBuilder.buildAlfrescoURL( 
				"alfresco_context_url", "alf_get_odg", new String[]{tipoTemplate,idSeduta,gruppo}));
		
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
