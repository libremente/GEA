package com.sourcesense.crl.web.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.model.Collegamento;
import com.sourcesense.crl.business.service.AttoServiceManager;
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
	
	
	@PostConstruct
	protected void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));
		setAtto((Atto) attoBean.getAtto().clone());	
		
		setAttiCollegatiList(Clonator.cloneList(atto.getCollegamenti()));
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
				collegamento.setAtto(attoDaCollegare);
				attiCollegatiList.add(collegamento);
				
				//updateAbbinamentiHandler();
			}
		}
	}

	public void removeCollegamento() {

		for (Collegamento element : attiCollegatiList) {

			if (element.getAtto().getId().equals(collegamentoToDelete)) {

				attiCollegatiList.remove(element);
//				updateAbbinamentiHandler();
				break;
			}
		}
	}

	private boolean checkCollegamenti(String idAttoToAdd) {

		for (Collegamento element : attiCollegatiList) {

			if (element.getAtto().getId().equals(idAttoToAdd)) {

				return false;
			}
		}
		return true;
	}
	
	public void salvaCollegamenti() {
		atto.setCollegamenti(getAttiCollegatiList());
		//TODO: alfresco service
		
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));
		
		attoBean.getAtto().setCollegamenti(Clonator.cloneList(getAttiCollegatiList()));
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

	
	

	
	
	
}
