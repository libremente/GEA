package com.sourcesense.crl.webscript.odg;

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

import com.sourcesense.crl.webscript.template.TemplateFiller;


public class OdgGenericoAulaCommand extends OdgBaseCommand{

	private static Log logger = LogFactory.getLog(OdgGenericoAulaCommand.class);
	
	public byte[] generate(byte[] templateByteArray, NodeRef templateNodeRef, NodeRef sedutaNodeRef, String gruppo) throws IOException{
		
		byte[] documentFilledByteArray = null;
		
		// map object containing terms for search in document
    	HashMap<String, String> searchTerms = new HashMap<String, String>();
    	DictionaryService dictionaryService = serviceRegistry.getDictionaryService();	
    	
    	NodeRef legislatura = getLegislaturaCorrente();
    	
    	if(legislatura!=null){
    		String legislaturaString = (String) nodeService.getProperty(legislatura, ContentModel.PROP_NAME);
    		searchTerms.put("<NumeroLegislatura>", legislaturaString);
    	}
		
		Date dataSeduta = (Date) nodeService.getProperty(sedutaNodeRef, QName.createQName(CRL_ATTI_MODEL, PROP_DATA_SEDUTA));
		
    	if(dataSeduta != null) {
    		SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yy", Locale.ITALY);
    		String dataSedutaString = formatter.format(dataSeduta);
        	searchTerms.put("<dataSeduta>", dataSedutaString.toUpperCase());
    	}
		
		String orarioInzioSeduta = (String) nodeService.getProperty(sedutaNodeRef, QName.createQName(CRL_ATTI_MODEL, PROP_ORARIO_INIZIO_SEDUTA));
		
		if(orarioInzioSeduta!=null && !orarioInzioSeduta.equals("")){
			searchTerms.put("<orarioInizio>", orarioInzioSeduta.substring(11, 16));
		}
		
		String orarioFineSeduta = (String) nodeService.getProperty(sedutaNodeRef, QName.createQName(CRL_ATTI_MODEL, PROP_ORARIO_FINE_SEDUTA));

		if(orarioFineSeduta!=null && !orarioFineSeduta.equals("")){	
			searchTerms.put("<orarioFine>", orarioFineSeduta.substring(11, 16));
		}
		
		
		List<NodeRef> attiTrattati = getAttiTrattati(sedutaNodeRef);
		

		String attiTrattatiString = "";
		
		for(int i=0; i<attiTrattati.size(); i++){
			
			NodeRef attoTrattato = attiTrattati.get(i);
			
			String nomeAttoTrattato = (String) nodeService.getProperty(attoTrattato, ContentModel.PROP_NAME);
				
			TypeDefinition typeDef = dictionaryService.getType(nodeService.getType(attoTrattato));
			String tipoAttoDescrizione = typeDef.getTitle().toUpperCase();
					
			String oggettoAtto = (String) nodeService.getProperty(attoTrattato, QName.createQName(CRL_ATTI_MODEL, PROP_OGGETTO_ATTO));
			
			attiTrattatiString += tipoAttoDescrizione+" N."+nomeAttoTrattato + "\r";
			attiTrattatiString += oggettoAtto;
			
			attiTrattatiString += "\r\r\r";
			
		}
		
		searchTerms.put("<atto>",attiTrattatiString);
			
		// Generate byte array of filled document content
		documentFilledByteArray = TemplateFiller.searchAndReplace(templateByteArray, searchTerms);
		
		
		
		
		//documentFilledByteArray = TemplateFiller.createAttiTrattatiRows(documentFilledByteArrayTemp, attiTrattati);
		
		
		logger.info("Generazione del documento odg completato");
		
		return documentFilledByteArray;
	}
	
	
	
	
}
