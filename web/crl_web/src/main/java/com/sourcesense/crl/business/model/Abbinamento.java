package com.sourcesense.crl.business.model;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.sourcesense.crl.util.JsonDateSerializer;

@JsonRootName("abbinamento")
@JsonTypeName("abbinamento")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@AutoProperty
public class Abbinamento implements Cloneable{
	private String idAtto;
	private String idAttoAbbinato;
	private String tipoTesto;
	private Date dataAbbinamento;
	private Date dataDisabbinamento;
	private String note;
	private boolean abbinato;
	
	private String tipoAttoAbbinato;
	private String numeroAttoAbbinato;
	
	
		
	
	//private Atto atto;
	
	@Override public String toString() {
	    return Pojomatic.toString(this);
	 }
	
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*public Atto getAtto() {
		return atto;
	}
	public void setAtto(Atto atto) {
		this.atto = atto;
	}*/
	
	
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataAbbinamento() {
		return dataAbbinamento;
	}
	public void setDataAbbinamento(Date dataAbbinamento) {
		this.dataAbbinamento = dataAbbinamento;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
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

	public String getIdAtto() {
		return idAtto;
	}

	public void setIdAtto(String idAtto) {
		this.idAtto = idAtto;
	}

	public String getIdAttoAbbinato() {
		return idAttoAbbinato;
	}

	public void setIdAttoAbbinato(String idAttoAbbinato) {
		this.idAttoAbbinato = idAttoAbbinato;
	}

	public String getTipoAttoAbbinato() {
		return tipoAttoAbbinato;
	}

	public void setTipoAttoAbbinato(String tipoAttoAbbinato) {
		this.tipoAttoAbbinato = tipoAttoAbbinato;
	}

	public String getNumeroAttoAbbinato() {
		return numeroAttoAbbinato;
	}

	public void setNumeroAttoAbbinato(String numeroAttoAbbinato) {
		this.numeroAttoAbbinato = numeroAttoAbbinato;
	}

	
	
	
	

}
