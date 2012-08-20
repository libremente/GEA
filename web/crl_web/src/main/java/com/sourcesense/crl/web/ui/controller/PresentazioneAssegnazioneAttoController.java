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
import javax.print.attribute.standard.Severity;

import org.primefaces.event.FileUploadEvent;

import com.sourcesense.crl.business.model.Allegato;
import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.model.AttoRecord;
import com.sourcesense.crl.business.model.Commissione;
import com.sourcesense.crl.business.model.Firmatario;
import com.sourcesense.crl.business.model.GruppoConsiliare;
import com.sourcesense.crl.business.model.Link;
import com.sourcesense.crl.business.model.OrganismoStatutario;
import com.sourcesense.crl.business.model.Parere;
import com.sourcesense.crl.business.model.Personale;
import com.sourcesense.crl.business.model.Relatore;
import com.sourcesense.crl.business.service.AttoServiceManager;
import com.sourcesense.crl.business.service.CommissioneServiceManager;
import com.sourcesense.crl.business.service.OrganismoStatutarioServiceManager;
import com.sourcesense.crl.business.service.PersonaleServiceManager;
import com.sourcesense.crl.business.service.TipoIniziativaServiceManager;
import com.sourcesense.crl.util.CRLMessage;
import com.sourcesense.crl.web.ui.beans.AttoBean;
import com.sourcesense.crl.web.ui.beans.UserBean;

@ManagedBean(name = "presentazioneAssegnazioneAttoController")
@ViewScoped
public class PresentazioneAssegnazioneAttoController {

	@ManagedProperty(value = "#{personaleServiceManager}")
	private PersonaleServiceManager personaleServiceManager;

	@ManagedProperty(value = "#{attoServiceManager}")
	private AttoServiceManager attoServiceManager;

	@ManagedProperty(value = "#{commissioneServiceManager}")
	private CommissioneServiceManager commissioneServiceManager;

	@ManagedProperty(value = "#{organismoStatutarioServiceManager}")
	private OrganismoStatutarioServiceManager organismoStatutarioServiceManager;

	@ManagedProperty(value = "#{tipoIniziativaServiceManager}")
	TipoIniziativaServiceManager tipoIniziativaServiceManager;

	private Map<String, String> tipiIniziativa = new HashMap<String, String>();

	private Date dataPresaInCarico;
	private String numeroAtto;
	private String classificazione;
	private String oggetto;
	private String numeroRepertorio;
	private Date dataRepertorio;
	private String tipoIniziativa;
	private Date dataIniziativa;
	private String descrizioneIniziativa;
	private String numeroDgr;
	private Date dataDgr;
	private String assegnazione;

	private List<Firmatario> firmatariList = new ArrayList<Firmatario>();
	private Map<String, String> firmatari = new HashMap<String, String>();
	private String nomeFirmatario;

	private List<AttoRecord> testiAttoList = new ArrayList<AttoRecord>();
	private String gruppoConsiliare;
	private List<GruppoConsiliare> gruppiConsiliari = new ArrayList<GruppoConsiliare>();

	private Date dataFirma;
	private Date dataRitiro;
	private boolean primoFirmatario;

	private String firmatarioToDelete;
	private String testoAttoToDelete;

	private String statoCommitInfoGen = CRLMessage.COMMIT_DONE;
	private String statoCommitAmmissibilita = CRLMessage.COMMIT_DONE;
	private String statoCommitAssegnazione = CRLMessage.COMMIT_DONE;
	private String statoCommitNote = CRLMessage.COMMIT_DONE;

	private String valutazioneAmmissibilita;
	private Date dataRichiestaInformazioni;
	private Date dataRicevimentoInformazioni;
	private boolean aiutiStato;
	private boolean normaFinanziaria;
	private boolean richiestaUrgenza;
	private boolean votazioneUrgenza;
	private Date dataVotazioneUrgenza;
	private String noteAmmissibilita;

	private Map<String, String> commissioni = new HashMap<String, String>();
	private List<Commissione> commissioniList = new ArrayList<Commissione>();

	private String nomeCommissione;
	private Date dataProposta;
	private Date dataAssegnazione;
	private String ruolo;
	private Date dataAnnullo;

	private String commissioneToDelete;
	private String commissioneToAnnul;

	private Map<String, String> organismiStatutari = new HashMap<String, String>();
	private List<Parere> pareriList = new ArrayList<Parere>();

	private String nomeOrganismoStatutario;
	private Date dataAssegnazioneParere;
	private Date dataAnnulloParere;
	private boolean obbligatorio;

	private String parereToDelete;

	private List<Allegato> allegatiList = new ArrayList<Allegato>();
	private String allegatoToDelete;

	private List<Link> linksList = new ArrayList<Link>();

	private String nomeLink;
	private String urlLink;
	private boolean pubblico;

	private String linkToDelete;

	private String noteNoteAllegati;

	private Atto atto = new Atto();

	@PostConstruct
	protected void init() {
		setFirmatari(personaleServiceManager.findAllFirmatario());
		// TODO: setCommissioni(commissioneServiceManager.findAll());
		setCommissioni(commissioneServiceManager.findAllCommissioneConsultiva());
		setOrganismiStatutari(organismoStatutarioServiceManager.findAll());
		setTipiIniziativa(tipoIniziativaServiceManager.findAll());

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));
		setAtto((Atto) attoBean.getAtto().clone());

		this.firmatariList = new ArrayList<Firmatario>(this.atto.getFirmatari());

		//TODO caricamento liste attoBean
		this.commissioniList = new ArrayList<Commissione>(this.atto.getCommissioni());
		this.pareriList = new ArrayList<Parere>(this.atto.getPareri());
		this.linksList =  new ArrayList<Link>(this.atto.getLinksPresentazioneAssegnazione());

		//TODO
		//setAllegatiList(allegatoServiceManager.findByTipo());
		//setTestiAtto(recordAttoServiceManager.findByTipo());

	}

	public void updateInfoGenHandler() {
		setStatoCommitInfoGen(CRLMessage.COMMIT_UNDONE);
	}

	public void updateAmmissibilitaHandler() {
		setStatoCommitAmmissibilita(CRLMessage.COMMIT_UNDONE);
	}

	public void updateAssegnazioneHandler() {
		setStatoCommitAssegnazione(CRLMessage.COMMIT_UNDONE);
	}

	public void updateNoteHandler() {
		setStatoCommitNote(CRLMessage.COMMIT_UNDONE);
	}

	public void changeTabHandler() {

		if (statoCommitInfoGen.equals(CRLMessage.COMMIT_UNDONE)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Attenzione ! Le modifiche alle Informazioni Generali non sono state salvate ",
							""));
		}

		if (statoCommitAmmissibilita.equals(CRLMessage.COMMIT_UNDONE)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Attenzione ! Le modifiche di Ammissibilità non sono state salvate ",
							""));
		}

		if (statoCommitAssegnazione.equals(CRLMessage.COMMIT_UNDONE)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Attenzione ! Le modifiche di Assegnazione non sono state salvate ",
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

	// Info Generali******************************************************
	public void presaInCarico() {

		// TODO Service logic
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		attoBean.getAtto().setDataPresaInCarico(atto.getDataPresaInCarico());

		String username = ((UserBean) context.getExternalContext()
				.getSessionMap().get("userBean")).getUsername();

		String numeroAtto = attoBean.getNumeroAtto();

		context.addMessage(null, new FacesMessage("Atto " + numeroAtto
				+ " preso in carico con successo dall' utente " + username));

	}

	public void uploadTestoAtto(FileUploadEvent event) {

		// TODO Service logic
		String fileName = event.getFile().getFileName();

		if (!checkTestoAtto(fileName)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Attenzione ! Il file "
							+ fileName + " è già stato allegato ", ""));

		} else {

			// TODO Alfresco upload
			AttoRecord testoAttoRet = null;

			try {
				testoAttoRet = attoServiceManager.uploadTestoAttoPresentazioneAssegnazione(
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

			attoBean.getAtto().getTestiAtto().add(testoAttoRet);

			testiAttoList.add(testoAttoRet);
		}
	}

	private boolean checkTestoAtto(String fileName) {

		for (AttoRecord element : testiAttoList) {

			if (element.getDescrizione().equals(fileName)) {

				return false;
			}

		}

		return true;
	}

	public void removeTestoAtto() {

		for (AttoRecord element : testiAttoList) {

			if (element.getId().equals(testoAttoToDelete)) {
				// TODO Alfresco delete
				testiAttoList.remove(element);
				break;
			}
		}
	}

	public void addFirmatario() {

		if (nomeFirmatario != null && !nomeFirmatario.trim().equals("")) {
			if (!checkFirmatari()) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Attenzione ! Firmatario "
								+ nomeFirmatario + " già presente ", ""));

			} else if (primoFirmatario && checkPrimoFirmatario()){
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Attenzione ! Primo firmatario già selezionato ", ""));

			} else {
				Firmatario firmatario = new Firmatario();
				firmatario.setDescrizione(nomeFirmatario);
				firmatario.setDataFirma(dataFirma);
				firmatario.setDataRitiro(dataRitiro);
				firmatario.setGruppoConsiliare(gruppoConsiliare);
				firmatario.setPrimoFirmatario(primoFirmatario);
				firmatariList.add(firmatario);

				updateInfoGenHandler();
			}
		}
	}

	public void removeFirmatario() {

		for (Firmatario element : firmatariList) {

			if (element.getDescrizione().equals(firmatarioToDelete)) {

				firmatariList.remove(element);
				break;
			}
		}
	}

	private boolean checkFirmatari() {

		for (Firmatario element : firmatariList) {

			if (element.getDescrizione().equals(nomeFirmatario)) {

				return false;
			}
		}
		return true;
	}

	private boolean checkPrimoFirmatario() {

		for (Firmatario element : firmatariList) {

			if (element.isPrimoFirmatario()) {

				return true;
			}
		}
		return false;
	}

	public String ritiraPerMancanzaFirmatari() {

		// TODO Service logic

		return "pretty:Chiusura_Iter";

	}

	public void salvaInfoGenerali() {
		atto.setFirmatari(firmatariList);
		attoServiceManager.salvaInfoGeneraliPresentazione(this.atto);

		// TODO Service logic
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		attoBean.getAtto().setClassificazione(this.atto.getClassificazione());
		attoBean.getAtto().setOggetto(this.atto.getOggetto());
		attoBean.getAtto().setNumeroRepertorio(atto.getNumeroRepertorio());
		attoBean.getAtto().setDataRepertorio(this.atto.getDataRepertorio());
		attoBean.getAtto().setTipoIniziativa(atto.getTipoIniziativa());
		attoBean.getAtto().setDataIniziativa(atto.getDataIniziativa());
		attoBean.getAtto().setDescrizioneIniziativa(atto.getDescrizioneIniziativa());
		attoBean.getAtto().setNumeroDgr(atto.getNumeroDgr());
		attoBean.getAtto().setDataDgr(atto.getDataDgr());
		attoBean.getAtto().setAssegnazione(atto.getAssegnazione());

		attoBean.getAtto().setFirmatari(firmatariList);

		setStatoCommitInfoGen(CRLMessage.COMMIT_DONE);

		context.addMessage(null, new FacesMessage("Informazioni Generali salvate con successo", ""));

	}

	// Ammissibilita******************************************************

	public void salvaAmmissibilita() {
		attoServiceManager.salvaAmmissibilitaPresentazione(atto);

		// TODO Service logic
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		attoBean.getAtto().setValutazioneAmmissibilita(atto.getValutazioneAmmissibilita());
		attoBean.getAtto().setDataRichiestaInformazioni(atto.getDataRichiestaInformazioni());
		attoBean.getAtto().setDataRicevimentoInformazioni(atto.getDataRicevimentoInformazioni());
		attoBean.getAtto().setAiutiStato(atto.isAiutiStato());
		attoBean.getAtto().setNormaFinanziaria(atto.isNormaFinanziaria());
		attoBean.getAtto().setRichiestaUrgenza(atto.isRichiestaUrgenza());
		attoBean.getAtto().setVotazioneUrgenza(atto.isVotazioneUrgenza());
		attoBean.getAtto().setDataVotazioneUrgenza(atto.getDataVotazioneUrgenza());
		attoBean.getAtto().setNoteAmmissibilita(atto.getNoteAmmissibilita());

		setStatoCommitAmmissibilita(CRLMessage.COMMIT_DONE);

		context.addMessage(null, new FacesMessage("Ammissibilità salvata con successo", ""));

	}

	// Assegnazione******************************************************
	public void addCommissione() {

		if (nomeCommissione != null && !nomeCommissione.trim().equals("")) {
			if (!checkCommissioni()) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Attenzione ! Commissione " + nomeCommissione
						+ " già presente ", ""));

			} else {
				Commissione commissione = new Commissione();
				commissione.setDescrizione(nomeCommissione);
				commissione.setDataAssegnazione(dataAssegnazione);
				commissione.setDataProposta(dataProposta);
				commissione.setRuolo(ruolo);
				commissioniList.add(commissione);

				updateAssegnazioneHandler();
			}
		}
	}

	public void removeCommissione() {

		for (Commissione element : commissioniList) {

			if (element.getDescrizione().equals(commissioneToDelete)) {

				commissioniList.remove(element);
				break;
			}
		}
	}

	private boolean checkCommissioni() {

		for (Commissione element : commissioniList) {

			if (element.getDescrizione().equals(nomeCommissione)) {

				return false;
			}

		}

		return true;
	}

	public void annulCommissione() {

		for (Commissione element : commissioniList) {

			if (element.getDescrizione().equals(commissioneToAnnul)) {
				element.setAnnullata(true);
				element.setDataAnnullo(dataAnnullo);
				break;
			}
		}
	}

	public void addParere() {

		if (nomeOrganismoStatutario != null
				&& !nomeOrganismoStatutario.trim().equals("")) {
			if (!checkPareri()) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Attenzione ! Parere "
								+ nomeOrganismoStatutario + " già presente ",
						""));

			} else {
				Parere parere = new Parere();
				parere.setDescrizione(nomeOrganismoStatutario);
				parere.setDataAssegnazione(dataAssegnazioneParere);
				parere.setDataAnnullo(dataAnnulloParere);
				parere.setObbligatorio(obbligatorio);
				pareriList.add(parere);

				updateAssegnazioneHandler();
			}
		}
	}

	public void removeParere() {

		for (Parere element : pareriList) {

			if (element.getDescrizione().equals(parereToDelete)) {

				pareriList.remove(element);
				break;
			}
		}
	}

	private boolean checkPareri() {

		for (Parere element : pareriList) {

			if (element.getDescrizione().equals(nomeOrganismoStatutario)) {

				return false;
			}

		}

		return true;
	}

	public void confermaAssegnazione() {
		this.atto.setCommissioni(commissioniList);
		this.atto.setPareri(pareriList);
		attoServiceManager.salvaAssegnazionePresentazione(atto);

		// TODO Service logic
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		attoBean.getAtto().setCommissioni(commissioniList);
		attoBean.getAtto().setPareri(pareriList);

		setStatoCommitAssegnazione(CRLMessage.COMMIT_DONE);

		context.addMessage(null, new FacesMessage("Assegnazione salvata con successo", ""));
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
				allegatoRet = attoServiceManager.uploadAllegatoNoteAllegatiPresentazioneAssegnazione(
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

			attoBean.getAtto().getAllegatiNotePresentazioneAssegnazione().add(allegatoRet);

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
		this.atto.setLinksPresentazioneAssegnazione(linksList);
		attoServiceManager.salvaNoteAllegatiPresentazione(atto);

		// TODO Service logic
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		attoBean.getAtto().setNotePresentazioneAssegnazione(atto.getNotePresentazioneAssegnazione());
		attoBean.getAtto().setLinksPresentazioneAssegnazione(linksList);		

		setStatoCommitNote(CRLMessage.COMMIT_DONE);

		context.addMessage(null, new FacesMessage("Note e Allegati salvati con successo", ""));

	}

	// Getters & Setters******************************************************

	public PersonaleServiceManager getPersonaleServiceManager() {
		return personaleServiceManager;
	}

	public void setPersonaleServiceManager(
			PersonaleServiceManager personaleServiceManager) {
		this.personaleServiceManager = personaleServiceManager;
	}

	public Map<String, String> getFirmatari() {
		return firmatari;
	}

	public void setFirmatari(Map<String, String> firmatari) {
		this.firmatari = firmatari;
	}

	public String getNomeFirmatario() {
		return nomeFirmatario;
	}

	public void setNomeFirmatario(String nomeFirmatario) {
		this.nomeFirmatario = nomeFirmatario;
	}

	public List<GruppoConsiliare> getGruppiConsiliari() {
		return gruppiConsiliari;
	}

	public void setGruppiConsiliari(List<GruppoConsiliare> gruppiConsiliari) {
		this.gruppiConsiliari = gruppiConsiliari;
	}

	public String getGruppoConsiliare() {
		return gruppoConsiliare;
	}

	public void setGruppoConsiliare(String gruppoConsiliare) {
		this.gruppoConsiliare = gruppoConsiliare;
	}

	public Date getDataFirma() {
		return dataFirma;
	}

	public void setDataFirma(Date dataFirma) {
		this.dataFirma = dataFirma;
	}

	public Date getDataRitiro() {
		return dataRitiro;
	}

	public void setDataRitiro(Date dataRitiro) {
		this.dataRitiro = dataRitiro;
	}

	public boolean isPrimoFirmatario() {
		return primoFirmatario;
	}

	public void setPrimoFirmatario(boolean primoFirmatario) {
		this.primoFirmatario = primoFirmatario;
	}

	public String getFirmatarioToDelete() {
		return firmatarioToDelete;
	}

	public void setFirmatarioToDelete(String firmatarioToDelete) {
		this.firmatarioToDelete = firmatarioToDelete;
	}

	public String getTestoAttoToDelete() {
		return testoAttoToDelete;
	}

	public void setTestoAttoToDelete(String testoAttoToDelete) {
		this.testoAttoToDelete = testoAttoToDelete;
	}

	public AttoServiceManager getAttoServiceManager() {
		return attoServiceManager;
	}

	public void setAttoServiceManager(AttoServiceManager attoServiceManager) {
		this.attoServiceManager = attoServiceManager;
	}

	public Map<String, String> getCommissioni() {
		return commissioni;
	}

	public void setCommissioni(Map<String, String> commissioni) {
		this.commissioni = commissioni;
	}

	public CommissioneServiceManager getCommissioneServiceManager() {
		return commissioneServiceManager;
	}

	public void setCommissioneServiceManager(
			CommissioneServiceManager commissioneServiceManager) {
		this.commissioneServiceManager = commissioneServiceManager;
	}

	public String getNomeCommissione() {
		return nomeCommissione;
	}

	public void setNomeCommissione(String nomeCommissione) {
		this.nomeCommissione = nomeCommissione;
	}

	public Date getDataProposta() {
		return dataProposta;
	}

	public void setDataProposta(Date dataProposta) {
		this.dataProposta = dataProposta;
	}

	public Date getDataAssegnazione() {
		return dataAssegnazione;
	}

	public void setDataAssegnazione(Date dataAssegnazione) {
		this.dataAssegnazione = dataAssegnazione;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public Date getDataAnnullo() {
		return dataAnnullo;
	}

	public void setDataAnnullo(Date dataAnnullo) {
		this.dataAnnullo = dataAnnullo;
	}

	public String getCommissioneToDelete() {
		return commissioneToDelete;
	}

	public void setCommissioneToDelete(String commissioneToDelete) {
		this.commissioneToDelete = commissioneToDelete;
	}

	public Map<String, String> getOrganismiStatutari() {
		return organismiStatutari;
	}

	public void setOrganismiStatutari(Map<String, String> organismiStatutari) {
		this.organismiStatutari = organismiStatutari;
	}	

	public String getNomeOrganismoStatutario() {
		return nomeOrganismoStatutario;
	}

	public void setNomeOrganismoStatutario(String nomeOrganismoStatutario) {
		this.nomeOrganismoStatutario = nomeOrganismoStatutario;
	}

	public Date getDataAssegnazioneParere() {
		return dataAssegnazioneParere;
	}

	public void setDataAssegnazioneParere(Date dataAssegnazioneParere) {
		this.dataAssegnazioneParere = dataAssegnazioneParere;
	}

	public Date getDataAnnulloParere() {
		return dataAnnulloParere;
	}

	public void setDataAnnulloParere(Date dataAnnulloParere) {
		this.dataAnnulloParere = dataAnnulloParere;
	}

	public boolean isObbligatorio() {
		return obbligatorio;
	}

	public void setObbligatorio(boolean obbligatorio) {
		this.obbligatorio = obbligatorio;
	}	

	public String getParereToDelete() {
		return parereToDelete;
	}

	public void setParereToDelete(String parereToDelete) {
		this.parereToDelete = parereToDelete;
	}

	public OrganismoStatutarioServiceManager getOrganismoStatutarioServiceManager() {
		return organismoStatutarioServiceManager;
	}

	public void setOrganismoStatutarioServiceManager(
			OrganismoStatutarioServiceManager organismoStatutarioServiceManager) {
		this.organismoStatutarioServiceManager = organismoStatutarioServiceManager;
	}

	public String getAllegatoToDelete() {
		return allegatoToDelete;
	}

	public void setAllegatoToDelete(String allegatoToDelete) {
		this.allegatoToDelete = allegatoToDelete;
	}

	public String getLinkToDelete() {
		return linkToDelete;
	}

	public void setLinkToDelete(String linkToDelete) {
		this.linkToDelete = linkToDelete;
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

	public String getCommissioneToAnnul() {
		return commissioneToAnnul;
	}

	public void setCommissioneToAnnul(String commissioneToAnnul) {
		this.commissioneToAnnul = commissioneToAnnul;
	}

	public String getStatoCommitInfoGen() {
		return statoCommitInfoGen;
	}

	public void setStatoCommitInfoGen(String statoCommitInfoGen) {
		this.statoCommitInfoGen = statoCommitInfoGen;
	}

	public String getStatoCommitAmmissibilita() {
		return statoCommitAmmissibilita;
	}

	public void setStatoCommitAmmissibilita(String statoCommitAmmissibilita) {
		this.statoCommitAmmissibilita = statoCommitAmmissibilita;
	}

	public String getStatoCommitAssegnazione() {
		return statoCommitAssegnazione;
	}

	public void setStatoCommitAssegnazione(String statoCommitAssegnazione) {
		this.statoCommitAssegnazione = statoCommitAssegnazione;
	}

	public String getStatoCommitNote() {
		return statoCommitNote;
	}

	public void setStatoCommitNote(String statoCommitNote) {
		this.statoCommitNote = statoCommitNote;
	}

	public Atto getAtto() {
		return atto;
	}

	public void setAtto(Atto atto) {
		this.atto = atto;
	}

	public String getValutazioneAmmissibilita() {
		return atto.getValutazioneAmmissibilita();
	}

	public void setValutazioneAmmissibilita(String valutazioneAmmissibilita) {
		this.atto.setValutazioneAmmissibilita(valutazioneAmmissibilita);
	}

	public Date getDataRichiestaInformazioni() {
		return atto.getDataRichiestaInformazioni();
	}

	public void setDataRichiestaInformazioni(Date dataRichiestaInformazioni) {
		this.atto.setDataRichiestaInformazioni(dataRichiestaInformazioni);
	}

	public Date getDataRicevimentoInformazioni() {
		return atto.getDataRicevimentoInformazioni();
	}

	public void setDataRicevimentoInformazioni(
			Date dataRicevimentoInformazioni) {
		this.atto.setDataRicevimentoInformazioni(dataRicevimentoInformazioni);
	}

	public boolean isAiutiStato() {
		return atto.isAiutiStato();
	}

	public void setAiutiStato(boolean aiutiStato) {
		this.atto.setAiutiStato(aiutiStato);
	}

	public boolean isNormaFinanziaria() {
		return atto.isNormaFinanziaria();
	}

	public void setNormaFinanziaria(boolean normaFinanziaria) {
		this.atto.setNormaFinanziaria(normaFinanziaria);
	}

	public boolean isRichiestaUrgenza() {
		return atto.isRichiestaUrgenza();
	}

	public void setRichiestaUrgenza(boolean richiestaUrgenza) {
		this.atto.setRichiestaUrgenza(richiestaUrgenza);
	}

	public boolean isVotazioneUrgenza() {
		return atto.isVotazioneUrgenza();
	}

	public void setVotazioneUrgenza(boolean votazioneUrgenza) {
		this.atto.setVotazioneUrgenza(votazioneUrgenza);
	}

	public Date getDataVotazioneUrgenza() {
		return atto.getDataVotazioneUrgenza();
	}

	public void setDataVotazioneUrgenza(Date dataVotazioneUrgenza) {
		this.atto.setDataVotazioneUrgenza(dataVotazioneUrgenza);
	}

	public String getNoteAmmissibilita() {
		return atto.getNoteAmmissibilita();
	}

	public void setNoteAmmissibilita(String noteAmmissibilita) {
		this.atto.setNoteAmmissibilita(noteAmmissibilita);
	}

	public String getNoteNoteAllegati() {
		return atto.getNotePresentazioneAssegnazione();
	}

	public void setNoteNoteAllegati(String noteNoteAllegati) {
		this.atto.setNotePresentazioneAssegnazione(noteNoteAllegati);
	}

	public Map<String, String> getTipiIniziativa() {
		return tipiIniziativa;
	}

	public void setTipiIniziativa(Map<String, String> tipiIniziativa) {
		this.tipiIniziativa = tipiIniziativa;
	}

	public TipoIniziativaServiceManager getTipoIniziativaServiceManager() {
		return tipoIniziativaServiceManager;
	}

	public void setTipoIniziativaServiceManager(
			TipoIniziativaServiceManager tipoIniziativaServiceManager) {
		this.tipoIniziativaServiceManager = tipoIniziativaServiceManager;
	}

	public String getNumeroAtto() {
		return atto.getNumeroAtto();
	}

	public void setNumeroAtto(String numeroAtto) {
		this.atto.setNumeroAtto(numeroAtto);
	}

	public String getClassificazione() {
		return atto.getClassificazione();
	}

	public void setClassificazione(String classificazione) {
		this.atto.setClassificazione(classificazione);
	}

	public String getOggetto() {
		return atto.getOggetto();
	}

	public void setOggetto(String oggetto) {
		this.atto.setOggetto(oggetto);
	}

	public String getNumeroRepertorio() {
		return atto.getNumeroRepertorio();
	}

	public void setNumeroRepertorio(String numeroRepertorio) {
		this.atto.setNumeroRepertorio(numeroRepertorio);
	}

	public Date getDataRepertorio() {
		return atto.getDataRepertorio();
	}

	public void setDataRepertorio(Date dataRepertorio) {
		this.atto.setDataRepertorio(dataRepertorio);
	}

	public String getTipoIniziativa() {
		return atto.getTipoIniziativa();
	}

	public void setTipoIniziativa(String tipoIniziativa) {
		this.atto.setTipoIniziativa(tipoIniziativa);
	}

	public Date getDataIniziativa() {
		return atto.getDataIniziativa();
	}

	public void setDataIniziativa(Date dataIniziativa) {
		this.atto.setDataIniziativa(dataIniziativa);
	}

	public String getDescrizioneIniziativa() {
		return atto.getDescrizioneIniziativa();
	}

	public void setDescrizioneIniziativa(String descrizioneIniziativa) {
		this.atto.setDescrizioneIniziativa(descrizioneIniziativa);
	}

	public String getNumeroDgr() {
		return atto.getNumeroDgr();
	}

	public void setNumeroDgr(String numeroDgr) {
		this.atto.setNumeroDgr(numeroDgr);
	}

	public Date getDataDgr() {
		return atto.getDataDgr();
	}

	public void setDataDgr(Date dataDgr) {
		this.atto.setDataDgr(dataDgr);
	}

	public String getAssegnazione() {
		return atto.getAssegnazione();
	}

	public void setAssegnazione(String assegnazione) {
		this.atto.setAssegnazione(assegnazione);
	}

	public List<Firmatario> getFirmatariList() {
		return firmatariList;
	}

	public void setFirmatariList(List<Firmatario> firmatariList) {
		this.firmatariList = firmatariList;
	}


	public List<Commissione> getCommissioniList() {
		return commissioniList;
	}

	public void setCommissioniList(List<Commissione> commissioniList) {
		this.commissioniList = commissioniList;
	}

	public List<Link> getLinksList() {
		return linksList;
	}

	public void setLinksList(List<Link> linksList) {
		this.linksList = linksList;
	}

	public List<AttoRecord> getTestiAttoList() {
		return testiAttoList;
	}

	public void setTestiAttoList(List<AttoRecord> testiAttoList) {
		this.testiAttoList = testiAttoList;
	}

	public List<Allegato> getAllegatiList() {
		return allegatiList;
	}

	public void setAllegatiList(List<Allegato> allegatiList) {
		this.allegatiList = allegatiList;
	}

	public List<Parere> getPareriList() {
		return pareriList;
	}

	public void setPareriList(List<Parere> pareriList) {
		this.pareriList = pareriList;
	}

	public Date getDataPresaInCarico() {
		return atto.getDataPresaInCarico();
	}

	public void setDataPresaInCarico(Date dataPresaInCarico) {
		this.atto.setDataPresaInCarico(dataPresaInCarico);
	}





}
