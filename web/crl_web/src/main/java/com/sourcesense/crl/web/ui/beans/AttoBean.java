package com.sourcesense.crl.web.ui.beans;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.service.AttoServiceManager;

@ManagedBean(name = "attoBean")
@SessionScoped
public class AttoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean showCommDetail;
	private Atto atto = new Atto();
	private String codice;
	private String numeroAtto;
	private String tipoAtto;
	private String tipologia;
	private String legislatura;
	private Date dataPresaInCarico;
	
	private String statoAttuale;
	private String classificazione;
	private String numeroRepertorio;
	private Date dataRepertorio;
	private Date dataIniziativa;
	private String tipoIniziativa;
	private String oggetto;
    private Date dataAssegnazione;
    private String esitoValidazione;    
    private Date dataValidazione;
    private Date dataAssegnazioneCommissioni;
    

	private String descrizioneIniziativa;
    private String numeroDGR;
    private Date   dataDGR;
    private String assegnazione;
    private String id;
	private String tipo;
    private String primoFirmatario;
    private Date   dataPresentazione;
    private String stato;
    private String anno;
    
	private String numeroProtocollo;
	private String numeroDcr;
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

	

	@ManagedProperty(value = "#{attoServiceManager}")
	private AttoServiceManager attoServiceManager;

	/* Services */
	public AttoServiceManager getAttoServiceManager() {
		return attoServiceManager;
	}

	public void setAttoServiceManager(AttoServiceManager attoServiceManager) {
		this.attoServiceManager = attoServiceManager;
	}

	/* Fields */
	/**
	 * @return the code
	 */
	public String getCodice() {
		return atto.getCodice();
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCodice(String codice) {
		this.codice = codice;

//		if (codice != null)
//			setAtto(attoServiceManager.get(codice));
	}

	/**
	 * @return the atto
	 */
	public Atto getAtto() {
		return atto;
	}

	/**
	 * @param atto
	 *            the atto to set
	 */
	public void setAtto(Atto atto) {
		this.atto = atto;
	}

	public boolean isShowCommDetail() {
		return showCommDetail;
	}

	public void setShowCommDetail(boolean showCommDetail) {
		this.showCommDetail = showCommDetail;
	}

	public void visualizeCommDetail() {
		this.showCommDetail = true;
	}

	public String getNumeroAtto() {
		return atto.getNumeroAtto();
	}

	public void setNumeroAtto(String numeroAtto) {

		this.atto.setNumeroAtto(numeroAtto);
	}

	public String getTipoAtto() {
		return this.atto.getTipoAtto();
	}

	public void setTipoAtto(String tipoAtto) {
		this.atto.setTipoAtto(tipoAtto);
	}

	public String getTipologia() {
		return this.atto.getTipologia();
	}

	public void setTipologia(String tipologia) {
		this.atto.setTipologia(tipologia);
	}

	public String getLegislatura() {
		return this.atto.getLegislatura();
	}

	public void setLegislatura(String legislatura) {
		this.atto.setLegislatura(legislatura);
	}

	public Date getDataPresaInCarico() {
		return atto.getDataPresaInCarico();
	}

	public void setDataPresaInCarico(Date dataPresaInCarico) {
		this.atto.setDataPresaInCarico(dataPresaInCarico);
	}

	public String getStatoAttuale() {
		return atto.getStatoAttuale();
	}

	public void setStatoAttuale(String statoAttuale) {
		this.atto.setStatoAttuale(statoAttuale);
	}

	public String getClassificazione() {
		return atto.getClassificazione();
	}

	public void setClassificazione(String classificazione) {
		this.atto.setClassificazione(classificazione);
	}

	public String getnRepertorio() {
		return atto.getnRepertorio();
	}

	public void setDataRepertorio(Date dataRepertorio) {
		this.atto.setDataRepertorio(dataRepertorio);
	}

	public Date getDataIniziativa() {
		return atto.getDataIniziativa();
	}

	public void setDataIniziativa(Date dataIniziativa) {
		this.atto.setDataIniziativa(dataIniziativa);
	}

	public String getTipoIniziativa() {
		return atto.getTipoIniziativa();
	}

	public void setTipoIniziativa(String tipoIniziativa) {
		this.atto.setTipoIniziativa(tipoIniziativa);
	}

	public String getOggetto() {
		return atto.getOggetto();
	}

	public void setOggetto(String oggetto) {
		this.atto.setOggetto(oggetto);
	}

	public String getNumeroRepertorio() {
		return atto.getNumeroRepertorio();
	}

	public void setNumeroRepertorio(String numeroRepertorio) {
		this.atto.setNumeroRepertorio(numeroRepertorio);
	}

	public Date getDataRepertorio() {
		return atto.getDataRepertorio();
	}

	public Date getDataAssegnazione() {
		return atto.getDataAssegnazione();
	}

	public void setDataAssegnazione(Date dataAssegnazione) {
		this.atto.setDataAssegnazione(dataAssegnazione);
	}

	public String getEsitoValidazione() {
		return atto.getEsitoValidazione();
	}

	public void setEsitoValidazione(String esitoValidazione) {
		this.atto.setEsitoValidazione(esitoValidazione);
	}

	public Date getDataValidazione() {
		return atto.getDataValidazione();
	}

	public void setDataValidazione(Date dataValidazione) {
		this.atto.setDataValidazione(dataValidazione);
	}

	public Date getDataAssegnazioneCommissioni() {
		return atto.getDataAssegnazioneCommissioni();
	}

	public void setDataAssegnazioneCommissioni(Date dataAssegnazioneCommissioni) {
		this.atto.setDataAssegnazioneCommissioni(dataAssegnazioneCommissioni);
	}
	
	
	
	public String getDescrizioneIniziativa() {
		return atto.getDescrizioneIniziativa();
	}

	public void setDescrizioneIniziativa(String descrizioneIniziativa) {
		this.atto.setDescrizioneIniziativa(descrizioneIniziativa);
	}

	public String getNumeroDGR() {
		return atto.getNumeroDgr();
	}

	public void setNumeroDGR(String numeroDGR) {
		this.atto.setNumeroDgr(numeroDGR);
	}

	public Date getDataDGR() {
		return atto.getDataDgr();
	}

	public void setDataDGR(Date dataDGR) {
		this.atto.setDataDgr(dataDGR);
	}

	public String getAssegnazione() {
		return atto.getAssegnazione();
	}

	public void setAssegnazione(String assegnazione) {
		this.atto.setAssegnazione(assegnazione);
	}

	public String getId() {
		return atto.getId();
	}

	public void setId(String id) {
		this.atto.setId(id);
	}

	public String getTipo() {
		return atto.getTipo();
	}

	public void setTipo(String tipo) {
		this.atto.setTipo(tipo);
	}

	public String getPrimoFirmatario() {
		return atto.getPrimoFirmatario();
	}

	public void setPrimoFirmatario(String primoFirmatario) {
		this.atto.setPrimoFirmatario(primoFirmatario);
	}

	public Date getDataPresentazione() {
		return atto.getDataPresentazione();
	}

	public void setDataPresentazione(Date dataPresentazione) {
		this.atto.setDataPresentazione(dataPresentazione);
	}

	public String getStato() {
		return atto.getStato();
	}

	public void setStato(String stato) {
		this.atto.setStato(stato);
	}

	public String getAnno() {
		return atto.getAnno();
	}

	public void setAnno(String anno) {
		this.atto.setAnno(anno);
	}

	public String getNumeroProtocollo() {
		return atto.getNumeroProtocollo();
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.atto.setNumeroProtocollo(numeroProtocollo);
	}

	public String getNumeroDcr() {
		return atto.getNumeroDcr();
	}

	public void setNumeroDcr(String numeroDcr) {
		this.atto.setNumeroDcr(numeroDcr);
	}

	public String getFirmatario() {
		return atto.getFirmatario();
	}

	public void setFirmatario(String firmatario) {
		this.atto.setFirmatario(firmatario);
	}

	public String getTipoChiusura() {
		return atto.getTipoChiusura();
	}

	public void setTipoChiusura(String tipoChiusura) {
		this.atto.setTipoChiusura(tipoChiusura);
	}

	public String getEsitoVotoCommissioneReferente() {
		return atto.getEsitoVotoCommissioneReferente();
	}

	public void setEsitoVotoCommissioneReferente(
			String esitoVotoCommissioneReferente) {
		this.atto.setEsitoVotoCommissioneReferente(esitoVotoCommissioneReferente);
	}

	public String getEsitoVotoAula() {
		return atto.getEsitoVotoAula();
	}

	public void setEsitoVotoAula(String esitoVotoAula) {
		this.atto.setEsitoVotoAula(esitoVotoAula);
	}

	public String getCommissioneReferente() {
		return atto.getCommissioneReferente();
	}

	public void setCommissioneReferente(String commissioneReferente) {
		this.atto.setCommissioneReferente(commissioneReferente);
	}

	public String getCommissioneConsultiva() {
		return atto.getCommissioneConsultiva();
	}

	public void setCommissioneConsultiva(String commissioneConsultiva) {
		this.atto.setCommissioneConsultiva(commissioneConsultiva);
	}

	public boolean isRedigente() {
		return atto.isRedigente();
	}

	public void setRedigente(boolean redigente) {
		this.atto.setRedigente(redigente);
	}

	public boolean isDeliberante() {
		return atto.isDeliberante();
	}

	public void setDeliberante(boolean deliberante) {
		this.atto.setDeliberante(deliberante);
	}

	public String getNumeroLCR() {
		return atto.getNumeroLcr();
	}

	public void setNumeroLCR(String numeroLCR) {
		this.atto.setNumeroLcr(numeroLCR);
	}

	public String getNumeroLR() {
		return atto.getNumeroLr();
	}

	public void setNumeroLR(String numeroLR) {
		this.atto.setNumeroLr(numeroLR);
	}

	public boolean isAbbinamento() {
		return atto.isAbbinamento();
	}

	public void setAbbinamento(boolean abbinamento) {
		this.atto.setAbbinamento(abbinamento);
	}

	public boolean isStralcio() {
		return atto.isStralcio();
	}

	public void setStralcio(boolean stralcio) {
		this.atto.setStralcio(stralcio);
	}


	public String getOrganismoStatutario() {
		return atto.getOrganismoStatutario();
	}

	public void setOrganismoStatutario(String organismoStatutario) {
		this.atto.setOrganismoStatutario(organismoStatutario);
	}

	public String getSoggettoConsultato() {
		return atto.getSoggettoConsultato();
	}

	public void setSoggettoConsultato(String soggettoConsultato) {
		this.atto.setSoggettoConsultato(soggettoConsultato);
	}

	public boolean isEmendato() {
		return atto.isEmendato();
	}

	public void setEmendato(boolean emendato) {
		this.atto.setEmendato(emendato);
	}

	public boolean isRinviato() {
		return atto.isRinviato();
	}

	public void setRinviato(boolean rinviato) {
		this.atto.setRinviato(rinviato);
	}

	public boolean isSospeso() {
		return atto.isSospeso();
	}

	public void setSospeso(boolean sospeso) {
		this.atto.setSospeso(sospeso);
	}

	public String getValutazioneAmmissibilita() {
		return atto.getValutazioneAmmissibilita();
	}

	public void setValutazioneAmmissibilita(String valutazioneAmmissibilita) {
		this.atto.setValutazioneAmmissibilita(valutazioneAmmissibilita);
	}

	public Date getDataRichiestaInformazioni() {
		return atto.getDataRichiestaInformazioni();
	}

	public void setDataRichiestaInformazioni(Date dataRichiestaInformazioni) {
		this.atto.setDataRichiestaInformazioni(dataRichiestaInformazioni);
	}

	public Date getDataRicevimentoInformazioni() {
		return atto.getDataRicevimentoInformazioni();
	}

	public void setDataRicevimentoInformazioni(
			Date dataRicevimentoInformazioni) {
		this.atto.setDataRicevimentoInformazioni(dataRicevimentoInformazioni);
	}

	public boolean isAiutiStato() {
		return atto.isAiutiStato();
	}

	public void setAiutiStato(boolean aiutiStato) {
		this.atto.setAiutiStato(aiutiStato);
	}

	public boolean isNormaFinanziaria() {
		return atto.isNormaFinanziaria();
	}

	public void setNormaFinanziaria(boolean normaFinanziaria) {
		this.atto.setNormaFinanziaria(normaFinanziaria);
	}

	public boolean isRichiestaUrgenza() {
		return atto.isRichiestaUrgenza();
	}

	public void setRichiestaUrgenza(boolean richiestaUrgenza) {
		this.atto.setRichiestaUrgenza(richiestaUrgenza);
	}

	public boolean isVotazioneUrgenza() {
		return atto.isVotazioneUrgenza();
	}

	public void setVotazioneUrgenza(boolean votazioneUrgenza) {
		this.atto.setVotazioneUrgenza(votazioneUrgenza);
	}

	public Date getDataVotazioneUrgenza() {
		return atto.getDataVotazioneUrgenza();
	}

	public void setDataVotazioneUrgenza(Date dataVotazioneUrgenza) {
		this.atto.setDataVotazioneUrgenza(dataVotazioneUrgenza);
	}

	public String getNoteAmmissibilita() {
		return atto.getNoteAmmissibilita();
	}

	public void setNoteAmmissibilita(String noteAmmissibilita) {
		this.atto.setNoteAmmissibilita(noteAmmissibilita);
	}

	public String getNoteNoteAllegatiPresentazioneAssegnazione() {
		return atto.getNoteNoteAllegatiPresentazioneAssegnazione();
	}

	public void setNoteNoteAllegatiPresentazioneAssegnazione(
			String noteNoteAllegatiPresentazioneAssegnazione) {
		this.atto.setNoteNoteAllegatiPresentazioneAssegnazione(noteNoteAllegatiPresentazioneAssegnazione);
	}
	
}
