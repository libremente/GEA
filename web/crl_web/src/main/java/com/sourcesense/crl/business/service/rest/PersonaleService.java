package com.sourcesense.crl.business.service.rest;

import java.io.Serializable;
import java.util.Map;

import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

@Component(value = "personaleService")
@Path("/personale")
public class PersonaleService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//TODO
	public Map<String, String> getAllFirmatario() {
		return null;
	}

	public Map<String, String> getAllPrimoFirmatario() {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, String> getAllRelatore() {
		// TODO Auto-generated method stub
		return null;
	}

}
