package com.sourcesense.crl.web.ui.controller;

import com.sourcesense.crl.business.model.Allegato;
import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.model.AttoMIS;
import com.sourcesense.crl.business.service.AttoServiceManager;
import com.sourcesense.crl.business.service.CommissioneServiceManager;
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

@ManagedBean(name = "inserisciMISController")
@ViewScoped
public class InserisciMISController {

	
	

	@ManagedProperty(value = "#{attoServiceManager}")
	private AttoServiceManager attoServiceManager;
	
	@ManagedProperty(value = "#{commissioneServiceManager}")
	private CommissioneServiceManager commissioneServiceManager;
	
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
	private String istitutoIncaricato;
	private String numeroAttoUdp;
	private Date dataScadenzaMV;
	private Date dataEsameRapportoFinale;
	private Date dataTrasmissioneCommissioni;
	private String note;
	
	private String allegatoMISToDelete;
	
	private List<Allegato> allegatiMIS = new ArrayList<Allegato>();
	private Map<String, String> commissioni = new HashMap<String, String>();
	
	@PostConstruct
	private void initializeValues(){
		
		setCommissioni(commissioneServiceManager.findAll());
		
	}
	
	
	public String inserisciAtto() {

		atto.setStato("MIS");
		
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
						.uploadAllegatoMIS(
								((AttoBean) FacesContext.getCurrentInstance()
										.getExternalContext().getSessionMap()
										.get("attoBean")).getAtto(), event
										.getFile().getInputstream(), event
										.getFile().getFileName());
			} catch (IOException e) {
				e.printStackTrace();
			}

			
			allegatiMIS.add(allegatoRet);
		}
	}

	private boolean checkAllegatoParere(String fileName) {

		for (Allegato element : allegatiMIS) {

			if (element.getDescrizione().equals(fileName)) {

				return false;
			}

		}

		return true;
	}

	public void removeAllegatoParere() {

		for (Allegato element : allegatiMIS) {

			if (element.getId().equals(allegatoMISToDelete)) {

				// TODO Alfresco delete
				allegatiMIS.remove(element);
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
	

	public String getLegislatura() {
		return this.atto.getLegislatura();
	}



	public void setLegislatura(String legislatura) {
		this.atto.setLegislatura(legislatura);
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

	public void setAllegatoMISToDelete(String allegatoParereToDelete) {
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


	public Map<String, String> getCommissioni() {
		return commissioni;
	}


	public void setCommissioni(Map<String, String> commissioni) {
		this.commissioni = commissioni;
	}
	
	
	
	

}
