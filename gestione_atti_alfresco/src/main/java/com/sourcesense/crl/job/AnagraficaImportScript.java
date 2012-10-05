package com.sourcesense.crl.job;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.jscript.BaseScopableProcessorExtension;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.model.FileInfo;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.QName;
import org.alfresco.web.bean.repository.Repository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

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

        String crlModel = "http://www.regione.lombardia.it/content/model/atti/1.0";

        /* Extract data from DB */
        Legislature legislature = dataExtractor.getCurrentLegislature();
        if (legislature != null) {
            logger.warn("Impossibile determinare la legislatura corrente: termino");
            return;
        }
        List<Councilor> councilors = dataExtractor.getCouncilors(legislature.getId());

        /* If no councilor exists... */
        if (councilors.size() <= 0) {
            logger.warn("Nessun consigliere estratto dal DB: termino");
            return;
        }

        logger.info("Estratti dal DB " + councilors.size() + " consigliere/i");

        /* Locate legislature folder node on Alfresco repository */
        ResultSet legislatureFolderNodeResultSet = searchService.query(Repository.getStoreRef(),
                                                                       SearchService.LANGUAGE_LUCENE,
                                                                       "PATH:\"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti/cm:Anagrafica/cm:Legislature\"");

        /* Determine legislature number from data extracted from DB */
        String legislatureName = "Legislatura"+legislature.getNumber();
        logger.info("La legislatura a cui i consiglieri si riferiscono e' la '" + legislatureName+"'");

        /* Locate legislature node on Alfresco repository */
        ResultSet legislatureNodeResultSet = searchService.query(Repository.getStoreRef(),
                                                                 SearchService.LANGUAGE_LUCENE,
                                                                 "PATH:\"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti/cm:Anagrafica/cm:Legislature/"+legislatureName+"\"");

        /* If legislature node does not exist... create it */
        if (legislatureNodeResultSet.length() > 0) {
            QName LEGISLATURE_TYPE_NAME = QName.createQName(crlModel, "legislaturaAnagrafica");
            QName LEGISLATURE_PROPERTY_YEARS = QName.createQName(crlModel, "anni");
            FileInfo legislatureFileInfo = fileFolderService.create(legislatureFolderNodeResultSet.getNodeRef(0), legislatureName, LEGISLATURE_TYPE_NAME);
            logger.info("Creato il nodo '" + legislatureFileInfo.getName() + "' nel repository di Alfresco");
            nodeService.setProperty(legislatureFileInfo.getNodeRef(), LEGISLATURE_PROPERTY_YEARS, legislature.getFrom());
            logger.info("Ho settato la proprieta' '"+LEGISLATURE_PROPERTY_YEARS.toString()+"' a '"+legislature.getFrom()+"'");

        }

        /* Locate all councilors nodes on Alfresco repository */
        ResultSet councilorNodesResultSet = searchService.query(Repository.getStoreRef(),
                                                            SearchService.LANGUAGE_LUCENE,
                                                            "PATH:\"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti/cm:Anagrafica/cm:Consiglieri/*\"");
        logger.info("Trovato/i " + councilorNodesResultSet.length() + " consigliere/i sul repository di Alfresco");

        /* Delete all councilors on Alfresco repository */
        if (councilorNodesResultSet.length() > 0) {
            for (ChildAssociationRef councilorNodeRef : councilorNodesResultSet.getChildAssocRefs()) {
                fileFolderService.delete(councilorNodeRef.getChildRef());
                logger.info("Cancellato il nodo '" + councilorNodeRef.getQName().getLocalName() + "' dal repository di Alfresco");
            }
        }

        /* Locate councilors folder node on Alfresco repository */
        ResultSet councilorsFolderNodeResultSet = searchService.query(Repository.getStoreRef(),
                                                                      SearchService.LANGUAGE_LUCENE,
                                                                      "PATH:\"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti/cm:Anagrafica/cm:Consiglieri\"");

        /* Load all councilors extracted from DB on Alfresco repository */
        QName COUNCILOR_TYPE_NAME = QName.createQName(crlModel, "consigliereAnagrafica");
        QName COUNCILOR_PROPERTY_FIRST_NAME = QName.createQName(crlModel, "nomeConsigliereAnagrafica");
        QName COUNCILOR_PROPERTY_LAST_NAME = QName.createQName(crlModel, "cognomeConsigliereAnagrafica");
        QName COUNCILOR_PROPERTY_LEGISLATURE = QName.createQName(crlModel, "legislaturaConsigliereAnagrafica");
        QName COUNCILOR_PROPERTY_GROUP = QName.createQName(crlModel, "gruppoConsigliereAnagrafica");
        for (Councilor councilor : councilors) {
            String councilorNodeName = councilor.getFirstName() + "_" + councilor.getLastName();
            FileInfo councilorFileInfo = fileFolderService.create(councilorsFolderNodeResultSet.getNodeRef(0), councilorNodeName, COUNCILOR_TYPE_NAME);
            logger.info("Creato il nodo '"+councilorFileInfo.getName()+"' nel repository di Alfresco");
            nodeService.setProperty(councilorFileInfo.getNodeRef(), COUNCILOR_PROPERTY_FIRST_NAME, councilor.getFirstName());
            logger.info("Ho settato la proprieta' '"+COUNCILOR_PROPERTY_FIRST_NAME.toString()+"' a '"+councilor.getFirstName()+"'");
            nodeService.setProperty(councilorFileInfo.getNodeRef(), COUNCILOR_PROPERTY_LAST_NAME, councilor.getLastName());
            logger.info("Ho settato la proprieta' '"+COUNCILOR_PROPERTY_LAST_NAME.toString()+"' a '"+councilor.getLastName()+"'");
            nodeService.setProperty(councilorFileInfo.getNodeRef(), COUNCILOR_PROPERTY_LEGISLATURE, legislatureName);
            logger.info("Ho settato la proprieta' '"+COUNCILOR_PROPERTY_LEGISLATURE.toString()+"' a '"+legislatureName+"'");
            nodeService.setProperty(councilorFileInfo.getNodeRef(), COUNCILOR_PROPERTY_GROUP, councilor.getGroupName());
            logger.info("Ho settato la proprieta' '"+COUNCILOR_PROPERTY_GROUP.toString()+"' a '"+councilor.getGroupName()+"'");
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
