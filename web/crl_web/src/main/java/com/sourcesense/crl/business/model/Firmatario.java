package com.sourcesense.crl.business.model;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;

@JsonRootName("firmatario")
@JsonTypeName("firmatario")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public class Firmatario extends Personale {

	private String nome;
	private String gruppoConsiliare;
	private Date dataFirma;
	private Date dataRitiro;
	private boolean primoFirmatario;
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
	public Date getDataFirma() {
		return dataFirma;
	}
	public void setDataFirma(Date dataFirma) {
		this.dataFirma = dataFirma;
	}
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
