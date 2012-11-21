package com.sourcesense.crl.webscript.report;

import java.io.IOException;

import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.search.SearchService;

public abstract class ReportBaseCommand implements ReportCommand {
	protected ContentService contentService;
	protected SearchService searchService;
	protected NodeService nodeService;

	public ReportBaseCommand(ContentService contentService,
			SearchService searchService, NodeService nodeService) {
		super();
		this.contentService = contentService;
		this.searchService = searchService;
		this.nodeService = nodeService;
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


	@Override
	public abstract byte[] generate(byte[] templateByteArray,String json) throws IOException;
	

}
