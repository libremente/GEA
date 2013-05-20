package com.sourcesense.crl.business.model;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.sourcesense.crl.util.JsonDateSerializer;

@JsonRootName("organo")
@JsonTypeName("organo")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@AutoProperty
public class Organo {
	
	private String nome;
	private Date dataSeduta;
	private Allegato ODG;
	private List<Allegato> risorseEsterne;
	
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
	public Date getDataSeduta() {
		return dataSeduta;
	}
	public void setDataSeduta(Date dataSeduta) {
		this.dataSeduta = dataSeduta;
	}
	public Allegato getODG() {
		return ODG;
	}
	public void setODG(Allegato oDG) {
		ODG = oDG;
	}
	public List<Allegato> getRisorseEsterne() {
		return risorseEsterne;
	}
	public void setRisorseEsterne(List<Allegato> risorseEsterne) {
		this.risorseEsterne = risorseEsterne;
	}
	
	
	

}
