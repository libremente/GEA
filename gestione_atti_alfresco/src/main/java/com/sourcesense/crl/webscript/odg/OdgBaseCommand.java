package com.sourcesense.crl.webscript.odg;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.AssociationRef;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.DynamicNamespacePrefixResolver;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.alfresco.service.namespace.RegexQNamePattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;

import com.sourcesense.crl.util.AttoUtil;
import com.sourcesense.crl.webscript.template.LetteraGenericaAulaCommand;


public abstract class OdgBaseCommand implements OdgCommand{
	
	protected ContentService contentService;
	protected SearchService searchService;
	protected NodeService nodeService;
	protected ServiceRegistry serviceRegistry;
	protected AttoUtil attoUtil;
	
	private static Log logger = LogFactory.getLog(OdgBaseCommand.class);
	
	
	
	
	
	
	public abstract byte[] generate(byte[] templateByteArray, NodeRef templateNodeRef, NodeRef attoNodeRef, String gruppo) throws IOException;
	
	
	// Get property value 
	public String getStringProperty(NodeRef attoNodeRef, String nameSpaceURI, String localName){
		String value = (String) nodeService.getProperty(attoNodeRef, QName.createQName(nameSpaceURI, localName));
		
		if(value==null){
			value = "";
		}
	
		return value;
	}
	
	
	
	
	
	public List<NodeRef> getAttiTrattati(NodeRef sedutaNodeRef){
		
		List<NodeRef> attiTrattati = new ArrayList<NodeRef>();
				
		DynamicNamespacePrefixResolver namespacePrefixResolver = new DynamicNamespacePrefixResolver(null);
        namespacePrefixResolver.registerNamespace(NamespaceService.SYSTEM_MODEL_PREFIX, NamespaceService.SYSTEM_MODEL_1_0_URI);
        namespacePrefixResolver.registerNamespace(NamespaceService.CONTENT_MODEL_PREFIX, NamespaceService.CONTENT_MODEL_1_0_URI);
        namespacePrefixResolver.registerNamespace(NamespaceService.APP_MODEL_PREFIX, NamespaceService.APP_MODEL_1_0_URI);
        
    	String luceneSedutaNodePath = nodeService.getPath(sedutaNodeRef).toPrefixString(namespacePrefixResolver);
        
    	ResultSet attiTrattatiODG = searchService.query(sedutaNodeRef.getStoreRef(), 
  				SearchService.LANGUAGE_LUCENE, "PATH:\""+luceneSedutaNodePath+"/cm:AttiTrattati/*\"");
    	
    	for(int i=0; i< attiTrattatiODG.length(); i++){
    		
    		NodeRef attoTrattatoODGNodeRef = attiTrattatiODG.getNodeRef(i);
   
    		
    		List<AssociationRef> attiTrattatiAssociati = nodeService.getTargetAssocs(attoTrattatoODGNodeRef,
    				QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.ASSOC_ATTO_TRATTATO_SEDUTA));
    		
    		if(attiTrattatiAssociati.size() > 0){
    			attiTrattati.add(attiTrattatiAssociati.get(0).getTargetRef());    		}
    		
    	}
    	
    	return attiTrattati;
	}
	

	 protected byte[] searchAndReplaceDocx(byte[] documentByteArray , HashMap<String, String> replacements) throws IOException {
		  	
		 try{
		 
		 XWPFDocument document = new XWPFDocument(new ByteArrayInputStream(documentByteArray));
		 	
		 
			
         List<XWPFParagraph> paragraphs = document.getParagraphs();

			// Recover a Set of the keys in the HashMap
			Set<String> keySet = replacements.keySet();
			XWPFParagraph paragraph;
		
			XWPFRun run = null;
			Iterator<String> keySetIterator = null;
			String text = null;
			String key = null;
			String value = null;
		
			// Step through each Paragraph
			for(int i = 0; i < paragraphs.size(); i++) {
				paragraph = paragraphs.get(i);
				
				 List<XWPFRun> runs = paragraph.getRuns();
				
				for(int j=0; j < runs.size(); j++) {
					
					run = runs.get(j);
					
					// Get the text from the CharacterRun 
					text = run.getText(0);
					
					// KeySet Iterator
					if(text!=null){
						keySetIterator = keySet.iterator();
						while(keySetIterator.hasNext()) {
			
							// check the key in CharacterRuns text
						    key = keySetIterator.next();
						    if(text.contains(key)) {
						    	
						    	// replace term
						    	if(replacements.get(key)!=null){
						    		value = replacements.get(key);
						    	}else{
						    		value = "";
						    	}
						        
						        String newText = text.replaceAll(key, value);
						        						        
						        run.setText(newText,0);
						    }
						}
					}
				}
			}

			ByteArrayOutputStream ostream = new ByteArrayOutputStream();
			document.write(ostream);
			return ostream.toByteArray();
			
		 }catch (Exception e) {
			 e.printStackTrace();
			 return null;
		 }

	 }
	
	 
	 
	 protected void searchAndReplaceParagraph( XWPFTableCell cell, HashMap<String, String> replacements) throws IOException {
		  	
		 
		 	List<XWPFParagraph> paragraphs = cell.getParagraphs();

			// Recover a Set of the keys in the HashMap
			Set<String> keySet = replacements.keySet();
			XWPFParagraph paragraph;
		
			XWPFRun run = null;
			Iterator<String> keySetIterator = null;
			String text = null;
			String key = null;
			String value = null;
		
			// Step through each Paragraph
			for(int i = 0; i < paragraphs.size(); i++) {
		
				paragraph = paragraphs.get(i);
				
				List<XWPFRun> runs = paragraph.getRuns();
				 
				for(int j=0; j < runs.size(); j++) {
			
				run = runs.get(j);
				
				// Get the text from the CharacterRun 
				text = run.getText(0);
				
				// KeySet Iterator
				if(text!=null){
					keySetIterator = keySet.iterator();
					while(keySetIterator.hasNext()) {
		
						// check the key in CharacterRuns text
					    key = keySetIterator.next();
					    if(text.contains(key)) {
					    	
					    	// replace term
					    	if(replacements.get(key)!=null){
					    		value = replacements.get(key);
					    	}else{
					    		value = "";
					    	}
					        
					        String newText = text.replaceAll(key, value);
					        				        
					        run.setText(newText,0);
					    }
					}
				}
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

	public ServiceRegistry getServiceRegistry() {
		return serviceRegistry;
	}

	public void setServiceRegistry(ServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}

	public AttoUtil getAttoUtil() {
		return attoUtil;
	}

	public void setAttoUtil(AttoUtil attoUtil) {
		this.attoUtil = attoUtil;
	}
    
	
}
