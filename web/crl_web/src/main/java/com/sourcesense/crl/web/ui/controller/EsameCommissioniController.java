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
import com.sourcesense.crl.business.model.Commissione;
import com.sourcesense.crl.business.model.EsameCommissione;
import com.sourcesense.crl.business.model.Firmatario;
import com.sourcesense.crl.business.model.GruppoUtente;
import com.sourcesense.crl.business.model.Link;
import com.sourcesense.crl.business.model.Componente;
import com.sourcesense.crl.business.model.OrganismoStatutario;
import com.sourcesense.crl.business.model.Personale;
import com.sourcesense.crl.business.model.Relatore;
import com.sourcesense.crl.business.model.StatoAtto;
import com.sourcesense.crl.business.model.Target;
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

	private List<Commissione> commissioniList = new ArrayList<Commissione>();
	private Commissione commissioneUser = new Commissione();

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
	private String messaggioGiorniScadenza;
	private boolean presenzaComitatoRistretto;
	private Date dataIstituzioneComitato;
	private Date dataFineLavori;

	private List <Relatore> relatoriList = new ArrayList <Relatore>();
	private List <Componente> membriComitatoList = new ArrayList <Componente>();
	private List <Allegato> testiComitatoRistrettoList = new ArrayList <Allegato>();

	private String testoComitatoToDelete;


	private List <Abbinamento> abbinamentiList = new ArrayList<Abbinamento>(); 
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


	@PostConstruct
	protected void init() {

		setRelatori(personaleServiceManager.findAllRelatore());
		// TODO setMembriComitato(personaleServiceManager.findAll())

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));
		setAtto((Atto) attoBean.getAtto().clone());	

		setCommissioniList(Clonator.cloneList(atto.getCommissioni()));

		UserBean userBean = ((UserBean) context.getExternalContext()
				.getSessionMap().get("userBean"));
		//MOCK
		

		setCommissioneUser(findCommissione(userBean.getUser().getSessionGroup().getNome()));
		setDataPresaInCarico(commissioneUser.getDataPresaInCarico());
		setMateria(commissioneUser.getMateria());
		setDataScadenza(commissioneUser.getDataScadenza());
		setPresenzaComitatoRistretto(commissioneUser.isPresenzaComitatoRistretto());
		setDataIstituzioneComitato(commissioneUser.getDataIstituzioneComitato());
		setDataFineLavori(commissioneUser.getDataFineLavoriComitato());
		setDataTrasmissione(commissioneUser.getDataTrasmissione());
		setDataRichiestaIscrizione(commissioneUser.getDataTrasmissione());
		setPassaggioDiretto(commissioneUser.isPassaggioDirettoInAula());

		totaleEmendApprovati();
		totaleEmendPresentati();
		totaleNonApprovati();

		//TODO : init liste
		relatoriList = Clonator.cloneList(commissioneUser.getRelatori());
		membriComitatoList = Clonator.cloneList(commissioneUser.getComitatoRistretto().getComponenti());
		testiComitatoRistrettoList = Clonator.cloneList(commissioneUser.getComitatoRistretto().getTesti());
		abbinamentiList = Clonator.cloneList(atto.getAbbinamenti()); 	
		testiAttoVotatoList = Clonator.cloneList(atto.getTestiAttoVotatoEsameCommissioni());
		emendamentiList = Clonator.cloneList(atto.getEmendamentiEsameCommissioni());
		testiClausolaList = Clonator.cloneList(atto.getTestiClausola());
		allegatiList = Clonator.cloneList(atto.getAllegatiNoteEsameCommissioni());
		linksList = Clonator.cloneList(commissioneUser.getLinksNoteEsameCommissione());

		confrontaDataScadenza();

//		if(!abbinamentiList.isEmpty()) {
//			setIdAbbinamentoSelected(abbinamentiList.get(0).getIdAttoAbbinato());
//			showAbbinamentoDetail();
//		}

		//TODO da cancellare
		membriComitato.put("componente1", "componente1");
		membriComitato.put("componente2", "componente2");
		membriComitato.put("componente3", "componente3");

	}

	private Commissione findCommissione(String nome) {
		for(Commissione element : commissioniList) {
			if(element.getDescrizione().equals(nome)) {
				return element;
			}
		}
		return null;
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

		if (statoCommitAbbinamentieDisabbinamenti.equals(CRLMessage.COMMIT_UNDONE)) {
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

	}


	// Relatori e Comitati ristretti***********************************************
	public void presaInCarico() {
		commissioneUser.setDataPresaInCarico(getDataPresaInCarico());
		commissioneUser.setMateria(materia);
		commissioneUser.setDataScadenza(dataScadenza);
		commissioneUser.setStato(Commissione.STATO_IN_CARICO);		
		
		atto.setCommissioni(getCommissioniList());
		atto.setStato(StatoAtto.PRESO_CARICO_COMMISSIONE);
		
		Target target = new Target();
		target.setCommissione(commissioneUser.getDescrizione());
		EsameCommissione esameCommissione = new EsameCommissione();
		esameCommissione.setAtto(atto);
		esameCommissione.setTarget(target);
		
		commissioneServiceManager.salvaPresaInCaricoEsameCommissioni(esameCommissione);		
		
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		UserBean userBean = ((UserBean) context.getExternalContext()
				.getSessionMap().get("userBean"));		

		attoBean.getAtto().setDataPresaInCaricoEsameCommissioni(atto.getDataPresaInCaricoEsameCommissioni());
		attoBean.getAtto().setCommissioni(commissioniList);
		attoBean.setStato(StatoAtto.PRESO_CARICO_COMMISSIONE);

		confrontaDataScadenza();

		String numeroAtto = attoBean.getNumeroAtto();
		setStatoCommitPresaInCarico(CRLMessage.COMMIT_DONE);
		context.addMessage(null, new FacesMessage("Atto " + numeroAtto
				+ " preso in carico con successo dall' utente " + userBean.getUser().getUsername(),""));
	}

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
		commissioneUser.setRelatori(relatoriList);
		atto.setCommissioni(commissioniList);
		
		Target target = new Target();
		target.setCommissione(commissioneUser.getDescrizione());
		EsameCommissione esameCommissione = new EsameCommissione();
		esameCommissione.setAtto(atto);
		esameCommissione.setTarget(target);
		
		commissioneServiceManager.salvaRelatoriEsameCommissioni(esameCommissione);

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		attoBean.getAtto().setCommissioni(Clonator.cloneList(commissioniList));

		if(isNominatoRelatore()) {
			attoBean.setStato(StatoAtto.NOMINATO_RELATORE);
		}

		setStatoCommitRelatori(CRLMessage.COMMIT_DONE);
		context.addMessage(null, new FacesMessage("Relatori salvati con successo", ""));

	}

	private boolean isNominatoRelatore() {
		for(Relatore element : relatoriList) {

			if(element.getDataUscita()==null) {

				return true;
			}
		}
		return false;
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

	private boolean checkOneMembroAttivo() {

		for (Componente element : membriComitatoList) {

			if (element.getDataUscita()==null) {

				return true;
			}
		}
		return false;
	}

	public void confermaComitatoRistretto() {
		boolean isOneMembroAttivo = checkOneMembroAttivo();
		FacesContext context = FacesContext.getCurrentInstance();

		if((isPresenzaComitatoRistretto() && isOneMembroAttivo) || 
				(!isPresenzaComitatoRistretto() && !isOneMembroAttivo)) {
			commissioneUser.getComitatoRistretto().setComponenti(membriComitatoList);
			commissioneUser.setPresenzaComitatoRistretto(isPresenzaComitatoRistretto());
			commissioneUser.setDataIstituzioneComitato(getDataIstituzioneComitato());
			atto.setCommissioni(commissioniList);
			
			Target target = new Target();
			target.setCommissione(commissioneUser.getDescrizione());
			EsameCommissione esameCommissione = new EsameCommissione();
			esameCommissione.setAtto(atto);
			esameCommissione.setTarget(target);
			commissioneServiceManager.salvaComitatoRistrettoEsameCommissioni(esameCommissione);

			AttoBean attoBean = ((AttoBean) context.getExternalContext()
					.getSessionMap().get("attoBean"));

			attoBean.getAtto().setCommissioni(Clonator.cloneList(commissioniList));

			setStatoCommitComitatoRistretto(CRLMessage.COMMIT_DONE);
			context.addMessage(null, new FacesMessage("Comitato ristretto salvato con successo", ""));			
		} 

		else {
			if (isPresenzaComitatoRistretto()) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Attenzione ! non ci sono membri attivi nel comitato ristretto" , ""));
			}

			else {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Attenzione ! casella Presenza Comitato Ristretto non spuntata " , ""));
			}
		}

	}

	public void uploadTestoComitatoRistretto(FileUploadEvent event) {
		// TODO Service logic
		String fileName = event.getFile().getFileName();

		if (!checkTestoComitato(fileName)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Attenzione ! Il file "
							+ fileName + " è già stato allegato ", ""));

		} else {

			// TODO Alfresco upload
			Allegato testoComitatoRet = null;

			try {
				testoComitatoRet = attoServiceManager.uploadTestoComitatoRistretto(
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

			commissioneUser.getComitatoRistretto().getTesti().add(testoComitatoRet);
			attoBean.getAtto().setCommissioni(getCommissioniList());

			testiComitatoRistrettoList.add(testoComitatoRet);
		}
	}

	private boolean checkTestoComitato(String fileName) {

		for (Allegato element : testiComitatoRistrettoList) {

			if (element.getDescrizione().equals(fileName)) {

				return false;
			}

		}

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
		commissioneUser.setStato(Commissione.STATO_ANNULLATO);
		atto.setCommissioni(getCommissioniList());
		
		Target target = new Target();
		target.setCommissione(commissioneUser.getDescrizione());
		EsameCommissione esameCommissione = new EsameCommissione();
		esameCommissione.setAtto(atto);
		esameCommissione.setTarget(target);
		commissioneServiceManager.salvaFineLavoriEsameCommissioni(esameCommissione);

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		attoBean.getAtto().setCommissioni(Clonator.cloneList(getCommissioniList()));

		setStatoCommitPresaInCarico(CRLMessage.COMMIT_DONE);
		context.addMessage(null, new FacesMessage("Relatori e Comitati Ristretti salvati con successo", ""));
	}


	// Abbinamenti***************************************************************

	public void addAbbinamento(String idAbbinamento) {

		if (!idAbbinamento.trim().equals("")) {
			if (!checkAbbinamenti(idAbbinamento)) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Attenzione ! Atto già abbinato ", ""));

			} else {
				Abbinamento abbinamento = new Abbinamento();
				abbinamento.setIdAtto(atto.getId());
				abbinamento.setIdAttoAbbinato(idAbbinamento);
				abbinamento.setAbbinato(true);
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

		if(abbinamentoSelected!=null) {
			Atto attoDaAbbinare = attoServiceManager.findById(abbinamentoSelected.getIdAttoAbbinato());
			abbinamentoSelected.setAtto(attoDaAbbinare);
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

		for(Abbinamento element : abbinamentiList) {

			if(element.getIdAttoAbbinato().equals(id)) {

				return element;

			}

		}

		return null;
	}

	public void salvaAbbinamentoDisabbinamento() {

		if (dataAbbinamento == null && dataDisabbinamento != null){

			FacesContext context = FacesContext.getCurrentInstance();

			setStatoCommitAbbinamentieDisabbinamenti(CRLMessage.COMMIT_DONE);
			context.addMessage(null, new FacesMessage("Impossibile disabbinare un atto non ancora abbinato", ""));	

		}

		else if (dataAbbinamento != null && dataDisabbinamento != null){

			abbinamentoSelected.setTipoTesto(tipoTesto);
			abbinamentoSelected.setDataAbbinamento(dataAbbinamento);
			abbinamentoSelected.setDataDisabbinamento(dataDisabbinamento);
			abbinamentoSelected.setNote(noteAbbinamento);

			abbinamentoServiceManager.salvaAbbinamento(abbinamentoSelected);
			
			FacesContext context = FacesContext.getCurrentInstance();
			AttoBean attoBean = ((AttoBean) context.getExternalContext()
					.getSessionMap().get("attoBean"));

			mergeAbbinamento(abbinamentoSelected, attoBean.getAtto().getAbbinamenti());

			setStatoCommitAbbinamentieDisabbinamenti(CRLMessage.COMMIT_DONE);
			context.addMessage(null, new FacesMessage("Disabbinamento salvato con successo", ""));	

		}

		else if (dataAbbinamento != null && dataDisabbinamento == null){

			abbinamentoSelected.setTipoTesto(tipoTesto);
			abbinamentoSelected.setDataAbbinamento(dataAbbinamento);
			abbinamentoSelected.setNote(noteAbbinamento);

			abbinamentoServiceManager.salvaAbbinamento(abbinamentoSelected);

			FacesContext context = FacesContext.getCurrentInstance();
			AttoBean attoBean = ((AttoBean) context.getExternalContext()
					.getSessionMap().get("attoBean"));

			mergeAbbinamento(abbinamentoSelected, attoBean.getAtto().getAbbinamenti());

			setStatoCommitAbbinamentieDisabbinamenti(CRLMessage.COMMIT_DONE);
			context.addMessage(null, new FacesMessage("Abbinamento salvato con successo", ""));	

		}
		
	}
	
	private void mergeAbbinamento(Abbinamento abbinamento, List<Abbinamento> abbinamentiSession) {
		for(Abbinamento element : abbinamentiSession) {
			if(element.getIdAttoAbbinato().equals(abbinamento.getIdAttoAbbinato())) {
				element.setTipoTesto(getTipoTesto());
				element.setDataAbbinamento(getDataAbbinamento());
				element.setDataDisabbinamento(getDataDisabbinamento());
				element.setNote(getNoteAbbinamento());
				return;
			}
		}
		
		abbinamentiSession.add(abbinamento);
	}


	//	public void salvaDisabbinamento() {
	//		abbinamentoSelected.setTipoTesto(tipoTesto);
	//		abbinamentoSelected.setDataDisabbinamento(dataDisabbinamento);
	//		abbinamentoSelected.setNote(noteAbbinamento);
	//
	//		atto.setAbbinamenti(abbinamentiList);		
	//		//TODO: alfresco service
	//
	//
	//		FacesContext context = FacesContext.getCurrentInstance();
	//		AttoBean attoBean = ((AttoBean) context.getExternalContext()
	//				.getSessionMap().get("attoBean"));
	//
	//		attoBean.getAtto().setAbbinamenti(abbinamentiList);
	//
	//		setStatoCommitAbbinamentieDisabbinamenti(CRLMessage.COMMIT_DONE);
	//		context.addMessage(null, new FacesMessage("Disabbinamento salvato con successo", ""));		
	//
	//	}


	public void salvaOggetto() {
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		attoBean.getAtto().setOggetto(atto.getOggetto());

		setStatoCommitOggettoAttoCorrente(CRLMessage.COMMIT_DONE);

		context.addMessage(null, new FacesMessage("Oggetto Atto corrente con successo", ""));

	}




	// Votazione****************************************************************

	public void confrontaDataScadenza() {
		int giorniDifferenza;

		Date scadenza = getDataScadenza();
		if(scadenza != null) {
			long scadenzaLong = scadenza.getTime();
			long todayLong = (new Date()).getTime();

			long msDifferenza = (scadenzaLong - todayLong);
			if(msDifferenza<0) {
				giorniDifferenza = (int) (msDifferenza/86400000);
				setMessaggioGiorniScadenza("Attenzione! Ritardo di "+(-giorniDifferenza)+" giorni.");

			} else {
				giorniDifferenza = 1 + (int) (msDifferenza/86400000);
				setMessaggioGiorniScadenza("Mancano "+giorniDifferenza+" giorni alla scadenza.");
			}		
		}
	}

	public void registraVotazione() {
		attoServiceManager.salvaVotazioneEsameCommissioni(atto);

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		attoBean.getAtto().setEsitoVotoCommissioneReferente(atto.getEsitoVotoCommissioneReferente());
		attoBean.getAtto().setQuorumEsameCommissioni(atto.getQuorumEsameCommissioni());
		attoBean.getAtto().setDataSedutaCommissione(atto.getDataSedutaCommissione());

		if(attoBean.getAtto().getEsitoVotoCommissioneReferente()!=null &&
				!attoBean.getAtto().getEsitoVotoCommissioneReferente().trim().equals("")) {
			attoBean.getAtto().setStato(StatoAtto.VOTATO_COMMISSIONE);
		}

		setStatoCommitRegistrazioneVotazione(CRLMessage.COMMIT_DONE);
		context.addMessage(null, new FacesMessage("Registrazione Votazione salvata con successo", ""));				
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
				allegatoRet = attoServiceManager.uploadTestoAttoVotatoEsameCommissioni(
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

			attoBean.getAtto().getTestiAttoVotatoEsameCommissioni().add(allegatoRet);

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


	public void cambiaRuoloInReferente() {
		//TODO


		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		//TODO

		setStatoCommitContinuazioneLavori(CRLMessage.COMMIT_DONE);
		context.addMessage(null, new FacesMessage("Continuazione dei lavori in referente salvata con successo", ""));		
	}


	public void confermaTrasmissione() {

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		if(isPassaggioDiretto() && (attoBean.getEsitoVotoCommissioneReferente()!=null && 
				!attoBean.getAtto().getEsitoVotoCommissioneReferente().trim().equals(""))) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Attenzione ! Per il Passaggio Diretto in Aula eliminare " +
							"gli estremi della votazione", ""));
		}

		else {
			commissioneUser.setDataTrasmissione(getDataTrasmissione());
			commissioneUser.setPassaggioDirettoInAula(isPassaggioDiretto());
			commissioneUser.setDataRichiestaIscrizioneAula(getDataRichiestaIscrizione());
			atto.setCommissioni(getCommissioniList());
			
			attoBean.getAtto().setCommissioni(Clonator.cloneList(getCommissioniList()));

			if(commissioneUser.isPassaggioDirettoInAula()) {
				attoBean.setStato(StatoAtto.TRASMESSO_AULA);
				atto.setStato(StatoAtto.TRASMESSO_AULA);
			} else if(attoBean.getAtto().getEsitoVotoCommissioneReferente()!=null && 
					!attoBean.getAtto().getEsitoVotoCommissioneReferente().trim().equals("")) {
				atto.setStato(StatoAtto.TRASMESSO_COMMISSIONE);
				attoBean.setStato(StatoAtto.TRASMESSO_COMMISSIONE);
			}
			
			//TODO: alfresco service

			setStatoCommitTrasmissione(CRLMessage.COMMIT_DONE);
			context.addMessage(null, new FacesMessage("Trasmissione salvata con successo", ""));		

		}
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
				emendamentoRet = attoServiceManager.uploadEmendamentoEsameCommissioni(
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

			attoBean.getAtto().getEmendamentiEsameCommissioni().add(emendamentoRet);

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


	public void uploadTestoClausola(FileUploadEvent event) {
		// TODO Service logic
		String fileName = event.getFile().getFileName();

		if (!checkTestiClausola(fileName)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Attenzione ! Il file "
							+ fileName + " è già stato allegato ", ""));
		} else {

			// TODO Alfresco upload
			Allegato testoClausolaRet = null;

			try {
				//TODO change method
				testoClausolaRet = attoServiceManager.uploadTestoClausolaEsameCommissioni(
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

			attoBean.getAtto().getTestiClausola().add(testoClausolaRet);

			testiClausolaList.add(testoClausolaRet);
		}
	}

	private boolean checkTestiClausola(String fileName) {

		for (Allegato element : testiClausolaList) {

			if (element.getDescrizione().equals(fileName)) {

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
		attoServiceManager.salvaEmendamentiClausoleEsameCommissioni(atto);

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		attoBean.getAtto().setNumEmendPresentatiMaggiorEsameCommissioni(atto.getNumEmendPresentatiMaggiorEsameCommissioni());
		attoBean.getAtto().setNumEmendPresentatiMinorEsameCommissioni(atto.getNumEmendPresentatiMinorEsameCommissioni());
		attoBean.getAtto().setNumEmendPresentatiGiuntaEsameCommissioni(atto.getNumEmendPresentatiGiuntaEsameCommissioni());
		attoBean.getAtto().setNumEmendPresentatiMistoEsameCommissioni(atto.getNumEmendPresentatiMistoEsameCommissioni());

		attoBean.getAtto().setNumEmendApprovatiMaggiorEsameCommissioni(atto.getNumEmendApprovatiMaggiorEsameCommissioni());
		attoBean.getAtto().setNumEmendApprovatiMinorEsameCommissioni(atto.getNumEmendApprovatiMinorEsameCommissioni());
		attoBean.getAtto().setNumEmendApprovatiGiuntaEsameCommissioni(atto.getNumEmendApprovatiGiuntaEsameCommissioni());
		attoBean.getAtto().setNumEmendApprovatiMistoEsameCommissioni(atto.getNumEmendApprovatiMistoEsameCommissioni());

		attoBean.getAtto().setNonAmmissibiliEsameCommissioni(atto.getNonAmmissibiliEsameCommissioni());
		attoBean.getAtto().setDecadutiEsameCommissioni(atto.getDecadutiEsameCommissioni());
		attoBean.getAtto().setRitiratiEsameCommissioni(atto.getRitiratiEsameCommissioni());
		attoBean.getAtto().setRespintiEsameCommissioni(atto.getRespintiEsameCommissioni());

		attoBean.getAtto().setNoteEmendamentiEsameCommissioni(atto.getNoteEmendamentiEsameCommissioni());

		attoBean.getAtto().setDataPresaInCaricoProposta(atto.getDataPresaInCaricoProposta());
		attoBean.getAtto().setDataIntesa(atto.getDataIntesa());
		attoBean.getAtto().setEsitoVotazioneIntesa(atto.getEsitoVotazioneIntesa());
		attoBean.getAtto().setNoteClausolaValutativa(atto.getNoteClausolaValutativa());

		setStatoCommitEmendamentiClausole(CRLMessage.COMMIT_DONE);
		context.addMessage(null, new FacesMessage("Emendamenti e Clausole salvati con successo", ""));
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
				allegatoRet = attoServiceManager.uploadAllegatoNoteAllegatiEsameCommissioni(
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

			attoBean.getAtto().getAllegatiNoteEsameCommissioni().add(allegatoRet);

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
		
		attoServiceManager.salvaNoteAllegatiEsameCommissioni(atto);

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		attoBean.getAtto().setCommissioni(Clonator.cloneList(getCommissioniList()));
		
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
		return atto.getEsitoVotoCommissioneReferente();
	}



	public void setEsitoVotazione(String esitoVotazione) {
		this.atto.setEsitoVotoCommissioneReferente(esitoVotazione);
	}



	public String getQuorum() {
		return atto.getQuorumEsameCommissioni();
	}



	public void setQuorum(String quorum) {
		this.atto.setQuorumEsameCommissioni(quorum);
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
		return atto.getNumEmendPresentatiMaggiorEsameCommissioni();
	}



	public void setNumEmendPresentatiMaggior(int numEmendPresentatiMaggior) {
		this.atto.setNumEmendPresentatiMaggiorEsameCommissioni(numEmendPresentatiMaggior);
	}



	public int getNumEmendPresentatiMinor() {
		return atto.getNumEmendPresentatiMinorEsameCommissioni();
	}



	public void setNumEmendPresentatiMinor(int numEmendPresentatiMinor) {
		this.atto.setNumEmendPresentatiMinorEsameCommissioni(numEmendPresentatiMinor);
	}



	public int getNumEmendPresentatiGiunta() {
		return atto.getNumEmendPresentatiGiuntaEsameCommissioni();
	}



	public void setNumEmendPresentatiGiunta(int numEmendPresentatiGiunta) {
		this.atto.setNumEmendPresentatiGiuntaEsameCommissioni(numEmendPresentatiGiunta);
	}



	public int getNumEmendPresentatiMisto() {
		return atto.getNumEmendPresentatiMistoEsameCommissioni();
	}



	public void setNumEmendPresentatiMisto(int numEmendPresentatiMisto) {
		this.atto.setNumEmendPresentatiMistoEsameCommissioni(numEmendPresentatiMisto);
	}



	public int getNumEmendPresentatiTotale() {
		return numEmendPresentatiTotale;
	}



	public void setNumEmendPresentatiTotale(int numEmendPresentatiTotale) {
		this.numEmendPresentatiTotale = numEmendPresentatiTotale;
	}



	public int getNumEmendApprovatiMaggior() {
		return atto.getNumEmendApprovatiMaggiorEsameCommissioni();
	}



	public void setNumEmendApprovatiMaggior(int numEmendApprovatiMaggior) {
		this.atto.setNumEmendApprovatiMaggiorEsameCommissioni(numEmendApprovatiMaggior);
	}



	public int getNumEmendApprovatiMinor() {
		return atto.getNumEmendApprovatiMinorEsameCommissioni();
	}



	public void setNumEmendApprovatiMinor(int numEmendApprovatiMinor) {
		this.atto.setNumEmendApprovatiMinorEsameCommissioni(numEmendApprovatiMinor);
	}



	public int getNumEmendApprovatiGiunta() {
		return atto.getNumEmendApprovatiGiuntaEsameCommissioni();
	}



	public void setNumEmendApprovatiGiunta(int numEmendApprovatiGiunta) {
		this.atto.setNumEmendApprovatiGiuntaEsameCommissioni(numEmendApprovatiGiunta);
	}



	public int getNumEmendApprovatiMisto() {
		return atto.getNumEmendApprovatiMistoEsameCommissioni();
	}



	public void setNumEmendApprovatiMisto(int numEmendApprovatiMisto) {
		this.atto.setNumEmendApprovatiMistoEsameCommissioni(numEmendApprovatiMisto);
	}



	public int getNumEmendApprovatiTotale() {
		return numEmendApprovatiTotale;
	}



	public void setNumEmendApprovatiTotale(int numEmendApprovatiTotale) {
		this.numEmendApprovatiTotale = numEmendApprovatiTotale;
	}



	public int getDecaduti() {
		return atto.getDecadutiEsameCommissioni();
	}



	public void setDecaduti(int decaduti) {
		this.atto.setDecadutiEsameCommissioni(decaduti);
	}



	public int getRitirati() {
		return atto.getRitiratiEsameCommissioni();
	}



	public void setRitirati(int ritirati) {
		this.atto.setRitiratiEsameCommissioni(ritirati);
	}



	public int getRespinti() {
		return atto.getRespintiEsameCommissioni();
	}



	public void setRespinti(int respinti) {
		this.atto.setRespintiEsameCommissioni(respinti);
	}



	public int getTotaleNonApprovati() {
		return totaleNonApprovati;
	}



	public void setTotaleNonApprovati(int totaleNonApprovati) {
		this.totaleNonApprovati = totaleNonApprovati;
	}



	public String getNoteEmendamenti() {
		return atto.getNoteEmendamentiEsameCommissioni();
	}



	public void setNoteEmendamenti(String noteEmendamenti) {
		this.atto.setNoteEmendamentiEsameCommissioni(noteEmendamenti);
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
		return commissioneUser.getNoteGeneraliEsameCommissione();
	}



	public void setNoteGenerali(String noteGenerali) {
		this.commissioneUser.setNoteGeneraliEsameCommissione(noteGenerali);
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



	public void setStatoCommitPresaInCarico(
			String statoCommitPresaInCarico) {
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

	public List <Abbinamento> getAbbinamentiList() {
		return abbinamentiList;
	}

	public void setAbbinamentiList(List <Abbinamento> abbinamentiList) {
		this.abbinamentiList = abbinamentiList;
	}

	public int getNonAmmissibili() {
		return atto.getNonAmmissibiliEsameCommissioni();
	}

	public void setNonAmmissibili(int nonAmmissibili) {
		this.atto.setNonAmmissibiliEsameCommissioni(nonAmmissibili);
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

	public void setAbbinamentoServiceManager(AbbinamentoServiceManager abbinamentoServiceManager) {
		this.abbinamentoServiceManager = abbinamentoServiceManager;
	}
	
	








}
