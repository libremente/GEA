package com.sourcesense.crl.web.ui.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import com.sourcesense.crl.business.model.Aula;
import com.sourcesense.crl.business.model.Commissione;
import com.sourcesense.crl.business.model.EsameAula;
import com.sourcesense.crl.business.model.EsameCommissione;
import com.sourcesense.crl.business.model.Firmatario;
import com.sourcesense.crl.business.model.GestioneAbbinamento;
import com.sourcesense.crl.business.model.GruppoUtente;
import com.sourcesense.crl.business.model.Link;
import com.sourcesense.crl.business.model.Componente;
import com.sourcesense.crl.business.model.OrganismoStatutario;
import com.sourcesense.crl.business.model.Passaggio;
import com.sourcesense.crl.business.model.Personale;
import com.sourcesense.crl.business.model.Relatore;
import com.sourcesense.crl.business.model.StatoAtto;
import com.sourcesense.crl.business.model.Target;
import com.sourcesense.crl.business.model.TestoAtto;
import com.sourcesense.crl.business.service.AbbinamentoServiceManager;
import com.sourcesense.crl.business.service.AttoServiceManager;
import com.sourcesense.crl.business.service.CommissioneServiceManager;
import com.sourcesense.crl.business.service.PersonaleServiceManager;
import com.sourcesense.crl.util.CRLMessage;
import com.sourcesense.crl.util.Clonator;
import com.sourcesense.crl.web.ui.beans.AttoBean;
import com.sourcesense.crl.web.ui.beans.UserBean;

@ManagedBean(name = "esameCommissioniController")
@ViewScoped
public class EsameCommissioniController {

	@ManagedProperty(value = "#{personaleServiceManager}")
	private PersonaleServiceManager personaleServiceManager;

	@ManagedProperty(value = "#{attoServiceManager}")
	private AttoServiceManager attoServiceManager;

	@ManagedProperty(value = "#{commissioneServiceManager}")
	private CommissioneServiceManager commissioneServiceManager;

	@ManagedProperty(value = "#{abbinamentoServiceManager}")
	private AbbinamentoServiceManager abbinamentoServiceManager;

	private Atto atto = new Atto();

	private boolean readonly = false;

	private List<Commissione> commissioniList = new ArrayList<Commissione>();
	// Commissione dell utente loggato
	private Commissione commissioneUser = new Commissione();
	// Passaggio selezionato
	private Passaggio passaggio;
	private String passaggioSelected;

	/*private Map<String, String> relatori = new HashMap<String, String>();
	private Map<String, String> membriComitato = new HashMap<String, String>();*/

	private List<Relatore> relatori = new ArrayList<Relatore>();
	private List<Relatore> membriComitato = new ArrayList<Relatore>();
	//private Map<String, String> membriComitato = new HashMap<String, String>();
	
	private String relatoreToDelete;
	private Date dataNominaRelatore;
	private Date dataUscitaRelatore;
	private String nomeRelatore;

	private boolean currentFilePubblico;
	private Date currentDataSeduta;

	private String componenteToDelete;
	private Date dataNominaComponente;
	private boolean coordinatore;
	private Date dataUscitaComponente;
	private String nomeComponente;

	private Date dataPresaInCarico;
	private String materia;
	private Date dataScadenza;
	private String messaggioGiorniScadenza;
	private boolean presenzaComitatoRistretto;
	private Date dataIstituzioneComitato;
	private Date dataFineLavori;

	private List<Relatore> relatoriList = new ArrayList<Relatore>();
	private List<Componente> membriComitatoList = new ArrayList<Componente>();

	private String testoComitatoToDelete;

	private List<Abbinamento> abbinamentiList = new ArrayList<Abbinamento>();
	private String abbinamentoToDelete;
	private String idAbbinamentoSelected;
	private Abbinamento abbinamentoSelected;

	private String tipoTesto;
	private Date dataAbbinamento;
	private Date dataDisabbinamento;
	private String noteAbbinamento;
	private String oggettoAttoCorrente;

	private String esitoVotazione;
	private String quorum;
	private Date dataSedutaRegistrazioneVotazione;

	private List<TestoAtto> testiAttoVotatoList = new ArrayList<TestoAtto>();
	private List<Allegato> testiComitatoRistrettoList = new ArrayList<Allegato>();
	private List<Allegato> emendamentiList = new ArrayList<Allegato>();
	private List<Allegato> testiClausolaList = new ArrayList<Allegato>();
	private List<Allegato> allegatiList = new ArrayList<Allegato>();

	private String testoAttoVotatoToDelete;
	private Date dataSedutaContinuazioneLavori;
	private String motivazioni;
	private Date dataTrasmissione;
	private Date dataRichiestaIscrizione;
	private boolean passaggioDiretto;
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
	private int numEmendApprovatiCommissione;
	private int numEmendPresentatiCommissione;

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

	private String testoClausolaToDelete;
	private String noteGenerali;

	private List<Link> linksList = new ArrayList<Link>();

	private String allegatoToDelete;
	private String linkToDelete;

	private String nomeLink;
	private String urlLink;
	private boolean pubblico;

	private Date dataSedutaStralcio;
	private Date dataIniziativaStralcio;
	private Date dataStralcio;
	private String articoli;
	private String noteStralcio;
	private String quorumStralcio;

	private String statoCommitRelatori = CRLMessage.COMMIT_DONE;
	private String statoCommitComitatoRistretto = CRLMessage.COMMIT_DONE;
	private String statoCommitPresaInCarico = CRLMessage.COMMIT_DONE;
	private String statoCommitFineLavori = CRLMessage.COMMIT_DONE;
	private String statoCommitAbbinamentieDisabbinamenti = CRLMessage.COMMIT_DONE;
	private String statoCommitOggettoAttoCorrente = CRLMessage.COMMIT_DONE;
	private String statoCommitRegistrazioneVotazione = CRLMessage.COMMIT_DONE;
	private String statoCommitContinuazioneLavori = CRLMessage.COMMIT_DONE;
	private String statoCommitTrasmissione = CRLMessage.COMMIT_DONE;
	private String statoCommitEmendamentiClausole = CRLMessage.COMMIT_DONE;
	private String statoCommitNote = CRLMessage.COMMIT_DONE;
	private String statoCommitStralci = CRLMessage.COMMIT_DONE;

	@PostConstruct
	protected void init() {

		// Liste per combo
		setRelatori(personaleServiceManager.getAllRelatori());
	    setMembriComitato(personaleServiceManager.getAllMembriComitato());

		// Bean atto di sessione
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		// set atto di classe clone di attoBean
		setAtto((Atto) attoBean.getAtto().clone());

		// Utente di sessione e Commissione di appartenenza
		UserBean userBean = ((UserBean) context.getExternalContext()
				.getSessionMap().get("userBean"));

		// Ricavo le commisioni dall'ultimo passaggio
		setCommissioniList(Clonator.cloneList(attoBean.getLastPassaggio()
				.getCommissioni()));

		// I dati della commissione relativa all'utente loggato
		// solo se admin prende la referente o deliberante
		Commissione commTemp = findCommissione(userBean.getUser()
				.getSessionGroup().getNome());
		if (commTemp == null) {

			commTemp = attoBean.getCommissioneReferente();

			if (commTemp == null) {

				commTemp = attoBean.getCommissioneDeliberante();

				if (commTemp == null) {
					commTemp = new Commissione();
				}

			}

		}

		setCommissioneUser(commTemp);
		setPassaggioSelected(attoBean.getLastPassaggio().getNome());
		loadData(attoBean.getLastPassaggio(), attoBean);

	}

	private boolean canChangeStatoAtto() {

		return (commissioneUser.getRuolo().equals(Commissione.RUOLO_REFERENTE)
				|| commissioneUser.getRuolo().equalsIgnoreCase(
						Commissione.RUOLO_REDIGENTE) || commissioneUser
				.getRuolo().equalsIgnoreCase(Commissione.RUOLO_DELIBERANTE) || commissioneUser
				.getRuolo().equalsIgnoreCase(Commissione.RUOLO_COREFERENTE));

	}

	public void changePassaggio() {

		Passaggio passaggioSelected = null;

		int conta = 0;
		for (Passaggio passaggioRec : this.atto.getPassaggi()) {

			conta++;

			if (passaggioRec.getNome().equalsIgnoreCase(this.passaggioSelected)) {

				passaggioSelected = passaggioRec;
				break;
			}

		}

		if (conta < this.atto.getPassaggi().size()) {

			setReadonly(true);
		} else {
			setReadonly(false);

		}

		// Ricavo le commisioni dall'ultimo passaggio
		setCommissioniList(Clonator.cloneList(passaggioSelected
				.getCommissioni()));

		FacesContext context = FacesContext.getCurrentInstance();

		// Utente di sessione e Commissione di appartenenza
		UserBean userBean = ((UserBean) context.getExternalContext()
				.getSessionMap().get("userBean"));

		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		// I dati della commissione relativa all'utente loggato
		Commissione commTemp = findCommissione(userBean.getUser()
				.getSessionGroup().getNome());
		if (commTemp == null) {

			commTemp = attoBean.getCommissioneReferente();

			if (commTemp == null) {

				commTemp = attoBean.getCommissioneDeliberante();

				if (commTemp == null) {
					commTemp = new Commissione();
				}

			}

		}

		setCommissioneUser(commTemp);

		loadData(passaggioSelected, attoBean);
	}
	
	
	private Commissione findCommissione(String nome) {

		for (Commissione element : commissioniList) {
			if (element.getDescrizione().equals(nome)) {
				return element;
			}
		}
		return null;
	}

	private void loadData(Passaggio passaggioIn, AttoBean attoBean) {

		// Controllo per admin
		setDataPresaInCarico(commissioneUser.getDataPresaInCarico());
		setMateria(commissioneUser.getMateria());
		setDataScadenza(commissioneUser.getDataScadenza());
		setPresenzaComitatoRistretto(commissioneUser
				.isPresenzaComitatoRistretto());
		setDataIstituzioneComitato(commissioneUser.getDataIstituzioneComitato());
		setDataFineLavori(commissioneUser.getDataFineLavoriComitato());
		setDataTrasmissione(commissioneUser.getDataTrasmissione());
		setDataRichiestaIscrizione(commissioneUser.getDataTrasmissione());
		setPassaggioDiretto(commissioneUser.isPassaggioDirettoInAula());

		totaleEmendApprovati();
		totaleEmendPresentati();
		totaleNonApprovati();

		// TODO : init liste
		abbinamentiList = Clonator.cloneList(passaggioIn.getAbbinamenti());
		relatoriList = Clonator.cloneList(commissioneUser.getRelatori());
		membriComitatoList = Clonator.cloneList(commissioneUser
				.getComitatoRistretto().getComponenti());
		testiComitatoRistrettoList = Clonator.cloneList(commissioneUser
				.getAllegatiNoteEsameCommissioni());
		testiAttoVotatoList = Clonator.cloneList(commissioneUser
				.getTestiAttoVotatoEsameCommissioni());
		emendamentiList = Clonator.cloneList(commissioneUser
				.getEmendamentiEsameCommissioni());
		testiClausolaList = Clonator.cloneList(commissioneUser
				.getTestiClausola());
		allegatiList = Clonator.cloneList(attoBean.getAllegatiCommissioni());
		linksList = Clonator.cloneList(commissioneUser
				.getLinksNoteEsameCommissione());

		confrontaDataScadenza();

	}

	

	public void updateRelatoriHandler() {
		setStatoCommitRelatori(CRLMessage.COMMIT_UNDONE);
	}

	public void updateComitatoRistrettoHandler() {
		setStatoCommitComitatoRistretto(CRLMessage.COMMIT_UNDONE);
	}

	public void updatePresaInCaricoHandler() {
		setStatoCommitPresaInCarico(CRLMessage.COMMIT_UNDONE);
	}

	public void updateFineLavoriHandler() {
		setStatoCommitFineLavori(CRLMessage.COMMIT_DONE);
	}

	public void updateAbbinamentiHandler() {
		setStatoCommitAbbinamentieDisabbinamenti(CRLMessage.COMMIT_UNDONE);
	}

	public void updateOggettoAttoCorrenteHandler() {
		setStatoCommitOggettoAttoCorrente(CRLMessage.COMMIT_UNDONE);
	}

	public void updateRegistrazioneVotazioneHandler() {
		setStatoCommitRegistrazioneVotazione(CRLMessage.COMMIT_UNDONE);
	}

	public void updateContinuazioneLavoriHandler() {
		setStatoCommitContinuazioneLavori(CRLMessage.COMMIT_UNDONE);
	}

	public void updateTrasmissioneHandler() {
		setStatoCommitTrasmissione(CRLMessage.COMMIT_UNDONE);
	}

	public void updateEmendamentiClausoleHandler() {
		setStatoCommitEmendamentiClausole(CRLMessage.COMMIT_UNDONE);
	}

	public void updateNoteHandler() {
		setStatoCommitNote(CRLMessage.COMMIT_UNDONE);
	}

	public void updateStralciHandler() {
		setStatoCommitStralci(CRLMessage.COMMIT_UNDONE);
	}

	public void changeTabHandler() {

		if (statoCommitRelatori.equals(CRLMessage.COMMIT_UNDONE)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Attenzione ! Le modifiche ai Relatori non sono state salvate ",
							""));
		}

		if (statoCommitComitatoRistretto.equals(CRLMessage.COMMIT_UNDONE)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Attenzione ! Le modifiche al Comitato Ristretto non sono state salvate ",
							""));
		}

		if (statoCommitPresaInCarico.equals(CRLMessage.COMMIT_UNDONE)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Attenzione ! Le modifiche della Presa in Carico non sono state salvate ",
							""));
		}

		if (statoCommitFineLavori.equals(CRLMessage.COMMIT_UNDONE)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Attenzione ! Le modifiche relative alla Fine dei Lavori non sono state salvate ",
							""));
		}

		if (statoCommitAbbinamentieDisabbinamenti
				.equals(CRLMessage.COMMIT_UNDONE)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Attenzione ! Le modifiche di Abbinamenti e Disabbinamenti non sono state salvate ",
							""));
		}

		if (statoCommitOggettoAttoCorrente.equals(CRLMessage.COMMIT_UNDONE)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Attenzione ! Le modifiche all'Oggetto dell'Atto corrente non sono state salvate ",
							""));
		}

		if (statoCommitRegistrazioneVotazione.equals(CRLMessage.COMMIT_UNDONE)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Attenzione ! Le modifiche della Registrazione Votazione non sono state salvate ",
							""));
		}

		if (statoCommitContinuazioneLavori.equals(CRLMessage.COMMIT_UNDONE)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Attenzione ! Le modifiche sulla Continuazione dei lavori in referente non sono state salvate ",
							""));
		}

		if (statoCommitTrasmissione.equals(CRLMessage.COMMIT_UNDONE)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Attenzione ! Le modifiche sulla Trasmissione non sono state salvate ",
							""));
		}

		if (statoCommitEmendamentiClausole.equals(CRLMessage.COMMIT_UNDONE)) {
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

		if (statoCommitStralci.equals(CRLMessage.COMMIT_UNDONE)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Attenzione ! Le modifiche a Stralci non sono state salvate ",
							""));
		}

	}

	// Relatori e Comitati
	// ristretti***********************************************
	public void presaInCarico() {

		commissioneUser.setDataPresaInCarico(getDataPresaInCarico());
		commissioneUser.setMateria(materia);
		commissioneUser.setDataScadenza(dataScadenza);
		commissioneUser.setStato(Commissione.STATO_IN_CARICO);

		// Puo essere modificato solo l'ultimo passaggio
		atto.getPassaggi().get(atto.getPassaggi().size() - 1)
				.setCommissioni(getCommissioniList());

		if (canChangeStatoAtto()) {
			atto.setStato(StatoAtto.PRESO_CARICO_COMMISSIONE);
		}

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		Target target = new Target();
		target.setCommissione(commissioneUser.getDescrizione());
		target.setPassaggio(attoBean.getLastPassaggio().getNome());
		EsameCommissione esameCommissione = new EsameCommissione();
		esameCommissione.setAtto(atto);
		esameCommissione.setTarget(target);

		commissioneServiceManager
				.salvaPresaInCaricoEsameCommissioni(esameCommissione);

		UserBean userBean = ((UserBean) context.getExternalContext()
				.getSessionMap().get("userBean"));

		attoBean.getLastPassaggio().setCommissioni(commissioniList);

		if (canChangeStatoAtto()) {
			attoBean.setStato(StatoAtto.PRESO_CARICO_COMMISSIONE);
		}

		confrontaDataScadenza();

		String numeroAtto = attoBean.getNumeroAtto();
		setStatoCommitPresaInCarico(CRLMessage.COMMIT_DONE);
		context.addMessage(null, new FacesMessage("Atto " + numeroAtto
				+ " preso in carico con successo dall' utente "
				+ userBean.getUser().getUsername(), ""));
	}

	public void addRelatore() {

		if (nomeRelatore != null && !nomeRelatore.trim().equals("")) {
			if (!checkRelatori()) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Attenzione ! Relatore "
								+ nomeRelatore + " già presente ", ""));

			} else {
				Relatore relatore = new Relatore();
				relatore.setDescrizione(nomeRelatore);
				relatore.setDataNomina(dataNominaRelatore);
				relatore.setDataUscita(dataUscitaRelatore);
				relatoriList.add(relatore);

				updateRelatoriHandler();
			}
		}
	}

	public void removeRelatore() {

		for (Relatore element : relatoriList) {

			if (element.getDescrizione().equals(relatoreToDelete)) {

				relatoriList.remove(element);
				updateRelatoriHandler();
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

	public void confermaRelatori() {

		// if (relatoriList.size() > 0) {

		commissioneUser.setRelatori(relatoriList);

		atto.getPassaggi().get(atto.getPassaggi().size() - 1)
				.setCommissioni(commissioniList);

		if (canChangeStatoAtto()) {

			if (checkStatiRelatori()) {
				atto.setStato(StatoAtto.NOMINATO_RELATORE);
				commissioneUser.setStato(Commissione.STATO_NOMINATO_RELATORE);
			} else {
				atto.setStato(StatoAtto.PRESO_CARICO_COMMISSIONE);
				commissioneUser.setStato(Commissione.STATO_IN_CARICO);
			}

		}
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		Target target = new Target();
		target.setCommissione(commissioneUser.getDescrizione());
		target.setPassaggio(attoBean.getLastPassaggio().getNome());
		EsameCommissione esameCommissione = new EsameCommissione();
		esameCommissione.setAtto(atto);
		esameCommissione.setTarget(target);

		commissioneServiceManager
				.salvaRelatoriEsameCommissioni(esameCommissione);

		attoBean.getLastPassaggio().setCommissioni(
				Clonator.cloneList(commissioniList));

		if (canChangeStatoAtto()) {
			if (checkStatiRelatori()) {
				attoBean.setStato(StatoAtto.NOMINATO_RELATORE);
			} else {
				attoBean.setStato(StatoAtto.PRESO_CARICO_COMMISSIONE);

			}
		}

		setStatoCommitRelatori(CRLMessage.COMMIT_DONE);
		context.addMessage(null, new FacesMessage(
				"Relatori salvati con successo", ""));

		// }
	}

	private boolean checkStatiRelatori() {

		int annullo = 0;

		for (Relatore relatore : relatoriList) {

			if (relatore.getDataUscita() != null) {
				annullo++;

			}
		}

		return annullo < relatoriList.size();

	}

	private boolean isNominatoRelatore() {

		int usciti = 0;

		for (Relatore element : relatoriList) {

			if (element.getDataUscita() != null) {

				usciti++;
			}
		}
		return (usciti < relatoriList.size());
	}

	public void addComponente() {

		if (nomeComponente != null && !nomeComponente.trim().equals("")) {
			if (!checkComponenti()) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Attenzione ! Componente "
								+ nomeComponente + " già presente ", ""));

			} else if (coordinatore && checkCoordinatore()) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Attenzione ! Coordinatore già selezionato ", ""));

			} else {
				Componente componente = new Componente();
				componente.setDescrizione(nomeComponente);
				componente.setDataNomina(dataNominaComponente);
				componente.setDataUscita(dataUscitaComponente);
				componente.setCoordinatore(coordinatore);
				membriComitatoList.add(componente);

				updateComitatoRistrettoHandler();
			}
		}
	}

	public void removeComponente() {

		for (Componente element : membriComitatoList) {

			if (element.getDescrizione().equals(componenteToDelete)) {

				membriComitatoList.remove(element);
				updateComitatoRistrettoHandler();
				break;
			}
		}
	}

	private boolean checkComponenti() {

		for (Componente element : membriComitatoList) {

			if (element.getDescrizione().equals(nomeComponente)) {

				return false;
			}
		}
		return true;
	}

	private boolean checkCoordinatore() {

		for (Componente element : membriComitatoList) {

			if (element.isCoordinatore()) {

				return true;
			}
		}
		return false;
	}

	private boolean checkOneMembroAttivo(AttoBean attoBean) {

		for (Componente element : membriComitatoList) {

			if (element.getDataUscita() == null) {

				return true;
			}
		}

		// Update di un componente persistito
		/*
		 * if (!ritorno &&
		 * attoBean.getWorkingCommissione(commissioneUser.getDescrizione
		 * ()).getComitatoRistretto().getComponenti().size() ==
		 * membriComitatoList.size() && membriComitatoList.size() > 0) { ritorno
		 * = true; }
		 */

		return false;
	}

	public void confermaComitatoRistretto() {

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		boolean isOneMembroAttivo = checkOneMembroAttivo(attoBean);

		commissioneUser.getComitatoRistretto().setComponenti(membriComitatoList);
		commissioneUser.setDataIstituzioneComitato(getDataIstituzioneComitato());

		Target target = new Target();
		target.setCommissione(commissioneUser.getDescrizione());
		target.setPassaggio(attoBean.getLastPassaggio().getNome());
        
		boolean statoChange = atto.getStato().equals(StatoAtto.ASSEGNATO_COMMISSIONE) || 
				atto.getStato().equals(StatoAtto.PRESO_CARICO_COMMISSIONE) ||
				atto.getStato().equals(StatoAtto.NOMINATO_RELATORE); 
		
		if (canChangeStatoAtto() && isOneMembroAttivo) {
			
					if(statoChange){
			  atto.setStato(StatoAtto.LAVORI_COMITATO_RISTRETTO);
			}
			
			commissioneUser.setStato(Commissione.STATO_COMITATO_RISTRETTO);
		
		}

		atto.getPassaggi().get(atto.getPassaggi().size() - 1)
				.setCommissioni(commissioniList);

		EsameCommissione esameCommissione = new EsameCommissione();
		esameCommissione.setAtto(atto);
		esameCommissione.setTarget(target);
		commissioneServiceManager
				.salvaComitatoRistrettoEsameCommissioni(esameCommissione);

		if (canChangeStatoAtto() && isNominatoRelatore()) {
			if(statoChange){
			attoBean.setStato(StatoAtto.LAVORI_COMITATO_RISTRETTO);
			}
		}
		attoBean.getLastPassaggio().setCommissioni(
				Clonator.cloneList(commissioniList));

		setStatoCommitComitatoRistretto(CRLMessage.COMMIT_DONE);
		context.addMessage(null, new FacesMessage(
				"Comitato ristretto salvato con successo", ""));

	}

	public void uploadTestoComitatoRistretto(FileUploadEvent event) {

		String fileName = event.getFile().getFileName();
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		if (!checkTestoComitato(fileName)) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Attenzione ! Il file "
							+ fileName + " è già stato allegato ", ""));

		} else {

			Allegato allegatoRet = new Allegato();
			allegatoRet.setNome(event.getFile().getFileName());
			allegatoRet.setPubblico(currentFilePubblico);
			allegatoRet.setDataSeduta(currentDataSeduta);
			allegatoRet.setCommissione(commissioneUser.getDescrizione());
			allegatoRet.setPassaggio(attoBean.getLastPassaggio().getNome());

			try {
				allegatoRet = commissioneServiceManager
						.uploadTestoComitatoRistretto(((AttoBean) FacesContext
								.getCurrentInstance().getExternalContext()
								.getSessionMap().get("attoBean")).getAtto(),
								event.getFile().getInputstream(), allegatoRet);

			} catch (IOException e) {
				e.printStackTrace();
			}

			setCurrentDataSeduta(null);
			setCurrentFilePubblico(false);

			attoBean.getWorkingCommissione(commissioneUser.getDescrizione())
					.getAllegatiNoteEsameCommissioni().add(allegatoRet);
			testiComitatoRistrettoList.add(allegatoRet);
		}
	}

	private boolean checkTestoComitato(String fileName) {

		// for (TestoAtto element : testiComitatoRistrettoList) {
		//
		// if (element.getDescrizione().equals(fileName)) {
		//
		// return false;
		// }
		//
		// }

		return true;
	}

	public void removeTestoComitato() {

		for (Allegato element : testiComitatoRistrettoList) {

			if (element.getId().equals(testoComitatoToDelete)) {
				// TODO Alfresco delete
				testiComitatoRistrettoList.remove(element);
				break;
			}
		}
	}

	public void confermaFineLavori() {
		commissioneUser.setDataFineLavoriComitato(getDataFineLavori());
		// commissioneUser.setStato(Commissione.STATO_ANNULLATO);
		atto.getPassaggi().get(atto.getPassaggi().size() - 1)
				.setCommissioni(getCommissioniList());
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		Target target = new Target();
		target.setCommissione(commissioneUser.getDescrizione());
		target.setPassaggio(attoBean.getLastPassaggio().getNome());
		EsameCommissione esameCommissione = new EsameCommissione();
		esameCommissione.setAtto(atto);
		esameCommissione.setTarget(target);
		commissioneServiceManager
				.salvaFineLavoriEsameCommissioni(esameCommissione);

		attoBean.getLastPassaggio().setCommissioni(
				Clonator.cloneList(getCommissioniList()));

		setStatoCommitPresaInCarico(CRLMessage.COMMIT_DONE);
		context.addMessage(null, new FacesMessage(
				"Relatori e Comitati Ristretti salvati con successo", ""));
	}

	// Abbinamenti***************************************************************

	public void addAbbinamento(String idAbbinamento) {

		if (!idAbbinamento.trim().equals("")) {
			if (!checkAbbinamenti(idAbbinamento)) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Attenzione ! Atto già abbinato ", ""));

			} else {

				Atto attoDaAbbinare = attoServiceManager
						.findById(idAbbinamento);

				Abbinamento abbinamento = new Abbinamento();
				abbinamento.setIdAtto(atto.getId());
				abbinamento.setIdAttoAbbinato(idAbbinamento);
				abbinamento.setAbbinato(true);
				abbinamento.setNumeroAttoAbbinato(attoDaAbbinare.getNumeroAtto());
				abbinamento.setTipoAttoAbbinato(attoDaAbbinare.getTipoAtto());
				abbinamentiList.add(abbinamento);
				setIdAbbinamentoSelected(idAbbinamento);
				showAbbinamentoDetail();
				updateAbbinamentiHandler();

				
			}
		}
	}

	public void removeAbbinamento() {
		
		for (Abbinamento element : abbinamentiList) {

			if (element.getIdAttoAbbinato().equals(abbinamentoToDelete)) {

				abbinamentiList.remove(element);
				updateAbbinamentiHandler();
				element.setAbbinato(false);
				break;
			}
		}
	}

	private boolean checkAbbinamenti(String idAbbinamento) {

		for (Abbinamento element : abbinamentiList) {

			if (element.getIdAttoAbbinato().equals(idAbbinamento)) {

				return false;
			}
		}
		return true;
	}

	public void showAbbinamentoDetail() {

		setAbbinamentoSelected(findAbbinamento(idAbbinamentoSelected));

		if (abbinamentoSelected != null) {
			Atto attoDaAbbinare = attoServiceManager
					.findById(abbinamentoSelected.getIdAttoAbbinato());

			// abbinamentoSelected.setAtto(attoDaAbbinare);

			setTipoTesto(abbinamentoSelected.getTipoTesto());
			setDataAbbinamento(abbinamentoSelected.getDataAbbinamento());
			setDataDisabbinamento(abbinamentoSelected.getDataDisabbinamento());
			setNoteAbbinamento(abbinamentoSelected.getNote());
		}

		else {
			setTipoTesto("");
			setDataAbbinamento(null);
			setDataDisabbinamento(null);
			setNoteAbbinamento("");
		}
	}

	private Abbinamento findAbbinamento(String id) {

		for (Abbinamento element : abbinamentiList) {

			if (element.getIdAttoAbbinato().equals(id)) {

				return element;

			}

		}

		return null;
	}

	public void salvaAbbinamentoDisabbinamento() {

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		if (dataAbbinamento == null && dataDisabbinamento != null) {

			setStatoCommitAbbinamentieDisabbinamenti(CRLMessage.COMMIT_DONE);
			context.addMessage(null, new FacesMessage(
					"Impossibile disabbinare un atto non ancora abbinato", ""));

		}

		else if (dataAbbinamento != null && dataDisabbinamento != null) {

			abbinamentoSelected.setTipoTesto(tipoTesto);
			abbinamentoSelected.setDataAbbinamento(dataAbbinamento);
			abbinamentoSelected.setDataDisabbinamento(dataDisabbinamento);
			abbinamentoSelected.setNote(noteAbbinamento);

			GestioneAbbinamento gestioneAbbinamento = new GestioneAbbinamento();
			Target target = new Target();
			target.setPassaggio(attoBean.getLastPassaggio().getNome());
			gestioneAbbinamento.setAbbinamento(abbinamentoSelected);
			gestioneAbbinamento.setTarget(target);
			abbinamentoServiceManager.salvaAbbinamento(gestioneAbbinamento);

			mergeAbbinamento(abbinamentoSelected, attoBean.getAbbinamenti());

			setStatoCommitAbbinamentieDisabbinamenti(CRLMessage.COMMIT_DONE);
			context.addMessage(null, new FacesMessage(
					"Disabbinamento salvato con successo", ""));

		}

		else if (dataAbbinamento != null && dataDisabbinamento == null) {

			abbinamentoSelected.setTipoTesto(tipoTesto);
			abbinamentoSelected.setDataAbbinamento(dataAbbinamento);
			abbinamentoSelected.setNote(noteAbbinamento);
			abbinamentoSelected.setDataDisabbinamento(null);

			GestioneAbbinamento gestioneAbbinamento = new GestioneAbbinamento();
			Target target = new Target();
			target.setPassaggio(attoBean.getLastPassaggio().getNome());
			gestioneAbbinamento.setAbbinamento(abbinamentoSelected);
			gestioneAbbinamento.setTarget(target);
			abbinamentoServiceManager.salvaAbbinamento(gestioneAbbinamento);

			mergeAbbinamento(abbinamentoSelected, attoBean.getLastPassaggio()
					.getAbbinamenti());

			setStatoCommitAbbinamentieDisabbinamenti(CRLMessage.COMMIT_DONE);
			context.addMessage(null, new FacesMessage(
					"Abbinamento salvato con successo", ""));

		}

	}

	private void mergeAbbinamento(Abbinamento abbinamento,
			List<Abbinamento> abbinamentiSession) {
		
		for (Abbinamento element : abbinamentiSession) {
			
			if (element.getIdAttoAbbinato().equals(
					abbinamento.getIdAttoAbbinato())) {
				
				element.setTipoTesto(abbinamento.getTipoTesto());
				element.setDataAbbinamento(abbinamento.getDataAbbinamento());
				element.setDataDisabbinamento(abbinamento.getDataDisabbinamento());
				element.setNote(abbinamento.getNote());
				
				return;
			}
		}

		abbinamentiSession.add(abbinamento);
	}

	public void salvaOggetto() {
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		attoBean.getAtto().setOggetto(atto.getOggetto());

		setStatoCommitOggettoAttoCorrente(CRLMessage.COMMIT_DONE);

		context.addMessage(null, new FacesMessage(
				"Oggetto Atto corrente salvato con successo", ""));

	}

	// Votazione****************************************************************

	public void confrontaDataScadenza() {
		int giorniDifferenza;

		Date scadenza = getDataScadenza();
		if (scadenza != null) {
			long scadenzaLong = scadenza.getTime();
			long todayLong = (new Date()).getTime();

			long msDifferenza = (scadenzaLong - todayLong);
			if (msDifferenza < 0) {
				giorniDifferenza = (int) (msDifferenza / 86400000);
				setMessaggioGiorniScadenza("Attenzione! Ritardo di "
						+ (-giorniDifferenza) + " giorni.");

			} else {
				giorniDifferenza = 1 + (int) (msDifferenza / 86400000);
				setMessaggioGiorniScadenza("Mancano " + giorniDifferenza
						+ " giorni alla scadenza.");
			}
		}
	}

	public String registraVotazione() {

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		String stato = atto.getStato();
        
		
		
		
		// Check se annulla votazione
		if (checkAnnullaCommissione()) {

			commissioneUser.setStato(Commissione.STATO_IN_CARICO);
			commissioneUser.setDataSedutaCommissione(null);

			if (checkLavoriComitatoRistretto() && canChangeStatoAtto()) {

				stato = StatoAtto.LAVORI_COMITATO_RISTRETTO;

			} else if (checkNominatoRelatore() && canChangeStatoAtto()) {
				stato = StatoAtto.NOMINATO_RELATORE;

			} else if (canChangeStatoAtto()) {
				stato = StatoAtto.PRESO_CARICO_COMMISSIONE;

			}

			// Votazione OK
		} else {
			commissioneUser.setStato(Commissione.STATO_VOTATO);

			if (canChangeStatoAtto()) {
				stato = StatoAtto.VOTATO_COMMISSIONE;
			}
		}

		atto.setStato(stato);
		atto.getPassaggi().get(atto.getPassaggi().size() - 1)
				.setCommissioni(getCommissioniList());
		Target target = new Target();
		target.setCommissione(commissioneUser.getDescrizione());
		target.setPassaggio(attoBean.getLastPassaggio().getNome());
		EsameCommissione esameCommissione = new EsameCommissione();
		esameCommissione.setAtto(atto);
		esameCommissione.setTarget(target);
		commissioneServiceManager
				.salvaVotazioneEsameCommissioni(esameCommissione);

		if (canChangeStatoAtto()) {
			attoBean.getAtto().setStato(stato);
		}

		attoBean.getLastPassaggio().setCommissioni(commissioniList);
		setStatoCommitRegistrazioneVotazione(CRLMessage.COMMIT_DONE);
		context.addMessage(null, new FacesMessage(
				"Registrazione Votazione salvata con successo", ""));
		
		//Se deliberante e PDA forward su chiusura iter
		if(Commissione.RUOLO_DELIBERANTE.equals(commissioneUser.getRuolo()) 
				&& atto.getTipoAtto().equals("PDA")){
			return "pretty:Chiusura_Iter";
		}else{
			return null;	
		}
		
	}
	
	
	

	private boolean checkAnnullaCommissione() {

		if (("".equals(getQuorum()) || getQuorum() == null)
				&& ("".equals(getEsitoVotazione()) || getQuorum() == null)
				&& getDataSedutaRegistrazioneVotazione() == null) {
			return true;
		}

		return false;

	}

	private boolean checkLavoriComitatoRistretto() {

		for (Componente componente : this.getMembriComitatoList()) {

			if (componente.getDataUscita() == null) {
				return true;
			}
		}
		return false;
	}

	private boolean checkNominatoRelatore() {
		for (Relatore relatore : getRelatoriList()) {

			if (relatore.getDataUscita() == null) {
				return true;
			}
		}
		return false;

	}

	public void uploadTestoAttoVotato(FileUploadEvent event) {
		String fileName = event.getFile().getFileName();
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		if (!checkTestoAttoVotato(fileName)) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Attenzione ! Il file "
							+ fileName + " è già stato allegato ", ""));
		} else {

			TestoAtto testoVotatoRet = new TestoAtto();
			testoVotatoRet.setNome(event.getFile().getFileName());
			testoVotatoRet.setPubblico(currentFilePubblico);
			testoVotatoRet.setCommissione(commissioneUser.getDescrizione());
			testoVotatoRet.setPassaggio(attoBean.getLastPassaggio().getNome());

			try {

				testoVotatoRet = commissioneServiceManager
						.uploadTestoAttoVotatoEsameCommissioni(
								((AttoBean) FacesContext.getCurrentInstance()
										.getExternalContext().getSessionMap()
										.get("attoBean")).getAtto(), event
										.getFile().getInputstream(),
								testoVotatoRet);

			} catch (IOException e) {
				e.printStackTrace();
			}

			setCurrentFilePubblico(false);

			attoBean.getWorkingCommissione(commissioneUser.getDescrizione())
					.getTestiAttoVotatoEsameCommissioni().add(testoVotatoRet);

			testiAttoVotatoList.add(testoVotatoRet);
		}
	}

	private boolean checkTestoAttoVotato(String fileName) {

		for (TestoAtto element : testiAttoVotatoList) {

			if (fileName.equals(element.getNome())) {

				return false;
			}

		}

		return true;
	}

	public void removeTestoAttoVotato() {

		for (TestoAtto element : testiAttoVotatoList) {

			if (element.getId().equals(testoAttoVotatoToDelete)) {

				// TODO Alfresco delete
				testiAttoVotatoList.remove(element);
				break;
			}
		}
	}

	public void cambiaRuoloInReferente() {

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		commissioneUser.setRuolo(Commissione.RUOLO_REFERENTE);

		Target target = new Target();
		target.setCommissione(commissioneUser.getDescrizione());
		target.setPassaggio(attoBean.getLastPassaggio().getNome());

		EsameCommissione esameCommissione = new EsameCommissione();

		for (Commissione commissioneRec : atto.getPassaggi()
				.get(atto.getPassaggi().size() - 1).getCommissioni()) {

			if (commissioneRec.getDescrizione().equals(
					commissioneUser.getDescrizione())) {

				commissioneRec.setRuolo(Commissione.RUOLO_REFERENTE);
				commissioneRec
						.setMotivazioniContinuazioneInReferente(motivazioni);
				commissioneRec
						.setDataSedutaContinuazioneInReferente(dataSedutaContinuazioneLavori);
			}
		}

		atto.getPassaggi().get(atto.getPassaggi().size() - 1)
				.setCommissioni(getCommissioniList());
		esameCommissione.setAtto(atto);
		esameCommissione.setTarget(target);

		commissioneServiceManager.salvaCambiaRuoloInReferente(esameCommissione);

		attoBean.getAtto().setStato(StatoAtto.VOTATO_COMMISSIONE);
		attoBean.getLastPassaggio().setCommissioni(commissioniList);

		// TODO

		setStatoCommitContinuazioneLavori(CRLMessage.COMMIT_DONE);
		context.addMessage(null, new FacesMessage(
				"Continuazione dei lavori in referente salvata con successo",
				""));
	}

	public String confermaTrasmissione() {

		String risultato = "";

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		/*Commissione comm = attoBean.getWorkingCommissione(commissioneUser
				.getDescrizione());*/

		if (isPassaggioDiretto()
				&& (
				(commissioneUser.getEsitoVotazione() != null
				&& !commissioneUser.getEsitoVotazione().trim().equals(""))||  
				(commissioneUser.getQuorumEsameCommissioni() != null
				&& !commissioneUser.getQuorumEsameCommissioni().trim().equals(""))||
				(commissioneUser.getDataSedutaCommissione() != null)
						)) {
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Attenzione ! Presenza di estremi della votazione (art. 23 comma 9)",
							""));

		} else {

			commissioneUser.setDataTrasmissione(getDataTrasmissione());
			commissioneUser.setPassaggioDirettoInAula(isPassaggioDiretto());
			commissioneUser
					.setDataRichiestaIscrizioneAula(getDataRichiestaIscrizione());
			commissioneUser.setStato(Commissione.STATO_TRASMESSO);

			atto.getPassaggi().get(atto.getPassaggi().size() - 1)
					.setCommissioni(getCommissioniList());

			attoBean.getLastPassaggio().setCommissioni(
					Clonator.cloneList(getCommissioniList()));

			if (atto.getTipoAtto().equals("IND")
					|| atto.getTipoAtto().equals("PAR")
					|| atto.getTipoAtto().equals("REL")) {

				risultato = "pretty:Chiusura_Iter";

			} else if (atto.getTipoAtto().equals("IND")
					&& commissioneUser.getRuolo().equals(
							Commissione.RUOLO_DELIBERANTE)) {

				risultato = "pretty:Chiusura_Iter";

			} else if (canChangeStatoAtto()) {
				attoBean.setStato(StatoAtto.TRASMESSO_AULA);
				atto.setStato(StatoAtto.TRASMESSO_AULA);

			}

			Target target = new Target();
			target.setCommissione(commissioneUser.getDescrizione());
			target.setPassaggio(attoBean.getLastPassaggio().getNome());
			EsameCommissione esameCommissione = new EsameCommissione();
			esameCommissione.setAtto(atto);
			esameCommissione.setTarget(target);

			commissioneServiceManager.salvaTrasmissione(esameCommissione);

			setStatoCommitTrasmissione(CRLMessage.COMMIT_DONE);
			context.addMessage(null, new FacesMessage(
					"Trasmissione salvata con successo", ""));

		}

		return risultato;
	}

	public String testoParereEspressoAttoVotato() {

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = (AttoBean) context
				.getApplication()
				.getExpressionFactory()
				.createValueExpression(context.getELContext(), "#{attoBean}",
						AttoBean.class).getValue(context.getELContext());

		if (attoBean.getTipoAtto().equals("PAR"))

		{
			return "Testo del parere espresso";
		}

		else if (Commissione.RUOLO_CONSULTIVA
				.equals(commissioneUser.getRuolo()))

		{
			return "Testo del parere espresso";
		} else {
			return "Testo dell'atto votato";
		}

	}

	// Emendamenti e Clausole*************************************************

	public void totaleEmendPresentati() {
		numEmendPresentatiTotale = getNumEmendPresentatiGiunta()
				+ getNumEmendPresentatiMaggior() + getNumEmendPresentatiMinor()
				+ getNumEmendPresentatiMisto()
				+ getNumEmendPresentatiCommissione();
	}

	public void totaleEmendApprovati() {
		numEmendApprovatiTotale = getNumEmendApprovatiGiunta()
				+ getNumEmendApprovatiMaggior() + getNumEmendApprovatiMinor()
				+ getNumEmendApprovatiMisto()
				+ getNumEmendApprovatiCommissione();
	}

	public void totaleNonApprovati() {
		totaleNonApprovati = getNonAmmissibili() + getDecaduti()
				+ getRitirati() + getRespinti();
	}

	public void uploadEmendamento(FileUploadEvent event) {

		String fileName = event.getFile().getFileName();
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		if (!checkEmendamenti(fileName)) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Attenzione ! Il file "
							+ fileName + " è già stato allegato ", ""));
		} else {

			Allegato allegatoRet = new Allegato();

			try {

				allegatoRet.setNome(event.getFile().getFileName());
				allegatoRet.setPubblico(currentFilePubblico);
				allegatoRet.setCommissione(commissioneUser.getDescrizione());
				allegatoRet.setPassaggio(attoBean.getLastPassaggio().getNome());

				allegatoRet = commissioneServiceManager
						.uploadEmendamentoEsameCommissioni(
								((AttoBean) FacesContext.getCurrentInstance()
										.getExternalContext().getSessionMap()
										.get("attoBean")).getAtto(), event
										.getFile().getInputstream(),
								allegatoRet);

			} catch (IOException e) {
				e.printStackTrace();
			}

			setCurrentFilePubblico(false);

			attoBean.getWorkingCommissione(commissioneUser.getDescrizione())
					.getEmendamentiEsameCommissioni().add(allegatoRet);

			emendamentiList.add(allegatoRet);
		}
	}

	private boolean checkEmendamenti(String fileName) {

		// for (TestoAtto element : emendamentiList) {
		//
		// if (element.getDescrizione().equals(fileName)) {
		//
		// return false;
		// }
		//
		// }

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

	public void uploadTestoClausola(FileUploadEvent event) {
		// TODO Service logic
		String fileName = event.getFile().getFileName();
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		if (!checkTestiClausola(fileName)) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Attenzione ! Il file "
							+ fileName + " è già stato allegato ", ""));
		} else {

			Allegato allegatoRet = new Allegato();

			try {
				// TODO change method

				allegatoRet.setNome(event.getFile().getFileName());
				allegatoRet.setPubblico(currentFilePubblico);
				allegatoRet.setDataSeduta(currentDataSeduta);
				allegatoRet.setCommissione(commissioneUser.getDescrizione());
				allegatoRet.setPassaggio(attoBean.getLastPassaggio().getNome());

				allegatoRet = commissioneServiceManager
						.uploadTestoClausolaEsameCommissioni(
								((AttoBean) FacesContext.getCurrentInstance()
										.getExternalContext().getSessionMap()
										.get("attoBean")).getAtto(), event
										.getFile().getInputstream(),
								allegatoRet);

			} catch (IOException e) {
				e.printStackTrace();
			}

			setCurrentFilePubblico(false);

			attoBean.getWorkingCommissione(commissioneUser.getDescrizione())
					.getTestiClausola().add(allegatoRet);

			testiClausolaList.add(allegatoRet);
		}
	}

	private boolean checkTestiClausola(String fileName) {

		for (Allegato element : testiClausolaList) {

			if (fileName.equals(element.getNome())) {

				return false;
			}

		}

		return true;
	}

	public void removeTestoClausola() {

		for (Allegato element : testiClausolaList) {

			if (element.getId().equals(testoClausolaToDelete)) {

				// TODO Alfresco delete
				testiClausolaList.remove(element);
				break;
			}
		}
	}

	public void salvaEmendamentiClausole() {

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		atto.getPassaggi().get(atto.getPassaggi().size() - 1)
				.setCommissioni(getCommissioniList());

		Target target = new Target();
		target.setCommissione(commissioneUser.getDescrizione());
		target.setPassaggio(attoBean.getLastPassaggio().getNome());
		EsameCommissione esameCommissione = new EsameCommissione();
		esameCommissione.setAtto(atto);
		esameCommissione.setTarget(target);
		commissioneServiceManager
				.salvaEmendamentiClausoleEsameCommissioni(esameCommissione);

		attoBean.getWorkingCommissione(commissioneUser.getDescrizione())
				.setNumEmendPresentatiMaggiorEsameCommissioni(
						commissioneUser
								.getNumEmendPresentatiMaggiorEsameCommissioni());
		attoBean.getWorkingCommissione(commissioneUser.getDescrizione())
				.setNumEmendPresentatiMinorEsameCommissioni(
						commissioneUser
								.getNumEmendPresentatiMinorEsameCommissioni());
		attoBean.getWorkingCommissione(commissioneUser.getDescrizione())
				.setNumEmendPresentatiGiuntaEsameCommissioni(
						commissioneUser
								.getNumEmendPresentatiGiuntaEsameCommissioni());
		attoBean.getWorkingCommissione(commissioneUser.getDescrizione())
				.setNumEmendPresentatiMistoEsameCommissioni(
						commissioneUser
								.getNumEmendPresentatiMistoEsameCommissioni());

		attoBean.getWorkingCommissione(commissioneUser.getDescrizione())
				.setNumEmendApprovatiMaggiorEsameCommissioni(
						commissioneUser
								.getNumEmendApprovatiMaggiorEsameCommissioni());
		attoBean.getWorkingCommissione(commissioneUser.getDescrizione())
				.setNumEmendApprovatiMinorEsameCommissioni(
						commissioneUser
								.getNumEmendApprovatiMinorEsameCommissioni());
		attoBean.getWorkingCommissione(commissioneUser.getDescrizione())
				.setNumEmendApprovatiGiuntaEsameCommissioni(
						commissioneUser
								.getNumEmendApprovatiGiuntaEsameCommissioni());
		attoBean.getWorkingCommissione(commissioneUser.getDescrizione())
				.setNumEmendApprovatiMistoEsameCommissioni(
						commissioneUser
								.getNumEmendApprovatiMistoEsameCommissioni());

		attoBean.getWorkingCommissione(commissioneUser.getDescrizione())
				.setNonAmmissibiliEsameCommissioni(
						commissioneUser.getNonAmmissibiliEsameCommissioni());
		attoBean.getWorkingCommissione(commissioneUser.getDescrizione())
				.setDecadutiEsameCommissioni(
						commissioneUser.getDecadutiEsameCommissioni());
		attoBean.getWorkingCommissione(commissioneUser.getDescrizione())
				.setRitiratiEsameCommissioni(
						commissioneUser.getRitiratiEsameCommissioni());
		attoBean.getWorkingCommissione(commissioneUser.getDescrizione())
				.setRespintiEsameCommissioni(
						commissioneUser.getRespintiEsameCommissioni());

		attoBean.getWorkingCommissione(commissioneUser.getDescrizione())
				.setNoteEmendamentiEsameCommissioni(
						commissioneUser.getNoteEmendamentiEsameCommissioni());

		attoBean.getWorkingCommissione(commissioneUser.getDescrizione())
				.setDataPresaInCaricoProposta(
						commissioneUser.getDataPresaInCaricoProposta());
		attoBean.getWorkingCommissione(commissioneUser.getDescrizione())
				.setDataIntesa(commissioneUser.getDataIntesa());
		attoBean.getWorkingCommissione(commissioneUser.getDescrizione())
				.setEsitoVotazioneIntesa(
						commissioneUser.getEsitoVotazioneIntesa());
		attoBean.getWorkingCommissione(commissioneUser.getDescrizione())
				.setNoteClausolaValutativa(
						commissioneUser.getNoteClausolaValutativa());

		setStatoCommitEmendamentiClausole(CRLMessage.COMMIT_DONE);
		context.addMessage(null, new FacesMessage(
				"Emendamenti e Clausole salvati con successo", ""));
	}

	// Note e Allegati******************************************************
	public void uploadAllegato(FileUploadEvent event) {

		// TODO Service logic
		String fileName = event.getFile().getFileName();
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		if (!checkAllegato(fileName)) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Attenzione ! Il file "
							+ fileName + " è già stato allegato ", ""));
		} else {

			Allegato allegatoRet = new Allegato();

			try {
				// TODO change method

				allegatoRet.setNome(event.getFile().getFileName());
				allegatoRet.setPubblico(currentFilePubblico);
				allegatoRet.setDataSeduta(currentDataSeduta);
				allegatoRet.setCommissione(commissioneUser.getDescrizione());
				allegatoRet.setPassaggio(attoBean.getLastPassaggio().getNome());

				allegatoRet = commissioneServiceManager
						.uploadAllegatoNoteAllegatiEsameCommissioni(
								((AttoBean) FacesContext.getCurrentInstance()
										.getExternalContext().getSessionMap()
										.get("attoBean")).getAtto(), event
										.getFile().getInputstream(),
								allegatoRet);

			} catch (IOException e) {
				e.printStackTrace();
			}

			setCurrentFilePubblico(false);
			attoBean.getWorkingCommissione(commissioneUser.getDescrizione())
					.getAllegati().add(allegatoRet);
			allegatiList.add(allegatoRet);
		}
	}

	private boolean checkAllegato(String fileName) {

		for (Allegato element : allegatiList) {

			if (fileName.equals(element.getNome())) {

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

		this.commissioneUser.setLinksNoteEsameCommissione(linksList);
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));
		atto.getPassaggi().get(atto.getPassaggi().size() - 1)
				.setCommissioni(getCommissioniList());
		Target target = new Target();
		target.setCommissione(commissioneUser.getDescrizione());
		target.setPassaggio(attoBean.getLastPassaggio().getNome());
		EsameCommissione esameCommissione = new EsameCommissione();
		esameCommissione.setAtto(atto);
		esameCommissione.setTarget(target);

		commissioneServiceManager
				.salvaNoteAllegatiEsameCommissioni(esameCommissione);

		attoBean.getLastPassaggio().setCommissioni(
				Clonator.cloneList(getCommissioniList()));
		
		
		setStatoCommitNote(CRLMessage.COMMIT_DONE);

		context.addMessage(null, new FacesMessage(
				"Note e Allegati salvati con successo", ""));

	}

	// Stralci **************************************************************
	public void salvaStralci() {

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));
		atto.getPassaggi().get(atto.getPassaggi().size() - 1)
				.setCommissioni(getCommissioniList());
		Target target = new Target();
		target.setCommissione(commissioneUser.getDescrizione());
		target.setPassaggio(attoBean.getLastPassaggio().getNome());
		EsameCommissione esameCommissione = new EsameCommissione();
		esameCommissione.setAtto(atto);
		esameCommissione.setTarget(target);

		commissioneServiceManager.salvaStralci(esameCommissione);

		attoBean.getLastPassaggio().setCommissioni(
				Clonator.cloneList(getCommissioniList()));

		setStatoCommitStralci(CRLMessage.COMMIT_DONE);
		context.addMessage(null, new FacesMessage(
				"Stralci salvati con successo", ""));
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
		return this.materia;
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
		return commissioneUser.getEsitoVotazione();
	}

	public void setEsitoVotazione(String esitoVotazione) {
		this.commissioneUser.setEsitoVotazione(esitoVotazione);
	}

	public String getQuorum() {
		return commissioneUser.getQuorumEsameCommissioni();
	}

	public void setQuorum(String quorum) {
		this.commissioneUser.setQuorumEsameCommissioni(quorum);
	}

	public Date getDataSedutaRegistrazioneVotazione() {
		return commissioneUser.getDataSedutaCommissione();
	}

	public void setDataSedutaRegistrazioneVotazione(
			Date dataSedutaRegistrazioneVotazione) {
		this.commissioneUser
				.setDataSedutaCommissione(dataSedutaRegistrazioneVotazione);
	}

	public Date getDataSedutaContinuazioneLavori() {
		return commissioneUser.getDataSedutaContinuazioneInReferente();
	}

	public void setDataSedutaContinuazioneLavori(
			Date dataSedutaContinuazioneLavori) {
		this.commissioneUser.getDataSedutaContinuazioneInReferente();
	}

	public String getMotivazioni() {
		return commissioneUser.getMotivazioniContinuazioneInReferente();
	}

	public void setMotivazioni(String motivazioni) {
		this.commissioneUser
				.setMotivazioniContinuazioneInReferente(motivazioni);
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
		return commissioneUser.getNumEmendPresentatiMaggiorEsameCommissioni();
	}

	public void setNumEmendPresentatiMaggior(int numEmendPresentatiMaggior) {
		this.commissioneUser
				.setNumEmendPresentatiMaggiorEsameCommissioni(numEmendPresentatiMaggior);
	}

	public int getNumEmendPresentatiMinor() {
		return commissioneUser.getNumEmendPresentatiMinorEsameCommissioni();
	}

	public void setNumEmendPresentatiMinor(int numEmendPresentatiMinor) {
		this.commissioneUser
				.setNumEmendPresentatiMinorEsameCommissioni(numEmendPresentatiMinor);
	}

	public int getNumEmendPresentatiGiunta() {
		return commissioneUser.getNumEmendPresentatiGiuntaEsameCommissioni();
	}

	public void setNumEmendPresentatiGiunta(int numEmendPresentatiGiunta) {
		this.commissioneUser
				.setNumEmendPresentatiGiuntaEsameCommissioni(numEmendPresentatiGiunta);
	}

	public int getNumEmendPresentatiMisto() {
		return commissioneUser.getNumEmendPresentatiMistoEsameCommissioni();
	}

	public void setNumEmendPresentatiMisto(int numEmendPresentatiMisto) {
		this.commissioneUser
				.setNumEmendPresentatiMistoEsameCommissioni(numEmendPresentatiMisto);
	}

	public int getNumEmendPresentatiTotale() {
		return numEmendPresentatiTotale;
	}

	public void setNumEmendPresentatiTotale(int numEmendPresentatiTotale) {
		this.numEmendPresentatiTotale = numEmendPresentatiTotale;
	}

	public int getNumEmendApprovatiMaggior() {
		return commissioneUser.getNumEmendApprovatiMaggiorEsameCommissioni();
	}

	public void setNumEmendApprovatiMaggior(int numEmendApprovatiMaggior) {
		this.commissioneUser
				.setNumEmendApprovatiMaggiorEsameCommissioni(numEmendApprovatiMaggior);
	}

	public int getNumEmendApprovatiMinor() {
		return commissioneUser.getNumEmendApprovatiMinorEsameCommissioni();
	}

	public void setNumEmendApprovatiMinor(int numEmendApprovatiMinor) {
		this.commissioneUser
				.setNumEmendApprovatiMinorEsameCommissioni(numEmendApprovatiMinor);
	}

	public int getNumEmendApprovatiGiunta() {
		return commissioneUser.getNumEmendApprovatiGiuntaEsameCommissioni();
	}

	public void setNumEmendApprovatiGiunta(int numEmendApprovatiGiunta) {
		this.commissioneUser
				.setNumEmendApprovatiGiuntaEsameCommissioni(numEmendApprovatiGiunta);
	}

	public int getNumEmendApprovatiMisto() {
		return commissioneUser.getNumEmendApprovatiMistoEsameCommissioni();
	}

	public void setNumEmendApprovatiMisto(int numEmendApprovatiMisto) {
		this.commissioneUser
				.setNumEmendApprovatiMistoEsameCommissioni(numEmendApprovatiMisto);
	}

	public int getNumEmendApprovatiTotale() {
		return numEmendApprovatiTotale;
	}

	public void setNumEmendApprovatiTotale(int numEmendApprovatiTotale) {
		this.numEmendApprovatiTotale = numEmendApprovatiTotale;
	}

	public int getDecaduti() {
		return commissioneUser.getDecadutiEsameCommissioni();
	}

	public void setDecaduti(int decaduti) {
		this.commissioneUser.setDecadutiEsameCommissioni(decaduti);
	}

	public int getRitirati() {
		return commissioneUser.getRitiratiEsameCommissioni();
	}

	public void setRitirati(int ritirati) {
		this.commissioneUser.setRitiratiEsameCommissioni(ritirati);
	}

	public int getRespinti() {
		return commissioneUser.getRespintiEsameCommissioni();
	}

	public void setRespinti(int respinti) {
		this.commissioneUser.setRespintiEsameCommissioni(respinti);
	}

	public int getTotaleNonApprovati() {
		return totaleNonApprovati;
	}

	public void setTotaleNonApprovati(int totaleNonApprovati) {
		this.totaleNonApprovati = totaleNonApprovati;
	}

	public String getNoteEmendamenti() {
		return commissioneUser.getNoteEmendamentiEsameCommissioni();
	}

	public void setNoteEmendamenti(String noteEmendamenti) {
		this.commissioneUser
				.setNoteEmendamentiEsameCommissioni(noteEmendamenti);
	}

	public Date getDataPresaInCaricoProposta() {
		return commissioneUser.getDataPresaInCaricoProposta();
	}

	public void setDataPresaInCaricoProposta(Date dataPresaInCaricoProposta) {
		this.commissioneUser
				.setDataPresaInCaricoProposta(dataPresaInCaricoProposta);
	}

	public Date getDataIntesa() {
		return commissioneUser.getDataIntesa();
	}

	public void setDataIntesa(Date dataIntesa) {
		this.commissioneUser.setDataIntesa(dataIntesa);
	}

	public String getEsitoVotazioneIntesa() {
		return commissioneUser.getEsitoVotazioneIntesa();
	}

	public void setEsitoVotazioneIntesa(String esitoVotazioneIntesa) {
		this.commissioneUser.setEsitoVotazioneIntesa(esitoVotazioneIntesa);
	}

	public String getNoteClausolaValutativa() {
		return commissioneUser.getNoteClausolaValutativa();
	}

	public void setNoteClausolaValutativa(String noteClausolaValutativa) {
		this.commissioneUser.setNoteClausolaValutativa(noteClausolaValutativa);
	}

	public String getNoteGenerali() {
		return commissioneUser.getNoteGeneraliEsameCommissione();
	}

	public void setNoteGenerali(String noteGenerali) {
		this.commissioneUser.setNoteGeneraliEsameCommissione(noteGenerali);
	}

	/*public Map<String, String> getRelatori() {
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
	}*/
	
	

	public List<Relatore> getRelatoriList() {
		return relatoriList;
	}

	public List<Relatore> getRelatori() {
		return relatori;
	}

	public void setRelatori(List<Relatore> relatori) {
		this.relatori = relatori;
	}

	public List<Relatore> getMembriComitato() {
		return membriComitato;
	}

	public void setMembriComitato(List<Relatore> membriComitato) {
		this.membriComitato = membriComitato;
	}

	public void setRelatoriList(List<Relatore> relatoriList) {
		this.relatoriList = relatoriList;
	}

	public List<Componente> getMembriComitatoList() {
		return membriComitatoList;
	}

	public void setMembriComitatoList(List<Componente> membriComitatoList) {
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

	public String getStatoCommitPresaInCarico() {
		return statoCommitPresaInCarico;
	}

	public void setStatoCommitPresaInCarico(String statoCommitPresaInCarico) {
		this.statoCommitPresaInCarico = statoCommitPresaInCarico;
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

	public List<TestoAtto> getTestiAttoVotatoList() {
		return testiAttoVotatoList;
	}

	public void setTestiAttoVotatoList(List<TestoAtto> testiAttoVotatoList) {
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
		return presenzaComitatoRistretto;
	}

	public void setPresenzaComitatoRistretto(boolean presenzaComitatoRistretto) {
		this.presenzaComitatoRistretto = presenzaComitatoRistretto;
	}

	public Date getDataIstituzioneComitato() {
		return dataIstituzioneComitato;
	}

	public void setDataIstituzioneComitato(Date dataIstituzioneComitato) {
		this.dataIstituzioneComitato = dataIstituzioneComitato;
	}

	public List<Abbinamento> getAbbinamentiList() {
		return abbinamentiList;
	}

	public void setAbbinamentiList(List<Abbinamento> abbinamentiList) {
		this.abbinamentiList = abbinamentiList;
	}

	public int getNonAmmissibili() {
		return commissioneUser.getNonAmmissibiliEsameCommissioni();
	}

	public void setNonAmmissibili(int nonAmmissibili) {
		this.commissioneUser.setNonAmmissibiliEsameCommissioni(nonAmmissibili);
	}

	public String getAbbinamentoToDelete() {
		return abbinamentoToDelete;
	}

	public void setAbbinamentoToDelete(String abbinamentoToDelete) {
		this.abbinamentoToDelete = abbinamentoToDelete;
	}

	public String getIdAbbinamentoSelected() {
		return idAbbinamentoSelected;
	}

	public void setIdAbbinamentoSelected(String idAbbinamentoSelected) {
		this.idAbbinamentoSelected = idAbbinamentoSelected;
	}

	public Abbinamento getAbbinamentoSelected() {
		return abbinamentoSelected;
	}

	public void setAbbinamentoSelected(Abbinamento abbinamentoSelected) {
		this.abbinamentoSelected = abbinamentoSelected;
	}

	public String getStatoCommitRelatori() {
		return statoCommitRelatori;
	}

	public void setStatoCommitRelatori(String statoCommitRelatori) {
		this.statoCommitRelatori = statoCommitRelatori;
	}

	public String getStatoCommitComitatoRistretto() {
		return statoCommitComitatoRistretto;
	}

	public void setStatoCommitComitatoRistretto(
			String statoCommitComitatoRistretto) {
		this.statoCommitComitatoRistretto = statoCommitComitatoRistretto;
	}

	public String getStatoCommitRegistrazioneVotazione() {
		return statoCommitRegistrazioneVotazione;
	}

	public void setStatoCommitRegistrazioneVotazione(
			String statoCommitRegistrazioneVotazione) {
		this.statoCommitRegistrazioneVotazione = statoCommitRegistrazioneVotazione;
	}

	public String getStatoCommitContinuazioneLavori() {
		return statoCommitContinuazioneLavori;
	}

	public void setStatoCommitContinuazioneLavori(
			String statoCommitContinuazioneLavori) {
		this.statoCommitContinuazioneLavori = statoCommitContinuazioneLavori;
	}

	public String getStatoCommitTrasmissione() {
		return statoCommitTrasmissione;
	}

	public void setStatoCommitTrasmissione(String statoCommitTrasmissione) {
		this.statoCommitTrasmissione = statoCommitTrasmissione;
	}

	public String getMessaggioGiorniScadenza() {
		return messaggioGiorniScadenza;
	}

	public void setMessaggioGiorniScadenza(String messaggioGiorniScadenza) {
		this.messaggioGiorniScadenza = messaggioGiorniScadenza;
	}

	public String getTestoComitatoToDelete() {
		return testoComitatoToDelete;
	}

	public void setTestoComitatoToDelete(String testoComitatoToDelete) {
		this.testoComitatoToDelete = testoComitatoToDelete;
	}

	public String getStatoCommitAbbinamentieDisabbinamenti() {
		return statoCommitAbbinamentieDisabbinamenti;
	}

	public void setStatoCommitAbbinamentieDisabbinamenti(
			String statoCommitAbbinamentieDisabbinamenti) {
		this.statoCommitAbbinamentieDisabbinamenti = statoCommitAbbinamentieDisabbinamenti;
	}

	public String getStatoCommitOggettoAttoCorrente() {
		return statoCommitOggettoAttoCorrente;
	}

	public void setStatoCommitOggettoAttoCorrente(
			String statoCommitOggettoAttoCorrente) {
		this.statoCommitOggettoAttoCorrente = statoCommitOggettoAttoCorrente;
	}

	public Commissione getCommissioneUser() {
		return commissioneUser;
	}

	public void setCommissioneUser(Commissione commissioneUser) {
		this.commissioneUser = commissioneUser;
	}

	public List<Commissione> getCommissioniList() {
		return commissioniList;
	}

	public void setCommissioniList(List<Commissione> commissioniList) {
		this.commissioniList = commissioniList;
	}

	public CommissioneServiceManager getCommissioneServiceManager() {
		return commissioneServiceManager;
	}

	public void setCommissioneServiceManager(
			CommissioneServiceManager commissioneServiceManager) {
		this.commissioneServiceManager = commissioneServiceManager;
	}

	public String getStatoCommitFineLavori() {
		return statoCommitFineLavori;
	}

	public void setStatoCommitFineLavori(String statoCommitFineLavori) {
		this.statoCommitFineLavori = statoCommitFineLavori;
	}

	public AbbinamentoServiceManager getAbbinamentoServiceManager() {
		return abbinamentoServiceManager;
	}

	public void setAbbinamentoServiceManager(
			AbbinamentoServiceManager abbinamentoServiceManager) {
		this.abbinamentoServiceManager = abbinamentoServiceManager;
	}

	public Date getCurrentDataSeduta() {
		return currentDataSeduta;
	}

	public void setCurrentDataSeduta(Date currentDataSeduta) {
		this.currentDataSeduta = currentDataSeduta;
	}

	public Passaggio getPassaggio() {
		return passaggio;
	}

	public void setPassaggio(Passaggio passaggio) {
		this.passaggio = passaggio;
	}

	public boolean isCurrentFilePubblico() {
		return currentFilePubblico;
	}

	public void setCurrentFilePubblico(boolean currentFilePubblico) {
		this.currentFilePubblico = currentFilePubblico;
	}

	public Date getDataSedutaStralcio() {
		return this.commissioneUser.getDataSedutaStralcio();
	}

	public void setDataSedutaStralcio(Date dataSedutaStralcio) {
		this.commissioneUser.setDataSedutaStralcio(dataSedutaStralcio);
	}

	public Date getDataIniziativaStralcio() {
		return this.commissioneUser.getDataIniziativaStralcio();
	}

	public void setDataIniziativaStralcio(Date dataIniziativaStralcio) {
		this.commissioneUser.setDataIniziativaStralcio(dataIniziativaStralcio);
	}

	public Date getDataStralcio() {
		return this.commissioneUser.getDataStralcio();
	}

	public void setDataStralcio(Date dataStralcio) {
		this.commissioneUser.setDataStralcio(dataStralcio);
	}

	public String getArticoli() {
		return this.commissioneUser.getArticoli();
	}

	public void setArticoli(String articoli) {
		this.commissioneUser.setArticoli(articoli);
	}

	public String getNoteStralcio() {
		return this.commissioneUser.getNoteStralcio();
	}

	public void setNoteStralcio(String noteStralcio) {
		this.commissioneUser.setNoteStralcio(noteStralcio);
	}

	public String getQuorumStralcio() {
		return this.commissioneUser.getQuorumStralcio();
	}

	public void setQuorumStralcio(String quorumStralcio) {
		this.commissioneUser.setQuorumStralcio(quorumStralcio);
	}

	public String getStatoCommitStralci() {
		return statoCommitStralci;
	}

	public void setStatoCommitStralci(String statoCommitStralci) {
		this.statoCommitStralci = statoCommitStralci;
	}

	public String getPassaggioSelected() {
		return passaggioSelected;
	}

	public void setPassaggioSelected(String passaggioSelected) {
		this.passaggioSelected = passaggioSelected;
	}

	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	public int getNumEmendApprovatiCommissione() {
		return numEmendApprovatiCommissione;
	}

	public void setNumEmendApprovatiCommissione(int numEmendApprovatiCommissione) {
		this.numEmendApprovatiCommissione = numEmendApprovatiCommissione;
	}

	public int getNumEmendPresentatiCommissione() {
		return numEmendPresentatiCommissione;
	}

	public void setNumEmendPresentatiCommissione(
			int numEmendPresentatiCommissione) {
		this.numEmendPresentatiCommissione = numEmendPresentatiCommissione;
	}

}
