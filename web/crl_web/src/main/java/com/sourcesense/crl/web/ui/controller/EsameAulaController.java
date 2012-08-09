package com.sourcesense.crl.web.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.sourcesense.crl.business.model.Allegato;
import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.model.Link;
import com.sourcesense.crl.util.CRLMessage;
import com.sourcesense.crl.web.ui.beans.AttoBean;

@ManagedBean(name = "esameAulaController")
@ViewScoped
public class EsameAulaController {
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
	
	private List<Allegato> emendamentiList = new ArrayList<Allegato>();
	
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
	private List<Link> linksList = new ArrayList<Link>();
	
	
	
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
	
	
	// Emendamenti************************************************************
	
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

	


	// Getters & Setters******************************************************
	
	public Atto getAtto() {
		return atto;
	}


	public void setAtto(Atto atto) {
		this.atto = atto;
	}


	public Date getDataPresaInCarico() {
		return dataPresaInCarico;
	}


	public void setDataPresaInCarico(Date dataPresaInCarico) {
		this.dataPresaInCarico = dataPresaInCarico;
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
		return atto.getDataSedutaVotazione();
	}


	public void setDataSedutaVotazione(Date dataSedutaVotazione) {
		this.atto.setDataSedutaVotazione(dataSedutaVotazione);
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
	
	
}



