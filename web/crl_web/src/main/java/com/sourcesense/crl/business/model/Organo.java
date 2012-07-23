package com.sourcesense.crl.business.model;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;

@JsonRootName("organo")
@JsonTypeName("organo")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public class Organo {
	
	private String nome;
	private Date dataSeduta;
	// link
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getDataSeduta() {
		return dataSeduta;
	}
	public void setDataSeduta(Date dataSeduta) {
		this.dataSeduta = dataSeduta;
	}
	
	

}
