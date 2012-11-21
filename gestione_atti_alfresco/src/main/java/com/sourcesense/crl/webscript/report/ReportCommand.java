package com.sourcesense.crl.webscript.report;

import java.io.IOException;

import org.alfresco.service.cmr.repository.NodeRef;

public interface ReportCommand {

	byte[] generate(byte[] templateByteArray,String json)  throws IOException;
}
