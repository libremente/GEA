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

import com.sourcesense.crl.business.service.AttoRecordServiceManager;
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
import com.sourcesense.crl.business.model.AttoEAC;
import com.sourcesense.crl.business.model.AttoMIS;
import com.sourcesense.crl.business.model.AttoSearch;
import com.sourcesense.crl.business.model.ColonnaAtto;
import com.sourcesense.crl.business.model.Commissione;
import com.sourcesense.crl.business.model.GruppoUtente;
import com.sourcesense.crl.business.model.Relatore;
import com.sourcesense.crl.business.model.StatoAtto;
import com.sourcesense.crl.util.LazyAttoDataModel;
import com.sourcesense.crl.web.ui.beans.AttoBean;
import com.sourcesense.crl.web.ui.beans.UserBean;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
import javax.swing.plaf.ListUI;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 * 
 * @author uji
 */

@ViewScoped
@ManagedBean(name = "searchAttoController")
public class SearchAttoController implements Serializable{

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

	@ManagedProperty(value = "#{attoRecordServiceManager}")
	private AttoRecordServiceManager attoRecordServiceManager;

	private String listaLavoro;

	private String idAttoSelected;
	private String tipoAttoSelected;

	private String numeroDGR;
	private String numeroDcr;
	private Date dataDGR;
	private Date dataLR;
	private Date dataAssegnazioneA;
	private Date dataAssegnazioneDa;
	private Date dataChiusuraDa;
	private Date dataChiusuraA;
	private String commissione1;
	private String commissione2;
	private String commissione3;
	private String ruoloCommissione1;
	private String ruoloCommissione2;
	private String ruoloCommissione3;

	private List<Atto> listAtti = new ArrayList<Atto>();

	private String numeroAtto;

	private String numeroAttoDa;

	private String numeroAttoA;

	private Date dataIniziativaDa;

	private Date dataIniziativaA;

	private String tipoatto;

	private String legislatura;

	private String stato;

	private String numeroprotocollo;

	private String tipoiniziativa;

	private String numerodcr;

	private String primofirmatario;
	
	private String gruppoFirmatario;
	
	private String gruppoPrimoFirmatario;

	private String oggetto;

	private String firmatario;

	private String tipoChiusura;

	private String esitoVotoCommissioneReferente;

	private String esitoVotoAula;

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

	private boolean emendatoAula;

	private boolean rinviato;

	private boolean sospeso;

	private AttoSearch atto = new AttoSearch();

	private Map<String, String> tipiAtto = new HashMap<String, String>();

	private List<String> legislature = new ArrayList<String>();

	private Map<String, String> stati = new HashMap<String, String>();

	private Map<String, String> tipiIniziative = new HashMap<String, String>();

	private Map<String, String> tipiChiusura = new HashMap<String, String>();

	private Map<String, String> esitiVotoCommissioneReferente = new HashMap<String, String>();

	private Map<String, String> esitiVotoAula = new HashMap<String, String>();

	private List<String> commissioni = new ArrayList<String>();

	private List<String> firmatari = new ArrayList<String>();
	
	private List<String> gruppiConsiliari = new ArrayList<String>();
	
	private List<Relatore> relatori = new ArrayList<Relatore>();
	
	
	private Map<String, String> organismiStatutari = new HashMap<String, String>();

	public void searchAtti() {

		FacesContext context = FacesContext.getCurrentInstance();
		UserBean userBean = ((UserBean) context.getExternalContext()
				.getSessionMap().get("userBean"));

		atto.getStatiUtente().clear();
		atto.setCommissioneUser("");
		atto.setRuoloUtente(userBean.getUser().getSessionGroup().getNome());
		atto.setTipoWorkingList("");
		
		// LAVORAZIONE
		if ("inlavorazione".equals(listaLavoro)) {

			atto.setTipoWorkingList("inlavorazione");
			
			if (userBean.getUser().getSessionGroup().isCommissione()) {

				atto.setCommissioneUser(userBean.getUser().getSessionGroup()
						.getNome());
				
				atto.getStatiUtente().add(
						new StatoAtto(StatoAtto.ASSEGNATO_COMMISSIONE));
				atto.getStatiUtente().add(
						new StatoAtto(StatoAtto.PRESO_CARICO_COMMISSIONE));
				atto.getStatiUtente().add(
						new StatoAtto(StatoAtto.NOMINATO_RELATORE));
				atto.getStatiUtente().add(
						new StatoAtto(StatoAtto.VOTATO_COMMISSIONE));
				atto.getStatiUtente().add(
						new StatoAtto(StatoAtto.LAVORI_COMITATO_RISTRETTO));
				atto.getStatiUtente().add(
						new StatoAtto(StatoAtto.MIS));
				
			} else if (GruppoUtente.AULA.equalsIgnoreCase(userBean.getUser()
					.getSessionGroup().getNome())) {

				atto.getStatiUtente().add(
						new StatoAtto(StatoAtto.TRASMESSO_AULA));
				atto.getStatiUtente().add(
						new StatoAtto(StatoAtto.PRESO_CARICO_AULA));
				atto.getStatiUtente().add(new StatoAtto(StatoAtto.VOTATO_AULA));
				atto.getStatiUtente().add(new StatoAtto(StatoAtto.PUBBLICATO));

			} else if (GruppoUtente.SERVIZIO_COMMISSIONI
					.equalsIgnoreCase(userBean.getUser().getSessionGroup()
							.getNome())) {

				atto.getStatiUtente()
						.add(new StatoAtto(StatoAtto.PROTOCOLLATO));
				atto.getStatiUtente().add(
						new StatoAtto(StatoAtto.PRESO_CARICO_SC));
				atto.getStatiUtente().add(
						new StatoAtto(StatoAtto.VERIFICATA_AMMISSIBILITA));
				atto.getStatiUtente().add(
						new StatoAtto(StatoAtto.PROPOSTA_ASSEGNAZIONE));
				atto.getStatiUtente().add(
						new StatoAtto(StatoAtto.EAC));

			} else if (GruppoUtente.CPCV.equalsIgnoreCase(userBean.getUser()
					.getSessionGroup().getNome())) {

				atto.setTipoAtto("MIS");

			}

			// LAVORATI
		} else if ("lavorati".equals(listaLavoro)) {

			atto.setTipoWorkingList("lavorati");
			
			if (userBean.getUser().getSessionGroup().isCommissione()) {

				atto.setCommissioneUser(userBean.getUser().getSessionGroup()
						.getNome());
				
				atto.getStatiUtente().add(
						new StatoAtto(StatoAtto.TRASMESSO_AULA));
				atto.getStatiUtente().add(
						new StatoAtto(StatoAtto.PRESO_CARICO_AULA));
				atto.getStatiUtente().add(new StatoAtto(StatoAtto.VOTATO_AULA));
				atto.getStatiUtente().add(new StatoAtto(StatoAtto.PUBBLICATO));
				atto.getStatiUtente().add(new StatoAtto(StatoAtto.CHIUSO));

			} else if (GruppoUtente.AULA.equalsIgnoreCase(userBean.getUser()
					.getSessionGroup().getNome())) {

				atto.getStatiUtente().add(new StatoAtto(StatoAtto.CHIUSO));

			} else if (GruppoUtente.SERVIZIO_COMMISSIONI
					.equalsIgnoreCase(userBean.getUser().getSessionGroup()
							.getNome())) {

				atto.getStatiUtente().add(
						new StatoAtto(StatoAtto.ASSEGNATO_COMMISSIONE));
				atto.getStatiUtente().add(
						new StatoAtto(StatoAtto.PRESO_CARICO_COMMISSIONE));
				atto.getStatiUtente().add(
						new StatoAtto(StatoAtto.NOMINATO_RELATORE));
				atto.getStatiUtente().add(
						new StatoAtto(StatoAtto.VOTATO_COMMISSIONE));
				atto.getStatiUtente().add(
						new StatoAtto(StatoAtto.TRASMESSO_COMMISSIONE));
				atto.getStatiUtente().add(
						new StatoAtto(StatoAtto.LAVORI_COMITATO_RISTRETTO));
				atto.getStatiUtente().add(
						new StatoAtto(StatoAtto.TRASMESSO_AULA));
				atto.getStatiUtente().add(
						new StatoAtto(StatoAtto.PRESO_CARICO_AULA));
				atto.getStatiUtente().add(new StatoAtto(StatoAtto.VOTATO_AULA));
				atto.getStatiUtente().add(new StatoAtto(StatoAtto.PUBBLICATO));
				atto.getStatiUtente().add(new StatoAtto(StatoAtto.CHIUSO));

			} else if (GruppoUtente.CPCV.equalsIgnoreCase(userBean.getUser()
					.getSessionGroup().getNome())) {

			}

		}

		atto.setGruppoUtente(userBean.getUser().getSessionGroup().getNome());
		setListAtti(attoServiceManager.searchAtti(atto));
		Collections.sort(listAtti);
	}

	@PostConstruct
	protected void initLazyModel() {

		setCommissioni(commissioneServiceManager.getAll());
		setOrganismiStatutari(organismoStatutarioServiceManager.findAll());
		setFirmatari(personaleServiceManager.getAllFirmatario());
		setRelatori(personaleServiceManager.getAllRelatori());
		setStati(statoAttoServiceManager.findAll());
		setTipiChiusura(tipoChiusuraServiceManager.findAll());
		setTipiIniziative(tipoIniziativaServiceManager.findAll());
		setTipiAtto(tipoAttoServiceManager.findAll());
		setLegislature(legislaturaServiceManager.list());
		setGruppiConsiliari(personaleServiceManager.findGruppiConsiliari());
		atto.setLegislatura(legislaturaServiceManager.getAll().get(0).getNome());
		//atto.setLegislatura("IX");
		FacesContext context = FacesContext.getCurrentInstance();
		UserBean userBean = ((UserBean) context.getExternalContext()
				.getSessionMap().get("userBean"));

		atto.getStatiUtente().clear();
		atto.setCommissioneUser("");  
		atto.setTipoWorkingList("inlavorazione");
		atto.setRuoloUtente(userBean.getUser().getSessionGroup().getNome());
		
		if (userBean.getUser().getSessionGroup().isCommissione()) {

			atto.setCommissioneUser(userBean.getUser().getSessionGroup()
					.getNome());
			
			atto.getStatiUtente().add(
					new StatoAtto(StatoAtto.ASSEGNATO_COMMISSIONE));
			atto.getStatiUtente().add(
					new StatoAtto(StatoAtto.PRESO_CARICO_COMMISSIONE));
			atto.getStatiUtente().add(
					new StatoAtto(StatoAtto.NOMINATO_RELATORE));
			atto.getStatiUtente().add(
					new StatoAtto(StatoAtto.VOTATO_COMMISSIONE));
			atto.getStatiUtente().add(
					new StatoAtto(StatoAtto.LAVORI_COMITATO_RISTRETTO));
			atto.getStatiUtente().add(
					new StatoAtto(StatoAtto.MIS));

		} else if (GruppoUtente.AULA.equalsIgnoreCase(userBean.getUser()
				.getSessionGroup().getNome())) {

			atto.getStatiUtente().add(new StatoAtto(StatoAtto.TRASMESSO_AULA));
			atto.getStatiUtente().add(
					new StatoAtto(StatoAtto.PRESO_CARICO_AULA));
			atto.getStatiUtente().add(new StatoAtto(StatoAtto.VOTATO_AULA));
			atto.getStatiUtente().add(new StatoAtto(StatoAtto.PUBBLICATO));

		} else if (GruppoUtente.SERVIZIO_COMMISSIONI.equalsIgnoreCase(userBean
				.getUser().getSessionGroup().getNome())) {

			atto.getStatiUtente().add(new StatoAtto(StatoAtto.PROTOCOLLATO));
			atto.getStatiUtente().add(new StatoAtto(StatoAtto.PRESO_CARICO_SC));
			atto.getStatiUtente().add(
					new StatoAtto(StatoAtto.VERIFICATA_AMMISSIBILITA));
			atto.getStatiUtente().add(
					new StatoAtto(StatoAtto.PROPOSTA_ASSEGNAZIONE));
			atto.getStatiUtente().add(
					new StatoAtto(StatoAtto.EAC));

		} else if (GruppoUtente.CPCV.equalsIgnoreCase(userBean.getUser()
				.getSessionGroup().getNome())) {

			atto.setTipoAtto("MIS");

		}

		setListAtti(attoServiceManager.searchAtti(atto));
		Collections.sort(listAtti);

	}

	public void postProcessXLS(Object document) throws SecurityException,
			NoSuchFieldException, IllegalArgumentException,
			IllegalAccessException {

		FacesContext context = FacesContext.getCurrentInstance();
		UserBean userBean = ((UserBean) context.getExternalContext()
				.getSessionMap().get("userBean"));

		HSSFWorkbook wb = (HSSFWorkbook) document;
		wb.removeSheetAt(0);
		HSSFSheet sheet = wb.createSheet();
		sheet.createRow(0);

		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		HSSFCell cell = sheet.getRow(0).createCell(0);
		cell.setCellValue("Tipo atto");
		cell.setCellStyle(cellStyle);
		cell = sheet.getRow(0).createCell(1);
		cell.setCellValue("N° atto");
		cell.setCellStyle(cellStyle);

		int colCount = 2;

		for (ColonnaAtto colonna : userBean.getColonneTotali()) {

			HSSFCell cellRec = sheet.getRow(0).createCell(colCount);
			cellRec.setCellValue(colonna.getNome());
			cellRec.setCellStyle(cellStyle);
			colCount++;

		}

		int rowNum = 1;

		for (Atto atto : listAtti) {

			sheet.createRow(rowNum);
			HSSFCell cella = sheet.getRow(rowNum).createCell(0);
			cella.setCellValue(atto.getTipo());
			cella = sheet.getRow(rowNum).createCell(1);
			cella.setCellValue(atto.getNumeroAtto());

			int colConta = 2;
			DateFormat myDateFormat = new SimpleDateFormat("dd/MM/yyyy");

			for (ColonnaAtto colonna : userBean.getColonneTotali()) {

				Field privateStringField = atto.getClass().getDeclaredField(
						colonna.getAttoProperty());

				privateStringField.setAccessible(true);

				cella = sheet.getRow(rowNum).createCell(colConta);

				String value = "";

				if (privateStringField.get(atto) instanceof Date) {

					value = myDateFormat.format((Date) privateStringField
							.get(atto));

				} else {

					value = (String) privateStringField.get(atto);
				}

				cella.setCellValue(value);
				colConta++;
			}

			rowNum++;
		}

	}

	public String attoDetail() {

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = (AttoBean) context
				.getApplication()
				.getExpressionFactory()
				.createValueExpression(context.getELContext(), "#{attoBean}",
						AttoBean.class).getValue(context.getELContext());

		Atto attoRet = null;

		if ("MIS".equalsIgnoreCase(tipoAttoSelected)) {

			attoBean.setAttoMIS(attoServiceManager.findMISById(idAttoSelected));
			return "pretty:Inserimento_MIS";

		} else if ("EAC".equalsIgnoreCase(tipoAttoSelected)) {

			attoBean.setAttoEAC(attoServiceManager.findEACById(idAttoSelected));
			return "pretty:Inserimento_EAC";

		} else {

			attoBean.setAtto(attoServiceManager.findById(idAttoSelected));
			attoBean.getAtto().setFirmatari(
					personaleServiceManager.findFirmatariByAtto(attoBean
							.getAtto()));
			attoBean.getAtto()
					.setTestiAtto(
							attoRecordServiceManager.testiAttoByAtto(attoBean
									.getAtto()));
			attoBean.getAtto().setAllegati(
					attoRecordServiceManager.allAllegatiAttoByAtto(attoBean
							.getAtto()));
			return "pretty:Riepilogo_Atto";

		}
	}

	public String attoDetail(String idAttoParam, String tipo) {

		FacesContext context = FacesContext.getCurrentInstance();
		AttoBean attoBean = (AttoBean) context
				.getApplication()
				.getExpressionFactory()
				.createValueExpression(context.getELContext(), "#{attoBean}",
						AttoBean.class).getValue(context.getELContext());

		Atto attoRet = null;

		if ("MIS".equalsIgnoreCase(tipo)) {

			attoBean.setAttoMIS(attoServiceManager.findMISById(idAttoParam));
			return "pretty:Inserimento_MIS";

		} else if ("EAC".equalsIgnoreCase(tipo)) {

			attoBean.setAttoEAC(attoServiceManager.findEACById(idAttoParam));
			return "pretty:Inserimento_EAC";

		} else {

			attoBean.setAtto(attoServiceManager.findById(idAttoParam));
			attoBean.getAtto().setFirmatari(
					personaleServiceManager.findFirmatariByAtto(attoBean
							.getAtto()));
			attoBean.getAtto()
					.setTestiAtto(
							attoRecordServiceManager.testiAttoByAtto(attoBean
									.getAtto()));
			attoBean.getAtto().setAllegati(
					attoRecordServiceManager.allAllegatiAttoByAtto(attoBean
							.getAtto()));
			return "pretty:Riepilogo_Atto";

		}

	}
	
	public void reset(){
		
		setAbbinamento(false);
		setAnno("");
		setCommissione1("");
		setCommissione2("");
		setCommissione3("");
		setDataAssegnazioneA(null);
		setDataAssegnazioneDa(null);
		setDataChiusuraA(null);
		setDataChiusuraDa(null);
		setDataDGR(null);
		setDataIniziativaA(null);
		setDataIniziativaDa(null);
		setDataLR(null);
		setDataPubblicazioneA(null);
		setDataPubblicazioneDa(null);
		setDataSedutaAulaA(null);
		setDataSedutaAulaDa(null);
		setDataSedutaCommissioneA(null);
		setDataSedutaCommissioneDa(null);
		setDataSedutaSCA(null);
		setDataSedutaSCDa(null);
		setDeliberante(false);
		setEmendato(false);
		setEmendatoAula(false);
		setEsitoVotoAula("");
		setEsitoVotoCommissioneReferente("");
		setFirmatario("");
		setNumeroAttoA("");
		setNumeroAttoDa("");
		setNumerodcr("");
		setNumeroDcr("");
		setNumeroDGR("");
		setNumeroLCR("");
		setNumeroLR("");
		setNumeroprotocollo("");
		setOggetto("");
		setOrganismoStatutario("");
		setPrimofirmatario("");
		setRedigente(false);
		setRelatore("");
		setRinviato(false);
		setRuoloCommissione1("");
		setRuoloCommissione2("");
		setRuoloCommissione3("");
		setSoggettoConsultato("");
		setSospeso(false);
		setStato("");
		setStralcio(false);
		setTipoatto("");
		setTipoChiusura("");
		setTipoiniziativa("");
		
		
	}
	
	public int sortNumeriAtto(String s1, String s2) {
        
        return Integer.parseInt(s1) -  Integer.parseInt(s2);
    }
	

	public String getNumeroAtto() {
		return this.atto.getNumeroAtto();
	}

	public void setNumeroAtto(String numeroAtto) {
		this.atto.setNumeroAtto(numeroAtto);
	}

	public String getNumeroAttoDa() {
		return this.atto.getNumeroAttoDa();
	}

	public void setNumeroAttoDa(String numeroAttoDa) {
		this.atto.setNumeroAttoDa(numeroAttoDa);
	}

	public String getNumeroAttoA() {
		return this.atto.getNumeroAttoA();
	}

	public void setNumeroAttoA(String numeroAttoA) {
		this.atto.setNumeroAttoA(numeroAttoA);
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
		return this.atto.getNumeroRepertorio();
	}

	public void setNumeroprotocollo(String numeroprotocollo) {
		this.atto.setNumeroRepertorio(numeroprotocollo);
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

	
	public String getGruppoFirmatario() {
		return atto.getGruppoFirmatario();
	}

	public void setGruppoFirmatario(String gruppoFirmatario) {
		this.atto.setGruppoFirmatario (gruppoFirmatario);
	}

	public String getGruppoPrimoFirmatario() {
		return atto.getGruppoPrimoFirmatario();
	}

	public void setGruppoPrimoFirmatario(String gruppoPrimoFirmatario) {
		this.atto.setGruppoPrimoFirmatario ( gruppoPrimoFirmatario);
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

	public AttoRecordServiceManager getAttoRecordServiceManager() {
		return attoRecordServiceManager;
	}

	public void setAttoRecordServiceManager(
			AttoRecordServiceManager attoRecordServiceManager) {
		this.attoRecordServiceManager = attoRecordServiceManager;
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

	public Date getDataChiusuraA() {
		return this.atto.getDataChiusuraA();
	}

	public void setDataChiusuraA(Date dataSedutaAulaA) {
		this.atto.setDataChiusuraA(dataSedutaAulaA);
	}

	public Date getDataChiusuraDa() {
		return this.atto.getDataChiusuraDa();
	}

	public void setDataChiusuraDa(Date dataSedutaAulaA) {
		this.atto.setDataChiusuraDa(dataSedutaAulaA);
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

	public boolean isEmendatoAula() {
		return this.atto.isEmendatoAula();
	}

	public void setEmendatoAula(boolean emendatoAula) {
		this.atto.setEmendatoAula(emendatoAula);
	}

	public String getNumeroDcr() {
		return this.atto.getNumeroDcr();
	}

	public void setNumeroDcr(String numeroDcr) {
		this.atto.setNumeroDcr(numeroDcr);
	}

	public String getNumeroDGR() {
		return this.atto.getNumeroDGR();
	}

	public void setNumeroDGR(String numeroDGR) {
		this.atto.setNumeroDGR(numeroDGR);
	}

	public Date getDataAssegnazioneA() {
		return atto.getDataAssegnazioneA();
	}

	public void setDataAssegnazioneA(Date dataAssegnazioneA) {
		this.atto.setDataAssegnazioneA(dataAssegnazioneA);
	}

	public Date getDataAssegnazioneDa() {
		return atto.getDataAssegnazioneDa();
	}

	public void setDataAssegnazioneDa(Date dataAssegnazioneDa) {
		this.atto.setDataAssegnazioneDa(dataAssegnazioneDa);
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

	public Map<String, String> getStati() {
		return stati;
	}

	public List<String> getLegislature() {
		return legislature;
	}

	public void setLegislature(List<String> legislature) {
		this.legislature = legislature;
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

	/*
	 * public Map<String, String> getFirmatari() { return firmatari; }
	 * 
	 * public void setFirmatari(Map<String, String> firmatari) { this.firmatari
	 * = firmatari; }
	 */

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

	public List<String> getCommissioni() {
		return commissioni;
	}

	public void setCommissioni(List<String> commissioni) {
		this.commissioni = commissioni;
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

	public String getListaLavoro() {
		return listaLavoro;
	}

	public void setListaLavoro(String listaLavoro) {
		this.listaLavoro = listaLavoro;
	}

	public Date getDataDGR() {
		return atto.getDataDGR();
	}

	public void setDataDGR(Date dataDGR) {
		this.atto.setDataDGR(dataDGR);
	}

	public Date getDataLR() {
		return atto.getDataLR();
	}

	public void setDataLR(Date dataLR) {
		this.atto.setDataLR(dataLR);
	}

	public String getCommissione1() {
		return atto.getCommissione1();
	}

	public void setCommissione1(String commissione1) {
		this.atto.setCommissione1(commissione1);
	}

	public String getCommissione2() {
		return atto.getCommissione2();
	}

	public void setCommissione2(String commissione2) {
		this.atto.setCommissione2(commissione2);
	}

	public String getCommissione3() {
		return atto.getCommissione3();
	}

	public void setCommissione3(String commissione3) {
		this.atto.setCommissione3(commissione3);
	}

	public String getRuoloCommissione1() {
		return atto.getRuoloCommissione1();
	}

	public void setRuoloCommissione1(String ruoloCommissione1) {
		this.atto.setRuoloCommissione1(ruoloCommissione1);

	}

	public String getRuoloCommissione2() {
		return atto.getRuoloCommissione2();
	}

	public void setRuoloCommissione2(String ruoloCommissione2) {
		this.atto.setRuoloCommissione2(ruoloCommissione2);
	}

	public String getRuoloCommissione3() {
		return atto.getRuoloCommissione3();
	}

	public void setRuoloCommissione3(String ruoloCommissione3) {
		this.atto.setRuoloCommissione3(ruoloCommissione3);
	}

	public List<String> getFirmatari() {
		return firmatari;
	}

	public void setFirmatari(List<String> firmatari) {
		this.firmatari = firmatari;
	}

	

	
	public List<String> getGruppiConsiliari() {
		return gruppiConsiliari;
	}

	public void setGruppiConsiliari(List<String> gruppiConsiliari) {
		this.gruppiConsiliari = gruppiConsiliari;
	}

	public List<Relatore> getRelatori() {
		return relatori;
	}

	public void setRelatori(List<Relatore> relatori) {
		this.relatori = relatori;
	}
	
	public String getIdAttoSelected() {
		return idAttoSelected;
	}

	public void setIdAttoSelected(String idAttoSelected) {
		this.idAttoSelected = idAttoSelected;
	}

	public String getTipoAttoSelected() {
		return tipoAttoSelected;
	}

	public void setTipoAttoSelected(String tipoAttoSelected) {
		this.tipoAttoSelected = tipoAttoSelected;
	}

}
