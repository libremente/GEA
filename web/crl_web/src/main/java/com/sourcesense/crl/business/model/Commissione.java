package com.sourcesense.crl.business.model;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;


@JsonRootName("commissione")
@JsonTypeName("commissione")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public class Commissione {
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
	
	private Date dataChiusuraLavoriComitato;
	private Date dataAperturaLavoriComitato;
	private Date dataNomina;
	
	private List <Relatore> relatori = new ArrayList<Relatore>();
	private Date dataProposta;
	private Date dataAnnullo;
	private boolean annullata = false;
	

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

	public Date getDataAssegnazione() {
		return dataAssegnazione;
	}

	public void setDataAssegnazione(Date dataAssegnazione) {
		this.dataAssegnazione = dataAssegnazione;
	}

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

	public Date getDataVotazione() {
		return dataVotazione;
	}

	public void setDataVotazione(Date dataVotazione) {
		this.dataVotazione = dataVotazione;
	}

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

	public Date getDataProposta() {
		return dataProposta;
	}

	public void setDataProposta(Date dataProposta) {
		this.dataProposta = dataProposta;
	}

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

	public Date getDataChiusuraLavoriComitato() {
		return dataChiusuraLavoriComitato;
	}

	public void setDataChiusuraLavoriComitato(Date dataChiusuraLavoriComitato) {
		this.dataChiusuraLavoriComitato = dataChiusuraLavoriComitato;
	}

	public Date getDataAperturaLavoriComitato() {
		return dataAperturaLavoriComitato;
	}

	public void setDataAperturaLavoriComitato(Date dataAperturaLavoriComitato) {
		this.dataAperturaLavoriComitato = dataAperturaLavoriComitato;
	}

	public Date getDataNomina() {
		return dataNomina;
	}

	public void setDataNomina(Date dataNomina) {
		this.dataNomina = dataNomina;
	}
	
	

}
