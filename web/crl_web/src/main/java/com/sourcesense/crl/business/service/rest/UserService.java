package com.sourcesense.crl.business.service.rest;

import java.util.List;
import java.util.Locale;

import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import com.sourcesense.crl.business.model.GruppoUtente;
import com.sourcesense.crl.business.model.TipoAtto;
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
	ObjectMapper objectMapper;

	@Autowired
	Client client;

	public AlfrescoSessionTicket getAuthenticationToken(String url, User user) {
		
		String responseMsg = "error";
		AlfrescoSessionTicket data = null;
		try {
			WebResource webResource = client.resource(url);

			ClientResponse response = webResource.type(
					MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, user);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			responseMsg = response.getEntity(String.class);
			objectMapper.configure(
					DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
			data = objectMapper.readValue(responseMsg,
					AlfrescoSessionTicket.class);
			
			
			
		} catch (Exception ex) {

			ex.printStackTrace();
		}

		return data;
	}
	
	
	
	
	public User completeAuthentication (String url, User user){
		
		String responseMsg = "error";
		List<GruppoUtente> gruppi =null;
		
		
		try {
			WebResource webResource = client.resource(url);

			ClientResponse response = webResource.type(
					MediaType.APPLICATION_JSON)
					.get(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			responseMsg = response.getEntity(String.class);
			objectMapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
			gruppi =  objectMapper.readValue(responseMsg, new TypeReference<List<GruppoUtente>>() { }); 
            
		
			
		} catch (Exception ex) {

			ex.printStackTrace();
		}
		
		
		if(gruppi !=null){
			user.setGruppi(gruppi);
			return user;
		}
		
		return null;
		
	}
	

}
