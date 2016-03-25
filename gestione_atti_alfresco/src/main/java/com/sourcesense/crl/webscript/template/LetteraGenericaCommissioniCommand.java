package com.sourcesense.crl.webscript.template;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class LetteraGenericaCommissioniCommand extends LetteraBaseCommand{

	private static Log logger = LogFactory.getLog(LetteraGenericaCommissioniCommand.class);
	
	public byte[] generate(byte[] templateByteArray, NodeRef templateNodeRef, NodeRef attoNodeRef, String gruppo) throws IOException{
		    	 
		
		byte[] documentFilledByteArray = null;
		
		// map object containing terms for search in document
    	HashMap<String, String> searchTerms = new HashMap<String, String>();

		// Set properties from template
    	String firmatario = (String) nodeService.getProperty(templateNodeRef, QName.createQName(attoUtil.CRL_TEMPLATE_MODEL, PROP_FIRMATARIO));	
		searchTerms.put("<firmatario>", firmatario);
		
		QName templateType  =  nodeService.getType(templateNodeRef);
		String nomeTemplate = templateType.getLocalName();
    	
    	// Set properties from atto
		String numeroAtto = ((Integer) nodeService.getProperty(attoNodeRef, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_NUM_ATTO))) + 
    			((String) nodeService.getProperty(attoNodeRef, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_ESTENSIONE_ATTO)));
       	String oggettoAtto = (String) nodeService.getProperty(attoNodeRef, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_OGGETTO_ATTO));
    	String numeroRepertorio = (String) nodeService.getProperty(attoNodeRef, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_NUMERO_REPERTORIO));
    	String numeroDgr = (String) nodeService.getProperty(attoNodeRef, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_NUMERO_DGR));
    	Date dataDgr = (Date) nodeService.getProperty(attoNodeRef, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_DATA_DGR));
    	
    	if(dataDgr != null) {
    		SimpleDateFormat dataDgrFormatter = new SimpleDateFormat("dd/MM/yyyy");
    		String dataDgrString = dataDgrFormatter.format(dataDgr);
    		searchTerms.put("<dataDGR>", dataDgrString);
    	}
    	//aggiunto tipo atto nel modello
      	String tipoAttoSigla= getTipoAttoSigla(attoNodeRef);
      	
        searchTerms.put("<tipoAttoSigla>", tipoAttoSigla);
    	searchTerms.put("<numeroAtto>", numeroAtto);
		searchTerms.put("<oggettoAtto>", oggettoAtto);
		searchTerms.put("<numeroRepertorio>", numeroRepertorio);
		searchTerms.put("<numeroDGR>", numeroDgr);
		

	 	List<String> commissioniReferenti = (List<String>) nodeService.getProperty(attoNodeRef, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_COMMISSIONI_REFERENTI));
    	List<String> commissioniCoreferenti = (List<String>) nodeService.getProperty(attoNodeRef, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_COMMISSIONE_COREFERENTE));
    	String commissioneRedigente = (String) nodeService.getProperty(attoNodeRef, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_COMMISSIONE_REDIGENTE));
    	String commissioneDeliberante = (String) nodeService.getProperty(attoNodeRef, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_COMMISSIONE_DELIBERANTE));
    	
    	List<String> listaCommissioniPrincipali = new ArrayList<String>();
    	
    	// Check for commissione referente
    	if(commissioniReferenti!=null && commissioniReferenti.size()>0) {
    		
    		listaCommissioniPrincipali.add(commissioniReferenti.get(0));	
    		
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
    	
    	if(commissioniCoreferenti != null && commissioniCoreferenti.size() > 0){
        	Iterator<String> i = commissioniCoreferenti.iterator();
        	while(i.hasNext()){
        		listaCommissioniPrincipali.add(i.next());
        	}
        }
    	
    	// Create commissioni principali address list 
    	String listaCommissioniPrincipaliDestString = "";
    	
    	for(int i=0; i<listaCommissioniPrincipali.size(); i++) {
    		
    		if(i!=0){
    			listaCommissioniPrincipaliDestString += "\r\r";
    		}
    		listaCommissioniPrincipaliDestString += "Al Signor Presidente\rdella "+getCommissioneNumber(listaCommissioniPrincipali.get(i)) +" Commissione";
    	}
    					
    	searchTerms.put("<listaCommissioniPrincipaliDest>", listaCommissioniPrincipaliDestString);
    	
    	if(listaCommissioniPrincipali.size() > 0){
    		searchTerms.put("<commissioneReferente>", listaCommissioniPrincipali.get(0));
    	}
    	
    	
    	// Target Commission Data
    	
    	NodeRef commissioneCorrenteNodeRef = attoUtil.getCommissioneCorrente(attoNodeRef, gruppo);
    	
    
    	if(commissioneCorrenteNodeRef!=null){
    		// <relatoreCommissione>
        	
        	String quorumVotazione = (String) nodeService.getProperty(commissioneCorrenteNodeRef, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_QUORUM_VOTAZIONE_COMMISSIONE));
        	
        	if(quorumVotazione!=null){
        		if(quorumVotazione.equalsIgnoreCase("maggioranza")){
            		quorumVotazione = "a maggioranza";
            	}else if(quorumVotazione.equalsIgnoreCase("unanimità")){
            		quorumVotazione = "all'unanimità";
            	}
        	}
        	
        	
        	
        	String esitoVotazione = (String) nodeService.getProperty(commissioneCorrenteNodeRef, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_ESITO_VOTAZIONE_COMMISSIONE));
        	
        	if(esitoVotazione!=null){
        		esitoVotazione = esitoVotazione.toLowerCase();
        	}
        	
        	
        	Date dataVotazione = (Date) nodeService.getProperty(commissioneCorrenteNodeRef, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_DATA_VOTAZIONE_COMMISSIONE));
        	
        	if(dataVotazione != null) {
        		SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy", Locale.ITALY);
        		String dataVotazioneString = formatter.format(dataVotazione);
            	searchTerms.put("<dataVotazione>", dataVotazioneString);
        	}
        	
        	searchTerms.put("<quorumVotazione>", quorumVotazione);
        	searchTerms.put("<esitoVotazione>", esitoVotazione);
        	
        	// get Relatore con property crlatti:dataUscitaRelatore non valorizzata
        	
        	NodeRef relatoreNodeRef = attoUtil.getRelatoreCorrente(commissioneCorrenteNodeRef);
        	
        	if(relatoreNodeRef!=null){
        		String relatore = (String) nodeService.getProperty(relatoreNodeRef, ContentModel.PROP_NAME);
        		searchTerms.put("<relatoreCommissione>", relatore);
        	}
    		
    	}
    	
    			
    			
		// Generate byte array of filled document content
		documentFilledByteArray = TemplateFiller.searchAndReplace(templateByteArray, searchTerms);
		
		logger.info("Generazione della lettera completata - template: "+nomeTemplate);
		
		return documentFilledByteArray;
	}
	
	private String getCommissioneNumber(String nomeCommissione){
		
		return nomeCommissione.split(" ")[0];
	}
	
	
}