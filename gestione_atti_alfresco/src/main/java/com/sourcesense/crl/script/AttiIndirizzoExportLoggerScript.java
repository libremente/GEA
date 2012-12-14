package com.sourcesense.crl.script;

import org.alfresco.repo.jscript.BaseScopableProcessorExtension;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class AttiIndirizzoExportLoggerScript extends BaseScopableProcessorExtension {

	
	private static Log logger = LogFactory.getLog(AttiIndirizzoExportLoggerScript.class);

	
	public void info(String logString) {
        
		logger.info(logString);
				 
    }
	
	public void debug(String logString) {
	        
		logger.debug(logString);			
	        
	}
	
	public void error(String logString) {
        
		logger.error(logString);			
	        
	}
	
	public void fatal(String logString) {
        
		logger.fatal(logString);			
	        
	}
	
	public void trace(String logString) {
        
		logger.trace(logString);			
	        
	}
	
	public void warn(String logString) {
        
		logger.warn(logString);			
	        
	}
	
	
	
}
