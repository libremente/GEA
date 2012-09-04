package com.sourcesense.crl.web.ui.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.sourcesense.crl.web.ui.beans.AttoBean;
import com.sourcesense.crl.web.ui.beans.UserBean;


@ManagedBean(name = "navigationBean")
@SessionScoped
public class NavigationRules {
	
	
	AttoBean attoBean;
	UserBean userBean;
	
	
	@PostConstruct
	protected void init(){
		
		FacesContext context = FacesContext.getCurrentInstance();
		attoBean = (AttoBean) context
				.getApplication()
				.getExpressionFactory()
				.createValueExpression(context.getELContext(), "#{attoBean}",
						AttoBean.class).getValue(context.getELContext());
		
		//TODO get userBean 
		
	}
	
	
	
	
	
	
    public boolean gestioneSeduteConsultazioniCommissione (){
    	
    	 return userBean.getUser().getGruppi().equals("commissione");
    } 
	

}
