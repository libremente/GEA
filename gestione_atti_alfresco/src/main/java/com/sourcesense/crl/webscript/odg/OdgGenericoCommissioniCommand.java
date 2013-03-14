package com.sourcesense.crl.webscript.odg;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.DynamicNamespacePrefixResolver;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;



public class OdgGenericoCommissioniCommand extends OdgBaseCommand{

	private static Log logger = LogFactory.getLog(OdgGenericoCommissioniCommand.class);
	
	
	// Genera il documento docx contenente l'ordine del giorno
	public byte[] generate(byte[] templateByteArray, NodeRef templateNodeRef, NodeRef sedutaNodeRef, String gruppo) throws IOException{
		
		byte[] documentFilledByteArray = null;
		
		// map object containing terms for search in document
    	HashMap<String, String> searchTerms = new HashMap<String, String>();
		
    	searchTerms.put("commissioneCorrente", gruppo);
    	
   
    	// compila dati generali
    	documentFilledByteArray = searchAndReplaceDocx(templateByteArray, searchTerms);
    	
    	// compila la tabella introduttiva del documento (dati della seduta)
    	documentFilledByteArray = fillIntroTableDocx(documentFilledByteArray, sedutaNodeRef);
    	
    	// compila i dati riferiti alla seduta precedente 
    	documentFilledByteArray = fillSedutaPrecedenteTableDocx(documentFilledByteArray, sedutaNodeRef, gruppo);
    	
    	// get delle consultazioni/audizioni generali
    	List<NodeRef> consultazioniGenerali = getConsultazioniGenerali(sedutaNodeRef);
    	
    	// get degli atti trattati
    	List<NodeRef> attiTrattati = getAttiTrattati(sedutaNodeRef);
    	
    	
    	// get delle consultazioni legate all'atto (inserisco gli atti trattati già ricercati per evitare una nuova ricerca)
    	List<NodeRef> consultazioniAtto = getConsultazioniAtti(sedutaNodeRef, attiTrattati, gruppo);
    	
    	// generazione delle righe della tabella necessarie a riportare tutte le consultazioni
    	documentFilledByteArray = createConsultazioniRowsCommissioniDocx(documentFilledByteArray, consultazioniGenerali, consultazioniAtto);
    	
    	documentFilledByteArray = fillConsultazioniRowsCommissioniDocx(documentFilledByteArray, consultazioniGenerali, consultazioniAtto);
    	
    	
    	// get degli atti di indirizzo trattati
    	List<NodeRef> attiIndirizzoTrattati = getAttiIndirizzoTrattati(sedutaNodeRef);
		
    	// generazione delle righe della tabella necessarie a riportare tutti gli atti trattati
    	documentFilledByteArray = createAttiTrattatiRowsCommissioniDocx(documentFilledByteArray, attiTrattati, attiIndirizzoTrattati);
    	
    	// compilazione delle righe della tabella degli atti trattati
    	documentFilledByteArray = fillAttiTrattatiRowsCommissioniDocx(documentFilledByteArray, attiTrattati, attiIndirizzoTrattati, gruppo);
		
		
		
		logger.info("Generazione del documento odg completato");
		
		return documentFilledByteArray;
	}
	
	
	private byte[] fillSedutaPrecedenteTableDocx (byte[] documentByteArray, NodeRef seduta, String gruppo) throws IOException {
		
		XWPFDocument document = new XWPFDocument(new ByteArrayInputStream(documentByteArray));
	
		List<XWPFTable> tables = document.getTables();
		
		XWPFTable table = tables.get(1);
		
		// riga della tabella contenente i riferimenti alla seduta precedente
		XWPFTableRow row = table.getRow(4);
		
		
		
		HashMap<String, String> searchTerms = new HashMap<String, String>();
		
		VerbaleObject verbale = getVerbaleSedutaPrecedente(gruppo, seduta);
		
		if(verbale!=null){
			
			Date dataVerbalePrec = verbale.getDataSeduta();
			
			if(dataVerbalePrec != null){
		    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
		    	String dataScadenzaString = formatter.format(dataVerbalePrec);
		    	searchTerms.put("dataVerbalePrec", dataScadenzaString);
			}
			
			String numeroVerbalePrec = verbale.getNumeroSeduta();
			
			searchTerms.put("numeroVerbalePrec", numeroVerbalePrec);
		}
		
		
		searchAndReplaceParagraph(row.getCell(1), searchTerms);
		
		ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		document.write(ostream);
		return ostream.toByteArray();
		
	}
	
	
	private byte[] fillAttiTrattatiRowsCommissioniDocx(byte[] documentByteArray, List<NodeRef> attiTrattati, List<NodeRef> attiIndirizzoTrattati, String commissioneCorrente) throws IOException {
		

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
			String tipoAttoDescrizione = "";
			
			if(typeDef.getName().getLocalName().length() > 4){
				tipoAttoDescrizione= typeDef.getName().getLocalName().substring(4).toUpperCase();
			}
					
			String oggettoAtto = (String) nodeService.getProperty(attoTrattato, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_OGGETTO_ATTO));
			
			
			
			String tipoIniziativaAttoTrattato = (String) nodeService.getProperty(attoTrattato, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_TIPO_INIZIATIVA));

			
			searchTerms.put("titoloAtto", tipoAttoDescrizione+" N. "+nomeAttoTrattato);
			searchTerms.put("oggettoAtto", oggettoAtto);
			
			if(tipoIniziativaAttoTrattato!=null && tipoIniziativaAttoTrattato.length()>20){
				searchTerms.put("tipoIniziativa", tipoIniziativaAttoTrattato.substring(22).toLowerCase());
			}else{
				searchTerms.put("tipoIniziativa", "");
			}
			
			
			NodeRef commissione = attoUtil.getCommissioneCorrente(attoTrattato, commissioneCorrente);
			
			
			if(commissione!=null){
				
				
				// Data di scadenza in caso di atto di tipo PAR
				
				Date dataScadenza = (Date) nodeService.getProperty(commissione, QName.createQName(attoUtil.CRL_ATTI_MODEL, "dataScadenzaCommissione"));
				
				if(tipoAttoDescrizione.equals("PAR") && dataScadenza != null){
			    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
			    	String dataScadenzaString = formatter.format(dataScadenza);
			        searchTerms.put("dataScadenzaPAR", dataScadenzaString.toUpperCase());
				}else{
					 searchTerms.put("dataScadenzaPAR", "");
					 searchTerms.put("Data Scadenza:", "");
				}
				
				

				NodeRef relatore = attoUtil.getRelatoreCorrente(commissione);
				
				if(relatore!=null){
					
					String nomeRelatore =  (String) nodeService.getProperty(relatore, ContentModel.PROP_NAME);
					
					searchTerms.put("relatoreAttivo", nomeRelatore);
				}else{
					searchTerms.put("relatoreAttivo", "Nomina relatore");
				}
				
				
				String ruoloCommissione = (String) nodeService.getProperty(commissione, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_RUOLO_COMMISSIONE));
				searchTerms.put("ruoloCommissione", ruoloCommissione);
				
				Date dataAssegnazioneCommissione = (Date) nodeService.getProperty(commissione, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_DATA_ASSEGNAZIONE_COMMISSIONE));
				
				if(dataAssegnazioneCommissione != null) {
		    		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
		    		String dataAssegnazioneCommissioneString = formatter.format(dataAssegnazioneCommissione);
		        	searchTerms.put("dataAssegnazioneCommissione", dataAssegnazioneCommissioneString.toUpperCase());
		    	}
	
				
			}	
			

			searchAndReplaceParagraph(row.getCell(1), searchTerms);
			searchAndReplaceParagraph(row.getCell(2), searchTerms);
			
			
			// Abbinamenti
			
			List<NodeRef> attiAbbinati = attoUtil.getAttiAbbinati(attoTrattato);
			List<AttiAbbinatiLineObject> abbinamentiStringList = new ArrayList<AttiAbbinatiLineObject>();
			
			
			
			if(attiAbbinati.size()>0){
				abbinamentiStringList.add(new AttiAbbinatiLineObject("", false, false, 10));
				abbinamentiStringList.add(new AttiAbbinatiLineObject("", false, false, 10));
				
				abbinamentiStringList.add(new AttiAbbinatiLineObject("Abbinato a: ", false, false, 10));
			}
			
			
			for(int j=0; j<attiAbbinati.size(); j++){
				
				abbinamentiStringList.add(new AttiAbbinatiLineObject("", false, false, 10));
				
				NodeRef attoAbbinato = attiAbbinati.get(j);
				
				String nomeAttoAbbinato = (String) nodeService.getProperty(attoAbbinato, ContentModel.PROP_NAME);
				
				TypeDefinition typeDefAttoAbbinato = dictionaryService.getType(nodeService.getType(attoTrattato));
				String tipoAttoDescrizioneAttoAbbinato = typeDefAttoAbbinato.getTitle().toUpperCase();
						
				String oggettoAttoAbbinato = (String) nodeService.getProperty(attoAbbinato, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_OGGETTO_ATTO));
				String tipoIniziativaAttoAbbinato = (String) nodeService.getProperty(attoAbbinato, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_TIPO_INIZIATIVA));
				
				abbinamentiStringList.add(new AttiAbbinatiLineObject(tipoAttoDescrizioneAttoAbbinato+" N."+nomeAttoAbbinato, true, false, 10));
				abbinamentiStringList.add(new AttiAbbinatiLineObject(oggettoAttoAbbinato, false, false, 10));
				
				if(tipoIniziativaAttoAbbinato!=null && tipoIniziativaAttoAbbinato.length()>20){
					abbinamentiStringList.add(new AttiAbbinatiLineObject("Atto di iniziativa "+tipoIniziativaAttoAbbinato.substring(22).toLowerCase(), false, true, 10));
				}
				
				NodeRef commissioneAttoAbbinato = attoUtil.getCommissioneCorrente(attoAbbinato, commissioneCorrente);
				
				if(commissioneAttoAbbinato!=null){
					
					Date dataAssegnazioneCommissioneAttoAbbinato = (Date) nodeService.getProperty(commissioneAttoAbbinato, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_DATA_ASSEGNAZIONE_COMMISSIONE));
					
					if(dataAssegnazioneCommissioneAttoAbbinato != null) {
			    		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
			    		String dataAssegnazioneCommissioneAttoAbbinatoStirng = formatter.format(dataAssegnazioneCommissioneAttoAbbinato);
			    		abbinamentiStringList.add(new AttiAbbinatiLineObject("Assegnazione: "+dataAssegnazioneCommissioneAttoAbbinatoStirng.toUpperCase(), false, true, 10));
			    	}
				}
			
				
			}
												
			
			
			int numeroParTableCell = row.getCell(1).getParagraphs().size();
			
			
			XWPFParagraph para = row.getCell(1).getParagraphs().get(numeroParTableCell-1);
			for (int k=0; k<abbinamentiStringList.size(); k++) {
				
				AttiAbbinatiLineObject ab = abbinamentiStringList.get(k);
				
				XWPFRun run = para.createRun();
				run.setItalic(ab.isItalic());
				run.setBold(ab.isBold());
				run.setFontSize(ab.getSize());
				run.setText(ab.getText().trim());
				run.addBreak();
			}
			
		}
		
		// rimuovo la riga template per gli atti interni
		int rowsAttiTrattatiNumber = attiTrattati.size() + 7;
		table.removeRow(rowsAttiTrattatiNumber);
		
		
		for(int i=0; i<attiIndirizzoTrattati.size(); i++){
			
			XWPFTableRow row = table.getRow(7+attiTrattati.size()+i);
		
			NodeRef attoTrattato = attiIndirizzoTrattati.get(i);
			
			HashMap<String, String> searchTerms = new HashMap<String, String>();
			 
			String numeroAttoTrattato = (String) nodeService.getProperty(attoTrattato, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_NUMERO_ATTO_INDIRIZZO));
			String tipoAttoTrattato = (String) nodeService.getProperty(attoTrattato, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_TIPO_ATTO_INDIRIZZO));
			String oggettoAttoTrattato = (String) nodeService.getProperty(attoTrattato, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_OGGETTO_ATTO_INDIRIZZO));
		
			
			searchTerms.put("titoloAtto", tipoAttoTrattato+" N. "+numeroAttoTrattato);
			searchTerms.put("oggettoAtto", oggettoAttoTrattato);
			
			
			String firmatariAttoTrattato = "";
			
			
			DynamicNamespacePrefixResolver namespacePrefixResolver = new DynamicNamespacePrefixResolver(null);
	        namespacePrefixResolver.registerNamespace(NamespaceService.SYSTEM_MODEL_PREFIX, NamespaceService.SYSTEM_MODEL_1_0_URI);
	        namespacePrefixResolver.registerNamespace(NamespaceService.CONTENT_MODEL_PREFIX, NamespaceService.CONTENT_MODEL_1_0_URI);
	        namespacePrefixResolver.registerNamespace(NamespaceService.APP_MODEL_PREFIX, NamespaceService.APP_MODEL_1_0_URI);
	    	
	        String luceneFirmatariNodePath = nodeService.getPath(attoTrattato).toPrefixString(namespacePrefixResolver);

	        String firmatarioType = "{"+attoUtil.CRL_ATTI_MODEL+"}"+"firmatarioAttoIndirizzo";
	        
			// Get firmatari
	    	ResultSet firmatariNodes = searchService.query(attoTrattato.getStoreRef(),
	  				SearchService.LANGUAGE_LUCENE, "PATH:\""+luceneFirmatariNodePath+"/cm:Firmatari/*\" AND TYPE:\""+firmatarioType+"\"");
			
			
			for(int j=0; j<firmatariNodes.length(); j++){
				firmatariAttoTrattato += (String) nodeService.getProperty(firmatariNodes.getNodeRef(j), ContentModel.PROP_NAME);
				if(j<firmatariNodes.length()-1){
					firmatariAttoTrattato += ", ";
				}
			}
			
			
			
			searchTerms.put("firmatariAttoIndirizzo", firmatariAttoTrattato);
			
			
			if(tipoAttoTrattato.equals("RIS")){
				
				searchTerms.put("Assegnazione: XXXX", "");
				
				HashMap<String, String> searchTermsRispAssessore = new HashMap<String, String>();
				searchTermsRispAssessore.put("Risposta dell’Assessore XXXXX", "");
				
		
				searchAndReplaceParagraph(row.getCell(2), searchTermsRispAssessore);
			}
		

			searchAndReplaceParagraph(row.getCell(1), searchTerms);
			
			
			
			
		}
		
		// rimuovo la riga template per gli atti di indirizzo
		int rowsAttiIndirizzoTrattatiNumber = attiTrattati.size() + attiIndirizzoTrattati.size() + 7;
		table.removeRow(rowsAttiIndirizzoTrattatiNumber);
		
		ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		document.write(ostream);
		return ostream.toByteArray();
	
	}
	
	
	private byte[] createAttiTrattatiRowsCommissioniDocx(byte[] documentByteArray ,List<NodeRef> attiTrattati, List<NodeRef> attiIndirizzoTrattati) throws IOException {
		  
		
		XWPFDocument document = new XWPFDocument(new ByteArrayInputStream(documentByteArray));

		List<XWPFTable> tables = document.getTables();
						
		XWPFTable table = tables.get(1);
			 	 
		XWPFTableRow templateAttoTrattatoRow= table.getRow(7);
		XWPFTableRow templateAttoIndirizzoTrattatoRow= table.getRow(8);
		
		for(int i=0; i<attiTrattati.size(); i++){
			table.addRow(templateAttoTrattatoRow, 8);			
		}
		
		for(int i=0; i<attiIndirizzoTrattati.size(); i++){
			table.addRow(templateAttoIndirizzoTrattatoRow, 8 + attiTrattati.size());
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
	
	
	private byte[] createConsultazioniRowsCommissioniDocx(byte[] documentByteArray ,List<NodeRef> consultazioniGenerali, List<NodeRef> consultazioniAtto) throws IOException {
		  
		
		XWPFDocument document = new XWPFDocument(new ByteArrayInputStream(documentByteArray));

		List<XWPFTable> tables = document.getTables();
						
		XWPFTable table = tables.get(0);
			 	 
		XWPFTableRow templateConsultazioneGeneraleRow= table.getRow(1);
		XWPFTableRow templateConsultazioneAttoRow= table.getRow(2);
		
		for(int i=0; i<consultazioniGenerali.size(); i++){
			table.addRow(templateConsultazioneGeneraleRow, 2);			
		}
		
		
		for(int i=0; i<consultazioniAtto.size(); i++){
			table.addRow(templateConsultazioneAttoRow, 2 + consultazioniGenerali.size());
		}
		 	
			
		ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		document.write(ostream);
		return ostream.toByteArray();
	

	 }
	
	
	private byte[] fillConsultazioniRowsCommissioniDocx(byte[] documentByteArray, List<NodeRef> consultazioniGenerali, List<NodeRef> consultazioniAtto) throws IOException {
		
		DictionaryService dictionaryService = serviceRegistry.getDictionaryService();	
		
		XWPFDocument document = new XWPFDocument(new ByteArrayInputStream(documentByteArray));

		List<XWPFTable> tables = document.getTables();
						
		XWPFTable table = tables.get(0);
	
		// Modifica delle righe della tabella relative alle consultazioni generali
		
		for(int i=0; i<consultazioniGenerali.size(); i++){
		
			HashMap<String, String> searchTerms = new HashMap<String, String>();
			HashMap<String, String> searchTerms2 = new HashMap<String, String>();
			
			XWPFTableRow row = table.getRow(1+i);
		
			NodeRef consultazioneGenerale = consultazioniGenerali.get(i);
			
			// determino il noderef della seduta a partire dalla consultazione
			NodeRef sedutaNodeRef = nodeService.getParentAssocs(nodeService.getParentAssocs(consultazioneGenerale).get(0).getParentRef()).get(0).getParentRef();
			
			Date dataSeduta = (Date) nodeService.getProperty(sedutaNodeRef, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_DATA_SEDUTA));
			
	    	if(dataSeduta != null) {
	    		SimpleDateFormat formatterGiorno = new SimpleDateFormat("EEEE", Locale.ITALY);
	    		String dataSedutaGiornoString = formatterGiorno.format(dataSeduta).toUpperCase();
	    		
	    		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
	    		String dataSedutaString = formatter.format(dataSeduta);
	    		
	        	searchTerms.put("dataSedutaAG", dataSedutaGiornoString + "\r" + dataSedutaString);
	    	}
			
	    	searchAndReplaceParagraph(row.getCell(0), searchTerms);
	    	
	    	
	    	String soggettiConsultatiAG = (String) nodeService.getProperty(consultazioneGenerale, ContentModel.PROP_NAME);
	    	searchTerms2.put("soggettiConsultatiAG", soggettiConsultatiAG);
	    	
	    	searchAndReplaceParagraph(row.getCell(1), searchTerms2);
	    	
	    	
		}
		
		// rimuovo la riga template per le consultazioni generali
		int rowsConsultazioniGeneraliNumber = consultazioniGenerali.size() + 1;
		table.removeRow(rowsConsultazioniGeneraliNumber);
		
		
		// Modifica delle righe della tabella relative alle consultazioni degli atti
		
		for(int i=0; i<consultazioniAtto.size(); i++){
			
			HashMap<String, String> searchTerms = new HashMap<String, String>();
			HashMap<String, String> searchTerms2 = new HashMap<String, String>();
			
			XWPFTableRow row = table.getRow(consultazioniGenerali.size()+i+1);
			
			NodeRef consultazioneAtto = consultazioniAtto.get(i);
			
			Date dataConsultazione = (Date) nodeService.getProperty(consultazioneAtto, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_DATA_CONSULTAZIONE_ATTO));
			
	    	if(dataConsultazione != null) {
	    		SimpleDateFormat formatterGiorno = new SimpleDateFormat("EEEE", Locale.ITALY);
	    		String dataConsultazioneGiornoString = formatterGiorno.format(dataConsultazione).toUpperCase();
	    		
	    		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
	    		String dataConsultazioneString = formatter.format(dataConsultazione);
	    		
	        	searchTerms.put("dataConsultazioneAtto", dataConsultazioneGiornoString + "\r" + dataConsultazioneString);
	    	}
			
	    	searchAndReplaceParagraph(row.getCell(0), searchTerms);
			
			
			// determino il noderef dell'atto a partire dalla consultazione
			NodeRef attoNodeRef = nodeService.getParentAssocs(nodeService.getParentAssocs(consultazioneAtto).get(0).getParentRef()).get(0).getParentRef();
			
			String nomeAtto = (String) nodeService.getProperty(attoNodeRef, ContentModel.PROP_NAME);
			
			TypeDefinition typeDef = dictionaryService.getType(nodeService.getType(attoNodeRef));
			String tipoAttoDescrizione = "";
			
			if(typeDef.getName().getLocalName().length() > 4){
				tipoAttoDescrizione= typeDef.getName().getLocalName().substring(4).toUpperCase();
			}
					
			String oggettoAtto = (String) nodeService.getProperty(attoNodeRef, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_OGGETTO_ATTO));
			
			String soggettiConsultatiAtto = (String) nodeService.getProperty(consultazioneAtto, ContentModel.PROP_NAME);
			
			searchTerms2.put("numeroAttoConsultazione", nomeAtto);
			searchTerms2.put("tipoAttoConsultazione", tipoAttoDescrizione);
			searchTerms2.put("oggettoAttoConsultazione", oggettoAtto);
			searchTerms2.put("soggettiConsultatiAtto", soggettiConsultatiAtto);
			
			searchAndReplaceParagraph(row.getCell(1), searchTerms2);
			
		}
		
		
		
		// rimuovo la riga template per le consultazioni generali
		int rowsConsultazioniAttoNumber = consultazioniAtto.size() + consultazioniGenerali.size() + 1;
		table.removeRow(rowsConsultazioniAttoNumber);
		
		
		
		
		ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		document.write(ostream);
		return ostream.toByteArray();
		
	}
	
	
}


class AttiAbbinatiLineObject{
	
	private String text;
	private boolean bold;
	private boolean italic;
	private int size;
	
	public AttiAbbinatiLineObject(String text, boolean bold, boolean italic, int size){
		this.text = text;
		this.bold = bold;
		this.italic = italic;
		this.size = size;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isBold() {
		return bold;
	}
	public void setBold(boolean bold) {
		this.bold = bold;
	}
	public boolean isItalic() {
		return italic;
	}
	public void setItalic(boolean italic) {
		this.italic = italic;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	
}
