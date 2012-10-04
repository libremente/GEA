package com.sourcesense.crl.web.ui.controller;

import com.sourcesense.crl.business.model.Allegato;
import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.model.AttoEAC;
import com.sourcesense.crl.business.model.CollegamentoAttiSindacato;
import com.sourcesense.crl.business.service.AttoServiceManager;
import com.sourcesense.crl.business.service.LegislaturaServiceManager;
import com.sourcesense.crl.business.service.TipoAttoServiceManager;

import com.sourcesense.crl.util.CRLMessage;
import com.sourcesense.crl.web.ui.beans.AttoBean;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.FileUploadEvent;

/**
 * 
 * @author uji
 */

@ManagedBean(name = "inserisciEACController")
@ViewScoped
public class InserisciEACController {

	@ManagedProperty(value = "#{attoServiceManager}")
	private AttoServiceManager attoServiceManager;

	@ManagedProperty(value = "#{legislaturaServiceManager}")
	private LegislaturaServiceManager legislaturaServiceManager;
	                          
	@ManagedProperty(value = "#{tipoAttoServiceManager}")
	private  TipoAttoServiceManager tipoAttoServiceManager;
	
	private Map<String, String> tipiAttoSindacato = new HashMap<String, String>();
	private Map<String, String> numeriAttoSindacato = new HashMap<String, String>();
	
	
	private AttoEAC atto = new AttoEAC();

	private String numeroAtto;
	
	private String noteAtto;
	
	private Date dataAtto;
	
	
	private String currentFilePubblico;
	
	private List<Allegato> allegatiEAC = new ArrayList<Allegato>();
	
	
	private String allegatoEACToDelete;
	
	private List<CollegamentoAttiSindacato> collegamentiAttiSindacato = new ArrayList<CollegamentoAttiSindacato>();
	private String tipoAttoSindacato;
	private String numeroAttoSindacato;
	private String descrizioneAttoSindacato;
	private String attoSindacatoToDelete;
	
	
	@PostConstruct
	private void initializeValues(){
		
		
		
	}
	
    
	public String inserisciAtto() {

		atto.setStato("EAC");
		
		Atto attoRet = attoServiceManager.persist(atto);
		
		if (attoRet!=null && attoRet.getError()==null) {

			FacesContext context = FacesContext.getCurrentInstance();
			AttoBean attoBean = (AttoBean) context
					.getApplication()
					.getExpressionFactory()
					.createValueExpression(context.getELContext(),
							"#{attoBean}", AttoBean.class)
					.getValue(context.getELContext());

			attoBean.setAtto(attoRet);
			return "pretty:Riepilogo_Atto";

		} else if (attoRet!=null && attoRet.getError()!=null && !attoRet.getError().equals("")) {
			
			
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, attoRet.getError(), ""));
			return null;
			
		}else {

		    return null;

		}
	}
	
	
	
	public void uploadAllegatoParere(FileUploadEvent event) {

		// TODO Service logic
		String fileName = event.getFile().getFileName();

		if (!checkAllegatoParere(fileName)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Attenzione ! Il file "
							+ fileName + " è già stato allegato ", ""));
		} else {

			// TODO Alfresco upload
			Allegato allegatoRet = null;

			try {
				allegatoRet = attoServiceManager
						.uploadAllegatoEAC(
								((AttoBean) FacesContext.getCurrentInstance()
										.getExternalContext().getSessionMap()
										.get("attoBean")).getAtto(), event
										.getFile().getInputstream(), event
										.getFile().getFileName());
			} catch (IOException e) {
				e.printStackTrace();
			}

			
			allegatiEAC.add(allegatoRet);
		}
	}

	private boolean checkAllegatoParere(String fileName) {

		for (Allegato element : allegatiEAC) {

			if (element.getDescrizione().equals(fileName)) {

				return false;
			}

		}

		return true;
	}

	public void removeAllegatoParere() {

		for (Allegato element : allegatiEAC) {

			if (element.getId().equals(allegatoEACToDelete)) {

				// TODO Alfresco delete
				allegatiEAC.remove(element);
				break;
			}
		}
	}
	
	
	
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
				
				
			}
		}
	}

	public void removeCollegamentoAttoSindacato() {

		for (CollegamentoAttiSindacato element : collegamentiAttiSindacato) {

			if (element.getNumeroAtto().equals(attoSindacatoToDelete)) {

				collegamentiAttiSindacato.remove(element);
				
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
	
	

	public AttoServiceManager getAttoServiceManager() {
		return attoServiceManager;
	}

	public void setAttoServiceManager(AttoServiceManager attoServiceManager) {
		this.attoServiceManager = attoServiceManager;
	}

	

	


	public LegislaturaServiceManager getLegislaturaServiceManager() {
		return legislaturaServiceManager;
	}


	public void setLegislaturaServiceManager(
			LegislaturaServiceManager legislaturaServiceManager) {
		this.legislaturaServiceManager = legislaturaServiceManager;
	}


	public TipoAttoServiceManager getTipoAttoServiceManager() {
		return tipoAttoServiceManager;
	}


	public void setTipoAttoServiceManager(TipoAttoServiceManager tipoAttoServiceManager) {
		this.tipoAttoServiceManager = tipoAttoServiceManager;
	}

	
	
	public AttoEAC getAtto() {
		return atto;
	}

	public void setAtto(AttoEAC atto) {
		this.atto = atto;
	}
	
	public String getNumeroAtto() {
		return this.atto.getNumeroAtto();
	}

	public void setNumeroAtto(String numeroAtto) {
		this.atto.setNumeroAtto(numeroAtto);
	}
	
	public String getNoteAtto() {
		return this.atto.getNote();
	}


	public void setNoteAtto(String noteAtto) {
		this.atto.setNote(noteAtto);
	}


	public Date getDataAtto() {
		return this.atto.getDataAtto();
	}


	public void setDataAtto(Date dataAtto) {
		this.atto.setDataAtto(dataAtto);
	}


	public List<Allegato> getAllegatiEAC() {
		return allegatiEAC;
	}


	public void setAllegatiEAC(List<Allegato> allegatiEAC) {
		this.allegatiEAC = allegatiEAC;
	}


	public String getAllegatoEACToDelete() {
		return allegatoEACToDelete;
	}

	public void setAllegatoEACToDelete(String allegatoParereToDelete) {
		this.allegatoEACToDelete = allegatoEACToDelete;
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


	public String getCurrentFilePubblico() {
		return currentFilePubblico;
	}


	public void setCurrentFilePubblico(String currentFilePubblico) {
		this.currentFilePubblico = currentFilePubblico;
	}


	
}
