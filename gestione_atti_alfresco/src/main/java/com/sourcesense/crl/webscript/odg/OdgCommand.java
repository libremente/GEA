package com.sourcesense.crl.webscript.odg;

import java.io.IOException;

import org.alfresco.service.cmr.repository.NodeRef;

public interface OdgCommand {
	byte[] generate(byte[] templateByteArray, NodeRef templateNodeRef, NodeRef attoNodeRef, String gruppo)  throws IOException;
}
