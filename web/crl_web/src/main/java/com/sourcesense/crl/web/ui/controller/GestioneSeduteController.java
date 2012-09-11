package com.sourcesense.crl.web.ui.controller;

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

import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.model.AttoTrattato;
import com.sourcesense.crl.business.model.Audizione;
import com.sourcesense.crl.business.model.Collegamento;
import com.sourcesense.crl.business.model.CollegamentoAttiSindacato;
import com.sourcesense.crl.business.model.Consultazione;
import com.sourcesense.crl.business.model.ConsultazioneAtto;
import com.sourcesense.crl.business.model.GruppoUtente;
import com.sourcesense.crl.business.model.Link;
import com.sourcesense.crl.business.model.Seduta;
import com.sourcesense.crl.business.service.AttoServiceManager;
import com.sourcesense.crl.util.CRLMessage;
import com.sourcesense.crl.util.Clonator;
import com.sourcesense.crl.web.ui.beans.UserBean;

@ManagedBean(name = "gestioneSeduteController")
@ViewScoped
public class GestioneSeduteController {

	@ManagedProperty(value = "#{attoServiceManager}")
	private AttoServiceManager attoServiceManager;

	private Date dataSedutaDa;
	private Date dataSedutaA;
	private List<Seduta> seduteList = new ArrayList<Seduta>();
	private Date sedutaToDelete;
	private Date dataSedutaSelected;
	private Seduta sedutaSelected;

	private Date dataSeduta;
	private String numVerbale;
	private String note;

	private List<Link> linksList = new ArrayList<Link>();	
	private String linkToDelete;
	private String nomeLink;
	private String urlLink;


	private Map<Date, Date> dateSedute = new HashMap<Date, Date>();

	private List<AttoTrattato> attiTrattati = new ArrayList<AttoTrattato>();
	private String idAttoTrattatoToDelete;

	private List<ConsultazioneAtto> consultazioniAtti = new ArrayList<ConsultazioneAtto>();
	private String idConsultazioneToDelete;

	private List<Audizione> audizioni = new ArrayList<Audizione>();
	private String soggettoPartecipante;
	private boolean previsto;
	private boolean discusso;
	private String audizioneToDelete;

	private List<CollegamentoAttiSindacato> attiSindacato = new ArrayList<CollegamentoAttiSindacato>();
	private String tipoAttoSindacato;
	private String numeroAttoSindacato;
	private String descrizioneCollegamento;
	private String attoSindacatoToDelete;


	private Map<String, String> tipiAttoSindacato = new HashMap<String, String>();
	private Map<String, String> numeriAttoSindacato = new HashMap<String, String>();

	private String statoCommitInserisciSeduta = CRLMessage.COMMIT_DONE;
	private String statoCommitInserisciOdg = CRLMessage.COMMIT_DONE;


	@PostConstruct
	protected void init() {
		// TODO caricamento liste
		FacesContext context = FacesContext.getCurrentInstance();
		UserBean userBean = ((UserBean) context.getExternalContext()
				.getSessionMap().get("userBean"));
		setSeduteList(Clonator.cloneList(userBean.getUser().getSedute()));

		if(!seduteList.isEmpty()) {
			setDataSedutaSelected(seduteList.get(0).getDataSeduta());
			showSedutaDetail();

			fillDateSeduteMap();
		}

	}

	public void updateInserisciSedutaHandler() {
		setStatoCommitInserisciSeduta(CRLMessage.COMMIT_UNDONE);
	}

	public void updateInserisciOdgHandler() {
		setStatoCommitInserisciOdg(CRLMessage.COMMIT_UNDONE);
	}

	public void changeTabHandler() {

		if (statoCommitInserisciSeduta.equals(CRLMessage.COMMIT_UNDONE)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Attenzione ! Le modifiche alla Seduta non sono state salvate ",
							""));
		}

		if (statoCommitInserisciOdg.equals(CRLMessage.COMMIT_UNDONE)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Attenzione ! Le modifiche su Inserisci ODG non sono state salvate ",
							""));
		}
	}

	// Inserisci Seduta***********************************

	public void showSedutaDetail() {
		setSedutaSelected(findSeduta(dataSedutaSelected));

		if(sedutaSelected!=null) {
			setDataSeduta(sedutaSelected.getDataSeduta());
			setNumVerbale(sedutaSelected.getNumVerbale());
			setNote(sedutaSelected.getNote());
			setLinksList(Clonator.cloneList(sedutaSelected.getLinks()));
			setAttiTrattati(Clonator.cloneList(sedutaSelected.getAttiTrattati()));
			setAttiSindacato(Clonator.cloneList(sedutaSelected.getAttiSindacato()));
			setConsultazioniAtti(Clonator.cloneList(sedutaSelected.getConsultazioniAtti()));
			setAudizioni(Clonator.cloneList(sedutaSelected.getAudizioni()));
		}
		else {
			setDataSeduta(null);
			setNumVerbale("");
			setNote(null);
			setLinksList(new ArrayList<Link>());
			setAttiTrattati(new ArrayList<AttoTrattato>());
			setAttiSindacato(new ArrayList<CollegamentoAttiSindacato>());
			setConsultazioniAtti(new ArrayList<ConsultazioneAtto>());
			setAudizioni(new ArrayList<Audizione>());
		}
	}

	public Seduta findSeduta(Date dataSeduta) {
		for(Seduta element : seduteList) {
			if(element.getDataSeduta().equals(dataSeduta)) {
				return element;
			}
		}
		return null;
	}

	public void removeSeduta() {
		for (Seduta element : seduteList) {

			if (element.getDataSeduta().equals(sedutaToDelete)) {

				seduteList.remove(element);
				updateInserisciSedutaHandler();

				if(!seduteList.isEmpty()) {
					setDataSedutaSelected(seduteList.get(0).getDataSeduta());
				}
				showSedutaDetail();
				break;
			}
		}
	}

	public String dettaglioOdg() {
		setSedutaSelected(findSeduta(dataSedutaSelected));
		//TODO: ritorno alla pagina di inserimento ODG
		return null;
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
				linksList.add(link);

				updateInserisciSedutaHandler();
			}
		}
	}

	public void removeLink() {

		for (Link element : linksList) {

			if (element.getDescrizione().equals(linkToDelete)) {

				linksList.remove(element);
				updateInserisciSedutaHandler();
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


	public void salvaAggiungiSeduta() {
		if(getDataSeduta()!=null) {
			Seduta seduta = findSeduta(dataSeduta);

			if(seduta==null) {
				seduta = new Seduta();
				seduta.setDataSeduta(getDataSeduta());
				seduta.setNumVerbale(getNumVerbale());
				seduta.setNote(getNote());
				seduta.setLinks(Clonator.cloneList(getLinksList()));

				seduteList.add(seduta);
			}

			else {
				seduta.setNumVerbale(getNumVerbale());
				seduta.setNote(getNote());
				seduta.setLinks(Clonator.cloneList(getLinksList()));
			}
			updateInserisciSedutaHandler();
		}
	}

	public void salvaInserisciSedute() {
		// TODO: alfresco service

		FacesContext context = FacesContext.getCurrentInstance();
		UserBean userBean = ((UserBean) context.getExternalContext()
				.getSessionMap().get("userBean"));

		userBean.getUser().setSedute(Clonator.cloneList(getSeduteList()));

		fillDateSeduteMap();

		setStatoCommitInserisciSeduta(CRLMessage.COMMIT_DONE);
		context.addMessage(null, new FacesMessage("Sedute salvate con successo", ""));
	}


	// Inserisci ODG***************************************

	public void fillDateSeduteMap() {
		dateSedute = new HashMap<Date, Date>();
		for(Seduta element : seduteList) {			
			dateSedute.put(element.getDataSeduta(), element.getDataSeduta());
		}
	}

	public void updateSedutaInserisciOdg() {
		if(!seduteList.isEmpty()) {
			setDataSedutaSelected(seduteList.get(0).getDataSeduta());
		}
		showSedutaDetail();
	}

	public void addAttoTrattato(String idAttoTrattatoToAdd) {

		if (!idAttoTrattatoToAdd.trim().equals("")) {
			if (!checkAttiTrattati(idAttoTrattatoToAdd)) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Attenzione ! Atto già abbinato ", ""));

			} else {
				Atto attoDaCollegare = attoServiceManager.findById(idAttoTrattatoToAdd);
				AttoTrattato attoTrattato = new AttoTrattato();
				attoTrattato.setAtto(attoDaCollegare);
				attiTrattati.add(attoTrattato);

				updateInserisciOdgHandler();
			}
		}
	}

	public void removeAttoTrattato() {

		for (AttoTrattato element : attiTrattati) {

			if (element.getAtto().getId().equals(idAttoTrattatoToDelete)) {

				attiTrattati.remove(element);
				updateInserisciOdgHandler();
				break;
			}
		}
	}

	private boolean checkAttiTrattati(String idAttoTrattatoToAdd) {

		for (AttoTrattato element : attiTrattati) {

			if (element.getAtto().getId().equals(idAttoTrattatoToAdd)) {

				return false;
			}
		}
		return true;
	}

	public void addConsultazioneAtto(String idAttoConsultatoToAdd) {

		if (!idAttoConsultatoToAdd.trim().equals("")) {
			if (!checkAttiConsultati(idAttoConsultatoToAdd)) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Attenzione ! Atto già abbinato ", ""));

			} else {
				Atto attoDaCollegare = attoServiceManager.findById(idAttoConsultatoToAdd);
				ConsultazioneAtto attoConsultato = new ConsultazioneAtto();
				attoConsultato.setAtto(attoDaCollegare);
				consultazioniAtti.add(attoConsultato);

				updateInserisciOdgHandler();
			}
		}
	}

	public void removeAttoConsultato() {

		for (ConsultazioneAtto element : consultazioniAtti) {

			if (element.getAtto().getId().equals(idConsultazioneToDelete)) {

				consultazioniAtti.remove(element);
				updateInserisciOdgHandler();
				break;
			}
		}
	}

	private boolean checkAttiConsultati(String idAttoConsultatoToAdd) {

		for (ConsultazioneAtto element : consultazioniAtti) {

			if (element.getAtto().getId().equals(idAttoConsultatoToAdd)) {

				return false;
			}
		}
		return true;
	}

	public void addAudizione() {

		if (!soggettoPartecipante.trim().equals("")) {
			if (!checkAudizioni(soggettoPartecipante)) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Attenzione ! Soggetto già inserito ", ""));

			} else {
				Audizione audizione = new Audizione();
				audizione.setSoggettoPartecipante(getSoggettoPartecipante());
				audizione.setDiscusso(isDiscusso());
				audizione.setPrevisto(isPrevisto());
				audizioni.add(audizione);

				updateInserisciOdgHandler();
			}
		}
	}

	public void removeAudizione() {

		for (Audizione element : audizioni) {

			if (element.getSoggettoPartecipante().equals(audizioneToDelete)) {

				audizioni.remove(element);
				updateInserisciOdgHandler();
				break;
			}
		}
	}

	private boolean checkAudizioni(String soggettoToAdd) {

		for (Audizione element : audizioni) {

			if (element.getSoggettoPartecipante().equals(soggettoToAdd)) {

				return false;
			}
		}
		return true;
	}

	public void addCollegamentoAttoSindacato() {

		if (!numeroAttoSindacato.trim().equals("")) {
			if (!checkCollegamentiAttiSindacati()) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Attenzione ! Atto già collegato ", ""));

			} else {
				//TODO: alfresco service (oggetto e lista firmatari)
				CollegamentoAttiSindacato collegamento = new CollegamentoAttiSindacato();
				collegamento.setNumeroAtto(getNumeroAttoSindacato());
				collegamento.setTipoAtto(getTipoAttoSindacato());
				attiSindacato.add(collegamento);

				updateInserisciOdgHandler();
			}
		}
	}

	public void removeCollegamentoAttoSindacato() {

		for (CollegamentoAttiSindacato element : attiSindacato) {

			if (element.getNumeroAtto().equals(attoSindacatoToDelete)) {

				attiSindacato.remove(element);
				updateInserisciOdgHandler();
				break;
			}
		}
	}

	private boolean checkCollegamentiAttiSindacati() {

		for (CollegamentoAttiSindacato element : attiSindacato) {

			if (element.getNumeroAtto().equals(numeroAttoSindacato)) {

				return false;
			}
		}
		return true;
	}

	public void salvaInserisciOdg() {
		//TODO: alfresco service

		if (sedutaSelected!=null) {
			sedutaSelected.setAttiSindacato(Clonator
					.cloneList(getAttiSindacato()));
			sedutaSelected.setAttiTrattati(Clonator
					.cloneList(getAttiTrattati()));
			sedutaSelected.setAudizioni(Clonator.cloneList(getAudizioni()));
			sedutaSelected.setConsultazioniAtti(Clonator
					.cloneList(getConsultazioniAtti()));
		}
		FacesContext context = FacesContext.getCurrentInstance();
		UserBean userBean = ((UserBean) context.getExternalContext()
				.getSessionMap().get("userBean"));

		userBean.getUser().setSedute(Clonator.cloneList(getSeduteList()));

		setStatoCommitInserisciOdg(CRLMessage.COMMIT_DONE);
		context.addMessage(null, new FacesMessage("ODG salvato con successo", ""));
	}




	// Getters & Setters***********************************
	public Date getDataSedutaDa() {
		return dataSedutaDa;
	}


	public void setDataSedutaDa(Date dataSedutaDa) {
		this.dataSedutaDa = dataSedutaDa;
	}


	public Date getDataSedutaA() {
		return dataSedutaA;
	}


	public void setDataSedutaA(Date dataSedutaA) {
		this.dataSedutaA = dataSedutaA;
	}


	public List<Seduta> getSeduteList() {
		return seduteList;
	}


	public void setSeduteList(List<Seduta> seduteList) {
		this.seduteList = seduteList;
	}


	public Date getSedutaToDelete() {
		return sedutaToDelete;
	}


	public void setSedutaToDelete(Date sedutaToDelete) {
		this.sedutaToDelete = sedutaToDelete;
	}


	public Date getDataSeduta() {
		return dataSeduta;
	}


	public void setDataSeduta(Date dataSeduta) {
		this.dataSeduta = dataSeduta;
	}


	public String getNumVerbale() {
		return numVerbale;
	}


	public void setNumVerbale(String numVerbale) {
		this.numVerbale = numVerbale;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
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


	public String getStatoCommitInserisciSeduta() {
		return statoCommitInserisciSeduta;
	}


	public void setStatoCommitInserisciSeduta(String statoCommitInserisciSeduta) {
		this.statoCommitInserisciSeduta = statoCommitInserisciSeduta;
	}


	public String getStatoCommitInserisciOdg() {
		return statoCommitInserisciOdg;
	}


	public void setStatoCommitInserisciOdg(String statoCommitInserisciOdg) {
		this.statoCommitInserisciOdg = statoCommitInserisciOdg;
	}

	public Date getDataSedutaSelected() {
		return dataSedutaSelected;
	}

	public void setDataSedutaSelected(Date dataSedutaSelected) {
		this.dataSedutaSelected = dataSedutaSelected;
	}

	public Seduta getSedutaSelected() {
		return sedutaSelected;
	}

	public void setSedutaSelected(Seduta sedutaSelected) {
		this.sedutaSelected = sedutaSelected;
	}

	public Map<Date, Date> getDateSedute() {
		return dateSedute;
	}

	public void setDateSedute(Map<Date, Date> dateSedute) {
		this.dateSedute = dateSedute;
	}

	public String getIdAttoTrattatoToDelete() {
		return idAttoTrattatoToDelete;
	}

	public void setIdAttoTrattatoToDelete(String idAttoTrattatoToDelete) {
		this.idAttoTrattatoToDelete = idAttoTrattatoToDelete;
	}


	public String getIdConsultazioneToDelete() {
		return idConsultazioneToDelete;
	}

	public void setIdConsultazioneToDelete(String idConsultazioneToDelete) {
		this.idConsultazioneToDelete = idConsultazioneToDelete;
	}

	public String getSoggettoPartecipante() {
		return soggettoPartecipante;
	}

	public void setSoggettoPartecipante(String soggettoPartecipante) {
		this.soggettoPartecipante = soggettoPartecipante;
	}

	public boolean isPrevisto() {
		return previsto;
	}

	public void setPrevisto(boolean previsto) {
		this.previsto = previsto;
	}

	public boolean isDiscusso() {
		return discusso;
	}

	public void setDiscusso(boolean discusso) {
		this.discusso = discusso;
	}

	public Map<String, String> getTipiAttoSindacato() {
		return tipiAttoSindacato;
	}

	public void setTipiAttoSindacato(Map<String, String> tipiAttoSindacato) {
		this.tipiAttoSindacato = tipiAttoSindacato;
	}

	public Map<String, String> getNumeriAttoSindacato() {
		return numeriAttoSindacato;
	}

	public void setNumeriAttoSindacato(Map<String, String> numeriAttoSindacato) {
		this.numeriAttoSindacato = numeriAttoSindacato;
	}

	public List<AttoTrattato> getAttiTrattati() {
		return attiTrattati;
	}

	public void setAttiTrattati(List<AttoTrattato> attiTrattati) {
		this.attiTrattati = attiTrattati;
	}

	public List<ConsultazioneAtto> getConsultazioniAtti() {
		return consultazioniAtti;
	}

	public void setConsultazioniAtti(List<ConsultazioneAtto> consultazioniAtti) {
		this.consultazioniAtti = consultazioniAtti;
	}

	public List<Audizione> getAudizioni() {
		return audizioni;
	}

	public void setAudizioni(List<Audizione> audizioni) {
		this.audizioni = audizioni;
	}

	public List<CollegamentoAttiSindacato> getAttiSindacato() {
		return attiSindacato;
	}

	public void setAttiSindacato(List<CollegamentoAttiSindacato> attiSindacato) {
		this.attiSindacato = attiSindacato;
	}

	public AttoServiceManager getAttoServiceManager() {
		return attoServiceManager;
	}

	public void setAttoServiceManager(AttoServiceManager attoServiceManager) {
		this.attoServiceManager = attoServiceManager;
	}

	public String getAudizioneToDelete() {
		return audizioneToDelete;
	}

	public void setAudizioneToDelete(String audizioneToDelete) {
		this.audizioneToDelete = audizioneToDelete;
	}

	public String getTipoAttoSindacato() {
		return tipoAttoSindacato;
	}

	public void setTipoAttoSindacato(String tipoAttoSindacato) {
		this.tipoAttoSindacato = tipoAttoSindacato;
	}

	public String getNumeroAttoSindacato() {
		return numeroAttoSindacato;
	}

	public void setNumeroAttoSindacato(String numeroAttoSindacato) {
		this.numeroAttoSindacato = numeroAttoSindacato;
	}

	public String getDescrizioneCollegamento() {
		return descrizioneCollegamento;
	}

	public void setDescrizioneCollegamento(String descrizioneCollegamento) {
		this.descrizioneCollegamento = descrizioneCollegamento;
	}

	public String getAttoSindacatoToDelete() {
		return attoSindacatoToDelete;
	}

	public void setAttoSindacatoToDelete(String attoSindacatoToDelete) {
		this.attoSindacatoToDelete = attoSindacatoToDelete;
	}













}
