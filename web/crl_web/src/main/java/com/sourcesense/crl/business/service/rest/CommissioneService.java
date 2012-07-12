package com.sourcesense.crl.business.service.rest;

import java.io.Serializable;
import java.util.Map;

import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

@Component(value = "commissioneService")
@Path("/commissioni")
public class CommissioneService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//TODO
	public Map<String, String> getAllCommissioneReferente() {
		return null;
	}

	public Map<String, String> getAllCommissioneConsultiva() {
		// TODO Auto-generated method stub
		return null;
	}

}
