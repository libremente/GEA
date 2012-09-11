package com.sourcesense.crl.business.model;

import java.util.ArrayList;
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

	private List <GruppoUtente> gruppi = new ArrayList<GruppoUtente>();

	private List <Seduta> sedute = new ArrayList<Seduta>();
	
	private GruppoUtente sessionGroup = new GruppoUtente();

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

	public List <Seduta> getSedute() {
		return sedute;
	}

	public void setSedute(List <Seduta> sedute) {
		this.sedute = sedute;
	}

	public GruppoUtente getSessionGroup() {
		return sessionGroup;
	}

	public void setSessionGroup(GruppoUtente sessionGroup) {
		this.sessionGroup = sessionGroup;
	}





}
