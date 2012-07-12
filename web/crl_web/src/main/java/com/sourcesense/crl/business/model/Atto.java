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
    
    
}
