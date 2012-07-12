package com.sourcesense.crl.util;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.sourcesense.crl.business.security.AlfrescoSessionTicket;

public class URLBuilder {
	
	
	public static final String ALFRESCO_TCKT_PARAM_NAME= "alf_ticket=";
	
	@Autowired
	MessageSource messageSource;
	
	
	@Autowired
	AlfrescoSessionTicket alfrescoSessionTicket;

	
	public  String buildAlfrescoURL(String contextPropertyName,String pathPropertyName,String[] paramsValues){
		
		
		String url ="";
		
		url=messageSource.getMessage(contextPropertyName, null, Locale.ITALY)+
	        messageSource.getMessage(pathPropertyName, paramsValues, Locale.ITALY);
		
		if(url.indexOf("?") == -1){
			url+="?"+ALFRESCO_TCKT_PARAM_NAME+alfrescoSessionTicket.getTicket();	
		}else{
		    	
			url+="&"+ALFRESCO_TCKT_PARAM_NAME+alfrescoSessionTicket.getTicket();
		}
		
	    
		
		
		return url;
	}
	
     public  String buildURL(String contextPropertyName,String pathPropertyName){
		
		
		String url ="";
		
		url=messageSource.getMessage(contextPropertyName, null, Locale.ITALY)+
	        messageSource.getMessage(pathPropertyName, null, Locale.ITALY);
		
		
		return url;
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
