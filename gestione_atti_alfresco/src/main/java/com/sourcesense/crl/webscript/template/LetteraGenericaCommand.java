package com.sourcesense.crl.webscript.template;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.DynamicNamespacePrefixResolver;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class LetteraGenericaCommand extends LetteraBaseCommand{

	private static Log logger = LogFactory.getLog(LetteraGenericaCommand.class);
	
	public byte[] generate(byte[] templateByteArray, NodeRef templateNodeRef, NodeRef attoNodeRef) throws IOException{
		
		byte[] documentFilledByteArray = null;
		
		// map object containing terms for search in document
    	HashMap<String, String> searchTerms = new HashMap<String, String>();

		// Set properties from template
    	String nomePresidenteConsiglioRegionale = (String) nodeService.getProperty(templateNodeRef, QName.createQName(CRL_TEMPLATE_MODEL, PROP_NOME_PRES_CONS_REG));	
		searchTerms.put("<nomePresidenteConsiglioRegionale>", nomePresidenteConsiglioRegionale);
    	
    	
    	// Set properties from atto
    	String numeroAtto = (String) nodeService.getProperty(attoNodeRef, QName.createQName(CRL_ATTI_MODEL, PROP_NUM_ATTO));
    	String oggettoAtto = (String) nodeService.getProperty(attoNodeRef, QName.createQName(CRL_ATTI_MODEL, PROP_OGGETTO_ATTO));
    	String iniziativa = (String) nodeService.getProperty(attoNodeRef, QName.createQName(CRL_ATTI_MODEL, PROP_INIZIATIVA));
    	String numeroRepertorio = (String) nodeService.getProperty(attoNodeRef, QName.createQName(CRL_ATTI_MODEL, PROP_NUMERO_REPERTORIO));

    	List<String> commissioneReferente = (List<String>) nodeService.getProperty(attoNodeRef, QName.createQName(CRL_ATTI_MODEL, PROP_COMMISSIONE_REFERENTE));
    	List<String> commissioniConsultive = (List<String>) nodeService.getProperty(attoNodeRef, QName.createQName(CRL_ATTI_MODEL, PROP_COMMISSIONI_CONSULTIVE));
    	List<String> organismiStatutari = (List<String>) nodeService.getProperty(attoNodeRef, QName.createQName(CRL_ATTI_MODEL, PROP_ORGANISMI_STATUTARI));
    	
    	//  Set firmatari list
    	List<String> firmatariList = (List<String>) nodeService.getProperty(attoNodeRef, QName.createQName(CRL_ATTI_MODEL, PROP_FIRMATARI));
    	String firmatari = "";
    	
    	if(firmatariList!=null) {
	    	for(int i=0; i<firmatariList.size(); i++){	
	    		firmatari += firmatariList.get(i);
	    	}
    	}
    	
    	searchTerms.put("<numeroAtto>", numeroAtto);
		searchTerms.put("<oggettoAtto>", oggettoAtto);
		searchTerms.put("<iniziativa>", iniziativa);
		searchTerms.put("<commissioneReferente>", commissioneReferente.get(0));
		searchTerms.put("<commissioneConsultiva>", commissioniConsultive.get(0));
		searchTerms.put("<organismoStatutario>", organismiStatutari.get(0));
		searchTerms.put("<firmatari>", firmatari);
		searchTerms.put("<numeroRepertorio>", numeroRepertorio);
    	
    	
    	DynamicNamespacePrefixResolver namespacePrefixResolver = new DynamicNamespacePrefixResolver(null);
        namespacePrefixResolver.registerNamespace(NamespaceService.SYSTEM_MODEL_PREFIX, NamespaceService.SYSTEM_MODEL_1_0_URI);
        namespacePrefixResolver.registerNamespace(NamespaceService.CONTENT_MODEL_PREFIX, NamespaceService.CONTENT_MODEL_1_0_URI);
        namespacePrefixResolver.registerNamespace(NamespaceService.APP_MODEL_PREFIX, NamespaceService.APP_MODEL_1_0_URI);

    	String luceneAttoNodePath = nodeService.getPath(attoNodeRef).toPrefixString(namespacePrefixResolver);
    	
    	// Get current Passaggio 
    	NodeRef passaggioNodeRef = getLastPassaggio(attoNodeRef.getStoreRef(), luceneAttoNodePath);
    	String lucenePassaggioNodePath = nodeService.getPath(passaggioNodeRef).toPrefixString(namespacePrefixResolver);
       	
    	// Get commissione referente
    	ResultSet commissioneReferenteNodes = searchService.query(attoNodeRef.getStoreRef(),
  				SearchService.LANGUAGE_LUCENE, "PATH:\""+lucenePassaggioNodePath+"/cm:Commissioni/*\" AND @crlatti\\:ruoloCommissione:\"Referente\"");
   	
    	ResultSet relatoreReferenteNodes = null;
    	String relatoreCommissioneReferente = "";
    	String dataVotazioneCommissioneReferente = "";
    	
    	if(commissioneReferenteNodes.length() > 0) {
    		
    		NodeRef commissioneReferenteNode = commissioneReferenteNodes.getNodeRef(0);
	    	dataVotazioneCommissioneReferente = (String) nodeService.getProperty(commissioneReferenteNode, QName.createQName(CRL_ATTI_MODEL, PROP_DATA_VOTAZIONE_COMMISSIONE));

	    	String luceneCommissioneNodePath = nodeService.getPath(commissioneReferenteNode).toPrefixString(namespacePrefixResolver);
    		
			// Get Relatore commissione referente (suppose that it is the first element of search results)
			String relatoreType = "{"+CRL_ATTI_MODEL+"}"+RELATORE_TYPE;
		
			relatoreReferenteNodes = searchService.query(commissioneReferenteNode.getStoreRef(), 
					SearchService.LANGUAGE_LUCENE, "PATH:\""+luceneCommissioneNodePath+"/cm:Relatori/*\" AND TYPE:\""+relatoreType+"\"");
			
			if(relatoreReferenteNodes.length() > 0) {
		  		NodeRef relatoreNode = relatoreReferenteNodes.getNodeRef(0);
				
		  		relatoreCommissioneReferente = (String) nodeService.getProperty(relatoreNode, ContentModel.PROP_NAME);
			}else{
				logger.debug("Relatore della Commissione Referente non torvato");
			}
			
			searchTerms.put("<relatoreCommissioneReferente>", relatoreCommissioneReferente);
		    searchTerms.put("<dataVotazioneCommissioneReferente>", dataVotazioneCommissioneReferente);
    	}else{
    		logger.debug("Commissione Referente non trovata");
    	}
    		
    	
		

		// Generate byte array of filled document content
		documentFilledByteArray = TemplateFiller.searchAndReplace(templateByteArray, searchTerms);
		
		logger.info("Generazione della lettera completata");
		
		return documentFilledByteArray;
	}
	
	
	// Function to search last passaggio element
	public NodeRef getLastPassaggio(StoreRef storeRef, String luceneAttoNodePath){
		
		NodeRef passaggio = null;
				
	  	ResultSet passaggiNodes = searchService.query(storeRef, 
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
	

}
