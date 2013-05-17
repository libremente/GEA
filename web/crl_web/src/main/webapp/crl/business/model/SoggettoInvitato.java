package com.sourcesense.crl.business.model;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@JsonRootName("soggettoInvitato")
@JsonTypeName("soggettoInvitato")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@AutoProperty
public class SoggettoInvitato {
	private String descrizione;
	private boolean intervenuto;
	
	@Override public String toString() {
	    return Pojomatic.toString(this);
	  }
	
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public boolean isIntervenuto() {
		return intervenuto;
	}
	public void setIntervenuto(boolean intervenuto) {
		this.intervenuto = intervenuto;
	}
	
	

}
