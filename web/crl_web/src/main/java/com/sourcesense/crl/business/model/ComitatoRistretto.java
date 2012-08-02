package com.sourcesense.crl.business.model;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;

@JsonRootName("comitatoRistretto")
@JsonTypeName("comitatoRistretto")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public class ComitatoRistretto {
	
	private List<Personale> componenti = new ArrayList<Personale>();
	private List<Allegato> testi = new ArrayList<Allegato>();
	public List<Personale> getComponenti() {
		return componenti;
	}
	public void setComponenti(List<Personale> componenti) {
		this.componenti = componenti;
	}
	public List<Allegato> getTesti() {
		return testi;
	}
	public void setTesti(List<Allegato> testi) {
		this.testi = testi;
	}
	
	
	

}
