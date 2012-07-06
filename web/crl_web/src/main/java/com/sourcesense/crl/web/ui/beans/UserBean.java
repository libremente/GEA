package com.sourcesense.crl.web.ui.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.sourcesense.crl.business.model.User;




@ManagedBean(name = "userBean")
@SessionScoped
public class UserBean {
	
	
	
	private User user = new User();
	
	private String username;
	
	private String password;
	
	private String sessionToken;

	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}
	
	
	

}
