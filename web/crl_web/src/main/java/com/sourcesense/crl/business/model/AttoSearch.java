package com.sourcesense.crl.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.sourcesense.crl.util.JsonDateSerializer;

@JsonRootName("atto")
@JsonTypeName("atto")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public class AttoSearch extends Atto {
	
	private String numeroDGR;
	private Date dataAssegnazioneA;
	private Date dataAssegnazioneDa;
	private Date dataDGR;
	
	
    private String numeroAttoDa;
	private String numeroAttoA;
	private Date dataPubblicazioneDa;
	private Date dataPubblicazioneA;
	private Date dataSedutaSCDa;
	private Date dataSedutaSCA;
	private Date dataSedutaCommissioneDa;
	private Date dataSedutaCommissioneA;
	private Date dataSedutaAulaDa;
	private Date dataSedutaAulaA;
	private Date dataIniziativaDa;
	private Date dataIniziativaA;
	private Date dataChiusuraDa;
	private Date dataChiusuraA;
	private Date dataLR;
	private String relatore;	
	private String numeroDcr;
	private String esitoVotoCommissioneReferente;
	private String esitoVotoAula;
	private String commissione1;
	private String commissione2;
	private String commissione3;
	private String ruoloCommissione1;
	private String ruoloCommissione2;
	private String ruoloCommissione3;
	
	private String numeroLcr;
	private List<StatoAtto> statiUtente = new ArrayList<StatoAtto>();
	private String gruppoUtente;
	private boolean emendato;
	private boolean emendatoAula;
	
	
	/********DELETE***********/
	private String commissioneReferente;
	private String commissioneConsultiva;
	  
	 /***************************/
	
	
	
	
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataPubblicazioneDa() {
		return dataPubblicazioneDa;
		
	}

	public void setDataPubblicazioneDa(Date dataPubblicazioneDa) {
		this.dataPubblicazioneDa = dataPubblicazioneDa;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataPubblicazioneA() {
		return dataPubblicazioneA;
	}

	public void setDataPubblicazioneA(Date dataPubblicazioneA) {
		this.dataPubblicazioneA = dataPubblicazioneA;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataSedutaSCDa() {
		return dataSedutaSCDa;
	}

	public void setDataSedutaSCDa(Date dataSedutaSCDa) {
		this.dataSedutaSCDa = dataSedutaSCDa;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataSedutaSCA() {
		return dataSedutaSCA;
	}

	public void setDataSedutaSCA(Date dataSedutaSCA) {
		this.dataSedutaSCA = dataSedutaSCA;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataSedutaCommissioneDa() {
		return dataSedutaCommissioneDa;
	}

	public void setDataSedutaCommissioneDa(Date dataSedutaCommissioneDa) {
		this.dataSedutaCommissioneDa = dataSedutaCommissioneDa;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataSedutaCommissioneA() {
		return dataSedutaCommissioneA;
	}

	public void setDataSedutaCommissioneA(Date dataSedutaCommissioneA) {
		this.dataSedutaCommissioneA = dataSedutaCommissioneA;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataSedutaAulaDa() {
		return dataSedutaAulaDa;
	}

	public void setDataSedutaAulaDa(Date dataSedutaAulaDa) {
		this.dataSedutaAulaDa = dataSedutaAulaDa;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataSedutaAulaA() {
		return dataSedutaAulaA;
	}

	public void setDataSedutaAulaA(Date dataSedutaAulaA) {
		this.dataSedutaAulaA = dataSedutaAulaA;
	}
	
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataIniziativaDa() {
		return dataIniziativaDa;
	}
	
	public void setDataIniziativaDa(Date dataIniziativaDa) {
		this.dataIniziativaDa = dataIniziativaDa;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataIniziativaA() {
		return dataIniziativaA;
	}

	public void setDataIniziativaA(Date dataIniziativaA) {
		this.dataIniziativaA = dataIniziativaA;
	}

	public String getRelatore() {
		return relatore;
	}

	public void setRelatore(String relatore) {
		this.relatore = relatore;
	}

	public String getNumeroDcr() {
		return numeroDcr;
	}

	public void setNumeroDcr(String numeroDcr) {
		this.numeroDcr = numeroDcr;
	}

	public String getEsitoVotoCommissioneReferente() {
		return esitoVotoCommissioneReferente;
	}

	public void setEsitoVotoCommissioneReferente(
			String esitoVotoCommissioneReferente) {
		this.esitoVotoCommissioneReferente = esitoVotoCommissioneReferente;
	}

	public String getEsitoVotoAula() {
		return esitoVotoAula;
	}

	public void setEsitoVotoAula(String esitoVotoAula) {
		this.esitoVotoAula = esitoVotoAula;
	}

	

	public String getCommissione1() {
		return commissione1;
	}

	public void setCommissione1(String commissione1) {
		this.commissione1 = commissione1;
	}

	public String getCommissione2() {
		return commissione2;
	}

	public void setCommissione2(String commissione2) {
		this.commissione2 = commissione2;
	}

	public String getCommissione3() {
		return commissione3;
	}

	public void setCommissione3(String commissione3) {
		this.commissione3 = commissione3;
	}

	public String getRuoloCommissione1() {
		return ruoloCommissione1;
	}

	public void setRuoloCommissione1(String ruoloCommissione1) {
		this.ruoloCommissione1 = ruoloCommissione1;
	}

	public String getRuoloCommissione2() {
		return ruoloCommissione2;
	}

	public void setRuoloCommissione2(String ruoloCommissione2) {
		this.ruoloCommissione2 = ruoloCommissione2;
	}

	public String getRuoloCommissione3() {
		return ruoloCommissione3;
	}

	public void setRuoloCommissione3(String ruoloCommissione3) {
		this.ruoloCommissione3 = ruoloCommissione3;
	}

	public String getNumeroLcr() {
		return numeroLcr;
	}

	public void setNumeroLcr(String numeroLcr) {
		this.numeroLcr = numeroLcr;
	}

	public boolean isEmendato() {
		return emendato;
	}

	public void setEmendato(boolean emendato) {
		this.emendato = emendato;
	}

	public List<StatoAtto> getStatiUtente() {
		return statiUtente;
	}

	public void setStatiUtente(List<StatoAtto> statiUtente) {
		this.statiUtente = statiUtente;
	}

	public String getGruppoUtente() {
		return gruppoUtente;
	}

	public void setGruppoUtente(String gruppoUtente) {
		this.gruppoUtente = gruppoUtente;
	}

	public String getNumeroAttoDa() {
		return numeroAttoDa;
	}

	public void setNumeroAttoDa(String numeroAttoDa) {
		this.numeroAttoDa = numeroAttoDa;
	}

	public String getNumeroAttoA() {
		return numeroAttoA;
	}

	public void setNumeroAttoA(String numeroAttoA) {
		this.numeroAttoA = numeroAttoA;
	}

	public String getNumeroDGR() {
		return numeroDGR;
	}

	public void setNumeroDGR(String numeroDGR) {
		this.numeroDGR = numeroDGR;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataAssegnazioneA() {
		return dataAssegnazioneA;
	}

	public void setDataAssegnazioneA(Date dataAssegnazioneA) {
		this.dataAssegnazioneA = dataAssegnazioneA;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataAssegnazioneDa() {
		return dataAssegnazioneDa;
	}

	public void setDataAssegnazioneDa(Date dataAssegnazioneDa) {
		this.dataAssegnazioneDa = dataAssegnazioneDa;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataDGR() {
		return dataDGR;
	}

	public void setDataDGR(Date dataDGR) {
		this.dataDGR = dataDGR;
	}

	public boolean isEmendatoAula() {
		return emendatoAula;
	}

	public void setEmendatoAula(boolean emendatoAula) {
		this.emendatoAula = emendatoAula;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataChiusuraDa() {
		return dataChiusuraDa;
	}

	public void setDataChiusuraDa(Date dataChiusuraDa) {
		this.dataChiusuraDa = dataChiusuraDa;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataChiusuraA() {
		return dataChiusuraA;
	}

	public void setDataChiusuraA(Date dataChiusuraA) {
		this.dataChiusuraA = dataChiusuraA;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataLR() {
		return dataLR;
	}

	public void setDataLR(Date dataLR) {
		this.dataLR = dataLR;
	}

	
	///////////////////////DELETE
	public String getCommissioneReferente() {
		return commissioneReferente;
	}

	public void setCommissioneReferente(String commissioneReferente) {
		this.commissioneReferente = commissioneReferente;
	}

	public String getCommissioneConsultiva() {
		return commissioneConsultiva;
	}

	public void setCommissioneConsultiva(String commissioneConsultiva) {
		this.commissioneConsultiva = commissioneConsultiva;
	}
/////////////////////DELETE
	
	
	
	
	
}
