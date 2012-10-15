package com.sourcesense.crl.business.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.pojomatic.annotations.AutoProperty;
import org.springframework.beans.factory.annotation.Configurable;


@Configurable()
@XmlRootElement(name="lettera")
@JsonRootName("lettera")
@JsonTypeName("lettera")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@AutoProperty
public class Lettera implements Cloneable
{
	/**
	 *"idAtto" : ""+idAtto+"",
            "tipoTemplate" : ""+tipoTemplate+"" 
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
	
	
	
	
	
}
