package com.sourcesense.crl.business.service.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Formatter;
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

import com.sourcesense.crl.business.model.Allegato;
import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.model.Commissione;
import com.sourcesense.crl.business.model.CommissioneConsultiva;
import com.sourcesense.crl.business.model.CommissioneReferente;
import com.sourcesense.crl.business.model.EsameCommissione;
import com.sourcesense.crl.business.model.Legislatura;
import com.sourcesense.crl.business.model.TestoAtto;
import com.sourcesense.crl.util.ServiceNotAvailableException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.StreamDataBodyPart;

@Component(value = "commissioneService")
@Path("/commissioni")
public class CommissioneService {

	@Autowired
	Client client;

	public void merge(String url, EsameCommissione esameCommissione) {
		try {
			
            ObjectMapper objMapper = new ObjectMapper();
			
			WebResource webResource = client.resource(url);
			objMapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, false);     
			objMapper.configure(SerializationConfig.Feature.WRAP_ROOT_VALUE, false);
			objMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
			//DateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			//objMapper.getSerializationConfig().withDateFormat(myDateFormat);
			
			objMapper.configure(SerializationConfig.Feature.USE_ANNOTATIONS, false);
			//objMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);

			String json = objMapper.writeValueAsString(esameCommissione);

			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, json);

			
			if (response.getStatus() != 200) {
				throw new ServiceNotAvailableException("Errore - " + response.getStatus() + ": Alfresco non raggiungibile ");
			}

		} catch (JsonMappingException e) {

			throw new ServiceNotAvailableException(this.getClass().getSimpleName(), e);

		} catch (JsonParseException e) {
			throw new ServiceNotAvailableException(this.getClass().getSimpleName(), e);

		} catch (IOException e) {
			throw new ServiceNotAvailableException(this.getClass().getSimpleName(), e);
		}

	}

	public void removeAbbinamento(String url) {

		WebResource webResource = client.resource(url);
		ObjectMapper objectMapper = new ObjectMapper();
		ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);

		if (response.getStatus() != 200) {
			throw new ServiceNotAvailableException("Errore - " + response.getStatus() + ": Alfresco non raggiungibile ");
		}

		objectMapper.configure(SerializationConfig.Feature.USE_ANNOTATIONS, true);

	}

	public List<CommissioneReferente> retrieveCommissioniReferenteByAtto(String url) {

		List<CommissioneReferente> listCommissioniReferenti = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			WebResource webResource = client.resource(url);

			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new ServiceNotAvailableException("Errore - " + response.getStatus() + ": Alfresco non raggiungibile ");
			}

			String responseMsg = response.getEntity(String.class);
			objectMapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
			listCommissioniReferenti = objectMapper.readValue(responseMsg, new TypeReference<List<CommissioneReferente>>() {
			});

		} catch (JsonMappingException e) {

			throw new ServiceNotAvailableException(this.getClass().getSimpleName(), e);

		} catch (JsonParseException e) {
			throw new ServiceNotAvailableException(this.getClass().getSimpleName(), e);

		} catch (IOException e) {
			throw new ServiceNotAvailableException(this.getClass().getSimpleName(), e);
		}
		return listCommissioniReferenti;
	}

	public List<CommissioneConsultiva> retrieveCommissioniConsultiveByAtto(String url) {

		List<CommissioneConsultiva> listCommissioniConsultive = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			WebResource webResource = client.resource(url);

			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new ServiceNotAvailableException("Errore - " + response.getStatus() + ": Alfresco non raggiungibile ");
			}

			String responseMsg = response.getEntity(String.class);
			objectMapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, false);
			listCommissioniConsultive = objectMapper.readValue(responseMsg, new TypeReference<List<CommissioneConsultiva>>() {
			});

		} catch (JsonMappingException e) {

			throw new ServiceNotAvailableException(this.getClass().getSimpleName(), e);

		} catch (JsonParseException e) {
			throw new ServiceNotAvailableException(this.getClass().getSimpleName(), e);

		} catch (IOException e) {
			throw new ServiceNotAvailableException(this.getClass().getSimpleName(), e);
		}
		return listCommissioniConsultive;
	}

	public List<Commissione> getAllCommissioni(String url) {
		List<Commissione> listCommissioni = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			WebResource webResource = client.resource(url);

			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new ServiceNotAvailableException("Errore - " + response.getStatus() + ": Alfresco non raggiungibile ");
			}

			String responseMsg = response.getEntity(String.class);
			objectMapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
			listCommissioni = objectMapper.readValue(responseMsg, new TypeReference<List<Commissione>>() {
			});

		} catch (JsonMappingException e) {

			throw new ServiceNotAvailableException(this.getClass().getSimpleName(), e);

		} catch (JsonParseException e) {
			throw new ServiceNotAvailableException(this.getClass().getSimpleName(), e);

		} catch (IOException e) {
			throw new ServiceNotAvailableException(this.getClass().getSimpleName(), e);
		}
		return listCommissioni;
	}

	public Allegato uploadAllegato(String url, Atto atto, InputStream stream, Allegato allegato, String tipologia) {

		ObjectMapper objectMapper = new ObjectMapper();
		try {

			WebResource webResource = client.resource(url);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			FormDataMultiPart part = new FormDataMultiPart();
			String dataSeduta = (allegato.getDataSeduta() == null) ? "" : format.format(allegato.getDataSeduta());
			part.bodyPart(new StreamDataBodyPart("file", stream, allegato.getNome()));
			part.field("id", atto.getId());
			part.field("pubblico", allegato.isPubblico() + "");
			part.field("provenienza", allegato.getCommissione());
			part.field("passaggio", allegato.getPassaggio());

			part.field("dataSeduta", dataSeduta);

			part.field("tipologia", tipologia);

			ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).header("Accept-Charset", "UTF-8").post(ClientResponse.class, part);

			if (response.getStatus() != 200) {
				throw new ServiceNotAvailableException("Errore - " + response.getStatus() + ": Alfresco non raggiungibile ");
			}

			String responseMsg = response.getEntity(String.class);
			objectMapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, false);
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
		ObjectMapper objectMapper = new ObjectMapper();
		try {

			WebResource webResource = client.resource(url);
			FormDataMultiPart part = new FormDataMultiPart();
			part.bodyPart(new StreamDataBodyPart("file", stream, testoAtto.getNome()));
			part.field("id", atto.getId());
			part.field("tipologia", tipologia);
			part.field("commissione", testoAtto.getCommissione());
			part.field("passaggio", testoAtto.getPassaggio());
			part.field("pubblico", "" + testoAtto.isPubblico());

			ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).header("Accept-Charset", "UTF-8").post(ClientResponse.class, part);

			if (response.getStatus() != 200) {
				throw new ServiceNotAvailableException("Errore - " + response.getStatus() + ": Alfresco non raggiungibile ");
			}

			String responseMsg = response.getEntity(String.class);
			objectMapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, false);
			attoRecord = objectMapper.readValue(responseMsg, TestoAtto.class);

		} catch (JsonMappingException e) {

			throw new ServiceNotAvailableException(this.getClass().getSimpleName(), e);

		} catch (JsonParseException e) {
			throw new ServiceNotAvailableException(this.getClass().getSimpleName(), e);

		} catch (IOException e) {
			throw new ServiceNotAvailableException(this.getClass().getSimpleName(), e);
		}
		return attoRecord;
	}

}