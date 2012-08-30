package com.sourcesense.crl.business.service.rest;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcesense.crl.business.model.GruppoUtente;
import com.sourcesense.crl.business.model.User;
import com.sourcesense.crl.business.security.AlfrescoSessionTicket;
import com.sourcesense.crl.util.ServiceNotAvailableException;
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

		WebResource webResource = client.resource(url);

		ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, user);

		if (response.getStatus() != 200) {
			throw new ServiceNotAvailableException(
					"Errore - "+ response.getStatus() +": Alfresco non raggiungibile " ) ;
		}

		responseMsg = response.getEntity(String.class);
		objectMapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE,
				true);
		try {
			
			data = objectMapper.readValue(responseMsg,AlfrescoSessionTicket.class);
		
		} catch (JsonMappingException e) {

			throw new ServiceNotAvailableException( this.getClass().getSimpleName() ,e);

		} catch (JsonParseException e) {
			throw new ServiceNotAvailableException(this.getClass().getSimpleName() ,e);

		} catch (IOException e) {
			throw new ServiceNotAvailableException(this.getClass().getSimpleName() ,e);
		}

		return data;
	}

	public User completeAuthentication(String url, User user) {

		String responseMsg = "error";
		List<GruppoUtente> gruppi = null;

		WebResource webResource = client.resource(url);

		ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
				.get(ClientResponse.class);

		if (response.getStatus() != 200) {
			throw new ServiceNotAvailableException(
					"Errore - "+ response.getStatus() +": Alfresco non raggiungibile " ) ;
		}

		responseMsg = response.getEntity(String.class);
		objectMapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE,
				true);
		try {
			gruppi = objectMapper.readValue(responseMsg,
					new TypeReference<List<GruppoUtente>>() {
					});

		} catch (JsonMappingException e) {

			throw new ServiceNotAvailableException( this.getClass().getSimpleName() ,e);

		} catch (JsonParseException e) {
			throw new ServiceNotAvailableException(this.getClass().getSimpleName() ,e);

		} catch (IOException e) {
			throw new ServiceNotAvailableException(this.getClass().getSimpleName() ,e);
		}

		if (gruppi != null) {
			user.setGruppi(gruppi);
			return user;
		}

		return null;

	}

}
