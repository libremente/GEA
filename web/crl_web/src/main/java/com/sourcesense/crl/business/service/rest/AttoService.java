package com.sourcesense.crl.business.service.rest;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus.Series;
import org.springframework.stereotype.Component;

import com.sourcesense.crl.business.model.Allegato;
import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.model.AttoRecord;
import com.sourcesense.crl.business.model.Legislatura;
import com.sourcesense.crl.business.model.TipologiaAtto;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.multipart.BodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.MultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;
import com.sun.jersey.multipart.file.StreamDataBodyPart;


@Component(value = "attoService")
@Path("/atti")
public class AttoService  {


	@Autowired 
	Client client;

	@Autowired
	ObjectMapper objectMapper;




	public Atto create(String url,Atto atto) {

		Atto attoRet=null;

		try{
			WebResource webResource = client.resource(url);
			objectMapper.configure(SerializationConfig.Feature.WRAP_ROOT_VALUE, false);
			String json =  objectMapper.writeValueAsString(atto);
			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, json);

			if (response.getStatus() == 500) {

				atto.setError(response.getEntity(String.class));

			}else if (response.getStatus() != 200) {

				throw new RuntimeException();

			}

			String responseMsg = response.getEntity(String.class);
			//objectMapper.configure(DeserializationConfig.Feature.AUTO_DETECT_FIELDS, true);
			//objectMapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
			objectMapper.configure(DeserializationConfig.Feature.USE_ANNOTATIONS, false);
			//responseMsg = responseMsg.replace("atto", "Atto").replace("nome", "numeroAtto");
			//objectMapper.configure(DeserializationConfig.Feature., false);
			attoRet =  objectMapper.readValue(responseMsg, Atto.class);
			atto.setId(attoRet.getId());

		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return atto;
	}



	public boolean update(Atto atto) {

		//TODO
		return true;
	}


	public Atto findById(String url) {
		Atto atto =null;

		try{
			WebResource webResource = client
					.resource(url);

			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}
			String responseMsg = response.getEntity(String.class);
			objectMapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
			objectMapper.configure(DeserializationConfig.Feature.USE_ANNOTATIONS, false);
			atto =  objectMapper.readValue(responseMsg, Atto.class);

		}catch(Exception ex){

			ex.printStackTrace();
		}


		return atto;
	}

	public ArrayList<Atto> findAll() {

		//TODO
		return null;
	}


	public Allegato uploadAllegato(String url,Atto atto,InputStream stream, String nomeFile, String tipologia){

		Allegato allegato=null;

		try {

			WebResource webResource = client.resource(url);
			FormDataMultiPart part = new FormDataMultiPart();
			part.bodyPart(new StreamDataBodyPart("file", stream, nomeFile));
			part.field("id", atto.getId());
			part.field("tipologia", tipologia);

			ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).header("Accept-Charset", "UTF-8").post(ClientResponse.class, part);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			String responseMsg = response.getEntity(String.class);
			objectMapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
			allegato = objectMapper.readValue(responseMsg,
					Allegato.class);


		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return allegato;
	}

	public AttoRecord uploadTestoAtto(String url,Atto atto,InputStream stream, String nomeFile, String tipologia){

		AttoRecord attoRecord=null;

		try {

			WebResource webResource = client.resource(url);
			FormDataMultiPart part = new FormDataMultiPart();
			part.bodyPart(new StreamDataBodyPart("file", stream, nomeFile));
			part.field("id", atto.getId());
			part.field("tipologia", tipologia);

			ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).header("Accept-Charset", "UTF-8").post(ClientResponse.class, part);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			String responseMsg = response.getEntity(String.class);
			objectMapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
			System.out.println(responseMsg);
			attoRecord = objectMapper.readValue(responseMsg,
					AttoRecord.class);


		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return attoRecord;
	}


	public List<Atto> parametricSearch(Atto atto, String url) {
		List<Atto> listAtti =null;

		try {
			WebResource webResource = client.resource(url);
			objectMapper.configure(SerializationConfig.Feature.WRAP_ROOT_VALUE, false);
			//objectMapper.configure(SerializationConfig.Feature.WRITE_NULL_PROPERTIES, false);
			String json =  objectMapper.writeValueAsString(atto);
			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, json);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			String responseMsg = response.getEntity(String.class);
			//System.out.println("response==="+responseMsg);
			objectMapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
			listAtti = objectMapper.readValue(responseMsg,
					new TypeReference<List<Atto>>() {
			});


		} catch (Exception ex) {

			ex.printStackTrace();
		}
		//System.out.println("size lista: "+listAtti.size());
		return listAtti;

	}

	public Atto merge(Atto atto, String url) {
		//TODO
		return null;
	}






}
