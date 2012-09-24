package com.sourcesense.crl.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.sourcesense.crl.util.JsonDateSerializer;
import com.sourcesense.crl.util.JsonNoteDeserializer;

public class Aula implements Cloneable{
	
	
	private Date dataPresaInCaricoEsameAula;
	private String relazioneScritta;
	private String esitoVotoAula;
	private String tipologiaVotazione;
	private Date   dataSedutaAula;
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
	private Date dataIniziativaStralcio;
	
	private String articoli;
	private String noteStralcio;
	private String quorumEsameAula;
	
	private List<Allegato> allegatiEsameAula = new ArrayList<Allegato>();
	private List<Link> linksEsameAula = new ArrayList<Link>();
	
	private String noteGeneraliEsameAula;
	
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataSedutaAula() {
		return dataSedutaAula;
	}

	public void setDataSedutaAula(Date dataSedutaAula) {
		this.dataSedutaAula = dataSedutaAula;
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

	@JsonDeserialize(using=JsonNoteDeserializer.class)
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

	@JsonDeserialize(using=JsonNoteDeserializer.class)
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

	

	public String getArticoli() {
		return articoli;
	}

	public void setArticoli(String articoli) {
		this.articoli = articoli;
	}

	public String getNoteStralcio() {
		return noteStralcio;
	}
	
	@JsonDeserialize(using=JsonNoteDeserializer.class)
	public void setNoteStralcio(String noteStralcio) {
		this.noteStralcio = noteStralcio;
	}

	public String getQuorumEsameAula() {
		return quorumEsameAula;
	}

	public void setQuorumEsameAula(String quorumEsameAula) {
		this.quorumEsameAula = quorumEsameAula;
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
	public Date getDataPresaInCaricoEsameAula() {
		return dataPresaInCaricoEsameAula;
	}

	public void setDataPresaInCaricoEsameAula(Date dataPresaInCaricoEsameAula) {
		this.dataPresaInCaricoEsameAula = dataPresaInCaricoEsameAula;
	}


	public Date getDataIniziativaStralcio() {
		return dataIniziativaStralcio;
	}


	public void setDataIniziativaStralcio(Date dataIniziativaStralcio) {
		this.dataIniziativaStralcio = dataIniziativaStralcio;
	}
    
	public String getNoteGeneraliEsameAula() {
		return noteGeneraliEsameAula;
	}

	@JsonDeserialize(using=JsonNoteDeserializer.class)
	public void setNoteGeneraliEsameAula(String noteGeneraliEsameAula) {
		this.noteGeneraliEsameAula = noteGeneraliEsameAula;
	}




}
