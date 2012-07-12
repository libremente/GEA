package com.sourcesense.crl.business.service.rest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus.Series;
import org.springframework.stereotype.Component;

import com.sourcesense.crl.business.model.Atto;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Component(value = "attoService")
@Path("/atti")
public class AttoService  {
	
	
	@Autowired 
	Client client;
	
	@Autowired
	ObjectMapper objectMapper;
	
	
	

	public boolean create(String url,Atto atto) {

		
		try{
		
		WebResource webResource = client.resource(url);
		objectMapper.configure(SerializationConfig.Feature.WRAP_ROOT_VALUE, false);
		String json =  objectMapper.writeValueAsString(atto);
		System.out.println("JSON==="+json);
		ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, json);

		if (response.getStatus() != 200) {
			
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}

		}catch (Exception e) {
			// TODO: handle exception
		}
		return true;
	}

	
	
	public boolean update(Atto atto) {

		//TODO
		return true;
	}
	
	
	public Atto findById(Atto atto) {

		//TODO
		return null;
	}
	
	public ArrayList<Atto> findAll() {

		//TODO
		return null;
	}
	
	
	public ArrayList<Atto> parametricSearch(Atto atto) {

		//TODO
		return null;
	}
	
	
	
}
