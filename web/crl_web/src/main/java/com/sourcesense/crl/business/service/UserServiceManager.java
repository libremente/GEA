package com.sourcesense.crl.business.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.sourcesense.crl.business.model.User;
import com.sourcesense.crl.business.security.AlfrescoSessionTicket;
import com.sourcesense.crl.business.service.rest.UserService;



@Service("userServiceManager")
public class UserServiceManager implements ServiceManager {

	@Autowired
	private UserService userService;

	
	public AlfrescoSessionTicket authenticate(User user) {
		// TODO Auto-generated method stub
		return userService.getAuthenticationToken(user);
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
