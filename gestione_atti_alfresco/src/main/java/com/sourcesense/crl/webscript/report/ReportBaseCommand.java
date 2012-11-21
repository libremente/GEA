package com.sourcesense.crl.webscript.report;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.search.SearchService;

public abstract class ReportBaseCommand implements ReportCommand {
	protected ContentService contentService;
	protected SearchService searchService;
	protected NodeService nodeService;
	protected Map<String,String> json2luceneField;

	protected static final String CRL_ATTI_MODEL = "http://www.regione.lombardia.it/content/model/atti/1.0";
	
	protected static final String RUOLO_COMM_REFERENTE = "Referente";
	protected static final String RUOLO_COMM_COREFERENTE = "Co-Referente";
	protected static final String RUOLO_COMM_CONSULTIVA = "Consultiva";
	protected static final String RUOLO_COMM_REDIGENTE = "Redigente";
	protected static final String RUOLO_COMM_DELIBERANTE = "Deliberante";
	
	/**
	 * init a simple map that link json names to lucene names
	 * */
	public void initJson2lucene(){
		json2luceneField=new HashMap<String,String>();
		json2luceneField.put(RUOLO_COMM_REFERENTE, "crlatti:commReferente");
		json2luceneField.put(RUOLO_COMM_COREFERENTE, "crlatti:commCoreferente");
		json2luceneField.put(RUOLO_COMM_CONSULTIVA, "crlatti:commConsultiva");
		json2luceneField.put(RUOLO_COMM_REDIGENTE, "crlatti:commRedigente");
		json2luceneField.put(RUOLO_COMM_DELIBERANTE, "crlatti:commDeliberante");
		
	}
	
	public Map<String, String> getJson2luceneField() {
		return json2luceneField;
	}


	public void setJson2luceneField(Map<String, String> json2luceneField) {
		this.json2luceneField = json2luceneField;
	}


	public ContentService getContentService() {
		return contentService;
	}


	public void setContentService(ContentService contentService) {
		this.contentService = contentService;
	}


	public SearchService getSearchService() {
		return searchService;
	}


	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}


	public NodeService getNodeService() {
		return nodeService;
	}


	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}


	@Override
	public abstract byte[] generate(byte[] templateByteArray,String json) throws IOException;
	

}
