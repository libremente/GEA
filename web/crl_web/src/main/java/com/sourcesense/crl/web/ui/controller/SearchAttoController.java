/*
 * Copyright Shragger sarl. All Rights Reserved.
 *
 *
 * The copyright to the computer program(s) herein is the property of
 * Shragger sarl., France. The program(s) may be used and/or copied only
 * with the written permission of Shragger sarl.. or in accordance with the
 * terms and conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied. This copyright notice must not be removed.
 */
package com.sourcesense.crl.web.ui.controller;

import com.sourcesense.crl.business.service.AttoServiceManager;
import com.sourcesense.crl.business.service.CommissioneServiceManager;
import com.sourcesense.crl.business.service.LegislaturaServiceManager;
import com.sourcesense.crl.business.service.OrganismoStatutarioServiceManager;
import com.sourcesense.crl.business.service.PersonaleServiceManager;
import com.sourcesense.crl.business.service.StatoAttoServiceManager;
import com.sourcesense.crl.business.service.TipoAttoServiceManager;
import com.sourcesense.crl.business.service.TipoChiusuraServiceManager;
import com.sourcesense.crl.business.service.TipoIniziativaServiceManager;
import com.sourcesense.crl.business.service.VotazioneServiceManager;
import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.model.AttoSearch;
import com.sourcesense.crl.business.model.Commissione;
import com.sourcesense.crl.util.LazyAttoDataModel;
import com.sourcesense.crl.web.ui.beans.AttoBean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 * 
 * @author uji
 */

@ViewScoped
@ManagedBean(name = "searchAttoController")
public class SearchAttoController {

	@ManagedProperty(value = "#{attoServiceManager}")
	private AttoServiceManager attoServiceManager;

	@ManagedProperty(value = "#{legislaturaServiceManager}")
	private LegislaturaServiceManager legislaturaServiceManager;

	@ManagedProperty(value = "#{tipoAttoServiceManager}")
	private TipoAttoServiceManager tipoAttoServiceManager;

	@ManagedProperty(value = "#{statoAttoServiceManager}")
	private StatoAttoServiceManager statoAttoServiceManager;

	@ManagedProperty(value = "#{tipoIniziativaServiceManager}")
	private TipoIniziativaServiceManager tipoIniziativaServiceManager;

	@ManagedProperty(value = "#{personaleServiceManager}")
	private PersonaleServiceManager personaleServiceManager;

	@ManagedProperty(value = "#{tipoChiusuraServiceManager}")
	private TipoChiusuraServiceManager tipoChiusuraServiceManager;

	@ManagedProperty(value = "#{votazioneServiceManager}")
	private VotazioneServiceManager votazioneServiceManager;

	@ManagedProperty(value = "#{commissioneServiceManager}")
	private CommissioneServiceManager commissioneServiceManager;

	@ManagedProperty(value = "#{organismoStatutarioServiceManager}")
	private OrganismoStatutarioServiceManager organismoStatutarioServiceManager;

	private LazyDataModel<Atto> lazyAttoModel;

	private List<Atto> listAtti;

	private String numeroAtto;

	private Date dataIniziativaDa;

	private Date dataIniziativaA;

	private String tipoatto;

	private String legislatura;

	private String stato;

	private String numeroprotocollo;

	private String tipoiniziativa;

	private String numerodcr;

	private String primofirmatario;

	private String oggetto;

	private String firmatario;

	private String tipoChiusura;

	private String esitoVotoCommissioneReferente;

	private String esitoVotoAula;

	private String commissioneReferente;

	private String commissioneConsultiva;

	private boolean redigente;

	private boolean deliberante;

	private String numeroLCR;

	private String numeroLR;

	private String anno;

	private boolean abbinamento;

	private boolean stralcio;

	private Date dataPubblicazioneDa;

	private Date dataPubblicazioneA;

	private Date dataSedutaSCDa;

	private Date dataSedutaSCA;

	private Date dataSedutaCommissioneDa;

	private Date dataSedutaCommissioneA;

	private Date dataSedutaAulaDa;

	private Date dataSedutaAulaA;

	private String relatore;

	private String organismoStatutario;

	private String soggettoConsultato;

	private boolean emendato;

	private boolean rinviato;

	private boolean sospeso;

	private AttoSearch atto = new AttoSearch();

	private Map<String, String> tipiAtto = new HashMap<String, String>();

	private Map<String, String> legislature = new HashMap<String, String>();

	private Map<String, String> stati = new HashMap<String, String>();

	private Map<String, String> tipiIniziative = new HashMap<String, String>();

	private Map<String, String> firmatari = new HashMap<String, String>();

	private Map<String, String> tipiChiusura = new HashMap<String, String>();

	private Map<String, String> esitiVotoCommissioneReferente = new HashMap<String, String>();

	private Map<String, String> esitiVotoAula = new HashMap<String, String>();

	private Map<String, String> commissioniReferenti = new HashMap<String, String>();

	private Map<String, String> commissioniConsultive = new HashMap<String, String>();

	private Map<String, String> relatori = new HashMap<String, String>();

	private Map<String, String> organismiStatutari = new HashMap<String, String>();

	public void searchAtti() {

		setListAtti(attoServiceManager.searchAtti(atto));

		lazyAttoModel = new LazyAttoDataModel(listAtti);
	}

	@PostConstruct
	protected void initLazyModel() {

		setCommissioniReferenti(commissioneServiceManager
				.findAllCommissioneReferente());
		setCommissioniConsultive(commissioneServiceManager
				.findAllCommissioneConsultiva());
		setOrganismiStatutari(organismoStatutarioServiceManager.findAll());
		setFirmatari(personaleServiceManager.findAllFirmatario());
		setRelatori(personaleServiceManager.findAllRelatore());
		setStati(statoAttoServiceManager.findAll());
		setTipiChiusura(tipoChiusuraServiceManager.findAll());
		setTipiIniziative(tipoIniziativaServiceManager.findAll());

		// TODO:
		// setEsitiVotoAula(votazioneServiceManager.findAllEsitoVotoAula());
		// setEsitoVotoCommissioneReferente(votazioneServiceManager.findAllEsitoVotoCommissioneReferente());

		setTipiAtto(tipoAttoServiceManager.findAll());
		setLegislature(legislaturaServiceManager.findAll());

		setListAtti(attoServiceManager.initListAtti());

		setLazyAttoModel(new LazyAttoDataModel(listAtti));
	}

	/**
	 * @return the lazyAttoModel
	 */
	public LazyDataModel<Atto> getLazyAttoModel() {
		return lazyAttoModel;
	}

	/**
	 * @param lazyAttoModel
	 *            the lazyAttoModel to set
	 */
	public void setLazyAttoModel(LazyDataModel<Atto> lazyAttoModel) {
		this.lazyAttoModel = lazyAttoModel;
	}

	/**
     */
	// public void searchLazyAttoModel() {
	//
	//
	//
	// lazyAttoModel = new LazyDataModel<Atto>() {
	//
	// @Override
	// public List<Atto> load(int first, int pageSize, String sortField,
	// SortOrder sortOrder, Map<String, String> filters) {
	// return null;
	// }
	//
	// @Override
	// public int getRowCount() {
	// return (int) attoServiceManager.count();
	// }
	// };
	//
	// }

	public String attoDetail(String idAttoParam) {

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = (AttoBean) context
				.getApplication()
				.getExpressionFactory()
				.createValueExpression(context.getELContext(), "#{attoBean}",
						AttoBean.class).getValue(context.getELContext());

		// TODO Ottenere dettaglio atto da Alfresco
		attoBean.setAtto(attoServiceManager.findById(idAttoParam));
		// TODO riempire liste firmatari
		attoBean.getAtto().setFirmatari(personaleServiceManager.findFirmatariByAtto(attoBean.getAtto()));

		// TODO riempire liste abbinamenti

		// TODO riempire liste testi
		attoBean.getAtto().setTestiAtto(
				attoServiceManager.testiAttoByAtto(attoBean.getAtto()));
		// TODO riempire liste allegati
		attoBean.getAtto().setAllegati(
				attoServiceManager.allAllegatiAttoByAtto(attoBean.getAtto()));
		// TODO riempire liste organi interni

		// TODO riempire liste altri organi
		
		// TODO riempire liste membri comitato ristretto
		
		return "pretty:Riepilogo_Atto";

	}

	public String getNumeroAtto() {
		return this.atto.getNumeroAtto();
	}

	public void setNumeroAtto(String numeroAtto) {
		this.atto.setNumeroAtto(numeroAtto);
	}

	public Date getDataIniziativaDa() {
		return this.atto.getDataIniziativaDa();
	}

	public void setDataIniziativaDa(Date dataIniziativaDa) {
		this.atto.setDataIniziativaDa(dataIniziativaDa);
	}

	public Date getDataIniziativaA() {
		return atto.getDataIniziativaA();
	}

	public void setDataIniziativaA(Date dataIniziativaA) {
		this.atto.setDataIniziativaA(dataIniziativaA);
	}

	public String getTipoatto() {
		return this.atto.getTipoAtto();
	}

	public void setTipoatto(String tipoatto) {
		this.atto.setTipoAtto(tipoatto);
	}

	public String getLegislatura() {
		return this.atto.getLegislatura();
	}

	public void setLegislatura(String legislatura) {
		this.atto.setLegislatura(legislatura);
	}

	public String getStato() {
		return this.atto.getStato();
	}

	public void setStato(String stato) {
		this.atto.setStato(stato);
	}

	public String getNumeroprotocollo() {
		return this.atto.getNumeroProtocollo();
	}

	public void setNumeroprotocollo(String numeroprotocollo) {
		this.atto.setNumeroProtocollo(numeroprotocollo);
	}

	public String getTipoiniziativa() {
		return this.atto.getTipoIniziativa();
	}

	public void setTipoiniziativa(String tipoiniziativa) {
		this.atto.setTipoIniziativa(tipoiniziativa);
	}

	public String getNumerodcr() {
		return atto.getNumeroDcr();
	}

	public void setNumerodcr(String numerodcr) {
		this.atto.setNumeroDcr(numerodcr);
	}

	public String getPrimofirmatario() {
		return this.atto.getPrimoFirmatario();
	}

	public void setPrimofirmatario(String primofirmatario) {
		this.atto.setPrimoFirmatario(primofirmatario);
	}

	public String getOggetto() {
		return this.atto.getOggetto();
	}

	public void setOggetto(String oggetto) {
		this.atto.setOggetto(oggetto);
	}

	public String getFirmatario() {
		return this.atto.getFirmatario();
	}

	public void setFirmatario(String firmatario) {
		this.atto.setFirmatario(firmatario);
	}

	public AttoServiceManager getAttoServiceManager() {
		return attoServiceManager;
	}

	public void setAttoServiceManager(AttoServiceManager attoServiceManager) {
		this.attoServiceManager = attoServiceManager;
	}

	public AttoSearch getAtto() {
		return atto;
	}

	public void setAtto(AttoSearch atto) {
		this.atto = atto;
	}

	public String getTipoChiusura() {
		return atto.getTipoChiusura();
	}

	public void setTipoChiusura(String tipoChiusura) {
		this.atto.setTipoChiusura(tipoChiusura);
	}

	public String getEsitoVotoCommissioneReferente() {
		return this.atto.getEsitoVotoCommissioneReferente();
	}

	public void setEsitoVotoCommissioneReferente(
			String esitoVotoCommissioneReferente) {
		this.atto
				.setEsitoVotoCommissioneReferente(esitoVotoCommissioneReferente);
	}

	public String getEsitoVotoAula() {
		return this.atto.getEsitoVotoAula();
	}

	public void setEsitoVotoAula(String esitoVotoAula) {
		this.atto.setEsitoVotoAula(esitoVotoAula);
	}

	public String getCommissioneReferente() {
		return this.atto.getCommissioneReferente();
	}

	public void setCommissioneReferente(String commissioneReferente) {
		this.atto.setCommissioneReferente(commissioneReferente);
	}

	public String getCommissioneConsultiva() {
		return this.atto.getCommissioneConsultiva();
	}

	public void setCommissioneConsultiva(String commissioneConsultiva) {
		this.atto.setCommissioneConsultiva(commissioneConsultiva);
	}

	public boolean isRedigente() {
		return this.atto.isRedigente();
	}

	public void setRedigente(boolean redigente) {
		this.atto.setRedigente(redigente);
	}

	public boolean isDeliberante() {
		return this.atto.isDeliberante();
	}

	public void setDeliberante(boolean deliberante) {
		this.atto.setDeliberante(deliberante);
	}

	public String getNumeroLCR() {
		return this.atto.getNumeroLcr();
	}

	public void setNumeroLCR(String numeroLCR) {
		this.atto.setNumeroLcr(numeroLCR);
	}

	public String getNumeroLR() {
		return this.atto.getNumeroLr();
	}

	public void setNumeroLR(String numeroLR) {
		this.atto.setNumeroLr(numeroLR);
	}

	public String getAnno() {
		return this.atto.getAnno();
	}

	public void setAnno(String anno) {
		this.atto.setAnno(anno);
	}

	public boolean isAbbinamento() {
		return this.atto.isAbbinamento();
	}

	public void setAbbinamento(boolean abbinamento) {
		this.atto.setAbbinamento(abbinamento);
	}

	public boolean isStralcio() {
		return this.atto.isStralcio();
	}

	public void setStralcio(boolean stralcio) {
		this.atto.setStralcio(stralcio);
	}

	public Date getDataPubblicazioneDa() {
		return this.atto.getDataPubblicazioneDa();
	}

	public void setDataPubblicazioneDa(Date dataPubblicazioneDa) {
		this.atto.setDataPubblicazioneDa(dataPubblicazioneDa);
	}

	public Date getDataPubblicazioneA() {
		return this.atto.getDataPubblicazioneA();
	}

	public void setDataPubblicazioneA(Date dataPubblicazioneA) {
		this.atto.setDataPubblicazioneA(dataPubblicazioneA);
	}

	public Date getDataSedutaSCDa() {
		return this.atto.getDataSedutaSCDa();
	}

	public void setDataSedutaSCDa(Date dataSedutaSCDa) {
		this.atto.setDataSedutaSCDa(dataSedutaSCDa);
	}

	public Date getDataSedutaSCA() {
		return this.atto.getDataSedutaSCA();
	}

	public void setDataSedutaSCA(Date dataSedutaSCA) {
		this.atto.setDataSedutaSCA(dataSedutaSCA);
	}

	public Date getDataSedutaCommissioneDa() {
		return this.atto.getDataSedutaCommissioneDa();
	}

	public void setDataSedutaCommissioneDa(Date dataSedutaCommissioneDa) {
		this.atto.setDataSedutaCommissioneDa(dataSedutaCommissioneDa);
	}

	public Date getDataSedutaCommissioneA() {
		return this.atto.getDataSedutaCommissioneA();
	}

	public void setDataSedutaCommissioneA(Date dataSedutaCommissioneA) {
		this.atto.setDataSedutaCommissioneA(dataSedutaCommissioneA);
	}

	public Date getDataSedutaAulaDa() {
		return atto.getDataSedutaAulaDa();
	}

	public void setDataSedutaAulaDa(Date dataSedutaAulaDa) {
		this.atto.setDataSedutaAulaDa(dataSedutaAulaDa);
	}

	public Date getDataSedutaAulaA() {
		return this.atto.getDataSedutaAulaA();
	}

	public void setDataSedutaAulaA(Date dataSedutaAulaA) {
		this.atto.setDataSedutaAulaA(dataSedutaAulaA);
	}

	public String getRelatore() {
		return this.atto.getRelatore();
	}

	public void setRelatore(String relatore) {
		this.atto.setRelatore(relatore);
	}

	public String getOrganismoStatutario() {
		return this.atto.getOrganismoStatutario();
	}

	public void setOrganismoStatutario(String organismoStatutario) {
		this.atto.setOrganismoStatutario(organismoStatutario);
	}

	public String getSoggettoConsultato() {
		return this.atto.getSoggettoConsultato();
	}

	public void setSoggettoConsultato(String soggettoConsultato) {
		this.atto.setSoggettoConsultato(soggettoConsultato);
	}

	public boolean isEmendato() {
		return this.atto.isEmendato();
	}

	public void setEmendato(boolean emendato) {
		this.atto.setEmendato(emendato);
	}

	public boolean isRinviato() {
		return this.atto.isRinviato();
	}

	public void setRinviato(boolean rinviato) {
		this.atto.setRinviato(rinviato);
	}

	public boolean isSospeso() {
		return this.atto.isSospeso();
	}

	public void setSospeso(boolean sospeso) {
		this.atto.setSospeso(sospeso);
	}

	public Map<String, String> getTipiAtto() {
		return tipiAtto;
	}

	public void setTipiAtto(Map<String, String> tipiAtto) {
		this.tipiAtto = tipiAtto;
	}

	public Map<String, String> getLegislature() {
		return legislature;
	}

	public void setLegislature(Map<String, String> legislature) {
		this.legislature = legislature;
	}

	public Map<String, String> getStati() {
		return stati;
	}

	public void setStati(Map<String, String> stati) {
		this.stati = stati;
	}

	public Map<String, String> getTipiIniziative() {
		return tipiIniziative;
	}

	public void setTipiIniziative(Map<String, String> tipiIniziative) {
		this.tipiIniziative = tipiIniziative;
	}

	public Map<String, String> getFirmatari() {
		return firmatari;
	}

	public void setFirmatari(Map<String, String> firmatari) {
		this.firmatari = firmatari;
	}

	public Map<String, String> getTipiChiusura() {
		return tipiChiusura;
	}

	public void setTipiChiusura(Map<String, String> tipiChiusura) {
		this.tipiChiusura = tipiChiusura;
	}

	public Map<String, String> getEsitiVotoCommissioneReferente() {
		return esitiVotoCommissioneReferente;
	}

	public void setEsitiVotoCommissioneReferente(
			Map<String, String> esitiVotoCommissioneReferente) {
		this.esitiVotoCommissioneReferente = esitiVotoCommissioneReferente;
	}

	public Map<String, String> getEsitiVotoAula() {
		return esitiVotoAula;
	}

	public void setEsitiVotoAula(Map<String, String> esitiVotoAula) {
		this.esitiVotoAula = esitiVotoAula;
	}

	public Map<String, String> getCommissioniReferenti() {
		return commissioniReferenti;
	}

	public void setCommissioniReferenti(Map<String, String> commissioniReferenti) {
		this.commissioniReferenti = commissioniReferenti;
	}

	public Map<String, String> getCommissioniConsultive() {
		return commissioniConsultive;
	}

	public void setCommissioniConsultive(
			Map<String, String> commissioniConsultive) {
		this.commissioniConsultive = commissioniConsultive;
	}

	public Map<String, String> getRelatori() {
		return relatori;
	}

	public void setRelatori(Map<String, String> relatori) {
		this.relatori = relatori;
	}

	public Map<String, String> getOrganismiStatutari() {
		return organismiStatutari;
	}

	public void setOrganismiStatutari(Map<String, String> organismiStatutari) {
		this.organismiStatutari = organismiStatutari;
	}

	public LegislaturaServiceManager getLegislaturaServiceManager() {
		return legislaturaServiceManager;
	}

	public void setLegislaturaServiceManager(
			LegislaturaServiceManager legislaturaServiceManager) {
		this.legislaturaServiceManager = legislaturaServiceManager;
	}

	public TipoAttoServiceManager getTipoAttoServiceManager() {
		return tipoAttoServiceManager;
	}

	public void setTipoAttoServiceManager(
			TipoAttoServiceManager tipoAttoServiceManager) {
		this.tipoAttoServiceManager = tipoAttoServiceManager;
	}

	public StatoAttoServiceManager getStatoAttoServiceManager() {
		return statoAttoServiceManager;
	}

	public void setStatoAttoServiceManager(
			StatoAttoServiceManager statoAttoServiceManager) {
		this.statoAttoServiceManager = statoAttoServiceManager;
	}

	public TipoIniziativaServiceManager getTipoIniziativaServiceManager() {
		return tipoIniziativaServiceManager;
	}

	public void setTipoIniziativaServiceManager(
			TipoIniziativaServiceManager tipoIniziativaServiceManager) {
		this.tipoIniziativaServiceManager = tipoIniziativaServiceManager;
	}

	public TipoChiusuraServiceManager getTipoChiusuraServiceManager() {
		return tipoChiusuraServiceManager;
	}

	public void setTipoChiusuraServiceManager(
			TipoChiusuraServiceManager tipoChiusuraServiceManager) {
		this.tipoChiusuraServiceManager = tipoChiusuraServiceManager;
	}

	public OrganismoStatutarioServiceManager getOrganismoStatutarioServiceManager() {
		return organismoStatutarioServiceManager;
	}

	public void setOrganismoStatutarioServiceManager(
			OrganismoStatutarioServiceManager organismoStatutarioServiceManager) {
		this.organismoStatutarioServiceManager = organismoStatutarioServiceManager;
	}

	public PersonaleServiceManager getPersonaleServiceManager() {
		return personaleServiceManager;
	}

	public void setPersonaleServiceManager(
			PersonaleServiceManager personaleServiceManager) {
		this.personaleServiceManager = personaleServiceManager;
	}

	public VotazioneServiceManager getVotazioneServiceManager() {
		return votazioneServiceManager;
	}

	public void setVotazioneServiceManager(
			VotazioneServiceManager votazioneServiceManager) {
		this.votazioneServiceManager = votazioneServiceManager;
	}

	public CommissioneServiceManager getCommissioneServiceManager() {
		return commissioneServiceManager;
	}

	public void setCommissioneServiceManager(
			CommissioneServiceManager commissioneServiceManager) {
		this.commissioneServiceManager = commissioneServiceManager;
	}

	public List<Atto> getListAtti() {
		return listAtti;
	}

	public void setListAtti(List<Atto> listAtti) {
		this.listAtti = listAtti;
	}

}
