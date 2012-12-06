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
public class Firmatario implements Cloneable ,Comparable <Firmatario>{

	
	private String nome;
	private String gruppoConsiliare;
	private Date dataFirma;
	private Date dataRitiro;
	private boolean primoFirmatario;
	private String numeroOrdinamento;
	private String id;
	private String descrizione;
	
	
	@Override public String toString() {
	    return Pojomatic.toString(this);
	  }
	
	@Override
	public int compareTo(Firmatario o) {
		return Integer.parseInt(this.numeroOrdinamento) -  Integer.parseInt(o.numeroOrdinamento);
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

	public String getNumeroOrdinamento() {
		return numeroOrdinamento;
	}

	public void setNumeroOrdinamento(String numeroOrdinamento) {
		this.numeroOrdinamento = numeroOrdinamento;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	
	
	
	
	
	
	
}
