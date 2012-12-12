package com.sourcesense.crl.business.model;

import java.util.Date;
import java.util.List;

public class Report {

	private String tipoTemplate;
	private String nome;
	
	private List <String> tipiAtto;
	private List <String> commissioni;
	private String ruoloCommissione;
	private Date dataAssegnazioneDa;
	private Date dataAssegnazioneA;
	private Date dataVotazioneCommReferenteDa;
	private Date dataVotazioneCommReferenteA;
	private Date dataRitiroDa;
	private Date dataRitiroA;
	private Date dataNominaRelatoreDa;
	private Date dataNominaRelatoreA;
	private List <String> relatori;
	private String organismo;
	private Date dataAssegnazioneParereDa;
	private Date dataAssegnazioneParereA;
	private String firmatario;
	private String tipologiaFirma;
	private Date dataPresentazioneDa;
	private Date dataPresentazioneA;
	private Date dataAssegnazioneCommReferenteDa;
	private Date dataAssegnazioneCommReferenteA;
	private Date dataSedutaDa;
	private Date dataSedutaA;
	
	public Report(){
		
		
	}
	
    public Report(String nome,String tipoTemplate){
		
    	this.nome = nome;
    	this.tipoTemplate = tipoTemplate;
		
	}
	
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<String> getTipiAtto() {
		return tipiAtto;
	}
	public void setTipiAtto(List<String> tipiAtto) {
		this.tipiAtto = tipiAtto;
	}
	public List<String> getCommissioni() {
		return commissioni;
	}
	public void setCommissioni(List<String> commissioni) {
		this.commissioni = commissioni;
	}
	public String getRuoloCommissione() {
		return ruoloCommissione;
	}
	public void setRuoloCommissione(String ruoloCommissione) {
		this.ruoloCommissione = ruoloCommissione;
	}
	public Date getDataAssegnazioneDa() {
		return dataAssegnazioneDa;
	}
	public void setDataAssegnazioneDa(Date dataAssegnazioneDa) {
		this.dataAssegnazioneDa = dataAssegnazioneDa;
	}
	public Date getDataAssegnazioneA() {
		return dataAssegnazioneA;
	}
	public void setDataAssegnazioneA(Date dataAssegnazioneA) {
		this.dataAssegnazioneA = dataAssegnazioneA;
	}
	public Date getDataVotazioneCommReferenteDa() {
		return dataVotazioneCommReferenteDa;
	}
	public void setDataVotazioneCommReferenteDa(Date dataVotazioneCommReferenteDa) {
		this.dataVotazioneCommReferenteDa = dataVotazioneCommReferenteDa;
	}
	public Date getDataVotazioneCommReferenteA() {
		return dataVotazioneCommReferenteA;
	}
	public void setDataVotazioneCommReferenteA(Date dataVotazioneCommReferenteA) {
		this.dataVotazioneCommReferenteA = dataVotazioneCommReferenteA;
	}
	public Date getDataRitiroDa() {
		return dataRitiroDa;
	}
	public void setDataRitiroDa(Date dataRitiroDa) {
		this.dataRitiroDa = dataRitiroDa;
	}
	public Date getDataRitiroA() {
		return dataRitiroA;
	}
	public void setDataRitiroA(Date dataRitiroA) {
		this.dataRitiroA = dataRitiroA;
	}
	public Date getDataNominaRelatoreDa() {
		return dataNominaRelatoreDa;
	}
	public void setDataNominaRelatoreDa(Date dataNominaRelatoreDa) {
		this.dataNominaRelatoreDa = dataNominaRelatoreDa;
	}
	public Date getDataNominaRelatoreA() {
		return dataNominaRelatoreA;
	}
	public void setDataNominaRelatoreA(Date dataNominaRelatoreA) {
		this.dataNominaRelatoreA = dataNominaRelatoreA;
	}
	public List<String> getRelatori() {
		return relatori;
	}
	public void setRelatori(List<String> relatori) {
		this.relatori = relatori;
	}
	public String getOrganismo() {
		return organismo;
	}
	public void setOrganismo(String organismo) {
		this.organismo = organismo;
	}
	public Date getDataAssegnazioneParereDa() {
		return dataAssegnazioneParereDa;
	}
	public void setDataAssegnazioneParereDa(Date dataAssegnazioneParereDa) {
		this.dataAssegnazioneParereDa = dataAssegnazioneParereDa;
	}
	public Date getDataAssegnazioneParereA() {
		return dataAssegnazioneParereA;
	}
	public void setDataAssegnazioneParereA(Date dataAssegnazioneParereA) {
		this.dataAssegnazioneParereA = dataAssegnazioneParereA;
	}
	public String getFirmatario() {
		return firmatario;
	}
	public void setFirmatario(String firmatario) {
		this.firmatario = firmatario;
	}
	public String getTipologiaFirma() {
		return tipologiaFirma;
	}
	public void setTipologiaFirma(String tipologiaFirma) {
		this.tipologiaFirma = tipologiaFirma;
	}
	public Date getDataPresentazioneDa() {
		return dataPresentazioneDa;
	}
	public void setDataPresentazioneDa(Date dataPresentazioneDa) {
		this.dataPresentazioneDa = dataPresentazioneDa;
	}
	public Date getDataPresentazioneA() {
		return dataPresentazioneA;
	}
	public void setDataPresentazioneA(Date dataPresentazioneA) {
		this.dataPresentazioneA = dataPresentazioneA;
	}
	public Date getDataAssegnazioneCommReferenteDa() {
		return dataAssegnazioneCommReferenteDa;
	}
	public void setDataAssegnazioneCommReferenteDa(
			Date dataAssegnazioneCommReferenteDa) {
		this.dataAssegnazioneCommReferenteDa = dataAssegnazioneCommReferenteDa;
	}
	public Date getDataAssegnazioneCommReferenteA() {
		return dataAssegnazioneCommReferenteA;
	}
	public void setDataAssegnazioneCommReferenteA(
			Date dataAssegnazioneCommReferenteA) {
		this.dataAssegnazioneCommReferenteA = dataAssegnazioneCommReferenteA;
	}
	public Date getDataSedutaDa() {
		return dataSedutaDa;
	}
	public void setDataSedutaDa(Date dataSedutaDa) {
		this.dataSedutaDa = dataSedutaDa;
	}
	public Date getDataSedutaA() {
		return dataSedutaA;
	}
	public void setDataSedutaA(Date dataSedutaA) {
		this.dataSedutaA = dataSedutaA;
	}

	public String getTipoTemplate() {
		return tipoTemplate;
	}

	public void setTipoTemplate(String tipoTemplate) {
		this.tipoTemplate = tipoTemplate;
	}

	
	
	
	
	
	

	
}
