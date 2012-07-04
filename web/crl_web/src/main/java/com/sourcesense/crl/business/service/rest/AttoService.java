package com.sourcesense.crl.business.service.rest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.util.CRLMessage;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Component(value = "attoService")
@Path("/atti")
public class AttoService implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
    ResourceBundleMessageSource messageSource;
	
	@Autowired 
	Client client;
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Atto list() {

		Atto atto = new Atto();
		atto.setCodice("pippo");
		atto.setPrimoFirmatario("mario");
		return atto;

	}

	public boolean create(Atto atto) {

		
		WebResource webResource = client.resource(messageSource.getMessage("alfresco_context_url", null, Locale.ITALY));
		
		ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, atto);

		if (response.getStatus() != 201) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
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
