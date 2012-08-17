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

import com.sourcesense.crl.util.JsonDateSerializer;

/**@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
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
	private String numeroRepertorio;
	private String descrizioneIniziativa;
	private String numeroDgr;
	private Date   dataDgr;
	private String assegnazione;    
	private Date dataAssegnazione;
	private String esitoValidazione;
	private Date dataValidazione;

	private Date dataAssegnazioneCommissioni;


	private String numeroProtocollo;
	private String tipoIniziativa;
	private String firmatario;
	private String tipoChiusura;
	private String commissioneReferente;
	private String commissioneConsultiva;
	private boolean redigente;
	private boolean deliberante;
	private String numeroLr;
	private boolean abbinamento;	
	private boolean stralcio;
	private String organismoStatutario;	
	private String soggettoConsultato;	
	private boolean rinviato;	
	private boolean sospeso;
	private Date dataLR;
	private String numeroPubblicazioneBURL;
	private Date dataPubblicazioneBURL;
	private Date dataChiusura;
	private String statoChiusura;

	private List <Commissione> commissioni = new ArrayList<Commissione>();
	private List <Abbinamento> abbinamenti = new ArrayList<Abbinamento>();
	private List <Organo> organi = new ArrayList<Organo>();
	private List <Firmatario> firmatari = new ArrayList<Firmatario>();
	private List <Relatore> relatori = new ArrayList<Relatore>();
	private List <Parere> pareri = new ArrayList<Parere>();
	private List <Consultazione> consultazioni = new ArrayList<Consultazione>();
	private List <Atto> collegamenti = new ArrayList<Atto>();
	private List <Allegato> allegati = new ArrayList<Allegato>();
	private List <Allegato> allegatiNotePresentazioneAssegnazione = new ArrayList<Allegato>();
	private List <Allegato> allegatiNoteEsameCommissioni = new ArrayList<Allegato>();
	private List <Link> linksPresentazioneAssegnazione = new ArrayList<Link>();
	private List <Link> linksNoteEsameCommissioni = new ArrayList<Link>();
	private List <OrganismoStatutario> organismiStatutari = new ArrayList<OrganismoStatutario>();
	private List <AttoRecord> testiAtto = new ArrayList<AttoRecord>();
	private List <Allegato> testiAttoVotatoEsameCommissioni = new ArrayList<Allegato>();
	private List <Allegato> emendamentiEsameCommissioni = new ArrayList<Allegato>();
	private List <Allegato> testiClausola = new ArrayList<Allegato>();

	private Date dataPresaInCarico;
	private String valutazioneAmmissibilita;
	private Date dataRichiestaInformazioni;
	private Date dataRicevimentoInformazioni;
	private boolean aiutiStato;
	private boolean normaFinanziaria;
	private boolean richiestaUrgenza;
	private boolean votazioneUrgenza;
	private Date dataVotazioneUrgenza;
	private String noteAmmissibilita;	
	private String notePresentazioneAssegnazione;


	private Date dataPresaInCaricoEsameCommissioni;
	private String materia;
	private Date dataScadenzaEsameCommissioni;
	private boolean presenzaComitatoRistretto;
	private Date dataIstituzioneComitato;
	private ComitatoRistretto comitatoRistretto = new ComitatoRistretto();
	private Date dataFineLavoriEsameCommissioni;
	private String esitoVotoCommissioneReferente;
	private String quorumEsameCommissioni;
	private Date dataSedutaCommissione;
	private Date dataSedutaContinuazioneInReferente;
	private String motivazioniContinuazioneInReferente;
	private Date dataTrasmissione;
	private Date dataRichiestaIscrizioneAula;
	private boolean passaggioDirettoInAula;
	private int numEmendPresentatiMaggiorEsameCommissioni = 0;
	private int numEmendPresentatiMinorEsameCommissioni = 0;
	private int numEmendPresentatiGiuntaEsameCommissioni = 0;
	private int numEmendPresentatiMistoEsameCommissioni = 0;
	private int numEmendApprovatiMaggiorEsameCommissioni = 0;
	private int numEmendApprovatiMinorEsameCommissioni = 0;
	private int numEmendApprovatiGiuntaEsameCommissioni = 0;
	private int numEmendApprovatiMistoEsameCommissioni = 0;
	private int nonAmmissibiliEsameCommissioni = 0;
	private int decadutiEsameCommissioni = 0;
	private int ritiratiEsameCommissioni = 0;
	private int respintiEsameCommissioni = 0;
	private String noteEmendamentiEsameCommissioni;
	private Date dataPresaInCaricoProposta;
	private Date dataIntesa;
	private String esitoVotazioneIntesa;
	private String noteClausolaValutativa;
	private String noteGeneraliEsameCommissioni;


	private Date dataPresaInCaricoEsameAula;
	private String relazioneScritta;
	private String esitoVotoAula;
	private String tipologiaVotazione;
	private String numeroDcr;
	private String numeroLcr;
	private boolean emendato;
	private String noteVotazione;
	
	private List<Allegato> testiAttoVotatoEsameAula = new ArrayList<Allegato>();
	
	private List<Allegato> emendamentiEsameAula = new ArrayList<Allegato>();
	
	private int numEmendPresentatiMaggiorEsameAula;
	private int numEmendPresentatiMinorEsameAula;
	private int numEmendPresentatiGiuntaEsameAula;
	private int numEmendPresentatiMistoEsameAula;
	private int numEmendApprovatiMaggiorEsameAula;
	private int numEmendApprovatiMinorEsameAula;
	private int numEmendApprovatiGiuntaEsameAula;
	private int numEmendApprovatiMistoEsameAula;
	private int nonAmmissibiliEsameAula;
	private int decadutiEsameAula;
	private int ritiratiEsameAula;
	private int respintiEsameAula;
	private int totaleNonApprovatiEsameAula;
	private String noteEmendamentiEsameAula;
	
	private Date dataSedutaRinvio;
	private Date dataTermineMassimo;
	private String motivazioneRinvio;
	private Date dataSedutaStralcio;
	private Date dataStralcio;
	private Date dataIniziativa;
	private String articoli;
	private String noteStralcio;
	private String quorumEsameAula;
	
	private String noteGeneraliEsameAula;
	
	private List<Allegato> allegatiEsameAula = new ArrayList<Allegato>();
	private List<Link> linksEsameAula = new ArrayList<Link>();
	
	private String noteChiusuraIter;

	public Atto(){

		Commissione commissione = new Commissione();
		commissione.setDescrizione("Descrizione1");
		commissione.setNome("Commissione 1");
		commissione.setRuolo("Ruolo commissione 1");
		commissione.setStato("Stato comm 1");
		commissione.setDataAssegnazione(new Date());
		commissione.setDataPresaInCarico(new Date());
		commissione.setTipoVotazione("tipo lavorazione 1");
		commissione.setEsitoVotazione("Esito votazione 1");
		commissione.setDataVotazione(new Date());
		commissione.setDataTrasmissione(new Date());
		commissione.setDataChiusuraLavoriComitato(new Date());
		commissione.setDataAperturaLavoriComitato(new Date());
		commissione.setDataNomina(new Date());
		commissione.setDataProposta(new Date());
		commissione.setDataAnnullo(new Date());


		Commissione commissione2 = new Commissione();
		commissione2.setDescrizione("Descrizione2");
		commissione2.setNome("Commissione 2");
		commissione2.setRuolo("Ruolo commissione 2");
		commissione2.setStato("Stato comm 2");
		commissione2.setDataAssegnazione(new Date());
		commissione2.setDataPresaInCarico(new Date());
		commissione2.setTipoVotazione("tipo lavorazione 2");
		commissione2.setEsitoVotazione("Esito votazione 2");
		commissione2.setDataVotazione(new Date());
		commissione2.setDataTrasmissione(new Date());
		commissione2.setDataChiusuraLavoriComitato(new Date());
		commissione2.setDataAperturaLavoriComitato(new Date());
		commissione2.setDataNomina(new Date());
		commissione2.setDataProposta(new Date());
		commissione2.setDataAnnullo(new Date());



		List <Commissione> commissioni = new ArrayList<Commissione>();


		commissioni.add(commissione);
		commissioni.add(commissione2);

		this.setCommissioni(commissioni);

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getPrimoFirmatario() {
		return primoFirmatario;
	}

	public void setPrimoFirmatario(String primoFirmatario) {
		this.primoFirmatario = primoFirmatario;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataPresentazione() {
		return dataPresentazione;
	}

	public void setDataPresentazione(Date dataPresentazione) {
		this.dataPresentazione = dataPresentazione;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataPubblicazione() {
		return dataPubblicazione;
	}

	public void setDataPubblicazione(Date dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataSedutaSc() {
		return dataSedutaSc;
	}

	public void setDataSedutaSc(Date dataSedutaSc) {
		this.dataSedutaSc = dataSedutaSc;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataSedutaAula() {
		return dataSedutaAula;
	}

	public void setDataSedutaAula(Date dataSedutaAula) {
		this.dataSedutaAula = dataSedutaAula;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
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

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getStatoAttuale() {
		return statoAttuale;
	}

	public void setStatoAttuale(String statoAttuale) {
		this.statoAttuale = statoAttuale;
	}

	public String getClassificazione() {
		return classificazione;
	}

	public void setClassificazione(String classificazione) {
		this.classificazione = classificazione;
	}

	public String getnRepertorio() {
		return nRepertorio;
	}

	public void setnRepertorio(String nRepertorio) {
		this.nRepertorio = nRepertorio;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataRepertorio() {
		return dataRepertorio;
	}

	public void setDataRepertorio(Date dataRepertorio) {
		this.dataRepertorio = dataRepertorio;
	}

	public String getNumeroRepertorio() {
		return numeroRepertorio;
	}

	public void setNumeroRepertorio(String numeroRepertorio) {
		this.numeroRepertorio = numeroRepertorio;
	}

	public String getDescrizioneIniziativa() {
		return descrizioneIniziativa;
	}

	public void setDescrizioneIniziativa(String descrizioneIniziativa) {
		this.descrizioneIniziativa = descrizioneIniziativa;
	}

	public String getNumeroDgr() {
		return numeroDgr;
	}

	public void setNumeroDgr(String numeroDgr) {
		this.numeroDgr = numeroDgr;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataDgr() {
		return dataDgr;
	}

	public void setDataDgr(Date dataDgr) {
		this.dataDgr = dataDgr;
	}

	public String getAssegnazione() {
		return assegnazione;
	}

	public void setAssegnazione(String assegnazione) {
		this.assegnazione = assegnazione;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
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

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataValidazione() {
		return dataValidazione;
	}

	public void setDataValidazione(Date dataValidazione) {
		this.dataValidazione = dataValidazione;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataAssegnazioneCommissioni() {
		return dataAssegnazioneCommissioni;
	}

	public void setDataAssegnazioneCommissioni(Date dataAssegnazioneCommissioni) {
		this.dataAssegnazioneCommissioni = dataAssegnazioneCommissioni;
	}

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public String getTipoIniziativa() {
		return tipoIniziativa;
	}

	public void setTipoIniziativa(String tipoIniziativa) {
		this.tipoIniziativa = tipoIniziativa;
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

	public String getNumeroLr() {
		return numeroLr;
	}

	public void setNumeroLr(String numeroLr) {
		this.numeroLr = numeroLr;
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

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataLR() {
		return dataLR;
	}

	public void setDataLR(Date dataLR) {
		this.dataLR = dataLR;
	}

	public String getNumeroPubblicazioneBURL() {
		return numeroPubblicazioneBURL;
	}

	public void setNumeroPubblicazioneBURL(String numeroPubblicazioneBURL) {
		this.numeroPubblicazioneBURL = numeroPubblicazioneBURL;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataPubblicazioneBURL() {
		return dataPubblicazioneBURL;
	}

	public void setDataPubblicazioneBURL(Date dataPubblicazioneBURL) {
		this.dataPubblicazioneBURL = dataPubblicazioneBURL;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataChiusura() {
		return dataChiusura;
	}

	public void setDataChiusura(Date dataChiusura) {
		this.dataChiusura = dataChiusura;
	}

	public String getStatoChiusura() {
		return statoChiusura;
	}

	public void setStatoChiusura(String statoChiusura) {
		this.statoChiusura = statoChiusura;
	}

	public List<Commissione> getCommissioni() {
		return commissioni;
	}

	public void setCommissioni(List<Commissione> commissioni) {
		this.commissioni = commissioni;
	}

	public List<Abbinamento> getAbbinamenti() {
		return abbinamenti;
	}

	public void setAbbinamenti(List<Abbinamento> abbinamenti) {
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

	public List<Relatore> getRelatori() {
		return relatori;
	}

	public void setRelatori(List<Relatore> relatori) {
		this.relatori = relatori;
	}

	public List<Parere> getPareri() {
		return pareri;
	}

	public void setPareri(List<Parere> pareri) {
		this.pareri = pareri;
	}

	public List<Consultazione> getConsultazioni() {
		return consultazioni;
	}

	public void setConsultazioni(List<Consultazione> consultazioni) {
		this.consultazioni = consultazioni;
	}

	public List<Atto> getCollegamenti() {
		return collegamenti;
	}

	public void setCollegamenti(List<Atto> collegamenti) {
		this.collegamenti = collegamenti;
	}

	public List<Allegato> getAllegati() {
		return allegati;
	}

	public void setAllegati(List<Allegato> allegati) {
		this.allegati = allegati;
	}

	public List<Allegato> getAllegatiNotePresentazioneAssegnazione() {
		return allegatiNotePresentazioneAssegnazione;
	}

	public void setAllegatiNotePresentazioneAssegnazione(
			List<Allegato> allegatiNotePresentazioneAssegnazione) {
		this.allegatiNotePresentazioneAssegnazione = allegatiNotePresentazioneAssegnazione;
	}

	public List<Allegato> getAllegatiNoteEsameCommissioni() {
		return allegatiNoteEsameCommissioni;
	}

	public void setAllegatiNoteEsameCommissioni(
			List<Allegato> allegatiNoteEsameCommissioni) {
		this.allegatiNoteEsameCommissioni = allegatiNoteEsameCommissioni;
	}

	public List<Link> getLinksPresentazioneAssegnazione() {
		return linksPresentazioneAssegnazione;
	}

	public void setLinksPresentazioneAssegnazione(
			List<Link> linksPresentazioneAssegnazione) {
		this.linksPresentazioneAssegnazione = linksPresentazioneAssegnazione;
	}

	public List<Link> getLinksNoteEsameCommissioni() {
		return linksNoteEsameCommissioni;
	}

	public void setLinksNoteEsameCommissioni(List<Link> linksNoteEsameCommissioni) {
		this.linksNoteEsameCommissioni = linksNoteEsameCommissioni;
	}

	public List<OrganismoStatutario> getOrganismiStatutari() {
		return organismiStatutari;
	}

	public void setOrganismiStatutari(List<OrganismoStatutario> organismiStatutari) {
		this.organismiStatutari = organismiStatutari;
	}

	public List<AttoRecord> getTestiAtto() {
		return testiAtto;
	}

	public void setTestiAtto(List<AttoRecord> testiAtto) {
		this.testiAtto = testiAtto;
	}

	public List<Allegato> getTestiAttoVotatoEsameCommissioni() {
		return testiAttoVotatoEsameCommissioni;
	}

	public void setTestiAttoVotatoEsameCommissioni(
			List<Allegato> testiAttoVotatoEsameCommissioni) {
		this.testiAttoVotatoEsameCommissioni = testiAttoVotatoEsameCommissioni;
	}

	public List<Allegato> getEmendamentiEsameCommissioni() {
		return emendamentiEsameCommissioni;
	}

	public void setEmendamentiEsameCommissioni(
			List<Allegato> emendamentiEsameCommissioni) {
		this.emendamentiEsameCommissioni = emendamentiEsameCommissioni;
	}

	public List<Allegato> getTestiClausola() {
		return testiClausola;
	}

	public void setTestiClausola(List<Allegato> testiClausola) {
		this.testiClausola = testiClausola;
	}

	public String getValutazioneAmmissibilita() {
		return valutazioneAmmissibilita;
	}

	public void setValutazioneAmmissibilita(String valutazioneAmmissibilita) {
		this.valutazioneAmmissibilita = valutazioneAmmissibilita;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataRichiestaInformazioni() {
		return dataRichiestaInformazioni;
	}

	public void setDataRichiestaInformazioni(Date dataRichiestaInformazioni) {
		this.dataRichiestaInformazioni = dataRichiestaInformazioni;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
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

	@JsonSerialize(using=JsonDateSerializer.class)
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

	public String getNotePresentazioneAssegnazione() {
		return notePresentazioneAssegnazione;
	}

	public void setNotePresentazioneAssegnazione(
			String notePresentazioneAssegnazione) {
		this.notePresentazioneAssegnazione = notePresentazioneAssegnazione;
	}

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}
	
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataScadenzaEsameCommissioni() {
		return dataScadenzaEsameCommissioni;
	}

	public void setDataScadenzaEsameCommissioni(Date dataScadenzaEsameCommissioni) {
		this.dataScadenzaEsameCommissioni = dataScadenzaEsameCommissioni;
	}

	public boolean isPresenzaComitatoRistretto() {
		return presenzaComitatoRistretto;
	}

	public void setPresenzaComitatoRistretto(boolean presenzaComitatoRistretto) {
		this.presenzaComitatoRistretto = presenzaComitatoRistretto;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataIstituzioneComitato() {
		return dataIstituzioneComitato;
	}

	public void setDataIstituzioneComitato(Date dataIstituzioneComitato) {
		this.dataIstituzioneComitato = dataIstituzioneComitato;
	}

	public ComitatoRistretto getComitatoRistretto() {
		return comitatoRistretto;
	}

	public void setComitatoRistretto(ComitatoRistretto comitatoRistretto) {
		this.comitatoRistretto = comitatoRistretto;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataFineLavoriEsameCommissioni() {
		return dataFineLavoriEsameCommissioni;
	}

	public void setDataFineLavoriEsameCommissioni(
			Date dataFineLavoriEsameCommissioni) {
		this.dataFineLavoriEsameCommissioni = dataFineLavoriEsameCommissioni;
	}

	public String getEsitoVotoCommissioneReferente() {
		return esitoVotoCommissioneReferente;
	}

	public void setEsitoVotoCommissioneReferente(
			String esitoVotoCommissioneReferente) {
		this.esitoVotoCommissioneReferente = esitoVotoCommissioneReferente;
	}

	public String getQuorumEsameCommissioni() {
		return quorumEsameCommissioni;
	}

	public void setQuorumEsameCommissioni(String quorumEsameCommissioni) {
		this.quorumEsameCommissioni = quorumEsameCommissioni;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataSedutaCommissione() {
		return dataSedutaCommissione;
	}

	public void setDataSedutaCommissione(Date dataSedutaCommissione) {
		this.dataSedutaCommissione = dataSedutaCommissione;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataSedutaContinuazioneInReferente() {
		return dataSedutaContinuazioneInReferente;
	}

	public void setDataSedutaContinuazioneInReferente(
			Date dataSedutaContinuazioneInReferente) {
		this.dataSedutaContinuazioneInReferente = dataSedutaContinuazioneInReferente;
	}

	public String getMotivazioniContinuazioneInReferente() {
		return motivazioniContinuazioneInReferente;
	}

	public void setMotivazioniContinuazioneInReferente(
			String motivazioniContinuazioneInReferente) {
		this.motivazioniContinuazioneInReferente = motivazioniContinuazioneInReferente;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataTrasmissione() {
		return dataTrasmissione;
	}

	public void setDataTrasmissione(Date dataTrasmissione) {
		this.dataTrasmissione = dataTrasmissione;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataRichiestaIscrizioneAula() {
		return dataRichiestaIscrizioneAula;
	}

	public void setDataRichiestaIscrizioneAula(Date dataRichiestaIscrizioneAula) {
		this.dataRichiestaIscrizioneAula = dataRichiestaIscrizioneAula;
	}

	public boolean isPassaggioDirettoInAula() {
		return passaggioDirettoInAula;
	}

	public void setPassaggioDirettoInAula(boolean passaggioDirettoInAula) {
		this.passaggioDirettoInAula = passaggioDirettoInAula;
	}

	public int getNumEmendPresentatiMaggiorEsameCommissioni() {
		return numEmendPresentatiMaggiorEsameCommissioni;
	}

	public void setNumEmendPresentatiMaggiorEsameCommissioni(
			int numEmendPresentatiMaggiorEsameCommissioni) {
		this.numEmendPresentatiMaggiorEsameCommissioni = numEmendPresentatiMaggiorEsameCommissioni;
	}

	public int getNumEmendPresentatiMinorEsameCommissioni() {
		return numEmendPresentatiMinorEsameCommissioni;
	}

	public void setNumEmendPresentatiMinorEsameCommissioni(
			int numEmendPresentatiMinorEsameCommissioni) {
		this.numEmendPresentatiMinorEsameCommissioni = numEmendPresentatiMinorEsameCommissioni;
	}

	public int getNumEmendPresentatiGiuntaEsameCommissioni() {
		return numEmendPresentatiGiuntaEsameCommissioni;
	}

	public void setNumEmendPresentatiGiuntaEsameCommissioni(
			int numEmendPresentatiGiuntaEsameCommissioni) {
		this.numEmendPresentatiGiuntaEsameCommissioni = numEmendPresentatiGiuntaEsameCommissioni;
	}

	public int getNumEmendPresentatiMistoEsameCommissioni() {
		return numEmendPresentatiMistoEsameCommissioni;
	}

	public void setNumEmendPresentatiMistoEsameCommissioni(
			int numEmendPresentatiMistoEsameCommissioni) {
		this.numEmendPresentatiMistoEsameCommissioni = numEmendPresentatiMistoEsameCommissioni;
	}

	public int getNumEmendApprovatiMaggiorEsameCommissioni() {
		return numEmendApprovatiMaggiorEsameCommissioni;
	}

	public void setNumEmendApprovatiMaggiorEsameCommissioni(
			int numEmendApprovatiMaggiorEsameCommissioni) {
		this.numEmendApprovatiMaggiorEsameCommissioni = numEmendApprovatiMaggiorEsameCommissioni;
	}

	public int getNumEmendApprovatiMinorEsameCommissioni() {
		return numEmendApprovatiMinorEsameCommissioni;
	}

	public void setNumEmendApprovatiMinorEsameCommissioni(
			int numEmendApprovatiMinorEsameCommissioni) {
		this.numEmendApprovatiMinorEsameCommissioni = numEmendApprovatiMinorEsameCommissioni;
	}

	public int getNumEmendApprovatiGiuntaEsameCommissioni() {
		return numEmendApprovatiGiuntaEsameCommissioni;
	}

	public void setNumEmendApprovatiGiuntaEsameCommissioni(
			int numEmendApprovatiGiuntaEsameCommissioni) {
		this.numEmendApprovatiGiuntaEsameCommissioni = numEmendApprovatiGiuntaEsameCommissioni;
	}

	public int getNumEmendApprovatiMistoEsameCommissioni() {
		return numEmendApprovatiMistoEsameCommissioni;
	}

	public void setNumEmendApprovatiMistoEsameCommissioni(
			int numEmendApprovatiMistoEsameCommissioni) {
		this.numEmendApprovatiMistoEsameCommissioni = numEmendApprovatiMistoEsameCommissioni;
	}

	public int getNonAmmissibiliEsameCommissioni() {
		return nonAmmissibiliEsameCommissioni;
	}

	public void setNonAmmissibiliEsameCommissioni(int nonAmmissibiliEsameCommissioni) {
		this.nonAmmissibiliEsameCommissioni = nonAmmissibiliEsameCommissioni;
	}

	public int getDecadutiEsameCommissioni() {
		return decadutiEsameCommissioni;
	}

	public void setDecadutiEsameCommissioni(int decadutiEsameCommissioni) {
		this.decadutiEsameCommissioni = decadutiEsameCommissioni;
	}

	public int getRitiratiEsameCommissioni() {
		return ritiratiEsameCommissioni;
	}

	public void setRitiratiEsameCommissioni(int ritiratiEsameCommissioni) {
		this.ritiratiEsameCommissioni = ritiratiEsameCommissioni;
	}

	public int getRespintiEsameCommissioni() {
		return respintiEsameCommissioni;
	}

	public void setRespintiEsameCommissioni(int respintiEsameCommissioni) {
		this.respintiEsameCommissioni = respintiEsameCommissioni;
	}

	public String getNoteEmendamentiEsameCommissioni() {
		return noteEmendamentiEsameCommissioni;
	}

	public void setNoteEmendamentiEsameCommissioni(
			String noteEmendamentiEsameCommissioni) {
		this.noteEmendamentiEsameCommissioni = noteEmendamentiEsameCommissioni;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataPresaInCaricoProposta() {
		return dataPresaInCaricoProposta;
	}

	public void setDataPresaInCaricoProposta(Date dataPresaInCaricoProposta) {
		this.dataPresaInCaricoProposta = dataPresaInCaricoProposta;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataIntesa() {
		return dataIntesa;
	}

	public void setDataIntesa(Date dataIntesa) {
		this.dataIntesa = dataIntesa;
	}

	public String getEsitoVotazioneIntesa() {
		return esitoVotazioneIntesa;
	}

	public void setEsitoVotazioneIntesa(String esitoVotazioneIntesa) {
		this.esitoVotazioneIntesa = esitoVotazioneIntesa;
	}

	public String getNoteClausolaValutativa() {
		return noteClausolaValutativa;
	}

	public void setNoteClausolaValutativa(String noteClausolaValutativa) {
		this.noteClausolaValutativa = noteClausolaValutativa;
	}

	public String getNoteGeneraliEsameCommissioni() {
		return noteGeneraliEsameCommissioni;
	}

	public void setNoteGeneraliEsameCommissioni(String noteGeneraliEsameCommissioni) {
		this.noteGeneraliEsameCommissioni = noteGeneraliEsameCommissioni;
	}

	public String getRelazioneScritta() {
		return relazioneScritta;
	}

	public void setRelazioneScritta(String relazioneScritta) {
		this.relazioneScritta = relazioneScritta;
	}

	public String getEsitoVotoAula() {
		return esitoVotoAula;
	}

	public void setEsitoVotoAula(String esitoVotoAula) {
		this.esitoVotoAula = esitoVotoAula;
	}

	public String getTipologiaVotazione() {
		return tipologiaVotazione;
	}

	public void setTipologiaVotazione(String tipologiaVotazione) {
		this.tipologiaVotazione = tipologiaVotazione;
	}

	public String getNumeroDcr() {
		return numeroDcr;
	}

	public void setNumeroDcr(String numeroDcr) {
		this.numeroDcr = numeroDcr;
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

	public String getNoteVotazione() {
		return noteVotazione;
	}

	public void setNoteVotazione(String noteVotazione) {
		this.noteVotazione = noteVotazione;
	}

	public List<Allegato> getTestiAttoVotatoEsameAula() {
		return testiAttoVotatoEsameAula;
	}

	public void setTestiAttoVotatoEsameAula(List<Allegato> testiAttoVotatoEsameAula) {
		this.testiAttoVotatoEsameAula = testiAttoVotatoEsameAula;
	}

	public List<Allegato> getEmendamentiEsameAula() {
		return emendamentiEsameAula;
	}

	public void setEmendamentiEsameAula(List<Allegato> emendamentiEsameAula) {
		this.emendamentiEsameAula = emendamentiEsameAula;
	}

	public int getNumEmendPresentatiMaggiorEsameAula() {
		return numEmendPresentatiMaggiorEsameAula;
	}

	public void setNumEmendPresentatiMaggiorEsameAula(
			int numEmendPresentatiMaggiorEsameAula) {
		this.numEmendPresentatiMaggiorEsameAula = numEmendPresentatiMaggiorEsameAula;
	}

	public int getNumEmendPresentatiMinorEsameAula() {
		return numEmendPresentatiMinorEsameAula;
	}

	public void setNumEmendPresentatiMinorEsameAula(
			int numEmendPresentatiMinorEsameAula) {
		this.numEmendPresentatiMinorEsameAula = numEmendPresentatiMinorEsameAula;
	}

	public int getNumEmendPresentatiGiuntaEsameAula() {
		return numEmendPresentatiGiuntaEsameAula;
	}

	public void setNumEmendPresentatiGiuntaEsameAula(
			int numEmendPresentatiGiuntaEsameAula) {
		this.numEmendPresentatiGiuntaEsameAula = numEmendPresentatiGiuntaEsameAula;
	}

	public int getNumEmendPresentatiMistoEsameAula() {
		return numEmendPresentatiMistoEsameAula;
	}

	public void setNumEmendPresentatiMistoEsameAula(
			int numEmendPresentatiMistoEsameAula) {
		this.numEmendPresentatiMistoEsameAula = numEmendPresentatiMistoEsameAula;
	}

	public int getNumEmendApprovatiMaggiorEsameAula() {
		return numEmendApprovatiMaggiorEsameAula;
	}

	public void setNumEmendApprovatiMaggiorEsameAula(
			int numEmendApprovatiMaggiorEsameAula) {
		this.numEmendApprovatiMaggiorEsameAula = numEmendApprovatiMaggiorEsameAula;
	}

	public int getNumEmendApprovatiMinorEsameAula() {
		return numEmendApprovatiMinorEsameAula;
	}

	public void setNumEmendApprovatiMinorEsameAula(
			int numEmendApprovatiMinorEsameAula) {
		this.numEmendApprovatiMinorEsameAula = numEmendApprovatiMinorEsameAula;
	}

	public int getNumEmendApprovatiGiuntaEsameAula() {
		return numEmendApprovatiGiuntaEsameAula;
	}

	public void setNumEmendApprovatiGiuntaEsameAula(
			int numEmendApprovatiGiuntaEsameAula) {
		this.numEmendApprovatiGiuntaEsameAula = numEmendApprovatiGiuntaEsameAula;
	}

	public int getNumEmendApprovatiMistoEsameAula() {
		return numEmendApprovatiMistoEsameAula;
	}

	public void setNumEmendApprovatiMistoEsameAula(
			int numEmendApprovatiMistoEsameAula) {
		this.numEmendApprovatiMistoEsameAula = numEmendApprovatiMistoEsameAula;
	}

	public int getNonAmmissibiliEsameAula() {
		return nonAmmissibiliEsameAula;
	}

	public void setNonAmmissibiliEsameAula(int nonAmmissibiliEsameAula) {
		this.nonAmmissibiliEsameAula = nonAmmissibiliEsameAula;
	}

	public int getDecadutiEsameAula() {
		return decadutiEsameAula;
	}

	public void setDecadutiEsameAula(int decadutiEsameAula) {
		this.decadutiEsameAula = decadutiEsameAula;
	}

	public int getRitiratiEsameAula() {
		return ritiratiEsameAula;
	}

	public void setRitiratiEsameAula(int ritiratiEsameAula) {
		this.ritiratiEsameAula = ritiratiEsameAula;
	}

	public int getRespintiEsameAula() {
		return respintiEsameAula;
	}

	public void setRespintiEsameAula(int respintiEsameAula) {
		this.respintiEsameAula = respintiEsameAula;
	}

	public int getTotaleNonApprovatiEsameAula() {
		return totaleNonApprovatiEsameAula;
	}

	public void setTotaleNonApprovatiEsameAula(int totaleNonApprovatiEsameAula) {
		this.totaleNonApprovatiEsameAula = totaleNonApprovatiEsameAula;
	}

	public String getNoteEmendamentiEsameAula() {
		return noteEmendamentiEsameAula;
	}

	public void setNoteEmendamentiEsameAula(String noteEmendamentiEsameAula) {
		this.noteEmendamentiEsameAula = noteEmendamentiEsameAula;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataSedutaRinvio() {
		return dataSedutaRinvio;
	}

	public void setDataSedutaRinvio(Date dataSedutaRinvio) {
		this.dataSedutaRinvio = dataSedutaRinvio;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataTermineMassimo() {
		return dataTermineMassimo;
	}

	public void setDataTermineMassimo(Date dataTermineMassimo) {
		this.dataTermineMassimo = dataTermineMassimo;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataSedutaStralcio() {
		return dataSedutaStralcio;
	}

	public void setDataSedutaStralcio(Date dataSedutaStralcio) {
		this.dataSedutaStralcio = dataSedutaStralcio;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataStralcio() {
		return dataStralcio;
	}

	public void setDataStralcio(Date dataStralcio) {
		this.dataStralcio = dataStralcio;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataIniziativa() {
		return dataIniziativa;
	}

	public void setDataIniziativa(Date dataIniziativa) {
		this.dataIniziativa = dataIniziativa;
	}

	public String getArticoli() {
		return articoli;
	}

	public void setArticoli(String articoli) {
		this.articoli = articoli;
	}

	public String getNoteStralcio() {
		return noteStralcio;
	}

	public void setNoteStralcio(String noteStralcio) {
		this.noteStralcio = noteStralcio;
	}

	public String getQuorumEsameAula() {
		return quorumEsameAula;
	}

	public void setQuorumEsameAula(String quorumEsameAula) {
		this.quorumEsameAula = quorumEsameAula;
	}

	public String getNoteGeneraliEsameAula() {
		return noteGeneraliEsameAula;
	}

	public void setNoteGeneraliEsameAula(String noteGeneraliEsameAula) {
		this.noteGeneraliEsameAula = noteGeneraliEsameAula;
	}

	public List<Allegato> getAllegatiEsameAula() {
		return allegatiEsameAula;
	}

	public void setAllegatiEsameAula(List<Allegato> allegatiEsameAula) {
		this.allegatiEsameAula = allegatiEsameAula;
	}

	public List<Link> getLinksEsameAula() {
		return linksEsameAula;
	}

	public void setLinksEsameAula(List<Link> linksEsameAula) {
		this.linksEsameAula = linksEsameAula;
	}

	public String getMotivazioneRinvio() {
		return motivazioneRinvio;
	}

	public void setMotivazioneRinvio(String motivazioneRinvio) {
		this.motivazioneRinvio = motivazioneRinvio;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataPresaInCarico() {
		return dataPresaInCarico;
	}

	public void setDataPresaInCarico(
			Date dataPresaInCarico) {
		this.dataPresaInCarico = dataPresaInCarico;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataPresaInCaricoEsameCommissioni() {
		return dataPresaInCaricoEsameCommissioni;
	}

	public void setDataPresaInCaricoEsameCommissioni(
			Date dataPresaInCaricoEsameCommissioni) {
		this.dataPresaInCaricoEsameCommissioni = dataPresaInCaricoEsameCommissioni;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataPresaInCaricoEsameAula() {
		return dataPresaInCaricoEsameAula;
	}

	public void setDataPresaInCaricoEsameAula(Date dataPresaInCaricoEsameAula) {
		this.dataPresaInCaricoEsameAula = dataPresaInCaricoEsameAula;
	}

	public String getNoteChiusuraIter() {
		return noteChiusuraIter;
	}

	public void setNoteChiusuraIter(String noteChiusuraIter) {
		this.noteChiusuraIter = noteChiusuraIter;
	}	


}

