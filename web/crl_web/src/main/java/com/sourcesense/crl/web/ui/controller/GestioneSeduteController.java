package com.sourcesense.crl.web.ui.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.sourcesense.crl.business.model.Allegato;
import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.model.AttoTrattato;
import com.sourcesense.crl.business.model.Audizione;
import com.sourcesense.crl.business.model.Collegamento;
import com.sourcesense.crl.business.model.CollegamentoAttiSindacato;
import com.sourcesense.crl.business.model.Consultazione;
import com.sourcesense.crl.business.model.ConsultazioneAtto;
import com.sourcesense.crl.business.model.GestioneSedute;
import com.sourcesense.crl.business.model.GruppoUtente;
import com.sourcesense.crl.business.model.Link;
import com.sourcesense.crl.business.model.Seduta;
import com.sourcesense.crl.business.model.Target;
import com.sourcesense.crl.business.service.AttoRecordServiceManager;
import com.sourcesense.crl.business.service.AttoServiceManager;
import com.sourcesense.crl.business.service.LegislaturaServiceManager;
import com.sourcesense.crl.business.service.SeduteServiceManager;
import com.sourcesense.crl.util.CRLMessage;
import com.sourcesense.crl.util.Clonator;
import com.sourcesense.crl.web.ui.beans.AttoBean;
import com.sourcesense.crl.web.ui.beans.UserBean;
@ManagedBean(name = "gestioneSeduteController")
@ViewScoped
public class GestioneSeduteController {

	@ManagedProperty(value = "#{attoServiceManager}")
	private AttoServiceManager attoServiceManager;

	@ManagedProperty(value = "#{seduteServiceManager}")
	private SeduteServiceManager seduteServiceManager;

	@ManagedProperty(value = "#{attoRecordServiceManager}")
	private AttoRecordServiceManager attoRecordServiceManager;
	
	@ManagedProperty(value = "#{legislaturaServiceManager}")
	private LegislaturaServiceManager legislaturaServiceManager;

	private String legislatura;
	private List<String> legislature = new ArrayList<String>();
	
	private Date dataSedutaDa;
	private Date dataSedutaA;
	private List<Seduta> seduteList = new ArrayList<Seduta>();
	private List<Seduta> seduteListAll = new ArrayList<Seduta>();
	private List<String> dateSeduteList = new ArrayList<String>();
	private String dataSedutaSelected;
	private String attoDaTrattare;
	private Date sedutaToDelete;
	private Seduta sedutaSelected;
	
	private String legislaturaCorrente;

	private Date dataSeduta;
	private String numVerbale;
	private Date dalleOre;
	private Date alleOre;
	private String note;

	private List<Link> linksList = new ArrayList<Link>();
	private String linkToDelete;
	private String nomeLink;
	private String urlLink;

	private StreamedContent file;

	private List<Date> dateSedute = new ArrayList<Date>();

	private List<AttoTrattato> attiTrattati = new ArrayList<AttoTrattato>();
	private String attiTrattatiorder;
	private String attiSindacatoTrattatiorder;

	private String idAttoTrattatoToDelete;

	private List<Consultazione> consultazioniAtti = new ArrayList<Consultazione>();
	private String idConsultazioneToDelete;

	private List<Audizione> audizioni = new ArrayList<Audizione>();
	private String soggettoPartecipante;
	private boolean previsto;
	private boolean discusso;
	private String audizioneToDelete;

	private List<String> tipiAttoSindacato = new ArrayList<String>();
	private String tipoAttoSindacato;
	private List<CollegamentoAttiSindacato> numeriAttoSindacato = new ArrayList<CollegamentoAttiSindacato>();
	private List<CollegamentoAttiSindacato> collegamentiAttiSindacato = new ArrayList<CollegamentoAttiSindacato>();

	private List<CollegamentoAttiSindacato> attiSindacato = new ArrayList<CollegamentoAttiSindacato>();
	private String idAttoSindacato;
	private String descrizioneAttoSindacato;
	private String descrizioneCollegamento;
	private String attoSindacatoToDelete;
	private String statoCommitInserisciSeduta = CRLMessage.COMMIT_DONE;
	private String statoCommitInserisciOdg = CRLMessage.COMMIT_DONE;

	private List<Allegato> odgList = new ArrayList<Allegato>();
	private List<Allegato> verbaliList = new ArrayList<Allegato>();
	private String odgToDelete;
	private String verbaleToDelete;
	private boolean currentFilePubblico;
	// false per legislatura corrente e true per vecchie legislature
	private boolean disableModifiche; 
	
	@PostConstruct
	protected void init() {
		
		disableModifiche = false;

		FacesContext context = FacesContext.getCurrentInstance();
		UserBean userBean = ((UserBean) context.getExternalContext()
				.getSessionMap().get("userBean"));
		
		setLegislature(legislaturaServiceManager.list());
		setLegislatura(legislaturaServiceManager.getAll().get(0).getNome());
		legislaturaCorrente = legislatura;
		seduteListAll = seduteServiceManager.getSedute(userBean.getUser()
				.getSessionGroup().getNome(),legislatura);
	
		seduteList = Clonator.cloneList(seduteListAll);
		Collections.sort(seduteList);
		setAttiSindacato(attoServiceManager.findAllAttiSindacato());
		setTipiAttoSindacato(attoServiceManager.findTipoAttiSindacato());
		
		setDateSeduteList();

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

	public void filterDataTable() {
		
		dateSeduteList.clear();
		seduteList.clear();
		if (!legislatura.equals(legislaturaCorrente)){
			disableModifiche = true;
		}
		else {
			disableModifiche = false;
		}
		
		
		FacesContext context = FacesContext.getCurrentInstance();
		UserBean userBean = ((UserBean) context.getExternalContext()
				.getSessionMap().get("userBean"));
		seduteListAll = seduteServiceManager.getSedute(userBean.getUser()
				.getSessionGroup().getNome(),legislatura);
	
				
		for (Seduta seduta : seduteListAll) {
							
				if  (getDataSedutaDa() == null && getDataSedutaA() == null){
					
					seduteList.add((Seduta) seduta.clone());
				
				}
				
				else if (getDataSedutaDa() != null
						&& getDataSedutaA() != null
						&& seduta.getDataSeduta().getTime()
								- dataSedutaDa.getTime() >= 0
						&& seduta.getDataSeduta().getTime() - dataSedutaA.getTime() <= 86399999) {
	
					seduteList.add((Seduta) seduta.clone());
	
				} else if (getDataSedutaDa() != null
						&& getDataSedutaA() == null
						&& seduta.getDataSeduta().getTime()
								- dataSedutaDa.getTime() >= 0) {
	
					seduteList.add((Seduta) seduta.clone());
	
				} else if (getDataSedutaDa() == null
						&& getDataSedutaA() != null
						&& seduta.getDataSeduta().getTime() - dataSedutaA.getTime() <= 86399999) {
	
					seduteList.add((Seduta) seduta.clone());
				}
			}
		
		setDateSeduteList();
		}	
	
	
	public void showSedutaDetail() {
		setSedutaSelected(findSeduta(dataSedutaSelected));

		if (sedutaSelected != null) {

			setDataSeduta(sedutaSelected.getDataSeduta());
			setNumVerbale(sedutaSelected.getNumVerbale());
			setNote(sedutaSelected.getNote());
			setDalleOre(sedutaSelected.getDalleOre());
			setAlleOre(sedutaSelected.getAlleOre());
			setLinksList(Clonator.cloneList(sedutaSelected.getLinks()));
			// setVerbaliList(Clonator.cloneList(sedutaSelected.getVerbaliList()));
			// setOdgList(Clonator.cloneList(sedutaSelected.getOdgList()));
			setAttiTrattati(Clonator
					.cloneList(sedutaSelected.getAttiTrattati()));
			Collections.sort(attiTrattati);

			setCollegamentiAttiSindacato(Clonator.cloneList(sedutaSelected
					.getAttiSindacato()));
			Collections.sort(collegamentiAttiSindacato);

			setAudizioni(Clonator.cloneList(sedutaSelected.getAudizioni()));

			FacesContext context = FacesContext.getCurrentInstance();
			UserBean userBean = ((UserBean) context.getExternalContext()
					.getSessionMap().get("userBean"));

			consultazioniAtti.clear();

			for (AttoTrattato element : attiTrattati) {

				for (Consultazione consultazione : element.getAtto()
						.getConsultazioni()) {

					Format formatter = new SimpleDateFormat("dd/MM/yyyy");

					Consultazione cons = (Consultazione) consultazione.clone();
					if (formatter.format(cons.getDataSeduta()).equals(
							formatter.format(sedutaSelected.getDataSeduta()))
							&& userBean.getUser().getSessionGroup().getNome()
									.equals(cons.getCommissione())) {
						cons.setNumeroAtto(element.getAtto().getNumeroAtto());
						cons.setTipoAtto(element.getAtto().getTipoAtto());
						cons.setIdAtto(element.getAtto().getId());
						consultazioniAtti.add(cons);
					}
				}

			}

		} else {

			setDataSeduta(null);
			setNumVerbale("");
			setNote(null);
			setLinksList(new ArrayList<Link>());
			setAttiTrattati(new ArrayList<AttoTrattato>());
			setCollegamentiAttiSindacato(new ArrayList<CollegamentoAttiSindacato>());
			setConsultazioniAtti(new ArrayList<Consultazione>());
			setAudizioni(new ArrayList<Audizione>());
		}
	}
	
	
	public Seduta findSeduta(String dataSeduta) {
		Format formatter = new SimpleDateFormat("dd/MM/yyyy");

		for (Seduta element : seduteList) {
		
			if (dataSeduta.equals(formatter.format(element.getDataSeduta()))) {
				return element;
			}
		}
		return null;
	}

	public void removeFromDateSedutaList(Date dataSeduta) {

		for (String element : dateSeduteList) {
			if (dataSeduta.equals(element)) {
				dateSeduteList.remove(element);
				break;
			}
		}

	}

	public void removeSeduta() {

		for (Seduta element : seduteList) {

			if (element.getDataSeduta().equals(sedutaToDelete)) {

				seduteList.remove(element);
				updateInserisciSedutaHandler();
				removeFromDateSedutaList(sedutaToDelete);

				if (!seduteList.isEmpty()) {
					setDataSedutaSelected(dateSeduteList.get(0));
				}

				seduteServiceManager.deleteSeduta(element.getIdSeduta());

				showSedutaDetail();
				refreshInsert();
				break;
			}
		}
	}

	public String dettaglioOdg() {

		FacesContext context = FacesContext.getCurrentInstance();
		
		UserBean userBean = ((UserBean) context.getExternalContext()
				.getSessionMap().get("userBean"));

		setSedutaSelected(findSeduta(dataSedutaSelected));
		setAttiTrattati(sedutaSelected.getAttiTrattati());
		Collections.sort(attiTrattati);
		setCollegamentiAttiSindacato(sedutaSelected.getAttiSindacato());
		Collections.sort(collegamentiAttiSindacato);
		setAudizioni(sedutaSelected.getAudizioni());
		consultazioniAtti.clear();

		for (AttoTrattato element : attiTrattati) {

			for (Consultazione consultazione : element.getAtto()
					.getConsultazioni()) {

				Format formatter = new SimpleDateFormat("dd/MM/yyyy");

				Consultazione cons = (Consultazione) consultazione.clone();
				if (cons.getDataSeduta() != null
						&& formatter.format(cons.getDataSeduta())
								.equals(formatter.format(sedutaSelected
										.getDataSeduta()))

						&& userBean.getUser().getSessionGroup().getNome()
								.equals(cons.getCommissione())) {

					cons.setNumeroAtto(element.getAtto().getNumeroAtto());
					cons.setTipoAtto(element.getAtto().getTipoAtto());
					cons.setIdAtto(element.getAtto().getId());
					consultazioniAtti.add(cons);
				}
			}

		}
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

		FacesContext context = FacesContext.getCurrentInstance();
		UserBean userBean = ((UserBean) context.getExternalContext()
				.getSessionMap().get("userBean"));

		GestioneSedute gestioneSedute = new GestioneSedute();
		Target target = new Target();
		target.setProvenienza(userBean.getUser().getSessionGroup().getNome());
		gestioneSedute.setTarget(target);

		if (getDataSeduta() != null) {

			Format formatter = new SimpleDateFormat("dd/MM/yyyy");
			
			//ABILITARE DOPO MODIFICA DI ALFRESCO
			 //Seduta seduta =  seduteServiceManager.getSeduta(userBean
			//.getUser().getSessionGroup().getNome(),formatter.format(getDataSeduta()),legislaturaCorrente);
			Seduta seduta = findSeduta(formatter.format(getDataSeduta()));

			// Inserimento
			if (seduta == null) {
				seduta = new Seduta();
				seduta.setDalleOre(getDalleOre());
				seduta.setAlleOre(getAlleOre());
				seduta.setDataSeduta(getDataSeduta());
				seduta.setNumVerbale(getNumVerbale());
				seduta.setNote(getNote());
				seduta.setLinks(Clonator.cloneList(getLinksList()));
				seduta.setLegislatura(legislaturaCorrente);
				gestioneSedute.setSeduta(seduta);
				Seduta sedutaRet = seduteServiceManager
						.salvaSeduta(gestioneSedute);
				seduta.setIdSeduta(sedutaRet.getIdSeduta());
				setSedutaSelected(seduta);
				seduteListAll.add(seduta);
				seduteList.add(seduta);
				dateSeduteList.add(formatter.format(seduta.getDataSeduta()));
				seduteListAll = seduteServiceManager.getSedute(userBean
						.getUser().getSessionGroup().getNome(),legislaturaCorrente);

				seduteList = Clonator.cloneList(seduteListAll);
				Collections.sort(seduteList);
			
				// Modifica
			} else {
				seduta.setNumVerbale(getNumVerbale());
				seduta.setNote(getNote());
				seduta.setDalleOre(getDalleOre());
				seduta.setAlleOre(getAlleOre());
				seduta.setLinks(Clonator.cloneList(getLinksList()));
				gestioneSedute.setSeduta(seduta);
				seduta = seduteServiceManager.updateSeduta(gestioneSedute);
				setSedutaSelected(seduta);
				
			}

			updateInserisciSedutaHandler();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Seduta numero "
							+ getNumVerbale() + " salvata con successo", ""));

			refreshInsert();

		} else {

			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Attenzione ! Inserire Data seduta ", ""));
		}
	}
	


	private void refreshInsert() {

		setSedutaSelected(null);
		setDataSeduta(null);
		setAlleOre(null);
		setDalleOre(null);
		setNomeLink(null);
		setNumVerbale(null);
		setNote(null);
		setDescrizioneCollegamento(null);
		setUrlLink(null);
		linksList.clear();

	}

	// Inserisci ODG***************************************

	public void fillDateSeduteMap() {
		dateSedute = new ArrayList<Date>();
		for (Seduta element : seduteList) {
			dateSedute.add(element.getDataSeduta());
		}
	}

	public void updateSedutaInserisciOdg() {
		if (!seduteList.isEmpty()) {
			Format formatter = new SimpleDateFormat("dd/MM/yyyy");
			setDataSedutaSelected(formatter.format(seduteList.get(0)
					.getDataSeduta()));
		}
		showSedutaDetail();
	}

	public void addAttoTrattato() {

		if (!attoDaTrattare.trim().equals("")) {
			if (!checkAttiTrattati(attoDaTrattare)) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Attenzione ! Atto già iscritto ", ""));

			} else {
				Atto attoDaCollegare = attoServiceManager
						.findById(attoDaTrattare);
				AttoTrattato attoTrattato = new AttoTrattato();
				attoTrattato.setAtto(attoDaCollegare);
				attoTrattato.setPrevisto(true);
				attiTrattati.add(attoTrattato);
				FacesContext context = FacesContext.getCurrentInstance();
				UserBean userBean = ((UserBean) context.getExternalContext()
						.getSessionMap().get("userBean"));

				for (Consultazione consultazione : attoTrattato.getAtto()
						.getConsultazioni()) {

					Format formatter = new SimpleDateFormat("dd/MM/yyyy");
					Consultazione cons = (Consultazione) consultazione.clone();
					if (formatter.format(sedutaSelected.getDataSeduta())
							.equals(formatter.format(cons.getDataSeduta()))

							&& userBean.getUser().getSessionGroup().getNome()
									.equals(cons.getCommissione())) {

						cons.setNumeroAtto(attoTrattato.getAtto()
								.getNumeroAtto());
						cons.setTipoAtto(attoTrattato.getAtto().getTipoAtto());
						cons.setIdAtto(attoTrattato.getAtto().getId());
						consultazioniAtti.add(cons);
					}
				}
				updateInserisciOdgHandler();
			}
		}
	}

	public void addAttoTrattato(String idAttoToAdd, String tipoAtto) {

		if (!idAttoToAdd.trim().equals("")) {
			if (!checkAttiTrattati(idAttoToAdd)) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Attenzione ! Atto già iscritto ", ""));

			} else {

				Atto attoDaCollegare = null;
				boolean collega = true;

				if ("MIS".equalsIgnoreCase(tipoAtto)) {

					
					//TODO
					//attoDaCollegare = attoServiceManager
					//		.findMISById(idAttoToAdd);
					collega = false;
					
				} else if ("EAC".equalsIgnoreCase(tipoAtto)) {

					FacesContext context = FacesContext.getCurrentInstance();
					context.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"Attenzione ! Non è possibile collegare atti di questo tipo",
									""));

					collega = false;

				} else {

					attoDaCollegare = attoServiceManager.findById(idAttoToAdd);

				}

				if (collega) {
					AttoTrattato attoTrattato = new AttoTrattato();
					attoTrattato.setAtto(attoDaCollegare);
					attoTrattato.setPrevisto(true);
					attiTrattati.add(attoTrattato);
					FacesContext context = FacesContext.getCurrentInstance();
					UserBean userBean = ((UserBean) context
							.getExternalContext().getSessionMap()
							.get("userBean"));

					for (Consultazione consultazione : attoTrattato.getAtto()
							.getConsultazioni()) {

						Format formatter = new SimpleDateFormat("dd/MM/yyyy");
						Consultazione cons = (Consultazione) consultazione
								.clone();
						if (formatter.format(sedutaSelected.getDataSeduta())
								.equals(formatter.format(cons.getDataSeduta()))

								&& userBean.getUser().getSessionGroup()
										.getNome()
										.equals(cons.getCommissione())) {

							cons.setNumeroAtto(attoTrattato.getAtto()
									.getNumeroAtto());
							cons.setTipoAtto(attoTrattato.getAtto()
									.getTipoAtto());
							cons.setIdAtto(attoTrattato.getAtto().getId());
							consultazioniAtti.add(cons);
						}
					}
					updateInserisciOdgHandler();
				}
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

	public void addAudizione() {

		if (!soggettoPartecipante.trim().equals("")) {
			if (!checkAudizioni(soggettoPartecipante)) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Attenzione ! Soggetto già inserito ", ""));

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

		if (!"".equals(idAttoSindacato)) {
			if (!checkCollegamentiAttiSindacati()) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Attenzione ! Atto già collegato ", ""));

			} else {

				for (CollegamentoAttiSindacato collegamento : attiSindacato) {

					if (collegamento.getIdAtto().equals(idAttoSindacato)) {

						collegamento.setDescrizione(descrizioneAttoSindacato);
						collegamentiAttiSindacato.add(collegamento);
						break;
					}

				}
				updateInserisciOdgHandler();
			}
		}
	}

	public void removeCollegamentoAttoSindacato() {

		for (CollegamentoAttiSindacato element : collegamentiAttiSindacato) {

			if (element.getIdAtto().equals(attoSindacatoToDelete)) {

				collegamentiAttiSindacato.remove(element);

				break;
			}
		}
	}

	private boolean checkCollegamentiAttiSindacati() {

		for (CollegamentoAttiSindacato element : collegamentiAttiSindacato) {

			if (element.getNumeroAtto().equals(idAttoSindacato)) {

				return false;
			}
		}
		return true;
	}

	public void handleAttoSindacatoChange() {

		getNumeriAttoSindacato().clear();

		for (CollegamentoAttiSindacato collegamento : attiSindacato) {

			if (collegamento.getTipoAtto().equals(tipoAttoSindacato)) {

				getNumeriAttoSindacato().add(collegamento);

			}

		}

	}

	public void salvaInserisciOdg() {

		if (sedutaSelected != null) {

			sedutaSelected.setAttiSindacato(Clonator
					.cloneList(getOrderedAttiSindacatoTrattati()));

			Collections.sort(collegamentiAttiSindacato);

			sedutaSelected.setAttiTrattati(Clonator
					.cloneList(getOrderedAttiTrattati()));

			Collections.sort(attiTrattati);

			sedutaSelected.setAudizioni(Clonator.cloneList(getAudizioni()));

			sedutaSelected.setConsultazioniAtti(Clonator
					.cloneList(getConsultazioniAtti()));
		}

		FacesContext context = FacesContext.getCurrentInstance();
		UserBean userBean = ((UserBean) context.getExternalContext()
				.getSessionMap().get("userBean"));

		seduteServiceManager.salvaOdg(sedutaSelected);

		userBean.getUser().getSessionGroup()
				.setSedute(Clonator.cloneList(getSeduteList()));

		setStatoCommitInserisciOdg(CRLMessage.COMMIT_DONE);
		context.addMessage(null, new FacesMessage("ODG salvato con successo",
				""));
	}

	private List<AttoTrattato> getOrderedAttiTrattati() {

		if (getAttiTrattatiorder() != null
				&& !getAttiTrattatiorder().equals("")) {
			// Numeri atto ordinati
			String[] attiOrd = getAttiTrattatiorder().split("_");

			for (int i = 0; i < attiOrd.length; i++) {

				String numeroAtto = attiOrd[i];

				for (AttoTrattato attoTrattato : getAttiTrattati()) {

					if (numeroAtto.equals(attoTrattato.getAtto()
							.getNumeroAtto())) {

						if (i < 10) {
							attoTrattato.setNumeroOrdinamento("0" + i);
						} else {
							attoTrattato.setNumeroOrdinamento("" + i);
						}
					}
				}
			}
		} else {

			int i = 0;
			for (AttoTrattato attoTrattato : getAttiTrattati()) {
				if (i < 10) {
					attoTrattato.setNumeroOrdinamento("0" + i);
				} else {
					attoTrattato.setNumeroOrdinamento("" + i);
				}
				i++;
			}

		}

		Collections.sort(attiTrattati);
		return attiTrattati;

	}

	private List<CollegamentoAttiSindacato> getOrderedAttiSindacatoTrattati() {

		if (getAttiSindacatoTrattatiorder() != null
				&& !getAttiSindacatoTrattatiorder().equals("")) {
			// Numeri atto ordinati
			String[] attiOrd = getAttiSindacatoTrattatiorder().split("_");

			for (int i = 0; i < attiOrd.length; i++) {

				String numeroAtto = attiOrd[i].trim();

				for (CollegamentoAttiSindacato attoTrattato : getCollegamentiAttiSindacato()) {

					if (numeroAtto.equals(attoTrattato.getNumeroAtto())) {

						if (i < 10) {
							attoTrattato.setNumeroOrdinamento("0" + i);
						} else {
							attoTrattato.setNumeroOrdinamento("" + i);
						}
					}
				}
			}
		} else {

			int i = 0;
			for (CollegamentoAttiSindacato attoTrattato : getCollegamentiAttiSindacato()) {
				if (i < 10) {
					attoTrattato.setNumeroOrdinamento("0" + i);
				} else {
					attoTrattato.setNumeroOrdinamento("" + i);
				}
				i++;
			}

		}

		Collections.sort(collegamentiAttiSindacato);
		return collegamentiAttiSindacato;

	}

	public StreamedContent getFile() {

		FacesContext context = FacesContext.getCurrentInstance();

		if (sedutaSelected != null) {

			UserBean userBean = ((UserBean) context.getExternalContext()
					.getSessionMap().get("userBean"));

			String tipoTemplate = "";
			String gruppo = userBean.getUser().getSessionGroup().getNome();
			String nomeFile = "";
			Format formatter = new SimpleDateFormat("dd_MM_yyyy");

			if (GruppoUtente.AULA.equals(gruppo)) {

				tipoTemplate = "crlodg:odgGenericoAulaDocument";
				nomeFile = "ODG_Aula_"
						+ formatter.format(sedutaSelected.getDataSeduta());

			} else {

				tipoTemplate = "crlodg:odgGenericoCommissioniDocument";
				nomeFile = "ODG_" + gruppo + "_"
						+ formatter.format(sedutaSelected.getDataSeduta());
			}

			InputStream stream = seduteServiceManager.getODGFile(tipoTemplate,
					sedutaSelected.getIdSeduta(), gruppo);
			StreamedContent file = new DefaultStreamedContent(stream,
					"document", nomeFile + ".docx");
			return file;

		} else {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Attenzione ! Selezionare una Seduta ", ""));

			return null;
		}

	}

	public void uploadODG(FileUploadEvent event) {
		if (sedutaSelected != null) {
			Allegato allegatoRet = new Allegato();
			allegatoRet.setNome(event.getFile().getFileName());
			allegatoRet.setPubblico(currentFilePubblico);

			try {

				Allegato allegatoAlf = seduteServiceManager.uploadOgg(
						sedutaSelected, event.getFile().getInputstream(),
						allegatoRet);

				allegatoRet.setId(allegatoAlf.getId());
				allegatoRet.setMimetype(allegatoAlf.getMimetype());
			} catch (IOException e) {
				e.printStackTrace();
			}

			sedutaSelected.getOdgList().add(allegatoRet);
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Attenzione ! Selezionare una Seduta ", ""));
		}
	}

	public void uploadVerbale(FileUploadEvent event) {
		if (sedutaSelected != null) {

			Allegato allegatoRet = new Allegato();
			allegatoRet.setNome(event.getFile().getFileName());
			allegatoRet.setPubblico(currentFilePubblico);

			try {

				Allegato allegatoAlf = seduteServiceManager.uploadVerbale(
						sedutaSelected, event.getFile().getInputstream(),
						allegatoRet);

				allegatoRet.setId(allegatoAlf.getId());
				allegatoRet.setMimetype(allegatoAlf.getMimetype());
			} catch (IOException e) {
				e.printStackTrace();
			}

			sedutaSelected.getVerbaliList().add(allegatoRet);
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Attenzione ! Selezionare una Seduta ", ""));
		}

	}

	public void removeVerbale() {
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		for (Allegato element : sedutaSelected.getVerbaliList()) {

			if (element.getId().equals(verbaleToDelete)) {

				attoRecordServiceManager.deleteFile(element.getId());
				sedutaSelected.getVerbaliList().remove(element);
				break;
			}
		}
	}

	public void removeTestoODG() {
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		for (Allegato element : sedutaSelected.getOdgList()) {

			if (element.getId().equals(odgToDelete)) {

				attoRecordServiceManager.deleteFile(element.getId());
				sedutaSelected.getOdgList().remove(element);
				break;
			}
		}

	}

	public void setFile(StreamedContent file) {
		this.file = file;
	}

	// Getters & Setters***********************************
	
	public String getLegislatura() {
		return legislatura;
	}

	public void setLegislatura(String legislatura) {
		this.legislatura = legislatura;
	}

	public List<String> getLegislature() {
		return legislature;
	}

	public void setLegislature(List<String> legislature) {
		this.legislature = legislature;
	}

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

	public String getDataSedutaSelected() {
		return dataSedutaSelected;
	}

	public void setDataSedutaSelected(String dataSedutaSelected) {
		this.dataSedutaSelected = dataSedutaSelected;
	}

	public Seduta getSedutaSelected() {
		return sedutaSelected;
	}

	public void setSedutaSelected(Seduta sedutaSelected) {
		this.sedutaSelected = sedutaSelected;
	}

	public List<Date> getDateSedute() {
		return dateSedute;
	}

	public void setDateSedute(List<Date> dateSedute) {
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

	public List<AttoTrattato> getAttiTrattati() {
		return attiTrattati;
	}

	public void setAttiTrattati(List<AttoTrattato> attiTrattati) {
		this.attiTrattati = attiTrattati;
	}

	public List<Consultazione> getConsultazioniAtti() {
		return consultazioniAtti;
	}

	public void setConsultazioniAtti(List<Consultazione> consultazioniAtti) {
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

	public SeduteServiceManager getSeduteServiceManager() {
		return seduteServiceManager;
	}

	public void setSeduteServiceManager(
			SeduteServiceManager seduteServiceManager) {
		this.seduteServiceManager = seduteServiceManager;
	}

	public List<String> getDateSeduteList() {
		return dateSeduteList;
	}

	public void setDateSeduteList(List<String> dateSeduteList) {
		this.dateSeduteList = dateSeduteList;
	}

	public void setDateSeduteList() {

		Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		for (Seduta seduta : seduteList) {

			dateSeduteList.add(formatter.format(seduta.getDataSeduta()));
		}
	}

	public String getLegislaturaCorrente() {
		return legislaturaCorrente;
	}

	public void setLegislaturaCorrente(String legislaturaCorrente) {
		this.legislaturaCorrente = legislaturaCorrente;
	}

	public String getAttoDaTrattare() {
		return attoDaTrattare;
	}

	public void setAttoDaTrattare(String attoDaTrattare) {
		this.attoDaTrattare = attoDaTrattare;
	}

	public List<Seduta> getSeduteListAll() {
		return seduteListAll;
	}

	public void setSeduteListAll(List<Seduta> seduteListAll) {
		this.seduteListAll = seduteListAll;
	}

	public String getAttiTrattatiorder() {
		return attiTrattatiorder;
	}

	public void setAttiTrattatiorder(String attiTrattatiorder) {
		this.attiTrattatiorder = attiTrattatiorder;
	}

	public Date getDalleOre() {
		return dalleOre;
	}

	public void setDalleOre(Date dalleOre) {
		this.dalleOre = dalleOre;
	}

	public Date getAlleOre() {
		return alleOre;
	}

	public void setAlleOre(Date alleOre) {
		this.alleOre = alleOre;
	}

	public List<String> getTipiAttoSindacato() {
		return tipiAttoSindacato;
	}

	public void setTipiAttoSindacato(List<String> tipiAttoSindacato) {
		this.tipiAttoSindacato = tipiAttoSindacato;
	}

	public List<CollegamentoAttiSindacato> getNumeriAttoSindacato() {
		return numeriAttoSindacato;
	}

	public void setNumeriAttoSindacato(
			List<CollegamentoAttiSindacato> numeriAttoSindacato) {
		this.numeriAttoSindacato = numeriAttoSindacato;
	}

	public List<CollegamentoAttiSindacato> getCollegamentiAttiSindacato() {
		return collegamentiAttiSindacato;
	}

	public void setCollegamentiAttiSindacato(
			List<CollegamentoAttiSindacato> collegamentiAttiSindacato) {
		this.collegamentiAttiSindacato = collegamentiAttiSindacato;
	}

	public String getIdAttoSindacato() {
		return idAttoSindacato;
	}

	public void setIdAttoSindacato(String idAttoSindacato) {
		this.idAttoSindacato = idAttoSindacato;
	}

	public String getDescrizioneAttoSindacato() {
		return descrizioneAttoSindacato;
	}

	public void setDescrizioneAttoSindacato(String descrizioneAttoSindacato) {
		this.descrizioneAttoSindacato = descrizioneAttoSindacato;
	}

	public String getAttiSindacatoTrattatiorder() {
		return attiSindacatoTrattatiorder;
	}

	public void setAttiSindacatoTrattatiorder(String attiSindacatoTrattatiorder) {
		this.attiSindacatoTrattatiorder = attiSindacatoTrattatiorder;
	}

	public List<Allegato> getOdgList() {
		return odgList;
	}

	public void setOdgList(List<Allegato> odgList) {
		this.odgList = odgList;
	}

	public List<Allegato> getVerbaliList() {
		return verbaliList;
	}

	public void setVerbaliList(List<Allegato> verbaliList) {
		this.verbaliList = verbaliList;
	}

	public String getOdgToDelete() {
		return odgToDelete;
	}

	public void setOdgToDelete(String odgToDelete) {
		this.odgToDelete = odgToDelete;
	}

	public String getVerbaleToDelete() {
		return verbaleToDelete;
	}

	public void setVerbaleToDelete(String verbaleToDelete) {
		this.verbaleToDelete = verbaleToDelete;
	}

	public boolean isCurrentFilePubblico() {
		return currentFilePubblico;
	}

	public void setCurrentFilePubblico(boolean currentFilePubblico) {
		this.currentFilePubblico = currentFilePubblico;
	}

	public AttoRecordServiceManager getAttoRecordServiceManager() {
		return attoRecordServiceManager;
	}

	public void setAttoRecordServiceManager(
			AttoRecordServiceManager attoRecordServiceManager) {
		this.attoRecordServiceManager = attoRecordServiceManager;
	}
	
	public LegislaturaServiceManager getLegislaturaServiceManager() {
		return legislaturaServiceManager;
	}

	public void setLegislaturaServiceManager(
			LegislaturaServiceManager legislaturaServiceManager) {
		this.legislaturaServiceManager = legislaturaServiceManager;
	}

	public boolean isDisableModifiche() {
		return disableModifiche;
	}

	public void setDisableModifiche(boolean disableModifiche) {
		this.disableModifiche = disableModifiche;
	}
	
	
}
