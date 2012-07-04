package com.sourcesense.crl.business.service.rest;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;


@Component(value = "legislaturaService")
@Path("/legislature")
public class LegislaturaService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	ResourceBundleMessageSource messageSource;
	
	@Autowired
	Client client;
	
	
	
	
	
	
   public Map<String, String> getAllLegislatura() {
		
	    WebResource webResource = client.resource(messageSource.getMessage("alfresco_context_url", null, Locale.ITALY));
		
		ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
				.get(ClientResponse.class);

		if (response.getStatus() != 201) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}
		// TODO Auto-generated method stub
		return null;
	}
	
   public Map<String, String>  getAnniByLegislatura(String legislatura){
    	
        WebResource webResource = client.resource(messageSource.getMessage("alfresco_context_url", null, Locale.ITALY));
		
		ClientResponse response = webResource.queryParam("legislatura", legislatura).type(MediaType.APPLICATION_JSON)
				.get(ClientResponse.class);

		if (response.getStatus() != 201) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}
        
		return null;
    	
    }

}
