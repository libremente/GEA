package com.sourcesense.crl.web.ui.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.sourcesense.crl.business.model.GruppoUtente;
import com.sourcesense.crl.business.model.User;




@ManagedBean(name = "userBean")
@SessionScoped
public class UserBean implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7726122743894534255L;

	private User user = new User();
	
	private String username;
	
	private String password;
	
	private String sessionToken;

	
	@PostConstruct
	public void init(){
		
		GruppoUtente g1 = new GruppoUtente();
		g1.setNome("Commissione2");
		this.getUser().setSessionGroup(g1);
	}
	
	
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUsername() {
		return user.getUsername();
	}

	public void setUsername(String username) {
		this.user.setUsername(username);
	}

	public String getPassword() {
		return user.getPassword();
	}

	public void setPassword(String password) {
		this.user.setPassword(password);
	}

	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}
	
	
	

}
