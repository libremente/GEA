package com.sourcesense.crl.web.ui.controller;

import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.model.Commissione;
import com.sourcesense.crl.business.model.StatoAtto;

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
	 * Gruppi ADMINISTRATORS servizioCommissioni CommissioneN Aula Guest
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

		if ("ServizioCommissioni".equals(userBean.getUserGroupName())
				|| "ADMINISTRATORS".equals(userBean.getUserGroupName())) {
			return false;
		} else {
			return true;
		}

	}

	public boolean esameCommissioniDisabled() {

		if ((attoBean.containCommissione(userBean.getUser().getSessionGroup()
				.getNome()) || "ADMINISTRATORS".equals(userBean
				.getUserGroupName()))
				&& attoBean.getLastPassaggio().getCommissioni().size() > 0) {
			return false;
		} else {
			return true;
		}

	}

	public boolean isFirmatariEnabled() {

		if (attoBean.getTipoAtto().equals("PAR")
				|| attoBean.getTipoAtto().equals("INP")
				|| attoBean.getTipoAtto().equals("PRE")
				|| attoBean.getTipoAtto().equals("REL"))

		{
			return false;
		} else {
			return true;
		}

	}

	public boolean esameAulaDisabled() {

		if (("Aula".equals(userBean.getUserGroupName()) || "ADMINISTRATORS"
				.equals(userBean.getUserGroupName()))
				&& !(attoBean.getTipoAtto().equals("PAR")
						|| attoBean.getTipoAtto().equals("REL")
						|| attoBean.getTipoAtto().equals("INP")
						|| attoBean.getTipoAtto().equals("EAC") || attoBean
						.getTipoAtto().equals("MIS"))

		) {
			return false;
		} else {
			return true;
		}

	}

	public boolean chiusuraIterDisabled() {

		return false;

	}

	public boolean isSessionAttoEsitoCommissioneApprovato() {
		boolean risultato = false;
		if (attoBean.getTipoAtto().equalsIgnoreCase("PDL")
				|| attoBean.getTipoAtto().equalsIgnoreCase("PAR")
				|| attoBean.getTipoAtto().equalsIgnoreCase("PDA")
				|| attoBean.getTipoAtto().equalsIgnoreCase("PLP")
				|| attoBean.getTipoAtto().equalsIgnoreCase("PRE")
				|| attoBean.getTipoAtto().equalsIgnoreCase("REF")
				|| (attoBean.getTipoAtto().equalsIgnoreCase("DOC") && attoBean
						.getTipologia().equalsIgnoreCase("PRS"))) {

			risultato = true;

		}
		return risultato;
	}

	public boolean isSessionAttoEsitoCommissioneArchiviazioneINP() {
		boolean risultato = false;
		if (attoBean.getTipoAtto().equalsIgnoreCase("INP")
				|| (attoBean.getTipoAtto().equalsIgnoreCase("DOC") && !attoBean
						.getTipologia().equalsIgnoreCase("PRS"))) {
			risultato = true;

		}

		return risultato;
	}

	public boolean isSessionAttoEsitoCommissioneApprovatoREL() {
		boolean risultato = false;
		if (attoBean.getTipoAtto().equalsIgnoreCase("REL")) {
			risultato = true;

		}

		return risultato;
	}

	public boolean isSessionAttoChiuso() {
		return StatoAtto.CHIUSO.equals(attoBean.getStato());
	}

	public boolean isSessionAttoPAR() {
		return attoBean.getTipoAtto().equalsIgnoreCase("PAR");
	}

	public boolean isSessionAttoPDL() {
		return attoBean.getTipoAtto().equalsIgnoreCase("PDL");
	}

	public boolean gestioneSeduteConsultazioniCommissione() {
		// TODO
		return userBean.getUser().getSessionGroup().getNome()
				.startsWith("Commissione");
	}

	public boolean gestioneSeduteConsultazioniAula() {
		return userBean.getUser().getSessionGroup().getNome().equals("Aula");
	}

	public boolean isCommissioneReferente() {
		Commissione commissione = attoBean.getWorkingCommissione(userBean
				.getUser().getSessionGroup().getNome());

		if (commissione != null
				&& Commissione.RUOLO_REFERENTE.equals(commissione.getRuolo())) {
			return true;
		}

		return false;
	}

	public boolean isCommissioneConsultiva() {
		Commissione commissione = attoBean.getWorkingCommissione(userBean
				.getUser().getSessionGroup().getNome());

		if (commissione != null
				&& Commissione.RUOLO_CONSULTIVA.equals(commissione.getRuolo())) {
			return true;
		}

		return false;
	}

	public boolean isCommissioneDeliberante() {
		Commissione commissione = attoBean.getWorkingCommissione(userBean
				.getUser().getSessionGroup().getNome());

		if (commissione != null
				&& Commissione.RUOLO_DELIBERANTE.equals(commissione.getRuolo())) {
			return true;
		}

		return false;
	}
	
	public boolean isGuest(){
		
		
		
		if (userBean.getUser().getSessionGroup().getNome().equals("Guest")){
			return true;
		}
		
		return false;
		
	}

}
