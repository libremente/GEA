/*
 *  * #%L
 *  * Alfresco Repository
 *  * %%
 *  * Copyright (C) 2005 - 2016 Alfresco Software Limited
 *  * %%
 *  * This file is part of the Alfresco software. 
 *  * If the software was purchased under a paid Alfresco license, the terms of 
 *  * the paid license agreement will prevail.  Otherwise, the software is 
 *  * provided under the following open source license terms:
 *  * 
 *  * Alfresco is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU Lesser General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  * 
 *  * Alfresco is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU Lesser General Public License for more details.
 *  * 
 *  * You should have received a copy of the GNU Lesser General Public License
 *  * along with Alfresco. If not, see <http://www.gnu.org/licenses/>.
 *  * #L%
 */
package com.sourcesense.crl.webservice.opendata.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ODDelibera" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PRIVATE_TOKEN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "odDelibera",
    "privatetoken"
})
@XmlRootElement(name = "UpsertDUP")
public class UpsertDUP {

    @XmlElement(name = "ODDelibera")
    protected String odDelibera;
    @XmlElement(name = "PRIVATE_TOKEN")
    protected String privatetoken;

    /**
     * Gets the value of the odDelibera property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getODDelibera() {
        return odDelibera;
    }

    /**
     * Sets the value of the odDelibera property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setODDelibera(String value) {
        this.odDelibera = value;
    }

    /**
     * Gets the value of the privatetoken property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPRIVATETOKEN() {
        return privatetoken;
    }

    /**
     * Sets the value of the privatetoken property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPRIVATETOKEN(String value) {
        this.privatetoken = value;
    }

}
