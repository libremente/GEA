package com.sourcesense.crl.business.model;

public class ColonnaAtto {

	/*
	 * Tipo atto N° atto Oggetto Tipo iniziativa Firmatari Stato Tipo chiusura
	 * Com. referente, co-referente, redigente o deliberante Data assegnazione
	 * Commissioni consultive Relatore Data nomina relatore Abbinamenti "Esito
	 * votazione commissione referente (o redigente o deliberante)"	"Data
	 * votazione commissione"	Data scadenza	"Data richiesta iscrizione all'aula"
	 * Esito votazione Aula "Data votazione aula" N° DCR N° LCR BURL Data BURL
	 * N° LR Data LR
	 */

	private String nome;
	private String attoProperty;
	private boolean visible;

	public ColonnaAtto() {

	}

	
	
	public ColonnaAtto(String nome,String attoProperty,boolean visible) {

		this.nome = nome;
		this.visible = visible;
		this.attoProperty =attoProperty;
	}

	
	
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}



	public String getAttoProperty() {
		return attoProperty;
	}



	public void setAttoProperty(String attoProperty) {
		this.attoProperty = attoProperty;
	}

	
	
}
