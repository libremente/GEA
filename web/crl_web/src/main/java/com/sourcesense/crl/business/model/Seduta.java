package com.sourcesense.crl.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.sourcesense.crl.util.JsonDateSerializer;
import com.sourcesense.crl.util.JsonHourSerializer;

@JsonRootName("seduta")
@JsonTypeName("seduta")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@AutoProperty
public class Seduta implements Cloneable,Comparable<Seduta>{

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
	
	private String legislatura;
	private Date dalleOre;
	private Date alleOre;
	private String idSeduta;
	private Date dataSeduta;
	private String numVerbale;
	private String note;
	private List<Link> links = new ArrayList<Link>();

	private List<AttoTrattato> attiTrattati = new ArrayList<AttoTrattato>();	
	private List<Consultazione> consultazioniAtti = new ArrayList<Consultazione>();	
	private List<Audizione> audizioni = new ArrayList<Audizione>();	
	private List<CollegamentoAttiSindacato> attiSindacato = new ArrayList<CollegamentoAttiSindacato>();
	
	private List<Allegato> odgList = new ArrayList<Allegato>();
	private List<Allegato> verbaliList = new ArrayList<Allegato>();

	@JsonSerialize(using=JsonDateSerializer.class) 
	public Date getDataSeduta() {
		return dataSeduta;
	}
	
	@Override
	public int compareTo(Seduta o) {
		
		return o.getDataSeduta().compareTo(this.dataSeduta);
		
	}
	
	public void setDataSeduta(Date dataSeduta) {
		this.dataSeduta = dataSeduta;
	}
	public String getNumVerbale() {
		return numVerbale;
	}
	public void setNumVerbale(String numVerbale) {
		this.numVerbale = numVerbale;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public List<Link> getLinks() {
		return links;
	}
	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public List<AttoTrattato> getAttiTrattati() {
		return attiTrattati;
	}

	public void setAttiTrattati(List<AttoTrattato> attiTrattati) {
		this.attiTrattati = attiTrattati;
	}

	public List<Consultazione> getConsultazioniAtti() {
		
		return consultazioniAtti;
	}

	public void setConsultazioniAtti(List<Consultazione> consultazioniAtti) {
		this.consultazioniAtti = consultazioniAtti;
	}

	public List<Audizione> getAudizioni() {
		return audizioni;
	}

	public void setAudizioni(List<Audizione> audizioni) {
		this.audizioni = audizioni;
	}

	public List<CollegamentoAttiSindacato> getAttiSindacato() {
		return attiSindacato;
	}

	public void setAttiSindacato(List<CollegamentoAttiSindacato> attiSindacato) {
		this.attiSindacato = attiSindacato;
	}

	public String getIdSeduta() {
		return idSeduta;
	}

	public void setIdSeduta(String idSeduta) {
		this.idSeduta = idSeduta;
	}

	
	@JsonSerialize(using=JsonHourSerializer.class)
	public Date getDalleOre() {
		return dalleOre;
	}
    
	@JsonSerialize(using=JsonHourSerializer.class)
	public void setDalleOre(Date dalleOre) {
		this.dalleOre = dalleOre;
	}

	@JsonSerialize(using=JsonHourSerializer.class)
	public Date getAlleOre() {
		return alleOre;
	}

	@JsonSerialize(using=JsonHourSerializer.class)
	public void setAlleOre(Date alleOre) {
		this.alleOre = alleOre;
	}

	public List<Allegato> getOdgList() {
		return odgList;
	}

	public void setOdgList(List<Allegato> odgList) {
		this.odgList = odgList;
	}

	public List<Allegato> getVerbaliList() {
		return verbaliList;
	}

	public void setVerbaliList(List<Allegato> verbaliList) {
		this.verbaliList = verbaliList;
	}

	public String getLegislatura() {
		return legislatura;
	}

	public void setLegislatura(String legislatura) {
		this.legislatura = legislatura;
	}

	
}