package com.sourcesense.crl.business.service.rest;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcesense.crl.business.model.Report;
import com.sourcesense.crl.util.ServiceNotAvailableException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;



@Component(value = "reportService")
@Path("/report")
public class ReportService {
	
	
	@Autowired
	Client client;

	@Autowired
	ObjectMapper objectMapper;
	
	
	
	
	public InputStream getFile(String url, Report report) {

		InputStream responseFile = null;
		try {
			
		
		WebResource webResource = client.resource(url);

		String json = objectMapper.writeValueAsString(report);
		
		ClientResponse response = webResource.accept(
				MediaType.MULTIPART_FORM_DATA).post(ClientResponse.class, json);

		if (response.getStatus() != 200) {
			throw new ServiceNotAvailableException("Errore - "
					+ response.getStatus() + ": Alfresco non raggiungibile ");
		}

		responseFile = response.getEntity(InputStream.class);
		
		
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
		
		return responseFile;

	}
	
	
	

}
