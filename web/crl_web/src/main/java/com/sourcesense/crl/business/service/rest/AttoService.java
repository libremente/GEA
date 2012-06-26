package com.sourcesense.crl.business.service.rest;

import java.io.Serializable;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sourcesense.crl.business.model.Atto;


@Component (value = "attoService")
@Path("/atti")
public class AttoService implements Serializable{

	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Atto list() {
 
        Atto atto = new Atto();
        atto.setCodice("pippo");
        atto.setPrimoFirmatario("mario");
		return atto;
 
	}
	
}
