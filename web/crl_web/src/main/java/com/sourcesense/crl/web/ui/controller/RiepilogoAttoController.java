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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.sourcesense.crl.business.model.Abbinamento;
import com.sourcesense.crl.business.model.Allegato;
import com.sourcesense.crl.business.model.Commissione;
import com.sourcesense.crl.business.model.Organo;
import com.sourcesense.crl.business.model.Relatore;
import com.sourcesense.crl.business.service.AttoServiceManager;
import com.sourcesense.crl.business.service.CommissioneServiceManager;
import com.sourcesense.crl.util.Clonator;
import com.sourcesense.crl.web.ui.beans.AttoBean;

/**
 * 
 * 
 * @author sourcesense
 *
 */
@ManagedBean(name = "riepilogoAttoController")
@ViewScoped
public class RiepilogoAttoController {

	@ManagedProperty(value = "#{commissioneServiceManager}")
	private CommissioneServiceManager commissioneServiceManager;

	@ManagedProperty(value = "#{attoServiceManager}")
	private AttoServiceManager attoServiceManager;

	AttoBean attoBean;

	private List<Allegato> testiUfficiali;
	private List<Allegato> altriAllegati;
	private List<Organo> organiInterni;
	private List<Organo> altriOrgani;
	private List<Commissione> commissioni = new ArrayList<Commissione>();
	private String nomeCommissione;
	private String commissioneSelectedName;

	private List<Abbinamento> abbinamentiList = new ArrayList<Abbinamento>();
	private List<Relatore> relatoriAttivi = new ArrayList<Relatore>();
	private List<Relatore> relatoriList = new ArrayList<Relatore>();

	private String tipoIniziativa;

	private Commissione commissioneSelected = new Commissione();

	@PostConstruct
	protected void init() {

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = ((AttoBean) context.getExternalContext().getSessionMap().get("attoBean"));

		initTipoIniziativa(attoBean.getAtto().getTipoIniziativa());

		commissioni = Clonator.cloneList(attoBean.getLastPassaggio().getCommissioni());

		if (!commissioni.isEmpty()) {
			commissioneSelected = commissioni.get(0);
		}

		abbinamentiList = Clonator.cloneList(attoBean.getLastPassaggio().getAbbinamenti());
		relatoriList = Clonator.cloneList(commissioneSelected.getRelatori());
		filtraRelatori();

		trasmissioneAdAulaAbilited();

	}

	private void initTipoIniziativa(String tipoIniziativa) {

		if ("01_ATTO DI INIZIATIVA CONSILIARE".equals(tipoIniziativa)) {

			setTipoIniziativa("Consiliare");

		} else if ("03_ATTO DI INIZIATIVA POPOLARE".equals(tipoIniziativa)) {

			setTipoIniziativa("Popolare");

		} else if ("05_ATTO DI INIZIATIVA UFFICIO PRESIDENZA".equals(tipoIniziativa)) {

			setTipoIniziativa("Ufficio di Presidenza");

		} else if ("07_ATTO DI INIZIATIVA AUTONOMIE LOCALI".equals(tipoIniziativa)) {

			setTipoIniziativa("Consiglio delle Autonomie locali");

		} else if ("06_ATTO DI INIZIATIVA PRESIDENTE GIUNTA".equals(tipoIniziativa)) {

			setTipoIniziativa("Presidente della Giunta");

		} else if ("02_ATTO DI INIZIATIVA DI GIUNTA".equals(tipoIniziativa)) {

			setTipoIniziativa("Giunta");

		} else if ("04_ATTO DI INIZIATIVA COMMISSIONI".equals(tipoIniziativa)) {

			setTipoIniziativa("Commissioni");

		} else if ("08_ATTO DI ALTRA INIZIATIVA".equals(tipoIniziativa)) {

			setTipoIniziativa("Altra Iniziativa");

		}

	}

	public void showCommissioneDetail() {

		for (Commissione commissioneRec : commissioni) {

			if (commissioneRec.getDescrizione().equals(nomeCommissione)) {

				setCommissioneSelected((Commissione) commissioneRec.clone());
				break;
			}

		}

	}

	public String getDataTrasmissioneLabel() {
		return (Commissione.RUOLO_CONSULTIVA.equals(commissioneSelected.getRuolo()))
				? "Data trasmissione a Comm. referente:"
				: "Data Trasmissione Aula:";
	}

	public String getDataTrasmissioneLabelByRuolo(String ruolo) {
		return (Commissione.RUOLO_CONSULTIVA.equals(ruolo)) ? "Data trasmissione a Comm. referente:"
				: "Data Trasmissione Aula:";
	}

	public void filtraRelatori() {

		for (Relatore element : relatoriList) {

			if (element.getDataUscita() == null) {

				relatoriAttivi.add(element);
			}
		}
	}

	public boolean trasmissioneAdAulaAbilited() {

		FacesContext context = FacesContext.getCurrentInstance();
		attoBean = (AttoBean) context.getApplication().getExpressionFactory()
				.createValueExpression(context.getELContext(), "#{attoBean}", AttoBean.class)
				.getValue(context.getELContext());

		if (attoBean.getTipoAtto().equals("REF") || attoBean.getTipoAtto().equals("PDA")
				|| attoBean.getTipoAtto().equals("PDL") || attoBean.getTipoAtto().equals("PRE")
				|| attoBean.getTipoAtto().equals("PLP"))

		{
			return true;
		} else {
			return false;
		}

	}

	public List<Allegato> getTestiUfficiali() {
		return testiUfficiali;
	}

	public void setTestiUfficiali(List<Allegato> testiUfficiali) {
		this.testiUfficiali = testiUfficiali;
	}

	public List<Allegato> getAltriAllegati() {
		return altriAllegati;
	}

	public void setAltriAllegati(List<Allegato> altriAllegati) {
		this.altriAllegati = altriAllegati;
	}

	public List<Organo> getOrganiInterni() {
		return organiInterni;
	}

	public void setOrganiInterni(List<Organo> organiInterni) {
		this.organiInterni = organiInterni;
	}

	public List<Organo> getAltriOrgani() {
		return altriOrgani;
	}

	public void setAltriOrgani(List<Organo> altriOrgani) {
		this.altriOrgani = altriOrgani;
	}

	public String getNomeCommissione() {
		return nomeCommissione;
	}

	public void setNomeCommissione(String nomeCommissione) {
		this.nomeCommissione = nomeCommissione;
	}

	public CommissioneServiceManager getCommissioneServiceManager() {
		return commissioneServiceManager;
	}

	public void setCommissioneServiceManager(CommissioneServiceManager commissioneServiceManager) {
		this.commissioneServiceManager = commissioneServiceManager;
	}

	public String getCommissioneSelectedName() {
		return this.commissioneSelected.getClass().getSimpleName();
	}

	public void setCommissioneSelectedName(String commissioneSelectedName) {
		this.commissioneSelectedName = commissioneSelectedName;
	}

	public Commissione getCommissioneSelected() {
		return commissioneSelected;
	}

	public void setCommissioneSelected(Commissione commissioneSelected) {
		this.commissioneSelected = commissioneSelected;
	}

	public AttoServiceManager getAttoServiceManager() {
		return attoServiceManager;
	}

	public void setAttoServiceManager(AttoServiceManager attoServiceManager) {
		this.attoServiceManager = attoServiceManager;
	}

	public List<Relatore> getRelatoriAttivi() {
		return relatoriAttivi;
	}

	public void setRelatoriAttivi(List<Relatore> relatoriAttivi) {
		this.relatoriAttivi = relatoriAttivi;
	}

	public List<Commissione> getCommissioni() {
		return commissioni;
	}

	public void setCommissioni(List<Commissione> commissioni) {
		this.commissioni = commissioni;
	}

	public String getTipoIniziativa() {

		return tipoIniziativa;
	}

	public void setTipoIniziativa(String tipoIniziativa) {
		this.tipoIniziativa = tipoIniziativa;
	}

}
