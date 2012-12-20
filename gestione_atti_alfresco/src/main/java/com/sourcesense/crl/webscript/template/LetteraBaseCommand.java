package com.sourcesense.crl.webscript.template;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchParameters;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.DynamicNamespacePrefixResolver;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;

import com.sourcesense.crl.util.AttoUtil;


public abstract class LetteraBaseCommand implements LetteraCommand{
	
	protected ContentService contentService;
	protected SearchService searchService;
	protected NodeService nodeService;
	protected ServiceRegistry serviceRegistry;
	protected AttoUtil attoUtil;
	
	

	// crlTemplateModel properties 
	protected String PROP_FIRMATARIO = "firmatario";
	protected String PROP_UFFICIO = "ufficio";
	protected String PROP_DIREZIONE = "direzione";
	protected String PROP_NUMEROTELFIRMATARIO = "numeroTelFirmatario";
	protected String PROP_EMAILFIRMATARIO = "emailFirmatario";
	
	public abstract byte[] generate(byte[] templateByteArray, NodeRef templateNodeRef, NodeRef attoNodeRef, String gruppo) throws IOException;
	
	
	// Get property value 
	public String getStringProperty(NodeRef attoNodeRef, String nameSpaceURI, String localName){
		String value = (String) nodeService.getProperty(attoNodeRef, QName.createQName(nameSpaceURI, localName));
		
		if(value==null){
			value = "";
		}
	
		return value;
	}
	
	
	// Search Commissioni Consultive function
	public List<NodeRef> getCommissioniConsultive(NodeRef attoNodeRef){
		
		List<NodeRef> commissioniConsultiveList = new ArrayList<NodeRef>();
		
    	DynamicNamespacePrefixResolver namespacePrefixResolver = new DynamicNamespacePrefixResolver(null);
        namespacePrefixResolver.registerNamespace(NamespaceService.SYSTEM_MODEL_PREFIX, NamespaceService.SYSTEM_MODEL_1_0_URI);
        namespacePrefixResolver.registerNamespace(NamespaceService.CONTENT_MODEL_PREFIX, NamespaceService.CONTENT_MODEL_1_0_URI);
        namespacePrefixResolver.registerNamespace(NamespaceService.APP_MODEL_PREFIX, NamespaceService.APP_MODEL_1_0_URI);
    	
    	// Get current Passaggio 
    	NodeRef passaggioNodeRef = attoUtil.getLastPassaggio(attoNodeRef);
    	String lucenePassaggioNodePath = nodeService.getPath(passaggioNodeRef).toPrefixString(namespacePrefixResolver);
  
		
		// Get commissioni consultive
    	ResultSet commissioniConsultiveNodes = searchService.query(attoNodeRef.getStoreRef(),
  				SearchService.LANGUAGE_LUCENE, "PATH:\""+lucenePassaggioNodePath+"/cm:Commissioni/*\" AND @crlatti\\:ruoloCommissione:\""+attoUtil.RUOLO_COMM_CONSULTIVA+"\"");
    	
    	
    	for (int i=0; i<commissioniConsultiveNodes.length(); i++) {
    			
    		commissioniConsultiveList.add(commissioniConsultiveNodes.getNodeRef(i));
    	}
    	
		
		return commissioniConsultiveList;
	}
	
	
	public NodeRef getLastOrganoStatutarioAssegnato(NodeRef attoNodeRef){
		
		NodeRef lastOrgano = null;
		
		DynamicNamespacePrefixResolver namespacePrefixResolver = new DynamicNamespacePrefixResolver(null);
        namespacePrefixResolver.registerNamespace(NamespaceService.SYSTEM_MODEL_PREFIX, NamespaceService.SYSTEM_MODEL_1_0_URI);
        namespacePrefixResolver.registerNamespace(NamespaceService.CONTENT_MODEL_PREFIX, NamespaceService.CONTENT_MODEL_1_0_URI);
        namespacePrefixResolver.registerNamespace(NamespaceService.APP_MODEL_PREFIX, NamespaceService.APP_MODEL_1_0_URI);
    	
    	// Get current atto 
    	String luceneAttoNodePath = nodeService.getPath(attoNodeRef).toPrefixString(namespacePrefixResolver);
	
    		
    	String parereType = "{"+attoUtil.CRL_ATTI_MODEL+"}"+attoUtil.PARERE_TYPE;
    			
    	SearchParameters sp = new SearchParameters();
        sp.addStore(attoNodeRef.getStoreRef());
        sp.setLanguage(SearchService.LANGUAGE_LUCENE);
        sp.setQuery("PATH:\""+luceneAttoNodePath+"/cm:Pareri/*\" AND TYPE:\""+parereType+"\"");
        String field = "{"+attoUtil.CRL_ATTI_MODEL+"}"+attoUtil.PROP_DATA_ASSEGNAZIONE_PARERE;
        sp.addSort(field, false);
    
        ResultSet pareriNodes = serviceRegistry.getSearchService().query(sp);
           
        if(pareriNodes.getNodeRefs().size()>0){
        	lastOrgano = pareriNodes.getNodeRef(0); 
        }
		
		return lastOrgano; 
	}
	
	public NodeRef getLastFirmatario(NodeRef attoNodeRef){
		
		NodeRef lastFirmatario = null;
		
		DynamicNamespacePrefixResolver namespacePrefixResolver = new DynamicNamespacePrefixResolver(null);
        namespacePrefixResolver.registerNamespace(NamespaceService.SYSTEM_MODEL_PREFIX, NamespaceService.SYSTEM_MODEL_1_0_URI);
        namespacePrefixResolver.registerNamespace(NamespaceService.CONTENT_MODEL_PREFIX, NamespaceService.CONTENT_MODEL_1_0_URI);
        namespacePrefixResolver.registerNamespace(NamespaceService.APP_MODEL_PREFIX, NamespaceService.APP_MODEL_1_0_URI);
    	
    	// Get current atto 
    	String luceneAttoNodePath = nodeService.getPath(attoNodeRef).toPrefixString(namespacePrefixResolver);
	
    		
    	String firmatarioType = "{"+attoUtil.CRL_ATTI_MODEL+"}"+attoUtil.FIRMATARIO_TYPE;
    			
    	SearchParameters sp = new SearchParameters();
        sp.addStore(attoNodeRef.getStoreRef());
        sp.setLanguage(SearchService.LANGUAGE_LUCENE);
        sp.setQuery("PATH:\""+luceneAttoNodePath+"/cm:Firmatari/*\" AND TYPE:\""+firmatarioType+"\"");
        String field = "{"+attoUtil.CRL_ATTI_MODEL+"}"+attoUtil.PROP_DATA_FIRMA_FIRMATARIO;
        sp.addSort(field, false);
    
        ResultSet firmatariNodes = serviceRegistry.getSearchService().query(sp);
           
        if(firmatariNodes.getNodeRefs().size()>0){
        	lastFirmatario = firmatariNodes.getNodeRef(0); 
        }
		
		return lastFirmatario; 
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

	public ServiceRegistry getServiceRegistry() {
		return serviceRegistry;
	}

	public void setServiceRegistry(ServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}
	
	public AttoUtil getAttoUtil() {
		return attoUtil;
	}

	public void setAttoUtil(AttoUtil attoUtil) {
		this.attoUtil = attoUtil;
	}
    
}
