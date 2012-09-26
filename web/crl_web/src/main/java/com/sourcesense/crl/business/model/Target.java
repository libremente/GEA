package com.sourcesense.crl.business.model;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@JsonRootName("target")
@JsonTypeName("target")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@AutoProperty
public class Target {
	
	private String commissione;
	private String passaggio;
	
	
	@Override public String toString() {
	    return Pojomatic.toString(this);
	  }

	public String getCommissione() {
		return commissione;
	}

	public void setCommissione(String commissione) {
		this.commissione = commissione;
	}

	public String getPassaggio() {
		return passaggio;
	}

	public void setPassaggio(String passaggio) {
		this.passaggio = passaggio;
	}
	
	
}
