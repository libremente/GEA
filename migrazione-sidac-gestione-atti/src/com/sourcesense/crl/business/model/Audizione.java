package com.sourcesense.crl.business.model;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@JsonRootName("audizione")
@JsonTypeName("audizione")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@AutoProperty
public class Audizione implements Cloneable{

	private String soggettoPartecipante;
	private boolean previsto;
	private boolean discusso;
	
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

	public String getSoggettoPartecipante() {
		return soggettoPartecipante;
	}

	public void setSoggettoPartecipante(String soggettoPartecipante) {
		this.soggettoPartecipante = soggettoPartecipante;
	}

	public boolean isPrevisto() {
		return previsto;
	}

	public void setPrevisto(boolean previsto) {
		this.previsto = previsto;
	}

	public boolean isDiscusso() {
		return discusso;
	}

	public void setDiscusso(boolean discusso) {
		this.discusso = discusso;
	}
	
	
}
