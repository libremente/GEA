package com.sourcesense.crl.webscript.template;

import java.io.IOException;


import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.search.SearchService;


public abstract class LetteraBaseCommand implements LetteraCommand{
	
	protected ContentService contentService;
	protected SearchService searchService;
	protected NodeService nodeService;
	
	// crl namespaces
	protected String CRL_TEMPLATE_MODEL = "http://www.regione.lombardia.it/content/model/template/1.0";
	protected String CRL_ATTI_MODEL = "http://www.regione.lombardia.it/content/model/atti/1.0";
	
	// crlAttiModel type
	protected String COMMISSIONE_TYPE = "commissione";
	protected String RELATORE_TYPE = "relatore";
	
	// crlAttiModel properties 
	protected String PROP_ANNO = "anno";
	protected String PROP_MESE = "mese";
	protected String PROP_LEGISLATURA = "legislatura";
	protected String PROP_NUM_ATTO = "numeroAtto";
	protected String PROP_OGGETTO_ATTO = "oggetto";
	protected String PROP_INIZIATIVA = "descrizioneIniziativa";
	protected String PROP_COMMISSIONE_REFERENTE = "commReferente";
	protected String PROP_COMMISSIONI_CONSULTIVE = "commConsultiva";
	protected String PROP_ORGANISMI_STATUTARI = "organismiStatutari";
	protected String PROP_FIRMATARI = "firmatari";
	protected String PROP_NUMERO_REPERTORIO = "numeroRepertorio";
	protected String PROP_DATA_VOTAZIONE_COMMISSIONE = "dataVotazioneCommissione";
	

	// crlTemplateModel properties 
	protected String PROP_NOME_PRES_CONS_REG = "nomePresidenteConsiglioRegionale";
	
	public abstract byte[] generate(byte[] templateByteArray, NodeRef templateNodeRef, NodeRef attoNodeRef) throws IOException;
	
	
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
