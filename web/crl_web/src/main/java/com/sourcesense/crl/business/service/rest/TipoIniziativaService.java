package com.sourcesense.crl.business.service.rest;

import java.io.Serializable;
import java.util.Map;

import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

@Component(value = "tipoIniziativaService")
@Path("/tipiiniziative")
public class TipoIniziativaService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//TODO
	public Map<String, String> getAllTipoIniziativa() {
		return null;
	}
	
}
