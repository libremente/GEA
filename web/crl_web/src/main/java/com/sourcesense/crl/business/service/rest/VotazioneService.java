package com.sourcesense.crl.business.service.rest;

import java.io.Serializable;
import java.util.Map;

import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

@Component(value = "votazioneService")
@Path("/votazioni")
public class VotazioneService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//TODO
	public Map<String, String> getAllEsitoVotoAula() {
		return null;
	}

	public Map<String, String> getAllEsitoVotoCommissioneReferente() {
		// TODO Auto-generated method stub
		return null;
	}

}
