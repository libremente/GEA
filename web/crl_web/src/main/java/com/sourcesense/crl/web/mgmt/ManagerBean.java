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
package com.sourcesense.crl.web.mgmt;

import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.service.AttoService;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author uji
 */

@ManagedBean(name = "mgmtBean")
@ViewScoped
public class ManagerBean implements Serializable
{
	
	
	private boolean showCommDetail;
	
    private String code;
    
    private Atto atto;
    
    private AttoService as = new AttoService();
    
    /**
     * @return the code
     */
    public String getCode()
    {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code)
    {
        this.code = code;
        
        if (code != null)
            setAtto(as.get(code));
    }

    /**
     * @return the atto
     */
    public Atto getAtto()
    {
        return atto;
    }

    /**
     * @param atto the atto to set
     */
    public void setAtto(Atto atto)
    {
        this.atto = atto;
    }

	public boolean isShowCommDetail() {
		return showCommDetail;
	}

	public void setShowCommDetail(boolean showCommDetail) {
		this.showCommDetail = showCommDetail;
	}
    
	public void visualizeCommDetail() {
		this.showCommDetail=true;
	}
    
    
}
