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

/**
 *
 * @author uji
 */
public class Atto
{
    private String tipo;
    private String codice;
    private String oggetto;
    private String primoFirmatario;
    private Date   dataPresentazione;
    private String stato;

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
}
