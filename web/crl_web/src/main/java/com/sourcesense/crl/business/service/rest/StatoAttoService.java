package com.sourcesense.crl.business.service.rest;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcesense.crl.business.model.Legislatura;
import com.sourcesense.crl.business.model.StatoAtto;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Component(value = "statoService")
@Path("/stati")
public class StatoAttoService {

	@Autowired
	transient Client client;
	
	@Autowired
	transient ObjectMapper objectMapper;


	public List<StatoAtto> getAllStato(String url) {
		List<StatoAtto> listStati =null;

		try {
			WebResource webResource = client.resource(url);

			ClientResponse response = webResource.accept(
					MediaType.APPLICATION_JSON).get(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			String responseMsg = response.getEntity(String.class);
			objectMapper.configure(
					DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
		    listStati = objectMapper.readValue(responseMsg,
					new TypeReference<List<StatoAtto>>() {
					});

			
		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return listStati;
	}

}
