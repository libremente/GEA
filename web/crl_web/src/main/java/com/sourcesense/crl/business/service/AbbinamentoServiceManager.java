package com.sourcesense.crl.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sourcesense.crl.business.model.Abbinamento;
import com.sourcesense.crl.business.model.GestioneAbbinamento;
import com.sourcesense.crl.business.service.rest.AbbinamentoService;
import com.sourcesense.crl.util.URLBuilder;

@Service("abbinamentoServiceManager")
public class AbbinamentoServiceManager {

	@Autowired
	private  URLBuilder urlBuilder;

	@Autowired
	private AbbinamentoService abbinamentoService;
	
	public void salvaAbbinamento(GestioneAbbinamento abbinamento) {
		abbinamentoService.merge(urlBuilder.buildAlfrescoURL("alfresco_context_url", "alf_salva_abbinamento_esame_commissioni", null), abbinamento);
	}
}
