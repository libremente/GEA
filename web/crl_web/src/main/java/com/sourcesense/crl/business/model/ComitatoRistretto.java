package com.sourcesense.crl.business.model;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@JsonRootName("comitatoRistretto")
@JsonTypeName("comitatoRistretto")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@AutoProperty
public class ComitatoRistretto {
	
	private List<MembroComitatoRistretto> componenti = new ArrayList<MembroComitatoRistretto>();
	private List<Allegato> testi = new ArrayList<Allegato>();
	
	@Override public String toString() {
	    return Pojomatic.toString(this);
	 }
	
	public List<MembroComitatoRistretto> getComponenti() {
		return componenti;
	}
	public void setComponenti(List<MembroComitatoRistretto> componenti) {
		this.componenti = componenti;
	}
	public List<Allegato> getTesti() {
		return testi;
	}
	public void setTesti(List<Allegato> testi) {
		this.testi = testi;
	}
	
	
	

}
