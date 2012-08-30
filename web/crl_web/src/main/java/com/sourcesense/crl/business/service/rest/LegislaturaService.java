package com.sourcesense.crl.business.service.rest;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import com.sourcesense.crl.business.model.Legislatura;
import com.sourcesense.crl.business.model.TipoAtto;
import com.sourcesense.crl.util.ServiceNotAvailableException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Component(value = "legislaturaService")
@Path("/legislature")
public class LegislaturaService {

	

	@Autowired
	transient Client client;
	
	@Autowired
	transient ObjectMapper objectMapper;

	public List<Legislatura> getAllLegislatura(String url) {

		List<Legislatura> listLegislature =null;

		try {
			WebResource webResource = client.resource(url);

			ClientResponse response = webResource.accept(
					MediaType.APPLICATION_JSON).get(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new ServiceNotAvailableException("Errore - "
						+ response.getStatus()
						+ ": Alfresco non raggiungibile ");
			}

			String responseMsg = response.getEntity(String.class);
			objectMapper.configure(
					DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
		    listLegislature = objectMapper.readValue(responseMsg,
					new TypeReference<List<Legislatura>>() {
					});

			
		} catch (JsonMappingException e) {

			throw new ServiceNotAvailableException(this.getClass()
					.getSimpleName(), e);

		} catch (JsonParseException e) {
			throw new ServiceNotAvailableException(this.getClass()
					.getSimpleName(), e);

		} catch (IOException e) {
			throw new ServiceNotAvailableException(this.getClass()
					.getSimpleName(), e);
		}
		return listLegislature;
	}

	public Map<String, String> getAnniByLegislatura(String url,
			String legislatura) {

		/*
		 * WebResource webResource =
		 * client.resource(messageSource.getMessage("alfresco_context_url",
		 * null, Locale.ITALY));
		 * 
		 * ClientResponse response = webResource.queryParam("legislatura",
		 * legislatura).type(MediaType.APPLICATION_JSON)
		 * .get(ClientResponse.class);
		 * 
		 * if (response.getStatus() != 201) { throw new
		 * RuntimeException("Failed : HTTP error code : " +
		 * response.getStatus()); }
		 */

		return null;

	}

}
