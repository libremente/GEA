package com.sourcesense.crl.business.model;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.sourcesense.crl.util.JsonDateSerializer;

@JsonRootName("relatore")
@JsonTypeName("relatore")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@AutoProperty
public class Relatore extends Personale implements Cloneable{
	
	private String nome;
	private Date dataNomina;
	private Date dataUscita;
	private String cognomeNome;
	private Integer id_persona;
	
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
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataNomina() {
		return dataNomina;
	}
	public void setDataNomina(Date dataNomina) {
		this.dataNomina = dataNomina;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataUscita() {
		return dataUscita;
	}
	public void setId_persona(Integer id_persona) {
		this.id_persona = id_persona;
	}

	public int getId_persona() {
		return id_persona;
	}

	public void setDataUscita(Date dataUscita) {
		this.dataUscita = dataUscita;
	}

	public String getCognomeNome() {
		return cognomeNome;
	}

	public void setCognomeNome(String cognomeNome) {
		this.cognomeNome = cognomeNome;
	}
	
	

	

}
