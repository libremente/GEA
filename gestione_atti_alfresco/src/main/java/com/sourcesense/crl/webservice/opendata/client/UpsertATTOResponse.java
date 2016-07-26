
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
 *         &lt;element name="UpsertATTOResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "upsertATTOResult"
})
@XmlRootElement(name = "UpsertATTOResponse")
public class UpsertATTOResponse {

    @XmlElement(name = "UpsertATTOResult")
    protected String upsertATTOResult;

    /**
     * Gets the value of the upsertATTOResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpsertATTOResult() {
        return upsertATTOResult;
    }

    /**
     * Sets the value of the upsertATTOResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpsertATTOResult(String value) {
        this.upsertATTOResult = value;
    }

}
