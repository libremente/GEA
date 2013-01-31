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
		    	
		
		QName templateType  =  nodeService.getType(templateNodeRef);
		String nomeTemplate = templateType.getLocalName();
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy", Locale.ITALY);
				
		// map object containing terms for search in document
    	HashMap<String, String> searchTerms = new HashMap<String, String>();
    	
    	// map object containing terms for search in document
    	HashMap<String, String> searchTermsFooter = new HashMap<String, String>();
    	
		// Set properties from template
    	String firmatario = (String) nodeService.getProperty(templateNodeRef, QName.createQName(attoUtil.CRL_TEMPLATE_MODEL, PROP_FIRMATARIO));	
		searchTerms.put("<firmatario>", firmatario);
		
		String ufficio = (String) nodeService.getProperty(templateNodeRef, QName.createQName(attoUtil.CRL_TEMPLATE_MODEL, PROP_UFFICIO));	
		searchTerms.put("<ufficio>", ufficio);
		
		String direzione = (String) nodeService.getProperty(templateNodeRef, QName.createQName(attoUtil.CRL_TEMPLATE_MODEL, PROP_DIREZIONE));	
		searchTerms.put("<direzione>", direzione);
		
		String numeroTelFirmatario = (String) nodeService.getProperty(templateNodeRef, QName.createQName(attoUtil.CRL_TEMPLATE_MODEL, PROP_NUMEROTELFIRMATARIO));	
		searchTermsFooter.put("<numeroTelFirmatario>", numeroTelFirmatario);
		
		String emailFirmatario = (String) nodeService.getProperty(templateNodeRef, QName.createQName(attoUtil.CRL_TEMPLATE_MODEL, PROP_EMAILFIRMATARIO));	
		searchTermsFooter.put("<emailFirmatario>", emailFirmatario);
    	
    	// Set properties from atto
		String numeroAtto = ((Integer) nodeService.getProperty(attoNodeRef, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_NUM_ATTO))) + 
    			((String) nodeService.getProperty(attoNodeRef, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_ESTENSIONE_ATTO)));
       	String oggettoAtto = (String) nodeService.getProperty(attoNodeRef, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_OGGETTO_ATTO));
       
       	String legislatura = (String) nodeService.getProperty(attoNodeRef, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_LEGISLATURA));
       	
       	String numeroLCR = (String) nodeService.getProperty(attoNodeRef, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_NUMERO_LCR_ATTO));
       	String numeroDCR = (String) nodeService.getProperty(attoNodeRef, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_NUMERO_DCR));
       	Date dataVotazioneAula = (Date) nodeService.getProperty(attoNodeRef, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_DATA_SEDUTA_AULA_ATTO));

       	
       	if(dataVotazioneAula!=null){
       	
        	String dataVotazioneAulaString = formatter.format(dataVotazioneAula);
        	searchTerms.put("<dataLCR>", dataVotazioneAulaString.toUpperCase());
        	searchTerms.put("<dataVotazione>", dataVotazioneAulaString);
       	}
       	
     	
    	String dataOdiernaString = formatter.format(new Date());
    	searchTerms.put("<dataOdierna>", dataOdiernaString);
       	
    	 	
		searchTerms.put("<numeroAtto>", numeroAtto);
		searchTerms.put("<oggettoAtto>", oggettoAtto);
		searchTerms.put("<numeroLCR>", numeroLCR);
		searchTerms.put("<numeroDCR>", numeroDCR);
//		searchTerms.put("<legislatura>", legislatura);
		
	 	List<String> commissioniReferenti = (List<String>) nodeService.getProperty(attoNodeRef, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_COMMISSIONI_REFERENTI));
    	String commissioneCoreferente = (String) nodeService.getProperty(attoNodeRef, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_COMMISSIONE_COREFERENTE));
    	String commissioneRedigente = (String) nodeService.getProperty(attoNodeRef, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_COMMISSIONE_REDIGENTE));
    	String commissioneDeliberante = (String) nodeService.getProperty(attoNodeRef, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_COMMISSIONE_DELIBERANTE));
  
    	
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
    	
    	if(listaCommissioniPrincipali.size()>0){
    		String commissionePrincipale = listaCommissioniPrincipali.get(0);
    		searchTerms.put("<commissioneReferente>", commissionePrincipale);
    	}
    	
    	
    	
    	// Create commissioni principali address list 
    	String listaCommissioniPrincipaliDestString = "";
    	
    	for(int i=0; i<listaCommissioniPrincipali.size(); i++) {
    		
    		if(i!=0){
    			listaCommissioniPrincipaliDestString += "\r\r";
    		}
    		listaCommissioniPrincipaliDestString += "Al Signor Presidente\rdella "+getCommissioneNumber(listaCommissioniPrincipali.get(i)) + " Commissione consiliare";
    	}
    					
    	searchTerms.put("<listaCommissioniPrincipaliDest>", listaCommissioniPrincipaliDestString);

    	
    	
    	List<NodeRef> listaCommissioniPrincipaliNodeRef = attoUtil.getCommissioniPrincipali(attoNodeRef);
    	
    	if(listaCommissioniPrincipaliNodeRef.size() > 0){
    		Date dataAssegnazioneCommissionePrincipale = (Date) nodeService.getProperty(listaCommissioniPrincipaliNodeRef.get(0), QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_DATA_ASSEGNAZIONE_COMMISSIONE));

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
		
		// Fill doc footer
		documentFilledByteArray = TemplateFiller.searchAndReplaceFooter(documentFilledByteArray, searchTermsFooter);
		
		logger.info("Generazione della lettera completata - template: "+nomeTemplate);
		
		return documentFilledByteArray;
	}
	
	
	private String getCommissioneNumber(String nomeCommissione){
		
		return nomeCommissione.split(" ")[0];
	}	
	
}
