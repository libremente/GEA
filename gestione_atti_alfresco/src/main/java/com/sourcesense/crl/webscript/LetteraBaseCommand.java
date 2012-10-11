package com.sourcesense.crl.webscript;

import java.io.IOException;


import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.search.SearchService;


public abstract class LetteraBaseCommand implements LetteraCommand{
	
	protected ContentService contentService;
	protected SearchService searchService;
	protected NodeService nodeService;
	
	protected String CRL_TEMPLATE_MODEL = "http://www.regione.lombardia.it/content/model/template/1.0";
	protected String PROP_NOME_PRES_CONS_REG = "nomePresidenteConsiglioRegionale";
	
	
	
	public abstract byte[] generate(byte[] templateByteArray, NodeRef templateNodeRef) throws IOException;
	
	
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
}
