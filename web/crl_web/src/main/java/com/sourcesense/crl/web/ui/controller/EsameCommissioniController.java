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
import com.sourcesense.crl.web.ui.beans.UserBean;


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
	private String messaggioGiorniScadenza;
	private boolean presenzaComitatoRistretto;
	private Date dataIstituzioneComitato;
	private Date dataFineLavori;

	private List <Relatore> relatoriList = new ArrayList <Relatore>();
	private List <MembroComitatoRistretto> membriComitatoList = new ArrayList <MembroComitatoRistretto>();
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

		totaleEmendApprovati();
		totaleEmendPresentati();
		totaleNonApprovati();

		//TODO : init liste
		relatoriList = new ArrayList <Relatore>(atto.getRelatori());
		membriComitatoList = new ArrayList <MembroComitatoRistretto>(atto.getComitatoRistretto().getComponenti());
		testiComitatoRistrettoList = new ArrayList <Allegato>(atto.getComitatoRistretto().getTesti());
		abbinamentiList = new ArrayList<Abbinamento>(atto.getAbbinamenti()); 	
		testiAttoVotatoList = new ArrayList <Allegato>(atto.getTestiAttoVotatoEsameCommissioni());
		emendamentiList = new ArrayList <Allegato>(atto.getEmendamentiEsameCommissioni());
		testiClausolaList = new ArrayList <Allegato>(atto.getTestiClausola());
		allegatiList = new ArrayList <Allegato>(atto.getAllegatiNoteEsameCommissioni());
		linksList = new ArrayList <Link>(atto.getLinksNoteEsameCommissioni());

		confrontaDataScadenza();

		if(!abbinamentiList.isEmpty()) {
			setIdAbbinamentoSelected(abbinamentiList.get(0).getAtto().getId());
			showAbbinamentoDetail();
		}

		//TODO da cancellare
		membriComitato.put("componente1", "componente1");
		membriComitato.put("componente2", "componente2");
		membriComitato.put("componente3", "componente3");

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

		// TODO Service logic
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		String username = ((UserBean) context.getExternalContext()
				.getSessionMap().get("userBean")).getUsername();

		String numeroAtto = attoBean.getNumeroAtto();

		context.addMessage(null, new FacesMessage("Atto " + numeroAtto
				+ " preso in carico con successo dall' utente " + username));

		attoBean.getAtto().setDataPresaInCaricoEsameCommissioni(atto.getDataPresaInCaricoEsameCommissioni());
		attoBean.getAtto().setDataScadenzaEsameCommissioni(atto.getDataScadenzaEsameCommissioni());
		attoBean.getAtto().setMateria(atto.getMateria());
		
		//TODO: to complete
		confrontaDataScadenza();

		setStatoCommitPresaInCarico(CRLMessage.COMMIT_DONE);
		context.addMessage(null, new FacesMessage("Atto Preso in Carico con successo", ""));


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
		atto.setRelatori(relatoriList);
		attoServiceManager.salvaRelatoriEsameCommissioni(atto);

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		attoBean.getAtto().setRelatori(relatoriList);

		setStatoCommitRelatori(CRLMessage.COMMIT_DONE);
		context.addMessage(null, new FacesMessage("Relatori salvati con successo", ""));

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

				updateComitatoRistrettoHandler();
			}
		}
	}	

	public void removeComponente() {

		for (MembroComitatoRistretto element : membriComitatoList) {

			if (element.getDescrizione().equals(componenteToDelete)) {

				membriComitatoList.remove(element);
				updateComitatoRistrettoHandler();
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

	private boolean checkOneMembroAttivo() {

		for (MembroComitatoRistretto element : membriComitatoList) {

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
			atto.getComitatoRistretto().setComponenti(membriComitatoList);
			attoServiceManager.salvaComitatoRistrettoEsameCommissioni(atto);

			AttoBean attoBean = ((AttoBean) context.getExternalContext()
					.getSessionMap().get("attoBean"));

			attoBean.getAtto().getComitatoRistretto().setComponenti(membriComitatoList);
			attoBean.getAtto().setPresenzaComitatoRistretto(atto.isPresenzaComitatoRistretto());
			attoBean.getAtto().setDataIstituzioneComitato(atto.getDataIstituzioneComitato());

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

			attoBean.getAtto().getComitatoRistretto().getTesti().add(testoComitatoRet);

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
		attoServiceManager.salvaFineLavoriEsameCommissioni(atto);

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		attoBean.getAtto().setDataFineLavoriEsameCommissioni(atto.getDataFineLavoriEsameCommissioni());

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
				Atto attoDaAbbinare = attoServiceManager.findById(idAbbinamento);
				Abbinamento abbinamento = new Abbinamento();
				abbinamento.setAtto(attoDaAbbinare);
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

			if (element.getAtto().getId().equals(abbinamentoToDelete)) {

				abbinamentiList.remove(element);
				updateAbbinamentiHandler();
				element.setAbbinato(false);
				break;
			}
		}
	}

	private boolean checkAbbinamenti(String idAbbinamento) {

		for (Abbinamento element : abbinamentiList) {

			if (element.getAtto().getId().equals(idAbbinamento)) {

				return false;
			}
		}
		return true;
	}


	public void showAbbinamentoDetail() {

		setAbbinamentoSelected(findAbbinamento(idAbbinamentoSelected));

		if(abbinamentoSelected!=null) {
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

			if(element.getAtto().getId().equals(id)) {

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

			atto.setAbbinamenti(abbinamentiList);		
			attoServiceManager.salvaAbbinamentiEsameCommissioni(atto);


			FacesContext context = FacesContext.getCurrentInstance();
			AttoBean attoBean = ((AttoBean) context.getExternalContext()
					.getSessionMap().get("attoBean"));

			attoBean.getAtto().setAbbinamenti(abbinamentiList);

			setStatoCommitAbbinamentieDisabbinamenti(CRLMessage.COMMIT_DONE);
			context.addMessage(null, new FacesMessage("Disabbinamento salvato con successo", ""));	
			
		}
		
		else if (dataAbbinamento != null && dataDisabbinamento == null){
			
			abbinamentoSelected.setTipoTesto(tipoTesto);
			abbinamentoSelected.setDataAbbinamento(dataAbbinamento);
			abbinamentoSelected.setNote(noteAbbinamento);

			atto.setAbbinamenti(abbinamentiList);		
			attoServiceManager.salvaAbbinamentiEsameCommissioni(atto);


			FacesContext context = FacesContext.getCurrentInstance();
			AttoBean attoBean = ((AttoBean) context.getExternalContext()
					.getSessionMap().get("attoBean"));

			attoBean.getAtto().setAbbinamenti(abbinamentiList);

			setStatoCommitAbbinamentieDisabbinamenti(CRLMessage.COMMIT_DONE);
			context.addMessage(null, new FacesMessage("Abbinamento salvato con successo", ""));	
			
		}
		
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
		//TODO: alfresco service

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		attoBean.getAtto().setDataTrasmissione(atto.getDataTrasmissione());
		attoBean.getAtto().setDataRichiestaIscrizioneAula(atto.getDataRichiestaIscrizioneAula());
		attoBean.getAtto().setPassaggioDirettoInAula(atto.isPassaggioDirettoInAula());

		setStatoCommitTrasmissione(CRLMessage.COMMIT_DONE);
		context.addMessage(null, new FacesMessage("Trasmissione salvata con successo", ""));		

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
		this.atto.setLinksNoteEsameCommissioni(linksList);
		attoServiceManager.salvaNoteAllegatiEsameCommissioni(atto);

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		attoBean.getAtto().setNoteGeneraliEsameCommissioni(atto.getNoteGeneraliEsameCommissioni());
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
		return atto.getDataPresaInCaricoEsameCommissioni();
	}



	public void setDataPresaInCarico(Date dataPresaInCarico) {
		this.atto.setDataPresaInCaricoEsameCommissioni(dataPresaInCarico);
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








}
