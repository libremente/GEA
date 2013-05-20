package com.sourcesense.crl.business.model;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@JsonRootName("tipoIniziativa")
@JsonTypeName("tipoIniziativa")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@AutoProperty
public class TipoIniziativa implements Comparable<TipoIniziativa>{
	
	private String descrizione;

	
	
	@Override public String toString() {
	    return Pojomatic.toString(this);
	  }
	
	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Override
	public int compareTo(TipoIniziativa o) {
		// TODO Auto-generated method stub
		return this.descrizione.compareTo(o.descrizione);
	}

}
