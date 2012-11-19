package com.sourcesense.crl.job.anagrafica;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.jscript.BaseScopableProcessorExtension;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.model.FileInfo;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.cmr.security.AuthorityService;
import org.alfresco.service.cmr.security.AuthorityType;
import org.alfresco.service.cmr.security.PersonService;
import org.alfresco.web.bean.repository.Repository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnagraficaImportScript extends BaseScopableProcessorExtension {

    private static Log logger = LogFactory.getLog(AnagraficaImportScript.class);

    private NodeService nodeService;
    private FileFolderService fileFolderService;
    private SearchService searchService;
    private AuthorityService authorityService;

    private DataExtractor dataExtractor;
    
   

    public void executeImport() {
        logger.debug("Start of method 'executeImport'");

        /* Extract data from DB */
        List<Legislature> legislatures = dataExtractor.getLegislatures();        
       
        /* Locate legislature folder node on Alfresco repository */
        ResultSet legislatureFolderNodeResultSet = searchService.query(Repository.getStoreRef(),
        		SearchService.LANGUAGE_LUCENE, "PATH:\"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti/cm:Anagrafica/cm:Legislature\"");
        
        ResultSet legislatureNodeResultSet;
        Legislature legislature;
        
        /* TODO posso fare una query che prende tutte i nodi legislature e fa un confronto. Così non faccio n chiamate con searchservice */
        for(int i=0; i<legislatures.size(); i++){
        	legislature = legislatures.get(i);
        	legislatureNodeResultSet = searchService.query(Repository.getStoreRef(),
                    SearchService.LANGUAGE_LUCENE, "PATH:\"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti/cm:Anagrafica/cm:Legislature/cm:"+legislature.getNumber()+"\"");
        
        	
        	/* if legislature node does not exist...create it */
        	if(legislatureNodeResultSet.length() == 0) {
        		
        		FileInfo legislatureFileInfo = fileFolderService.create(legislatureFolderNodeResultSet.getNodeRef(0), legislature.getNumber(), Constant.TYPE_LEGISLATURA);
        		logger.info("Creato il nodo legislatura '" + legislature.getNumber() + "'");    
        		nodeService.setProperty(legislatureFileInfo.getNodeRef(), Constant.PROP_ID_ANAGRAFICA, legislature.getId());
                logger.info("Proprieta' '"+Constant.PROP_ID_ANAGRAFICA.toString()+"' valorizzata con '"+legislature.getId()+"'");
                nodeService.setProperty(legislatureFileInfo.getNodeRef(), Constant.PROP_DATA_INIZIO_LEGISLATURA, legislature.getFrom());
                logger.info("Proprieta' '"+Constant.PROP_DATA_INIZIO_LEGISLATURA.toString()+"' valorizzata con '"+legislature.getFrom()+"'");
                nodeService.setProperty(legislatureFileInfo.getNodeRef(), Constant.PROP_DATA_FINE_LEGISLATURA, legislature.getTo());
                logger.info("Proprieta' '"+Constant.PROP_DATA_FINE_LEGISLATURA.toString()+"' valorizzata con '"+legislature.getTo()+"'");
        	}
        }
   

      
        ResultSet currentlegislatureNodeResultSet = searchService.query(Repository.getStoreRef(),
  				SearchService.LANGUAGE_LUCENE, 
  				"PATH:\"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti/cm:Anagrafica/cm:Legislature/*\" AND @crlatti\\:correnteLegislatura:\"true\"");
    
        if(currentlegislatureNodeResultSet.length() == 1) {
        	
        	int idLegislaturaCorrente = (Integer) nodeService.getProperty(currentlegislatureNodeResultSet.getNodeRef(0), Constant.PROP_ID_ANAGRAFICA);
        	String nomeLegislaturaCorrente = (String) nodeService.getProperty(currentlegislatureNodeResultSet.getNodeRef(0), ContentModel.PROP_NAME);
        	List<Councilor> councilors = dataExtractor.getCouncilors(idLegislaturaCorrente);

        	/* If no councilor exists... */
            if (councilors.size() <= 0) {
                logger.warn("Nessun consigliere estratto dal DB: termino");
                return;
            }
            
            logger.info("Estratti dal database anagrafica " + councilors.size() + " consiglieri per la legislatura "+nomeLegislaturaCorrente+" (corrente)");

            /* Locate all councilors nodes on Alfresco repository */
            ResultSet councilorNodesResultSet = searchService.query(Repository.getStoreRef(),
                                                                SearchService.LANGUAGE_LUCENE,
                                                                "PATH:\"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti/cm:Anagrafica/cm:ConsiglieriAttivi/*\"");
            logger.info("Trovati " + councilorNodesResultSet.length() + " consiglieri attivi nel repository");
            

            /* Delete all councilors on Alfresco repository */
            if (councilorNodesResultSet.length() > 0) {
                for (ChildAssociationRef councilorNodeRef : councilorNodesResultSet.getChildAssocRefs()) {
                    fileFolderService.delete(councilorNodeRef.getChildRef());
                    logger.info("Cancellato il nodo '" + councilorNodeRef.getQName().getLocalName() + "' dal repository");
                }
            }
            
            /* Locate all committes nodes on Alfresco repository */
            ResultSet committesNodesResultSet = searchService.query(Repository.getStoreRef(),
                                                                SearchService.LANGUAGE_LUCENE,
                                                                "PATH:\"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti/cm:Anagrafica/cm:Commissioni/*\"");
            logger.info("Trovate " + committesNodesResultSet.length() + " commissioni nel repository");
            

            /* Delete all committees on Alfresco repository */
            if (committesNodesResultSet.length() > 0) {
                for (int j=0; j<committesNodesResultSet.length(); j++) {
                	
                	String nomeCommissione = (String) nodeService.getProperty(committesNodesResultSet.getNodeRef(j), ContentModel.PROP_NAME);
                	
                	if(!nomeCommissione.equals("Commissione1") && !nomeCommissione.equals("Commissione2") && !nomeCommissione.equals("Commissione3")){
	                    fileFolderService.delete(committesNodesResultSet.getNodeRef(j));
	                    logger.info("Cancellato il nodo '" + nomeCommissione + "' dal repository");
                	}
                }
            }
            
            /* Locate all group nodes on Alfresco repository */
            ResultSet groupNodesResultSet = searchService.query(Repository.getStoreRef(),
                                                                SearchService.LANGUAGE_LUCENE,
                                                                "PATH:\"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti/cm:Anagrafica/cm:GruppiConsiliari/*\"");
            logger.info("Trovati " + groupNodesResultSet.length() + " gruppi consiliari nel repository");
            

            /* Delete all groups on Alfresco repository */
            if (groupNodesResultSet.length() > 0) {
                for (ChildAssociationRef groupNodeRef : groupNodesResultSet.getChildAssocRefs()) {
                    fileFolderService.delete(groupNodeRef.getChildRef());
                    logger.info("Cancellato il nodo '" + groupNodeRef.getQName().getLocalName() + "' dal repository");
                }
            }
            
            
            /* extract DB data */
            List<Group> groups = dataExtractor.getGroups();
            
            /* Locate groups folder node on Alfresco repository */
            ResultSet groupFolderNodeResultSet = searchService.query(Repository.getStoreRef(),
                                                                          SearchService.LANGUAGE_LUCENE,
                                                                          "PATH:\"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti/cm:Anagrafica/cm:GruppiConsiliari\"");
           
            /* Load all groups extracted from DB on Alfresco repository */
            
            for (Group group : groups) {
            	
            	String groupNodeName = group.getId() + " " + group.getCode().trim();
            	
            	// replace all not alpha or digit character
            	groupNodeName = groupNodeName.replaceAll("[^a-zA-Z0-9]+","");
           
            	
            	FileInfo groupFileInfo = fileFolderService.create(groupFolderNodeResultSet.getNodeRef(0), groupNodeName, Constant.TYPE_GRUPPO_CONSILIARE_ANAGRAFICA);
        		logger.info("Creato il nodo gruppo '" + group.getName() + "'");    
        		nodeService.setProperty(groupFileInfo.getNodeRef(), Constant.PROP_ID_ANAGRAFICA, group.getId());
                logger.info("Proprieta' '"+Constant.PROP_ID_ANAGRAFICA.toString()+"' valorizzata con '"+group.getId()+"'");
                nodeService.setProperty(groupFileInfo.getNodeRef(), Constant.PROP_NOME_GRUPPO_CONSILIARE_ANAGRAFICA, group.getName());
                logger.info("Proprieta' '"+Constant.PROP_NOME_GRUPPO_CONSILIARE_ANAGRAFICA.toString()+"' valorizzata con '"+group.getName()+"'");
                nodeService.setProperty(groupFileInfo.getNodeRef(), Constant.PROP_CODICE_GRUPPO_CONSILIARE_ANAGRAFICA, group.getCode());
                logger.info("Proprieta' '"+Constant.PROP_CODICE_GRUPPO_CONSILIARE_ANAGRAFICA.toString()+"' valorizzata con '"+group.getCode()+"'");
            }
            
            
            /* Locate councilors folder node on Alfresco repository */
            ResultSet councilorsFolderNodeResultSet = searchService.query(Repository.getStoreRef(),
                                                                          SearchService.LANGUAGE_LUCENE,
                                                                          "PATH:\"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti/cm:Anagrafica/cm:ConsiglieriAttivi\"");
            
            /* Locate committes folder node on Alfresco repository */
            ResultSet committesFolderNodeResultSet = searchService.query(Repository.getStoreRef(),
                                                                          SearchService.LANGUAGE_LUCENE,
                                                                          "PATH:\"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti/cm:Anagrafica/cm:Commissioni\"");
            
            /* Load all councilors extracted from DB on Alfresco repository */
           
            Map<String, NodeRef> committesMap = new HashMap<String, NodeRef>();
            
            int count = 0;
            
            for (Councilor councilor : councilors) {
            	count ++;
            	
            	createCouncilor(councilor, councilorsFolderNodeResultSet.getNodeRef(0), nomeLegislaturaCorrente);
            	
                
                /* check for committes */
                for (int k=0; k<councilor.getCommitteeNames().size(); k++){
                	
                	String committeName = councilor.getCommitteeNames().get(k);
                	
                	// replace all not alpha or digit character
                	committeName = committeName.replaceAll("[^a-zA-Z0-9]+","");
                	
                	/* if committe does not exist...create it and insert the NodeRef in map */
                	if(!committesMap.containsKey(committeName)){
                		
                		FileInfo committeFolderInfo = fileFolderService.create(committesFolderNodeResultSet.getNodeRef(0), 
                				committeName, ContentModel.TYPE_FOLDER);
                		
                		/* create alfresco authority (group) named as "LEGISLATURA_NomeCommissione" if does not exist */
                		String alfrescoGroupName = nomeLegislaturaCorrente + "_" + committeName;
                		
//                		if(authorityService.getAuthorityNodeRef(alfrescoGroupName)==null){
//                			authorityService.createAuthority(AuthorityType.GROUP, alfrescoGroupName);
//                			authorityService.addAuthority(Constant.GROUP_COMMISSIONI, "GROUP_"+alfrescoGroupName);
//                		}
                		
                		
                		
                		committesMap.put(committeName,committeFolderInfo.getNodeRef());
                		 
                		logger.info("Creato il nodo folder '"+committeFolderInfo.getName()+"' nel repository di Alfresco"); 
                	}
                	createCouncilor(councilor, committesMap.get(committeName), nomeLegislaturaCorrente); 
                	
                }        
                
            }
            
            logger.info("Inseriti " + count + " consiglieri attivi nel repository");
            
            /* Locate all councilors nodes on Alfresco repository */
            ResultSet historicalCouncilorNodesResultSet = searchService.query(Repository.getStoreRef(),
                                                                SearchService.LANGUAGE_LUCENE,
                                                                "PATH:\"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti/cm:Anagrafica/cm:ConsiglieriStorici/*\"");
            logger.info("Trovati " + historicalCouncilorNodesResultSet.length() + " consiglieri storici nel repository");
            
            /* Locate councilors folder node on Alfresco repository */
            ResultSet historicalCouncilorsFolderNodeResultSet = searchService.query(Repository.getStoreRef(),
                                                                          SearchService.LANGUAGE_LUCENE,
                                                                          "PATH:\"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti/cm:Anagrafica/cm:ConsiglieriStorici\"");
            
            int historicalCount = 0;
            
            for (Councilor councilor : councilors) {
                
            	boolean exist = false;
            	
            	for(int j=0; j<historicalCouncilorNodesResultSet.length(); j++){
            		
            		int historicalCouncilorID = (Integer) nodeService.getProperty(historicalCouncilorNodesResultSet.getNodeRef(j), Constant.PROP_ID_ANAGRAFICA);
            		if(historicalCouncilorID == councilor.getId()){
            			exist = true;
            		}
            	}
            	
            	if(exist == false) {
            		historicalCount ++;
            		
            		createCouncilor(councilor, historicalCouncilorsFolderNodeResultSet.getNodeRef(0), nomeLegislaturaCorrente);
            		
            	}
            }
            
            
            logger.info("Inseriti " + historicalCount + " consiglieri storici nel repository");
            
            
            logger.info("Fine della sincronizzazione con il sistema di gestione anagrafica");
        	
        }else if(currentlegislatureNodeResultSet.length() > 1){
        	
        	logger.warn("Piu' di una legislatura attiva presente. Non e' stato possibile eseguire la sincronizzazione con il sistema di gestione anagrafica");
        }else{
        	logger.warn("Nessuna legislatura attiva presente. Non e' stato possibile eseguire la sincronizzazione con il sistema di gestione anagrafica");
        }
        
        
        logger.debug("end of method 'executeImport'");
        
    }
    
    
    
    private NodeRef createCouncilor(Councilor councilor, NodeRef folderNodeRef, String nomeLegislaturaCorrente){
    	
    	String councilorNodeName = councilor.getId() + " " + councilor.getFirstName() + " " + councilor.getLastName();
        FileInfo councilorFileInfo = fileFolderService.create(folderNodeRef, councilorNodeName, Constant.TYPE_CONSIGLIERE_ANAGRAFICA);
        logger.info("Creato il nodo '"+councilorFileInfo.getName()+"' nel repository di Alfresco");
        nodeService.setProperty(councilorFileInfo.getNodeRef(), Constant.PROP_ID_ANAGRAFICA, councilor.getId());
        logger.debug("Proprieta' '"+Constant.PROP_ID_ANAGRAFICA.toString()+"' valorizzata con '"+councilor.getId()+"'");
        nodeService.setProperty(councilorFileInfo.getNodeRef(), Constant.PROP_NOME_CONSIGLIERE_ANAGRAFICA, councilor.getFirstName());
        logger.debug("Proprieta' '"+Constant.PROP_NOME_CONSIGLIERE_ANAGRAFICA.toString()+"' valorizzata con '"+councilor.getFirstName()+"'");
        nodeService.setProperty(councilorFileInfo.getNodeRef(), Constant.PROP_COGNOME_CONSIGLIERE_ANAGRAFICA, councilor.getLastName());
        logger.debug("Proprieta' '"+Constant.PROP_COGNOME_CONSIGLIERE_ANAGRAFICA.toString()+"' valorizzata con '"+councilor.getLastName()+"'");
        nodeService.setProperty(councilorFileInfo.getNodeRef(), Constant.PROP_LEGISLATURA_CONSIGLIERE_ANAGRAFICA, nomeLegislaturaCorrente);
        logger.debug("Proprieta' '"+Constant.PROP_LEGISLATURA_CONSIGLIERE_ANAGRAFICA.toString()+"' valorizzata con '"+nomeLegislaturaCorrente+"'");           
        nodeService.setProperty(councilorFileInfo.getNodeRef(), Constant.PROP_GRUPPO_CONSIGLIERE_ANAGRAFICA, councilor.getGroupName());
        logger.debug("Proprieta' '"+Constant.PROP_GRUPPO_CONSIGLIERE_ANAGRAFICA.toString()+"' valorizzata con '"+councilor.getGroupName()+"'");
        nodeService.setProperty(councilorFileInfo.getNodeRef(), Constant.PROP_CODICE_GRUPPO_CONSIGLIERE_ANAGRAFICA, councilor.getCodeGroupName());
        logger.debug("Proprieta' '"+Constant.PROP_CODICE_GRUPPO_CONSIGLIERE_ANAGRAFICA.toString()+"' valorizzata con '"+councilor.getCodeGroupName()+"'");
        
        return  councilorFileInfo.getNodeRef();
    }
    

    public NodeService getNodeService() {
        return nodeService;
    }

    public void setNodeService(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    public FileFolderService getFileFolderService() {
        return fileFolderService;
    }

    public void setFileFolderService(FileFolderService fileFolderService) {
        this.fileFolderService = fileFolderService;
    }

    public SearchService getSearchService() {
        return searchService;
    }

    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    public AuthorityService getAuthorityService() {
		return authorityService;
	}

	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	public DataExtractor getDataExtractor() {
        return dataExtractor;
    }

    public void setDataExtractor(DataExtractor dataExtractor) {
        this.dataExtractor = dataExtractor;
    }
}