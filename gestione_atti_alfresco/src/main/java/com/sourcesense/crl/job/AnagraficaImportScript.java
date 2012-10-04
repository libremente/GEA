package com.sourcesense.crl.job;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.jscript.BaseScopableProcessorExtension;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.QName;
import org.alfresco.web.bean.repository.Repository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;

public class AnagraficaImportScript extends BaseScopableProcessorExtension {
	
    private static Log logger = LogFactory.getLog(AnagraficaImportScript.class);
	
    private NodeService nodeService;
    private FileFolderService fileFolderService;
    private SearchService searchService;
    private DataExtractor dataExtractor;

//	public void executeImport() {
//		logger.debug("AnagraficaScript: executing...");
//		ResultSet resultAnagrafica = searchService.query(Repository.getStoreRef(), SearchService.LANGUAGE_LUCENE, "PATH:\"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti/cm:Anagrafica\"");
//
//		String firstName = "Cosimo";
//		String lastName = "Garofalo";
//        String legislatureNum = "12345";
//        String groupName = "N_X";
//        String[] boardNames = {"board1, board2"};
//
//
//		ResultSet resultConsigliere = searchService.query(Repository.getStoreRef(), SearchService.LANGUAGE_LUCENE, "PATH:\"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti/cm:Anagrafica/cm:" + nome + " " + cognome + "\"");
//		boolean exists = (resultConsigliere.length() != 0 && resultConsigliere.getNodeRef(0) != null && nodeService.exists(resultConsigliere.getNodeRef(0))) ? true : false;
//
//		if(!exists){
//			logger.info("AnagraficaScript: creo il nodo...");
//	        FileInfo fileInfo = fileFolderService.create(resultAnagrafica.getNodeRef(0), nome + "_x0020_" + cognome, QName.createQName("http://www.regione.lombardia.it/content/model/atti/1.0", "consigliereAnagrafica"));
//	        NodeRef fileNodeRefConsigliere = fileInfo.getNodeRef();
//		} else {
//			logger.info("AnagraficaScript: nodo già esistente...");
//		}
//	}

    public void executeImport() {
        logger.debug("Start of method 'executeImport'");

        String councilorsStorePath = "PATH:\"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti/cm:Anagrafica/cm:Consiglieri\"";

        /* Extract data from DB */
        Collection<Councilor> councilors = dataExtractor.getCouncilors();

        /* Delete all Alfresco nodes related to Councilors and Committee */
        ResultSet councilorsResultSet = searchService.query(Repository.getStoreRef(), SearchService.LANGUAGE_LUCENE, councilorsStorePath);
        Collection<ChildAssociationRef> councilorNodeRefs = councilorsResultSet.getChildAssocRefs();
        logger.info("Trovato/i "+councilorNodeRefs.size()+" consigliere/i");
        for (ChildAssociationRef councilorNodeRef : councilorNodeRefs) {
            fileFolderService.delete(councilorNodeRef.getChildRef());
            logger.info("Cancellato il nodo '"+councilorNodeRef.getChildRef().getId()+"' dal repository di Alfresco");
        }

        /* Load legislature extracted from DB */

        /* Load all data extract from DB */
        String crlModel = "http://www.regione.lombardia.it/content/model/atti/1.0";
        QName COUNCILOR_TYPE_NAME = QName.createQName(crlModel, "crlatti:consigliereAnagrafica");
        QName COUNCILOR_PROPERTY_FIRST_NAME = QName.createQName(crlModel, "crlatti:nomeConsigliereAnagrafica");
        QName COUNCILOR_PROPERTY_LAST_NAME = QName.createQName(crlModel, "crlatti:cognomeConsigliereAnagrafica");
        for (Councilor councilor : councilors) {
            QName COUNCILOR_NODE_NAME = QName.createQName(councilor.getFirstName() + "_x0020_" + councilor.getLastName());
            ChildAssociationRef councilorNodeRef = nodeService.createNode(councilorsResultSet.getNodeRef(0), ContentModel.ASSOC_CONTAINS, COUNCILOR_NODE_NAME, COUNCILOR_TYPE_NAME);
            logger.info("Creato il nodo '"+councilorNodeRef.getChildRef().getId()+"' nel repository di Alfresco");
            nodeService.setProperty(councilorNodeRef.getChildRef(), COUNCILOR_PROPERTY_FIRST_NAME, councilor.getFirstName());
            logger.info("Ho settato la proprieta' '"+COUNCILOR_PROPERTY_FIRST_NAME.toString()+"' a '"+councilor.getFirstName()+"'");
            nodeService.setProperty(councilorNodeRef.getChildRef(), COUNCILOR_PROPERTY_LAST_NAME, councilor.getLastName());
            logger.info("Ho settato la proprieta' '"+COUNCILOR_PROPERTY_LAST_NAME.toString()+"' a '"+councilor.getLastName()+"'");
        }

        logger.debug("End of method 'executeImport'");
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

    public DataExtractor getDataExtractor() {
        return dataExtractor;
    }

    public void setDataExtractor(DataExtractor dataExtractor) {
        this.dataExtractor = dataExtractor;
    }
}
