package com.sourcesense.crl.business.model;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@JsonRootName("abbinamento")
@JsonTypeName("abbinamento")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@AutoProperty
public class Abbinamento {
	private Atto atto;
	private Date dataAbbinamento;
	private Date dataDisabbinamento;
	private String tipoTesto;
	private String note;
	private boolean abbinato;
	
	@Override public String toString() {
	    return Pojomatic.toString(this);
	 }
	
	public Atto getAtto() {
		return atto;
	}
	public void setAtto(Atto atto) {
		this.atto = atto;
	}
	public Date getDataAbbinamento() {
		return dataAbbinamento;
	}
	public void setDataAbbinamento(Date dataAbbinamento) {
		this.dataAbbinamento = dataAbbinamento;
	}
	public Date getDataDisabbinamento() {
		return dataDisabbinamento;
	}
	public void setDataDisabbinamento(Date dataDisabbinamento) {
		this.dataDisabbinamento = dataDisabbinamento;
	}
	public String getTipoTesto() {
		return tipoTesto;
	}
	public void setTipoTesto(String tipoTesto) {
		this.tipoTesto = tipoTesto;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public boolean isAbbinato() {
		return abbinato;
	}
	public void setAbbinato(boolean abbinato) {
		this.abbinato = abbinato;
	}
	
	

}
