package com.sourcesense.crl.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.pojomatic.annotations.AutoProperty;
import org.springframework.beans.factory.annotation.Configurable;

import com.sourcesense.crl.util.JsonDateSerializer;


@Configurable()
@XmlRootElement(name = "sedutaAtto")
@JsonRootName("sedutaAtto")
@JsonTypeName("sedutaAtto")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@AutoProperty
public class SedutaAtto implements Comparable<SedutaAtto>{
	
	
	
	private String idSeduta;
	private String nomeOrgano;
	private String ruolo;
	private Date dataSeduta;
	private List <Link> links = new ArrayList<Link>();
	private String numVerbale;
	
	
	@Override
	public int compareTo(SedutaAtto o) {
		return o.getDataSeduta().compareTo(this.dataSeduta);
	}

	
	public String getIdSeduta() {
		return idSeduta;
	}
	public void setIdSeduta(String idSeduta) {
		this.idSeduta = idSeduta;
	}
	public String getNomeOrgano() {
		return nomeOrgano;
	}
	public void setNomeOrgano(String nomeOrgano) {
		this.nomeOrgano = nomeOrgano;
	}
	public String getRuolo() {
		return ruolo;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	
	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getDataSeduta() {
		return dataSeduta;
	}
	public void setDataSeduta(Date dataSeduta) {
		this.dataSeduta = dataSeduta;
	}
	public List<Link> getLinks() {
		return links;
	}
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	public String getNumVerbale() {
		return numVerbale;
	}
	public void setNumVerbale(String numVerbale) {
		this.numVerbale = numVerbale;
	}
	
	
	
	
	

}
