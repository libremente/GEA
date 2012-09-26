package com.sourcesense.crl.business.service.rest;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcesense.crl.business.model.EsameAula;
import com.sourcesense.crl.business.model.EsameCommissione;
import com.sourcesense.crl.util.ServiceNotAvailableException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;



@Component(value = "aulaService")
@Path("/aula")
public class AulaService {
	
	@Autowired
	Client client;

	@Autowired
	ObjectMapper objectMapper;
	
	
	public void merge(String url, EsameAula esameAula) {
		try {
			WebResource webResource = client.resource(url);
			
			DateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			objectMapper.getSerializationConfig().setDateFormat(myDateFormat);
			
			objectMapper.configure(SerializationConfig.Feature.WRAP_ROOT_VALUE,
				false);
			objectMapper.configure(SerializationConfig.Feature.USE_ANNOTATIONS,
					false);
			objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,
					false);
			//objectMapper.getSerializationConfig().addMixInAnnotations(Commissione.class, Commissione.class);
			
			String json = objectMapper.writeValueAsString(esameAula);
	
			ClientResponse response = webResource.type(
					MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, json);

			if (response.getStatus() != 200) {
				throw new ServiceNotAvailableException("Errore - "
						+ response.getStatus()
						+ ": Alfresco non raggiungibile ");
			}
			
			objectMapper.configure(SerializationConfig.Feature.USE_ANNOTATIONS,
					true);
			
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

	}
	
	                  

}
