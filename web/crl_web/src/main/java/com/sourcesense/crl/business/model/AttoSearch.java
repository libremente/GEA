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
	private String relatore;	
	private String numeroDcr;
	private String esitoVotoCommissioneReferente;
	private String esitoVotoAula;
	private String commissioneReferente;
	private String commissioneConsultiva;
	private String numeroLcr;
	private List<StatoAtto> statiUtente = new ArrayList<StatoAtto>();
	private String gruppoUtente;
	private boolean emendato;
	
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

	
	
	
	
	
}
