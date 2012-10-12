package com.sourcesense.crl.business.service.rest;

import javax.ws.rs.Path;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.client.Client;


@Component(value = "lettereNotificheService")
@Path("/lettereNotifiche")
public class LettereNotificheService {
	
	
	@Autowired
	Client client;

	@Autowired
	ObjectMapper objectMapper;

}
