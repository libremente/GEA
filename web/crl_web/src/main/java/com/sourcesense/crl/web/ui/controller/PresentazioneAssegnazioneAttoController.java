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
import com.sourcesense.crl.business.model.Commissione;
import com.sourcesense.crl.business.model.Firmatario;
import com.sourcesense.crl.business.model.GruppoConsiliare;
import com.sourcesense.crl.business.model.Link;
import com.sourcesense.crl.business.model.OrganismoStatutario;
import com.sourcesense.crl.business.model.Personale;
import com.sourcesense.crl.business.model.Relatore;
import com.sourcesense.crl.business.service.AttoServiceManager;
import com.sourcesense.crl.business.service.CommissioneServiceManager;
import com.sourcesense.crl.business.service.OrganismoStatutarioServiceManager;
import com.sourcesense.crl.business.service.PersonaleServiceManager;
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

	private List<Firmatario> firmatariList = new ArrayList<Firmatario>();
	private Map<String, String> firmatari = new HashMap<String, String>();
	private String nomeFirmatario;

	private List<Allegato> testiAttoList = new ArrayList<Allegato>();
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
	private List<OrganismoStatutario> organismiStatutariList = new ArrayList<OrganismoStatutario>();

	private String nomeOrganismoStatutario;
	private Date dataAssegnazioneOrganismo;
	private Date dataAnnulloOrganismo;
	private boolean obbligatorio;

	private String organismoStatutarioToDelete;

	private List<Allegato> allegatiList = new ArrayList<Allegato>();
	private String allegatoToDelete;

	private List<Link> linksList = new ArrayList<Link>();

	private String nomeLink;
	private String urlLink;
	private boolean pubblico;

	private String linkToDelete;

	@PostConstruct
	protected void init() {
		setFirmatari(personaleServiceManager.findAllFirmatario());
		// TODO: setCommissioni(commissioneServiceManager.findAll());
		setCommissioni(commissioneServiceManager.findAllCommissioneConsultiva());
		setOrganismiStatutari(organismoStatutarioServiceManager.findAll());

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
			Allegato allegatoRet = null;
			Allegato allegato = new Allegato();
			allegato.setDescrizione(fileName);
			allegato.setDownloadUrl("hppt://yourhost:0808/file");
			allegato.setPubblico(true);
			allegato.setTipoAllegato("TestoAtto");

			try {
				allegatoRet = attoServiceManager.uploadFile(
						((AttoBean) FacesContext.getCurrentInstance()
								.getExternalContext().getSessionMap()
								.get("attoBean")).getAtto(), allegato, event
								.getFile().getInputstream());
			} catch (IOException e) {
				e.printStackTrace();
			}

			// TODO aggiungi a bean di sessione
			testiAttoList.add(allegato);
			setStatoCommitInfoGen(CRLMessage.COMMIT_UNDONE);
		}
	}

	private boolean checkTestoAtto(String fileName) {

		for (Allegato element : testiAttoList) {

			if (element.getDescrizione().equals(fileName)) {

				return false;
			}

		}

		return true;
	}

	public void removeTestoAtto() {

		for (Allegato element : testiAttoList) {

			if (element.getDescrizione().equals(testoAttoToDelete)) {
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

			} else {
				Firmatario firmatario = new Firmatario();
				firmatario.setDescrizione(nomeFirmatario);
				firmatario.setDataFirma(dataFirma);
				firmatario.setDataRitiro(dataRitiro);
				firmatario.setGruppoConsiliare(gruppoConsiliare);
				firmatario.setPrimoFirmatario(primoFirmatario);
				firmatariList.add(firmatario);
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

	public void ritiraPerMancanzaFirmatari() {

		// TODO Service logic

	}

	public void salvaInfoGenerali() {

		// TODO Service logic
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));
		// TODO

		// set lists
		attoBean.getAtto().setAllegati(this.testiAttoList);
		attoBean.getAtto().setFirmatari(this.firmatariList);
		attoServiceManager.merge(attoBean.getAtto());

	}

	// Ammissibilita******************************************************

	public void salvaAmmissibilita() {

		// TODO Service logic
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));
		// TODO

		// attoBean.merge();

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

	public void addOrganismoStatutario() {

		if (nomeOrganismoStatutario != null
				&& !nomeOrganismoStatutario.trim().equals("")) {
			if (!checkOrganismiStatutari()) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Attenzione ! Organismo Statutario "
								+ nomeOrganismoStatutario + " già presente ",
						""));

			} else {
				OrganismoStatutario organismoStatutario = new OrganismoStatutario();
				organismoStatutario.setDescrizione(nomeOrganismoStatutario);
				organismoStatutario
						.setDataAssegnazione(dataAssegnazioneOrganismo);
				organismoStatutario.setObbligatorio(obbligatorio);
				organismiStatutariList.add(organismoStatutario);
			}
		}
	}

	public void removeOrganismoStatutario() {

		for (OrganismoStatutario element : organismiStatutariList) {

			if (element.getDescrizione().equals(organismoStatutarioToDelete)) {

				organismiStatutariList.remove(element);
				break;
			}
		}
	}

	private boolean checkOrganismiStatutari() {

		for (OrganismoStatutario element : organismiStatutariList) {

			if (element.getDescrizione().equals(nomeOrganismoStatutario)) {

				return false;
			}

		}

		return true;
	}

	public void confermaAssegnazione() {
		// TODO
		setStatoCommitAssegnazione(CRLMessage.COMMIT_DONE);
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
			Allegato allegato = new Allegato();
			allegato.setDescrizione(fileName);
			allegato.setDownloadUrl("hppt://yourhost:0808/file");
			allegato.setPubblico(true);
			allegato.setTipoAllegato("Allegato");

			try {
				allegatoRet = attoServiceManager.uploadFile(
						((AttoBean) FacesContext.getCurrentInstance()
								.getExternalContext().getSessionMap()
								.get("attoBean")).getAtto(), allegato, event
								.getFile().getInputstream());
			} catch (IOException e) {
				e.printStackTrace();
			}

			// TODO aggiungi a bean di sessione
			allegatiList.add(allegato);
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

			if (element.getDescrizione().equals(allegatoToDelete)) {

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
		// TODO
	}

	// Getters & Setters******************************************************
	public List<Firmatario> getFirmatariList() {
		return firmatariList;
	}

	public void setFirmatariList(List<Firmatario> firmatariList) {
		this.firmatariList = firmatariList;
	}

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

	public List<Allegato> getTestiAttoList() {
		return testiAttoList;
	}

	public void setTestiAttoList(List<Allegato> testiAttoList) {
		this.testiAttoList = testiAttoList;
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

	public List<OrganismoStatutario> getOrganismiStatutariList() {
		return organismiStatutariList;
	}

	public void setOrganismiStatutariList(
			List<OrganismoStatutario> organismiStatutariList) {
		this.organismiStatutariList = organismiStatutariList;
	}

	public String getNomeOrganismoStatutario() {
		return nomeOrganismoStatutario;
	}

	public void setNomeOrganismoStatutario(String nomeOrganismoStatutario) {
		this.nomeOrganismoStatutario = nomeOrganismoStatutario;
	}

	public Date getDataAssegnazioneOrganismo() {
		return dataAssegnazioneOrganismo;
	}

	public void setDataAssegnazioneOrganismo(Date dataAssegnazioneOrganismo) {
		this.dataAssegnazioneOrganismo = dataAssegnazioneOrganismo;
	}

	public Date getDataAnnulloOrganismo() {
		return dataAnnulloOrganismo;
	}

	public void setDataAnnulloOrganismo(Date dataAnnulloOrganismo) {
		this.dataAnnulloOrganismo = dataAnnulloOrganismo;
	}

	public boolean isObbligatorio() {
		return obbligatorio;
	}

	public void setObbligatorio(boolean obbligatorio) {
		this.obbligatorio = obbligatorio;
	}

	public String getOrganismoStatutarioToDelete() {
		return organismoStatutarioToDelete;
	}

	public void setOrganismoStatutarioToDelete(
			String organismoStatutarioToDelete) {
		this.organismoStatutarioToDelete = organismoStatutarioToDelete;
	}

	public OrganismoStatutarioServiceManager getOrganismoStatutarioServiceManager() {
		return organismoStatutarioServiceManager;
	}

	public void setOrganismoStatutarioServiceManager(
			OrganismoStatutarioServiceManager organismoStatutarioServiceManager) {
		this.organismoStatutarioServiceManager = organismoStatutarioServiceManager;
	}

	public List<Allegato> getAllegatiList() {
		return allegatiList;
	}

	public void setAllegatiList(List<Allegato> allegatiList) {
		this.allegatiList = allegatiList;
	}

	public String getAllegatoToDelete() {
		return allegatoToDelete;
	}

	public void setAllegatoToDelete(String allegatoToDelete) {
		this.allegatoToDelete = allegatoToDelete;
	}

	public List<Link> getLinksList() {
		return linksList;
	}

	public void setLinksList(List<Link> linksList) {
		this.linksList = linksList;
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

}
