package com.sourcesense.crl.web.ui.controller;

import java.io.IOException;
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
import com.sourcesense.crl.business.model.Link;
import com.sourcesense.crl.business.service.AttoServiceManager;
import com.sourcesense.crl.util.CRLMessage;
import com.sourcesense.crl.web.ui.beans.AttoBean;
import com.sourcesense.crl.web.ui.beans.UserBean;

@ManagedBean(name = "esameAulaController")
@ViewScoped
public class EsameAulaController {

	@ManagedProperty(value = "#{attoServiceManager}")
	private AttoServiceManager attoServiceManager;

	private Atto atto = new Atto();

	private Date dataPresaInCarico;
	private String relazioneScritta;
	private String esitoVotazione;
	private String tipologiaVotazione;
	private Date dataSedutaVotazione;
	private String numeroDcr;
	private String numeroLcr;
	private boolean emendato;
	private String noteVotazione;

	private List<Allegato> testiAttoVotatoList = new ArrayList<Allegato>();
	private String testoAttoVotatoToDelete;

	private List<Allegato> emendamentiList = new ArrayList<Allegato>();
	private String emendamentoToDelete;

	private int numEmendPresentatiMaggior;
	private int numEmendPresentatiMinor;
	private int numEmendPresentatiGiunta;
	private int numEmendPresentatiMisto;
	private int numEmendPresentatiTotale;
	private int numEmendApprovatiMaggior;
	private int numEmendApprovatiMinor;
	private int numEmendApprovatiGiunta;
	private int numEmendApprovatiMisto;
	private int numEmendApprovatiTotale;
	private int nonAmmissibili;
	private int decaduti;
	private int ritirati;
	private int respinti;
	private int totaleNonApprovati;
	private String noteEmendamenti;

	private Date dataSedutaRinvio;
	private Date dataTermineMassimo;
	private String motivazioneRinvio;
	private Date dataSedutaStralcio;
	private Date dataStralcio;
	private Date dataIniziativa;
	private String articoli;
	private String noteStralcio;
	private String quorum;

	private String noteGenerali;

	private List<Allegato> allegatiList = new ArrayList<Allegato>();
	private String allegatoToDelete;

	private List<Link> linksList = new ArrayList<Link>();
	private String nomeLink;
	private String linkToDelete;
	private String urlLink;
	private boolean pubblico;



	private String statoCommitVotazione = CRLMessage.COMMIT_DONE;
	private String statoCommitEmendamenti = CRLMessage.COMMIT_DONE;
	private String statoCommitRinvioEsame = CRLMessage.COMMIT_DONE;
	private String statoCommitStralci = CRLMessage.COMMIT_DONE;
	private String statoCommitNoteAllegati = CRLMessage.COMMIT_DONE;

	@PostConstruct
	public void init() {

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));
		setAtto((Atto) attoBean.getAtto().clone());	

		testiAttoVotatoList = new ArrayList<Allegato>(atto.getTestiAttoVotatoEsameAula());		
		emendamentiList = new ArrayList<Allegato>(atto.getEmendamentiEsameAula());
		allegatiList = new ArrayList<Allegato>(atto.getAllegatiEsameAula());
		linksList = new ArrayList<Link>(atto.getLinksEsameAula());

		totaleEmendApprovati();
		totaleEmendPresentati();
		totaleNonApprovati();
	}



	public void updateVotazioneHandler() {
		setStatoCommitVotazione(CRLMessage.COMMIT_UNDONE);
	}

	public void updateEmendamentiHandler() {
		setStatoCommitEmendamenti(CRLMessage.COMMIT_UNDONE);
	}

	public void updateRinvioEsameHandler() {
		setStatoCommitRinvioEsame(CRLMessage.COMMIT_UNDONE);
	}

	public void updateStralciHandler() {
		setStatoCommitStralci(CRLMessage.COMMIT_UNDONE);
	}

	public void updateNoteAllegatiHandler() {
		setStatoCommitNoteAllegati(CRLMessage.COMMIT_UNDONE);
	}


	public void changeTabHandler() {

		if (statoCommitVotazione.equals(CRLMessage.COMMIT_UNDONE)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Attenzione ! Le modifiche a Votazione non sono state salvate ",
							""));
		}

		if (statoCommitEmendamenti.equals(CRLMessage.COMMIT_UNDONE)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Attenzione ! Le modifiche su Emendamenti non sono state salvate ",
							""));
		}

		if (statoCommitRinvioEsame.equals(CRLMessage.COMMIT_UNDONE)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Attenzione ! Le modifiche a Rinvio Esame non sono state salvate ",
							""));
		}

		if (statoCommitStralci.equals(CRLMessage.COMMIT_UNDONE)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Attenzione ! Le modifiche a Stralci non sono state salvate ",
							""));
		}

		if (statoCommitNoteAllegati.equals(CRLMessage.COMMIT_UNDONE)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Attenzione ! Le modifiche a Note e Allegati non sono state salvate ",
							""));
		}
	}

	// Votazione**************************************************************
	public void presaInCarico() {
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));
		
		attoBean.getAtto().setDataPresaInCaricoEsameAula(atto.getDataPresaInCaricoEsameAula());
		attoBean.getAtto().setRelazioneScritta(atto.getRelazioneScritta());

		String username = ((UserBean) context.getExternalContext()
				.getSessionMap().get("userBean")).getUsername();

		String numeroAtto = attoBean.getNumeroAtto();

		context.addMessage(null, new FacesMessage("Atto " + numeroAtto
				+ " preso in carico con successo dall' utente " + username));
		
		setStatoCommitVotazione(CRLMessage.COMMIT_DONE);
	}


	public void uploadTestoAttoVotato(FileUploadEvent event) {
		// TODO Service logic
		String fileName = event.getFile().getFileName();

		if (!checkTestoAttoVotato(fileName)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Attenzione ! Il file "
							+ fileName + " è già stato allegato ", ""));
		} else {

			// TODO Alfresco upload
			Allegato allegatoRet = null;

			try {
				//TODO change method
				allegatoRet = attoServiceManager.uploadTestoAttoVotatoEsameAula(
						((AttoBean) FacesContext.getCurrentInstance()
								.getExternalContext().getSessionMap()
								.get("attoBean")).getAtto(), event
								.getFile().getInputstream(), event.getFile().getFileName());
			} catch (IOException e) {
				e.printStackTrace();
			}

			// TODO aggiungi a bean di sessione
			FacesContext context = FacesContext.getCurrentInstance();
			AttoBean attoBean = ((AttoBean) context.getExternalContext()
					.getSessionMap().get("attoBean"));

			attoBean.getAtto().getTestiAttoVotatoEsameAula().add(allegatoRet);

			testiAttoVotatoList.add(allegatoRet);
		}
	}

	private boolean checkTestoAttoVotato(String fileName) {

		for (Allegato element : testiAttoVotatoList) {

			if (element.getDescrizione().equals(fileName)) {

				return false;
			}

		}

		return true;
	}

	public void removeTestoAttoVotato() {

		for (Allegato element : testiAttoVotatoList) {

			if (element.getId().equals(testoAttoVotatoToDelete)) {

				// TODO Alfresco delete
				testiAttoVotatoList.remove(element);
				break;
			}
		}
	}

	public void salvaVotazione() {
		attoServiceManager.salvaVotazioneEsameAula(atto);

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		attoBean.getAtto().setEsitoVotoAula(atto.getEsitoVotoAula());
		attoBean.getAtto().setTipologiaVotazione(atto.getTipologiaVotazione());
		attoBean.getAtto().setDataSedutaAula(atto.getDataSedutaAula());
		attoBean.getAtto().setNumeroDcr(atto.getNumeroDcr());
		attoBean.getAtto().setNumeroLcr(atto.getNumeroLcr());
		attoBean.getAtto().setEmendato(atto.isEmendato());
		attoBean.getAtto().setNoteVotazione(atto.getNoteVotazione());

		setStatoCommitVotazione(CRLMessage.COMMIT_DONE);
		context.addMessage(null, new FacesMessage("Votazione salvata con successo", ""));
	}



	// Emendamenti************************************************************

	public void uploadEmendamento(FileUploadEvent event) {
		// TODO Service logic
		String fileName = event.getFile().getFileName();

		if (!checkEmendamenti(fileName)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Attenzione ! Il file "
							+ fileName + " è già stato allegato ", ""));
		} else {

			// TODO Alfresco upload
			Allegato emendamentoRet = null;

			try {
				//TODO change method
				emendamentoRet = attoServiceManager.uploadEmendamentoEsameAula(
						((AttoBean) FacesContext.getCurrentInstance()
								.getExternalContext().getSessionMap()
								.get("attoBean")).getAtto(), event
								.getFile().getInputstream(), event.getFile().getFileName());
			} catch (IOException e) {
				e.printStackTrace();
			}

			// TODO aggiungi a bean di sessione
			FacesContext context = FacesContext.getCurrentInstance();
			AttoBean attoBean = ((AttoBean) context.getExternalContext()
					.getSessionMap().get("attoBean"));

			attoBean.getAtto().getEmendamentiEsameAula().add(emendamentoRet);

			emendamentiList.add(emendamentoRet);
		}
	}

	private boolean checkEmendamenti(String fileName) {

		for (Allegato element : emendamentiList) {

			if (element.getDescrizione().equals(fileName)) {

				return false;
			}

		}

		return true;
	}

	public void removeEmendamento() {

		for (Allegato element : emendamentiList) {

			if (element.getId().equals(emendamentoToDelete)) {

				// TODO Alfresco delete
				emendamentiList.remove(element);
				break;
			}
		}
	}


	public void totaleEmendPresentati() {
		numEmendPresentatiTotale = getNumEmendPresentatiGiunta() + getNumEmendPresentatiMaggior() + 
				getNumEmendPresentatiMinor() + getNumEmendPresentatiMisto();
	}

	public void totaleEmendApprovati() {
		numEmendApprovatiTotale = getNumEmendApprovatiGiunta() + getNumEmendApprovatiMaggior() +
				getNumEmendApprovatiMinor() + getNumEmendApprovatiMisto();
	}

	public void totaleNonApprovati() {
		totaleNonApprovati = getNonAmmissibili() + getDecaduti() + getRitirati() + getRespinti();
	}

	public void salvaEmendamenti() {
		attoServiceManager.salvaEmendamentiEsameAula(atto);

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		attoBean.getAtto().setNumEmendPresentatiMaggiorEsameAula(atto.getNumEmendPresentatiMaggiorEsameAula());
		attoBean.getAtto().setNumEmendPresentatiMinorEsameAula(atto.getNumEmendPresentatiMinorEsameAula());
		attoBean.getAtto().setNumEmendPresentatiGiuntaEsameAula(atto.getNumEmendPresentatiGiuntaEsameAula());
		attoBean.getAtto().setNumEmendPresentatiMistoEsameAula(atto.getNumEmendPresentatiMistoEsameAula());

		attoBean.getAtto().setNumEmendApprovatiMaggiorEsameAula(atto.getNumEmendApprovatiMaggiorEsameAula());
		attoBean.getAtto().setNumEmendApprovatiMinorEsameAula(atto.getNumEmendApprovatiMinorEsameAula());
		attoBean.getAtto().setNumEmendApprovatiGiuntaEsameAula(atto.getNumEmendApprovatiGiuntaEsameAula());
		attoBean.getAtto().setNumEmendApprovatiMistoEsameAula(atto.getNumEmendApprovatiMistoEsameAula());

		attoBean.getAtto().setNonAmmissibiliEsameAula(atto.getNonAmmissibiliEsameAula());
		attoBean.getAtto().setDecadutiEsameAula(atto.getDecadutiEsameAula());
		attoBean.getAtto().setRitiratiEsameAula(atto.getRitiratiEsameAula());
		attoBean.getAtto().setRespintiEsameAula(atto.getRespintiEsameAula());

		attoBean.getAtto().setNoteEmendamentiEsameAula(atto.getNoteEmendamentiEsameAula());
		
		setStatoCommitEmendamenti(CRLMessage.COMMIT_DONE);
		context.addMessage(null, new FacesMessage("Emendamenti salvati con successo", ""));
	}
	
	// Rinvio e Starlci******************************************************
	
	public void salvaRinvioEsame() {
		attoServiceManager.salvaRinvioEsameEsameAula(atto);
		
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));
		
		attoBean.getAtto().setDataSedutaRinvio(atto.getDataSedutaRinvio());
		attoBean.getAtto().setDataTermineMassimo(atto.getDataTermineMassimo());
		attoBean.getAtto().setMotivazioneRinvio(atto.getMotivazioneRinvio());
		
		setStatoCommitRinvioEsame(CRLMessage.COMMIT_DONE);
		context.addMessage(null, new FacesMessage("Rinvio Esame salvato con successo", ""));
	}
	
	
	public void salvaStralci() {
		attoServiceManager.salvaStralciEsameAula(atto);
		
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));
		
		attoBean.getAtto().setDataSedutaStralcio(atto.getDataSedutaStralcio());
		attoBean.getAtto().setDataStralcio(atto.getDataStralcio());
		attoBean.getAtto().setDataIniziativa(atto.getDataIniziativa());
		attoBean.getAtto().setArticoli(atto.getArticoli());
		attoBean.getAtto().setNoteStralcio(atto.getNoteStralcio());
		attoBean.getAtto().setQuorumEsameAula(atto.getQuorumEsameAula());
		
		setStatoCommitStralci(CRLMessage.COMMIT_DONE);
		context.addMessage(null, new FacesMessage("Stralci salvati con successo", ""));
	}	

	// Note e Allegati********************************************************

	public void uploadAllegato(FileUploadEvent event) {

		// TODO Service logic
		String fileName = event.getFile().getFileName();

		if (!checkAllegato(fileName)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Attenzione ! Il file "
							+ fileName + " è già stato allegato ", ""));
		} else {

			// TODO Alfresco upload
			Allegato allegatoRet = null;

			try {
				//TODO change method
				allegatoRet = attoServiceManager.uploadAllegatoNoteAllegatiEsameAula(
						((AttoBean) FacesContext.getCurrentInstance()
								.getExternalContext().getSessionMap()
								.get("attoBean")).getAtto(), event
								.getFile().getInputstream(), event.getFile().getFileName());
			} catch (IOException e) {
				e.printStackTrace();
			}

			// TODO aggiungi a bean di sessione
			FacesContext context = FacesContext.getCurrentInstance();
			AttoBean attoBean = ((AttoBean) context.getExternalContext()
					.getSessionMap().get("attoBean"));

			attoBean.getAtto().getAllegatiEsameAula().add(allegatoRet);

			allegatiList.add(allegatoRet);
		}
	}

	private boolean checkAllegato(String fileName) {

		for (Allegato element : allegatiList) {

			if (element.getDescrizione().equals(fileName)) {

				return false;
			}

		}

		return true;
	}

	public void removeAllegato() {

		for (Allegato element : allegatiList) {

			if (element.getId().equals(allegatoToDelete)) {

				// TODO Alfresco delete
				allegatiList.remove(element);
				break;
			}
		}
	}

	public void addLink() {

		if (nomeLink != null && !nomeLink.trim().equals("")) {
			if (!checkLinks()) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Attenzione ! Link "
								+ nomeLink + " già presente ", ""));

			} else {
				Link link = new Link();
				link.setDescrizione(nomeLink);
				link.setIndirizzo(urlLink);
				link.setPubblico(pubblico);
				linksList.add(link);

				updateNoteAllegatiHandler();
			}
		}
	}

	public void removeLink() {

		for (Link element : linksList) {

			if (element.getDescrizione().equals(linkToDelete)) {

				linksList.remove(element);
				break;
			}
		}
	}

	private boolean checkLinks() {

		for (Link element : linksList) {

			if (element.getDescrizione().equals(nomeLink)) {

				return false;
			}

		}

		return true;
	}
	
	
	public void salvaNoteEAllegati() {
		this.atto.setLinksEsameAula(linksList);
		attoServiceManager.salvaNoteAllegatiEsameAula(atto);

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		attoBean.getAtto().setNoteGeneraliEsameAula(atto.getNoteGeneraliEsameAula());
		attoBean.getAtto().setLinksEsameAula(linksList);		

		setStatoCommitNoteAllegati(CRLMessage.COMMIT_DONE);

		context.addMessage(null, new FacesMessage("Note e Allegati salvati con successo", ""));

	}

	// Getters & Setters******************************************************

	public Atto getAtto() {
		return atto;
	}


	public void setAtto(Atto atto) {
		this.atto = atto;
	}


	public Date getDataPresaInCarico() {
		return atto.getDataPresaInCaricoEsameAula();
	}


	public void setDataPresaInCarico(Date dataPresaInCarico) {
		this.atto.setDataPresaInCaricoEsameAula(dataPresaInCarico);
	}


	public String getRelazioneScritta() {
		return atto.getRelazioneScritta();
	}


	public void setRelazioneScritta(String relazioneScritta) {
		this.atto.setRelazioneScritta(relazioneScritta);
	}


	public String getEsitoVotazione() {
		return atto.getEsitoVotoAula();
	}


	public void setEsitoVotazione(String esitoVotazione) {
		this.atto.setEsitoVotoAula(esitoVotazione);
	}


	public String getTipologiaVotazione() {
		return atto.getTipologiaVotazione();
	}


	public void setTipologiaVotazione(String tipologiaVotazione) {
		this.atto.setTipologiaVotazione(tipologiaVotazione);
	}


	public Date getDataSedutaVotazione() {
		return atto.getDataSedutaAula();
	}


	public void setDataSedutaVotazione(Date dataSedutaVotazione) {
		this.atto.setDataSedutaAula(dataSedutaVotazione);
	}


	public String getNumeroDcr() {
		return atto.getNumeroDcr();
	}


	public void setNumeroDcr(String numeroDcr) {
		this.atto.setNumeroDcr(numeroDcr);
	}


	public String getNumeroLcr() {
		return atto.getNumeroLcr();
	}


	public void setNumeroLcr(String numeroLcr) {
		this.atto.setNumeroLcr(numeroLcr);
	}


	public boolean isEmendato() {
		return atto.isEmendato();
	}


	public void setEmendato(boolean emendato) {
		this.atto.setEmendato(emendato);
	}


	public String getNoteVotazione() {
		return atto.getNoteVotazione();
	}


	public void setNoteVotazione(String noteVotazione) {
		this.atto.setNoteVotazione(noteVotazione);
	}


	public List<Allegato> getTestiAttoVotatoList() {
		return testiAttoVotatoList;
	}


	public void setTestiAttoVotatoList(List<Allegato> testiAttoVotatoList) {
		this.testiAttoVotatoList = testiAttoVotatoList;
	}


	public List<Allegato> getEmendamentiList() {
		return emendamentiList;
	}


	public void setEmendamentiList(List<Allegato> emendamentiList) {
		this.emendamentiList = emendamentiList;
	}


	public int getNumEmendPresentatiMaggior() {
		return atto.getNumEmendPresentatiMaggiorEsameAula();
	}


	public void setNumEmendPresentatiMaggior(int numEmendPresentatiMaggior) {
		this.atto.setNumEmendPresentatiMaggiorEsameAula(numEmendPresentatiMaggior);
	}


	public int getNumEmendPresentatiMinor() {
		return atto.getNumEmendPresentatiMinorEsameAula();
	}


	public void setNumEmendPresentatiMinor(int numEmendPresentatiMinor) {
		this.atto.setNumEmendPresentatiMinorEsameAula(numEmendPresentatiMinor);
	}


	public int getNumEmendPresentatiGiunta() {
		return atto.getNumEmendPresentatiGiuntaEsameAula();
	}


	public void setNumEmendPresentatiGiunta(int numEmendPresentatiGiunta) {
		this.atto.setNumEmendPresentatiGiuntaEsameAula(numEmendPresentatiGiunta);
	}


	public int getNumEmendPresentatiMisto() {
		return atto.getNumEmendPresentatiMistoEsameAula();
	}


	public void setNumEmendPresentatiMisto(int numEmendPresentatiMisto) {
		this.atto.setNumEmendPresentatiMistoEsameAula(numEmendPresentatiMisto);
	}


	public int getNumEmendPresentatiTotale() {
		return numEmendPresentatiTotale;
	}


	public void setNumEmendPresentatiTotale(int numEmendPresentatiTotale) {
		this.numEmendPresentatiTotale = numEmendPresentatiTotale;
	}


	public int getNumEmendApprovatiMaggior() {
		return atto.getNumEmendApprovatiMaggiorEsameAula();
	}


	public void setNumEmendApprovatiMaggior(int numEmendApprovatiMaggior) {
		this.atto.setNumEmendApprovatiMaggiorEsameAula(numEmendApprovatiMaggior);
	}


	public int getNumEmendApprovatiMinor() {
		return atto.getNumEmendApprovatiMinorEsameAula();
	}


	public void setNumEmendApprovatiMinor(int numEmendApprovatiMinor) {
		this.atto.setNumEmendApprovatiMinorEsameAula(numEmendApprovatiMinor);
	}


	public int getNumEmendApprovatiGiunta() {
		return atto.getNumEmendApprovatiGiuntaEsameAula();
	}


	public void setNumEmendApprovatiGiunta(int numEmendApprovatiGiunta) {
		this.atto.setNumEmendApprovatiGiuntaEsameAula(numEmendApprovatiGiunta);
	}


	public int getNumEmendApprovatiMisto() {
		return atto.getNumEmendApprovatiMistoEsameAula();
	}


	public void setNumEmendApprovatiMisto(int numEmendApprovatiMisto) {
		this.atto.setNumEmendApprovatiMistoEsameAula(numEmendApprovatiMisto);
	}


	public int getNumEmendApprovatiTotale() {
		return numEmendApprovatiTotale;
	}


	public void setNumEmendApprovatiTotale(int numEmendApprovatiTotale) {
		this.numEmendApprovatiTotale = numEmendApprovatiTotale;
	}


	public int getNonAmmissibili() {
		return atto.getNonAmmissibiliEsameAula();
	}


	public void setNonAmmissibili(int nonAmmissibili) {
		this.atto.setNonAmmissibiliEsameAula(nonAmmissibili);
	}


	public int getDecaduti() {
		return atto.getDecadutiEsameAula();
	}


	public void setDecaduti(int decaduti) {
		this.atto.setDecadutiEsameAula(decaduti);
	}


	public int getRitirati() {
		return atto.getRitiratiEsameAula();
	}


	public void setRitirati(int ritirati) {
		this.atto.setRitiratiEsameAula(ritirati);
	}


	public int getRespinti() {
		return atto.getRespintiEsameAula();
	}


	public void setRespinti(int respinti) {
		this.atto.setRespintiEsameAula(respinti);
	}


	public int getTotaleNonApprovati() {
		return totaleNonApprovati;
	}


	public void setTotaleNonApprovati(int totaleNonApprovati) {
		this.totaleNonApprovati = totaleNonApprovati;
	}


	public String getNoteEmendamenti() {
		return atto.getNoteEmendamentiEsameAula();
	}


	public void setNoteEmendamenti(String noteEmendamenti) {
		this.atto.setNoteEmendamentiEsameAula(noteEmendamenti);
	}


	public Date getDataSedutaRinvio() {
		return atto.getDataSedutaRinvio();
	}


	public void setDataSedutaRinvio(Date dataSedutaRinvio) {
		this.atto.setDataSedutaRinvio(dataSedutaRinvio);
	}


	public Date getDataTermineMassimo() {
		return atto.getDataTermineMassimo();
	}


	public void setDataTermineMassimo(Date dataTermineMassimo) {
		this.atto.setDataTermineMassimo(dataTermineMassimo);
	}


	public String getMotivazioneRinvio() {
		return atto.getMotivazioneRinvio();
	}


	public void setMotivazioneRinvio(String motivazione) {
		this.atto.setMotivazioneRinvio(motivazione);
	}


	public Date getDataSedutaStralcio() {
		return atto.getDataSedutaStralcio();
	}


	public void setDataSedutaStralcio(Date dataSedutaStralcio) {
		this.atto.setDataSedutaStralcio(dataSedutaStralcio);
	}


	public Date getDataStralcio() {
		return atto.getDataStralcio();
	}


	public void setDataStralcio(Date dataStralcio) {
		this.atto.setDataStralcio(dataStralcio);
	}


	public Date getDataIniziativa() {
		return atto.getDataIniziativa();
	}


	public void setDataIniziativa(Date dataIniziativa) {
		this.atto.setDataIniziativa(dataIniziativa);
	}


	public String getArticoli() {
		return atto.getArticoli();
	}


	public void setArticoli(String articoli) {
		this.atto.setArticoli(articoli);
	}


	public String getNoteStralcio() {
		return atto.getNoteStralcio();
	}


	public void setNoteStralcio(String noteStralcio) {
		this.atto.setNoteStralcio(noteStralcio);
	}


	public String getQuorum() {
		return atto.getQuorumEsameAula();
	}


	public void setQuorum(String quorum) {
		this.atto.setQuorumEsameAula(quorum);
	}


	public String getNoteGenerali() {
		return atto.getNoteGeneraliEsameAula();
	}


	public void setNoteGenerali(String noteGenerali) {
		this.atto.setNoteGeneraliEsameAula(noteGenerali);
	}


	public List<Allegato> getAllegatiList() {
		return allegatiList;
	}


	public void setAllegatiList(List<Allegato> allegatiList) {
		this.allegatiList = allegatiList;
	}


	public List<Link> getLinksList() {
		return linksList;
	}


	public void setLinksList(List<Link> linksList) {
		this.linksList = linksList;
	}


	public String getStatoCommitVotazione() {
		return statoCommitVotazione;
	}


	public void setStatoCommitVotazione(String statoCommitVotazione) {
		this.statoCommitVotazione = statoCommitVotazione;
	}


	public String getStatoCommitEmendamenti() {
		return statoCommitEmendamenti;
	}


	public void setStatoCommitEmendamenti(String statoCommitEmendamenti) {
		this.statoCommitEmendamenti = statoCommitEmendamenti;
	}


	public String getStatoCommitRinvioEsame() {
		return statoCommitRinvioEsame;
	}


	public void setStatoCommitRinvioEsame(String statoCommitRinvioEsame) {
		this.statoCommitRinvioEsame = statoCommitRinvioEsame;
	}


	public String getStatoCommitStralci() {
		return statoCommitStralci;
	}


	public void setStatoCommitStralci(String statoCommitStralci) {
		this.statoCommitStralci = statoCommitStralci;
	}


	public String getStatoCommitNoteAllegati() {
		return statoCommitNoteAllegati;
	}


	public void setStatoCommitNoteAllegati(String statoCommitNoteAllegati) {
		this.statoCommitNoteAllegati = statoCommitNoteAllegati;
	}



	public AttoServiceManager getAttoServiceManager() {
		return attoServiceManager;
	}



	public void setAttoServiceManager(AttoServiceManager attoServiceManager) {
		this.attoServiceManager = attoServiceManager;
	}



	public String getTestoAttoVotatoToDelete() {
		return testoAttoVotatoToDelete;
	}



	public void setTestoAttoVotatoToDelete(String testoAttoVotatoToDelete) {
		this.testoAttoVotatoToDelete = testoAttoVotatoToDelete;
	}



	public String getEmendamentoToDelete() {
		return emendamentoToDelete;
	}



	public void setEmendamentoToDelete(String emendamentoToDelete) {
		this.emendamentoToDelete = emendamentoToDelete;
	}



	public String getAllegatoToDelete() {
		return allegatoToDelete;
	}



	public void setAllegatoToDelete(String allegatoToDelete) {
		this.allegatoToDelete = allegatoToDelete;
	}



	public String getNomeLink() {
		return nomeLink;
	}



	public void setNomeLink(String nomeLink) {
		this.nomeLink = nomeLink;
	}



	public String getLinkToDelete() {
		return linkToDelete;
	}



	public void setLinkToDelete(String linkToDelete) {
		this.linkToDelete = linkToDelete;
	}



	public String getUrlLink() {
		return urlLink;
	}



	public void setUrlLink(String urlLink) {
		this.urlLink = urlLink;
	}



	public boolean isPubblico() {
		return pubblico;
	}



	public void setPubblico(boolean pubblico) {
		this.pubblico = pubblico;
	}






}



