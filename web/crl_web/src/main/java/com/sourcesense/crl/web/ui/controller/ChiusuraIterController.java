package com.sourcesense.crl.web.ui.controller;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.model.Commissione;
import com.sourcesense.crl.business.model.StatoAtto;
import com.sourcesense.crl.business.service.AttoServiceManager;
import com.sourcesense.crl.util.URLBuilder;
import com.sourcesense.crl.web.ui.beans.AttoBean;
import com.sourcesense.crl.web.ui.beans.UserBean;

@ManagedBean(name = "chiusuraIterController")
@ViewScoped
public class ChiusuraIterController {
	
	
	@ManagedProperty(value = "#{attoServiceManager}")
	private AttoServiceManager attoServiceManager;
	
	private Atto atto = new Atto();
	
	private Date dataChiusura = new Date();
	private String tipoChiusura;
	private String note;
	private String numRiferimentoBurl;
	private Date dataBurl;
	private String numeroLr;
	private Date dataLr;
	private String urlLeggiRegionali;
	private String numeroDcr;
	
	AttoBean attoBean;

	@PostConstruct
	protected void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));
		setAtto((Atto) attoBean.getAtto().clone());
	}
	
	
	public void chiusuraAtto() {
		
		this.atto.setStato(StatoAtto.CHIUSO);
		
		attoServiceManager.chiusuraAtto(atto);
		
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));
		
		attoBean.getAtto().setDataChiusura(getDataChiusura());
		attoBean.getAtto().setTipoChiusura(getTipoChiusura());
		attoBean.getAtto().setNoteChiusuraIter(getNote());
		attoBean.getAtto().setNumeroPubblicazioneBURL(getNumRiferimentoBurl());
		attoBean.getAtto().setDataPubblicazioneBURL(getDataBurl());
		attoBean.getAtto().setNumeroLr(getNumeroLr());
		attoBean.getAtto().setDataLR(getDataLr());
		attoBean.setStato(StatoAtto.CHIUSO);
		context.addMessage(null, new FacesMessage("Atto chiuso con successo", ""));
	}
	
	
	public void createLeggeRegionaleLink (){
		//http://consiglionline.lombardia.it/normelombardia/accessibile/main.aspx?view=showdoc&iddoc=lr00{0}{1}
		if("".equals(getNumeroLr()) || getDataLr()==null || getNumeroLr()==null ){
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Attenzione ! sono necessari la Data ed il Numero LR", ""));
			
			
		}else{
			
			URLBuilder urlBuilder = new URLBuilder();
			Format formatter=new SimpleDateFormat("yyyyMMdd");
			String data = formatter.format(getDataLr());
			String numero = getNumeroLr();
			try{
			
			if(numero.length()<5){
				String app = "";
				for(int i=0;i < (5-numero.length());i++){
					
					app+="0";
				}
				
			}	
				
			String url = attoServiceManager.regioniUrl( data, numero);	
			FacesContext.getCurrentInstance().getExternalContext().redirect(url);
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		
	}
	
public String tipoChiusuraPar() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		attoBean = (AttoBean) context
				.getApplication()
				.getExpressionFactory()
				.createValueExpression(context.getELContext(), "#{attoBean}",
						AttoBean.class).getValue(context.getELContext());
		
		

		if (attoBean.getTipoAtto().equals("PAR"))
			
		{
			return "Respinto";
		}
			
		 else {
			return "Rifiutato e trasmesso alla Giunta";
		}

	}
	

	//Getters & Setters**************************************
	
	public Atto getAtto() {
		return atto;
	}

	public void setAtto(Atto atto) {
		this.atto = atto;
	}

	public Date getDataChiusura() {
		return atto.getDataChiusura();
	}

	public void setDataChiusura(Date dataChiusura) {
		this.atto.setDataChiusura(dataChiusura);
	}

	public String getTipoChiusura() {
		return atto.getTipoChiusura();
	}

	public void setTipoChiusura(String tipoChiusura) {
		this.atto.setTipoChiusura(tipoChiusura);
	}

	public String getNote() {
		return atto.getNoteChiusuraIter();
	}

	public void setNote(String note) {
		this.atto.setNoteChiusuraIter(note);
	}

	public String getNumRiferimentoBurl() {
		return atto.getNumeroPubblicazioneBURL();
	}

	public void setNumRiferimentoBurl(String numRiferimentoBurl) {
		this.atto.setNumeroPubblicazioneBURL(numRiferimentoBurl);
	}

	public Date getDataBurl() {
		return atto.getDataPubblicazioneBURL();
	}

	public void setDataBurl(Date dataBurl) {
		this.atto.setDataPubblicazioneBURL(dataBurl);
	}

	public String getNumeroLr() {
		return atto.getNumeroLr();
	}

	public void setNumeroLr(String numeroLr) {
		this.atto.setNumeroLr(numeroLr);
	}

	public Date getDataLr() {
		return atto.getDataLR();
	}

	public void setDataLr(Date dataLr) {
		this.atto.setDataLR(dataLr);
	}

	
	public String getNumeroDcr() {
		return atto.getNumeroDcr();
	}


	public void setNumeroDcr(String numeroDcr) {
		this.atto.setNumeroDcr(numeroDcr);
	}


	public AttoServiceManager getAttoServiceManager() {
		return attoServiceManager;
	}


	public void setAttoServiceManager(AttoServiceManager attoServiceManager) {
		this.attoServiceManager = attoServiceManager;
	}


	public String getUrlLeggiRegionali() {
		return urlLeggiRegionali;
	}


	public void setUrlLeggiRegionali(String urlLeggiRegionali) {
		this.urlLeggiRegionali = urlLeggiRegionali;
	}
	
	
	
	
}
