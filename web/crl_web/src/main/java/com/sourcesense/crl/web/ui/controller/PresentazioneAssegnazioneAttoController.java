package com.sourcesense.crl.web.ui.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.sourcesense.crl.web.ui.beans.AttoBean;
import com.sourcesense.crl.web.ui.beans.UserBean;




@ManagedBean(name = "presentazioneAssegnazioneAttoController")
@ViewScoped
public class PresentazioneAssegnazioneAttoController {

	
	public void presaInCarico() {  
        
		//TODO Service logic
		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean)context.getExternalContext().getSessionMap().get("attoBean"));
		String username = ((UserBean)context.getExternalContext().getSessionMap().get("userBean")).getUsername();
		String numeroAtto = attoBean.getNumeroAtto();
		System.out.println("AttoBean New ===="+attoBean.getDataPresaInCarico());
		context.addMessage(null, new FacesMessage("Atto "+numeroAtto+" preso in carico con successo dall' utente "+username));  
        
    }  
	
	
	
	
	
	
}
