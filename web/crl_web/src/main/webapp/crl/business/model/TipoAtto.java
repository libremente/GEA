package com.sourcesense.crl.business.model;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;



@JsonRootName("tipoAtto")
@JsonTypeName("tipoAtto")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@AutoProperty
public class TipoAtto implements Comparable<TipoAtto> {
	
	
	private String descrizione;
	
	private String codice;
	
	@Override
	public int compareTo(TipoAtto arg0) {
		// TODO Auto-generated method stub
		return this.descrizione.compareTo(arg0.descrizione);
	}
	
	
	
	@Override public String toString() {
	    return Pojomatic.toString(this);
	  }

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	
	
	

}
