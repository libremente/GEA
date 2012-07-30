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


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.annotate.JsonUnwrapped;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * "id" : "workspace://SpacesStore/b0190a2c-763e-4f29-b3f7-b1476e1ab8b9",
	"nome" : "777",
	"numeroAtto" : "777",
	"tipologia" : "Tipologia1",
	"legislatura" : "Legislatura1",
	"stato" : "",
	"dataIniziativa" : "",
	"numeroProtocollo" : "",
	"tipoIniziativa" : "",
	"numeroDcr" : "",
	"oggetto" : "",
	"tipoChiusura" : "",
	"dataPubblicazione" : "",
	"esitoVotoCommissioneReferente" : "",
	"dataSedutaSc" : "",
	"esitoVotoAula" : "",
	"dataSedutaCommissione" : "",
	"commissioneReferente" : "",
	"dataSedutaAula" : "",
	"commissioneConsultiva" : "",
	"redigente" : "",
	"deliberante" : "",
	"organismoStatutario" : "",
	"numeroLcr" : "",
	"soggettoConsultato" : "",
	"numeroLr" : "",
	"anno" : "2012",
	"emendato" : "",
	"rinviato" : "",
	"sospeso" : "",
	"abbinamento" : "",
	"stralcio" : "",
	"primoFirmatario" : "",
	"relatori" : 	
 *@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
 * @JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
 * @author uji
 */
/**
 * @author pronetics
 *
 */
@Configurable()
@XmlRootElement(name="atto")
@JsonRootName("atto")
@JsonTypeName("atto")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
	public class Atto implements Cloneable
	{
	    /**
		 * 
		 */
		
		public Object clone() {
			try {
				return super.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
				return null;
			}
		}
		
	

	private String id;
	private String nome;
	private String tipo;
    private String codice;
    private String oggetto;
    private String primoFirmatario;
    private Date   dataPresentazione;
    private Date   dataPresaInCarico;
    private Date   dataPubblicazione;
    private Date   dataSedutaSc;
    private Date   dataSedutaAula;
    private String stato;
    private String numeroAtto;
    private String tipoAtto;
    private String tipologia;
    private String legislatura;
    private String anno;
    private String error;
    
    private String statoAttuale;
    private String classificazione;
    private String nRepertorio;
    private Date dataRepertorio;
    private Date dataIniziativa;
    private String numeroRepertorio;
    private String descrizioneIniziativa;
    private String numeroDgr;
    private Date   dataDgr;
    private String assegnazione;    
    private Date dataAssegnazione;
    private String esitoValidazione;
    private Date dataValidazione;
    private Date dataAssegnazioneCommissioni;
    private Date dataSedutaCommissione;
    
	private String numeroProtocollo;
	private String tipoIniziativa;
	private String numeroDcr;
	private String firmatario;
	private String tipoChiusura;
	private String esitoVotoCommissioneReferente;
	private String esitoVotoAula;
	private String commissioneReferente;
	private String commissioneConsultiva;
	private boolean redigente;
	private boolean deliberante;
	private String numeroLcr;
	private String numeroLr;
	private boolean abbinamento;	
	private boolean stralcio;
	private String organismoStatutario;	
	private String soggettoConsultato;	
	private boolean emendato;	
	private boolean rinviato;	
	private boolean sospeso;
	
	private List <Commissione> commissioni = new ArrayList<Commissione>();
	private List <Atto> abbinamenti = new ArrayList<Atto>();
	private List <Organo> organi = new ArrayList<Organo>();
	private List <Firmatario> firmatari = new ArrayList<Firmatario>();
	private List <Relatore> relatori = new ArrayList<Relatore>();
	private List <Parere> pareri = new ArrayList<Parere>();
	private List <Consultazione> consultazioni = new ArrayList<Consultazione>();
	private List <Atto> collegamenti = new ArrayList<Atto>();
	private List <Allegato> allegati = new ArrayList<Allegato>();
	private List <Link> links = new ArrayList<Link>();
	private List <OrganismoStatutario> organismiStatutari = new ArrayList<OrganismoStatutario>();
	private List <AttoRecord> testiAtto = new ArrayList<AttoRecord>();
	
	private String valutazioneAmmissibilita;
	private Date dataRichiestaInformazioni;
	private Date dataRicevimentoInformazioni;
	private boolean aiutiStato;
	private boolean normaFinanziaria;
	private boolean richiestaUrgenza;
	private boolean votazioneUrgenza;
	private Date dataVotazioneUrgenza;
	private String noteAmmissibilita;
	
	private String noteNoteAllegatiPresentazioneAssegnazione;
	
    

    
    
    public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

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

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroprotocollo) {
		this.numeroProtocollo = numeroprotocollo;
	}

	public String getTipoIniziativa() {
		return tipoIniziativa;
	}

	public void setTipoIniziativa(String tipoiniziativa) {
		this.tipoIniziativa = tipoiniziativa;
	}

	public String getNumeroDcr() {
		return numeroDcr;
	}

	public void setNumeroDcr(String numerodcr) {
		this.numeroDcr = numerodcr;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public Date getDataPresaInCarico() {
		return dataPresaInCarico;
	}

	public void setDataPresaInCarico(Date dataPresaInCarico) {
		this.dataPresaInCarico = dataPresaInCarico;
	}

	public String getClassificazione() {
		return classificazione;
	}

	public void setClassificazione(String classificazione) {
		this.classificazione = classificazione;
	}

	public String getStatoAttuale() {
		return statoAttuale;
	}

	public void setStatoAttuale(String statoAttuale) {
		this.statoAttuale = statoAttuale;
	}

	public String getNumeroRepertorio() {
		return numeroRepertorio;
	}

	public void setNumeroRepertorio(String numeroRepertorio) {
		this.numeroRepertorio = numeroRepertorio;
	}

	public Date getDataRepertorio() {
		return dataRepertorio;
	}

	public void setDataRepertorio(Date dataRepertorio) {
		this.dataRepertorio = dataRepertorio;
	}

	public Date getDataIniziativa() {
		return dataIniziativa;
	}

	public void setDataIniziativa(Date dataIniziativa) {
		this.dataIniziativa = dataIniziativa;
	}

		
	public String getDescrizioneIniziativa() {
		return descrizioneIniziativa;
	}
	
    public void setDescrizioneIniziativa(String descrizioneIniziativa) {
		this.descrizioneIniziativa = descrizioneIniziativa;
	}

	

	public String getAssegnazione() {
		return assegnazione;
	}

	public void setAssegnazione(String assegnazione) {
		this.assegnazione = assegnazione;
	}
	
	public String getnRepertorio() {
		return nRepertorio;
	}

	public void setnRepertorio(String nRepertorio) {
		this.nRepertorio = nRepertorio;
	}

	public Date getDataAssegnazione() {
		return dataAssegnazione;
	}

	public void setDataAssegnazione(Date dataAssegnazione) {
		this.dataAssegnazione = dataAssegnazione;
	}

	public String getEsitoValidazione() {
		return esitoValidazione;
	}

	public void setEsitoValidazione(String esitoValidazione) {
		this.esitoValidazione = esitoValidazione;
	}

	public Date getDataValidazione() {
		return dataValidazione;
	}

	public void setDataValidazione(Date dataValidazione) {
		this.dataValidazione = dataValidazione;
	}

	public Date getDataAssegnazioneCommissioni() {
		return dataAssegnazioneCommissioni;
	}

	public void setDataAssegnazioneCommissioni(Date dataAssegnazioneCommissioni) {
		this.dataAssegnazioneCommissioni = dataAssegnazioneCommissioni;
	}

	public List<Commissione> getCommissioni() {
		return commissioni;
	}

	public void setCommissioni(List<Commissione> commissioni) {
		this.commissioni = commissioni;
	}

	public List<Atto> getAbbinamenti() {
		return abbinamenti;
	}

	public void setAbbinamenti(List<Atto> abbinamenti) {
		this.abbinamenti = abbinamenti;
	}

	public List<Organo> getOrgani() {
		return organi;
	}

	public void setOrgani(List<Organo> organi) {
		this.organi = organi;
	}

	public List<Firmatario> getFirmatari() {
		return firmatari;
	}

	public void setFirmatari(List<Firmatario> firmatari) {
		this.firmatari = firmatari;
	}

	public List <Relatore> getRelatori() {
		return relatori;
	}

	public void setRelatori(List <Relatore> relatori) {
		this.relatori = relatori;
	}

	public List <Parere> getPareri() {
		return pareri;
	}

	public void setPareri(List <Parere> pareri) {
		this.pareri = pareri;
	}

	public List <Consultazione> getConsultazioni() {
		return consultazioni;
	}

	public void setConsultazioni(List <Consultazione> consultazioni) {
		this.consultazioni = consultazioni;
	}

	public List <Atto> getCollegamenti() {
		return collegamenti;
	}

	public void setCollegamenti(List <Atto> collegamenti) {
		this.collegamenti = collegamenti;
	}

	public List <Allegato> getAllegati() {
		return allegati;
	}

	public void setAllegati(List <Allegato> allegati) {
		this.allegati = allegati;
	}

	public List <Link> getLinks() {
		return links;
	}

	public void setLinks(List <Link> links) {
		this.links = links;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Date getDataPubblicazione() {
		return dataPubblicazione;
	}

	public void setDataPubblicazione(Date dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
	}

	public Date getDataSedutaSc() {
		return dataSedutaSc;
	}

	public void setDataSedutaSc(Date dataSedutaSc) {
		this.dataSedutaSc = dataSedutaSc;
	}

	public Date getDataSedutaCommissione() {
		return dataSedutaCommissione;
	}

	public void setDataSedutaCommissione(Date dataSedutaCommissione) {
		this.dataSedutaCommissione = dataSedutaCommissione;
	}

	public Date getDataSedutaAula() {
		return dataSedutaAula;
	}

	public void setDataSedutaAula(Date dataSedutaAula) {
		this.dataSedutaAula = dataSedutaAula;
	}

	public String getNumeroDgr() {
		return numeroDgr;
	}

	public void setNumeroDgr(String numeroDgr) {
		this.numeroDgr = numeroDgr;
	}

	public Date getDataDgr() {
		return dataDgr;
	}

	public void setDataDgr(Date dataDgr) {
		this.dataDgr = dataDgr;
	}

	public String getNumeroLcr() {
		return numeroLcr;
	}

	public void setNumeroLcr(String numeroLcr) {
		this.numeroLcr = numeroLcr;
	}

	public String getNumeroLr() {
		return numeroLr;
	}

	public void setNumeroLr(String numeroLr) {
		this.numeroLr = numeroLr;
	}

	public List <OrganismoStatutario> getOrganismiStatutari() {
		return organismiStatutari;
	}

	public void setOrganismiStatutari(List <OrganismoStatutario> organismiStatutari) {
		this.organismiStatutari = organismiStatutari;
	}

	public String getValutazioneAmmissibilita() {
		return valutazioneAmmissibilita;
	}

	public void setValutazioneAmmissibilita(String valutazioneAmmissibilita) {
		this.valutazioneAmmissibilita = valutazioneAmmissibilita;
	}

	public Date getDataRichiestaInformazioni() {
		return dataRichiestaInformazioni;
	}

	public void setDataRichiestaInformazioni(Date dataRichiestaInformazioni) {
		this.dataRichiestaInformazioni = dataRichiestaInformazioni;
	}

	public Date getDataRicevimentoInformazioni() {
		return dataRicevimentoInformazioni;
	}

	public void setDataRicevimentoInformazioni(Date dataRicevimentoInformazioni) {
		this.dataRicevimentoInformazioni = dataRicevimentoInformazioni;
	}

	public boolean isAiutiStato() {
		return aiutiStato;
	}

	public void setAiutiStato(boolean aiutiStato) {
		this.aiutiStato = aiutiStato;
	}

	public boolean isNormaFinanziaria() {
		return normaFinanziaria;
	}

	public void setNormaFinanziaria(boolean normaFinanziaria) {
		this.normaFinanziaria = normaFinanziaria;
	}

	public boolean isRichiestaUrgenza() {
		return richiestaUrgenza;
	}

	public void setRichiestaUrgenza(boolean richiestaUrgenza) {
		this.richiestaUrgenza = richiestaUrgenza;
	}

	public boolean isVotazioneUrgenza() {
		return votazioneUrgenza;
	}

	public void setVotazioneUrgenza(boolean votazioneUrgenza) {
		this.votazioneUrgenza = votazioneUrgenza;
	}

	public Date getDataVotazioneUrgenza() {
		return dataVotazioneUrgenza;
	}

	public void setDataVotazioneUrgenza(Date dataVotazioneUrgenza) {
		this.dataVotazioneUrgenza = dataVotazioneUrgenza;
	}

	public String getNoteAmmissibilita() {
		return noteAmmissibilita;
	}

	public void setNoteAmmissibilita(String noteAmmissibilita) {
		this.noteAmmissibilita = noteAmmissibilita;
	}

	public String getNoteNoteAllegatiPresentazioneAssegnazione() {
		return noteNoteAllegatiPresentazioneAssegnazione;
	}

	public void setNoteNoteAllegatiPresentazioneAssegnazione(
			String noteNoteAllegatiPresentazioneAssegnazione) {
		this.noteNoteAllegatiPresentazioneAssegnazione = noteNoteAllegatiPresentazioneAssegnazione;
	}

	public List <AttoRecord> getTestiAtto() {
		return testiAtto;
	}

	public void setTestiAtto(List <AttoRecord> testiAtto) {
		this.testiAtto = testiAtto;
	}

	
	

	
	
}
