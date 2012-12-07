package com.sourcesense.crl.util;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.sourcesense.crl.business.security.AlfrescoSessionTicket;

public class URLBuilder {
	
	
	public static final String ALFRESCO_TCKT_PARAM_NAME= "alf_ticket=";
	public static final String ALFRESCO_DWNL_TCKT_PARAM_NAME= "ticket=";
	
	@Autowired
	MessageSource messageSource;
	
	
	@Autowired
	AlfrescoSessionTicket alfrescoSessionTicket;

	
    public  String buildAlfrescoDownloadURL(String contextPropertyName,String fileName,String[] paramsValues){
		
		String url ="";
		
		url=messageSource.getMessage(contextPropertyName, null, Locale.ITALY)+
				fileName;
		
		if(url.indexOf("?") == -1){
			url+="?"+ALFRESCO_DWNL_TCKT_PARAM_NAME+alfrescoSessionTicket.getTicket();	
		}else{
		    	
			url+="&"+ALFRESCO_DWNL_TCKT_PARAM_NAME+alfrescoSessionTicket.getTicket();
		}
		
		return url.replaceAll(" ", "%20");
	}
	
	public  String buildAlfrescoURL(String contextPropertyName,String pathPropertyName,String[] paramsValues){
		
		String url ="";
		
		url=messageSource.getMessage(contextPropertyName, null, Locale.ITALY)+
	        messageSource.getMessage(pathPropertyName, paramsValues, Locale.ITALY);
		
		url = url.trim();
		
		if(url.indexOf("?") == -1){
			url+="?"+ALFRESCO_TCKT_PARAM_NAME+alfrescoSessionTicket.getTicket();	
		}else{
		    	
			url+="&"+ALFRESCO_TCKT_PARAM_NAME+alfrescoSessionTicket.getTicket();
		}
		
		return url.replaceAll(" ", "%20");
	}
	
     public  String buildURL(String contextPropertyName,String pathPropertyName){
		
		
		String url ="";
		
		url=messageSource.getMessage(contextPropertyName, null, Locale.ITALY)+
	        messageSource.getMessage(pathPropertyName, null, Locale.ITALY);
		
		
		return url;
	}
	
     
     public  String buildSimpleURL(String contextPropertyName,String[] paramsValues){
 		
 		
 		String url ="";
 		
 		url=messageSource.getMessage(contextPropertyName, paramsValues, Locale.ITALY);
 		
 		return url.replaceAll(" ", "%20");
 	} 
	

	public MessageSource getMessageSource() {
		return messageSource;
	}


	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}


	public AlfrescoSessionTicket getAlfrescoSessionTicket() {
		return alfrescoSessionTicket;
	}


	public void setAlfrescoSessionTicket(AlfrescoSessionTicket alfrescoSessionTicket) {
		this.alfrescoSessionTicket = alfrescoSessionTicket;
	}
	
	
	
	
	
	

}
