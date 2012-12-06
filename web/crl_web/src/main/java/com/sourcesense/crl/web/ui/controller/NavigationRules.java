package com.sourcesense.crl.web.ui.controller;

import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.model.Commissione;
import com.sourcesense.crl.business.model.GruppoUtente;
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

	
	public String getSottoStatoCommissioneConsultiva (){
		
		Commissione comm = attoBean.getWorkingCommissione(userBean.getUser().getSessionGroup().getNome());
		
		if (comm != null && comm.getRuolo().equals(Commissione.RUOLO_CONSULTIVA)){
			
			return comm.getStato();
			
		}
		
		
		return null;
	}
	
	
	public boolean isEACDisabled(){
		
		return !("ServizioCommissioni".equals(userBean.getUserGroupName())
				|| GruppoUtente.ADMIN.equals(userBean.getUserGroupName())) ;
	}
	
	
	
    public boolean isMISDisabled(){
		
    	return !(GruppoUtente.CPCV.equals(userBean.getUserGroupName())
				|| GruppoUtente.ADMIN.equals(userBean.getUserGroupName())) ;
		
	}
	
	
	public boolean presaCaricoAulaDisabled() {

		if (attoBean.getStato().equals(StatoAtto.TRASMESSO_AULA)
				|| attoBean.getStato().equals(StatoAtto.PRESO_CARICO_AULA)
				|| attoBean.getStato().equals(StatoAtto.VOTATO_AULA)
				|| attoBean.getStato().equals(StatoAtto.PUBBLICATO)
				)

		{
			return false;
		} else {
			return true;
		}

	}
	
	public boolean presentazioneAssegnazioneDisabled() {
    boolean disabled;
		if (GruppoUtente.SERVIZIO_COMMISSIONI.equals(userBean.getUserGroupName())
				|| GruppoUtente.ADMIN.equals(userBean.getUserGroupName())) {
			disabled = false;
		} else {
			disabled = true;
		}
    
    if (! disabled){
      disabled = isSessionAttoPDA_UDP();
    }
    return disabled;
	}

	public boolean esameCommissioniDisabled() {
    
    boolean disabled;
		if ((attoBean.containCommissione(userBean.getUser().getSessionGroup()
				.getNome()) || GruppoUtente.ADMIN.equals(userBean
				.getUserGroupName()))
				&& attoBean.getLastPassaggio().getCommissioni().size() > 0) {
			disabled = false;
		} else {
			disabled = true;
		}
    
    if (!disabled){
      disabled = isSessionAttoPDA_UDP();
    }
    
    return disabled;
	}
  
  public boolean consultazioniEPareriDisabled(){
    return isSessionAttoPDA_UDP();
  }
  
  
  public boolean collegamentiDisabled(){
    return false;
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
	
	public boolean emendamentiClausoleEnabled() {
		
		if (attoBean.getTipoAtto().equals("PDA")
				|| attoBean.getTipoAtto().equals("PLP")
				|| attoBean.getTipoAtto().equals("PRE")
				|| attoBean.getTipoAtto().equals("REF")
				|| attoBean.getTipoAtto().equals("REL")
				|| attoBean.getTipoAtto().equals("PDL"))

		{
			return true;
		} else {
			return false;
		}
		
		
	}
	
	public boolean abbinamentiEnabled() {
		
		if (attoBean.getTipoAtto().equals("PDL") || attoBean.getTipoAtto().equals("PLP"))

		{
			return true;
		} else {
			return false;
		}
		
		
	}

	public boolean esameAulaDisabled() {

		if ((GruppoUtente.AULA.equals(userBean.getUserGroupName()) || GruppoUtente.ADMIN
				.equals(userBean.getUserGroupName()))
				&& !(attoBean.getTipoAtto().equals("PAR")
						|| attoBean.getTipoAtto().equals("REL")
						|| attoBean.getTipoAtto().equals("INP")
						|| attoBean.getTipoAtto().equals("EAC")
						|| attoBean.getTipoAtto().equals("MIS"))
						//TODO Flag
						//|| ! attoBean.getTipologia().equalsIgnoreCase("PRS")

		) {
			return false;
		} else {
			return true;
		}

	}
  
  public boolean emendamentiEnabled(){
    return isSessionAttoPDL() || isSessionAttoPDA_UDP();
  }
  
  
  public boolean rinvioEStralciEnabled(){
    return (!isSessionAttoDOC() && !isSessionAttoPDA_UDP());
  }
	
	public boolean stralciAulaEnabled() {

		if (attoBean.getTipoAtto().equals("PDA")
						|| attoBean.getTipoAtto().equals("PLP")
						|| attoBean.getTipoAtto().equals("PRE")
						|| attoBean.getTipoAtto().equals("REF")

		) {
			return false;
		} else {
			return true;
		}

	}
	
	public boolean pubblicazioneBurlEnabled() {

		if (attoBean.getTipoAtto().equals("INP")
						|| attoBean.getTipoAtto().equals("PAR")
						|| attoBean.getTipoAtto().equals("REL")

		) {
			return false;
		} else {
			return true;
		}

	}
	
	public boolean testoAttoVotatoEnabled() {

		if (attoBean.getTipoAtto().equals("REL")
						|| attoBean.getTipoAtto().equals("INP")

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
	
	public boolean isSessionAttoDOC() {
		return attoBean.getTipoAtto().equalsIgnoreCase("DOC");
	}
	
	public boolean isSessionAttoPDA() {
		return attoBean.getTipoAtto().equalsIgnoreCase("PDA");
	}
  
  public boolean isSessionAttoPDA_UDP(){
    boolean res = attoBean.getTipoAtto().equalsIgnoreCase("PDA")
      && ("Udp".equalsIgnoreCase(attoBean.getTipoIniziativa()));
    
    // TODO: da rimuovere, condizione per test
    res = "32".equals(attoBean.getNumeroAtto());
    
    return res;
  }  
	
	public boolean isSessionAttoPLP() {
		return attoBean.getTipoAtto().equalsIgnoreCase("PLP");
	}

	public boolean gestioneSeduteConsultazioniCommissione() {
		// TODO
		return userBean.getUser().getSessionGroup().isCommissione();
	}

	public boolean gestioneSeduteConsultazioniAula() {
		return GruppoUtente.AULA.equals(userBean.getUser().getSessionGroup().getNome());
	}

	public boolean isCommissioneUpdateEnabled(){
		
		Commissione commissione = attoBean.getWorkingCommissione(userBean
				.getUser().getSessionGroup().getNome());

		if (commissione != null
				&& (Commissione.RUOLO_REFERENTE.equals(commissione.getRuolo())
						|| Commissione.RUOLO_DELIBERANTE.equals(commissione.getRuolo())
						|| Commissione.RUOLO_REDIGENTE.equals(commissione.getRuolo())
						|| Commissione.RUOLO_COREFERENTE.equals(commissione.getRuolo()))) {
			return true;
		}

		return false;
 
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
		
		
		
		if (userBean.getUser().getSessionGroup().getNome().equals(GruppoUtente.GUEST)){
			return true;
		}
		
		return false;
		
	}

}
