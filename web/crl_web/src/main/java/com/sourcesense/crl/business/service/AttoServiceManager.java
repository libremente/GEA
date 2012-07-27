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
	private transient URLBuilder urlBuilder;

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

		return searchAtti(attoInit);

	}
	
	
	/*public Atto persist(Object object) {
		return attoService.create(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_new_atto", null), (Atto) object);

	}*/

	public Allegato uploadFile(Atto atto, Allegato allegato,InputStream stream) {

		return attoService.uploadFile(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_upload_file", new String[] { atto.getId() }),atto, stream);
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
