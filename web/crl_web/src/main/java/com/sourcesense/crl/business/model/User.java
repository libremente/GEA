package com.sourcesense.crl.business.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Configurable;


@Configurable
@XmlRootElement
public class User {

	private String username ;
	private String password ;
	
	private List <GruppoUtente> gruppi;
	
	
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
	public List<GruppoUtente> getGruppi() {
		return gruppi;
	}
	public void setGruppi(List<GruppoUtente> gruppi) {
		this.gruppi = gruppi;
	}
	
	
	
	
	
}
