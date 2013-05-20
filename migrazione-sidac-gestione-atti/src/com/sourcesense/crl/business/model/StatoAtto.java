package com.sourcesense.crl.business.model;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@JsonRootName("statoAtto")
@JsonTypeName("statoAtto")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@AutoProperty
public class StatoAtto {
	
	//USSC
	public static final String PROTOCOLLATO="Protocollato";
	public static final String PRESO_CARICO_SC="Preso in carico da S.C.";
	public static final String VERIFICATA_AMMISSIBILITA="Verificata ammissibilit??";
	public static final String PROPOSTA_ASSEGNAZIONE="Proposta assegnazione";
	
	
	//COMMISSIONI
	public static final String ASSEGNATO_COMMISSIONE="Assegnato a Commissione";
	public static final String PRESO_CARICO_COMMISSIONE="Preso in carico da Commissioni";
	public static final String NOMINATO_RELATORE="Nominato Relatore";
	public static final String VOTATO_COMMISSIONE="Votato in Commissione";
	public static final String TRASMESSO_COMMISSIONE="Trasmesso da Commissione";
	public static final String LAVORI_COMITATO_RISTRETTO="Lavori Comitato Ristretto";
	
	
	//AULA
	public static final String TRASMESSO_AULA="Trasmesso ad Aula";
	public static final String PRESO_CARICO_AULA="Preso in carico da Aula";
	public static final String VOTATO_AULA="Votato in Aula";
	public static final String PUBBLICATO="Pubblicato";
	public static final String CHIUSO="Chiuso";
	
	//EAC - MIS
	public static final String EAC="eac";
	public static final String MIS="mis";
	
	
	private String descrizione;

	@Override public String toString() {
	    return Pojomatic.toString(this);
	  }
	
	public StatoAtto(){
		
		
	}
	
	
    public StatoAtto(String descrizione){
		
		this.descrizione=descrizione;
	}
	
	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	

}
