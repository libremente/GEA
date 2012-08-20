package com.sourcesense.crl.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.sourcesense.crl.util.JsonDateSerializer;


@JsonRootName("consultazione")
@JsonTypeName("consultazione")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public class Consultazione {
	
	private String descrizione;
	private Date dataConsultazione;
	private boolean prevista;
	private boolean discussa;
	private Date dataSeduta;
	private String note;
	
	private List <SoggettoInvitato> soggettiInvitati = new ArrayList<SoggettoInvitato>();
	private List<Allegato> allegati = new ArrayList<Allegato>();
	
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataConsultazione() {
		return dataConsultazione;
	}
	public void setDataConsultazione(Date dataConsultazione) {
		this.dataConsultazione = dataConsultazione;
	}
	public boolean isPrevista() {
		return prevista;
	}
	public void setPrevista(boolean prevista) {
		this.prevista = prevista;
	}
	public boolean isDiscussa() {
		return discussa;
	}
	public void setDiscussa(boolean discussa) {
		this.discussa = discussa;
	}
	
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataSeduta() {
		return dataSeduta;
	}
	public void setDataSeduta(Date dataSeduta) {
		this.dataSeduta = dataSeduta;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public List<SoggettoInvitato> getSoggettiInvitati() {
		return soggettiInvitati;
	}
	public void setSoggettiInvitati(List<SoggettoInvitato> soggettiInvitati) {
		this.soggettiInvitati = soggettiInvitati;
	}
	public List<Allegato> getAllegati() {
		return allegati;
	}
	public void setAllegati(List<Allegato> allegati) {
		this.allegati = allegati;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	

}
