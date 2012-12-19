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


public class OdgGenericoCommissioniCommand extends OdgBaseCommand{

	private static Log logger = LogFactory.getLog(OdgGenericoAulaCommand.class);
	
	public byte[] generate(byte[] templateByteArray, NodeRef templateNodeRef, NodeRef sedutaNodeRef, String gruppo) throws IOException{
		
		byte[] documentFilledByteArray = null;
		
		
		
		
		//documentFilledByteArray = TemplateFiller.createAttiTrattatiRows(documentFilledByteArrayTemp, attiTrattati);
		
		
		logger.info("Generazione del documento odg completato");
		
		return documentFilledByteArray;
	}
	
	
	
	
}
