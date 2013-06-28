package com.sourcesense.crl.business.model;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.pojomatic.annotations.AutoProperty;


@JsonRootName("attoRecord")
@JsonTypeName("attoRecord")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@AutoProperty
public class TestoAtto implements Cloneable {

	
	//TYPE:"crlatti:testo" AND NOT @crlatti\:tipologia:"testo_atto" AND NOT @crlatti\:tipologia:"testo_atto_votato_commissione" AND NOT @crlatti\:tipologia:"testo_atto_votato_aula"
	
	public static final String TESTO_PRESENTAZIONE_ASSEGNAZIONE = "testo_atto";
	public static final String TESTO_ESAME_COMMISSIONE_VOTAZIONE = "testo_atto_votato_commissione";
	public static final String TESTO_ESAME_AULA_VOTAZIONE = "testo_atto_votato_aula";
	
	
	private String id;
	private String nome;
	private String mimetype;
    private String descrizione;
	private String downloadUrl;
	private boolean pubblico;
	private String tipoAllegato;
	private String tipologia;
	private String provenienza;
	private String dataSeduta;
	private String passaggio;
	private String commissione;
	
	
	private String appoTitolo;
	private String appoProvenienza;
	private String appoPath;
	
	
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public boolean isPubblico() {
		return pubblico;
	}
	public void setPubblico(boolean pubblico) {
		this.pubblico = pubblico;
	}
	public String getTipoAllegato() {
		return tipoAllegato;
	}
	public void setTipoAllegato(String tipoAllegato) {
		this.tipoAllegato = tipoAllegato;
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
	public String getMimetype() {
		return mimetype;
	}
	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public String getProvenienza() {
		return provenienza;
	}

	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}

	public String getDataSeduta() {
		return dataSeduta;
	}

	public void setDataSeduta(String dataSeduta) {
		this.dataSeduta = dataSeduta;
	}

	public String getPassaggio() {
		return passaggio;
	}

	public void setPassaggio(String passaggio) {
		this.passaggio = passaggio;
	}

	public String getCommissione() {
		return commissione;
	}

	public void setCommissione(String commissione) {
		this.commissione = commissione;
	}

	public String getAppoTitolo() {
		return appoTitolo;
	}

	public void setAppoTitolo(String appoTitolo) {
		this.appoTitolo = appoTitolo;
	}

	public String getAppoProvenienza() {
		return appoProvenienza;
	}

	public void setAppoProvenienza(String appoProvenienza) {
		this.appoProvenienza = appoProvenienza;
	}

	public String getAppoPath() {
		return appoPath;
	}

	public void setAppoPath(String appoPath) {
		this.appoPath = appoPath;
	}
	
	
	
	
	
	
}
