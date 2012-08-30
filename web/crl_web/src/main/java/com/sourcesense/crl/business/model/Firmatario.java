package com.sourcesense.crl.business.model;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.sourcesense.crl.util.JsonDateSerializer;

@JsonRootName("firmatario")
@JsonTypeName("firmatario")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@AutoProperty
public class Firmatario extends Personale implements Cloneable {

	
	/*
	 * "firmatario" : 
	   {
	    "id" : "workspace://SpacesStore/fb6a588a-186c-4127-8be8-5d91926f8ede",
		"descrizione" : "Carlo Verdi",
		"primoFirmatario" : "true",
		"gruppoConsiliare" : "",
		"dataFirma" : "2012-07-25",
		"dataRitiro" : "2012-07-26"
	   }*/
	
	private String nome;
	private String gruppoConsiliare;
	private Date dataFirma;
	private Date dataRitiro;
	private boolean primoFirmatario;
	
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
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getGruppoConsiliare() {
		return gruppoConsiliare;
	}
	public void setGruppoConsiliare(String gruppoConsiliare) {
		this.gruppoConsiliare = gruppoConsiliare;
	}
	
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataFirma() {
		return dataFirma;
	}
	public void setDataFirma(Date dataFirma) {
		this.dataFirma = dataFirma;
	}
	
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataRitiro() {
		return dataRitiro;
	}
	public void setDataRitiro(Date dataRitiro) {
		this.dataRitiro = dataRitiro;
	}
	public boolean isPrimoFirmatario() {
		return primoFirmatario;
	}
	public void setPrimoFirmatario(boolean primoFirmatario) {
		this.primoFirmatario = primoFirmatario;
	}
	
	
	
	
}
