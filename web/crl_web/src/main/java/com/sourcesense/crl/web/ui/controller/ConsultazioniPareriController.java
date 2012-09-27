package com.sourcesense.crl.web.ui.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

import com.sourcesense.crl.business.model.Allegato;
import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.model.Consultazione;
import com.sourcesense.crl.business.model.Parere;
import com.sourcesense.crl.business.model.SoggettoInvitato;
import com.sourcesense.crl.business.service.AttoServiceManager;
import com.sourcesense.crl.util.CRLMessage;
import com.sourcesense.crl.web.ui.beans.AttoBean;

@ManagedBean(name = "consultazioniPareriController")
@ViewScoped
public class ConsultazioniPareriController {

	@ManagedProperty(value = "#{attoServiceManager}")
	private AttoServiceManager attoServiceManager;

	private Atto atto = new Atto();

	private List<Parere> pareriList = new ArrayList<Parere>();

	private Parere parereSelected = new Parere();
	private String descrizioneParereSelected;

	private Date dataRicezioneParere;
	private Date dataRicezioneOrgano;
	private String esito;
	private String noteParere;

	private List<Allegato> allegatiParereList = new ArrayList<Allegato>();
	private String allegatoParereToDelete;
	private boolean currentFilePubblico;


	private List<Consultazione> consultazioniList = new ArrayList<Consultazione>();
	private String soggettoConsultato;
	private String consultazioneToDelete;
	private String allegatoConsultazioneToDelete;

	private Consultazione consultazioneSelected;
	private String descrizioneConsultazioneSelected;

	private Date dataSedutaConsultazione;
	private boolean prevista;
	private boolean discussa;
	private Date dataConsultazione;
	private String noteConsultazione;

	private List<SoggettoInvitato> soggettiInvitatiList = new ArrayList<SoggettoInvitato>();
	private String nomeSoggettoInvitato;
	private boolean intervenuto;
	private String soggettoInvitatoToDelete;

	private List<Allegato> allegatiConsultazioneList = new ArrayList<Allegato>();



	private String statoCommitPareri = CRLMessage.COMMIT_DONE;
	private String statoCommitConsultazioni = CRLMessage.COMMIT_DONE;


	@PostConstruct
	protected void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));
		setAtto((Atto) attoBean.getAtto().clone());	

		setPareriList(new ArrayList<Parere>(atto.getPareri()));

		if(!pareriList.isEmpty()) {
			setDescrizioneParereSelected(pareriList.get(0).getDescrizione());
			showParereDetail();
		}

		setConsultazioniList(new ArrayList<Consultazione>(atto.getConsultazioni()));

		if(!consultazioniList.isEmpty()) {
			setDescrizioneConsultazioneSelected(consultazioniList.get(0).getDescrizione());
			showConsultazioneDetail();
		}
	}

	public void updatePareriHandler() {
		setStatoCommitPareri(CRLMessage.COMMIT_UNDONE);
	}

	public void updateConsultazioniHandler() {
		setStatoCommitConsultazioni(CRLMessage.COMMIT_UNDONE);
	}


	public void changeTabHandler() {

		if (statoCommitPareri.equals(CRLMessage.COMMIT_UNDONE)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Attenzione ! Le modifiche ai Pareri non sono state salvate ",
							""));
		}

		if (statoCommitConsultazioni.equals(CRLMessage.COMMIT_UNDONE)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Attenzione ! Le modifiche alle Consultazioni non sono state salvate ",
							""));
		}
	}

	// Pareri********************************************************

	public void showParereDetail() {
		setParereSelected(findParere(descrizioneParereSelected));

		if(parereSelected!=null) {
			setDataRicezioneParere(parereSelected.getDataRicezioneParere());
			setDataRicezioneOrgano(parereSelected.getDataRicezioneOrgano());
			setEsito(parereSelected.getEsito());
			setNoteParere(parereSelected.getNote());
			setAllegatiParereList(parereSelected.getAllegati());
		}
		else {
			setDataRicezioneParere(null);
			setDataRicezioneOrgano(null);
			setEsito("");
			setNoteParere("");
			setAllegatiParereList(new ArrayList<Allegato>());
		}
	}

	private Parere findParere(String descrizione) {
		for(Parere element : pareriList) {
			if(element.getDescrizione().equals(descrizione)) {
				return element;
			}
		}
		return null;
	}

	
	public void uploadAllegatoParere(FileUploadEvent event) {

		// TODO Service logic
		String fileName = event.getFile().getFileName();

		if (!checkAllegatoParere(fileName)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Attenzione ! Il file "
							+ fileName + " è già stato allegato ", ""));
		} else {

			// TODO Alfresco upload
			Allegato allegatoParereRet = null;

			try {
				allegatoParereRet = attoServiceManager
						.uploadAllegatoConultaioneConsultazioniPareri(
								((AttoBean) FacesContext.getCurrentInstance()
										.getExternalContext().getSessionMap()
										.get("attoBean")).getAtto(), event
										.getFile().getInputstream(), event
										.getFile().getFileName());
			} catch (IOException e) {
				e.printStackTrace();
			}

			// TODO aggiungi a bean di sessione
			FacesContext context = FacesContext.getCurrentInstance();
			AttoBean attoBean = ((AttoBean) context.getExternalContext()
					.getSessionMap().get("attoBean"));

			attoBean.getAtto().getAllegatiNotePresentazioneAssegnazione()
			.add(allegatoParereRet);

			allegatiParereList.add(allegatoParereRet);
		}
	}

	private boolean checkAllegatoParere(String fileName) {

		for (Allegato element : allegatiParereList) {

			if (element.getDescrizione().equals(fileName)) {

				return false;
			}

		}

		return true;
	}

	public void removeAllegatoParere() {

		for (Allegato element : allegatiParereList) {

			if (element.getId().equals(allegatoParereToDelete)) {

				// TODO Alfresco delete
				allegatiParereList.remove(element);
				break;
			}
		}
	}


	public void salvaParere() {
		parereSelected.setDataRicezioneOrgano(getDataRicezioneOrgano());
		parereSelected.setDataRicezioneParere(getDataRicezioneParere());
		parereSelected.setEsito(getEsito());
		parereSelected.setNote(getNoteParere());
		//parereSelected.setAllegati

		atto.setPareri(pareriList);

		attoServiceManager.salvaPareri(atto);

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		attoBean.getAtto().setPareri(pareriList);

		setStatoCommitPareri(CRLMessage.COMMIT_DONE);
		context.addMessage(null, new FacesMessage("Pareri salvati con successo", ""));
	}


	// Consultazioni****************************************************

	public void showConsultazioneDetail() {
		setConsultazioneSelected(findConsultazione());

		if(consultazioneSelected!=null) {
			setDataSedutaConsultazione(consultazioneSelected.getDataSeduta());
			setPrevista(consultazioneSelected.isPrevista());
			setDiscussa(consultazioneSelected.isDiscussa());
			setNoteConsultazione(consultazioneSelected.getNote());
			setSoggettiInvitatiList(consultazioneSelected.getSoggettiInvitati());
			setAllegatiConsultazioneList(consultazioneSelected.getAllegati());
		}

		else {
			setDataSedutaConsultazione(null);
			setPrevista(false);
			setDiscussa(false);
			setNoteConsultazione("");
			setSoggettiInvitatiList(new ArrayList<SoggettoInvitato>());
			setAllegatiConsultazioneList(new ArrayList<Allegato>());
		}
	}

	private Consultazione findConsultazione() {
		if(descrizioneConsultazioneSelected!=null) {
			for(Consultazione element : consultazioniList) {
				if(element.getDescrizione().equals(descrizioneConsultazioneSelected)) {
					return element;
				}
			}
		}
		return null;
	}

	public void addConsultazione() {

		if (soggettoConsultato != null
				&& !soggettoConsultato.trim().equals("")) {
			if (!checkConsultazioni()) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Attenzione ! Soggetto consultato "
								+ soggettoConsultato + " già presente ",
						""));

			} else {
				Consultazione consultazione = new Consultazione();
				consultazione.setDescrizione(soggettoConsultato);
				consultazione.setDataConsultazione(dataConsultazione);
				consultazioniList.add(consultazione);

				setDescrizioneConsultazioneSelected(soggettoConsultato);
				showConsultazioneDetail();
				updateConsultazioniHandler();
			}
		}
	}

	public void removeConsultazione() {

		for (Consultazione element : consultazioniList) {

			if (element.getDescrizione().equals(consultazioneToDelete)) {

				consultazioniList.remove(element);

				if(!consultazioniList.isEmpty()) {
					setDescrizioneConsultazioneSelected(consultazioniList.get(0).getDescrizione());					
				}
				showConsultazioneDetail();

				updateConsultazioniHandler();
				break;
			}
		}
	}

	private boolean checkConsultazioni() {

		for (Consultazione element : consultazioniList) {

			if (element.getDescrizione().equals(soggettoConsultato)) {

				return false;
			}

		}

		return true;
	}

	public void addSoggettoInvitato() {

		if (nomeSoggettoInvitato != null
				&& !nomeSoggettoInvitato.trim().equals("")) {
			if (!checkSoggettiInvitati()) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Attenzione ! Soggetto invitato "
								+ nomeSoggettoInvitato + " già presente ",
						""));

			} else {
				SoggettoInvitato soggetto = new SoggettoInvitato();
				soggetto.setDescrizione(nomeSoggettoInvitato);
				soggetto.setIntervenuto(intervenuto);
				soggettiInvitatiList.add(soggetto);

				updateConsultazioniHandler();
			}
		}
	}

	public void removeSoggettoInvitato() {

		for (SoggettoInvitato element : soggettiInvitatiList) {

			if (element.getDescrizione().equals(soggettoInvitatoToDelete)) {

				soggettiInvitatiList.remove(element);
				updateConsultazioniHandler();
				break;
			}
		}
	}

	private boolean checkSoggettiInvitati() {

		for (SoggettoInvitato element : soggettiInvitatiList) {

			if (element.getDescrizione().equals(nomeSoggettoInvitato)) {

				return false;
			}

		}

		return true;
	}


	public void salvaConsultazione() {
		consultazioneSelected.setDataSeduta(getDataSedutaConsultazione());
		consultazioneSelected.setPrevista(isPrevista());
		consultazioneSelected.setDiscussa(isDiscussa());
		consultazioneSelected.setNote(getNoteConsultazione());

		atto.setConsultazioni(getConsultazioniList());
		attoServiceManager.salvaAmmissibilitaPresentazione(atto);

		//TODO: alfresco service

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		attoBean.getAtto().setConsultazioni(getConsultazioniList());

		setStatoCommitConsultazioni(CRLMessage.COMMIT_DONE);
		context.addMessage(null, new FacesMessage("Consultazioni salvati con successo", ""));
	}
	
	
		public void uploadAllegatoConsultazione(FileUploadEvent event) {

			// TODO Service logic
			String fileName = event.getFile().getFileName();

			if (!checkAllegatoConsultazione(fileName)) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Attenzione ! Il file "
								+ fileName + " è già stato allegato ", ""));
			} else {

				// TODO Alfresco upload
				Allegato allegatoConsultazioneRet = null;

				try {
					allegatoConsultazioneRet = attoServiceManager
							.uploadAllegatoParereConsultazioniPareri(
									((AttoBean) FacesContext.getCurrentInstance()
											.getExternalContext().getSessionMap()
											.get("attoBean")).getAtto(), event
											.getFile().getInputstream(), event
											.getFile().getFileName());
				} catch (IOException e) {
					e.printStackTrace();
				}

				// TODO aggiungi a bean di sessione
				FacesContext context = FacesContext.getCurrentInstance();
				AttoBean attoBean = ((AttoBean) context.getExternalContext()
						.getSessionMap().get("attoBean"));

				attoBean.getAtto().getAllegatiNotePresentazioneAssegnazione()
				.add(allegatoConsultazioneRet);

				allegatiConsultazioneList.add(allegatoConsultazioneRet);
			}
		}

		private boolean checkAllegatoConsultazione(String fileName) {

			for (Allegato element : allegatiConsultazioneList) {

				if (element.getDescrizione().equals(fileName)) {

					return false;
				}

			}

			return true;
		}

		public void removeAllegatoConsultazione() {

			for (Allegato element : allegatiConsultazioneList) {

				if (element.getId().equals(allegatoConsultazioneToDelete)) {

					// TODO Alfresco delete
					allegatiConsultazioneList.remove(element);
					break;
				}
			}
		}


	// Getters & Setters**********************************************

	public Atto getAtto() {
		return atto;
	}
	public void setAtto(Atto atto) {
		this.atto = atto;
	}
	public List<Parere> getPareriList() {
		return pareriList;
	}
	public void setPareriList(List<Parere> pareriList) {
		this.pareriList = pareriList;
	}
	public Parere getParereSelected() {
		return parereSelected;
	}
	public void setParereSelected(Parere parereSelected) {
		this.parereSelected = parereSelected;
	}
	public Date getDataRicezioneParere() {
		return dataRicezioneParere;
	}
	public void setDataRicezioneParere(Date dataRicezioneParere) {
		this.dataRicezioneParere = dataRicezioneParere;
	}
	public Date getDataRicezioneOrgano() {
		return dataRicezioneOrgano;
	}
	public void setDataRicezioneOrgano(Date dataRicezioneOrgano) {
		this.dataRicezioneOrgano = dataRicezioneOrgano;
	}
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}

	public AttoServiceManager getAttoServiceManager() {
		return attoServiceManager;
	}

	public void setAttoServiceManager(AttoServiceManager attoServiceManager) {
		this.attoServiceManager = attoServiceManager;
	}

	public String getNoteParere() {
		return noteParere;
	}

	public void setNoteParere(String noteParere) {
		this.noteParere = noteParere;
	}

	public String getDescrizioneParereSelected() {
		return descrizioneParereSelected;
	}

	public void setDescrizioneParereSelected(String descrizioneParereSelected) {
		this.descrizioneParereSelected = descrizioneParereSelected;
	}

	public List<Allegato> getAllegatiParereList() {
		return allegatiParereList;
	}

	public void setAllegatiParereList(List<Allegato> allegatiParereList) {
		this.allegatiParereList = allegatiParereList;
	}

	public String getAllegatoParereToDelete() {
		return allegatoParereToDelete;
	}

	public void setAllegatoParereToDelete(String allegatoParereToDelete) {
		this.allegatoParereToDelete = allegatoParereToDelete;
	}

	public String getStatoCommitPareri() {
		return statoCommitPareri;
	}

	public void setStatoCommitPareri(String statoCommitPareri) {
		this.statoCommitPareri = statoCommitPareri;
	}

	public String getStatoCommitConsultazioni() {
		return statoCommitConsultazioni;
	}

	public void setStatoCommitConsultazioni(String statoCommitConsultazioni) {
		this.statoCommitConsultazioni = statoCommitConsultazioni;
	}

	public List<Consultazione> getConsultazioniList() {
		return consultazioniList;
	}

	public void setConsultazioniList(List<Consultazione> consultazioniList) {
		this.consultazioniList = consultazioniList;
	}

	public Date getDataSedutaConsultazione() {
		return dataSedutaConsultazione;
	}

	public void setDataSedutaConsultazione(Date dataSedutaConsultazione) {
		this.dataSedutaConsultazione = dataSedutaConsultazione;
	}

	public boolean isPrevista() {
		return prevista;
	}

	public void setPrevista(boolean prevista) {
		this.prevista = prevista;
	}

	public boolean isDiscussa() {
		return discussa;
	}

	public void setDiscussa(boolean discussa) {
		this.discussa = discussa;
	}

	public Date getDataConsultazione() {
		return dataConsultazione;
	}

	public void setDataConsultazione(Date dataConsultazione) {
		this.dataConsultazione = dataConsultazione;
	}

	public String getNoteConsultazione() {
		return noteConsultazione;
	}

	public void setNoteConsultazione(String noteConsultazione) {
		this.noteConsultazione = noteConsultazione;
	}

	public List<SoggettoInvitato> getSoggettiInvitatiList() {
		return soggettiInvitatiList;
	}

	public void setSoggettiInvitatiList(
			List<SoggettoInvitato> soggettiInvitatiList) {
		this.soggettiInvitatiList = soggettiInvitatiList;
	}

	public List<Allegato> getAllegatiConsultazioneList() {
		return allegatiConsultazioneList;
	}

	public void setAllegatiConsultazioneList(
			List<Allegato> allegatiConsultazioneList) {
		this.allegatiConsultazioneList = allegatiConsultazioneList;
	}

	public Consultazione getConsultazioneSelected() {
		return consultazioneSelected;
	}

	public void setConsultazioneSelected(Consultazione consultazioneSelected) {
		this.consultazioneSelected = consultazioneSelected;
	}

	public String getDescrizioneConsultazioneSelected() {
		return descrizioneConsultazioneSelected;
	}

	public void setDescrizioneConsultazioneSelected(
			String descrizioneConsultazioneSelected) {
		this.descrizioneConsultazioneSelected = descrizioneConsultazioneSelected;
	}

	public String getSoggettoConsultato() {
		return soggettoConsultato;
	}

	public void setSoggettoConsultato(String soggettoConsultato) {
		this.soggettoConsultato = soggettoConsultato;
	}

	public String getConsultazioneToDelete() {
		return consultazioneToDelete;
	}

	public void setConsultazioneToDelete(String consultazioneToDelete) {
		this.consultazioneToDelete = consultazioneToDelete;
	}

	public String getNomeSoggettoInvitato() {
		return nomeSoggettoInvitato;
	}

	public void setNomeSoggettoInvitato(String nomeSoggettoInvitato) {
		this.nomeSoggettoInvitato = nomeSoggettoInvitato;
	}

	public String getSoggettoInvitatoToDelete() {
		return soggettoInvitatoToDelete;
	}

	public void setSoggettoInvitatoToDelete(String soggettoInvitatoToDelete) {
		this.soggettoInvitatoToDelete = soggettoInvitatoToDelete;
	}

	public boolean isIntervenuto() {
		return intervenuto;
	}

	public void setIntervenuto(boolean intervenuto) {
		this.intervenuto = intervenuto;
	}

	public boolean isCurrentFilePubblico() {
		return currentFilePubblico;
	}

	public void setCurrentFilePubblico(boolean currentFilePubblico) {
		this.currentFilePubblico = currentFilePubblico;
	}


	public String getAllegatoConsultazioneToDelete() {
		return allegatoConsultazioneToDelete;
	}

	public void setAllegatoConsultazioneToDelete(
			String allegatoConsultazioneToDelete) {
		this.allegatoConsultazioneToDelete = allegatoConsultazioneToDelete;
	}



}
