package com.sourcesense.crl.webscript.report.model;

import java.util.Date;

import org.alfresco.service.cmr.repository.NodeRef;

public class Atto implements Comparable<Atto> {
	private Date dataNomina;
	private String tipoAtto;
	private String numeroAtto;
	private String consigliere;
	
	private NodeRef attoNodeRef;

	public Atto(Date dataNomina, String tipoAtto, String numeroAtto,
			String consigliere, NodeRef attoNodeRef) {
		super();
		this.dataNomina = dataNomina;
		this.tipoAtto = tipoAtto;
		this.numeroAtto = numeroAtto;
		this.consigliere = consigliere;
		this.attoNodeRef = attoNodeRef;
	}


	public Atto() {
	}


	public String getTipoAtto() {
		return tipoAtto;
	}

	public void setTipoAtto(String tipoAtto) {
		this.tipoAtto = tipoAtto;
	}

	public String getNumeroAtto() {
		return numeroAtto;
	}

	public void setNumeroAtto(String numeroAtto) {
		this.numeroAtto = numeroAtto;
	}

	public String getConsigliere() {
		return consigliere;
	}

	public void setConsigliere(String consigliere) {
		this.consigliere = consigliere;
	}

	public NodeRef getAttoNodeRef() {
		return attoNodeRef;
	}

	public void setAttoNodeRef(NodeRef attoNodeRef) {
		this.attoNodeRef = attoNodeRef;
	}

	public Date getDataNomina() {
		return dataNomina;
	}

	public void setDataNomina(Date dataNomina) {
		this.dataNomina = dataNomina;
	}


	@Override
	public int compareTo(Atto o) {
		int result=0;
		result=this.getDataNomina().compareTo(o.getDataNomina());
		if(result==0){
			result=this.getConsigliere().compareTo(o.getConsigliere());
		}
		if(result==0){
			result=this.getTipoAtto().compareTo(o.getTipoAtto());
		}
		if(result==0){
			result=this.getNumeroAtto().compareTo(o.getNumeroAtto());
		}
		return result;
	}

	
	

}
