package com.sourcesense.crl.webscript.report;

import java.io.IOException;

import org.alfresco.service.cmr.repository.StoreRef;

public interface ReportCommand {

	byte[] generate(byte[] templateByteArray,String json, StoreRef attoNodeRef)  throws IOException;
}
