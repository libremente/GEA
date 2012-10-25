package com.sourcesense.crl.webscript.template;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.alfresco.model.ContentModel;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchParameters;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.DynamicNamespacePrefixResolver;
import org.alfresco.service.namespace.NamespaceService;


public abstract class LetteraBaseCommand implements LetteraCommand{
	
	protected ContentService contentService;
	protected SearchService searchService;
	protected NodeService nodeService;
	protected ServiceRegistry serviceRegistry;
	
	// crl namespaces
	protected static final String CRL_TEMPLATE_MODEL = "http://www.regione.lombardia.it/content/model/template/1.0";
	protected static final String CRL_ATTI_MODEL = "http://www.regione.lombardia.it/content/model/atti/1.0";
	
	// crlAttiModel type
	protected static final String COMMISSIONE_TYPE = "commissione";
	protected static final String RELATORE_TYPE = "relatore";
	protected static final String PARERE_TYPE = "parere";
	
	// crlAttiModel properties 
	protected static final String PROP_ANNO = "anno";
	protected static final String PROP_MESE = "mese";
	protected static final String PROP_LEGISLATURA = "legislatura";
	protected static final String PROP_NUM_ATTO = "numeroAtto";
	protected static final String PROP_OGGETTO_ATTO = "oggetto";
	protected static final String PROP_DESCRIZIONE_INIZIATIVA = "descrizioneIniziativa";
	protected static final String PROP_TIPO_INIZIATIVA = "tipoIniziativa";
	protected static final String PROP_COMMISSIONI_REFERENTI = "commReferente";
	protected static final String PROP_COMMISSIONI_CONSULTIVE = "commConsultiva";
	protected static final String PROP_COMMISSIONE_COREFERENTE = "coreferente";
	protected static final String PROP_COMMISSIONE_REDIGENTE = "redigente";
	protected static final String PROP_COMMISSIONE_DELIBERANTE = "deliberante";
	protected static final String PROP_ORGANISMI_STATUTARI = "organismiStatutari";
	protected static final String PROP_FIRMATARI = "firmatari";
	protected static final String PROP_NUMERO_REPERTORIO = "numeroRepertorio";
	protected static final String PROP_DATA_VOTAZIONE_COMMISSIONE = "dataVotazioneCommissione";
	protected static final String PROP_DATA_ASSEGNAZIONE_COMMISSIONE = "dataAssegnazioneCommissione";
	protected static final String PROP_DATA_ASSEGNAZIONE_PARERE = "dataAssegnazioneParere";
	
	protected static final String RUOLO_COMM_REFERENTE = "Referente";
	protected static final String RUOLO_COMM_COREFERENTE = "Co-Referente";
	protected static final String RUOLO_COMM_CONSULTIVA = "Consultiva";
	protected static final String RUOLO_COMM_REDIGENTE = "Redigente";
	protected static final String RUOLO_COMM_DELIBERANTE = "Deliberante";
	
	protected static final String INIZIATIVA_CONSILIARE = "DI INIZIATIVA CONSILIARE";
	protected static final String INIZIATIVA_POPOLARE = "DI INIZIATIVA POPOLARE";

	// crlTemplateModel properties 
	protected String PROP_FIRMATARIO = "firmatario";
	
	public abstract byte[] generate(byte[] templateByteArray, NodeRef templateNodeRef, NodeRef attoNodeRef) throws IOException;
	
	
	// Search last "passaggio" function
	public NodeRef getLastPassaggio(NodeRef attoNodeRef){
		
		NodeRef passaggio = null;

		DynamicNamespacePrefixResolver namespacePrefixResolver = new DynamicNamespacePrefixResolver(null);
        namespacePrefixResolver.registerNamespace(NamespaceService.SYSTEM_MODEL_PREFIX, NamespaceService.SYSTEM_MODEL_1_0_URI);
        namespacePrefixResolver.registerNamespace(NamespaceService.CONTENT_MODEL_PREFIX, NamespaceService.CONTENT_MODEL_1_0_URI);
        namespacePrefixResolver.registerNamespace(NamespaceService.APP_MODEL_PREFIX, NamespaceService.APP_MODEL_1_0_URI);

		
    	String luceneAttoNodePath = nodeService.getPath(attoNodeRef).toPrefixString(namespacePrefixResolver);
				
	  	ResultSet passaggiNodes = searchService.query(attoNodeRef.getStoreRef(), 
  				SearchService.LANGUAGE_LUCENE, "PATH:\""+luceneAttoNodePath+"/cm:Passaggi/*\"");
   
	  	int numeroPassaggio = 0;
	  	String nomePassaggio = "";
	  	int passaggioMax = 0;
		for(int i=0; i<passaggiNodes.length(); i++) {
			
			nomePassaggio = (String) nodeService.getProperty(passaggiNodes.getNodeRef(i), ContentModel.PROP_NAME);
			numeroPassaggio = Integer.parseInt(nomePassaggio.substring(9));
			
			if(numeroPassaggio > passaggioMax) {
				passaggioMax = numeroPassaggio;
				passaggio = passaggiNodes.getNodeRef(i) ;
			}
			
		}
		
		return passaggio;
	}
		

		
	// Search Commissioni Principali function
	public List<NodeRef> getCommissioniPrincipali(NodeRef attoNodeRef){
		
		List<NodeRef> commissioniPrincipaliList = new ArrayList<NodeRef>();
		
    	DynamicNamespacePrefixResolver namespacePrefixResolver = new DynamicNamespacePrefixResolver(null);
        namespacePrefixResolver.registerNamespace(NamespaceService.SYSTEM_MODEL_PREFIX, NamespaceService.SYSTEM_MODEL_1_0_URI);
        namespacePrefixResolver.registerNamespace(NamespaceService.CONTENT_MODEL_PREFIX, NamespaceService.CONTENT_MODEL_1_0_URI);
        namespacePrefixResolver.registerNamespace(NamespaceService.APP_MODEL_PREFIX, NamespaceService.APP_MODEL_1_0_URI);
    	
    	// Get current Passaggio 
    	NodeRef passaggioNodeRef = getLastPassaggio(attoNodeRef);
    	String lucenePassaggioNodePath = nodeService.getPath(passaggioNodeRef).toPrefixString(namespacePrefixResolver);
  
		
		// Get commissione referente
    	ResultSet commissioneReferenteNodes = searchService.query(attoNodeRef.getStoreRef(),
  				SearchService.LANGUAGE_LUCENE, "PATH:\""+lucenePassaggioNodePath+"/cm:Commissioni/*\" AND @crlatti\\:ruoloCommissione:\""+RUOLO_COMM_REFERENTE+"\"");
    	
    	
    	// Check for commissione referente and co-referente
    	if(commissioneReferenteNodes.length()>0) {
    		
    		
    		commissioniPrincipaliList.add(commissioneReferenteNodes.getNodeRef(0));
    	
    		ResultSet commissioneCoreferenteNodes = searchService.query(attoNodeRef.getStoreRef(),
      				SearchService.LANGUAGE_LUCENE, "PATH:\""+lucenePassaggioNodePath+"/cm:Commissioni/*\" AND @crlatti\\:ruoloCommissione:\""+RUOLO_COMM_COREFERENTE+"\"");
        
    		if(commissioneCoreferenteNodes.length()>0) {
    			
    			commissioniPrincipaliList.add(commissioneCoreferenteNodes.getNodeRef(0));
    		}
    		
    	// If commissione referente not exists	
    	}else{
    		
    		ResultSet commissioneRedigenteNodes = searchService.query(attoNodeRef.getStoreRef(),
      				SearchService.LANGUAGE_LUCENE, "PATH:\""+lucenePassaggioNodePath+"/cm:Commissioni/*\" AND @crlatti\\:ruoloCommissione:\""+RUOLO_COMM_REDIGENTE+"\"");
    		
    		// Check for commissione redigente
    		if(commissioneRedigenteNodes.length()>0) {
    			
    			commissioniPrincipaliList.add(commissioneRedigenteNodes.getNodeRef(0));
    		
    		// If commissione redigente not exists
    		}else {
    			
    			ResultSet commissioneDeliberanteNodes = searchService.query(attoNodeRef.getStoreRef(),
          				SearchService.LANGUAGE_LUCENE, "PATH:\""+lucenePassaggioNodePath+"/cm:Commissioni/*\" AND @crlatti\\:ruoloCommissione:\""+RUOLO_COMM_DELIBERANTE+"\"");
    			
    			// Check for commissione redigente
        		if(commissioneDeliberanteNodes.length()>0) {
    			
        			commissioniPrincipaliList.add(commissioneDeliberanteNodes.getNodeRef(0));
        		}
    			
    		}
    		
    	}    	
		
		
		return commissioniPrincipaliList;
	}
	
	
	// Search Commissioni Consultive function
	public List<NodeRef> getCommissioniConsultive(NodeRef attoNodeRef){
		
		List<NodeRef> commissioniConsultiveList = new ArrayList<NodeRef>();
		
    	DynamicNamespacePrefixResolver namespacePrefixResolver = new DynamicNamespacePrefixResolver(null);
        namespacePrefixResolver.registerNamespace(NamespaceService.SYSTEM_MODEL_PREFIX, NamespaceService.SYSTEM_MODEL_1_0_URI);
        namespacePrefixResolver.registerNamespace(NamespaceService.CONTENT_MODEL_PREFIX, NamespaceService.CONTENT_MODEL_1_0_URI);
        namespacePrefixResolver.registerNamespace(NamespaceService.APP_MODEL_PREFIX, NamespaceService.APP_MODEL_1_0_URI);
    	
    	// Get current Passaggio 
    	NodeRef passaggioNodeRef = getLastPassaggio(attoNodeRef);
    	String lucenePassaggioNodePath = nodeService.getPath(passaggioNodeRef).toPrefixString(namespacePrefixResolver);
  
		
		// Get commissione referente
    	ResultSet commissioniConsultiveNodes = searchService.query(attoNodeRef.getStoreRef(),
  				SearchService.LANGUAGE_LUCENE, "PATH:\""+lucenePassaggioNodePath+"/cm:Commissioni/*\" AND @crlatti\\:ruoloCommissione:\""+RUOLO_COMM_CONSULTIVA+"\"");
    	
    	
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
	
    		
    	String parereType = "{"+CRL_ATTI_MODEL+"}"+PARERE_TYPE;
    			
    	SearchParameters sp = new SearchParameters();
        sp.addStore(attoNodeRef.getStoreRef());
        sp.setLanguage(SearchService.LANGUAGE_LUCENE);
        sp.setQuery("PATH:\""+luceneAttoNodePath+"/cm:Pareri/*\" AND TYPE:\""+parereType+"\"");
        String field = "{"+CRL_ATTI_MODEL+"}"+PROP_DATA_ASSEGNAZIONE_PARERE;
        sp.addSort(field, false);
    
        ResultSet pareriNodes = serviceRegistry.getSearchService().query(sp);
           
        if(pareriNodes.getNodeRefs().size()>0){
        	lastOrgano = pareriNodes.getNodeRef(0); 
        }
		
		return lastOrgano; 
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
    
}
