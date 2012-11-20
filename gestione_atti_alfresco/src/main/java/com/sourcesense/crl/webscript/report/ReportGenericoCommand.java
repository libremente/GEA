package com.sourcesense.crl.webscript.report;

import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.search.SearchService;

public class ReportGenericoCommand extends ReportBaseCommand {

	public ReportGenericoCommand(ContentService contentService,
			SearchService searchService, NodeService nodeService) {
		super(contentService, searchService, nodeService);
		// TODO Auto-generated constructor stub
	}

}
