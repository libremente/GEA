package com.sourcesense.crl.business.model;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@JsonRootName("collegamento")
@JsonTypeName("collegamento")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@AutoProperty
public class Collegamento implements Cloneable{
	//private Atto atto;
	private String idAttoCollegato;
	private String tipoAttoCollegato;
	private String numeroAttoCollegato;
	private String note;
	
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override public String toString() {
	    return Pojomatic.toString(this);
	 }
	
	
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

	public String getIdAttoCollegato() {
		return idAttoCollegato;
	}

	public void setIdAttoCollegato(String idAttoCollegato) {
		this.idAttoCollegato = idAttoCollegato;
	}

	public String getTipoAttoCollegato() {
		return tipoAttoCollegato;
	}

	public void setTipoAttoCollegato(String tipoAttoCollegato) {
		this.tipoAttoCollegato = tipoAttoCollegato;
	}

	public String getNumeroAttoCollegato() {
		return numeroAttoCollegato;
	}

	public void setNumeroAttoCollegato(String numeroAttoCollegato) {
		this.numeroAttoCollegato = numeroAttoCollegato;
	}
	
	
	

}
