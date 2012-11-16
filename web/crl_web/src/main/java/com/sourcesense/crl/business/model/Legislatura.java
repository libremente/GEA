package com.sourcesense.crl.business.model;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;


@JsonRootName("legislatura")
@JsonTypeName("legislatura")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@AutoProperty
public class Legislatura {
	
	private String nome;
	private boolean corrente;
	private Date dataInizioLegislatura;
	private Date dataFineLegislatura;
	
	@Override public String toString() {
	    return Pojomatic.toString(this);
	  }
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isCorrente() {
		return corrente;
	}

	public void setCorrente(boolean corrente) {
		this.corrente = corrente;
	}

	public Date getDataInizioLegislatura() {
		return dataInizioLegislatura;
	}

	public void setDataInizioLegislatura(Date dataInizioLegislatura) {
		this.dataInizioLegislatura = dataInizioLegislatura;
	}

	public Date getDataFineLegislatura() {
		return dataFineLegislatura;
	}

	public void setDataFineLegislatura(Date dataFineLegislatura) {
		this.dataFineLegislatura = dataFineLegislatura;
	}

	
	

		
	
	

}
