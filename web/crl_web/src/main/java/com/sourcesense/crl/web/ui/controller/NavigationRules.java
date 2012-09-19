package com.sourcesense.crl.web.ui.controller;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
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
		
		userBean = (UserBean) context
				.getApplication()
				.getExpressionFactory()
				.createValueExpression(context.getELContext(), "#{userBean}",
						UserBean.class).getValue(context.getELContext());

	}

	
	
	
	public String presentazioneAssegnazioneEnabled(){
		   
		FacesContext context = FacesContext.getCurrentInstance();
		
		attoBean = (AttoBean) context
				.getApplication()
				.getExpressionFactory()
				.createValueExpression(context.getELContext(), "#{attoBean}",
						AttoBean.class).getValue(context.getELContext());
        
		//if(attoBean.containCommissione(userBean.getUser().getSessionGroup().getNome())){
		
        	return "pretty : Presentazione_e_Assegnazione";
       /* }else{
        	
        	//Message
        	context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Attenzione ! Utenza non autorizzata per la fase di processo Esame Commissioni ",
							""));
        	return "";
        }*/
	}
	
	public String esameCommissioniEnabled(){
		   
		FacesContext context = FacesContext.getCurrentInstance();
		
		attoBean = (AttoBean) context
				.getApplication()
				.getExpressionFactory()
				.createValueExpression(context.getELContext(), "#{attoBean}",
						AttoBean.class).getValue(context.getELContext());
        
		//if(attoBean.containCommissione(userBean.getUser().getSessionGroup().getNome())){
		
        	return "pretty : Esame_Commissioni";
        /*}else{
        	
        	//Message
        	context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Attenzione ! Utenza non autorizzata per la fase di processo Esame Commissioni ",
							""));
        	return "";
        }*/
	}
	
	public String esameAulaEnabled(){
		   
		FacesContext context = FacesContext.getCurrentInstance();
		
		attoBean = (AttoBean) context
				.getApplication()
				.getExpressionFactory()
				.createValueExpression(context.getELContext(), "#{attoBean}",
						AttoBean.class).getValue(context.getELContext());
        
		//if(attoBean.containCommissione(userBean.getUser().getSessionGroup().getNome())){
		
        	return "pretty : Esame_Aula";
        /*}else{
        	
        	//Message
        	context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Attenzione ! Utenza non autorizzata per la fase di processo Esame Commissioni ",
							""));
        	return "";
        }*/
	}
	
	
	public String chiusuraIterEnabled(){
		   
		FacesContext context = FacesContext.getCurrentInstance();
		
		attoBean = (AttoBean) context
				.getApplication()
				.getExpressionFactory()
				.createValueExpression(context.getELContext(), "#{attoBean}",
						AttoBean.class).getValue(context.getELContext());
        
		//if(attoBean.containCommissione(userBean.getUser().getSessionGroup().getNome())){
		
        	return "pretty : Chiusura_Iter";
        /*}else{
        	
        	//Message
        	context.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Attenzione ! Utenza non autorizzata per la fase di processo Esame Commissioni ",
							""));
        	return "";
        }*/
	}
	
	public boolean isSessionAttoPAR (){		
		return attoBean.getTipoAtto().equalsIgnoreCase("PAR");
	}


	public boolean gestioneSeduteConsultazioniCommissione (){		
		return userBean.getUser().getSessionGroup().getNome().equals("Commissione");
	} 
	
	public boolean gestioneSeduteConsultazioniAula () {
		return userBean.getUser().getSessionGroup().getNome().equals("Aula");
	}

}
