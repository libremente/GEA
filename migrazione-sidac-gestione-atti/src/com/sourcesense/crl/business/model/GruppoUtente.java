package com.sourcesense.crl.business.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
@XmlRootElement
@JsonRootName("gruppoUtente")
@JsonTypeName("gruppoUtente")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@AutoProperty
public class GruppoUtente {
	
	
	public static final String SERVIZIO_COMMISSIONI = "ServizioCommissioni"; 
	public static final String AULA = "Aula";
	public static final String ADMIN = "CRLAdmin";
	public static final String GUEST = "CRLGuest";
	public static final String CPCV = "CPCV";
	
	
	/*Servizio commissioni
	Commissioni (vari ruoli)
	Aula
	CPCV
	Giunta per il regolamento
	Guest (sola lettura)
	Amministratore
*/
    private List<Seduta> sedute = new ArrayList<Seduta>();
    private String nome;
    private boolean commissione;

    @Override
    public String toString() {
        return Pojomatic.toString(this);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Seduta> getSedute() {
        return sedute;
    }

    public void setSedute(List<Seduta> sedute) {
        this.sedute = sedute;
    }

	public boolean isCommissione() {
		return commissione;
	}

	public void setCommissione(boolean commissione) {
		this.commissione = commissione;
	}
    
    
}
