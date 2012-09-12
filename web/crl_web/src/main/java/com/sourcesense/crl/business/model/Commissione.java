package com.sourcesense.crl.business.model;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.sourcesense.crl.util.JsonDateSerializer;


@JsonRootName("commissione")
@JsonTypeName("commissione")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@AutoProperty
public class Commissione implements Cloneable {
	
	private String commissione;
	private String descrizione;
	private String nome;
	private String ruolo;
	private String stato;
	private Date dataAssegnazione;
	private Date dataPresaInCarico;
	private String tipoVotazione;
	private String esitoVotazione;
	private Date dataVotazione;
	private Date dataTrasmissione;
	
	private Date dataNomina;
	
	private Date dataFineLavoriEsameComitato;
	
	private List <Relatore> relatori = new ArrayList<Relatore>();
	private Date dataProposta;
	private Date dataAnnullo;
	private boolean annullata = false;
	
	private String materia;
	private Date dataScadenza;
	
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

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataFineLavoriEsameComitato() {
		return dataFineLavoriEsameComitato;
	}

	public void setDataFineLavoriEsameComitato(
			Date dataFineLavoriEsameComitato) {
		this.dataFineLavoriEsameComitato = dataFineLavoriEsameComitato;
	}
	
	

}
