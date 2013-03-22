package com.sourcesense.crl.web.ui.controller;

import com.sourcesense.crl.business.model.Allegato;
import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.model.AttoMIS;
import com.sourcesense.crl.business.model.Relatore;
import com.sourcesense.crl.business.model.StatoAtto;
import com.sourcesense.crl.business.service.AttoRecordServiceManager;
import com.sourcesense.crl.business.service.AttoServiceManager;
import com.sourcesense.crl.business.service.CommissioneServiceManager;
import com.sourcesense.crl.business.service.LegislaturaServiceManager;
import com.sourcesense.crl.business.service.PersonaleServiceManager;
import com.sourcesense.crl.business.service.TipoAttoServiceManager;

import com.sourcesense.crl.util.CRLMessage;
import com.sourcesense.crl.util.Clonator;
import com.sourcesense.crl.web.ui.beans.AttoBean;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

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

@ManagedBean(name = "inserisciMISController")
@ViewScoped
public class InserisciMISController {

	
	

	@ManagedProperty(value = "#{attoServiceManager}")
	private AttoServiceManager attoServiceManager;
	
	@ManagedProperty(value = "#{commissioneServiceManager}")
	private CommissioneServiceManager commissioneServiceManager;
	
	@ManagedProperty(value = "#{legislaturaServiceManager}")
	private LegislaturaServiceManager legislaturaServiceManager;
	
	@ManagedProperty(value = "#{attoRecordServiceManager}")
	private AttoRecordServiceManager attoRecordServiceManager;
	
	@ManagedProperty(value = "#{personaleServiceManager}")
	private PersonaleServiceManager personaleServiceManager;
	
	
	private AttoMIS atto = new AttoMIS();

	private String numeroRepertorio;
	private String oggetto;
	private Date dataIniziativaComitato;
	private Date dataPropostaCommissione;
	private String commissioneCompetente;
	private String esitoVotoIntesa;
	private Date dataIntesa;
	private Date dataRispostaComitato;
	private Date dataApprovazioneProgetto;
	private Date dataApprovazioneUdP;
	private Date dataTrasmissioneUdP;
	private String istitutoIncaricato;
	private String numeroAttoUdp;
	private Date dataScadenzaMV;
	private Date dataEsameRapportoFinale;
	private Date dataTrasmissioneCommissioni;
	private String note;
	private String legislatura;
	
	private String allegatoMISToDelete;
	private boolean currentFilePubblico;
	
	private List<Allegato> allegatiMIS = new ArrayList<Allegato>();
	private List<String> commissioni = new ArrayList<String>();
	
	private List<String> legislature = new ArrayList<String>();
	
	private List<Relatore> relatoriList = new ArrayList<Relatore>();
	private String relatore1;
	private String relatore2;
	
	
	@PostConstruct
	private void initializeValues(){
		
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));
		
		if(attoBean.getAttoMIS()!=null){
			
			atto = (AttoMIS)attoBean.getAttoMIS().clone();
			attoBean.setAttoMIS(null);
		}
		
		
		setCommissioni(commissioneServiceManager.getAll());
		setLegislature(legislaturaServiceManager.list());
		setRelatoriList(personaleServiceManager.getAllRelatori());
		
	}
	
	
	
	
	
	
	public void inserisciAtto() {

		atto.setTipoAtto("MIS");
		atto.setStato(StatoAtto.MIS);
		atto.setNumeroAtto(atto.getNumeroRepertorio());
		
		AttoMIS attoRet = attoServiceManager.persistMIS(atto);
		FacesContext context = FacesContext.getCurrentInstance();
		
		if (attoRet!=null && attoRet.getError()==null) {

		    this.atto=attoRet;
		    context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO,
					"Atto EAC inserito con successo", "")); 			

		} else if (attoRet!=null && attoRet.getError()!=null && !attoRet.getError().equals("")) {
			
			
			
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, attoRet.getError(), ""));
			
			
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

			Allegato allegatoRet = new Allegato();
			allegatoRet.setNome(event.getFile().getFileName());
			allegatoRet.setPubblico(currentFilePubblico);

			try {
				allegatoRet = attoServiceManager
						.uploadAllegatoMIS(
								atto, event
										.getFile().getInputstream(), allegatoRet);
				
			} catch (IOException e) {
				e.printStackTrace();
			}

			
			atto.getAllegati().add(allegatoRet);
		}
	}

	private boolean checkAllegatoParere(String fileName) {

		for (Allegato element : allegatiMIS) {

			if (fileName.equals(element.getDescrizione())) {

				return false;
			}

		}

		return true;
	}

	public void removeAllegatoParere() {

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));
		
		for (Allegato element : getAllegatiMIS()) {

			if (element.getId().equals(allegatoMISToDelete)) {

				attoRecordServiceManager.deleteFile(element.getId());
				getAllegatiMIS().remove(element);
				attoBean.getAttoMIS().setAllegati(Clonator.cloneList(getAllegatiMIS()));
				break;
			}
		}
	}
	
	

	public AttoServiceManager getAttoServiceManager() {
		return attoServiceManager;
	}

	public void setAttoServiceManager(AttoServiceManager attoServiceManager) {
		this.attoServiceManager = attoServiceManager;
	}

	
	public AttoMIS getAtto() {
		return atto;
	}

	public void setAtto(AttoMIS atto) {
		this.atto = atto;
	}
	
	public String getNumeroAtto() {
		return this.atto.getNumeroAtto();
	}

	public void setNumeroAtto(String numeroAtto) {
		this.atto.setNumeroAtto(numeroAtto);
	}


	public String getTipoAtto() {
		return this.atto.getTipoAtto();
	}



	public void setTipoAtto(String tipoAtto) {
		this.atto.setTipoAtto(tipoAtto);
	}
	


	public String getAnno() {
		return this.atto.getAnno();
	}



	public void setAnno(String anno) {
		this.atto.setAnno(anno);
	}
    
	public String getaAllegatoMISToDelete() {
		return allegatoMISToDelete;
	}

	public void setAllegatoMISToDelete(String allegatoMISToDelete) {
		this.allegatoMISToDelete = allegatoMISToDelete;
	}


	public CommissioneServiceManager getCommissioneServiceManager() {
		return commissioneServiceManager;
	}


	public void setCommissioneServiceManager(
			CommissioneServiceManager commissioneServiceManager) {
		this.commissioneServiceManager = commissioneServiceManager;
	}


	public String getNumeroRepertorio() {
		return atto.getNumeroRepertorio();
	}


	public void setNumeroRepertorio(String numeroRepertorio) {
		this.atto.setNumeroRepertorio(numeroRepertorio);
	}


	public String getOggetto() {
		return atto.getOggetto();
	}


	public void setOggetto(String oggetto) {
		this.atto.setOggetto(oggetto);
	}


	public Date getDataIniziativaComitato() {
		return atto.getDataIniziativaComitato();
	}


	public void setDataIniziativaComitato(Date dataIniziativaComitato) {
		this.atto.setDataIniziativaComitato (dataIniziativaComitato);
	}


	public Date getDataPropostaCommissione() {
		return atto.getDataPropostaCommissione();
	}


	public void setDataPropostaCommissione(Date dataPropostaCommissione) {
		this.atto.setDataPropostaCommissione(dataPropostaCommissione);
	}


	public String getCommissioneCompetente() {
		return atto.getCommissioneCompetente();
	}


	public void setCommissioneCompetente(String commissioneCompetente) {
		this.atto.setCommissioneCompetente(commissioneCompetente);
	}


	public String getEsitoVotoIntesa() {
		return atto.getEsitoVotoIntesa();
	}


	public void setEsitoVotoIntesa(String esitoVotoIntesa) {
		this.atto.setEsitoVotoIntesa(esitoVotoIntesa);
	}


	public Date getDataIntesa() {
		return atto.getDataIntesa();
	}


	public void setDataIntesa(Date dataIntesa) {
		this.atto.setDataIntesa(dataIntesa);
	}


	public Date getDataRispostaComitato() {
		return atto.getDataRispostaComitato();
	}


	public void setDataRispostaComitato(Date dataRispostaComitato) {
		this.atto.setDataRispostaComitato(dataRispostaComitato);
	}


	public Date getDataApprovazioneProgetto() {
		return atto.getDataApprovazioneProgetto();
	}


	public void setDataApprovazioneProgetto(Date dataApprovazioneProgetto) {
		this.atto.setDataApprovazioneProgetto (dataApprovazioneProgetto);
	}


	public Date getDataApprovazioneUdP() {
		return atto.getDataApprovazioneUdP();
	}


	public void setDataApprovazioneUdP(Date dataApprovazioneUdP) {
		this.atto.setDataApprovazioneUdP(dataApprovazioneUdP);
	}


	public String getIstitutoIncaricato() {
		return atto.getIstitutoIncaricato();
	}


	public void setIstitutoIncaricato(String istitutoIncaricato) {
		this.atto.setIstitutoIncaricato(istitutoIncaricato);
	}


	public String getNumeroAttoUdp() {
		return atto.getNumeroAttoUdp();
	}


	public void setNumeroAttoUdp(String numeroAttoUdp) {
		this.atto.setNumeroAttoUdp ( numeroAttoUdp);
	}


	public Date getDataScadenzaMV() {
		return atto.getDataScadenzaMV();
	}


	public void setDataScadenzaMV(Date dataScadenzaMV) {
		this.atto.setDataScadenzaMV ( dataScadenzaMV);
	}


	public Date getDataEsameRapportoFinale() {
		return atto.getDataEsameRapportoFinale();
	}


	public void setDataEsameRapportoFinale(Date dataEsameRapportoFinale) {
		this.atto.setDataEsameRapportoFinale ( dataEsameRapportoFinale);
	}


	public Date getDataTrasmissioneCommissioni() {
		return atto.getDataTrasmissioneCommissioni();
	}


	public void setDataTrasmissioneCommissioni(Date dataTrasmissioneCommissioni) {
		this.atto.setDataTrasmissioneCommissioni ( dataTrasmissioneCommissioni);
	}


	public String getNote() {
		return atto.getNote();
	}


	public void setNote(String note) {
		this.atto.setNote(note);
	}


	public List<Allegato> getAllegatiMIS() {
		return atto.getAllegati();
	}


	public void setAllegatiMIS(List<Allegato> allegatiMIS) {
		this.atto.setAllegati( allegatiMIS);
	}


	public String getAllegatoMISToDelete() {
		return allegatoMISToDelete;
	}


	public List<String> getCommissioni() {
		return commissioni;
	}


	public void setCommissioni(List<String> commissioni) {
		this.commissioni = commissioni;
	}


	public boolean isCurrentFilePubblico() {
		return currentFilePubblico;
	}


	public void setCurrentFilePubblico(boolean currentFilePubblico) {
		this.currentFilePubblico = currentFilePubblico;
	}
	
	public List<String> getLegislature() {
		return legislature;
	}

	public void setLegislature(List<String> legislature) {
		this.legislature = legislature;
	}
	
	public LegislaturaServiceManager getLegislaturaServiceManager() {
		return legislaturaServiceManager;
	}


	public void setLegislaturaServiceManager(
			LegislaturaServiceManager legislaturaServiceManager) {
		this.legislaturaServiceManager = legislaturaServiceManager;
	}
	
	public String getLegislatura() {
		return this.atto.getLegislatura();
	}



	public void setLegislatura(String legislatura) {
		this.atto.setLegislatura(legislatura);
	}

	public AttoRecordServiceManager getAttoRecordServiceManager() {
		return attoRecordServiceManager;
	}

	public void setAttoRecordServiceManager(
			AttoRecordServiceManager attoRecordServiceManager) {
		this.attoRecordServiceManager = attoRecordServiceManager;
	}

	public PersonaleServiceManager getPersonaleServiceManager() {
		return personaleServiceManager;
	}

	public void setPersonaleServiceManager(
			PersonaleServiceManager personaleServiceManager) {
		this.personaleServiceManager = personaleServiceManager;
	}

	public List<Relatore> getRelatoriList() {
		return relatoriList;
	}

	public void setRelatoriList(List<Relatore> relatoriList) {
		this.relatoriList = relatoriList;
	}

	public String getRelatore1() {
		return atto.getRelatore1();
	}

	public void setRelatore1(String relatore1) {
		this.atto.setRelatore1 ( relatore1);
	}

	public String getRelatore2() {
		return atto.getRelatore2();
	}

	public void setRelatore2(String relatore2) {
		this.atto.setRelatore2 ( relatore2);
	}

	public Date getDataTrasmissioneUdP() {
		return atto.getDataTrasmissioneUdP();
	}

	public void setDataTrasmissioneUdP(Date dataTrasmissioneUdP) {
		this.atto.setDataTrasmissioneUdP (dataTrasmissioneUdP);
	}
	
	

}
