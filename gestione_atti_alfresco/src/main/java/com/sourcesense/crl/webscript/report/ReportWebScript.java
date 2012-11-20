package com.sourcesense.crl.webscript.report;


import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.webscripts.AbstractWebScript;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;



public class ReportWebScript extends AbstractWebScript{

	private static Log logger = LogFactory.getLog(ReportWebScript.class);
	@Override
	public void execute(WebScriptRequest arg0, WebScriptResponse arg1)
			throws IOException {
		logger.info("TEST LOG");
		
	}

}
