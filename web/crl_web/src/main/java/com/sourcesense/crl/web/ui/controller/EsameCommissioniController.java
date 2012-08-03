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

import com.sourcesense.crl.business.model.Abbinamento;
import com.sourcesense.crl.business.model.Allegato;
import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.model.Firmatario;
import com.sourcesense.crl.business.model.Link;
import com.sourcesense.crl.business.model.MembroComitatoRistretto;
import com.sourcesense.crl.business.model.OrganismoStatutario;
import com.sourcesense.crl.business.model.Personale;
import com.sourcesense.crl.business.model.Relatore;
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

	private Atto atto = new Atto();

	private Map <String, String> relatori = new HashMap<String, String>();
	private Map <String, String> membriComitato = new HashMap<String, String>();

	private String relatoreToDelete;
	private Date dataNominaRelatore;
	private Date dataUscitaRelatore;
	private String nomeRelatore;

	private String componenteToDelete;
	private Date dataNominaComponente;
	private boolean coordinatore;
	private Date dataUscitaComponente;
	private String nomeComponente;

	private Date dataPresaInCarico;
	private String materia;
	private Date dataScadenza;
	private boolean presenzaComitatoRistretto;
	private Date dataIstituzioneComitato;
	private Date dataFineLavori;

	private List <Relatore> relatoriList = new ArrayList <Relatore>();
	private List <MembroComitatoRistretto> membriComitatoList = new ArrayList <MembroComitatoRistretto>();
	private List <Allegato> testiComitatoRistrettoList = new ArrayList <Allegato>();


	private List <Abbinamento> abbinamentiList = new ArrayList<Abbinamento>(); 

	private String tipoTesto;
	private Date dataAbbinamento;
	private Date dataDisabbinamento;
	private String noteAbbinamento;
	private String oggettoAttoCorrente;


	private String esitoVotazione;
	private String quorum;
	private Date dataSedutaRegistrazioneVotazione;

	private List <Allegato> testiAttoVotatoList = new ArrayList <Allegato>();
	private String testoAttoVotatoToDelete;

	private Date dataSedutaContinuazioneLavori;
	private String motivazioni;
	private Date dataTrasmissione;
	private Date dataRichiestaIscrizione;
	private boolean passaggioDiretto;


	private List <Allegato> emendamentiList = new ArrayList <Allegato>();
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
	private Date dataPresaInCaricoProposta;
	private Date dataIntesa;
	private String esitoVotazioneIntesa;
	private String noteClausolaValutativa;

	private List <Allegato> testiClausolaList = new ArrayList <Allegato>();
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
		// TODO setMembriComitato(personaleServiceManager.findAll())

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));
		setAtto((Atto) attoBean.getAtto().clone());
		
		totaleEmendApprovati();
		totaleEmendPresentati();
		totaleNonApprovati();

		//TODO : init liste
		relatoriList = new ArrayList <Relatore>(atto.getRelatori());
		membriComitatoList = new ArrayList <MembroComitatoRistretto>(atto.getComitatoRistretto().getComponenti());
		testiComitatoRistrettoList = new ArrayList <Allegato>(atto.getComitatoRistretto().getTesti());
		abbinamentiList = new ArrayList<Abbinamento>(atto.getAbbinamenti()); 	
		testiAttoVotatoList = new ArrayList <Allegato>(atto.getTestiAttoVotato());
		emendamentiList = new ArrayList <Allegato>(atto.getEmendamenti());
		testiClausolaList = new ArrayList <Allegato>(atto.getTestiClausola());
		allegatiList = new ArrayList <Allegato>(atto.getAllegatiNoteEsameCommissioni());
		linksList = new ArrayList <Link>(atto.getLinksNoteEsameCommissioni());

		//TODO da cancellare
		membriComitato.put("componente1", "componente1");
		membriComitato.put("componente2", "componente2");
		membriComitato.put("componente3", "componente3");

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


	// Relatori e Comitati ristretti***********************************************

	public void addRelatore() {

		if (nomeRelatore != null
				&& !nomeRelatore.trim().equals("")) {
			if (!checkRelatori()) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Attenzione ! Relatore "
								+ nomeRelatore + " già presente ",
						""));

			} else {
				Relatore relatore = new Relatore();
				relatore.setDescrizione(nomeRelatore);
				relatore.setDataNomina(dataNominaRelatore);
				relatore.setDataUscita(dataUscitaRelatore);
				relatoriList.add(relatore);

				updateRelatoriComitatiRistrettiHandler();
			}
		}
	}

	public void removeRelatore() {

		for (Relatore element : relatoriList) {

			if (element.getDescrizione().equals(relatoreToDelete)) {

				relatoriList.remove(element);
				break;
			}
		}
	}

	private boolean checkRelatori() {

		for (Relatore element : relatoriList) {

			if (element.getDescrizione().equals(nomeRelatore)) {

				return false;
			}

		}

		return true;
	}



	public void addComponente() {

		if (nomeComponente != null && !nomeComponente.trim().equals("")) {
			if (!checkComponenti()) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Attenzione ! Componente "
								+ nomeComponente + " già presente ", ""));

			} else if (coordinatore && checkCoordinatore()){
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Attenzione ! Coordinatore già selezionato ", ""));

			} else {
				MembroComitatoRistretto componente = new MembroComitatoRistretto();
				componente.setDescrizione(nomeComponente);
				componente.setDataNomina(dataNominaComponente);
				componente.setDataUscita(dataUscitaComponente);
				componente.setCoordinatore(coordinatore);
				membriComitatoList.add(componente);

				updateRelatoriComitatiRistrettiHandler();
			}
		}
	}

	public void removeComponente() {

		for (MembroComitatoRistretto element : membriComitatoList) {

			if (element.getDescrizione().equals(componenteToDelete)) {

				membriComitatoList.remove(element);
				break;
			}
		}
	}

	private boolean checkComponenti() {

		for (MembroComitatoRistretto element : membriComitatoList) {

			if (element.getDescrizione().equals(nomeComponente)) {

				return false;
			}
		}
		return true;
	}

	private boolean checkCoordinatore() {

		for (MembroComitatoRistretto element : membriComitatoList) {

			if (element.isCoordinatore()) {

				return true;
			}
		}
		return false;
	}



	// Votazione****************************************************************

	public void uploadTestoAttoVotato() {

	}

	public void removeTestoAttoVotato() {

	}



	// Emendamenti e Clausole*************************************************

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
		this.atto.setLinksNoteEsameCommissioni(linksList);
		//attoServiceManager.salvaNoteAllegatiPresentazione(atto);

		// TODO Service logic
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		attoBean.getAtto().setNoteNoteAllegatiPresentazioneAssegnazione(atto.getNoteNoteAllegatiPresentazioneAssegnazione());
		attoBean.getAtto().setLinksNoteEsameCommissioni(linksList);		

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
		return atto.getMateria();
	}



	public void setMateria(String materia) {
		this.atto.setMateria(materia);
	}



	public Date getDataScadenza() {
		return atto.getDataScadenzaEsameCommissioni();
	}



	public void setDataScadenza(Date dataScadenza) {
		this.atto.setDataScadenzaEsameCommissioni(dataScadenza);
	}



	public Date getDataFineLavori() {
		return atto.getDataFineLavoriEsameCommissioni();
	}



	public void setDataFineLavori(Date dataFineLavori) {
		this.atto.setDataFineLavoriEsameCommissioni(dataFineLavori);
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

	public Date getDataDisabbinamento() {
		return dataDisabbinamento;
	}

	public void setDataDisabbinamento(Date dataDisabbinamento) {
		this.dataDisabbinamento = dataDisabbinamento;
	}



	public String getNoteAbbinamento() {
		return noteAbbinamento;
	}



	public void setNoteAbbinamento(String noteAbbinamento) {
		this.noteAbbinamento = noteAbbinamento;
	}



	public String getOggettoAttoCorrente() {
		return atto.getOggetto();
	}



	public void setOggettoAttoCorrente(String oggettoAttoCorrente) {
		this.atto.setOggetto(oggettoAttoCorrente);
	}



	public String getEsitoVotazione() {
		return atto.getEsitoVotoCommissioneReferente();
	}



	public void setEsitoVotazione(String esitoVotazione) {
		this.atto.setEsitoVotoCommissioneReferente(esitoVotazione);
	}



	public String getQuorum() {
		return atto.getQuorum();
	}



	public void setQuorum(String quorum) {
		this.atto.setQuorum(quorum);
	}



	public Date getDataSedutaRegistrazioneVotazione() {
		return atto.getDataSedutaCommissione();
	}



	public void setDataSedutaRegistrazioneVotazione(
			Date dataSedutaRegistrazioneVotazione) {
		this.atto.setDataSedutaCommissione(dataSedutaRegistrazioneVotazione);
	}



	public Date getDataSedutaContinuazioneLavori() {
		return atto.getDataSedutaContinuazioneInReferente();
	}



	public void setDataSedutaContinuazioneLavori(Date dataSedutaContinuazioneLavori) {
		this.atto.getDataSedutaContinuazioneInReferente();
	}



	public String getMotivazioni() {
		return atto.getMotivazioniContinuazioneInReferente();
	}



	public void setMotivazioni(String motivazioni) {
		this.atto.setMotivazioniContinuazioneInReferente(motivazioni);
	}



	public Date getDataTrasmissione() {
		return atto.getDataTrasmissione();
	}



	public void setDataTrasmissione(Date dataTrasmissione) {
		this.atto.setDataTrasmissione(dataTrasmissione);
	}



	public Date getDataRichiestaIscrizione() {
		return atto.getDataRichiestaIscrizioneAula();
	}



	public void setDataRichiestaIscrizione(Date dataRichiestaIscrizione) {
		this.atto.setDataRichiestaIscrizioneAula(dataRichiestaIscrizione);
	}



	public boolean isPassaggioDiretto() {
		return atto.isPassaggioDirettoInAula();
	}



	public void setPassaggioDiretto(boolean passaggioDiretto) {
		this.atto.setPassaggioDirettoInAula(passaggioDiretto);
	}



	public int getNumEmendPresentatiMaggior() {
		return atto.getNumEmendPresentatiMaggior();
	}



	public void setNumEmendPresentatiMaggior(int numEmendPresentatiMaggior) {
		this.atto.setNumEmendPresentatiMaggior(numEmendPresentatiMaggior);
	}



	public int getNumEmendPresentatiMinor() {
		return atto.getNumEmendPresentatiMinor();
	}



	public void setNumEmendPresentatiMinor(int numEmendPresentatiMinor) {
		this.atto.setNumEmendPresentatiMinor(numEmendPresentatiMinor);
	}



	public int getNumEmendPresentatiGiunta() {
		return atto.getNumEmendPresentatiGiunta();
	}



	public void setNumEmendPresentatiGiunta(int numEmendPresentatiGiunta) {
		this.atto.setNumEmendPresentatiGiunta(numEmendPresentatiGiunta);
	}



	public int getNumEmendPresentatiMisto() {
		return atto.getNumEmendPresentatiMisto();
	}



	public void setNumEmendPresentatiMisto(int numEmendPresentatiMisto) {
		this.atto.setNumEmendPresentatiMisto(numEmendPresentatiMisto);
	}



	public int getNumEmendPresentatiTotale() {
		return numEmendPresentatiTotale;
	}



	public void setNumEmendPresentatiTotale(int numEmendPresentatiTotale) {
		this.numEmendPresentatiTotale = numEmendPresentatiTotale;
	}



	public int getNumEmendApprovatiMaggior() {
		return atto.getNumEmendApprovatiMaggior();
	}



	public void setNumEmendApprovatiMaggior(int numEmendApprovatiMaggior) {
		this.atto.setNumEmendApprovatiMaggior(numEmendApprovatiMaggior);
	}



	public int getNumEmendApprovatiMinor() {
		return atto.getNumEmendApprovatiMinor();
	}



	public void setNumEmendApprovatiMinor(int numEmendApprovatiMinor) {
		this.atto.setNumEmendApprovatiMinor(numEmendApprovatiMinor);
	}



	public int getNumEmendApprovatiGiunta() {
		return atto.getNumEmendApprovatiGiunta();
	}



	public void setNumEmendApprovatiGiunta(int numEmendApprovatiGiunta) {
		this.atto.setNumEmendApprovatiGiunta(numEmendApprovatiGiunta);
	}



	public int getNumEmendApprovatiMisto() {
		return atto.getNumEmendApprovatiMisto();
	}



	public void setNumEmendApprovatiMisto(int numEmendApprovatiMisto) {
		this.atto.setNumEmendApprovatiMisto(numEmendApprovatiMisto);
	}



	public int getNumEmendApprovatiTotale() {
		return numEmendApprovatiTotale;
	}



	public void setNumEmendApprovatiTotale(int numEmendApprovatiTotale) {
		this.numEmendApprovatiTotale = numEmendApprovatiTotale;
	}



	public int getDecaduti() {
		return atto.getDecaduti();
	}



	public void setDecaduti(int decaduti) {
		this.atto.setDecaduti(decaduti);
	}



	public int getRitirati() {
		return atto.getRitirati();
	}



	public void setRitirati(int ritirati) {
		this.atto.setRitirati(ritirati);
	}



	public int getRespinti() {
		return atto.getRespinti();
	}



	public void setRespinti(int respinti) {
		this.atto.setRespinti(respinti);
	}



	public int getTotaleNonApprovati() {
		return totaleNonApprovati;
	}



	public void setTotaleNonApprovati(int totaleNonApprovati) {
		this.totaleNonApprovati = totaleNonApprovati;
	}



	public String getNoteEmendamenti() {
		return atto.getNoteEmendamenti();
	}



	public void setNoteEmendamenti(String noteEmendamenti) {
		this.atto.setNoteEmendamenti(noteEmendamenti);
	}



	public Date getDataPresaInCaricoProposta() {
		return atto.getDataPresaInCaricoProposta();
	}



	public void setDataPresaInCaricoProposta(Date dataPresaInCaricoProposta) {
		this.atto.setDataPresaInCaricoProposta(dataPresaInCaricoProposta);
	}



	public Date getDataIntesa() {
		return atto.getDataIntesa();
	}



	public void setDataIntesa(Date dataIntesa) {
		this.atto.setDataIntesa(dataIntesa);
	}



	public String getEsitoVotazioneIntesa() {
		return atto.getEsitoVotazioneIntesa();
	}



	public void setEsitoVotazioneIntesa(String esitoVotazioneIntesa) {
		this.atto.setEsitoVotazioneIntesa(esitoVotazioneIntesa);
	}



	public String getNoteClausolaValutativa() {
		return atto.getNoteClausolaValutativa();
	}



	public void setNoteClausolaValutativa(String noteClausolaValutativa) {
		this.atto.setNoteClausolaValutativa(noteClausolaValutativa);
	}	



	public String getNoteGenerali() {
		return atto.getNoteGeneraliEsameCommissioni();
	}



	public void setNoteGenerali(String noteGenerali) {
		this.atto.setNoteGeneraliEsameCommissioni(noteGenerali);
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



	public List<MembroComitatoRistretto> getMembriComitatoList() {
		return membriComitatoList;
	}



	public void setMembriComitatoList(List<MembroComitatoRistretto> membriComitatoList) {
		this.membriComitatoList = membriComitatoList;
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

	public String getRelatoreToDelete() {
		return relatoreToDelete;
	}

	public void setRelatoreToDelete(String relatoreToDelete) {
		this.relatoreToDelete = relatoreToDelete;
	}	

	public Date getDataNominaRelatore() {
		return dataNominaRelatore;
	}

	public void setDataNominaRelatore(Date dataNominaRelatore) {
		this.dataNominaRelatore = dataNominaRelatore;
	}

	public Date getDataUscitaRelatore() {
		return dataUscitaRelatore;
	}

	public void setDataUscitaRelatore(Date dataUscitaRelatore) {
		this.dataUscitaRelatore = dataUscitaRelatore;
	}

	public String getNomeRelatore() {
		return nomeRelatore;
	}

	public void setNomeRelatore(String nomeRelatore) {
		this.nomeRelatore = nomeRelatore;
	}

	public List<Allegato> getTestiComitatoRistrettoList() {
		return testiComitatoRistrettoList;
	}

	public void setTestiComitatoRistrettoList(
			List<Allegato> testiComitatoRistrettoList) {
		this.testiComitatoRistrettoList = testiComitatoRistrettoList;
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

	public List<Allegato> getTestiClausolaList() {
		return testiClausolaList;
	}

	public void setTestiClausolaList(List<Allegato> testiClausolaList) {
		this.testiClausolaList = testiClausolaList;
	}

	public String getComponenteToDelete() {
		return componenteToDelete;
	}

	public void setComponenteToDelete(String componenteToDelete) {
		this.componenteToDelete = componenteToDelete;
	}

	public Date getDataNominaComponente() {
		return dataNominaComponente;
	}

	public void setDataNominaComponente(Date dataNominaComponente) {
		this.dataNominaComponente = dataNominaComponente;
	}

	public boolean isCoordinatore() {
		return coordinatore;
	}

	public void setCoordinatore(boolean coordinatore) {
		this.coordinatore = coordinatore;
	}

	public Date getDataUscitaComponente() {
		return dataUscitaComponente;
	}

	public void setDataUscitaComponente(Date dataUscitaComponente) {
		this.dataUscitaComponente = dataUscitaComponente;
	}

	public String getNomeComponente() {
		return nomeComponente;
	}

	public void setNomeComponente(String nomeComponente) {
		this.nomeComponente = nomeComponente;
	}

	public boolean isPresenzaComitatoRistretto() {
		return atto.isPresenzaComitatoRistretto();
	}

	public void setPresenzaComitatoRistretto(boolean presenzaComitatoRistretto) {
		this.atto.setPresenzaComitatoRistretto(presenzaComitatoRistretto);
	}

	public Date getDataIstituzioneComitato() {
		return atto.getDataIstituzioneComitato();
	}

	public void setDataIstituzioneComitato(Date dataIstituzioneComitato) {
		this.atto.setDataIstituzioneComitato(dataIstituzioneComitato);
	}

	public List <Abbinamento> getAbbinamentiList() {
		return abbinamentiList;
	}

	public void setAbbinamentiList(List <Abbinamento> abbinamentiList) {
		this.abbinamentiList = abbinamentiList;
	}

	public int getNonAmmissibili() {
		return atto.getNonAmmissibili();
	}

	public void setNonAmmissibili(int nonAmmissibili) {
		this.atto.setNonAmmissibili(nonAmmissibili);
	}

}
