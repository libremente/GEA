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
package com.sourcesense.crl.business.model;


import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.springframework.beans.factory.annotation.Configurable;

/**
 *
 * @author uji
 */
@Configurable
@XmlRootElement
@JsonRootName("atto")
@JsonTypeName("atto")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public class Atto 
{
    /**
	 * 
	 */
	
	private String tipo;
    private String codice;
    private String oggetto;
    private String primoFirmatario;
    private Date   dataPresentazione;
    private String stato;
    private String numeroAtto;
    private String tipoAtto;
    private String tipologia;
    private String legislatura;
    private String anno;
    
	private String numeroprotocollo;
	private String tipoiniziativa;
	private String numerodcr;
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
	private boolean abbinamento;	
	private boolean stralcio;
	private String relatore;	
	private String organismoStatutario;	
	private String soggettoConsultato;	
	private boolean emendato;	
	private boolean rinviato;	
	private boolean sospeso;
    
    
    
    
    public String getNumeroAtto() {
		return numeroAtto;
	}

	public void setNumeroAtto(String numeroAtto) {
		this.numeroAtto = numeroAtto;
	}

	public String getTipoAtto() {
		return tipoAtto;
	}

	public void setTipoAtto(String tipoAtto) {
		this.tipoAtto = tipoAtto;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	/**
     * @return the tipo
     */
    public String getTipo()
    {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo)
    {
        this.tipo = tipo;
    }

    /**
     * @return the codice
     */
    public String getCodice()
    {
        return codice;
    }

    /**
     * @param codice the codice to set
     */
    public void setCodice(String codice)
    {
        this.codice = codice;
    }

    /**
     * @return the oggetto
     */
    public String getOggetto()
    {
        return oggetto;
    }

    /**
     * @param oggetto the oggetto to set
     */
    public void setOggetto(String oggetto)
    {
        this.oggetto = oggetto;
    }

    /**
     * @return the primoFirmatario
     */
    public String getPrimoFirmatario()
    {
        return primoFirmatario;
    }

    /**
     * @param primoFirmatario the primoFirmatario to set
     */
    public void setPrimoFirmatario(String primoFirmatario)
    {
        this.primoFirmatario = primoFirmatario;
    }

    /**
     * @return the dataPresentazione
     */
    public Date getDataPresentazione()
    {
        return dataPresentazione;
    }

    /**
     * @param dataPresentazione the dataPresentazione to set
     */
    public void setDataPresentazione(Date dataPresentazione)
    {
        this.dataPresentazione = dataPresentazione;
    }

    /**
     * @return the stato
     */
    public String getStato()
    {
        return stato;
    }

    /**
     * @param stato the stato to set
     */
    public void setStato(String stato)
    {
        this.stato = stato;
    }

	public String getLegislatura() {
		return legislatura;
	}

	public void setLegislatura(String legislatura) {
		this.legislatura = legislatura;
	}

	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

	public String getNumeroprotocollo() {
		return numeroprotocollo;
	}

	public void setNumeroprotocollo(String numeroprotocollo) {
		this.numeroprotocollo = numeroprotocollo;
	}

	public String getTipoiniziativa() {
		return tipoiniziativa;
	}

	public void setTipoiniziativa(String tipoiniziativa) {
		this.tipoiniziativa = tipoiniziativa;
	}

	public String getNumerodcr() {
		return numerodcr;
	}

	public void setNumerodcr(String numerodcr) {
		this.numerodcr = numerodcr;
	}

	public String getFirmatario() {
		return firmatario;
	}

	public void setFirmatario(String firmatario) {
		this.firmatario = firmatario;
	}

	public String getTipoChiusura() {
		return tipoChiusura;
	}

	public void setTipoChiusura(String tipoChiusura) {
		this.tipoChiusura = tipoChiusura;
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

	public boolean isRedigente() {
		return redigente;
	}

	public void setRedigente(boolean redigente) {
		this.redigente = redigente;
	}

	public boolean isDeliberante() {
		return deliberante;
	}

	public void setDeliberante(boolean deliberante) {
		this.deliberante = deliberante;
	}

	public String getNumeroLCR() {
		return numeroLCR;
	}

	public void setNumeroLCR(String numeroLCR) {
		this.numeroLCR = numeroLCR;
	}

	public String getNumeroLR() {
		return numeroLR;
	}

	public void setNumeroLR(String numeroLR) {
		this.numeroLR = numeroLR;
	}

	public boolean isAbbinamento() {
		return abbinamento;
	}

	public void setAbbinamento(boolean abbinamento) {
		this.abbinamento = abbinamento;
	}

	public boolean isStralcio() {
		return stralcio;
	}

	public void setStralcio(boolean stralcio) {
		this.stralcio = stralcio;
	}

	public String getRelatore() {
		return relatore;
	}

	public void setRelatore(String relatore) {
		this.relatore = relatore;
	}

	public String getOrganismoStatutario() {
		return organismoStatutario;
	}

	public void setOrganismoStatutario(String organismoStatutario) {
		this.organismoStatutario = organismoStatutario;
	}

	public String getSoggettoConsultato() {
		return soggettoConsultato;
	}

	public void setSoggettoConsultato(String soggettoConsultato) {
		this.soggettoConsultato = soggettoConsultato;
	}

	public boolean isEmendato() {
		return emendato;
	}

	public void setEmendato(boolean emendato) {
		this.emendato = emendato;
	}

	public boolean isRinviato() {
		return rinviato;
	}

	public void setRinviato(boolean rinviato) {
		this.rinviato = rinviato;
	}

	public boolean isSospeso() {
		return sospeso;
	}

	public void setSospeso(boolean sospeso) {
		this.sospeso = sospeso;
	}
	
	
    
    
}
