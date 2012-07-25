package com.sourcesense.crl.business.model;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;


@JsonRootName("link")
@JsonTypeName("link")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public class Link {
	
	private String descrizione;
	private String collegamentoUrl;
	private boolean pubblico;
	
	
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getCollegamentoUrl() {
		return collegamentoUrl;
	}
	public void setCollegamentoUrl(String collegamentoUrl) {
		this.collegamentoUrl = collegamentoUrl;
	}
	public boolean isPubblico() {
		return pubblico;
	}
	public void setPubblico(boolean pubblico) {
		this.pubblico = pubblico;
	}
	
	

}
