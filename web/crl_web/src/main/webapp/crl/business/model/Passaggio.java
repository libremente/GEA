package com.sourcesense.crl.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;

@JsonRootName("passaggio")
@JsonTypeName("passaggio")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public class Passaggio {
	
	private String nome;
	private List <Commissione> commissioni = new ArrayList<Commissione>();
	private List <Abbinamento> abbinamenti = new ArrayList<Abbinamento>();
    private Aula aula = new Aula();
    
    
    
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
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
	public Aula getAula() {
		return aula;
	}
	public void setAula(Aula aula) {
		this.aula = aula;
	}	
    
    
    
    
	
}
