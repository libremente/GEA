package com.sourcesense.crl.business.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import java.util.Date;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.sourcesense.crl.util.JsonDateSerializer;


@JsonRootName("allegato")
@JsonTypeName("allegato")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonIgnoreProperties(ignoreUnknown=true)
public class Allegato {
	
	private String nome;
	private String descrizione;
	private String downloadUrl;
	private boolean pubblico;
	private String tipoAllegato;
	private String id;
	private String mimetype;
	private Date dataSeduta;
	
	
	
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public boolean isPubblico() {
		return pubblico;
	}
	public void setPubblico(boolean pubblico) {
		this.pubblico = pubblico;
	}
	public String getTipoAllegato() {
		return tipoAllegato;
	}
	public void setTipoAllegato(String tipoAllegato) {
		this.tipoAllegato = tipoAllegato;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getMimetype() {
		return mimetype;
	}
	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataSeduta() {
		return dataSeduta;
	}
	public void setDataSeduta(Date dataSeduta) {
		this.dataSeduta = dataSeduta;
	}
	
	
	
	

}
