package com.sourcesense.crl.business.service.rest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


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

import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.model.Legislatura;
import com.sourcesense.crl.business.model.TipologiaAtto;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Component(value = "attoService")
@Path("/atti")
public class AttoService  {


	@Autowired 
	Client client;

	@Autowired
	ObjectMapper objectMapper;




	public boolean create(String url,Atto atto) {


		try{
			WebResource webResource = client.resource(url);
			objectMapper.configure(SerializationConfig.Feature.WRAP_ROOT_VALUE, false);
			String json =  objectMapper.writeValueAsString(atto);
			System.out.println("JSON==="+json);
			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, json);

			if (response.getStatus() != 200) {

				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

		}catch (Exception e) {
			// TODO: handle exception
		}
		return true;
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
			atto =  objectMapper.readValue(responseMsg, new TypeReference<Atto>() { });

		}catch(Exception ex){

			ex.printStackTrace();
		}


		return atto;
	}

	public ArrayList<Atto> findAll() {

		//TODO
		return null;
	}


	public List<Atto> parametricSearch(Atto atto, String url) {
		List<Atto> listAtti =null;

		try {
			WebResource webResource = client.resource(url);
			objectMapper.configure(SerializationConfig.Feature.WRAP_ROOT_VALUE, false);
			//objectMapper.configure(SerializationConfig.Feature.WRITE_NULL_PROPERTIES, false);
			String json =  objectMapper.writeValueAsString(atto);

			System.out.println("JSON==="+json);
			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, json);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			String responseMsg = response.getEntity(String.class);
			//System.out.println("response==="+responseMsg);
			objectMapper.configure(
					DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
			listAtti = objectMapper.readValue(responseMsg,
					new TypeReference<List<Atto>>() {
			});


		} catch (Exception ex) {

			ex.printStackTrace();
		}
		System.out.println("size lista: "+listAtti.size());
		return listAtti;

	}






}
