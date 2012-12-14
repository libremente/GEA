package com.sourcesense.crl.web.ui.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.model.Collegamento;
import com.sourcesense.crl.business.model.CollegamentoAttiSindacato;
import com.sourcesense.crl.business.model.CollegamentoLeggiRegionali;
import com.sourcesense.crl.business.service.AttoServiceManager;
import com.sourcesense.crl.util.CRLMessage;
import com.sourcesense.crl.util.Clonator;
import com.sourcesense.crl.web.ui.beans.AttoBean;

@ManagedBean(name = "collegamentiController")
@ViewScoped
public class CollegamentiController {
	
	@ManagedProperty(value = "#{attoServiceManager}")
	private AttoServiceManager attoServiceManager;
	
	private Atto atto = new Atto();
	
	private List<Collegamento> attiCollegatiList = new ArrayList<Collegamento>();
	private String collegamentoToDelete;	
	
	private Map<String, String> tipiAttoSindacato = new HashMap<String, String>();
	private Map<String, String> numeriAttoSindacato = new HashMap<String, String>();
	
	private List<CollegamentoAttiSindacato> collegamentiAttiSindacato = new ArrayList<CollegamentoAttiSindacato>();
	
	private String tipoAttoSindacato;
	private String numeroAttoSindacato;
	private String descrizioneAttoSindacato;
	private String attoSindacatoToDelete;
	
	private Map<String, String> tipiAttoRegionale = new HashMap<String, String>();
	private Map<String, String> numeriAttoRegionale = new HashMap<String, String>();
	
	private List<CollegamentoLeggiRegionali> collegamentiLeggiRegionali = new ArrayList<CollegamentoLeggiRegionali>();
	private String tipoAttoRegionale;
	private String numeroAttoRegionale;
	private String descrizioneAttoRegionale;
	private String attoRegionaleToDelete;
	
	
	private String statoCommitAttiInterni = CRLMessage.COMMIT_DONE;
	private String statoCommitAttiIndirizzo = CRLMessage.COMMIT_DONE;
	private String statoCommitLeggiRegionali = CRLMessage.COMMIT_DONE;
	
	@PostConstruct
	protected void init() {
		//TODO: alfresco service mappe
		
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));
		setAtto((Atto) attoBean.getAtto().clone());	
		
		setAttiCollegatiList(Clonator.cloneList(atto.getCollegamenti()));
		setCollegamentiAttiSindacato(attoServiceManager.findAllAttiSindacato());
		setCollegamentiLeggiRegionali(Clonator.cloneList(atto.getCollegamentiLeggiRegionali()));
	}
	
	
	public void updateAttiInterniHandler() {
		setStatoCommitAttiInterni(CRLMessage.COMMIT_UNDONE);
	}

	public void updateAttiIndirizzoHandler() {
		setStatoCommitAttiIndirizzo(CRLMessage.COMMIT_UNDONE);
	}

	public void updateLeggiRegionaliHandler() {
		setStatoCommitLeggiRegionali(CRLMessage.COMMIT_UNDONE);
	}
	
	
	public void changeTabHandler() {

		if (statoCommitAttiInterni.equals(CRLMessage.COMMIT_UNDONE)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Attenzione ! Le modifiche agli Atti Interni non sono state salvate ",
							""));
		}

		if (statoCommitAttiIndirizzo.equals(CRLMessage.COMMIT_UNDONE)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Attenzione ! Le modifiche ad Atti Indirizzo e Sindacato Ispettivo non sono state salvate ",
							""));
		}

		if (statoCommitLeggiRegionali.equals(CRLMessage.COMMIT_UNDONE)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Attenzione ! Le modifiche alle Leggi Regionali non sono state salvate ",
							""));
		}
	}
	
	// Atti Interni********************************************
	
	public void addCollegamento(String idAttoToAdd) {

		if (!idAttoToAdd.trim().equals("")) {
			if (!checkCollegamenti(idAttoToAdd)) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Attenzione ! Atto già abbinato ", ""));

			} else {
				Atto attoDaCollegare = attoServiceManager.findById(idAttoToAdd);
				Collegamento collegamento = new Collegamento();
				collegamento.setIdAttoCollegato(attoDaCollegare.getId());
				collegamento.setNumeroAttoCollegato(attoDaCollegare.getNumeroAtto());
				collegamento.setTipoAttoCollegato(attoDaCollegare.getTipoAtto());
				attiCollegatiList.add(collegamento);
				
				updateAttiInterniHandler();
			}
		}
	}

	public void removeCollegamento() {

		for (Collegamento element : attiCollegatiList) {

			if (element.getNumeroAttoCollegato().equals(collegamentoToDelete)) {

				attiCollegatiList.remove(element);
				updateAttiInterniHandler();
				break;
			}
		}
	}

	private boolean checkCollegamenti(String idAttoToAdd) {

		for (Collegamento element : attiCollegatiList) {

			if (element.getIdAttoCollegato().equals(idAttoToAdd)) {

				return false;
			}
		}
		return true;
	}
	
	public void salvaCollegamenti() {
		atto.setCollegamenti(getAttiCollegatiList());
		attoServiceManager.salvaCollegamenti(atto);
		
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));
		
		attoBean.getAtto().setCollegamenti(Clonator.cloneList(getAttiCollegatiList()));
		
		setStatoCommitAttiInterni(CRLMessage.COMMIT_DONE);
		context.addMessage(null, new FacesMessage("Atti Interni salvati con successo", ""));
	}
	
	// Atti Indirizzo e Sindacati******************************
	
	public void addCollegamentoAttoSindacato() {

		if (!numeroAttoSindacato.trim().equals("")) {
			if (!checkCollegamentiAttiSindacati()) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Attenzione ! Atto già collegato ", ""));

			} else {
				//TODO: alfresco service (link collegamento)
				CollegamentoAttiSindacato collegamento = new CollegamentoAttiSindacato();
				collegamento.setDescrizione(getDescrizioneAttoSindacato());
				collegamento.setNumeroAtto(getNumeroAttoSindacato());
				collegamento.setTipoAtto(getTipoAttoSindacato());
				collegamentiAttiSindacato.add(collegamento);
				
				updateAttiIndirizzoHandler();
			}
		}
	}

	public void removeCollegamentoAttoSindacato() {

		for (CollegamentoAttiSindacato element : collegamentiAttiSindacato) {

			if (element.getNumeroAtto().equals(attoSindacatoToDelete)) {

				collegamentiAttiSindacato.remove(element);
				updateAttiIndirizzoHandler();
				break;
			}
		}
	}

	private boolean checkCollegamentiAttiSindacati() {

		for (CollegamentoAttiSindacato element : collegamentiAttiSindacato) {

			if (element.getNumeroAtto().equals(numeroAttoSindacato)) {

				return false;
			}
		}
		return true;
	}
	
	public void salvaAttiIndirizzo() {
		atto.setCollegamentiAttiSindacato(getCollegamentiAttiSindacato());
		//TODO: alfresco service
		
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));
		
		attoBean.getAtto().setCollegamentiAttiSindacato(Clonator.cloneList(getCollegamentiAttiSindacato()));
		
		setStatoCommitAttiIndirizzo(CRLMessage.COMMIT_DONE);
		context.addMessage(null, new FacesMessage("Atti di indirizzo e di Sindacato Ispettivo salvati con successo", ""));
	}
	
	// Leggi regionali********************************************
	
	public void addCollegamentoAttoRegionale() {

		if (!numeroAttoRegionale.trim().equals("")) {
			if (!checkCollegamentiAttiRegionali()) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Attenzione ! Atto già collegato ", ""));

			} else {
				//TODO: alfresco service (link collegamento)
				CollegamentoLeggiRegionali collegamento = new CollegamentoLeggiRegionali();
				collegamento.setDescrizione(getDescrizioneAttoRegionale());
				collegamento.setNumeroAtto(getNumeroAttoRegionale());
				collegamento.setTipoAtto(getTipoAttoRegionale());
				collegamentiLeggiRegionali.add(collegamento);
				
				updateLeggiRegionaliHandler();
			}
		}
	}

	public void removeCollegamentoAttoRegionale() {

		for (CollegamentoLeggiRegionali element : collegamentiLeggiRegionali) {

			if (element.getNumeroAtto().equals(attoRegionaleToDelete)) {

				collegamentiLeggiRegionali.remove(element);
				updateLeggiRegionaliHandler();
				break;
			}
		}
	}

	private boolean checkCollegamentiAttiRegionali() {

		for (CollegamentoLeggiRegionali element : collegamentiLeggiRegionali) {

			if (element.getNumeroAtto().equals(numeroAttoRegionale)) {

				return false;
			}
		}
		return true;
	}
	
	public void salvaLeggiRegionali() {
		atto.setCollegamentiLeggiRegionali(getCollegamentiLeggiRegionali());
		//TODO: alfresco service
		
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));
		
		attoBean.getAtto().setCollegamentiLeggiRegionali(Clonator.cloneList(getCollegamentiLeggiRegionali()));
		
		setStatoCommitLeggiRegionali(CRLMessage.COMMIT_DONE);
		context.addMessage(null, new FacesMessage("Leggi Regionali salvate con successo", ""));
	}


	// Getters & Setters*****************************************

	public Atto getAtto() {
		return atto;
	}


	public void setAtto(Atto atto) {
		this.atto = atto;
	}


	public List<Collegamento> getAttiCollegatiList() {
		return attiCollegatiList;
	}


	public void setAttiCollegatiList(List<Collegamento> attiCollegatiList) {
		this.attiCollegatiList = attiCollegatiList;
	}


	public String getCollegamentoToDelete() {
		return collegamentoToDelete;
	}


	public void setCollegamentoToDelete(String collegamentoToDelete) {
		this.collegamentoToDelete = collegamentoToDelete;
	}


	public AttoServiceManager getAttoServiceManager() {
		return attoServiceManager;
	}


	public void setAttoServiceManager(AttoServiceManager attoServiceManager) {
		this.attoServiceManager = attoServiceManager;
	}


	public String getStatoCommitAttiInterni() {
		return statoCommitAttiInterni;
	}


	public void setStatoCommitAttiInterni(String statoCommitAttiInterni) {
		this.statoCommitAttiInterni = statoCommitAttiInterni;
	}


	public String getStatoCommitAttiIndirizzo() {
		return statoCommitAttiIndirizzo;
	}


	public void setStatoCommitAttiIndirizzo(String statoCommitAttiIndirizzo) {
		this.statoCommitAttiIndirizzo = statoCommitAttiIndirizzo;
	}


	public String getStatoCommitLeggiRegionali() {
		return statoCommitLeggiRegionali;
	}


	public void setStatoCommitLeggiRegionali(String statoCommitLeggiRegionali) {
		this.statoCommitLeggiRegionali = statoCommitLeggiRegionali;
	}


	public List<CollegamentoAttiSindacato> getCollegamentiAttiSindacato() {
		return collegamentiAttiSindacato;
	}


	public void setCollegamentiAttiSindacato(
			List<CollegamentoAttiSindacato> collegamentiAttiSindacato) {
		this.collegamentiAttiSindacato = collegamentiAttiSindacato;
	}


	public String getTipoAttoSindacato() {
		return tipoAttoSindacato;
	}


	public void setTipoAttoSindacato(String tipoAttoSindacato) {
		this.tipoAttoSindacato = tipoAttoSindacato;
	}


	public String getNumeroAttoSindacato() {
		return numeroAttoSindacato;
	}


	public void setNumeroAttoSindacato(String numeroAttoSindacato) {
		this.numeroAttoSindacato = numeroAttoSindacato;
	}


	public String getDescrizioneAttoSindacato() {
		return descrizioneAttoSindacato;
	}


	public void setDescrizioneAttoSindacato(String descrizioneAttoSindacato) {
		this.descrizioneAttoSindacato = descrizioneAttoSindacato;
	}


	public String getAttoSindacatoToDelete() {
		return attoSindacatoToDelete;
	}


	public void setAttoSindacatoToDelete(String attoSindacatoToDelete) {
		this.attoSindacatoToDelete = attoSindacatoToDelete;
	}


	public List<CollegamentoLeggiRegionali> getCollegamentiLeggiRegionali() {
		return collegamentiLeggiRegionali;
	}


	public void setCollegamentiLeggiRegionali(
			List<CollegamentoLeggiRegionali> collegamentiLeggiRegionali) {
		this.collegamentiLeggiRegionali = collegamentiLeggiRegionali;
	}


	public String getAttoRegionaleToDelete() {
		return attoRegionaleToDelete;
	}


	public void setAttoRegionaleToDelete(String attoRegionaleToDelete) {
		this.attoRegionaleToDelete = attoRegionaleToDelete;
	}


	public String getTipoAttoRegionale() {
		return tipoAttoRegionale;
	}


	public void setTipoAttoRegionale(String tipoAttoRegionale) {
		this.tipoAttoRegionale = tipoAttoRegionale;
	}


	public String getNumeroAttoRegionale() {
		return numeroAttoRegionale;
	}


	public void setNumeroAttoRegionale(String numeroAttoRegionale) {
		this.numeroAttoRegionale = numeroAttoRegionale;
	}


	public String getDescrizioneAttoRegionale() {
		return descrizioneAttoRegionale;
	}


	public void setDescrizioneAttoRegionale(String descrizioneAttoRegionale) {
		this.descrizioneAttoRegionale = descrizioneAttoRegionale;
	}


	public Map<String, String> getTipiAttoSindacato() {
		return tipiAttoSindacato;
	}


	public void setTipiAttoSindacato(Map<String, String> tipiAttoSindacato) {
		this.tipiAttoSindacato = tipiAttoSindacato;
	}


	public Map<String, String> getNumeriAttoSindacato() {
		return numeriAttoSindacato;
	}


	public void setNumeriAttoSindacato(Map<String, String> numeriAttoSindacato) {
		this.numeriAttoSindacato = numeriAttoSindacato;
	}


	public Map<String, String> getTipiAttoRegionale() {
		return tipiAttoRegionale;
	}


	public void setTipiAttoRegionale(Map<String, String> tipiAttoRegionale) {
		this.tipiAttoRegionale = tipiAttoRegionale;
	}


	public Map<String, String> getNumeriAttoRegionale() {
		return numeriAttoRegionale;
	}


	public void setNumeriAttoRegionale(Map<String, String> numeriAttoRegionale) {
		this.numeriAttoRegionale = numeriAttoRegionale;
	}
	
	
	
	
	

	
	
	
	
	
	
	

	
	

	
	
	
}
