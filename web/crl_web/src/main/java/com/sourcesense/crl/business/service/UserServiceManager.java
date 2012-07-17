package com.sourcesense.crl.business.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;


import com.sourcesense.crl.business.model.User;
import com.sourcesense.crl.business.security.AlfrescoSessionTicket;
import com.sourcesense.crl.business.service.rest.UserService;
import com.sourcesense.crl.util.URLBuilder;



@Service("userServiceManager")
public class UserServiceManager implements ServiceManager {

	@Autowired
	private  URLBuilder urlBuilder;
	
	@Autowired
	private UserService userService;
	
	
	
	public User authenticate(User user) {
		
		urlBuilder.setAlfrescoSessionTicket(userService.getAuthenticationToken(urlBuilder.buildURL("alfresco_context_url","alfresco_authentication"),user));
		
		User sessionUser = userService.completeAuthentication(urlBuilder.buildAlfrescoURL("alfresco_context_url","alf_gruppi_utente",null),user);
		
		
		if(urlBuilder.getAlfrescoSessionTicket()!=null){
			return sessionUser;
		}else{
			return null;
		}
		
	}
	
	
	
	
	
	@Override
	public boolean persist(Object object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean merge(Object object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Object object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<String, String> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
