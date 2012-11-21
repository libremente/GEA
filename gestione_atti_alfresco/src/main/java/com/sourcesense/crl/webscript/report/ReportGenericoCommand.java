package com.sourcesense.crl.webscript.report;

import java.io.IOException;

import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.search.SearchService;

public class ReportGenericoCommand extends ReportBaseCommand {
	
	public ReportGenericoCommand(ContentService contentService,
			SearchService searchService, NodeService nodeService) {
		super(contentService, searchService, nodeService);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public byte[] generate(byte[] templateByteArray,String json) throws IOException {
		return templateByteArray;
		
	}

}
