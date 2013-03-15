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
	private String numeroDgrSeguito;
	private Date dataDgrSeguito;
    private boolean dgr;
	
	AttoBean attoBean;

	@PostConstruct
	protected void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext()
				.getSessionMap().get("attoBean"));
		setAtto((Atto) attoBean.getAtto().clone());
		
		if(atto.getTipoChiusura()!=null){
		dgr = "Parere negativo trasmesso alla Giunta".trim().equals(atto.getTipoChiusura().trim()) 
				|| "Parere favorevole trasmesso alla Giunta".trim().equals(atto.getTipoChiusura().trim());
		}
	
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
		attoBean.getAtto().setNumeroDgrSeguito(getNumeroDgrSeguito());
		attoBean.getAtto().setDataDgrSeguito(getDataDgrSeguito());
		attoBean.setStato(StatoAtto.CHIUSO);
		context.addMessage(null, new FacesMessage("Atto chiuso con successo", ""));
	}
	
	
	public void changeDgr(){
		
		String tipo = atto.getTipoChiusura().trim();
		
		dgr = "Parere negativo trasmesso alla Giunta".trim().equals(tipo) 
				|| "Parere favorevole trasmesso alla Giunta".trim().equals(tipo);
		
	} 
	
	
	public void createLeggeRegionaleLink (){
		//http://consiglionline.lombardia.it/normelombardia/accessibile/main.aspx?view=showdoc&iddoc=lr00{0}{1}
		if("".equals(getNumeroLr()) || getDataLr()==null || getNumeroLr()==null ){
			
			setUrlLeggiRegionali("");
			
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
				
				numero=app+numero;
				
			}	
				
			setUrlLeggiRegionali ( attoServiceManager.regioniUrl( data, numero));	
			//FacesContext.getCurrentInstance().getExternalContext().redirect(url);
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		
	}
	
public String tipoChiusuraPar() {
		
	return "Parere negativo trasmesso alla Giunta";
	
		/*FacesContext context = FacesContext.getCurrentInstance();
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
		}*/

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


	public String getNumeroDgrSeguito() {
		return this.atto.getNumeroDgrSeguito();
	}


	public void setNumeroDgrSeguito(String numeroDgrSeguito) {
		this.atto.setNumeroDgrSeguito(numeroDgrSeguito);
	}


	public Date getDataDgrSeguito() {
		return this.atto.getDataDgrSeguito();
	}


	public void setDataDgrSeguito(Date dataDgrSeguito) {
		this.atto.setDataDgrSeguito (dataDgrSeguito);
	}


	public boolean isDgr() {
		return dgr;
	}


	public void setDgr(boolean dgr) {
		this.dgr = dgr;
	}
	
	
	
	
	
	
}
