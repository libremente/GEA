package com.sourcesense.crl.business.service.rest;

import java.io.Serializable;
import java.util.Map;

import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

@Component(value = "tipoChiusuraService")
@Path("/tipichiusura")
public class TipoChiusuraService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//TODO
	public Map<String, String> getAllTipoChiusura() {
			return null;
	}

}
