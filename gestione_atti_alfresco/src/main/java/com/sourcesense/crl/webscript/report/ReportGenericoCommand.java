package com.sourcesense.crl.webscript.report;

import java.io.IOException;

public class ReportGenericoCommand extends ReportBaseCommand {

	@Override
	public byte[] generate(byte[] templateByteArray, String json)
			throws IOException {
		return templateByteArray;

	}

}
