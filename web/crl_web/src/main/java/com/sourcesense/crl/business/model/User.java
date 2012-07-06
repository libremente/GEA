package com.sourcesense.crl.business.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Configurable;


@Configurable
@XmlRootElement
public class User {

	private String username ;
	private String password ;
	
	
	
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
	
	
	
	
	
}
