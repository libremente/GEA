package com.sourcesense.crl.web.ui.controller;

import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.service.AttoServiceManager;
import com.sourcesense.crl.business.service.LegislaturaServiceManager;
import com.sourcesense.crl.business.service.TipoAttoServiceManager;

import com.sourcesense.crl.util.CRLMessage;
import com.sourcesense.crl.web.ui.beans.AttoBean;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 * 
 * @author uji
 */

@ManagedBean(name = "inserisciAttoController")
@ViewScoped
public class InserisciAttoController {

	
	

	@ManagedProperty(value = "#{attoServiceManager}")
	private AttoServiceManager attoServiceManager;

	@ManagedProperty(value = "#{legislaturaServiceManager}")
	private LegislaturaServiceManager legislaturaServiceManager;
	                          
	@ManagedProperty(value = "#{tipoAttoServiceManager}")
	private  TipoAttoServiceManager tipoAttoServiceManager;
	
	
	private Atto atto = new Atto();

	private String numeroAtto;
	
	private String tipoAtto;
	
	private String tipologia;
	
	private String legislatura;
	
	private String anno;

	private Map<String, String> tipiAtto = new HashMap<String, String>();

	private Map<String,String> tipologie = new HashMap<String, String>(){
		
	};

	private Map<String, String> legislature = new HashMap<String, String>();

	private Map<String,String> anni = new HashMap<String, String>();

	
	@PostConstruct
	private void initializeValues(){
		
		
		//TODO
		setTipiAtto(tipoAttoServiceManager.findAll());
		setLegislature(legislaturaServiceManager.findAll());
		
		
	}
	
    
	
	
	public void handleTipoAttoChange() {  
		
		
        if(atto.getTipoAtto() !=null && !atto.getTipoAtto().equals(""))  
            setTipologie(tipoAttoServiceManager.findTipologieByTipoAtto(atto.getTipoAtto()));  
        else  
        	tipologie = new HashMap<String, String>();  
    }  
	
	
	public String inserisciAtto() {

		
		if (attoServiceManager.persist(atto)) {

			FacesContext context = FacesContext.getCurrentInstance();
			AttoBean attoBean = (AttoBean) context
					.getApplication()
					.getExpressionFactory()
					.createValueExpression(context.getELContext(),
							"#{attoBean}", AttoBean.class)
					.getValue(context.getELContext());

			attoBean.setAtto(this.atto);
			return "pretty:atto";

		} else {

		    return null;

		}
	}
	
	

	public AttoServiceManager getAttoServiceManager() {
		return attoServiceManager;
	}

	public void setAttoServiceManager(AttoServiceManager attoServiceManager) {
		this.attoServiceManager = attoServiceManager;
	}

	

	public Map<String, String> getTipiAtto() {
		//return tipoAttoServiceManager.findAll();
		return this.tipiAtto;
	}

	public void setTipiAtto(Map<String, String> tipiAtto) {
		this.tipiAtto = tipiAtto;
	}

	public Map<String, String> getTipologie() {
		return tipologie;
	}

	public void setTipologie(Map<String, String> tipologie) {
		this.tipologie = tipologie;
	}

	public Map<String, String> getLegislature() {
		//return legislaturaServiceManager.findAll();
		return this.legislature;
	}

	public void setLegislature(Map<String, String> legislature) {
		this.legislature = legislature;
	}

	public Map<String, String> getAnni() {
		return anni;
	}

	public void setAnni(Map<String,String> anni) {
		this.anni = anni;
	}


	public LegislaturaServiceManager getLegislaturaServiceManager() {
		return legislaturaServiceManager;
	}


	public void setLegislaturaServiceManager(
			LegislaturaServiceManager legislaturaServiceManager) {
		this.legislaturaServiceManager = legislaturaServiceManager;
	}


	public TipoAttoServiceManager getTipoAttoServiceManager() {
		return tipoAttoServiceManager;
	}


	public void setTipoAttoServiceManager(TipoAttoServiceManager tipoAttoServiceManager) {
		this.tipoAttoServiceManager = tipoAttoServiceManager;
	}

	
	
	public Atto getAtto() {
		return atto;
	}

	public void setAtto(Atto atto) {
		this.atto = atto;
	}
	
	public String getNumeroAtto() {
		return this.atto.getNumeroAtto();
	}

	public void setNumeroAtto(String numeroAtto) {
		this.atto.setNumeroAtto(numeroAtto);
	}


	public String getTipoAtto() {
		return this.atto.getTipoAtto();
	}



	public void setTipoAtto(String tipoAtto) {
		this.atto.setTipoAtto(tipoAtto);
	}



	public String getTipologia() {
		return this.atto.getTipologia();
	}



	public void setTipologia(String tipologia) {
		this.atto.setTipologia(tipologia);
		this.tipologia = tipologia;
	}



	public String getLegislatura() {
		return this.atto.getLegislatura();
	}



	public void setLegislatura(String legislatura) {
		this.atto.setLegislatura(legislatura);
	}



	public String getAnno() {
		return this.atto.getAnno();
	}



	public void setAnno(String anno) {
		this.atto.setAnno(anno);
	}
    
	
	

}