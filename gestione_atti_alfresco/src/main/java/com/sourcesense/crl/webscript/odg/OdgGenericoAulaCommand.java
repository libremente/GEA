package com.sourcesense.crl.webscript.odg;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.dictionary.DictionaryService;
import org.alfresco.service.cmr.dictionary.TypeDefinition;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;



public class OdgGenericoAulaCommand extends OdgBaseCommand{

	private static Log logger = LogFactory.getLog(OdgGenericoAulaCommand.class);
	
	public byte[] generate(byte[] templateByteArray, NodeRef templateNodeRef, NodeRef sedutaNodeRef, String gruppo) throws IOException{
		
		byte[] documentFilledByteArray = null;
		
		// map object containing terms for search in document
    	HashMap<String, String> searchTerms = new HashMap<String, String>();
    	
    	NodeRef legislatura = attoUtil.getLegislaturaCorrente();
    	
    	
    	if(legislatura!=null){
    		String legislaturaString = (String) nodeService.getProperty(legislatura, ContentModel.PROP_NAME);
    		searchTerms.put("numeroLegislatura", legislaturaString+"\rLEGISLATURA");
    	}
		
		Date dataSeduta = (Date) nodeService.getProperty(sedutaNodeRef, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_DATA_SEDUTA));
		
    	if(dataSeduta != null) {
    		SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yy", Locale.ITALY);
    		String dataSedutaString = formatter.format(dataSeduta);
        	searchTerms.put("dataSeduta", dataSedutaString.toUpperCase());
    	}
		
		String orarioInzioSeduta = (String) nodeService.getProperty(sedutaNodeRef, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_ORARIO_INIZIO_SEDUTA));
		
		if(orarioInzioSeduta!=null && !orarioInzioSeduta.equals("")){
			searchTerms.put("orarioInizio", orarioInzioSeduta.substring(11, 16));
		}
		
		String orarioFineSeduta = (String) nodeService.getProperty(sedutaNodeRef, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_ORARIO_FINE_SEDUTA));

		if(orarioFineSeduta!=null && !orarioFineSeduta.equals("")){	
			searchTerms.put("orarioFine", orarioFineSeduta.substring(11, 16));
		}
		
		
		List<NodeRef> attiTrattati = getAttiTrattati(sedutaNodeRef);
			
		// riempimento placeholder
		documentFilledByteArray = searchAndReplaceDocx(templateByteArray, searchTerms);
		
		// genrazione documento con le righe della tabella vuote 
		documentFilledByteArray = createAttiTrattatiRowsAulaDocx(documentFilledByteArray, attiTrattati);
		
		// generazione documento con le righe della tabella popolate
		// non è stato possibile creare e riempire le righe in un solo passaggio per dei problemi sulla reference
		// degli oggetti riga
		documentFilledByteArray = fillAttiTrattatiRowsAulaDocx(documentFilledByteArray, attiTrattati);
		
		 
		logger.info("Generazione del documento odg completato");
		
		return documentFilledByteArray;
	}
	
	
	
	
	private byte[] createAttiTrattatiRowsAulaDocx(byte[] documentByteArray ,List<NodeRef> attiTrattati) throws IOException {
		  
  
		
		XWPFDocument document = new XWPFDocument(new ByteArrayInputStream(documentByteArray));

		List<XWPFTable> tables = document.getTables();
						
		XWPFTable table = tables.get(1);
			 	 
		XWPFTableRow templateRow= table.getRow(3);
		
		for(int i=0; i<attiTrattati.size(); i++){
			
			table.addRow(templateRow, 3);
			
		}
		 	
			
		ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		document.write(ostream);
		return ostream.toByteArray();
	

	 }
	
	
	
	private byte[] fillAttiTrattatiRowsAulaDocx(byte[] documentByteArray ,List<NodeRef> attiTrattati) throws IOException {
	

		DictionaryService dictionaryService = serviceRegistry.getDictionaryService();	

		XWPFDocument document = new XWPFDocument(new ByteArrayInputStream(documentByteArray));

		List<XWPFTable> tables = document.getTables();
						
		XWPFTable table = tables.get(1);
		
		
		for(int i=0; i<attiTrattati.size(); i++){
		
			XWPFTableRow row = table.getRow(3+i);
		
			NodeRef attoTrattato = attiTrattati.get(i);
			
			HashMap<String, String> searchTerms = new HashMap<String, String>();
			 
			String nomeAttoTrattato = (String) nodeService.getProperty(attoTrattato, ContentModel.PROP_NAME);
				
			TypeDefinition typeDef = dictionaryService.getType(nodeService.getType(attoTrattato));
			String tipoAttoDescrizione = typeDef.getTitle().toUpperCase();
					
			String oggettoAtto = (String) nodeService.getProperty(attoTrattato, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_OGGETTO_ATTO));
				
			searchTerms.put("titoloAtto", tipoAttoDescrizione+" N."+nomeAttoTrattato);
			searchTerms.put("oggettoAtto", oggettoAtto);
			
			List<NodeRef> commissioniRef = attoUtil.getCommissioniPrincipali(attoTrattato);
			
			if(commissioniRef.size()>0){
				String nomeCommissioneRel =  (String) nodeService.getProperty(commissioniRef.get(0), ContentModel.PROP_NAME);
				
				searchTerms.put("commissioneReferente", nomeCommissioneRel);
				
				NodeRef relatore = attoUtil.getRelatoreCorrente(commissioniRef.get(0));
				
				if(relatore!=null){
					
					String nomeRelatore =  "Relatore Cons."+(String) nodeService.getProperty(relatore, ContentModel.PROP_NAME);

					NodeRef aula = attoUtil.getAula(attoTrattato);
					
					String relazioneScritta= (String) nodeService.getProperty(aula, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_RELAZIONE_SCRITTA_AULA));

					if(relazioneScritta.equals("Sì")){
						nomeRelatore+= " con relazione scritta";
					}
					
					searchTerms.put("relatoreCommissione", nomeRelatore);
				}else{
					searchTerms.put("relatoreCommissione", "");
				}

				
				searchAndReplaceParagraph(row.getCell(1), searchTerms);
				
			}	
			
		}
		
		
		int rowsNumber = table.getRows().size();
		
		
		table.removeRow(rowsNumber-1);
	
		ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		document.write(ostream);
		return ostream.toByteArray();
	
	}
	
	
	
	
	
	
	 
	 

	
	
	
	
	
}
