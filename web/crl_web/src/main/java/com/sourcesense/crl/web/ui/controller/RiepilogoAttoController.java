package com.sourcesense.crl.web.ui.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sourcesense.crl.business.model.Allegato;
import com.sourcesense.crl.business.model.Organo;



@ManagedBean(name = "riepilogoAttoController")
@ViewScoped
public class RiepilogoAttoController {

	private List<Allegato> testiUfficiali;
	private List<Allegato> altriAllegati;
	private List<Organo> organiInterni;
	private List<Organo> altriOrgani;
	
	@PostConstruct
	protected void init() {
		//setDocumentiAllegati(AllegatoServiceManager.findTestiUfficiali());
		//setAltriAllegati(AllegatoServiceManager.findAltriAllegati());
		//setOrganiInterni(OrganoServiceManager.findOrganiInterni());
		//setAltriOrgani(OrganoServiceManager.findAltriOrgani());
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
	
	
	
}
