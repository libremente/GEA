/*
 * Copyright Shragger sarl. All Rights Reserved.
 *
 *
 * The copyright to the computer program(s) herein is the property of
 * Shragger sarl., France. The program(s) may be used and/or copied only
 * with the written permission of Shragger sarl.. or in accordance with the
 * terms and conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied. This copyright notice must not be removed.
 */
package com.sourcesense.crl.web.search;

import com.sourcesense.crl.business.service.AttoServiceManager;
import com.sourcesense.crl.business.model.Atto;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;


import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 * 
 * @author uji
 */

@ViewScoped
@ManagedBean(name= "searchBean")
public class SearchBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
	
	@ManagedProperty(value="#{attoServiceManager}")
	private AttoServiceManager attoServiceManager ;

	private LazyDataModel<Atto> lazyAttoModel;

	private String numeroAtto;

	private String dataIniziativaDa;

	private String dataIniziativaA;

	private String tipoatto;

	private String legislatura;

	private String stato;

	private String numeroprotocollo;

	private String tipoiniziativa;

	private String numerodcr;

	private String primofirmatario;

	private String oggetto;

	private String firmatario;

	@PostConstruct
	protected void initLazyModel() {
		setLazyAttoModel(new LazyDataModel<Atto>() {

			@Override
			public List<Atto> load(int first, int pageSize, String sortField,
					SortOrder sortOrder, Map<String, String> filters) {
				return attoServiceManager.find(first, pageSize);
			}

			@Override
			public int getRowCount() {
				return (int) attoServiceManager.count();
			}
		});
	}

	/**
	 * @return the lazyAttoModel
	 */
	public LazyDataModel<Atto> getLazyAttoModel() {
		return lazyAttoModel;
	}

	/**
	 * @param lazyAttoModel
	 *            the lazyAttoModel to set
	 */
	public void setLazyAttoModel(LazyDataModel<Atto> lazyAttoModel) {
		this.lazyAttoModel = lazyAttoModel;
	}

	/**
     */
	public void searchLazyAttoModel() {

		attoServiceManager = new AttoServiceManager();
		
		lazyAttoModel = new LazyDataModel<Atto>() {

			@Override
			public List<Atto> load(int first, int pageSize, String sortField,
					SortOrder sortOrder, Map<String, String> filters) {
				return attoServiceManager.find(1, 10);
			}

			@Override
			public int getRowCount() {
				return (int) attoServiceManager.count();
			}
		};

	}

	public String getNumeroAtto() {
		return numeroAtto;
	}

	public void setNumeroAtto(String numeroAtto) {
		this.numeroAtto = numeroAtto;
	}

	public String getDataIniziativaDa() {
		return dataIniziativaDa;
	}

	public void setDataIniziativaDa(String dataIniziativaDa) {
		this.dataIniziativaDa = dataIniziativaDa;
	}

	public String getDataIniziativaA() {
		return dataIniziativaA;
	}

	public void setDataIniziativaA(String dataIniziativaA) {
		this.dataIniziativaA = dataIniziativaA;
	}

	public String getTipoatto() {
		return tipoatto;
	}

	public void setTipoatto(String tipoatto) {
		this.tipoatto = tipoatto;
	}

	public String getLegislatura() {
		return legislatura;
	}

	public void setLegislatura(String legislatura) {
		this.legislatura = legislatura;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getNumeroprotocollo() {
		return numeroprotocollo;
	}

	public void setNumeroprotocollo(String numeroprotocollo) {
		this.numeroprotocollo = numeroprotocollo;
	}

	public String getTipoiniziativa() {
		return tipoiniziativa;
	}

	public void setTipoiniziativa(String tipoiniziativa) {
		this.tipoiniziativa = tipoiniziativa;
	}

	public String getNumerodcr() {
		return numerodcr;
	}

	public void setNumerodcr(String numerodcr) {
		this.numerodcr = numerodcr;
	}

	public String getPrimofirmatario() {
		return primofirmatario;
	}

	public void setPrimofirmatario(String primofirmatario) {
		this.primofirmatario = primofirmatario;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getFirmatario() {
		return firmatario;
	}

	public void setFirmatario(String firmatario) {
		this.firmatario = firmatario;
	}
	
	public AttoServiceManager getAttoServiceManager() {
		return attoServiceManager;
	}

	public void setAttoServiceManager(AttoServiceManager attoServiceManager) {
		this.attoServiceManager = attoServiceManager;
	}

}
