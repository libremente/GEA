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

import com.sourcesense.crl.business.model.Commissione;
import com.sourcesense.crl.business.model.CommissioneConsultiva;
import com.sourcesense.crl.business.model.CommissioneReferente;
import com.sourcesense.crl.business.model.Legislatura;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Component(value = "commissioneService")
@Path("/commissioni")
public class CommissioneService {

	@Autowired
	transient Client client;

	@Autowired
	transient ObjectMapper objectMapper;

	public List<CommissioneReferente> getAllCommissioneReferente(String url) {
		List<CommissioneReferente> listCommissioniReferenti =null;

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
			listCommissioniReferenti = objectMapper.readValue(responseMsg,
					new TypeReference<List<CommissioneReferente>>() {
			});


		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return listCommissioniReferenti;
	}

	public List<CommissioneConsultiva> getAllCommissioneConsultiva(String url) {
		List<CommissioneConsultiva> listCommissioniConsultive =null;

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
			listCommissioniConsultive = objectMapper.readValue(responseMsg,
					new TypeReference<List<CommissioneConsultiva>>() {
			});


		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return listCommissioniConsultive;
	}

}
