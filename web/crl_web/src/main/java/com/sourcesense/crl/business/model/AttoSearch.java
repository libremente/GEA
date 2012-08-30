package com.sourcesense.crl.business.model;

import java.util.Date;

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
	
	
	
}
