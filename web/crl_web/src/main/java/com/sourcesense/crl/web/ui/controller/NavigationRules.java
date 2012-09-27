package com.sourcesense.crl.web.ui.controller;

import com.sourcesense.crl.business.model.Commissione;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.sourcesense.crl.web.ui.beans.AttoBean;
import com.sourcesense.crl.web.ui.beans.UserBean;

@ManagedBean(name = "navigationBean")
@RequestScoped
public class NavigationRules {

	
	/**
	 * Gruppi 
	 * ADMINISTRATORS
	 * servizioCommissioni
	 * CommissioneN
	 * Aula
	 */
	
    AttoBean attoBean;
    UserBean userBean;

    @PostConstruct
    protected void init() {

        FacesContext context = FacesContext.getCurrentInstance();

        userBean = (UserBean) context
                .getApplication()
                .getExpressionFactory()
                .createValueExpression(context.getELContext(), "#{userBean}",
                UserBean.class).getValue(context.getELContext());

        attoBean = (AttoBean) context
                .getApplication()
                .getExpressionFactory()
                .createValueExpression(context.getELContext(), "#{attoBean}",
                AttoBean.class).getValue(context.getELContext());


    }

    public boolean presentazioneAssegnazioneDisabled() {

        return false;

    }

    public boolean esameCommissioniDisabled() {


        return !attoBean.containCommissione(userBean.getUser().getSessionGroup().getNome());

    }

    public boolean esameAulaDisabled() {

        FacesContext context = FacesContext.getCurrentInstance();

        return false;

    }

    public boolean chiusuraIterDisabled() {


        return false;

    }

    public boolean isSessionAttoPAR() {
        return attoBean.getTipoAtto().equalsIgnoreCase("PAR");
    }

    public boolean gestioneSeduteConsultazioniCommissione() {
    	//TODO
        return userBean.getUser().getSessionGroup().getNome().equals("Commissione");
    }

    public boolean gestioneSeduteConsultazioniAula() {
        return userBean.getUser().getSessionGroup().getNome().equals("Aula");
    }

    public boolean isCommissioneConsultiva() {
        Commissione commissione = attoBean.getWorkingCommissione(userBean.getUser().getSessionGroup().getNome());

        if (commissione != null && Commissione.RUOLO_CONSULTIVA.equals(commissione.getRuolo())) {
            return true;
        }

        return false;
    }
}
