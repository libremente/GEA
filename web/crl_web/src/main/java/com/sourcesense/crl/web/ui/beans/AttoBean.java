package com.sourcesense.crl.web.ui.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.service.AttoServiceManager;

@ManagedBean(name = "attoBean")
@SessionScoped
public class AttoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean showCommDetail;
	private Atto atto = new Atto();
	
	private String codice;
	private String numeroAtto;
	
	

	@ManagedProperty(value="#{attoServiceManager}")
	private AttoServiceManager attoServiceManager ;

	
	
	
	/**
	 * @return the code
	 */
	public String getCodice() {
		return atto.getCodice();
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCodice(String codice) {
		this.codice = codice;

		if (codice != null)
			setAtto(attoServiceManager.get(codice));
	}

	/**
	 * @return the atto
	 */
	public Atto getAtto() {
		return atto;
	}

	/**
	 * @param atto
	 *            the atto to set
	 */
	public void setAtto(Atto atto) {
		this.atto = atto;
	}

	public boolean isShowCommDetail() {
		return showCommDetail;
	}

	public void setShowCommDetail(boolean showCommDetail) {
		this.showCommDetail = showCommDetail;
	}

	public void visualizeCommDetail() {
		this.showCommDetail = true;
	}

	public String getNumeroAtto() {
		return atto.getNumeroAtto();
	}

	public void setNumeroAtto(String numeroAtto) {
		
		this.atto.setNumeroAtto(numeroAtto);
	}

	public AttoServiceManager getAttoServiceManager() {
		return attoServiceManager;
	}

	public void setAttoServiceManager(AttoServiceManager attoServiceManager) {
		this.attoServiceManager = attoServiceManager;
	}
	
	
	

}