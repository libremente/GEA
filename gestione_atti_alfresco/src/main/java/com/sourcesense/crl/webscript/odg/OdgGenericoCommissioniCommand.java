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



public class OdgGenericoCommissioniCommand extends OdgBaseCommand{

	private static Log logger = LogFactory.getLog(OdgGenericoAulaCommand.class);
	
	public byte[] generate(byte[] templateByteArray, NodeRef templateNodeRef, NodeRef sedutaNodeRef, String gruppo) throws IOException{
		
		byte[] documentFilledByteArray = null;
		
		// map object containing terms for search in document
    	HashMap<String, String> searchTerms = new HashMap<String, String>();
		
    	searchTerms.put("commissioneCorrente", gruppo);
    	
   
    	
    	documentFilledByteArray = searchAndReplaceDocx(templateByteArray, searchTerms);
    	
    	documentFilledByteArray = fillIntroTableDocx(documentFilledByteArray, sedutaNodeRef);
    	
    	List<NodeRef> attiTrattati = getAttiTrattati(sedutaNodeRef);
		
    	documentFilledByteArray = createAttiTrattatiRowsCommissioniDocx(documentFilledByteArray, attiTrattati);
    	
    	documentFilledByteArray = fillAttiTrattatiRowsCommissioniDocx(documentFilledByteArray, attiTrattati, gruppo);
		
		
		
		logger.info("Generazione del documento odg completato");
		
		return documentFilledByteArray;
	}
	
	
	private byte[] fillAttiTrattatiRowsCommissioniDocx(byte[] documentByteArray, List<NodeRef> attiTrattati, String commissioneCorrente) throws IOException {
		

		DictionaryService dictionaryService = serviceRegistry.getDictionaryService();	

		XWPFDocument document = new XWPFDocument(new ByteArrayInputStream(documentByteArray));

		List<XWPFTable> tables = document.getTables();
						
		XWPFTable table = tables.get(1);
		
		
		for(int i=0; i<attiTrattati.size(); i++){
		
			XWPFTableRow row = table.getRow(7+i);
		
			NodeRef attoTrattato = attiTrattati.get(i);
			
			HashMap<String, String> searchTerms = new HashMap<String, String>();
			 
			String nomeAttoTrattato = (String) nodeService.getProperty(attoTrattato, ContentModel.PROP_NAME);
				
			TypeDefinition typeDef = dictionaryService.getType(nodeService.getType(attoTrattato));
			String tipoAttoDescrizione = typeDef.getTitle().toUpperCase();
					
			String oggettoAtto = (String) nodeService.getProperty(attoTrattato, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_OGGETTO_ATTO));
			
			String tipoIniziativaAttoTrattato = (String) nodeService.getProperty(attoTrattato, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_TIPO_INIZIATIVA));

			
			searchTerms.put("titoloAtto", tipoAttoDescrizione+" N."+nomeAttoTrattato);
			searchTerms.put("oggettoAtto", oggettoAtto);
			searchTerms.put("tipoIniziativa", tipoIniziativaAttoTrattato);
			

			
			NodeRef commissione = attoUtil.getCommissioneCorrente(attoTrattato, commissioneCorrente);
			
			if(commissione!=null){

				NodeRef relatore = attoUtil.getRelatoreCorrente(commissione);
				
				if(relatore!=null){
					
					String nomeRelatore =  (String) nodeService.getProperty(relatore, ContentModel.PROP_NAME);
					
					searchTerms.put("relatoreAttivo", nomeRelatore);
				}else{
					searchTerms.put("relatoreAttivo", "");
				}
				
				
				String ruoloCommissione = (String) nodeService.getProperty(commissione, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_RUOLO_COMMISSIONE));
				searchTerms.put("ruoloCommissione", ruoloCommissione);
				
				Date dataAssegnazioneCommissione = (Date) nodeService.getProperty(commissione, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_DATA_ASSEGNAZIONE_COMMISSIONE));
				
				if(dataAssegnazioneCommissione != null) {
		    		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy", Locale.ITALY);
		    		String dataAssegnazioneCommissioneString = formatter.format(dataAssegnazioneCommissione);
		        	searchTerms.put("dataAssegnazioneCommissione", dataAssegnazioneCommissioneString.toUpperCase());
		    	}
	
				
			}	
			

			searchAndReplaceParagraph(row.getCell(1), searchTerms);
			searchAndReplaceParagraph(row.getCell(2), searchTerms);
			
		}
		
		
		int rowsNumber = table.getRows().size();
		
		
		table.removeRow(rowsNumber-1);
	
		ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		document.write(ostream);
		return ostream.toByteArray();
	
	}
	
	
	private byte[] createAttiTrattatiRowsCommissioniDocx(byte[] documentByteArray ,List<NodeRef> attiTrattati) throws IOException {
		  
		
		XWPFDocument document = new XWPFDocument(new ByteArrayInputStream(documentByteArray));

		List<XWPFTable> tables = document.getTables();
						
		XWPFTable table = tables.get(1);
			 	 
		XWPFTableRow templateRow= table.getRow(7);
		
		for(int i=0; i<attiTrattati.size(); i++){
			
			table.addRow(templateRow, 8);
			
		}
		 	
			
		ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		document.write(ostream);
		return ostream.toByteArray();
	

	 }
	
	
	private byte[] fillIntroTableDocx(byte[] documentByteArray , NodeRef sedutaNodeRef) throws IOException {
		

		XWPFDocument document = new XWPFDocument(new ByteArrayInputStream(documentByteArray));

		List<XWPFTable> tables = document.getTables();
						
		XWPFTable table1 = tables.get(0);
		
		
		HashMap<String, String> searchTerms = new HashMap<String, String>();
		
		Date dataSeduta = (Date) nodeService.getProperty(sedutaNodeRef, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_DATA_SEDUTA));
		
    	if(dataSeduta != null) {
    		SimpleDateFormat formatterGiorno = new SimpleDateFormat("EEEE", Locale.ITALY);
    		String dataSedutaGiornoString = formatterGiorno.format(dataSeduta);
    		
    		SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy", Locale.ITALY);
    		String dataSedutaString = formatter.format(dataSeduta);
    		
    		
        	searchTerms.put("dataSeduta1", dataSedutaGiornoString + "\r" + dataSedutaString);
        	searchTerms.put("dataSeduta2", dataSedutaGiornoString + " " + dataSedutaString);
    	}
		
		String orarioInzioSeduta = (String) nodeService.getProperty(sedutaNodeRef, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_ORARIO_INIZIO_SEDUTA));
		
		if(orarioInzioSeduta!=null && !orarioInzioSeduta.equals("")){
			searchTerms.put("orarioInizio", orarioInzioSeduta.substring(11, 16));
		}
		
		searchAndReplaceParagraph(table1.getRow(0).getCell(0), searchTerms);
		
		XWPFTable table2 = tables.get(1);
		
		searchAndReplaceParagraph(table2.getRow(0).getCell(1), searchTerms);
		
	
		ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		document.write(ostream);
		return ostream.toByteArray();
	
	}
	
}
