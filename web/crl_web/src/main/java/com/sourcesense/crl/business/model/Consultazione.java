package com.sourcesense.crl.business.model;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;


@JsonRootName("consultazione")
@JsonTypeName("consultazione")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public class Consultazione {
	
	private Date dataConsultazione;
	private String soggettiConsultati;
	private boolean prevista;
	private boolean discussa;
	private Date dataSeduta;
	private String note;
	private List <Personale> soggettiInvitati;
	public Date getDataConsultazione() {
		return dataConsultazione;
	}
	public void setDataConsultazione(Date dataConsultazione) {
		this.dataConsultazione = dataConsultazione;
	}
	public String getSoggettiConsultati() {
		return soggettiConsultati;
	}
	public void setSoggettiConsultati(String soggettiConsultati) {
		this.soggettiConsultati = soggettiConsultati;
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
	public List<Personale> getSoggettiInvitati() {
		return soggettiInvitati;
	}
	public void setSoggettiInvitati(List<Personale> soggettiInvitati) {
		this.soggettiInvitati = soggettiInvitati;
	}
	
	

}
