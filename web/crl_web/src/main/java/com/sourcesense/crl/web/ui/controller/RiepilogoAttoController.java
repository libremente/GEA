package com.sourcesense.crl.web.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.sourcesense.crl.business.model.Abbinamento;
import com.sourcesense.crl.business.model.Allegato;
import com.sourcesense.crl.business.model.Commissione;
import com.sourcesense.crl.business.model.Organo;
import com.sourcesense.crl.business.model.Passaggio;
import com.sourcesense.crl.business.model.Relatore;
import com.sourcesense.crl.business.model.StatoAtto;
import com.sourcesense.crl.business.service.AttoServiceManager;
import com.sourcesense.crl.business.service.CommissioneServiceManager;
import com.sourcesense.crl.util.Clonator;
import com.sourcesense.crl.web.ui.beans.AttoBean;

@ManagedBean(name = "riepilogoAttoController")
@ViewScoped
public class RiepilogoAttoController {
	
	AttoBean attoBean;

	private List<Allegato> testiUfficiali;
	private List<Allegato> altriAllegati;
	private List<Organo> organiInterni;
	private List<Organo> altriOrgani;
	private String nomeCommissione;
	private String commissioneSelectedName;
	
	private List<Abbinamento> abbinamentiAttivi = new ArrayList<Abbinamento>();
	private List<Abbinamento> abbinamentiList = new ArrayList<Abbinamento>();
	private List<Relatore> relatoriAttivi = new ArrayList<Relatore>();
	private List<Relatore> relatoriList = new ArrayList<Relatore>();

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
		if(!attoBean.getAtto().getPassaggi().get(0).getCommissioni().isEmpty()) {
			commissioneSelected = attoBean.getAtto().getPassaggi().get(0).getCommissioni().get(0);
		}
		
		abbinamentiList = Clonator.cloneList(attoBean.getLastPassaggio().getAbbinamenti());
		relatoriList =Clonator.cloneList(commissioneSelected.getRelatori());
		filtraAbbinamenti();
		filtraRelatori();
		
		trasmissioneAdAulaAbilited();
		
		
	}
	
	

	public void showCommissioneDetail() {

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));

		for (Commissione commissioneRec : attoBean.getAtto().getPassaggi().get(0).getCommissioni()) {

			if(commissioneRec.getDescrizione().equals(nomeCommissione)){

				commissioneSelected =	commissioneRec;
				break;
			}

		}

	}
	
	public void filtraAbbinamenti() {

		for (Abbinamento element : abbinamentiList) {

			if (element.getDataDisabbinamento() == null) {

				abbinamentiAttivi.add(element);
				
			}
		}
	}
	
	public void filtraRelatori(){
		
		for (Relatore element: relatoriList) {
			
			if ( element.getDataUscita() == null ) {
				
				relatoriAttivi.add(element);
			}
		}
	}
	
	public boolean trasmissioneAdAulaAbilited() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		attoBean = (AttoBean) context
				.getApplication()
				.getExpressionFactory()
				.createValueExpression(context.getELContext(), "#{attoBean}",
						AttoBean.class).getValue(context.getELContext());

		if (attoBean.getTipoAtto().equals("REF")
				|| attoBean.getTipoAtto().equals("PDA")
				|| attoBean.getTipoAtto().equals("PDL")
				|| attoBean.getTipoAtto().equals("PRE")
				|| attoBean.getTipoAtto().equals("PLP"))

		{
			return true;
		} else {
			return false;
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

	public List<Abbinamento> getAbbinamentiAttivi() {
		return abbinamentiAttivi;
	}

	public void setAbbinamentiAttivi(List<Abbinamento> abbinamentiAttivi) {
		this.abbinamentiAttivi = abbinamentiAttivi;
	}



	public List<Relatore> getRelatoriAttivi() {
		return relatoriAttivi;
	}



	public void setRelatoriAttivi(List<Relatore> relatoriAttivi) {
		this.relatoriAttivi = relatoriAttivi;
	}

	


}
