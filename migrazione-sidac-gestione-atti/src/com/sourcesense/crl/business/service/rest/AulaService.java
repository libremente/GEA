package com.sourcesense.crl.business.service.rest;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sourcesense.crl.business.model.Allegato;
import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.model.EsameAula;
import com.sourcesense.crl.business.model.EsameCommissione;
import com.sourcesense.crl.business.model.Passaggio;
import com.sourcesense.crl.business.model.TestoAtto;
import com.sourcesense.crl.util.ServiceNotAvailableException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.StreamDataBodyPart;

@Component(value = "aulaService")
@Path("/aula")
public class AulaService {

	@Autowired
	Client client;

	@Autowired
	ObjectMapper objectMapper;

	public Passaggio rinvioEsame(String url, EsameAula esameAula) {

		Passaggio passaggio = null;

		try {
			WebResource webResource = client.resource(url);

			DateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			objectMapper.getSerializationConfig().setDateFormat(myDateFormat);

			objectMapper.configure(SerializationConfig.Feature.WRAP_ROOT_VALUE, false);
			objectMapper.configure(SerializationConfig.Feature.USE_ANNOTATIONS, false);
			objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);

			String json = objectMapper.writeValueAsString(esameAula);

			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, json);

			if (response.getStatus() != 200) {
				throw new ServiceNotAvailableException("Errore - " + response.getStatus() + ": Alfresco non raggiungibile ");
			}

			objectMapper.configure(SerializationConfig.Feature.USE_ANNOTATIONS, true);

			String responseMsg = response.getEntity(String.class);
			/*
			 * objectMapper.configure(SerializationConfig.Feature.WRAP_ROOT_VALUE
			 * , true); passaggio = objectMapper.readValue(responseMsg,
			 * Passaggio.class);
			 */

		} catch (JsonMappingException e) {

			throw new ServiceNotAvailableException(this.getClass().getSimpleName(), e);

		} catch (JsonParseException e) {
			throw new ServiceNotAvailableException(this.getClass().getSimpleName(), e);

		} catch (IOException e) {
			throw new ServiceNotAvailableException(this.getClass().getSimpleName(), e);
		}

		return passaggio;

	}

	public void merge(String url, EsameAula esameAula) {
		try {
			WebResource webResource = client.resource(url);

			String json = objectMapper.writeValueAsString(esameAula);

			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, json);

			if (response.getStatus() != 200) {
				throw new ServiceNotAvailableException("Errore - " + response.getStatus() + ": Alfresco non raggiungibile ");
			}

			objectMapper.configure(SerializationConfig.Feature.USE_ANNOTATIONS, true);

		} catch (JsonMappingException e) {

			throw new ServiceNotAvailableException(url, e);

		} catch (JsonParseException e) {
			throw new ServiceNotAvailableException(url, e);

		} catch (IOException e) {
			throw new ServiceNotAvailableException(url, e);
		}

	}

	public Allegato uploadAllegato(String url, Atto atto, InputStream stream, Allegato allegato, String tipologia) {

		try {

			WebResource webResource = client.resource(url);
			FormDataMultiPart part = new FormDataMultiPart();
			part.bodyPart(new StreamDataBodyPart("file", stream, allegato.getNome()));
			part.field("id", atto.getId());
			part.field("tipologia", tipologia);
			part.field("passaggio", allegato.getPassaggio());
			ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).header("Accept-Charset", "UTF-8").post(ClientResponse.class, part);

			if (response.getStatus() != 200) {
				throw new ServiceNotAvailableException("Errore - " + response.getStatus() + ": Alfresco non raggiungibile ");
			}

			String responseMsg = response.getEntity(String.class);
			// objectMapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE,
			// true);
			allegato = objectMapper.readValue(responseMsg, Allegato.class);

		} catch (JsonMappingException e) {

			throw new ServiceNotAvailableException(this.getClass().getSimpleName(), e);

		} catch (JsonParseException e) {
			throw new ServiceNotAvailableException(this.getClass().getSimpleName(), e);

		} catch (IOException e) {
			throw new ServiceNotAvailableException(this.getClass().getSimpleName(), e);
		}
		return allegato;
	}

	public TestoAtto uploadTestoAtto(String url, Atto atto, InputStream stream, TestoAtto testoAtto, String tipologia) {

		TestoAtto attoRecord = null;

		WebResource webResource = client.resource(url);
		FormDataMultiPart part = new FormDataMultiPart();
		part.bodyPart(new StreamDataBodyPart("file", stream, testoAtto.getNome()));
		part.field("id", atto.getId());
		part.field("tipologia", tipologia);
		part.field("pubblico", "" + testoAtto.isPubblico());
		part.field("passaggio", testoAtto.getPassaggio());

		ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).header("Accept-Charset", "UTF-8").post(ClientResponse.class, part);

		if (response.getStatus() != 200) {
			throw new ServiceNotAvailableException("Errore - " + response.getStatus() + ": Alfresco non raggiungibile ");
		}

		// String responseMsg = response.getEntity(String.class);
		// attoRecord = objectMapper.readValue(responseMsg, TestoAtto.class);

		return attoRecord;
	}

}
