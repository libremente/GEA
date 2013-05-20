package com.sourcesense.crl.business.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.sourcesense.crl.util.JsonDateSerializer;

public class AttoEAC extends Atto {
	
	
	private Date dataAtto;
	private String note;
	
	
	
	
	
	
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDataAtto() {
		return dataAtto;
	}
	public void setDataAtto(Date dataAtto) {
		this.dataAtto = dataAtto;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	
	
	
	

}
