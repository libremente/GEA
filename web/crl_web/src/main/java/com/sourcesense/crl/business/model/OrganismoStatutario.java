package com.sourcesense.crl.business.model;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.sourcesense.crl.util.JsonDateSerializer;

@JsonRootName("organismoStatutario")
@JsonTypeName("organismoStatutario")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public class OrganismoStatutario {
	private String descrizione;
	private Date dataAssegnazione;
	private Date dataAnnullo;
	private boolean obbligatorio;

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataAssegnazione() {
		return dataAssegnazione;
	}

	public void setDataAssegnazione(Date dataAssegnazione) {
		this.dataAssegnazione = dataAssegnazione;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataAnnullo() {
		return dataAnnullo;
	}

	public void setDataAnnullo(Date dataAnnullo) {
		this.dataAnnullo = dataAnnullo;
	}

	public boolean isObbligatorio() {
		return obbligatorio;
	}

	public void setObbligatorio(boolean obbligatorio) {
		this.obbligatorio = obbligatorio;
	}
	

}
