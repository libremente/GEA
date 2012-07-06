package com.sourcesense.crl.business.service.rest;

import java.util.Locale;

import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import com.sourcesense.crl.business.model.User;
import com.sourcesense.crl.business.security.AlfrescoSessionTicket;
import com.sourcesense.crl.business.security.Data;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Component(value = "userService")
@Path("/users")
public class UserService {

	@Autowired
	ResourceBundleMessageSource messageSource;

	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	Client client;

	public AlfrescoSessionTicket getAuthenticationToken(User user) {
		String responseMsg = "error";
		AlfrescoSessionTicket data=null;
		try{
		WebResource webResource = client
				.resource("http://37.59.169.203:8081/alfresco/service/api/login.json");

		ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, user);

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}
		
		
		responseMsg = response.getEntity(String.class);
        objectMapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
		data =  objectMapper.readValue(responseMsg, AlfrescoSessionTicket.class);
		
		
		}catch(Exception ex){
			
			ex.printStackTrace();
		}
		
		return data;
	}

}
