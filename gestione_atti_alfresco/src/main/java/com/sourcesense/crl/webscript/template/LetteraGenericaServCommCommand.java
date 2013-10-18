package com.sourcesense.crl.webscript.template;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.dictionary.DictionaryService;
import org.alfresco.service.cmr.dictionary.TypeDefinition;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sourcesense.crl.util.AttoUtil;


public class LetteraGenericaServCommCommand extends LetteraBaseCommand{

	private static Log logger = LogFactory.getLog(LetteraGenericaServCommCommand.class);
	
	public byte[] generate(byte[] templateByteArray, NodeRef templateNodeRef, NodeRef attoNodeRef, String gruppo) {
		
		byte[] documentFilledByteArray = null;
		
		// map object containing terms for search in document
    	HashMap<String, String> searchTerms = new HashMap<String, String>();

		// Set properties from template
    	String firmatario = (String) nodeService.getProperty(templateNodeRef, QName.createQName(AttoUtil.CRL_TEMPLATE_MODEL, PROP_FIRMATARIO));	
		searchTerms.put("<firmatario>", firmatario);
		
		QName templateType  =  nodeService.getType(templateNodeRef);
		String nomeTemplate = templateType.getLocalName();
    	
    	// Set properties from atto
    	String numeroAtto = ((Integer) nodeService.getProperty(attoNodeRef, QName.createQName(AttoUtil.CRL_ATTI_MODEL, AttoUtil.PROP_NUM_ATTO))) + 
    			((String) nodeService.getProperty(attoNodeRef, QName.createQName(AttoUtil.CRL_ATTI_MODEL, AttoUtil.PROP_ESTENSIONE_ATTO)));
    	String oggettoAtto = (String) nodeService.getProperty(attoNodeRef, QName.createQName(AttoUtil.CRL_ATTI_MODEL, AttoUtil.PROP_OGGETTO_ATTO));
    	String iniziativa = (String) nodeService.getProperty(attoNodeRef, QName.createQName(AttoUtil.CRL_ATTI_MODEL, AttoUtil.PROP_TIPO_INIZIATIVA));
    	String descrizioneIniziativa = (String) nodeService.getProperty(attoNodeRef, QName.createQName(AttoUtil.CRL_ATTI_MODEL, AttoUtil.PROP_DESCRIZIONE_INIZIATIVA));
    	String numeroRepertorio = (String) nodeService.getProperty(attoNodeRef, QName.createQName(AttoUtil.CRL_ATTI_MODEL, AttoUtil.PROP_NUMERO_REPERTORIO));
    	String numeroDgr = (String) nodeService.getProperty(attoNodeRef, QName.createQName(AttoUtil.CRL_ATTI_MODEL, AttoUtil.PROP_NUMERO_DGR));
    	Date dataDgr = (Date) nodeService.getProperty(attoNodeRef, QName.createQName(AttoUtil.CRL_ATTI_MODEL, AttoUtil.PROP_DATA_DGR));
    	Date dataRitiro = (Date) nodeService.getProperty(attoNodeRef, QName.createQName(AttoUtil.CRL_ATTI_MODEL, AttoUtil.PROP_DATA_CHIUSURA_ATTO));
    	
    	if(dataDgr != null) {
    		SimpleDateFormat dataDgrFormatter = new SimpleDateFormat("dd/MM/yy");
    		String dataDgrString = dataDgrFormatter.format(dataDgr);
    		searchTerms.put("<dataDGR>", dataDgrString);
    	}
    	
    	if(dataRitiro != null) {
    		SimpleDateFormat dataRitiroFormatter = new SimpleDateFormat("dd/MM/yy");
    		String dataRitiroString = dataRitiroFormatter.format(dataRitiro);
    		searchTerms.put("<dataRitiroAtto>", dataRitiroString);
    	}
    		
    	DictionaryService dictionaryService = serviceRegistry.getDictionaryService();		
		TypeDefinition typeDef = dictionaryService.getType(nodeService.getType(attoNodeRef));
		String tipoAttoDescrizione = typeDef.getTitle().toLowerCase();

		searchTerms.put("<numeroAtto>", numeroAtto);
		searchTerms.put("<oggettoAtto>", oggettoAtto);
		searchTerms.put("<numeroRepertorio>", numeroRepertorio);
		searchTerms.put("<tipoAttoDescrizione>", tipoAttoDescrizione);
		searchTerms.put("<numeroDGR>", numeroDgr);
		
		
    	//  Set firmatari list
    	List<String> firmatariList = (List<String>) nodeService.getProperty(attoNodeRef, QName.createQName(AttoUtil.CRL_ATTI_MODEL, AttoUtil.PROP_FIRMATARI));
    	String firmatari = StringUtils.EMPTY;
    	
    	if(firmatariList!=null) {
	    	for(int i=0; i<firmatariList.size(); i++){	
	    		firmatari += firmatariList.get(i);
	    		if(i<firmatariList.size()-1){
	    			firmatari += ", ";
	    		}
	    	}
    	}
    	
    	searchTerms.put("<firmatariAtto>", firmatari);    	
    	
    	NodeRef ultimoFirmatario = getLastFirmatario(attoNodeRef);
    	if(ultimoFirmatario!=null) {
    		String ultimoFirmatarioString = (String) nodeService.getProperty(ultimoFirmatario, ContentModel.PROP_NAME);
    		searchTerms.put("<ultimoFirmatarioAtto>", ultimoFirmatarioString);
    	}
    	

    	if(iniziativa!=null && iniziativa!=StringUtils.EMPTY){
	    	if(iniziativa.equals(AttoUtil.INIZIATIVA_CONSILIARE)){
	    		
	    		descrizioneIniziativa = "di iniziativa dei Consiglieri regionali: ";
	        	
	        	if(firmatariList!=null) {
	    	    	for(int i=0; i<firmatariList.size(); i++){	
	    	    		descrizioneIniziativa += firmatariList.get(i);
	    	    		if(i<firmatariList.size()-1){
	    	    			descrizioneIniziativa += ", ";
	    	    		}
	    	    	}
	        	}
	        	
	        	searchTerms.put("<iniziativa>", iniziativa.substring(8));
	        	
	        }else if(iniziativa.equals(AttoUtil.INIZIATIVA_PRESIDENTE_GIUNTA)){
	        	
	        	iniziativa = "DI INIZIATIVA: PRESIDENTE DELLA GIUNTA REGIONALE";
	        	searchTerms.put("<iniziativa>", iniziativa);
	        	
	        }else{
	        	
	        	iniziativa = iniziativa.replaceAll("INIZIATIVA", "INIZIATIVA:");
	        	searchTerms.put("<iniziativa>", iniziativa.substring(8));
	        }
	    	
	    	
	    	searchTerms.put("<iniziativaMinuscolo>", iniziativa.substring(8).toLowerCase());
	    	searchTerms.put("<descrizioneIniziativa>", descrizioneIniziativa);
    	}
    	
		
	 	List<String> commissioniReferenti = (List<String>) nodeService.getProperty(attoNodeRef, QName.createQName(AttoUtil.CRL_ATTI_MODEL, AttoUtil.PROP_COMMISSIONI_REFERENTI));
    	List<String> commissioniConsultive = (List<String>) nodeService.getProperty(attoNodeRef, QName.createQName(AttoUtil.CRL_ATTI_MODEL, AttoUtil.PROP_COMMISSIONI_CONSULTIVE));
    	String commissioneCoreferente = (String) nodeService.getProperty(attoNodeRef, QName.createQName(AttoUtil.CRL_ATTI_MODEL, AttoUtil.PROP_COMMISSIONE_COREFERENTE));
    	String commissioneRedigente = (String) nodeService.getProperty(attoNodeRef, QName.createQName(AttoUtil.CRL_ATTI_MODEL, AttoUtil.PROP_COMMISSIONE_REDIGENTE));
    	String commissioneDeliberante = (String) nodeService.getProperty(attoNodeRef, QName.createQName(AttoUtil.CRL_ATTI_MODEL, AttoUtil.PROP_COMMISSIONE_DELIBERANTE));
    	List<String> organismiStatutari = (List<String>) nodeService.getProperty(attoNodeRef, QName.createQName(AttoUtil.CRL_ATTI_MODEL, AttoUtil.PROP_ORGANISMI_STATUTARI));
  
    	
    	List<String> listaCommissioniPrincipali = new ArrayList<String>();
    	
    	// Check for commissione referente
    	if(commissioniReferenti!=null && commissioniReferenti.size()>0) {
    		
    		listaCommissioniPrincipali.add(commissioniReferenti.get(0));
    		
    		// Check for commmissione co-referente
    		if(commissioneCoreferente!=null && !commissioneCoreferente.equals(StringUtils.EMPTY)) {
    			listaCommissioniPrincipali.add(commissioneCoreferente);
    		}
    	// Commissione referente not exists	
    	}else {
    		
    		// Check for commissione redigente
    		if(commissioneRedigente!=null && !commissioneRedigente.equals(StringUtils.EMPTY)) {
    			listaCommissioniPrincipali.add(commissioneRedigente);
    			
    		// If commissione redigente not exists than Check for commissione deliberante	
    		}else {
    			if(commissioneDeliberante!=null && !commissioneDeliberante.equals(StringUtils.EMPTY)) {
    				listaCommissioniPrincipali.add(commissioneDeliberante);
    			}
    		}
    		
    	}
    	
    	// Create commissioni principali address list 
    	String listaCommissioniPrincipaliDestString = StringUtils.EMPTY;
    	
    	for(int i=0; i<listaCommissioniPrincipali.size(); i++) {
    		
    		if(i!=0){
    			listaCommissioniPrincipaliDestString += "\r\r";
    		}
    		listaCommissioniPrincipaliDestString += "Al Signor Presidente\rdella Commissione consiliare "+getCommissioneNumber(listaCommissioniPrincipali.get(i));
    	}
    					
    	searchTerms.put("<listaCommissioniPrincipaliDest>", listaCommissioniPrincipaliDestString);

    	// Create commissioni consultive address list 
    	String listaCommissioniConsultiveDestString = StringUtils.EMPTY;
    	String listaCommissioniConsultiveInvitiString = "Commissione consiliare ";
    	
    	// Check for commissione consultive
    	if(commissioniConsultive!=null && commissioniConsultive.size()>0) {
    		
    		for(int i=0; i<commissioniConsultive.size(); i++) {
    			
    			if(i!=0){
    				listaCommissioniConsultiveDestString += "\r\r";
        		}
    			
    			listaCommissioniConsultiveDestString += "Al Signor Presidente\rdella Commissione consiliare "+getCommissioneNumber(commissioniConsultive.get(i));
    			listaCommissioniConsultiveInvitiString += getCommissioneNumber(commissioniConsultive.get(i));
    			
    			if(i<commissioniConsultive.size()-1){
    				listaCommissioniConsultiveInvitiString += ", ";
    			}
        	}	
    	}
    	
    	searchTerms.put("<listaCommissioniConsultiveDest>", listaCommissioniConsultiveDestString);
    	searchTerms.put("<listaCommissioniConsultiveInviti>", listaCommissioniConsultiveInvitiString);
    	
    	// Create organismi statutari address list 
    	String listaOrganismiStatutariDestString = StringUtils.EMPTY;	
    	String listaOrganismiStatutariInvitiString = StringUtils.EMPTY;
    	
    	// Check for organismi statutari
    	if(organismiStatutari!=null && organismiStatutari.size()>0) {
    		
    		for(int i=0; i<organismiStatutari.size(); i++) {
        		
    			if(i!=0){
    				listaOrganismiStatutariDestString += "\r\r";
        		}
    			
    			listaOrganismiStatutariDestString += "Al Signor Presidente\rdel "+organismiStatutari.get(i);
    			listaOrganismiStatutariInvitiString += organismiStatutari.get(i);
    			
    			if(i<organismiStatutari.size()-1){
    				listaOrganismiStatutariInvitiString += ", ";
    			}
    	
    		}
    	}
    	
    	searchTerms.put("<listaOrganismiStatutariDest>", listaOrganismiStatutariDestString);
    	searchTerms.put("<listaOrganismiStatutariInviti>", listaOrganismiStatutariInvitiString);
    	
    	
    	List<NodeRef> listaCommissioniPrincipaliNodeRef = attoUtil.getCommissioniPrincipali(attoNodeRef);
    	
    	if(listaCommissioniPrincipaliNodeRef.size() > 0){
    		Date dataAssegnazioneCommissionePrincipale = (Date) nodeService.getProperty(listaCommissioniPrincipaliNodeRef.get(0), QName.createQName(AttoUtil.CRL_ATTI_MODEL, AttoUtil.PROP_DATA_ASSEGNAZIONE_COMMISSIONE));

    		if(dataAssegnazioneCommissionePrincipale!=null){
    			SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yy", Locale.ITALY);
            	String dataAssegnazioneCommissionePrincipaleString = formatter.format(dataAssegnazioneCommissionePrincipale);
            	
            	searchTerms.put("<dataAssegnazioneCommissioneRef>", dataAssegnazioneCommissionePrincipaleString);
    		}
        
        	
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
	
	
	private String getCommissioneNumber(String nomeCommissione){
		
		return nomeCommissione.split(" ")[0];
	}
	
}
