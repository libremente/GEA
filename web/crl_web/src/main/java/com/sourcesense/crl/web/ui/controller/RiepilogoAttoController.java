package com.sourcesense.crl.web.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.sourcesense.crl.business.model.Allegato;
import com.sourcesense.crl.business.model.Commissione;
import com.sourcesense.crl.business.model.Organo;
import com.sourcesense.crl.business.service.AttoServiceManager;
import com.sourcesense.crl.business.service.CommissioneServiceManager;
import com.sourcesense.crl.web.ui.beans.AttoBean;

@ManagedBean(name = "riepilogoAttoController")
@ViewScoped
public class RiepilogoAttoController {

	private List<Allegato> testiUfficiali;
	private List<Allegato> altriAllegati;
	private List<Organo> organiInterni;
	private List<Organo> altriOrgani;
	private String nomeCommissione;
	private String commissioneSelectedName;

	@ManagedProperty(value = "#{commissioneServiceManager}")
	private CommissioneServiceManager commissioneServiceManager;

	@ManagedProperty(value = "#{attoServiceManager}")
	private AttoServiceManager attoServiceManager;


	private Commissione commissioneSelected = new Commissione();

	@PostConstruct
	protected void init() {
		// setDocumentiAllegati(AllegatoServiceManager.findTestiUfficiali());
		// setAltriAllegati(AllegatoServiceManager.findAltriAllegati());
		// setOrganiInterni(OrganoServiceManager.findOrganiInterni());
		// setAltriOrgani(OrganoServiceManager.findAltriOrgani());

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));


		// TODO riempire liste commissioni attoBean =>
		// attoBean.getAtto().setCommissioni(commissioneServiceManager.findCommissioniByAtto(attoBean.getId()));
		//
		if(!attoBean.getAtto().getCommissioni().isEmpty()) {
			commissioneSelected = attoBean.getAtto().getCommissioni().get(0);
		}
		// System.out.println("Nome == " + commissioneSelected.getNome());

	}

	public void showCommissioneDetail() {

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		for (Commissione commissioneRec : attoBean.getAtto().getCommissioni()) {

			if(commissioneRec.getDescrizione().equals(nomeCommissione)){

				commissioneSelected =	commissioneRec;
				break;
			}

		}

	}

	public List<Allegato> getTestiUfficiali() {
		return testiUfficiali;
	}

	public void setTestiUfficiali(List<Allegato> testiUfficiali) {
		this.testiUfficiali = testiUfficiali;
	}

	public List<Allegato> getAltriAllegati() {
		return altriAllegati;
	}

	public void setAltriAllegati(List<Allegato> altriAllegati) {
		this.altriAllegati = altriAllegati;
	}

	public List<Organo> getOrganiInterni() {
		return organiInterni;
	}

	public void setOrganiInterni(List<Organo> organiInterni) {
		this.organiInterni = organiInterni;
	}

	public List<Organo> getAltriOrgani() {
		return altriOrgani;
	}

	public void setAltriOrgani(List<Organo> altriOrgani) {
		this.altriOrgani = altriOrgani;
	}

	public String getNomeCommissione() {
		return nomeCommissione;
	}

	public void setNomeCommissione(String nomeCommissione) {
		this.nomeCommissione = nomeCommissione;
	}

	public CommissioneServiceManager getCommissioneServiceManager() {
		return commissioneServiceManager;
	}

	public void setCommissioneServiceManager(
			CommissioneServiceManager commissioneServiceManager) {
		this.commissioneServiceManager = commissioneServiceManager;
	}

	public String getCommissioneSelectedName() {
		return this.commissioneSelected.getClass().getSimpleName();
	}

	public void setCommissioneSelectedName(String commissioneSelectedName) {
		this.commissioneSelectedName = commissioneSelectedName;
	}

	public Commissione getCommissioneSelected() {
		return commissioneSelected;
	}

	public void setCommissioneSelected(Commissione commissioneSelected) {
		this.commissioneSelected = commissioneSelected;
	}

	public AttoServiceManager getAttoServiceManager() {
		return attoServiceManager;
	}

	public void setAttoServiceManager(AttoServiceManager attoServiceManager) {
		this.attoServiceManager = attoServiceManager;
	}



}
