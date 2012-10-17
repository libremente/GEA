package com.sourcesense.crl.webscript.template;

import java.io.IOException;

import org.alfresco.service.cmr.repository.NodeRef;

public interface LetteraCommand {
	byte[] generate(byte[] templateByteArray, NodeRef templateNodeRef, NodeRef attoNodeRef)  throws IOException;
}
