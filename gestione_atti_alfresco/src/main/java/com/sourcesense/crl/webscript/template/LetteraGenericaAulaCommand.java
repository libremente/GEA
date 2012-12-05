package com.sourcesense.crl.webscript.template;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.dictionary.DictionaryService;
import org.alfresco.service.cmr.dictionary.TypeDefinition;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.DynamicNamespacePrefixResolver;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class LetteraGenericaAulaCommand extends LetteraBaseCommand{

	private static Log logger = LogFactory.getLog(LetteraGenericaAulaCommand.class);
	
	public byte[] generate(byte[] templateByteArray, NodeRef templateNodeRef, NodeRef attoNodeRef, String gruppo) throws IOException{
		
		byte[] documentFilledByteArray = null;
		
// Aula
    	
    	// <listaCommissioniPrincipaliDest> -> Al Signor PRESIDENTE\rdella XXX Commissione consiliare 
    	// <numeroLCR> 
    	// <dataLCR> - formato (GG MESE AAAA)
    	// commissioneReferente
    	// legislatura
    	// numeroDCR
    	// direzione -> pannello di controllo
    	// <dataOdierna> (gg mese aaaa)
		
		QName templateType  =  nodeService.getType(templateNodeRef);
		String nomeTemplate = templateType.getLocalName();
				
		// map object containing terms for search in document
    	HashMap<String, String> searchTerms = new HashMap<String, String>();

		// Set properties from template
    	String firmatario = (String) nodeService.getProperty(templateNodeRef, QName.createQName(CRL_TEMPLATE_MODEL, PROP_FIRMATARIO));	
		searchTerms.put("<firmatario>", firmatario);
		
		String ufficio = (String) nodeService.getProperty(templateNodeRef, QName.createQName(CRL_TEMPLATE_MODEL, PROP_UFFICIO));	
		searchTerms.put("<ufficio>", ufficio);
		
		String direzione = (String) nodeService.getProperty(templateNodeRef, QName.createQName(CRL_TEMPLATE_MODEL, PROP_DIREZIONE));	
		searchTerms.put("<direzione>", direzione);
		
		String numeroTelFirmatario = (String) nodeService.getProperty(templateNodeRef, QName.createQName(CRL_TEMPLATE_MODEL, PROP_NUMEROTELFIRMATARIO));	
		searchTerms.put("<numeroTelFirmatario>", numeroTelFirmatario);
		
		String emailFirmatario = (String) nodeService.getProperty(templateNodeRef, QName.createQName(CRL_TEMPLATE_MODEL, PROP_EMAILFIRMATARIO));	
		searchTerms.put("<emailFirmatario>", emailFirmatario);
    	
    	// Set properties from atto
    	String numeroAtto = ((Integer) nodeService.getProperty(attoNodeRef, QName.createQName(CRL_ATTI_MODEL, PROP_NUM_ATTO))).toString();
    	String oggettoAtto = (String) nodeService.getProperty(attoNodeRef, QName.createQName(CRL_ATTI_MODEL, PROP_OGGETTO_ATTO));
    	
		searchTerms.put("<numeroAtto>", numeroAtto);
		searchTerms.put("<oggettoAtto>", oggettoAtto);
	
    	
		
	 	List<String> commissioniReferenti = (List<String>) nodeService.getProperty(attoNodeRef, QName.createQName(CRL_ATTI_MODEL, PROP_COMMISSIONI_REFERENTI));
    	String commissioneCoreferente = (String) nodeService.getProperty(attoNodeRef, QName.createQName(CRL_ATTI_MODEL, PROP_COMMISSIONE_COREFERENTE));
    	String commissioneRedigente = (String) nodeService.getProperty(attoNodeRef, QName.createQName(CRL_ATTI_MODEL, PROP_COMMISSIONE_REDIGENTE));
    	String commissioneDeliberante = (String) nodeService.getProperty(attoNodeRef, QName.createQName(CRL_ATTI_MODEL, PROP_COMMISSIONE_DELIBERANTE));
  
    	
    	List<String> listaCommissioniPrincipali = new ArrayList<String>();
    	
    	// Check for commissione referente
    	if(commissioniReferenti!=null && commissioniReferenti.size()>0) {
    		
    		listaCommissioniPrincipali.add(commissioniReferenti.get(0));
    		
    		// Check for commmissione co-referente
    		if(commissioneCoreferente!=null && !commissioneCoreferente.equals("")) {
    			listaCommissioniPrincipali.add(commissioneCoreferente);
    		}
    	// Commissione referente not exists	
    	}else {
    		
    		// Check for commissione redigente
    		if(commissioneRedigente!=null && !commissioneRedigente.equals("")) {
    			listaCommissioniPrincipali.add(commissioneRedigente);
    			
    		// If commissione redigente not exists than Check for commissione deliberante	
    		}else {
    			if(commissioneDeliberante!=null && !commissioneDeliberante.equals("")) {
    				listaCommissioniPrincipali.add(commissioneDeliberante);
    			}
    		}
    		
    	}
    	
    	// Create commissioni principali address list 
    	String listaCommissioniPrincipaliDestString = "";
    	
    	for(int i=0; i<listaCommissioniPrincipali.size(); i++) {
    		
    		if(i!=0){
    			listaCommissioniPrincipaliDestString += "\r\r";
    		}
    		listaCommissioniPrincipaliDestString += "Al Signor Presidente\rdella Commissione consiliare "+listaCommissioniPrincipali.get(i);
    	}
    					
    	searchTerms.put("<listaCommissioniPrincipaliDest>", listaCommissioniPrincipaliDestString);

    	
    	
    	List<NodeRef> listaCommissioniPrincipaliNodeRef = getCommissioniPrincipali(attoNodeRef);
    	
    	if(listaCommissioniPrincipaliNodeRef.size() > 0){
    		Date dataAssegnazioneCommissionePrincipale = (Date) nodeService.getProperty(listaCommissioniPrincipaliNodeRef.get(0), QName.createQName(CRL_ATTI_MODEL, PROP_DATA_ASSEGNAZIONE_COMMISSIONE));

        	SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yy", Locale.ITALY);
        	String dataAssegnazioneCommissionePrincipaleString = formatter.format(dataAssegnazioneCommissionePrincipale);
        	
        	searchTerms.put("<dataAssegnazioneCommissioneRef>", dataAssegnazioneCommissionePrincipaleString);
        	
    	}

        	
    	NodeRef ultimoOrganoStatutario = getLastOrganoStatutarioAssegnato(attoNodeRef);
    	if(ultimoOrganoStatutario!=null) {
    		String ultimoOrganoStatutarioString = (String) nodeService.getProperty(ultimoOrganoStatutario, ContentModel.PROP_NAME);
    		searchTerms.put("<ultimoOrganoStatutario>", ultimoOrganoStatutarioString);
    	}
    	

		// Generate byte array of filled document content
		documentFilledByteArray = TemplateFiller.searchAndReplace(templateByteArray, searchTerms);
		
		logger.info("Generazione della lettera completata - template: "+nomeTemplate);
		
		return documentFilledByteArray;
	}
	
	
	
	
}
