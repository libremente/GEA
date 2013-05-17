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

	public boolean isInsertMISEnabled() {

		return (GruppoUtente.SERVIZIO_COMMISSIONI.equals(userBean
				.getUserGroupName())
				|| GruppoUtente.ADMIN.equals(userBean.getUserGroupName()) || GruppoUtente.AULA
					.equals(userBean.getUserGroupName()));
	}

	public boolean isInsertEACEnabled() {

		return (GruppoUtente.SERVIZIO_COMMISSIONI.equals(userBean
				.getUserGroupName())
				|| GruppoUtente.ADMIN.equals(userBean.getUserGroupName()) || GruppoUtente.AULA
					.equals(userBean.getUserGroupName()));
	}

	public boolean isInsertEnabled() {

		return (GruppoUtente.SERVIZIO_COMMISSIONI.equals(userBean
				.getUserGroupName())
				|| GruppoUtente.ADMIN.equals(userBean.getUserGroupName()) || GruppoUtente.AULA
					.equals(userBean.getUserGroupName()));
	}

	public String getSottoStatoCommissioneConsultiva() {

		Commissione comm = attoBean.getWorkingCommissione(userBean.getUser()
				.getSessionGroup().getNome());

		if (comm != null
				&& comm.getRuolo().equals(Commissione.RUOLO_CONSULTIVA)) {

			return comm.getStato();

		}

		return null;
	}

	public boolean hasDataRichiestaIscrizioneAula() {
		Commissione comm = attoBean.getWorkingCommissione(userBean.getUser()
				.getSessionGroup().getNome());

		return !(comm.getRuolo().equals(Commissione.RUOLO_CONSULTIVA) || comm
				.getRuolo().equals(Commissione.RUOLO_DELIBERANTE));
	}

	public boolean isEACDisabled() {

		return !("ServizioCommissioni".equals(userBean.getUserGroupName()) || GruppoUtente.ADMIN
				.equals(userBean.getUserGroupName()));
	}

	public boolean isEACDisabledComm() {

		return !("ServizioCommissioni".equals(userBean.getUserGroupName())
				|| GruppoUtente.ADMIN.equals(userBean.getUserGroupName()) || userBean
				.getUser().getSessionGroup().isCommissione());
	}

	public boolean isMISDisabled() {

		return !(GruppoUtente.CPCV.equals(userBean.getUserGroupName()) || GruppoUtente.ADMIN
				.equals(userBean.getUserGroupName()));

	}

	public boolean isCPCVUser() {

		return (GruppoUtente.CPCV.equals(userBean.getUserGroupName()));

	}

	public boolean presaCaricoAulaDisabled() {

		if (attoBean.getStato().equals(StatoAtto.TRASMESSO_AULA)
				|| attoBean.getStato().equals(StatoAtto.PRESO_CARICO_AULA)
				|| attoBean.getStato().equals(StatoAtto.VOTATO_AULA)
				|| attoBean.getStato().equals(StatoAtto.PUBBLICATO))

		{
			return false;
		} else {
			return true;
		}

	}

	public boolean presentazioneAssegnazioneDisabled() {
		boolean disabled;

		if (!isSessionAttoORG()
				&& !isSessionAttoPDA_UDP()
				&& (GruppoUtente.SERVIZIO_COMMISSIONI.equals(userBean
						.getUserGroupName()) || GruppoUtente.ADMIN
						.equals(userBean.getUserGroupName()))) {
			disabled = false;
		} else {
			disabled = true;
		}

		return disabled;
	}

	public boolean esameCommissioniDisabled() {

		boolean disabled;
		if (!isSessionAttoORG()
				&& !isSessionAttoPDA_UDP()
				&& (attoBean.containCommissione(userBean.getUser()
						.getSessionGroup().getNome()) || GruppoUtente.ADMIN
						.equals(userBean.getUserGroupName()))
				&& attoBean.getLastPassaggio().getCommissioni().size() > 0) {
			disabled = false;

		} else {
			disabled = true;
		}

		return disabled;
	}

	public boolean consultazioniEPareriDisabled() {
		Commissione commissione = attoBean.getWorkingCommissione(userBean
				.getUser().getSessionGroup().getNome());

		return commissione == null || isSessionAttoPDA_UDP()
				|| isSessionAttoORG();
	}

	public boolean collegamentiDisabled() {
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

	public boolean organismiEnabled() {

		if (attoBean.getTipoAtto().equals("PDA")
				|| attoBean.getTipoAtto().equals("PLP")
				|| attoBean.getTipoAtto().equals("PRE")
				|| attoBean.getTipoAtto().equals("REF")
				|| attoBean.getTipoAtto().equals("PDL")
				|| attoBean.getTipoAtto().equals("DOC"))

		{
			return true;
		} else {
			return false;
		}

	}

	public boolean emendamentiClausoleEnabled() {

		if (attoBean.getTipoAtto().equals("PDA")
				|| attoBean.getTipoAtto().equals("PLP")
				|| attoBean.getTipoAtto().equals("PRE")
				|| attoBean.getTipoAtto().equals("REF")
				|| attoBean.getTipoAtto().equals("REL")
				|| attoBean.getTipoAtto().equals("PDL")
				|| attoBean.getTipoAtto().equals("DOC"))

		{
			return true;
		} else {
			return false;
		}

	}

	public boolean isClausoleEnabled() {

		Commissione commissione = attoBean.getWorkingCommissione(userBean
				.getUser().getSessionGroup().getNome());

		if (attoBean.getTipoAtto().equals("PDL")
				&& (Commissione.RUOLO_REFERENTE.equals(commissione.getRuolo())
						|| Commissione.RUOLO_COREFERENTE.equals(commissione
								.getRuolo()) || Commissione.RUOLO_REDIGENTE
							.equals(commissione.getRuolo())))

		{
			return true;
		} else {
			return false;
		}

	}

	public boolean abbinamentiEnabled() {

		if (attoBean.getTipoAtto().equals("PDL")
				|| attoBean.getTipoAtto().equals("PLP"))

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
						|| attoBean.getTipoAtto().equals("MIS") || (attoBean
						.getTipoAtto().equalsIgnoreCase("DOC") && !attoBean
						.getAtto().isIterAula()))

		) {
			return false;
		} else {
			return true;
		}

	}

	public boolean boxAulaVisible() {

		if (attoBean.getTipoAtto().equals("PAR")
				|| attoBean.getTipoAtto().equals("REL")
				|| attoBean.getTipoAtto().equals("INP")
				|| (attoBean.getTipoAtto().equalsIgnoreCase("DOC") && !attoBean
						.getAtto().isIterAula())) {
			return false;
		} else {
			return true;
		}

	}

	public boolean emendamentiEnabled() {
		if (isSessionAttoPDL() || isSessionAttoORG()
				|| attoBean.getTipoAtto().equals("PDA")
				|| attoBean.getTipoAtto().equals("PLP")
				|| attoBean.getTipoAtto().equals("PRE")
				|| attoBean.getTipoAtto().equals("DOC")
				|| attoBean.getTipoAtto().equals("REF"))

		{
			return true;
		} else {
			return false;
		}

	}

	public boolean datiAttoEnabled() {
		return isSessionAttoPDA_UDP() || isSessionAttoORG();
	}

	public boolean rinvioEStralciEnabled() {
		return (!isSessionAttoDOC() && !isSessionAttoPDA_UDP() && !isSessionAttoORG());
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

	public boolean isBURLEnabled() {

		if (attoBean.getTipoAtto().equals("REL")
				|| attoBean.getTipoAtto().equals("INP")
				|| "Per decadenza (fine legislatura)".equals(attoBean
						.getTipoChiusura())
				|| "Respinto dall'Aula".equals(attoBean.getTipoChiusura())
				|| "Ritirato dai promotori".equals(attoBean.getTipoChiusura())
				|| "Abbinato ad altro atto".equals(attoBean.getTipoChiusura())
				|| "Inammissibile".equals(attoBean.getTipoChiusura())
				|| "Istruttoria conclusa".equals(attoBean.getTipoChiusura())
				|| "Irricevibile".equals(attoBean.getTipoChiusura())
				|| "Improcedibile".equals(attoBean.getTipoChiusura())
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

	public boolean isSessionAttoINP() {
		return attoBean.getTipoAtto().equalsIgnoreCase("INP");
	}

	public boolean isSessionAttoDOC() {
		return attoBean.getTipoAtto().equalsIgnoreCase("DOC");
	}
	public boolean isSessionAttoPRE() {
		return attoBean.getTipoAtto().equalsIgnoreCase("PRE");
	}
	public boolean isNotSessionAttoDOCAula() {
		return attoBean.getTipoAtto().equalsIgnoreCase("DOC")
				&& !attoBean.getAtto().isIterAula();
	}

	
	
	public boolean canTransmitToAula() {

		/*
		 * if (isCommissioneConsultiva()) {
		 * 
		 * return false; }
		 */

		if (isSessionAttoPAR()) {

			return false;
		}

		return true;

	}

	public boolean isSessionAttoPDA() {
		return attoBean.getTipoAtto().equalsIgnoreCase("PDA");
	}

	public boolean isSessionAttoPDA_UDP() {
		boolean res = attoBean.getTipoAtto().equalsIgnoreCase("PDA")
				&& ("05_ATTO DI INIZIATIVA UFFICIO PRESIDENZA"
						.equalsIgnoreCase(attoBean.getTipoIniziativa()));

		return res;
	}

	public boolean isSessionAttoORG() {

		return attoBean.getTipoAtto().equalsIgnoreCase("ORG");

	}

	public boolean isSessionAttoPLP() {
		return attoBean.getTipoAtto().equalsIgnoreCase("PLP");
	}

	public boolean isGestioneSeduteEnabled() {

		return userBean.getUser().getSessionGroup().isCommissione()
				|| GruppoUtente.AULA.equals(userBean.getUser()
						.getSessionGroup().getNome());
	}

	public boolean gestioneSeduteConsultazioniCommissione() {

		return userBean.getUser().getSessionGroup().isCommissione();
	}

	public boolean gestioneSeduteConsultazioniAula() {
		return GruppoUtente.AULA.equals(userBean.getUser().getSessionGroup()
				.getNome());
	}

	public boolean isCommissioneUpdateEnabled() {

		Commissione commissione = attoBean.getWorkingCommissione(userBean
				.getUser().getSessionGroup().getNome());

		if (commissione != null
				&& (Commissione.RUOLO_REFERENTE.equals(commissione.getRuolo())
						|| Commissione.RUOLO_DELIBERANTE.equals(commissione
								.getRuolo())
						|| Commissione.RUOLO_REDIGENTE.equals(commissione
								.getRuolo()) || Commissione.RUOLO_COREFERENTE
							.equals(commissione.getRuolo()))) {
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

	public boolean hasCommissioneDeliberante() {

		return attoBean.getCommissioneDeliberante() != null;

	}

	public boolean isCalendarizzazioneTipo() {

		if (attoBean.getTipoAtto().equalsIgnoreCase("PDL")
				|| attoBean.getTipoAtto().equalsIgnoreCase("PDA")
				|| attoBean.getTipoAtto().equalsIgnoreCase("PLP")
				|| attoBean.getTipoAtto().equalsIgnoreCase("PRE")
				|| attoBean.getTipoAtto().equalsIgnoreCase("REF")
				|| attoBean.getTipoAtto().equalsIgnoreCase("REL")
				|| (attoBean.getTipoAtto().equalsIgnoreCase("DOC") && !attoBean
						.getAtto().isIterAula())

		) {
			return true;
		}

		return false;

	}

	public boolean isRisEnabled() {

		Commissione commissione = attoBean.getWorkingCommissione(userBean
				.getUser().getSessionGroup().getNome());

		if (commissione != null
				&& (Commissione.RUOLO_REFERENTE.equals(commissione.getRuolo())
						|| Commissione.RUOLO_COREFERENTE.equals(commissione
								.getRuolo())
						|| Commissione.RUOLO_REDIGENTE.equals(commissione
								.getRuolo()) || Commissione.RUOLO_DELIBERANTE
							.equals(commissione.getRuolo()))
				&& (attoBean.getTipoAtto().equalsIgnoreCase("INP")
						|| attoBean.getTipoAtto().equalsIgnoreCase("DOC") || attoBean
						.getTipoAtto().equalsIgnoreCase("REL"))

		) {
			return true;
		}

		return false;

	}

	public boolean isRisTipo() {

		if (attoBean.getTipoAtto().equalsIgnoreCase("INP")
				|| attoBean.getTipoAtto().equalsIgnoreCase("DOC")
				|| attoBean.getTipoAtto().equalsIgnoreCase("REL")) {
			return true;
		}

		return false;

	}

	public boolean isCalendarizzazioneEnabled() {

		Commissione commissione = attoBean.getWorkingCommissione(userBean
				.getUser().getSessionGroup().getNome());

		if (commissione != null
				&& (Commissione.RUOLO_REFERENTE.equals(commissione.getRuolo())
						|| Commissione.RUOLO_COREFERENTE.equals(commissione
								.getRuolo()) || Commissione.RUOLO_REDIGENTE
							.equals(commissione.getRuolo()))
				&& (attoBean.getTipoAtto().equalsIgnoreCase("PDL")
						|| attoBean.getTipoAtto().equalsIgnoreCase("PDA")
						|| attoBean.getTipoAtto().equalsIgnoreCase("PLP")
						|| attoBean.getTipoAtto().equalsIgnoreCase("PRE")
						|| attoBean.getTipoAtto().equalsIgnoreCase("REF")
						|| attoBean.getTipoAtto().equalsIgnoreCase("REL") || (attoBean
						.getTipoAtto().equalsIgnoreCase("DOC") && !attoBean
						.getAtto().isIterAula()))

		) {
			return true;
		}

		return false;

	}

	public boolean isContinuazioneLavoriReferente() {
		Commissione commissione = attoBean.getWorkingCommissione(userBean
				.getUser().getSessionGroup().getNome());

		if (commissione != null
				&& Commissione.RUOLO_DELIBERANTE.equals(commissione.getRuolo())
				|| (commissione.getMotivazioniContinuazioneInReferente() != null && !""
						.equals(commissione
								.getMotivazioniContinuazioneInReferente()))
				|| commissione.getDataSedutaContinuazioneInReferente() != null) {
			return true;
		}

		return false;
	}

	public boolean isServizioCommissioni() {

		return GruppoUtente.SERVIZIO_COMMISSIONI.equals(userBean
				.getUserGroupName());

	}

	public boolean isGuest() {

		if (userBean.getUser().getSessionGroup().getNome()
				.equals(GruppoUtente.GUEST)) {
			return true;
		}

		return false;

	}

}
