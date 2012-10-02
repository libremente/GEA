package org.alfresco.jobs;

import org.alfresco.repo.jscript.BaseScopableProcessorExtension;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.model.FileInfo;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.QName;
import org.alfresco.web.bean.repository.Repository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AnagraficaImportScript extends BaseScopableProcessorExtension {
	
    private static Log logger = LogFactory.getLog(AnagraficaImportScript.class);
	
    private NodeService nodeService;
    private FileFolderService fileFolderService;
    private SearchService searchService;
    
	public void executeImport() {
		logger.debug("AnagraficaScript: executing...");
		ResultSet resultAnagrafica = searchService.query(Repository.getStoreRef(), SearchService.LANGUAGE_LUCENE, "PATH:\"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti/cm:Anagrafica\"");
		
		String nome = "Nome";
		String cognome = "Cognome";
		ResultSet resultConsigliere = searchService.query(Repository.getStoreRef(), SearchService.LANGUAGE_LUCENE, "PATH:\"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti/cm:Anagrafica/cm:" + nome + " " + cognome + "\"");
		boolean exists = (resultConsigliere.length() != 0 && resultConsigliere.getNodeRef(0) != null && nodeService.exists(resultConsigliere.getNodeRef(0))) ? true : false;
		
		if(!exists){
			logger.info("AnagraficaScript: creo il nodo...");
	        FileInfo fileInfo = fileFolderService.create(resultAnagrafica.getNodeRef(0), nome + "_x0020_" + cognome, QName.createQName("http://www.regione.lombardia.it/content/model/atti/1.0", "consigliereAnagrafica"));
	        NodeRef fileNodeRefConsigliere = fileInfo.getNodeRef();
		} else {
			logger.info("AnagraficaScript: nodo già esistente...");
		}
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

}
