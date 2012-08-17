package com.sourcesense.crl.web.ui.controller;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.web.ui.beans.AttoBean;

@ManagedBean(name = "chiusuraIterController")
@ViewScoped
public class ChiusuraIterController {
	
	private Atto atto = new Atto();
	
	private Date dataChiusura;
	private String tipoChiusura;
	private String note;
	private String numRiferimentoBurl;
	private Date dataBurl;
	private String numeroLr;
	private Date dataLr;

	@PostConstruct
	protected void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));
		setAtto((Atto) attoBean.getAtto().clone());
	}
	
	
	public void chiusuraAtto() {
		//TODO: alfresco service
		
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));
		
		attoBean.getAtto().setDataChiusura(getDataChiusura());
		attoBean.getAtto().setTipoChiusura(getTipoChiusura());
		attoBean.getAtto().setNoteChiusuraIter(getNote());
		attoBean.getAtto().setNumeroPubblicazioneBURL(getNumRiferimentoBurl());
		attoBean.getAtto().setDataPubblicazioneBURL(getDataBurl());
		attoBean.getAtto().setNumeroLr(getNumeroLr());
		attoBean.getAtto().setDataLR(getDataLr());
		
		context.addMessage(null, new FacesMessage("Atto chiuso con successo", ""));
	}
	

	//Getters & Setters**************************************
	
	public Atto getAtto() {
		return atto;
	}

	public void setAtto(Atto atto) {
		this.atto = atto;
	}

	public Date getDataChiusura() {
		return atto.getDataChiusura();
	}

	public void setDataChiusura(Date dataChiusura) {
		this.atto.setDataChiusura(dataChiusura);
	}

	public String getTipoChiusura() {
		return atto.getTipoChiusura();
	}

	public void setTipoChiusura(String tipoChiusura) {
		this.atto.setTipoChiusura(tipoChiusura);
	}

	public String getNote() {
		return atto.getNoteChiusuraIter();
	}

	public void setNote(String note) {
		this.atto.setNoteChiusuraIter(note);
	}

	public String getNumRiferimentoBurl() {
		return atto.getNumeroPubblicazioneBURL();
	}

	public void setNumRiferimentoBurl(String numRiferimentoBurl) {
		this.atto.setNumeroPubblicazioneBURL(numRiferimentoBurl);
	}

	public Date getDataBurl() {
		return atto.getDataPubblicazioneBURL();
	}

	public void setDataBurl(Date dataBurl) {
		this.atto.setDataPubblicazioneBURL(dataBurl);
	}

	public String getNumeroLr() {
		return atto.getNumeroLr();
	}

	public void setNumeroLr(String numeroLr) {
		this.atto.setNumeroLr(numeroLr);
	}

	public Date getDataLr() {
		return atto.getDataLR();
	}

	public void setDataLr(Date dataLr) {
		this.atto.setDataLR(dataLr);
	}
	
	
}
