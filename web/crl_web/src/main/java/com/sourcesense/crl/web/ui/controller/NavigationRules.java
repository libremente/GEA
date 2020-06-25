/* 
 * Copyright (C) 2019 Consiglio Regionale della Lombardia
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.sourcesense.crl.web.ui.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.sourcesense.crl.business.model.Commissione;
import com.sourcesense.crl.business.model.GruppoUtente;
import com.sourcesense.crl.business.model.StatoAtto;
import com.sourcesense.crl.web.ui.beans.AttoBean;
import com.sourcesense.crl.web.ui.beans.AttoSearchBean;
import com.sourcesense.crl.web.ui.beans.UserBean;

/**
 * Regole di navigazione. Contiene i flag per la configurazione della
 * navigazione da pagina web
 * 
 * @author sourcesense
 *
 */
@ManagedBean(name = "navigationBean")
@RequestScoped
public class NavigationRules {

	/**
	 * Gruppi ADMINISTRATORS servizioCommissioni CommissioneN Aula Guest
	 */

	AttoBean attoBean;
	AttoSearchBean attoSearchBean;
	UserBean userBean;

	/**
	 * 
	 */
	@PostConstruct
	protected void init() {

		FacesContext context = FacesContext.getCurrentInstance();

		userBean = (UserBean) context.getApplication().getExpressionFactory()
				.createValueExpression(context.getELContext(), "#{userBean}", UserBean.class)
				.getValue(context.getELContext());

		attoBean = (AttoBean) context.getApplication().getExpressionFactory()
				.createValueExpression(context.getELContext(), "#{attoBean}", AttoBean.class)
				.getValue(context.getELContext());

		attoSearchBean = (AttoSearchBean) context.getApplication().getExpressionFactory()
				.createValueExpression(context.getELContext(), "#{attoSearchBean}", AttoSearchBean.class)
				.getValue(context.getELContext());

	}

	/**
	 * 
	 * @return
	 */
	public boolean isAmministrazioneEnabled() {

		return (GruppoUtente.SERVIZIO_COMMISSIONI.equals(userBean.getUserGroupName())
				|| GruppoUtente.ADMIN.equals(userBean.getUserGroupName()));
	}

	/**
	 * 
	 * @return
	 */
	public boolean isInsertMISEnabled() {

		return (GruppoUtente.SERVIZIO_COMMISSIONI.equals(userBean.getUserGroupName())
				|| GruppoUtente.ADMIN.equals(userBean.getUserGroupName())
				|| GruppoUtente.AULA.equals(userBean.getUserGroupName()));
	}

	/**
	 * 
	 * @return
	 */
	public boolean isInsertEACEnabled() {

		return (GruppoUtente.SERVIZIO_COMMISSIONI.equals(userBean.getUserGroupName())
				|| GruppoUtente.ADMIN.equals(userBean.getUserGroupName())
				|| GruppoUtente.AULA.equals(userBean.getUserGroupName()));
	}

	/**
	 * 
	 * @return
	 */
	public boolean isInsertEnabled() {

		return (GruppoUtente.SERVIZIO_COMMISSIONI.equals(userBean.getUserGroupName())
				|| GruppoUtente.ADMIN.equals(userBean.getUserGroupName())
				|| GruppoUtente.AULA.equals(userBean.getUserGroupName()));
	}

	/**
	 * 
	 * @return
	 */
	public String getSottoStatoCommissioneConsultiva() {

		Commissione comm = attoBean.getWorkingCommissione(userBean.getUser().getSessionGroup().getNome());

		if (comm != null && comm.getRuolo().equals(Commissione.RUOLO_CONSULTIVA)) {

			return comm.getStato();

		}

		return null;
	}

	/**
	 * 
	 * @return
	 */
	public boolean hasDataRichiestaIscrizioneAula() {
		Commissione comm = attoBean.getWorkingCommissione(userBean.getUser().getSessionGroup().getNome());

		return !(comm.getRuolo().equals(Commissione.RUOLO_CONSULTIVA)
				|| comm.getRuolo().equals(Commissione.RUOLO_DELIBERANTE));
	}

	/**
	 * 
	 * @return
	 */
	public boolean isEACDisabled() {

		return !("ServizioCommissioni".equals(userBean.getUserGroupName())
				|| GruppoUtente.ADMIN.equals(userBean.getUserGroupName()));
	}

	/**
	 * 
	 * @return
	 */
	public boolean isEACDisabledComm() {

		return !("ServizioCommissioni".equals(userBean.getUserGroupName())
				|| GruppoUtente.ADMIN.equals(userBean.getUserGroupName())
				|| userBean.getUser().getSessionGroup().isCommissione());
	}

	/**
	 * 
	 * @return
	 */
	public boolean isMISDisabled() {

		return !(GruppoUtente.CPCV.equals(userBean.getUserGroupName())
				|| GruppoUtente.ADMIN.equals(userBean.getUserGroupName()));

	}

	/**
	 * 
	 * @return
	 */
	public boolean isCPCVUser() {

		return (GruppoUtente.CPCV.equals(userBean.getUserGroupName()));

	}

	/**
	 * 
	 * @return
	 */
	public boolean isInviaDirettamenteAula() {
		return StatoAtto.PRESO_CARICO_SC.equals(attoBean.getStato())
				&& (GruppoUtente.ADMIN.equals(userBean.getUserGroupName())
						|| GruppoUtente.SERVIZIO_COMMISSIONI.equals(userBean.getUserGroupName()));
	}

	/**
	 * 
	 * @return
	 */
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

	/**
	 * 
	 * @return
	 */
	public boolean presentazioneAssegnazioneDisabled() {
		boolean disabled;

		if (!isSessionAttoORG() && !isSessionAttoPDA_UDP()
				&& (GruppoUtente.SERVIZIO_COMMISSIONI.equals(userBean.getUserGroupName())
						|| GruppoUtente.ADMIN.equals(userBean.getUserGroupName()))) {
			disabled = false;
		} else {
			disabled = true;
		}

		return disabled;
	}

	/**
	 * 
	 * @return
	 */
	public boolean esameCommissioniDisabled() {

		boolean disabled;
		if (!isSessionAttoORG() && !isSessionAttoPDA_UDP()
				&& (attoBean.containCommissione(userBean.getUser().getSessionGroup().getNome())
						|| GruppoUtente.ADMIN.equals(userBean.getUserGroupName()))
				&& attoBean.getLastPassaggio().getCommissioni().size() > 0) {
			disabled = false;

		} else {
			disabled = true;
		}

		return disabled;
	}

	/**
	 * 
	 * @return
	 */
	public boolean consultazioniEPareriDisabled() {
		Commissione commissione = attoBean.getWorkingCommissione(userBean.getUser().getSessionGroup().getNome());

		return commissione == null || isSessionAttoPDA_UDP() || isSessionAttoORG();
	}

	/**
	 * 
	 * @return
	 */
	public boolean collegamentiDisabled() {
		return false;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isFirmatariEnabled() {

		if (attoBean.getTipoAtto().equals("PAR") || attoBean.getTipoAtto().equals("INP")
				|| attoBean.getTipoAtto().equals("PRE") || attoBean.getTipoAtto().equals("REL"))

		{
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 
	 * @return
	 */
	public boolean isFirmatariPopolariEnabled() {

		if (("PDL".equals(attoBean.getTipoAtto()) || "PLP".equals(attoBean.getTipoAtto()))
				&& "03_ATTO DI INIZIATIVA POPOLARE".equals(attoBean.getTipoIniziativa()))

		{
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 
	 * @return
	 */
	public boolean organismiEnabled() {

		if (attoBean.getTipoAtto().equals("PDA") || attoBean.getTipoAtto().equals("PLP")
				|| attoBean.getTipoAtto().equals("PRE") || attoBean.getTipoAtto().equals("REF")
				|| attoBean.getTipoAtto().equals("PDL") || attoBean.getTipoAtto().equals("DOC")
				|| attoBean.getTipoAtto().equals("REL"))

		{
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 
	 * @return
	 */
	public boolean emendamentiClausoleEnabled() {

		if (attoBean.getTipoAtto().equals("PDA") || attoBean.getTipoAtto().equals("PLP")
				|| attoBean.getTipoAtto().equals("PRE") || attoBean.getTipoAtto().equals("REF")
				|| attoBean.getTipoAtto().equals("REL") || attoBean.getTipoAtto().equals("PDL")
				|| attoBean.getTipoAtto().equals("DOC"))

		{
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 
	 * @return
	 */
	public boolean isClausoleEnabled() {

		Commissione commissione = attoBean.getWorkingCommissione(userBean.getUser().getSessionGroup().getNome());

		if (attoBean.getTipoAtto().equals("PDL") && (Commissione.RUOLO_REFERENTE.equals(commissione.getRuolo())
				|| Commissione.RUOLO_COREFERENTE.equals(commissione.getRuolo())
				|| Commissione.RUOLO_REDIGENTE.equals(commissione.getRuolo())))

		{
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 
	 * @return
	 */
	public boolean abbinamentiEnabled() {

		if (attoBean.getTipoAtto().equals("PDL") || attoBean.getTipoAtto().equals("PLP"))

		{
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 
	 * @return
	 */
	public boolean esameAulaDisabled() {

		if ((GruppoUtente.AULA.equals(userBean.getUserGroupName())
				|| GruppoUtente.ADMIN.equals(userBean.getUserGroupName()))
				&& !(attoBean.getTipoAtto().equals("PAR") || attoBean.getTipoAtto().equals("REL")
						|| attoBean.getTipoAtto().equals("INP") || attoBean.getTipoAtto().equals("EAC")
						|| attoBean.getTipoAtto().equals("MIS")
						|| (attoBean.getTipoAtto().equalsIgnoreCase("DOC") && !attoBean.getAtto().isIterAula()))

		) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 
	 * @return
	 */
	public boolean boxAulaVisible() {

		if (attoBean.getTipoAtto().equals("PAR") || attoBean.getTipoAtto().equals("REL")
				|| attoBean.getTipoAtto().equals("INP")
				|| (attoBean.getTipoAtto().equalsIgnoreCase("DOC") && !attoBean.getAtto().isIterAula())) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 
	 * @return
	 */
	public boolean emendamentiEnabled() {
		if (isSessionAttoPDL() || isSessionAttoORG() || attoBean.getTipoAtto().equals("PDA")
				|| attoBean.getTipoAtto().equals("PLP") || attoBean.getTipoAtto().equals("PRE")
				|| attoBean.getTipoAtto().equals("DOC") || attoBean.getTipoAtto().equals("REF"))

		{
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 
	 * @return
	 */
	public boolean datiAttoEnabled() {
		return isSessionAttoPDA_UDP() || isSessionAttoORG();
	}

	/**
	 * 
	 * @return
	 */
	public boolean rinvioEStralciEnabled() {
		return (!isSessionAttoDOC() && !isSessionAttoPDA_UDP() && !isSessionAttoORG());
	}

	/**
	 * 
	 * @return
	 */
	public boolean stralciAulaEnabled() {

		if (attoBean.getTipoAtto().equals("PDA") || attoBean.getTipoAtto().equals("PLP")
				|| attoBean.getTipoAtto().equals("PRE") || attoBean.getTipoAtto().equals("REF")

		) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 
	 * @return
	 */
	public boolean pubblicazioneBurlEnabled() {

		if (attoBean.getTipoAtto().equals("INP") || attoBean.getTipoAtto().equals("PAR")
				|| attoBean.getTipoAtto().equals("REL")

		) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 
	 * @return
	 */
	public boolean testoAttoVotatoEnabled() {

		if (attoBean.getTipoAtto().equals("REL") || attoBean.getTipoAtto().equals("INP")

		) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 
	 * @return
	 */
	public boolean isBURLEnabled() {

		if (attoBean.getTipoAtto().equals("REL") || attoBean.getTipoAtto().equals("INP")
				|| "Per decadenza (fine legislatura)".equals(attoBean.getTipoChiusura())
				|| "Respinto dall'Aula".equals(attoBean.getTipoChiusura())
				|| "Ritirato dai promotori".equals(attoBean.getTipoChiusura())
				|| "Abbinato ad altro atto".equals(attoBean.getTipoChiusura())
				|| "Inammissibile".equals(attoBean.getTipoChiusura())
				|| "Istruttoria conclusa".equals(attoBean.getTipoChiusura())
				|| "Irricevibile".equals(attoBean.getTipoChiusura())
				|| "Improcedibile".equals(attoBean.getTipoChiusura())) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 
	 * @return
	 */
	public boolean chiusuraIterDisabled() {

		return false;

	}

	/**
	 * 
	 * @return
	 */
	public boolean isSessionAttoEsitoCommissioneApprovato() {
		boolean risultato = false;
		if (attoBean.getTipoAtto().equalsIgnoreCase("PDL") || attoBean.getTipoAtto().equalsIgnoreCase("PAR")
				|| attoBean.getTipoAtto().equalsIgnoreCase("PDA") || attoBean.getTipoAtto().equalsIgnoreCase("PLP")
				|| attoBean.getTipoAtto().equalsIgnoreCase("PRE") || attoBean.getTipoAtto().equalsIgnoreCase("REF")
				|| (attoBean.getTipoAtto().equalsIgnoreCase("DOC")
						&& attoBean.getTipologia().equalsIgnoreCase("PRS"))) {

			risultato = true;

		}
		return risultato;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSessionAttoEsitoCommissioneArchiviazioneINP() {
		boolean risultato = false;
		if (attoBean.getTipoAtto().equalsIgnoreCase("INP") || (attoBean.getTipoAtto().equalsIgnoreCase("DOC")
				&& !attoBean.getTipologia().equalsIgnoreCase("PRS"))) {
			risultato = true;

		}

		return risultato;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSessionAttoEsitoCommissioneApprovatoREL() {
		boolean risultato = false;
		if (attoBean.getTipoAtto().equalsIgnoreCase("REL")) {
			risultato = true;

		}

		return risultato;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSessionAttoChiuso() {

		return StatoAtto.CHIUSO.equals(attoBean.getStato());
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSessionAttoPARChiuso() {

		boolean isCommissione = userBean.getUser().getSessionGroup().isCommissione();
		return !(isSessionAttoPAR() && isCommissione) && isSessionAttoChiuso();
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSessionAttoPAR() {
		return attoBean.getTipoAtto().equalsIgnoreCase("PAR");
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSessionAttoPDL() {
		return attoBean.getTipoAtto().equalsIgnoreCase("PDL");
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSessionAttoINP() {
		return attoBean.getTipoAtto().equalsIgnoreCase("INP");
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSessionAttoDOC() {
		return attoBean.getTipoAtto().equalsIgnoreCase("DOC");
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSessionAttoPRE() {
		return attoBean.getTipoAtto().equalsIgnoreCase("PRE");
	}

	/**
	 * 
	 * @return
	 */
	public boolean isNotSessionAttoDOCAula() {
		return attoBean.getTipoAtto().equalsIgnoreCase("DOC") && !attoBean.getAtto().isIterAula();
	}

	/**
	 * 
	 * @return
	 */
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

	/**
	 * 
	 * @return
	 */
	public boolean isSessionAttoPDA() {
		return attoBean.getTipoAtto().equalsIgnoreCase("PDA");
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSessionAttoPDA_UDP() {
		boolean res = attoBean.getTipoAtto().equalsIgnoreCase("PDA")
				&& ("05_ATTO DI INIZIATIVA UFFICIO PRESIDENZA".equalsIgnoreCase(attoBean.getTipoIniziativa()));

		return res;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSessionAttoORG() {

		return attoBean.getTipoAtto().equalsIgnoreCase("ORG");

	}

	/**
	 * 
	 * @return
	 */
	public boolean isSessionAttoPLP() {
		return attoBean.getTipoAtto().equalsIgnoreCase("PLP");
	}

	/**
	 * 
	 * @return
	 */
	public boolean isGestioneSeduteEnabled() {

		return userBean.getUser().getSessionGroup().isCommissione()
				|| GruppoUtente.AULA.equals(userBean.getUser().getSessionGroup().getNome());
	}

	/**
	 * 
	 * @return
	 */
	public boolean gestioneSeduteConsultazioniCommissione() {

		return userBean.getUser().getSessionGroup().isCommissione();
	}

	/**
	 * 
	 * @return
	 */
	public boolean gestioneSeduteConsultazioniAula() {
		return GruppoUtente.AULA.equals(userBean.getUser().getSessionGroup().getNome());
	}

	/**
	 * 
	 * @return
	 */
	public boolean isCommissioneUpdateEnabled() {

		Commissione commissione = attoBean.getWorkingCommissione(userBean.getUser().getSessionGroup().getNome());

		if (commissione != null && (Commissione.RUOLO_REFERENTE.equals(commissione.getRuolo())
				|| Commissione.RUOLO_DELIBERANTE.equals(commissione.getRuolo())
				|| Commissione.RUOLO_REDIGENTE.equals(commissione.getRuolo())
				|| Commissione.RUOLO_COREFERENTE.equals(commissione.getRuolo()))) {
			return true;
		}

		return false;

	}

	/**
	 * 
	 * @return
	 */
	public boolean isCommissioneReferente() {
		Commissione commissione = attoBean.getWorkingCommissione(userBean.getUser().getSessionGroup().getNome());

		if (commissione != null && Commissione.RUOLO_REFERENTE.equals(commissione.getRuolo())) {
			return true;
		}

		return false;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isCommissioneConsultiva() {
		Commissione commissione = attoBean.getWorkingCommissione(userBean.getUser().getSessionGroup().getNome());

		if (commissione != null && Commissione.RUOLO_CONSULTIVA.equals(commissione.getRuolo())) {
			return true;
		}

		return false;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isCommissioneDeliberante() {
		Commissione commissione = attoBean.getWorkingCommissione(userBean.getUser().getSessionGroup().getNome());

		if (commissione != null && Commissione.RUOLO_DELIBERANTE.equals(commissione.getRuolo())) {
			return true;
		}

		return false;
	}

	/**
	 * 
	 * @return
	 */
	public boolean hasCommissioneDeliberante() {

		return attoBean.getCommissioneDeliberante() != null;

	}

	/**
	 * 
	 * @return
	 */
	public boolean isCalendarizzazioneTipo() {

		if (attoBean.getTipoAtto().equalsIgnoreCase("PDL") || attoBean.getTipoAtto().equalsIgnoreCase("PDA")
				|| attoBean.getTipoAtto().equalsIgnoreCase("PLP") || attoBean.getTipoAtto().equalsIgnoreCase("PRE")
				|| attoBean.getTipoAtto().equalsIgnoreCase("REF") || attoBean.getTipoAtto().equalsIgnoreCase("REL")
				|| (attoBean.getTipoAtto().equalsIgnoreCase("DOC") && !attoBean.getAtto().isIterAula())

		) {
			return true;
		}

		return false;

	}

	/**
	 * 
	 * @return
	 */
	public boolean isRisEnabled() {

		Commissione commissione = attoBean.getWorkingCommissione(userBean.getUser().getSessionGroup().getNome());

		if (commissione != null
				&& (Commissione.RUOLO_REFERENTE.equals(commissione.getRuolo())
						|| Commissione.RUOLO_COREFERENTE.equals(commissione.getRuolo())
						|| Commissione.RUOLO_REDIGENTE.equals(commissione.getRuolo())
						|| Commissione.RUOLO_DELIBERANTE.equals(commissione.getRuolo()))
				&& (attoBean.getTipoAtto().equalsIgnoreCase("INP") || attoBean.getTipoAtto().equalsIgnoreCase("DOC")
						|| attoBean.getTipoAtto().equalsIgnoreCase("REL"))

		) {
			return true;
		}

		return false;

	}

	/**
	 * 
	 * @return
	 */
	public boolean isRisTipo() {

		if (attoBean.getTipoAtto().equalsIgnoreCase("INP") || attoBean.getTipoAtto().equalsIgnoreCase("DOC")
				|| attoBean.getTipoAtto().equalsIgnoreCase("REL")) {
			return true;
		}

		return false;

	}

	/**
	 * 
	 * @return
	 */
	public boolean isCalendarizzazioneEnabled() {

		Commissione commissione = attoBean.getWorkingCommissione(userBean.getUser().getSessionGroup().getNome());

		if (commissione != null
				&& (Commissione.RUOLO_REFERENTE.equals(commissione.getRuolo())
						|| Commissione.RUOLO_COREFERENTE.equals(commissione.getRuolo())
						|| Commissione.RUOLO_REDIGENTE.equals(commissione.getRuolo()))
				&& (attoBean.getTipoAtto().equalsIgnoreCase("PDL") || attoBean.getTipoAtto().equalsIgnoreCase("PDA")
						|| attoBean.getTipoAtto().equalsIgnoreCase("PLP")
						|| attoBean.getTipoAtto().equalsIgnoreCase("PRE")
						|| attoBean.getTipoAtto().equalsIgnoreCase("REF")
						|| attoBean.getTipoAtto().equalsIgnoreCase("REL")
						|| (attoBean.getTipoAtto().equalsIgnoreCase("DOC") && !attoBean.getAtto().isIterAula()))

		) {
			return true;
		}

		return false;

	}

	/**
	 * 
	 * @return
	 */
	public boolean isContinuazioneLavoriReferente() {
		Commissione commissione = attoBean.getWorkingCommissione(userBean.getUser().getSessionGroup().getNome());

		if (commissione != null && Commissione.RUOLO_DELIBERANTE.equals(commissione.getRuolo())
				|| (commissione.getMotivazioniContinuazioneInReferente() != null
						&& !"".equals(commissione.getMotivazioniContinuazioneInReferente()))
				|| commissione.getDataSedutaContinuazioneInReferente() != null) {
			return true;
		}

		return false;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isServizioCommissioni() {

		return GruppoUtente.SERVIZIO_COMMISSIONI.equals(userBean.getUserGroupName());

	}

	/**
	 * 
	 * @return
	 */
	public boolean isGuest() {

		if (userBean.getUser().getSessionGroup().getNome().equals(GruppoUtente.GUEST)) {
			return true;
		}

		return false;

	}

}
