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

import com.sourcesense.crl.business.service.AttoService;
import com.sourcesense.crl.business.model.Atto;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author uji
 */

@ManagedBean(name = "searchBean")
@ViewScoped
public class SearchBean implements Serializable
{
    private AttoService as = new AttoService();
    
    private LazyDataModel<Atto> lazyAttoModel;
    
    @PostConstruct
    protected void initLazyModel()
    {
        setLazyAttoModel(new LazyDataModel<Atto>()
         {
             
             @Override
             public List<Atto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
             {
                 return as.find(first, pageSize);
             }

             @Override
             public int getRowCount()
             {
                 return (int) as.count();
             }
         });
    }

    /**
     * @return the lazyAttoModel
     */
    public LazyDataModel<Atto> getLazyAttoModel()
    {
        return lazyAttoModel;
    }

    /**
     * @param lazyAttoModel the lazyAttoModel to set
     */
    public void setLazyAttoModel(LazyDataModel<Atto> lazyAttoModel)
    {
        this.lazyAttoModel = lazyAttoModel;
    }
    
    
    
}
