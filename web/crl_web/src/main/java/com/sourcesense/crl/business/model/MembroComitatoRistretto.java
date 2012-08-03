package com.sourcesense.crl.business.model;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;

@JsonRootName("membroComitatoRistretto")
@JsonTypeName("membroComitatoRistretto")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public class MembroComitatoRistretto extends Personale {
	private String nome;
	private Date dataNomina;
	private Date dataUscita;
	private boolean coordinatore;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getDataNomina() {
		return dataNomina;
	}
	public void setDataNomina(Date dataNomina) {
		this.dataNomina = dataNomina;
	}
	public Date getDataUscita() {
		return dataUscita;
	}
	public void setDataUscita(Date dataUscita) {
		this.dataUscita = dataUscita;
	}
	public boolean isCoordinatore() {
		return coordinatore;
	}
	public void setCoordinatore(boolean coordinatore) {
		this.coordinatore = coordinatore;
	}
	
	

}