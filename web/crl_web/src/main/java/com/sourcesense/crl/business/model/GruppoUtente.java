package com.sourcesense.crl.business.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;
import org.springframework.beans.factory.annotation.Configurable;


@Configurable
@XmlRootElement
@JsonRootName("gruppoUtente")
@JsonTypeName("gruppoUtente")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@AutoProperty
public class GruppoUtente {

	
	private String nome;

	@Override public String toString() {
	    return Pojomatic.toString(this);
	  }
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	
	
	
}
