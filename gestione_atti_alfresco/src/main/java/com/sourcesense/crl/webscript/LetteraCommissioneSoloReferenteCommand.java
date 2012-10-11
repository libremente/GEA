package com.sourcesense.crl.webscript;

import java.io.IOException;
import java.util.HashMap;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class LetteraCommissioneSoloReferenteCommand extends LetteraBaseCommand{

	private static Log logger = LogFactory.getLog(LetteraCommissioneSoloReferenteCommand.class);
	
	public byte[] generate(byte[] templateByteArray, NodeRef templateNodeRef) throws IOException{
		
		
		byte[] documentFilledByteArray = null;

    	QName PROP_QNAME_NOME_PRES_CONS_REG = QName.createQName(CRL_TEMPLATE_MODEL, PROP_NOME_PRES_CONS_REG);
    	String nomePresidente = (String) nodeService.getProperty(templateNodeRef, PROP_QNAME_NOME_PRES_CONS_REG);
		
    	
    	
    	HashMap<String, String> searchTerms = new HashMap<String, String>();
		searchTerms.put("<Pluto>", "Pippo");
		
		
		// Generate byte array of filled document content
		documentFilledByteArray = TemplateFiller.searchAndReplace(templateByteArray, searchTerms);
		
		return documentFilledByteArray;
	}
	

}
