package com.sourcesense.crl.web.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

import com.sourcesense.crl.business.model.Allegato;
import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.model.Parere;
import com.sourcesense.crl.business.service.AttoServiceManager;
import com.sourcesense.crl.util.CRLMessage;
import com.sourcesense.crl.web.ui.beans.AttoBean;

@ManagedBean(name = "consultazionePareriController")
@ViewScoped
public class ConsultazionePareriController {
	
	@ManagedProperty(value = "#{attoServiceManager}")
	private AttoServiceManager attoServiceManager;

	private Atto atto = new Atto();

	private List<Parere> pareriList = new ArrayList<Parere>();

	private Parere parereSelected = new Parere();
	private String descrizioneParereSelected;
	
	private Date dataRicezioneParere;
	private Date dataRicezioneOrgano;
	private String esito;
	private String noteParere;
	
	private List<Allegato> allegatiParereList = new ArrayList<Allegato>();
	private String allegatoParereToDelete;
	
	
	private String statoCommitPareri = CRLMessage.COMMIT_DONE;
	private String statoCommitConsultazioni = CRLMessage.COMMIT_DONE;


	@PostConstruct
	protected void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));
		setAtto((Atto) attoBean.getAtto().clone());	

		setPareriList(atto.getPareri());

		if(pareriList.get(0)!=null) {
			setDescrizioneParereSelected(pareriList.get(0).getDescrizione());
			showParereDetail();
		}
	}
	
	public void updatePareriHandler() {
		setStatoCommitPareri(CRLMessage.COMMIT_UNDONE);
	}

	public void updateConsultazioniHandler() {
		setStatoCommitConsultazioni(CRLMessage.COMMIT_UNDONE);
	}
	
	
	public void changeTabHandler() {

		if (statoCommitPareri.equals(CRLMessage.COMMIT_UNDONE)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Attenzione ! Le modifiche ai Pareri non sono state salvate ",
							""));
		}

		if (statoCommitConsultazioni.equals(CRLMessage.COMMIT_UNDONE)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Attenzione ! Le modifiche alle Consultazioni non sono state salvate ",
							""));
		}
	}

	// Pareri********************************************************

	public void showParereDetail() {
		setParereSelected(findParere(descrizioneParereSelected));

		if(parereSelected!=null) {
			setDataRicezioneParere(parereSelected.getDataRicezioneParere());
			setDataRicezioneOrgano(parereSelected.getDataRicezioneOrgano());
			setEsito(parereSelected.getEsito());
			setNoteParere(parereSelected.getNote());
			setAllegatiParereList(parereSelected.getAllegati());
		}
	}

	private Parere findParere(String descrizione) {
		for(Parere element : pareriList) {
			if(element.getDescrizione().equals(descrizione)) {
				return element;
			}
		}
		return null;
	}
	
	public void uploadAllegato(FileUploadEvent event) {
		//TODO
	}
	
	
	public void salvaParere() {
		parereSelected.setDataRicezioneOrgano(getDataRicezioneOrgano());
		parereSelected.setDataRicezioneParere(getDataRicezioneParere());
		parereSelected.setEsito(getEsito());
		parereSelected.setNote(getNoteParere());
		//parereSelected.setAllegati
		
		atto.setPareri(pareriList);
		
		attoServiceManager.salvaPareri(atto);
		
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));
		
		attoBean.getAtto().setPareri(pareriList);
		
		setStatoCommitPareri(CRLMessage.COMMIT_DONE);
		context.addMessage(null, new FacesMessage("Pareri salvati con successo", ""));
	}




	// Getters & Setters**********************************************

	public Atto getAtto() {
		return atto;
	}
	public void setAtto(Atto atto) {
		this.atto = atto;
	}
	public List<Parere> getPareriList() {
		return pareriList;
	}
	public void setPareriList(List<Parere> pareriList) {
		this.pareriList = pareriList;
	}
	public Parere getParereSelected() {
		return parereSelected;
	}
	public void setParereSelected(Parere parereSelected) {
		this.parereSelected = parereSelected;
	}
	public Date getDataRicezioneParere() {
		return dataRicezioneParere;
	}
	public void setDataRicezioneParere(Date dataRicezioneParere) {
		this.dataRicezioneParere = dataRicezioneParere;
	}
	public Date getDataRicezioneOrgano() {
		return dataRicezioneOrgano;
	}
	public void setDataRicezioneOrgano(Date dataRicezioneOrgano) {
		this.dataRicezioneOrgano = dataRicezioneOrgano;
	}
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	
	public AttoServiceManager getAttoServiceManager() {
		return attoServiceManager;
	}

	public void setAttoServiceManager(AttoServiceManager attoServiceManager) {
		this.attoServiceManager = attoServiceManager;
	}

	public String getNoteParere() {
		return noteParere;
	}

	public void setNoteParere(String noteParere) {
		this.noteParere = noteParere;
	}

	public String getDescrizioneParereSelected() {
		return descrizioneParereSelected;
	}

	public void setDescrizioneParereSelected(String descrizioneParereSelected) {
		this.descrizioneParereSelected = descrizioneParereSelected;
	}

	public List<Allegato> getAllegatiParereList() {
		return allegatiParereList;
	}

	public void setAllegatiParereList(List<Allegato> allegatiParereList) {
		this.allegatiParereList = allegatiParereList;
	}

	public String getAllegatoParereToDelete() {
		return allegatoParereToDelete;
	}

	public void setAllegatoParereToDelete(String allegatoParereToDelete) {
		this.allegatoParereToDelete = allegatoParereToDelete;
	}

	public String getStatoCommitPareri() {
		return statoCommitPareri;
	}

	public void setStatoCommitPareri(String statoCommitPareri) {
		this.statoCommitPareri = statoCommitPareri;
	}

	public String getStatoCommitConsultazioni() {
		return statoCommitConsultazioni;
	}

	public void setStatoCommitConsultazioni(String statoCommitConsultazioni) {
		this.statoCommitConsultazioni = statoCommitConsultazioni;
	}
	
	


}
