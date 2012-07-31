package com.sourcesense.crl.business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.sourcesense.crl.util.JsonDateSerializer;

@JsonRootName("commissioneReferente")
@JsonTypeName("commissioneReferente")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public class CommissioneReferente extends Commissione {
	
	private Date dataNomina;
	private List <Personale> membriComitatoRistretto = new ArrayList<Personale>();
	
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataNomina() {
		return dataNomina;
	}
	
	public void setDataNomina(Date dataNomina) {
		this.dataNomina = dataNomina;
	}
	
	public List <Personale> getMembriComitatoRistretto() {
		return this.membriComitatoRistretto;
	}
	public void setMembriComitatoRistretto(List<Personale> membriComitatoRistretto) {
		this.membriComitatoRistretto = membriComitatoRistretto;
	}
	
	

}
