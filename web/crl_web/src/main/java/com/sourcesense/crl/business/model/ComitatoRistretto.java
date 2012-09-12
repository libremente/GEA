package com.sourcesense.crl.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.sourcesense.crl.util.JsonDateSerializer;

@JsonRootName("comitatoRistretto")
@JsonTypeName("comitatoRistretto")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@AutoProperty
public class ComitatoRistretto {
	
	private List<MembroComitatoRistretto> componenti = new ArrayList<MembroComitatoRistretto>();
	private List<Allegato> testi = new ArrayList<Allegato>();
	private boolean presenzaComitatoRistretto;
	private Date dataIstituzioneComitato;
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

	public boolean isPresenzaComitatoRistretto() {
		return presenzaComitatoRistretto;
	}

	public void setPresenzaComitatoRistretto(boolean presenzaComitatoRistretto) {
		this.presenzaComitatoRistretto = presenzaComitatoRistretto;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataIstituzioneComitato() {
		return dataIstituzioneComitato;
	}

	public void setDataIstituzioneComitato(Date dataIstituzioneComitato) {
		this.dataIstituzioneComitato = dataIstituzioneComitato;
	}
	
	
	

}
