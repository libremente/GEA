package com.sourcesense.crl.business.service.rest;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
import com.sourcesense.crl.business.model.AttoEAC;
import com.sourcesense.crl.business.model.AttoMIS;
import com.sourcesense.crl.business.model.Collegamento;
import com.sourcesense.crl.business.model.CollegamentoAttiSindacato;
import com.sourcesense.crl.business.model.ConsultazioneParere;
import com.sourcesense.crl.business.model.TestoAtto;
import com.sourcesense.crl.util.ServiceNotAvailableException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.StreamDataBodyPart;

@Component(value = "attoService")
@Path("/atti")
public class AttoService {

	@Autowired
	Client client;

	@Autowired
	ObjectMapper objectMapper;

	public Atto create(String url, Atto atto) {

		Atto attoRet = null;

		WebResource webResource = client.resource(url);
		objectMapper.configure(SerializationConfig.Feature.WRAP_ROOT_VALUE,
				false);
		try {
			String json = objectMapper.writeValueAsString(atto);
			ClientResponse response = webResource.type(
					MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, json);

			if (response.getStatus() == 500) {

				atto.setError("Attenzione : l'atto "+atto.getNumeroAtto()+" è già presente" );

			} else if (response.getStatus() != 200) {

				throw new ServiceNotAvailableException("Errore - "
						+ response.getStatus()
						+ ": Alfresco non raggiungibile ");

			}else{

			String responseMsg = response.getEntity(String.class);
			attoRet = objectMapper.readValue(responseMsg, Atto.class);
			atto.setId(attoRet.getId());
			atto.setNumeroAtto(attoRet.getNumeroAtto());
			
			}

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
		return atto;
	}
	
	public AttoMIS createMIS(String url, AttoMIS atto) {

		AttoMIS attoRet = null;

		WebResource webResource = client.resource(url);
		objectMapper.configure(SerializationConfig.Feature.WRAP_ROOT_VALUE,
				false);
		try {
			String json = objectMapper.writeValueAsString(atto);
			ClientResponse response = webResource.type(
					MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, json);

			if (response.getStatus() == 500) {

				atto.setError(response.getEntity(String.class));

			} else if (response.getStatus() != 200) {

				throw new ServiceNotAvailableException("Errore - "
						+ response.getStatus()
						+ ": Alfresco non raggiungibile ");

			}

			String responseMsg = response.getEntity(String.class);
			attoRet = objectMapper.readValue(responseMsg, AttoMIS.class);
			atto.setId(attoRet.getId());

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
		return atto;
	}
	
	public AttoEAC createEAC(String url, AttoEAC atto) {

		AttoEAC attoRet = null;

		WebResource webResource = client.resource(url);
		objectMapper.configure(SerializationConfig.Feature.WRAP_ROOT_VALUE,
				false);
		try {
			String json = objectMapper.writeValueAsString(atto);
			ClientResponse response = webResource.type(
					MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, json);

			if (response.getStatus() == 500) {

				atto.setError(response.getEntity(String.class));

			} else if (response.getStatus() != 200) {

				throw new ServiceNotAvailableException("Errore - "
						+ response.getStatus()
						+ ": Alfresco non raggiungibile ");

			}

			String responseMsg = response.getEntity(String.class);
			//objectMapper.configure(DeserializationConfig.Feature.USE_ANNOTATIONS, false);

			attoRet = objectMapper.readValue(responseMsg, AttoEAC.class);
			atto.setId(attoRet.getId());

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
		return atto;
	}

	public boolean update(Atto atto) {

		// TODO
		return true;
	}

	public Atto findById(String url) {
		Atto atto = null;

		WebResource webResource = client.resource(url);

		ClientResponse response = webResource
				.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

		if (response.getStatus() != 200) {
			throw new ServiceNotAvailableException("Errore - "
					+ response.getStatus() + ": Alfresco non raggiungibile ");
		}
		String responseMsg = response.getEntity(String.class);
		
		try {
			
			//objectMapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
			atto = objectMapper.readValue(responseMsg, Atto.class);

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

		return atto;
	}

	
	
	public AttoEAC findEACById(String url) {
		AttoEAC atto = null;

		WebResource webResource = client.resource(url);

		ClientResponse response = webResource
				.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

		if (response.getStatus() != 200) {
			throw new ServiceNotAvailableException("Errore - "
					+ response.getStatus() + ": Alfresco non raggiungibile ");
		}
		String responseMsg = response.getEntity(String.class);
		
		try {
			
			//objectMapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
			atto = objectMapper.readValue(responseMsg, AttoEAC.class);

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

		return atto;
	}
	
	public AttoMIS findMISById(String url) {
		AttoMIS atto = null;

		WebResource webResource = client.resource(url);

		ClientResponse response = webResource
				.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

		if (response.getStatus() != 200) {
			throw new ServiceNotAvailableException("Errore - "
					+ response.getStatus() + ": Alfresco non raggiungibile ");
		}
		String responseMsg = response.getEntity(String.class);
		
		try {
			
			atto = objectMapper.readValue(responseMsg, AttoMIS.class);

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

		return atto;
	}
	
	
	
	public ArrayList<Atto> findAll() {

		// TODO
		return null;
	}

	public Allegato uploadAllegato(String url, Atto atto, InputStream stream,
			Allegato allegato, String tipologia) {

		

		try {

			WebResource webResource = client.resource(url);
			FormDataMultiPart part = new FormDataMultiPart();
			part.bodyPart(new StreamDataBodyPart("file", stream, allegato.getNome()));
			part.field("id", atto.getId());
			part.field("tipologia", tipologia);
			part.field("pubblico", ""+allegato.isPubblico());   
			ClientResponse response = webResource
					.type(MediaType.MULTIPART_FORM_DATA_TYPE)
					.header("Accept-Charset", "UTF-8")
					.post(ClientResponse.class, part);

			if (response.getStatus() != 200) {
				throw new ServiceNotAvailableException("Errore - "
						+ response.getStatus()
						+ ": Alfresco non raggiungibile ");
			}

			String responseMsg = response.getEntity(String.class);
			allegato = objectMapper.readValue(responseMsg, Allegato.class);

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
		return allegato;
	}

	
	public Allegato uploadAllegatoParere(String url, Atto atto, InputStream stream,
			Allegato allegato, String tipologia) {

		

		try {

			WebResource webResource = client.resource(url);
			FormDataMultiPart part = new FormDataMultiPart();
			part.bodyPart(new StreamDataBodyPart("file", stream, allegato.getNome()));
			part.field("id", atto.getId());
			part.field("tipologia", tipologia);
			part.field("organismoStatutario", allegato.getOrganismoStatutario());
			part.field("pubblico", ""+allegato.isPubblico());
			
			ClientResponse response = webResource
					.type(MediaType.MULTIPART_FORM_DATA_TYPE)
					.header("Accept-Charset", "UTF-8")
					.post(ClientResponse.class, part);

			if (response.getStatus() != 200) {
				throw new ServiceNotAvailableException("Errore - "
						+ response.getStatus()
						+ ": Alfresco non raggiungibile ");
			}

			String responseMsg = response.getEntity(String.class);
			//objectMapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
			allegato = objectMapper.readValue(responseMsg, Allegato.class);

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
		return allegato;
	}
	
	public Allegato uploadAllegatoConsultazioni(String url, Atto atto, InputStream stream,
			Allegato allegato, String tipologia) {

		
		try {

			WebResource webResource = client.resource(url);
			FormDataMultiPart part = new FormDataMultiPart();
			part.bodyPart(new StreamDataBodyPart("file", stream, allegato.getNome()));
			part.field("id", atto.getId());
			part.field("tipologia", tipologia);
			part.field("consultazione", allegato.getConsultazione());
			part.field("pubblico", ""+allegato.isPubblico());
			
			ClientResponse response = webResource
					.type(MediaType.MULTIPART_FORM_DATA_TYPE)
					.header("Accept-Charset", "UTF-8")
					.post(ClientResponse.class, part);

			if (response.getStatus() != 200) {
				throw new ServiceNotAvailableException("Errore - "
						+ response.getStatus()
						+ ": Alfresco non raggiungibile ");
			}

			String responseMsg = response.getEntity(String.class);
			//objectMapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
			allegato = objectMapper.readValue(responseMsg, Allegato.class);

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
		return allegato;
	}
	
	
	public void removeFirmatario(String url){
		
		WebResource webResource = client.resource(url);

		ClientResponse response = webResource
				.accept(MediaType.APPLICATION_JSON).delete(ClientResponse.class);

		if (response.getStatus() != 200) {
			throw new ServiceNotAvailableException("Errore - "
					+ response.getStatus() + ": Alfresco non raggiungibile ");
		}
				
	}
	
	
	public TestoAtto uploadTestoAtto(String url, Atto atto,
			InputStream stream, TestoAtto testoAtto, String tipologia) {

		TestoAtto attoRecord = null;

		try {

			WebResource webResource = client.resource(url);
			FormDataMultiPart part = new FormDataMultiPart();
			part.bodyPart(new StreamDataBodyPart("file", stream, testoAtto.getNome()));
			part.field("id", atto.getId());
			part.field("tipologia", tipologia);
			part.field("pubblico", ""+testoAtto.isPubblico());

			ClientResponse response = webResource
					.type(MediaType.MULTIPART_FORM_DATA_TYPE)
					.header("Accept-Charset", "UTF-8")
					.post(ClientResponse.class, part);

			if (response.getStatus() != 200) {
				throw new ServiceNotAvailableException("Errore - "
						+ response.getStatus()
						+ ": Alfresco non raggiungibile ");
			}

			String responseMsg = response.getEntity(String.class);
			attoRecord = objectMapper.readValue(responseMsg, TestoAtto.class);

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
		return attoRecord;
	}

	
	public TestoAtto changeAllegato(String url) {

		TestoAtto attoRecord = null;

		try {

			WebResource webResource = client.resource(url);
			
			ClientResponse response = webResource.post(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new ServiceNotAvailableException("Errore - "
						+ response.getStatus()
						+ ": Alfresco non raggiungibile ");
			}

			String responseMsg = response.getEntity(String.class);
			attoRecord = objectMapper.readValue(responseMsg, TestoAtto.class);

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
		return attoRecord;
	}
	
	
	
	
	public List<String> findTipoAttiSindacato (String url){
		List<String> listTipi = null;

		try {
			WebResource webResource = client.resource(url);
			objectMapper.configure(SerializationConfig.Feature.WRAP_ROOT_VALUE,
					false);
			ClientResponse response = webResource.type(
					MediaType.APPLICATION_JSON)
					.get(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new ServiceNotAvailableException("Errore - "
						+ response.getStatus()
						+ ": Alfresco non raggiungibile ");
			}

			String responseMsg = response.getEntity(String.class);
			
			objectMapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
			listTipi = objectMapper.readValue(responseMsg,
					new TypeReference<List<String>>() {
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
		return listTipi;

	}
	
	public List<CollegamentoAttiSindacato> findAllAttiSindacato (String url){
		List<CollegamentoAttiSindacato> listAtti = null;

		try {
			WebResource webResource = client.resource(url);
			objectMapper.configure(SerializationConfig.Feature.WRAP_ROOT_VALUE,
					false);
			ClientResponse response = webResource.type(
					MediaType.APPLICATION_JSON)
					.get(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new ServiceNotAvailableException("Errore - "
						+ response.getStatus()
						+ ": Alfresco non raggiungibile ");
			}

			String responseMsg = response.getEntity(String.class);
			
			objectMapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
			listAtti = objectMapper.readValue(responseMsg,
					new TypeReference<List<CollegamentoAttiSindacato>>() {
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
		return listAtti;

	}

	
	public List<Collegamento> findCollegamentiAttoById (String url){
		List<Collegamento> listAtti = null;

		try {
			WebResource webResource = client.resource(url);
			objectMapper.configure(SerializationConfig.Feature.WRAP_ROOT_VALUE,
					false);
			ClientResponse response = webResource.type(
					MediaType.APPLICATION_JSON)
					.get(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new ServiceNotAvailableException("Errore - "
						+ response.getStatus()
						+ ": Alfresco non raggiungibile ");
			}

			String responseMsg = response.getEntity(String.class);
			
			objectMapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
			listAtti = objectMapper.readValue(responseMsg,
					new TypeReference<List<Collegamento>>() {
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
		return listAtti;

	}
	
	public List<Atto> parametricSearch(Atto atto, String url) {
		List<Atto> listAtti = null;

		try {
			WebResource webResource = client.resource(url);
			objectMapper.configure(SerializationConfig.Feature.WRAP_ROOT_VALUE,
					false);
			String json = objectMapper.writeValueAsString(atto);
			ClientResponse response = webResource.type(
					MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, json);

			if (response.getStatus() != 200) {
				throw new ServiceNotAvailableException("Errore - "
						+ response.getStatus()
						+ ": Alfresco non raggiungibile ");
			}

			String responseMsg = response.getEntity(String.class);
			
			objectMapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
			listAtti = objectMapper.readValue(responseMsg,
					new TypeReference<List<Atto>>() {
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
		return listAtti;

	}

	public void merge(String url, Atto atto) {

		try {
			WebResource webResource = client.resource(url);
			objectMapper.configure(SerializationConfig.Feature.WRAP_ROOT_VALUE,
					false);
			String json = objectMapper.writeValueAsString(atto);
			ClientResponse response = webResource.type(
					MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, json);

			if (response.getStatus() != 200) {
				throw new ServiceNotAvailableException("Errore - "
						+ response.getStatus()
						+ ": Alfresco non raggiungibile ");
			}
			
			String responseMsg = response.getEntity(String.class);
			
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

	}
	
	
	public void salvaPareri(String url, ConsultazioneParere consultazioneParere) {

		try {
            WebResource webResource = client.resource(url);
			
			DateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			objectMapper.getSerializationConfig().setDateFormat(myDateFormat);
			
			objectMapper.configure(SerializationConfig.Feature.WRAP_ROOT_VALUE,
				false);
			objectMapper.configure(SerializationConfig.Feature.USE_ANNOTATIONS,
					false);
			objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,
					false);
			
			String json = objectMapper.writeValueAsString(consultazioneParere);
			ClientResponse response = webResource.type(
					MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, json);

			if (response.getStatus() != 200) {
				throw new ServiceNotAvailableException("Errore - "
						+ response.getStatus()
						+ ": Alfresco non raggiungibile ");
			}
			
			objectMapper.configure(SerializationConfig.Feature.USE_ANNOTATIONS,
					true);
			
			
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

	}

}
