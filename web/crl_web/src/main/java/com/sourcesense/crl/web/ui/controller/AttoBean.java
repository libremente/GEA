
package com.sourcesense.crl.web.ui.controller;

import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.service.AttoServiceManager;


import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author uji
 */

@ManagedBean(name = "attoBean")
@SessionScoped
public class AttoBean implements Serializable
{
	
	
	@ManagedProperty(value="#{attoServiceManager}")
	private AttoServiceManager attoServiceManager ;
	
	private Atto atto;
	
	private String numeroAtto;
	
	public Atto getAtto() {
		return atto;
	}

	public void setAtto(Atto atto) {
		this.atto = atto;
	}

	public AttoServiceManager getAttoServiceManager() {
		return attoServiceManager;
	}

	public void setAttoServiceManager(AttoServiceManager attoServiceManager) {
		this.attoServiceManager = attoServiceManager;
	}
	
	

	public String getNumeroAtto() {
		return numeroAtto;
	}

	public void setNumeroAtto(String numeroAtto) {
		this.numeroAtto = numeroAtto;
	}

	public void  inserisciAtto(ActionEvent actionEvent){
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Welcome !"));  
	}
    
    
}
