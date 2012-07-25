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
import com.sourcesense.crl.business.model.Firmatario;
import com.sourcesense.crl.business.model.GruppoConsiliare;
import com.sourcesense.crl.business.model.Personale;
import com.sourcesense.crl.business.model.Relatore;
import com.sourcesense.crl.business.service.AttoServiceManager;
import com.sourcesense.crl.business.service.PersonaleServiceManager;
import com.sourcesense.crl.web.ui.beans.AttoBean;
import com.sourcesense.crl.web.ui.beans.UserBean;

@ManagedBean(name = "presentazioneAssegnazioneAttoController")
@ViewScoped
public class PresentazioneAssegnazioneAttoController {

	@ManagedProperty(value = "#{personaleServiceManager}")
	private PersonaleServiceManager personaleServiceManager;
	
	@ManagedProperty(value = "#{attoServiceManager}")
	private AttoServiceManager attoServiceManager;
	

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

	@PostConstruct
	protected void init() {

		setFirmatari(personaleServiceManager.findAllFirmatario());

	}

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
			
			try{
				allegatoRet = attoServiceManager.uploadFile(((AttoBean) FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().get("attoBean")).getAtto(), allegato, event.getFile().getInputstream()) ;
			}catch (IOException e) {
				e.printStackTrace();
			}
			
			//TODO aggiungi a bean di sessione
			testiAttoList.add(allegato);
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

				
				//TODO Alfresco delete
				testiAttoList.remove(element);
				break;
			}
		}
	}

	public void ritiraPerMancanzaFirmatari() {

		// TODO Service logic

	}

	public void salva() {

		// TODO Service logic
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));
		// TODO

		// set lists
		// attoBean.merge();

	}

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
	
	

}
