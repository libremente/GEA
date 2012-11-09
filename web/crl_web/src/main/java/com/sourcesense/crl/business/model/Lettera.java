package com.sourcesense.crl.business.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.pojomatic.annotations.AutoProperty;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable()
@XmlRootElement(name = "lettera")
@JsonRootName("lettera")
@JsonTypeName("lettera")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@AutoProperty
public class Lettera implements Cloneable {
	/**
	 * "idAtto" : ""+idAtto+"",            "tipoTemplate" : ""+tipoTemplate+""
	 */

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	String idAtto;
	String tipoTemplate;
	String urlView;
	String nome;
	String firmatario;
	String ufficio;
	String direzione;
	String numeroTelFirmatario;
	String emailFirmatario;

	public String getIdAtto() {
		return idAtto;
	}

	public void setIdAtto(String idAtto) {
		this.idAtto = idAtto;
	}

	public String getTipoTemplate() {
		return tipoTemplate;
	}

	public void setTipoTemplate(String tipoTemplate) {
		this.tipoTemplate = tipoTemplate;
	}

	public String getUrlView() {
		return urlView;
	}

	public void setUrlView(String urlView) {
		this.urlView = urlView;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFirmatario() {
		return firmatario;
	}

	public void setFirmatario(String firmatario) {
		this.firmatario = firmatario;
	}

	public String getUfficio() {
		return ufficio;
	}

	public void setUfficio(String ufficio) {
		this.ufficio = ufficio;
	}

	public String getDirezione() {
		return direzione;
	}

	public void setDirezione(String direzione) {
		this.direzione = direzione;
	}

	public String getNumeroTelFirmatario() {
		return numeroTelFirmatario;
	}

	public void setNumeroTelFirmatario(String numeroTelFirmatario) {
		this.numeroTelFirmatario = numeroTelFirmatario;
	}

	public String getEmailFirmatario() {
		return emailFirmatario;
	}

	public void setEmailFirmatario(String emailFirmatario) {
		this.emailFirmatario = emailFirmatario;
	}
	
	

}
