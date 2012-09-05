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

import com.sourcesense.crl.business.model.Allegato;
import com.sourcesense.crl.business.model.TestoAtto;
import com.sourcesense.crl.util.ServiceNotAvailableException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;



@Component(value = "attoRecordService")
@Path("/attiRecord")
public class AttoRecordService {
	
	
	@Autowired
	Client client;

	@Autowired
	ObjectMapper objectMapper;
	
	
	public List<TestoAtto> retrieveTestiAtto(String url) {
		List<TestoAtto> listTestiAtto = null;

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
			
			objectMapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
			listTestiAtto = objectMapper.readValue(responseMsg, new TypeReference<List<TestoAtto>>() {});

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
		return listTestiAtto;

	}

	public List<Allegato> retrieveAllegati(String url) {
		List<Allegato> listAllegati = null;

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
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(
					DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);

			listAllegati = objectMapper.readValue(responseMsg,
					new TypeReference<List<Allegato>>() {
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
		return listAllegati;

	}

	
	

}
