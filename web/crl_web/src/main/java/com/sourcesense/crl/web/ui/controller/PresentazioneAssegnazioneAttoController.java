package com.sourcesense.crl.web.ui.controller;

import java.util.ArrayList;
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

import com.sourcesense.crl.business.model.Firmatario;
import com.sourcesense.crl.business.model.Personale;
import com.sourcesense.crl.business.model.Relatore;
import com.sourcesense.crl.business.service.PersonaleServiceManager;
import com.sourcesense.crl.web.ui.beans.AttoBean;
import com.sourcesense.crl.web.ui.beans.UserBean;

@ManagedBean(name = "presentazioneAssegnazioneAttoController")
@ViewScoped
public class PresentazioneAssegnazioneAttoController {

	@ManagedProperty(value = "#{personaleServiceManager}")
	private PersonaleServiceManager personaleServiceManager;

	private List<Firmatario> firmatariList = new ArrayList<Firmatario>();
	private Map<String, String> firmatari = new HashMap<String, String>();
	private String nomeFirmatario;

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

	public void ritiraPerMancanzaFirmatari() {

		// TODO Service logic

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
				firmatariList.add(firmatario);
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

	public void salva() {

		// TODO Service logic
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));
		// attoBean.merge();

	}

	public void uploadTestoAtto(FileUploadEvent event) {

		// TODO Service logic
		System.out.println("File ===" + event.getFile().getFileName());

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

}
