package com.sourcesense.crl.web.ui.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.sourcesense.crl.business.model.GruppoUtente;
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

		userBean = (UserBean) context
				.getApplication()
				.getExpressionFactory()
				.createValueExpression(context.getELContext(), "#{userBean}",
						UserBean.class).getValue(context.getELContext());

	}






	public boolean gestioneSeduteConsultazioniCommissione (){		
		return checkGruppiUtente("commissione");
	} 
	
	public boolean gestioneSeduteConsultazioniAula () {
		return checkGruppiUtente("aula");
	}


	private boolean checkGruppiUtente(String gruppoUtente) {
		for(GruppoUtente element: userBean.getUser().getGruppi()) {

			if(element.getNome().equals(gruppoUtente)) {

				return true;
			}

		}

		return false;
	}


}
