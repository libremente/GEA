package com.sourcesense.crl.web.ui.controller;

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
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

import com.sourcesense.crl.business.model.Allegato;
import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.model.Emendamento;
import com.sourcesense.crl.business.model.Link;
import com.sourcesense.crl.business.model.Personale;
import com.sourcesense.crl.business.model.Relatore;
import com.sourcesense.crl.business.model.TestoAttoVotato;
import com.sourcesense.crl.business.model.TestoClausola;
import com.sourcesense.crl.business.model.TestoComitatoRistretto;
import com.sourcesense.crl.business.service.AttoServiceManager;
import com.sourcesense.crl.business.service.PersonaleServiceManager;
import com.sourcesense.crl.util.CRLMessage;
import com.sourcesense.crl.web.ui.beans.AttoBean;


@ManagedBean(name = "esameCommissioniController")
@ViewScoped
public class EsameCommissioniController {
	
	@ManagedProperty(value = "#{personaleServiceManager}")
	private PersonaleServiceManager personaleServiceManager;
	
	@ManagedProperty(value = "#{attoServiceManager}")
	private AttoServiceManager attoServiceManager;
	
	private Atto atto;
	
	private Map <String, String> relatori = new HashMap<String, String>();
	private Map <String, String> membriComitato = new HashMap<String, String>();
	
	private Date dataPresaInCarico;
	private String materia;
	private Date dataScadenza;
	private Date dataFineLavori;
	
	private List <Relatore> relatoriList = new ArrayList <Relatore>();
	private List <Personale> membriComitatoList = new ArrayList <Personale>();
	private List <TestoComitatoRistretto> testiComitatoRistrettoList = new ArrayList <TestoComitatoRistretto>();
	
	
	private List <Atto> abbinamenti = new ArrayList<Atto>(); 
	
	private String tipoTesto;
	private Date dataAbbinamento;
	private Date dataDisabbinamento;
	private String noteAbbinamento;
	private String oggettoAttoCorrente;
	
	
	private String esitoVotazione;
	private String quorum;
	private Date dataSedutaRegistrazioneVotazione;
	
	private List <TestoAttoVotato> testiAttoVotatoList = new ArrayList <TestoAttoVotato>();
	private String testoAttoVotatoToDelete;
	
	private Date dataSedutaContinuazioneLavori;
	private String motivazioni;
	private Date dataTrasmissione;
	private Date dataRichiestaIscrizione;
	private boolean passaggioDiretto;
	
	
	private List <Emendamento> emendamentiList = new ArrayList <Emendamento>();
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
	
	private int nomiAmmissibili;
	private int decaduti;
	private int ritirati;
	private int respinti;
	private int totaleNonApprovati;
	private String noteEmendamenti;
	private Date dataPresaInCaricoProposta;
	private Date dataIntesa;
	private String esitoVotazioneIntesa;
	private String noteClausolaValutativa;
	
	private List <TestoClausola> testiClausolaList = new ArrayList <TestoClausola>();
	private String testoClausolaToDelete;
	
	
	private String noteGenerali;
	
	private List <Allegato> allegatiList = new ArrayList <Allegato>();
	private List <Link> linksList = new ArrayList <Link>();
	
	private String allegatoToDelete;
	private String linkToDelete;
	
	private String nomeLink;
	private String urlLink;
	private boolean pubblico;
	
	
	private String statoCommitRelatoriComitatiRistretti = CRLMessage.COMMIT_DONE;
	private String statoCommitAbbinamenti = CRLMessage.COMMIT_DONE;
	private String statoCommitVotazione = CRLMessage.COMMIT_DONE;
	private String statoCommitEmendamentiClausole = CRLMessage.COMMIT_DONE;
	private String statoCommitNote = CRLMessage.COMMIT_DONE;
	
	
	@PostConstruct
	protected void init() {
		
		setRelatori(personaleServiceManager.findAllRelatore());
		//setMembriComitato(personaleServiceManager.findAll())
		
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));
		setAtto((Atto) attoBean.getAtto().clone());
		
		//TODO : init liste
		
				
	}
	
	public void updateRelatoriComitatiRistrettiHandler() {
		setStatoCommitRelatoriComitatiRistretti(CRLMessage.COMMIT_UNDONE);
	}

	public void updateAbbinamentiHandler() {
		setStatoCommitAbbinamenti(CRLMessage.COMMIT_UNDONE);
	}

	public void updateEmendamentiClausoleHandler() {
		setStatoCommitEmendamentiClausole(CRLMessage.COMMIT_UNDONE);
	}
	
	public void updateVotazioneHandler() {
		setStatoCommitVotazione(CRLMessage.COMMIT_UNDONE);
	}

	public void updateNoteHandler() {
		setStatoCommitNote(CRLMessage.COMMIT_UNDONE);
	}

	public void changeTabHandler() {

		if (statoCommitRelatoriComitatiRistretti.equals(CRLMessage.COMMIT_UNDONE)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Attenzione ! Le modifiche a Relatori e Comitati Ristretti non sono state salvate ",
							""));
		}

		if (statoCommitAbbinamenti.equals(CRLMessage.COMMIT_UNDONE)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Attenzione ! Le modifiche di Abbinamenti non sono state salvate ",
							""));
		}

		if (statoCommitVotazione.equals(CRLMessage.COMMIT_UNDONE)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Attenzione ! Le modifiche di Votazione non sono state salvate ",
							""));
		}

		if (statoCommitVotazione.equals(CRLMessage.COMMIT_UNDONE)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Attenzione ! Le modifiche di Emendamenti e Clausole non sono state salvate ",
							""));
		}
		
		if (statoCommitNote.equals(CRLMessage.COMMIT_UNDONE)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Attenzione ! Le modifiche alle Note ed Allegati non sono state salvate ",
							""));
		}

	}
	
	// Votazione****************************************************************
	
	public void uploadTestoAttoVotato() {
		
	}
	
	public void removeTestoAttoVotato() {
		
	}
	
	
	
	// Emendamenti e Clausole*************************************************
	
	
	public void uploadEmendamento() {
		
	}
	
	
	public void removeEmendamento() {
		
	}
	
	public void uploadTestoClausola() {
		
	}
	
	public void removeTestoClausola() {
		
	}
	
	
	// Note e Allegati******************************************************
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
					allegatoRet = attoServiceManager.uploadAllegato(
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

				attoBean.getAtto().getAllegati().add(allegatoRet);

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
					link.setCollegamentoUrl(urlLink);
					link.setPubblico(pubblico);
					linksList.add(link);
					
					updateNoteHandler();
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
			this.atto.setLinks(linksList);
			//attoServiceManager.salvaNoteAllegatiPresentazione(atto);

			// TODO Service logic
			FacesContext context = FacesContext.getCurrentInstance();
			AttoBean attoBean = ((AttoBean) context.getExternalContext()
					.getSessionMap().get("attoBean"));

			attoBean.getAtto().setNoteNoteAllegatiPresentazioneAssegnazione(atto.getNoteNoteAllegatiPresentazioneAssegnazione());
			attoBean.getAtto().setLinks(linksList);		

			setStatoCommitNote(CRLMessage.COMMIT_DONE);

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
		return dataPresaInCarico;
	}



	public void setDataPresaInCarico(Date dataPresaInCarico) {
		this.dataPresaInCarico = dataPresaInCarico;
	}



	public String getMateria() {
		return materia;
	}



	public void setMateria(String materia) {
		this.materia = materia;
	}



	public Date getDataScadenza() {
		return dataScadenza;
	}



	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}



	public Date getDataFineLavori() {
		return dataFineLavori;
	}



	public void setDataFineLavori(Date dataFineLavori) {
		this.dataFineLavori = dataFineLavori;
	}



	



	public List<Atto> getAbbinamenti() {
		return abbinamenti;
	}



	public void setAbbinamenti(List<Atto> abbinamenti) {
		this.abbinamenti = abbinamenti;
	}



	public String getTipoTesto() {
		return tipoTesto;
	}



	public void setTipoTesto(String tipoTesto) {
		this.tipoTesto = tipoTesto;
	}



	public Date getDataAbbinamento() {
		return dataAbbinamento;
	}



	public void setDataAbbinamento(Date dataAbbinamento) {
		this.dataAbbinamento = dataAbbinamento;
	}


	public String getNoteAbbinamento() {
		return noteAbbinamento;
	}



	public void setNoteAbbinamento(String noteAbbinamento) {
		this.noteAbbinamento = noteAbbinamento;
	}



	public String getOggettoAttoCorrente() {
		return oggettoAttoCorrente;
	}



	public void setOggettoAttoCorrente(String oggettoAttoCorrente) {
		this.oggettoAttoCorrente = oggettoAttoCorrente;
	}



	public String getEsitoVotazione() {
		return esitoVotazione;
	}



	public void setEsitoVotazione(String esitoVotazione) {
		this.esitoVotazione = esitoVotazione;
	}



	public String getQuorum() {
		return quorum;
	}



	public void setQuorum(String quorum) {
		this.quorum = quorum;
	}



	public Date getDataSedutaRegistrazioneVotazione() {
		return dataSedutaRegistrazioneVotazione;
	}



	public void setDataSedutaRegistrazioneVotazione(
			Date dataSedutaRegistrazioneVotazione) {
		this.dataSedutaRegistrazioneVotazione = dataSedutaRegistrazioneVotazione;
	}



	


	public Date getDataSedutaContinuazioneLavori() {
		return dataSedutaContinuazioneLavori;
	}



	public void setDataSedutaContinuazioneLavori(Date dataSedutaContinuazioneLavori) {
		this.dataSedutaContinuazioneLavori = dataSedutaContinuazioneLavori;
	}



	public String getMotivazioni() {
		return motivazioni;
	}



	public void setMotivazioni(String motivazioni) {
		this.motivazioni = motivazioni;
	}



	public Date getDataTrasmissione() {
		return dataTrasmissione;
	}



	public void setDataTrasmissione(Date dataTrasmissione) {
		this.dataTrasmissione = dataTrasmissione;
	}



	public Date getDataRichiestaIscrizione() {
		return dataRichiestaIscrizione;
	}



	public void setDataRichiestaIscrizione(Date dataRichiestaIscrizione) {
		this.dataRichiestaIscrizione = dataRichiestaIscrizione;
	}



	public boolean isPassaggioDiretto() {
		return passaggioDiretto;
	}



	public void setPassaggioDiretto(boolean passaggioDiretto) {
		this.passaggioDiretto = passaggioDiretto;
	}



	
	public int getNumEmendPresentatiMaggior() {
		return numEmendPresentatiMaggior;
	}



	public void setNumEmendPresentatiMaggior(int numEmendPresentatiMaggior) {
		this.numEmendPresentatiMaggior = numEmendPresentatiMaggior;
	}



	public int getNumEmendPresentatiMinor() {
		return numEmendPresentatiMinor;
	}



	public void setNumEmendPresentatiMinor(int numEmendPresentatiMinor) {
		this.numEmendPresentatiMinor = numEmendPresentatiMinor;
	}



	public int getNumEmendPresentatiGiunta() {
		return numEmendPresentatiGiunta;
	}



	public void setNumEmendPresentatiGiunta(int numEmendPresentatiGiunta) {
		this.numEmendPresentatiGiunta = numEmendPresentatiGiunta;
	}



	public int getNumEmendPresentatiMisto() {
		return numEmendPresentatiMisto;
	}



	public void setNumEmendPresentatiMisto(int numEmendPresentatiMisto) {
		this.numEmendPresentatiMisto = numEmendPresentatiMisto;
	}



	public int getNumEmendPresentatiTotale() {
		return numEmendPresentatiTotale;
	}



	public void setNumEmendPresentatiTotale(int numEmendPresentatiTotale) {
		this.numEmendPresentatiTotale = numEmendPresentatiTotale;
	}



	public int getNumEmendApprovatiMaggior() {
		return numEmendApprovatiMaggior;
	}



	public void setNumEmendApprovatiMaggior(int numEmendApprovatiMaggior) {
		this.numEmendApprovatiMaggior = numEmendApprovatiMaggior;
	}



	public int getNumEmendApprovatiMinor() {
		return numEmendApprovatiMinor;
	}



	public void setNumEmendApprovatiMinor(int numEmendApprovatiMinor) {
		this.numEmendApprovatiMinor = numEmendApprovatiMinor;
	}



	public int getNumEmendApprovatiGiunta() {
		return numEmendApprovatiGiunta;
	}



	public void setNumEmendApprovatiGiunta(int numEmendApprovatiGiunta) {
		this.numEmendApprovatiGiunta = numEmendApprovatiGiunta;
	}



	public int getNumEmendApprovatiMisto() {
		return numEmendApprovatiMisto;
	}



	public void setNumEmendApprovatiMisto(int numEmendApprovatiMisto) {
		this.numEmendApprovatiMisto = numEmendApprovatiMisto;
	}



	public int getNumEmendApprovatiTotale() {
		return numEmendApprovatiTotale;
	}



	public void setNumEmendApprovatiTotale(int numEmendApprovatiTotale) {
		this.numEmendApprovatiTotale = numEmendApprovatiTotale;
	}



	public int getNomiAmmissibili() {
		return nomiAmmissibili;
	}



	public void setNomiAmmissibili(int nomiAmmissibili) {
		this.nomiAmmissibili = nomiAmmissibili;
	}



	public int getDecaduti() {
		return decaduti;
	}



	public void setDecaduti(int decaduti) {
		this.decaduti = decaduti;
	}



	public int getRitirati() {
		return ritirati;
	}



	public void setRitirati(int ritirati) {
		this.ritirati = ritirati;
	}



	public int getRespinti() {
		return respinti;
	}



	public void setRespinti(int respinti) {
		this.respinti = respinti;
	}



	public int getTotaleNonApprovati() {
		return totaleNonApprovati;
	}



	public void setTotaleNonApprovati(int totaleNonApprovati) {
		this.totaleNonApprovati = totaleNonApprovati;
	}



	public String getNoteEmendamenti() {
		return noteEmendamenti;
	}



	public void setNoteEmendamenti(String noteEmendamenti) {
		this.noteEmendamenti = noteEmendamenti;
	}



	public Date getDataPresaInCaricoProposta() {
		return dataPresaInCaricoProposta;
	}



	public void setDataPresaInCaricoProposta(Date dataPresaInCaricoProposta) {
		this.dataPresaInCaricoProposta = dataPresaInCaricoProposta;
	}



	public Date getDataIntesa() {
		return dataIntesa;
	}



	public void setDataIntesa(Date dataIntesa) {
		this.dataIntesa = dataIntesa;
	}



	public String getEsitoVotazioneIntesa() {
		return esitoVotazioneIntesa;
	}



	public void setEsitoVotazioneIntesa(String esitoVotazioneIntesa) {
		this.esitoVotazioneIntesa = esitoVotazioneIntesa;
	}



	public String getNoteClausolaValutativa() {
		return noteClausolaValutativa;
	}



	public void setNoteClausolaValutativa(String noteClausolaValutativa) {
		this.noteClausolaValutativa = noteClausolaValutativa;
	}



	



	public String getNoteGenerali() {
		return noteGenerali;
	}



	public void setNoteGenerali(String noteGenerali) {
		this.noteGenerali = noteGenerali;
	}



	public Map<String, String> getRelatori() {
		return relatori;
	}



	public void setRelatori(Map<String, String> relatori) {
		this.relatori = relatori;
	}



	public Map<String, String> getMembriComitato() {
		return membriComitato;
	}



	public void setMembriComitato(Map<String, String> membriComitato) {
		this.membriComitato = membriComitato;
	}



	public List<Relatore> getRelatoriList() {
		return relatoriList;
	}



	public void setRelatoriList(List<Relatore> relatoriList) {
		this.relatoriList = relatoriList;
	}



	public List<Personale> getMembriComitatoList() {
		return membriComitatoList;
	}



	public void setMembriComitatoList(List<Personale> membriComitatoList) {
		this.membriComitatoList = membriComitatoList;
	}



	public List<TestoComitatoRistretto> getTestiComitatoRistrettoList() {
		return testiComitatoRistrettoList;
	}



	public void setTestiComitatoRistrettoList(
			List<TestoComitatoRistretto> testiComitatoRistrettoList) {
		this.testiComitatoRistrettoList = testiComitatoRistrettoList;
	}



	public List<TestoAttoVotato> getTestiAttoVotatoList() {
		return testiAttoVotatoList;
	}



	public void setTestiAttoVotatoList(List<TestoAttoVotato> testiAttoVotatoList) {
		this.testiAttoVotatoList = testiAttoVotatoList;
	}



	public List<Emendamento> getEmendamentiList() {
		return emendamentiList;
	}



	public void setEmendamentiList(List<Emendamento> emendamentiList) {
		this.emendamentiList = emendamentiList;
	}



	public List<TestoClausola> getTestiClausolaList() {
		return testiClausolaList;
	}



	public void setTestiClausolaList(List<TestoClausola> testiClausolaList) {
		this.testiClausolaList = testiClausolaList;
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



	public PersonaleServiceManager getPersonaleServiceManager() {
		return personaleServiceManager;
	}



	public void setPersonaleServiceManager(
			PersonaleServiceManager personaleServiceManager) {
		this.personaleServiceManager = personaleServiceManager;
	}



	public String getStatoCommitRelatoriComitatiRistretti() {
		return statoCommitRelatoriComitatiRistretti;
	}



	public void setStatoCommitRelatoriComitatiRistretti(
			String statoCommitRelatoriComitatiRistretti) {
		this.statoCommitRelatoriComitatiRistretti = statoCommitRelatoriComitatiRistretti;
	}



	public String getStatoCommitAbbinamenti() {
		return statoCommitAbbinamenti;
	}



	public void setStatoCommitAbbinamenti(String statoCommitAbbinamenti) {
		this.statoCommitAbbinamenti = statoCommitAbbinamenti;
	}



	public String getStatoCommitVotazione() {
		return statoCommitVotazione;
	}



	public void setStatoCommitVotazione(String statoCommitVotazione) {
		this.statoCommitVotazione = statoCommitVotazione;
	}



	public String getStatoCommitEmendamentiClausole() {
		return statoCommitEmendamentiClausole;
	}



	public void setStatoCommitEmendamentiClausole(
			String statoCommitEmendamentiClausole) {
		this.statoCommitEmendamentiClausole = statoCommitEmendamentiClausole;
	}



	public String getStatoCommitNote() {
		return statoCommitNote;
	}



	public void setStatoCommitNote(String statoCommitNote) {
		this.statoCommitNote = statoCommitNote;
	}

	public Date getDataDisabbinamento() {
		return dataDisabbinamento;
	}

	public void setDataDisabbinamento(Date dataDisabbinamento) {
		this.dataDisabbinamento = dataDisabbinamento;
	}

	public AttoServiceManager getAttoServiceManager() {
		return attoServiceManager;
	}

	public void setAttoServiceManager(AttoServiceManager attoServiceManager) {
		this.attoServiceManager = attoServiceManager;
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

	public String getLinkToDelete() {
		return linkToDelete;
	}

	public void setLinkToDelete(String linkToDelete) {
		this.linkToDelete = linkToDelete;
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

	public String getTestoClausolaToDelete() {
		return testoClausolaToDelete;
	}

	public void setTestoClausolaToDelete(String testoClausolaToDelete) {
		this.testoClausolaToDelete = testoClausolaToDelete;
	}
	
	
	
	



	
	
	
}
