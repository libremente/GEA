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
public class TipoAtto {
	
	
	private String descrizione;
	
	private String codice;
	
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
