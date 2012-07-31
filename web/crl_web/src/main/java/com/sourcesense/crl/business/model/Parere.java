package com.sourcesense.crl.business.model;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.sourcesense.crl.util.JsonDateSerializer;


@JsonRootName("parere")
@JsonTypeName("parere")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public class Parere {
	
	private String organismoStatuario;
	private Date dataRichiesto;
	private Date dataRicevuto;
	private String esito;
	private Date dataRicezioneOrgano;
	private String note;
	public String getOrganismoStatuario() {
		return organismoStatuario;
	}
	public void setOrganismoStatuario(String organismoStatuario) {
		this.organismoStatuario = organismoStatuario;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataRichiesto() {
		return dataRichiesto;
	}
	public void setDataRichiesto(Date dataRichiesto) {
		this.dataRichiesto = dataRichiesto;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataRicevuto() {
		return dataRicevuto;
	}
	public void setDataRicevuto(Date dataRicevuto) {
		this.dataRicevuto = dataRicevuto;
	}
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataRicezioneOrgano() {
		return dataRicezioneOrgano;
	}
	public void setDataRicezioneOrgano(Date dataRicezioneOrgano) {
		this.dataRicezioneOrgano = dataRicezioneOrgano;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	

}
