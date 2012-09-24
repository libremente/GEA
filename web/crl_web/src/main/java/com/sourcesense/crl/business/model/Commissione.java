package com.sourcesense.crl.business.model;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.sourcesense.crl.util.JsonDateSerializer;
import com.sourcesense.crl.util.JsonNoteDeserializer;


@JsonRootName("commissione")
@JsonTypeName("commissione")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@AutoProperty
public class Commissione implements Cloneable {
	
	public static final String STATO_PROPOSTO = "Proposto";
	public static final String STATO_ASSEGNATO = "Assegnato";
	public static final String STATO_IN_CARICO = "In Carico";
	public static final String STATO_ANNULLATO = "Annullato";
	
	public static final String RUOLO_REFERENTE = "Referente";
	public static final String RUOLO_COREFERENTE = "Co-Referente";
	public static final String RUOLO_CONSULTIVA = "Consultiva";
	public static final String RUOLO_REDIGENTE = "Redigente";
	public static final String RUOLO_DELIBERANTE = "Deliberante";
	
	private String commissione;
	private String descrizione;
	private String nome;
	private String ruolo;
	private String stato;
	private String tipoVotazione;
	private String esitoVotazione;
	private String materia;
	private String esitoVotoCommissioneReferente;
	private String quorumEsameCommissioni;
	private String motivazioniContinuazioneInReferente;
	private String noteEmendamentiEsameCommissioni;
	private String noteGeneraliEsameCommissione;
	private String esitoVotazioneIntesa;
	private String noteClausolaValutativa;
	
	private Date dataAssegnazione;
	private Date dataPresaInCarico;
	private Date dataVotazione;
	private Date dataTrasmissione;
	private Date dataNomina;
	private Date dataFineLavoriComitato;
	private Date dataScadenzaEsameCommissioni;
	private Date dataProposta;
	private Date dataAnnullo;
	private Date dataScadenza;
	private Date dataSedutaCommissione;
	private Date dataSedutaContinuazioneInReferente;
	private Date dataIstituzioneComitato;
	private Date dataRichiestaIscrizioneAula;
	private Date dataPresaInCaricoProposta;
	private Date dataIntesa;
	
	
	private List <Relatore> relatori = new ArrayList<Relatore>();
	private List <Link> linksNoteEsameCommissione = new ArrayList<Link>();
	private List <Allegato> testiAttoVotatoEsameCommissioni = new ArrayList<Allegato>();
	private List <Allegato> emendamentiEsameCommissioni = new ArrayList<Allegato>();
	private List <Allegato> allegatiNoteEsameCommissioni = new ArrayList<Allegato>();
	private List <Allegato> testiClausola = new ArrayList<Allegato>();
	
	private boolean annullata = false;
	private boolean presenzaComitatoRistretto;
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
	
	
	private ComitatoRistretto comitatoRistretto = new ComitatoRistretto();
	
	
	@Override public String toString() {
	    return Pojomatic.toString(this);
	 }
	
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
	

	
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataScadenzaEsameCommissioni() {
		return dataScadenzaEsameCommissioni;
	}

	public void setDataScadenzaEsameCommissioni(Date dataScadenzaEsameCommissioni) {
		this.dataScadenzaEsameCommissioni = dataScadenzaEsameCommissioni;
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

	public List<Allegato> getAllegatiNoteEsameCommissioni() {
		return allegatiNoteEsameCommissioni;
	}

	public void setAllegatiNoteEsameCommissioni(
			List<Allegato> allegatiNoteEsameCommissioni) {
		this.allegatiNoteEsameCommissioni = allegatiNoteEsameCommissioni;
	}

	public String getCommissione() {
		return commissione;
	}

	public void setCommissione(String commissione) {
		this.commissione = commissione;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataAssegnazione() {
		return dataAssegnazione;
	}

	public void setDataAssegnazione(Date dataAssegnazione) {
		this.dataAssegnazione = dataAssegnazione;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataPresaInCarico() {
		return dataPresaInCarico;
	}

	public void setDataPresaInCarico(Date dataPresaInCarico) {
		this.dataPresaInCarico = dataPresaInCarico;
	}

	public String getTipoVotazione() {
		return tipoVotazione;
	}

	public void setTipoVotazione(String tipoVotazione) {
		this.tipoVotazione = tipoVotazione;
	}

	public String getEsitoVotazione() {
		return esitoVotazione;
	}

	public void setEsitoVotazione(String esitoVotazione) {
		this.esitoVotazione = esitoVotazione;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataVotazione() {
		return dataVotazione;
	}

	public void setDataVotazione(Date dataVotazione) {
		this.dataVotazione = dataVotazione;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataTrasmissione() {
		return dataTrasmissione;
	}

	public void setDataTrasmissione(Date dataTrasmissione) {
		this.dataTrasmissione = dataTrasmissione;
	}

	public List<Relatore> getRelatori() {
		return relatori;
	}

	public void setRelatori(List<Relatore> relatori) {
		this.relatori = relatori;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataProposta() {
		return dataProposta;
	}

	public void setDataProposta(Date dataProposta) {
		this.dataProposta = dataProposta;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataAnnullo() {
		return dataAnnullo;
	}

	public void setDataAnnullo(Date dataAnnullo) {
		this.dataAnnullo = dataAnnullo;
	}

	public boolean isAnnullata() {
		return annullata;
	}

	public void setAnnullata(boolean annullata) {
		this.annullata = annullata;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataNomina() {
		return dataNomina;
	}

	public void setDataNomina(Date dataNomina) {
		this.dataNomina = dataNomina;
	}

	public ComitatoRistretto getComitatoRistretto() {
		return comitatoRistretto;
	}

	public void setComitatoRistretto(ComitatoRistretto comitatoRistretto) {
		this.comitatoRistretto = comitatoRistretto;
	}

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
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

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataFineLavoriComitato() {
		return dataFineLavoriComitato;
	}

	public void setDataFineLavoriComitato(Date dataFineLavoriComitato) {
		this.dataFineLavoriComitato = dataFineLavoriComitato;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataRichiestaIscrizioneAula() {
		return dataRichiestaIscrizioneAula;
	}

	public void setDataRichiestaIscrizioneAula(
			Date dataRichiestaIscrizioneAula) {
		this.dataRichiestaIscrizioneAula = dataRichiestaIscrizioneAula;
	}

	public boolean isPassaggioDirettoInAula() {
		return passaggioDirettoInAula;
	}

	public void setPassaggioDirettoInAula(boolean passaggioDirettoInAula) {
		this.passaggioDirettoInAula = passaggioDirettoInAula;
	}
	
	public List<Link> getLinksNoteEsameCommissione() {
		return linksNoteEsameCommissione;
	}

	
	public void setLinksNoteEsameCommissione(List<Link> linksNoteEsameCommissione) {
		this.linksNoteEsameCommissione = linksNoteEsameCommissione;
	}
	
	
	public String getNoteGeneraliEsameCommissione() {
		return noteGeneraliEsameCommissione;
	}

	@JsonDeserialize(using=JsonNoteDeserializer.class)
	public void setNoteGeneraliEsameCommissione(String noteGeneraliEsameCommissione) {
		this.noteGeneraliEsameCommissione = noteGeneraliEsameCommissione;
	}
	
	public List<Allegato> getTestiClausola() {
		return testiClausola;
	}

	public void setTestiClausola(List<Allegato> testiClausola) {
		this.testiClausola = testiClausola;
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

	@JsonDeserialize(using=JsonNoteDeserializer.class)
	public void setNoteClausolaValutativa(String noteClausolaValutativa) {
		this.noteClausolaValutativa = noteClausolaValutativa;
	}
	

}
