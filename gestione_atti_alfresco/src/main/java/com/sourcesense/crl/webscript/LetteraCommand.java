package com.sourcesense.crl.webscript;

import java.io.IOException;

import org.alfresco.service.cmr.repository.NodeRef;

public interface LetteraCommand {
	byte[] generate(byte[] templateByteArray, NodeRef templateNodeRef)  throws IOException;
}
