package com.sourcesense.crl.business.service.rest;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import com.sourcesense.crl.business.model.TipoAtto;
import com.sourcesense.crl.business.model.TipologiaAtto;
import com.sourcesense.crl.util.ServiceNotAvailableException;
import com.sourcesense.crl.web.ui.beans.UserBean;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;


@Component(value = "tipoAttoService")
@Path("/tipiatto")
public class TipoAttoService {

	
	
	
	@Autowired
	Client client;
	
	@Autowired
	ObjectMapper objectMapper;
	
    public List<TipoAtto> getAllTipoAtto(String url) {
    	
    	List<TipoAtto> listTipiAtto =null;
        
		try{
			WebResource webResource = client
					.resource(url);

			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new ServiceNotAvailableException("Errore - "
						+ response.getStatus()
						+ ": Alfresco non raggiungibile ");
			}
			
			
			String responseMsg = response.getEntity(String.class);
	        objectMapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
	        listTipiAtto =  objectMapper.readValue(responseMsg, new TypeReference<List<TipoAtto>>() { });
	        
			}catch (JsonMappingException e) {

				throw new ServiceNotAvailableException(this.getClass()
						.getSimpleName(), e);

			} catch (JsonParseException e) {
				throw new ServiceNotAvailableException(this.getClass()
						.getSimpleName(), e);

			} catch (IOException e) {
				throw new ServiceNotAvailableException(this.getClass()
						.getSimpleName(), e);
			}
		
		
		return listTipiAtto;
		
	}
    
    
    
    
    public List<TipologiaAtto> getTipologieByTipoAtto(String url){
    	
        List<TipologiaAtto> listTipologieAtto =null;
        
		try{
			WebResource webResource = client
					.resource(url);

			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new ServiceNotAvailableException("Errore - "
						+ response.getStatus()
						+ ": Alfresco non raggiungibile ");
			}
			
			
			String responseMsg = response.getEntity(String.class);
	        objectMapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
	        listTipologieAtto =  objectMapper.readValue(responseMsg, new TypeReference<List<TipologiaAtto>>() { });
	        
			}catch (JsonMappingException e) {

				throw new ServiceNotAvailableException(this.getClass()
						.getSimpleName(), e);

			} catch (JsonParseException e) {
				throw new ServiceNotAvailableException(this.getClass()
						.getSimpleName(), e);

			} catch (IOException e) {
				throw new ServiceNotAvailableException(this.getClass()
						.getSimpleName(), e);
			}
		
		
		return listTipologieAtto;
    	
    }

}
