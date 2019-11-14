/*
 *  * #%L
 *  * Alfresco Repository
 *  * %%
 *  * Copyright (C) 2005 - 2016 Alfresco Software Limited
 *  * %%
 *  * This file is part of the Alfresco software. 
 *  * If the software was purchased under a paid Alfresco license, the terms of 
 *  * the paid license agreement will prevail.  Otherwise, the software is 
 *  * provided under the following open source license terms:
 *  * 
 *  * Alfresco is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU Lesser General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  * 
 *  * Alfresco is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU Lesser General Public License for more details.
 *  * 
 *  * You should have received a copy of the GNU Lesser General Public License
 *  * along with Alfresco. If not, see <http://www.gnu.org/licenses/>.
 *  * #L%
 */
package com.sourcesense.crl.webscript.odg;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.alfresco.model.ContentModel;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.AssociationRef;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchParameters;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.DynamicNamespacePrefixResolver;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.alfresco.service.namespace.RegexQNamePattern;
import org.alfresco.util.ISO8601DateFormat;
import org.alfresco.util.ISO9075;
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
    	ResultSet attiTrattatiODG = null;
    	try{
	    	attiTrattatiODG = searchService.query(sedutaNodeRef.getStoreRef(), 
	  				SearchService.LANGUAGE_LUCENE, "PATH:\""+luceneSedutaNodePath+"/cm:AttiTrattati/*\"");
	    	
	    	for(int i=0; i< attiTrattatiODG.length(); i++){
	    		
	    		NodeRef attoTrattatoODGNodeRef = attiTrattatiODG.getNodeRef(i);
	   
	    		
	    		List<AssociationRef> attiTrattatiAssociati = nodeService.getTargetAssocs(attoTrattatoODGNodeRef,
	    				QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.ASSOC_ATTO_TRATTATO_SEDUTA));
	    		
	    		if(attiTrattatiAssociati.size() > 0){
	    			attiTrattati.add(attiTrattatiAssociati.get(0).getTargetRef());    		}
	    		
	    	}
    	} finally {
    		if (attiTrattatiODG!=null){
    			attiTrattatiODG.close();
    		}
    	}
    	
    	return attiTrattati;
	}
	

	public List<NodeRef> getAttiIndirizzoTrattati(NodeRef sedutaNodeRef){
		
		List<NodeRef> attiTrattati = new ArrayList<NodeRef>();
				
		DynamicNamespacePrefixResolver namespacePrefixResolver = new DynamicNamespacePrefixResolver(null);
        namespacePrefixResolver.registerNamespace(NamespaceService.SYSTEM_MODEL_PREFIX, NamespaceService.SYSTEM_MODEL_1_0_URI);
        namespacePrefixResolver.registerNamespace(NamespaceService.CONTENT_MODEL_PREFIX, NamespaceService.CONTENT_MODEL_1_0_URI);
        namespacePrefixResolver.registerNamespace(NamespaceService.APP_MODEL_PREFIX, NamespaceService.APP_MODEL_1_0_URI);
        
    	String luceneSedutaNodePath = nodeService.getPath(sedutaNodeRef).toPrefixString(namespacePrefixResolver);
    	ResultSet attiIndirizzoTrattatiODG=null;
    	try{
	    	attiIndirizzoTrattatiODG = searchService.query(sedutaNodeRef.getStoreRef(), 
	  				SearchService.LANGUAGE_LUCENE, "PATH:\""+luceneSedutaNodePath+"/cm:AttiSindacato/*\"");
	    	
	    	for(int i=0; i< attiIndirizzoTrattatiODG.length(); i++){
	    		
	    		NodeRef attoIndirizzoTrattatoODGNodeRef = attiIndirizzoTrattatiODG.getNodeRef(i);
	   
	    		
	    		List<AssociationRef> attiTrattatiAssociati = nodeService.getTargetAssocs(attoIndirizzoTrattatoODGNodeRef,
	    				QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.ASSOC_ATTO_INDIRIZZO_TRATTATO_SEDUTA));
	    		
	    		if(attiTrattatiAssociati.size() > 0){
	    			attiTrattati.add(attiTrattatiAssociati.get(0).getTargetRef());    		}
	    		
	    	}
    	} finally {
    		if (attiIndirizzoTrattatiODG!=null){
    			attiIndirizzoTrattatiODG.close();
    		}
    	}
    	
    	return attiTrattati;
	}
	
	
	
	public List<NodeRef> getConsultazioniGenerali(NodeRef sedutaNodeRef){
		
		List<NodeRef> consultazioniGenerali = new ArrayList<NodeRef>();
				
		DynamicNamespacePrefixResolver namespacePrefixResolver = new DynamicNamespacePrefixResolver(null);
        namespacePrefixResolver.registerNamespace(NamespaceService.SYSTEM_MODEL_PREFIX, NamespaceService.SYSTEM_MODEL_1_0_URI);
        namespacePrefixResolver.registerNamespace(NamespaceService.CONTENT_MODEL_PREFIX, NamespaceService.CONTENT_MODEL_1_0_URI);
        namespacePrefixResolver.registerNamespace(NamespaceService.APP_MODEL_PREFIX, NamespaceService.APP_MODEL_1_0_URI);
        
    	String luceneSedutaNodePath = nodeService.getPath(sedutaNodeRef).toPrefixString(namespacePrefixResolver);
    	
    	ResultSet conusultazioniODG=null;
    	try{
	    	conusultazioniODG = searchService.query(sedutaNodeRef.getStoreRef(), 
	  				SearchService.LANGUAGE_LUCENE, "PATH:\""+luceneSedutaNodePath+"/cm:Audizioni/*\"");
	    	
	    	for(int i=0; i< conusultazioniODG.length(); i++){
	    		consultazioniGenerali.add(conusultazioniODG.getNodeRef(i));
	    	}
    	} finally {
    		if (conusultazioniODG!=null) {
    			conusultazioniODG.close();
    		}
    	}
    	
    	return consultazioniGenerali;
	}
	
	
	public List<NodeRef> getConsultazioniAtti(NodeRef seduta, List<NodeRef> attiTrattati, String gruppo){
		
		List<NodeRef> consultazioni = new ArrayList<NodeRef>();
		
		String dataSedutaConsultazione = (String)(String) nodeService.getProperty(seduta, ContentModel.PROP_NAME);
		
	
		DynamicNamespacePrefixResolver namespacePrefixResolver = new DynamicNamespacePrefixResolver(null);
        namespacePrefixResolver.registerNamespace(NamespaceService.SYSTEM_MODEL_PREFIX, NamespaceService.SYSTEM_MODEL_1_0_URI);
        namespacePrefixResolver.registerNamespace(NamespaceService.CONTENT_MODEL_PREFIX, NamespaceService.CONTENT_MODEL_1_0_URI);
        namespacePrefixResolver.registerNamespace(NamespaceService.APP_MODEL_PREFIX, NamespaceService.APP_MODEL_1_0_URI);
		
        String consultazioneType = "{"+attoUtil.CRL_ATTI_MODEL+"}consultazione";
        ResultSet consultazioniAttoTrattato=null;
    	for(int i=0; i< attiTrattati.size(); i++){
    		    			
    			String luceneAttoTrattatoNodePath = nodeService.getPath(attiTrattati.get(i)).toPrefixString(namespacePrefixResolver);
    			try {
	    			consultazioniAttoTrattato = searchService.query(attiTrattati.get(i).getStoreRef(), 
	    	  				SearchService.LANGUAGE_LUCENE, "PATH:\""+luceneAttoTrattatoNodePath+"/cm:Consultazioni/*\" AND TYPE:\""+consultazioneType+"\" " +
	    	  						"AND @crlatti\\:commissioneConsultazione:\""+gruppo+"\" " +
	    	  						"AND @crlatti\\:dataSedutaConsultazione:["+ dataSedutaConsultazione + " TO "+ dataSedutaConsultazione + " ]");
	    			
	    			
	    			for(int j=0; j<consultazioniAttoTrattato.length(); j++){	
	    				consultazioni.add(consultazioniAttoTrattato.getNodeRef(j));
	    			}
    			} finally {
    				if (consultazioniAttoTrattato!=null){
    					consultazioniAttoTrattato.close();
    				}
    			}
    		
    	}
    	
    	return consultazioni;
	}
	
	
	public VerbaleObject getVerbaleSedutaPrecedente(String gruppo, NodeRef seduta){   
		
		String path = "/app:company_home/cm:CRL/cm:"+ISO9075.encode("Gestione Atti")+"/cm:Sedute/cm:"+ISO9075.encode(gruppo);
		String dataSeduta = (String) nodeService.getProperty(seduta, ContentModel.PROP_NAME);
		
		String sedutaType = "{"+attoUtil.CRL_ATTI_MODEL+"}sedutaODG";
		
		String sortField = "@{" + attoUtil.CRL_ATTI_MODEL + "}dataSedutaSedutaODG";

		SearchParameters sp = new SearchParameters();
		sp.addStore(seduta.getStoreRef());
		sp.setLanguage(SearchService.LANGUAGE_LUCENE);
		
		String query = "PATH:\""+path+"//*\" AND TYPE:\""+sedutaType+"\" "+
					"AND @crlatti\\:dataSedutaSedutaODG:[ * TO "+ dataSeduta + " ]";
		
		sp.setQuery(query);
		sp.addSort(sortField, false);
		sp.setLimit(2);
		VerbaleObject verbale = null;
		ResultSet sedute=null;
		try{
			sedute = searchService.query(sp);
		
			
			
			if(sedute.length()>1){
				NodeRef sedutaPrec = sedute.getNodeRef(1);
				
				String numeroSedutaPrec = (String) nodeService.getProperty(sedutaPrec, QName.createQName(attoUtil.CRL_ATTI_MODEL, "numVerbaleSedutaODG"));
				Date dataSedutaPrec = (Date) nodeService.getProperty(sedutaPrec, QName.createQName(attoUtil.CRL_ATTI_MODEL, attoUtil.PROP_DATA_SEDUTA));
				
				if(numeroSedutaPrec==null){
					numeroSedutaPrec = "";
				}
				
				verbale = new VerbaleObject(numeroSedutaPrec, dataSedutaPrec);
		
			}
		} finally {
			if (sedute!=null){
				sedute.close();
			}
		}
				
		return verbale;
		
		
	}
	

	 protected byte[] searchAndReplaceDocx(byte[] documentByteArray , HashMap<String, String> replacements) throws IOException {
		  	
		 try{
		 
		 XWPFDocument document = new XWPFDocument(new ByteArrayInputStream(documentByteArray));
		 	
		 
			
         List<XWPFParagraph> paragraphs = document.getParagraphs(); 
			Set<String> keySet = replacements.keySet();
			XWPFParagraph paragraph;
		
			XWPFRun run = null;
			Iterator<String> keySetIterator = null;
			String text = null;
			String key = null;
			String value = null; 
			for(int i = 0; i < paragraphs.size(); i++) {
				paragraph = paragraphs.get(i);
				
				 List<XWPFRun> runs = paragraph.getRuns();
				
				for(int j=0; j < runs.size(); j++) {
					
					run = runs.get(j); 
					text = run.getText(0); 
					if(text!=null){
						keySetIterator = keySet.iterator();
						while(keySetIterator.hasNext()) { 
						    key = keySetIterator.next();
						    if(text.contains(key)) { 
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
			Set<String> keySet = replacements.keySet();
			XWPFParagraph paragraph;
		
			XWPFRun run = null;
			Iterator<String> keySetIterator = null;
			String text = null;
			String key = null;
			String value = null; 
			for(int i = 0; i < paragraphs.size(); i++) {
		
				paragraph = paragraphs.get(i);
				
				List<XWPFRun> runs = paragraph.getRuns();
				 
				for(int j=0; j < runs.size(); j++) {
			
				run = runs.get(j); 
				text = run.getText(0); 
				if(text!=null){
					keySetIterator = keySet.iterator();
					while(keySetIterator.hasNext()) { 
					    key = keySetIterator.next();
					    if(text.contains(key)) { 
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


class VerbaleObject{
	
	private String numeroSeduta;
	private Date dataSeduta;
	
	public VerbaleObject(String numeroSeduta, Date dataSeduta){
		
		this.numeroSeduta = numeroSeduta;
		this.dataSeduta = dataSeduta;
	}

	public String getNumeroSeduta() {
		return numeroSeduta;
	}

	public void setNumeroSeduta(String numeroSeduta) {
		this.numeroSeduta = numeroSeduta;
	}

	public Date getDataSeduta() {
		return dataSeduta;
	}

	public void setDataSeduta(Date dataSeduta) {
		this.dataSeduta = dataSeduta;
	}
	

	
}
