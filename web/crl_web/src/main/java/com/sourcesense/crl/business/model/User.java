package com.sourcesense.crl.business.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;
import org.springframework.beans.factory.annotation.Configurable;


@Configurable
@XmlRootElement
@AutoProperty
public class User {

	private String username ;
	private String password ;
	
	private List <GruppoUtente> gruppi;
	
	@Override public String toString() {
	    return Pojomatic.toString(this);
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
	public List<GruppoUtente> getGruppi() {
		return gruppi;
	}
	public void setGruppi(List<GruppoUtente> gruppi) {
		this.gruppi = gruppi;
	}
	
	
	
	
	
}
