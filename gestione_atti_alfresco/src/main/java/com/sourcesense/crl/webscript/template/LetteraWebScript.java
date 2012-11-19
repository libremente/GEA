package com.sourcesense.crl.webscript.template;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.QName;
import org.alfresco.web.bean.repository.Repository;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.extensions.webscripts.AbstractWebScript;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;



public class LetteraWebScript extends AbstractWebScript {
	
	private ContentService contentService;
	private SearchService searchService;
	private NodeService nodeService;
	private Map<String, LetteraCommand> lettereCommandMap;
	
	private static Log logger = LogFactory.getLog(LetteraWebScript.class);
		
    public void execute(WebScriptRequest req, WebScriptResponse res) throws IOException {
    	
    	OutputStream responseOutputStream = null;
    	InputStream templateInputStream = null;
    	
    	try {
    
	    	// Get json properties
	    	String idAtto = (String)req.getParameter("idAtto");
    		String tipoTemplate = (String)req.getParameter("tipoTemplate");
    		String gruppo = (String)req.getParameter("gruppo");
    		
			// Search document template node	
			ResultSet templatesResults = searchService.query(Repository.getStoreRef(), 
					SearchService.LANGUAGE_LUCENE, "PATH:\"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti/cm:Templates//*\" AND TYPE:\""+tipoTemplate+"\"");
			
			NodeRef templateNodeRef = templatesResults.getNodeRef(0);
			
			// Get atto node
			NodeRef attoNodeRef = new NodeRef(idAtto);
			
			// Get byte array of template node content
	    	ContentReader reader = contentService.getReader(templateNodeRef, ContentModel.PROP_CONTENT);
	    	templateInputStream = reader.getContentInputStream();
	    	byte[] templateByteArray = IOUtils.toByteArray(templateInputStream);
    		
    		
	    	byte[] documentFilledByteArray = lettereCommandMap.get(tipoTemplate).generate(templateByteArray, templateNodeRef, attoNodeRef, gruppo);
	    	
	    	
	    	String nomeLettera = tipoTemplate.split(":")[1];
    				
    		// Set response
            res.setContentType("application/ms-word");
            GregorianCalendar gc = new GregorianCalendar();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            res.setHeader( "Content-Disposition", "attachment; filename=\""+nomeLettera+"_"+sdf.format(gc.getTime())+".doc\"" );
    		
    		responseOutputStream = res.getOutputStream();
            responseOutputStream.write(documentFilledByteArray);        
            
    	}catch(Exception e) {
    		logger.error("Exception details: "+e.getMessage());
    		throw new WebScriptException("Unable to generate document from template");
    	}finally {
    		if(templateInputStream != null) {
    			templateInputStream.close();
    			templateInputStream = null;
    		}
    		if(responseOutputStream != null) {
    			responseOutputStream.close();
        		responseOutputStream = null;
    		}
    	}
    }   
    
    
    public ContentService getContentService() {
        return contentService;
    }

    public void setContentService(ContentService contentService) {
        this.contentService = contentService;
    }
    
    public SearchService getSearchService() {
        return searchService;
    }

    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }
    
    public NodeService getNodeService() {
        return nodeService;
    }

    public void setNodeService(NodeService nodeService) {
        this.nodeService = nodeService;
    }

	public Map<String, LetteraCommand> getLettereCommandMap() {
		return lettereCommandMap;
	}

	public void setLettereCommandMap(Map<String, LetteraCommand> lettereCommandMap) {
		this.lettereCommandMap = lettereCommandMap;
	}


    
    
}
