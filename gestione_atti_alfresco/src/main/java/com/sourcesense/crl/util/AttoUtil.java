package com.sourcesense.crl.util;

import java.util.ArrayList;
import java.util.List;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.AssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.DynamicNamespacePrefixResolver;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class AttoUtil {
	
	private static Log logger = LogFactory.getLog(AttoUtil.class);
	
	private SearchService searchService;
	private NodeService nodeService;
	
	// crl namespaces
	public static final String CRL_TEMPLATE_MODEL = "http://www.regione.lombardia.it/content/model/template/1.0";
	public static final String CRL_ATTI_MODEL = "http://www.regione.lombardia.it/content/model/atti/1.0";
	
	// crlAttiModel type
	public static final String COMMISSIONE_TYPE = "commissione";
	public static final String RELATORE_TYPE = "relatore";
	public static final String PARERE_TYPE = "parere";
	public static final String FIRMATARIO_TYPE = "firmatario";
	public static final String TYPE_LEGISLATURA = "legislaturaAnagrafica";
	public static final String ABBINAMENTO_TYPE = "abbinamento";
	
	// crlAttiModel properties 
	public static final String PROP_ANNO = "anno";
	public static final String PROP_MESE = "mese";
	public static final String PROP_LEGISLATURA = "legislatura";
	public static final String PROP_NUM_ATTO = "numeroAtto";
	public static final String PROP_ESTENSIONE_ATTO = "estensioneAtto";
	public static final String PROP_OGGETTO_ATTO = "oggetto";
	public static final String PROP_NUMERO_LCR_ATTO = "numeroLcr";
	public static final String PROP_DATA_SEDUTA_AULA_ATTO = "dataSedutaAula";
	public static final String PROP_DESCRIZIONE_INIZIATIVA = "descrizioneIniziativa";
	public static final String PROP_TIPO_INIZIATIVA = "tipoIniziativa";
	public static final String PROP_DATA_SCADENZA = "dataScadenza";
	public static final String PROP_NUMERO_DGR = "numeroDgr";
	public static final String PROP_DATA_DGR = "dataDgr";
	public static final String PROP_NUMERO_DCR = "numeroDcr";
	public static final String PROP_COMMISSIONI_REFERENTI = "commReferente";
	public static final String PROP_COMMISSIONI_CONSULTIVE = "commConsultiva";
	public static final String PROP_COMMISSIONE_COREFERENTE = "commCoreferente";
	public static final String PROP_COMMISSIONE_REDIGENTE = "commRedigente";
	public static final String PROP_COMMISSIONE_DELIBERANTE = "commDeliberante";
	public static final String PROP_ORGANISMI_STATUTARI = "organismiStatutari";
	public static final String PROP_FIRMATARI = "firmatari";
	public static final String PROP_NUMERO_REPERTORIO = "numeroRepertorio";
	public static final String PROP_DATA_VOTAZIONE_COMMISSIONE = "dataVotazioneCommissione";
	public static final String PROP_DATA_ASSEGNAZIONE_COMMISSIONE = "dataAssegnazioneCommissione";
	public static final String PROP_DATA_ASSEGNAZIONE_PARERE = "dataAssegnazioneParere";
	public static final String PROP_DATA_FIRMA_FIRMATARIO = "dataFirma";
	public static final String PROP_QUORUM_VOTAZIONE_COMMISSIONE = "tipoVotazioneCommissione";
	public static final String PROP_ESITO_VOTAZIONE_COMMISSIONE = "esitoVotazioneCommissione";
	public static final String PROP_RUOLO_COMMISSIONE = "ruoloCommissione";
	public static final String PROP_DATA_SEDUTA = "dataSedutaSedutaODG";
	public static final String PROP_ORARIO_INIZIO_SEDUTA = "dalleOreSedutaODG";
	public static final String PROP_ORARIO_FINE_SEDUTA = "alleOreSedutaODG";
	public static final String PROP_RELAZIONE_SCRITTA_AULA = "relazioneScrittaAula";
	public static final String PROP_COMMISSIONE_CONSULTAZIONE_ATTO = "commissioneConsultazione";
	public static final String PROP_DATA_CONSULTAZIONE_ATTO = "dataConsultazione";
	public static final String PROP_DATA_CHIUSURA_ATTO = "dataChiusura";
	

	public static final String PROP_TIPO_ATTO_INDIRIZZO = "tipoAttoIndirizzo";
	public static final String PROP_NUMERO_ATTO_INDIRIZZO = "numeroAttoIndirizzo";
	public static final String PROP_OGGETTO_ATTO_INDIRIZZO = "oggettoAttoIndirizzo";
	
	
	// crl associations
	public static final String ASSOC_ATTO_TRATTATO_SEDUTA = "attoTrattatoSedutaODG";
	public static final String ASSOC_ATTO_INDIRIZZO_TRATTATO_SEDUTA = "attoIndirizzoTrattatoSedutaODG";
	public static final String ASSOC_ATTO_ASSOCIATO_ABBINAMENTO = "attoAssociatoAbbinamento";
	
	
	// Constant
	
	public static final String RUOLO_COMM_REFERENTE = "Referente";
	public static final String RUOLO_COMM_COREFERENTE = "Co-Referente";
	public static final String RUOLO_COMM_CONSULTIVA = "Consultiva";
	public static final String RUOLO_COMM_REDIGENTE = "Redigente";
	public static final String RUOLO_COMM_DELIBERANTE = "Deliberante";
	
	public static final String INIZIATIVA_CONSILIARE = "01_ATTO DI INIZIATIVA CONSILIARE";
	public static final String INIZIATIVA_POPOLARE = "03_ATTO DI INIZIATIVA POPOLARE";
	public static final String INIZIATIVA_PRESIDENTE_GIUNTA = "06_ATTO DI INIZIATIVA PRESIDENTE GIUNTA";

	public static final QName PROP_DATA_VOTAZIONE = QName.createQName(CRL_ATTI_MODEL, PROP_DATA_VOTAZIONE_COMMISSIONE);
	

	public NodeRef getLegislaturaCorrente(){
		
		NodeRef legislatura = null;
    	
        String legislaturaType = "{"+CRL_ATTI_MODEL+"}"+TYPE_LEGISLATURA;
        
        StoreRef storeRef = new StoreRef("workspace://SpacesStore");
        
		// Get legislature
    	ResultSet legislatureNodes = searchService.query(storeRef,
  				SearchService.LANGUAGE_LUCENE, "PATH:\"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti/cm:Anagrafica/cm:Legislature/*\" AND TYPE:\""+legislaturaType+"\" AND @crlatti\\:correnteLegislatura:\"true\"");
		
    	if(legislatureNodes.length() > 1){
			logger.error("Piu' di una legislatura attiva presente. Non e' stato possibile determinare la legislatura attiva.");
		}else if(legislatureNodes.length() == 0){
			logger.error("Nessuna legislatura attiva presente.. Non e' stato possibile determinare la legislatura attiva.");
		}else{
			legislatura = legislatureNodes.getNodeRef(0);
		}
    	
    	return legislatura;
	}
	
	
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

	

	public NodeRef getRelatoreCorrente(NodeRef commissioneNodeRef){
		
		NodeRef relatore = null;
		
		DynamicNamespacePrefixResolver namespacePrefixResolver = new DynamicNamespacePrefixResolver(null);
        namespacePrefixResolver.registerNamespace(NamespaceService.SYSTEM_MODEL_PREFIX, NamespaceService.SYSTEM_MODEL_1_0_URI);
        namespacePrefixResolver.registerNamespace(NamespaceService.CONTENT_MODEL_PREFIX, NamespaceService.CONTENT_MODEL_1_0_URI);
        namespacePrefixResolver.registerNamespace(NamespaceService.APP_MODEL_PREFIX, NamespaceService.APP_MODEL_1_0_URI);
    	
        String luceneCommissioneNodePath = nodeService.getPath(commissioneNodeRef).toPrefixString(namespacePrefixResolver);

        String relatoreType = "{"+CRL_ATTI_MODEL+"}"+RELATORE_TYPE;
        
		// Get relatore
    	ResultSet relatoreNodes = searchService.query(commissioneNodeRef.getStoreRef(),
  				SearchService.LANGUAGE_LUCENE, "PATH:\""+luceneCommissioneNodePath+"/cm:Relatori/*\" AND TYPE:\""+relatoreType+"\"");
    	
    	if(relatoreNodes.length()>0){
    		relatore = relatoreNodes.getNodeRef(0);
    	}
    	
		return relatore; 
	}
	
	
	public NodeRef getAula(NodeRef attoNodeRef){
		
		boolean isRelazioneScritta;
		
		DynamicNamespacePrefixResolver namespacePrefixResolver = new DynamicNamespacePrefixResolver(null);
        namespacePrefixResolver.registerNamespace(NamespaceService.SYSTEM_MODEL_PREFIX, NamespaceService.SYSTEM_MODEL_1_0_URI);
        namespacePrefixResolver.registerNamespace(NamespaceService.CONTENT_MODEL_PREFIX, NamespaceService.CONTENT_MODEL_1_0_URI);
        namespacePrefixResolver.registerNamespace(NamespaceService.APP_MODEL_PREFIX, NamespaceService.APP_MODEL_1_0_URI);
    	
    	// Get current Passaggio 
    	NodeRef passaggioNodeRef = getLastPassaggio(attoNodeRef);
    	String lucenePassaggioNodePath = nodeService.getPath(passaggioNodeRef).toPrefixString(namespacePrefixResolver);
  
		
		// Get commissione referente
    	ResultSet aulaNodes = searchService.query(attoNodeRef.getStoreRef(),
  				SearchService.LANGUAGE_LUCENE, "PATH:\""+lucenePassaggioNodePath+"/cm:Aula\"");
    	
    	NodeRef aula = null;
    	
    	if(aulaNodes.length()>0){
    		aula = aulaNodes.getNodeRef(0);
    	}
    	
		return aula; 
	}
	
	
	public NodeRef getCommissioneCorrente(NodeRef attoNodeRef, String commissioneTarget){
		
		NodeRef commissione = null;
		
		DynamicNamespacePrefixResolver namespacePrefixResolver = new DynamicNamespacePrefixResolver(null);
        namespacePrefixResolver.registerNamespace(NamespaceService.SYSTEM_MODEL_PREFIX, NamespaceService.SYSTEM_MODEL_1_0_URI);
        namespacePrefixResolver.registerNamespace(NamespaceService.CONTENT_MODEL_PREFIX, NamespaceService.CONTENT_MODEL_1_0_URI);
        namespacePrefixResolver.registerNamespace(NamespaceService.APP_MODEL_PREFIX, NamespaceService.APP_MODEL_1_0_URI);
    	
        // Get current Passaggio 
    	NodeRef passaggioNodeRef = getLastPassaggio(attoNodeRef);
    	String lucenePassaggioNodePath = nodeService.getPath(passaggioNodeRef).toPrefixString(namespacePrefixResolver);
        
		// Get commissione
    	ResultSet commissioneTargetNodes = searchService.query(attoNodeRef.getStoreRef(),
  				SearchService.LANGUAGE_LUCENE, "PATH:\""+lucenePassaggioNodePath+"/cm:Commissioni/*\" AND @cm\\:name:\""+commissioneTarget+"\"");
    	
    	if(commissioneTargetNodes.length()>0){
    		commissione = commissioneTargetNodes.getNodeRef(0);
    	}
    	
		return commissione; 
	}

	
	// Search Atti Abbinati
	public List<NodeRef> getAttiAbbinati(NodeRef attoNodeRef){
		
		List<NodeRef> attiAbbinatiList = new ArrayList<NodeRef>();
		
    	DynamicNamespacePrefixResolver namespacePrefixResolver = new DynamicNamespacePrefixResolver(null);
        namespacePrefixResolver.registerNamespace(NamespaceService.SYSTEM_MODEL_PREFIX, NamespaceService.SYSTEM_MODEL_1_0_URI);
        namespacePrefixResolver.registerNamespace(NamespaceService.CONTENT_MODEL_PREFIX, NamespaceService.CONTENT_MODEL_1_0_URI);
        namespacePrefixResolver.registerNamespace(NamespaceService.APP_MODEL_PREFIX, NamespaceService.APP_MODEL_1_0_URI);
    	
    	// Get current Passaggio 
    	NodeRef passaggioNodeRef = getLastPassaggio(attoNodeRef);
    	String lucenePassaggioNodePath = nodeService.getPath(passaggioNodeRef).toPrefixString(namespacePrefixResolver);
  
    	String abbinamentoType = "{"+CRL_ATTI_MODEL+"}"+ABBINAMENTO_TYPE;
    	
    	ResultSet abbinamentiNodes = searchService.query(attoNodeRef.getStoreRef(),
  				SearchService.LANGUAGE_LUCENE, "PATH:\""+lucenePassaggioNodePath+"/cm:Abbinamenti/*\" AND TYPE:\""+abbinamentoType+"\" AND ISNULL:\"crlatti:dataDisabbinamento\"");
    	
    	//System.out.println((String) nodeService.getProperty(attoNodeRef, ContentModel.PROP_NAME));
		
    	
    	for(int i=0; i<abbinamentiNodes.length(); i++) {
    		
    		NodeRef abbinamento = abbinamentiNodes.getNodeRef(i);
    		
    		//System.out.println((String) nodeService.getProperty(abbinamento, ContentModel.PROP_NAME));
    		
    		List<AssociationRef> attiAbbinatiAssociati = nodeService.getTargetAssocs(abbinamento,
    				QName.createQName(CRL_ATTI_MODEL, ASSOC_ATTO_ASSOCIATO_ABBINAMENTO));
    		
    		if(attiAbbinatiAssociati.size()>0){
    			attiAbbinatiList.add(attiAbbinatiAssociati.get(0).getTargetRef());
    		}
    		
    		
    	}
    	
    	
    	
		return attiAbbinatiList;
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
