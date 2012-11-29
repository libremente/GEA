package com.sourcesense.crl.webscript.report;

import java.io.IOException;

import org.alfresco.service.cmr.repository.StoreRef;

public class ReportGenericoCommand extends ReportBaseCommand {

	@Override
	public byte[] generate(byte[] templateByteArray, String json, StoreRef spacesStore)
			throws IOException {
		return templateByteArray;

	}

}
