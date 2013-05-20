package com.sourcesense.crl.business.service.rest;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.model.CommissioneReferente;
import com.sourcesense.crl.business.model.EsameCommissione;
import com.sourcesense.crl.business.model.Lettera;
import com.sourcesense.crl.util.ServiceNotAvailableException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;


@Component(value = "lettereNotificheService")
@Path("/lettereNotifiche")
public class LettereNotificheService {
	
	
	@Autowired
	Client client;

	@Autowired
	ObjectMapper objectMapper;
	
	
	
	public void merge(String url, Lettera lettera) {
		try {
			WebResource webResource = client.resource(url);
			
			String json = objectMapper.writeValueAsString(lettera);
	
			ClientResponse response = webResource.type(
					MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, json);

			if (response.getStatus() != 200) {
				throw new ServiceNotAvailableException("Errore - "
						+ response.getStatus()
						+ ": Alfresco non raggiungibile "); 
				
			}
			
			
			
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

	
	
	
	public Lettera getLettera (String url){

		Lettera lettera =null;

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
			//objectMapper.configure(
			//		DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
			lettera = objectMapper.readValue(responseMsg, Lettera.class
					);
			
			
			


		}catch (JsonMappingException e) {

			throw new ServiceNotAvailableException(this.getClass()
					.getSimpleName(), e);

		} catch (JsonParseException e) {
			throw new ServiceNotAvailableException(this.getClass()
					.getSimpleName(), e);

		} catch (IOException e) {
			throw new ServiceNotAvailableException(this.getClass()
					.getSimpleName(), e);
		}
		return lettera;
	}
	
	
	public InputStream getFile(String url) {

		InputStream responseFile = null;
		
		WebResource webResource = client.resource(url);

		ClientResponse response = webResource.accept(
				MediaType.MULTIPART_FORM_DATA).get(ClientResponse.class);

		if (response.getStatus() != 200) {
			throw new ServiceNotAvailableException("Errore - "
					+ response.getStatus() + ": Alfresco non raggiungibile ");
		}

		responseFile = response.getEntity(InputStream.class);

		
		return responseFile;

	}
	

}
