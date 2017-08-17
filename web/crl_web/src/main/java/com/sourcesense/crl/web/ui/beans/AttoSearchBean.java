package com.sourcesense.crl.web.ui.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.sourcesense.crl.business.model.AttoSearch;

@ManagedBean(name = "attoSearchBean")
@SessionScoped
public class AttoSearchBean extends AttoSearch implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String TipoWorkingList;

	public String getTipoWorkingList() {
		return TipoWorkingList;
	}

	public void setTipoWorkingList(String tipoWorkingList) {
		TipoWorkingList = tipoWorkingList;
	}
}
