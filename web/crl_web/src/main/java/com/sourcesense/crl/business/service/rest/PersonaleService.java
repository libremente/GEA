package com.sourcesense.crl.business.service.rest;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcesense.crl.business.model.CommissioneReferente;
import com.sourcesense.crl.business.model.Firmatario;
import com.sourcesense.crl.business.model.Personale;
import com.sourcesense.crl.business.model.Relatore;
import com.sourcesense.crl.util.ServiceNotAvailableException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Component(value = "personaleService")
@Path("/personale")
public class PersonaleService {

	@Autowired
	transient Client client;

	@Autowired
	transient ObjectMapper objectMapper;


	public List<Firmatario> getAllFirmatario(String url) {
		List<Firmatario> listFirmatari =null;

		try {
			WebResource webResource = client.resource(url);

			ClientResponse response = webResource.accept(
					MediaType.APPLICATION_JSON).get(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new ServiceNotAvailableException("Errore - "
						+ response.getStatus()
						+ ": Alfresco non raggiungibile ");
			}

			String responseMsg = response.getEntity(String.class);
			objectMapper.configure(
					DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
			listFirmatari = objectMapper.readValue(responseMsg,
					new TypeReference<List<Firmatario>>() {
			});


		} catch (JsonMappingException e) {

			throw new ServiceNotAvailableException(this.getClass()
					.getSimpleName(), e);

		} catch (JsonParseException e) {
			throw new ServiceNotAvailableException(this.getClass()
					.getSimpleName(), e);

		} catch (IOException e) {
			throw new ServiceNotAvailableException(this.getClass()
					.getSimpleName(), e);
		}
		return listFirmatari;
	}


	public List<Relatore> getAllRelatore(String url) {
		List<Relatore> listRelatori =null;

		try {
			WebResource webResource = client.resource(url);

			ClientResponse response = webResource.accept(
					MediaType.APPLICATION_JSON).get(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new ServiceNotAvailableException("Errore - "
						+ response.getStatus()
						+ ": Alfresco non raggiungibile ");
			}

			String responseMsg = response.getEntity(String.class);
			objectMapper.configure(
					DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
			listRelatori = objectMapper.readValue(responseMsg,
					new TypeReference<List<Relatore>>() {
			});


		} catch (JsonMappingException e) {

			throw new ServiceNotAvailableException(this.getClass()
					.getSimpleName(), e);

		} catch (JsonParseException e) {
			throw new ServiceNotAvailableException(this.getClass()
					.getSimpleName(), e);

		} catch (IOException e) {
			throw new ServiceNotAvailableException(this.getClass()
					.getSimpleName(), e);
		}
		return listRelatori;
	}


	public List<Firmatario> findFirmatariById(String url) {
		List<Firmatario> listFirmatari =null;

		try {
			WebResource webResource = client.resource(url);

			ClientResponse response = webResource.accept(
					MediaType.APPLICATION_JSON).get(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new ServiceNotAvailableException("Errore - "
						+ response.getStatus()
						+ ": Alfresco non raggiungibile ");
			}

			String responseMsg = response.getEntity(String.class);
			
			System.out.println(responseMsg);
			
			objectMapper.configure(
					DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
			listFirmatari = objectMapper.readValue(responseMsg,
					new TypeReference<List<Firmatario>>() {
			});


		} catch (JsonMappingException e) {

			throw new ServiceNotAvailableException(this.getClass()
					.getSimpleName(), e);

		} catch (JsonParseException e) {
			throw new ServiceNotAvailableException(this.getClass()
					.getSimpleName(), e);

		} catch (IOException e) {
			throw new ServiceNotAvailableException(this.getClass()
					.getSimpleName(), e);
		}
		return listFirmatari;
	}



}
