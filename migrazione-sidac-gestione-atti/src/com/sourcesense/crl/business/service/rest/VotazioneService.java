package com.sourcesense.crl.business.service.rest;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Path;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcesense.crl.business.model.Votazione;
import com.sun.jersey.api.client.Client;

@Component(value = "votazioneService")
@Path("/votazioni")
public class VotazioneService {

	@Autowired
	 Client client;
	
	@Autowired
	 ObjectMapper objectMapper;
	
	//TODO
	public List<Votazione> getAllEsitoVotoAula() {
		return null;
	}

	public List<Votazione> getAllEsitoVotoCommissioneReferente() {
		// TODO Auto-generated method stub
		return null;
	}

}
