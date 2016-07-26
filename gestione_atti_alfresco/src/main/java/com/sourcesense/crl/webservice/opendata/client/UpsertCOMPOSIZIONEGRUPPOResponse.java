
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
 *         &lt;element name="UpsertCOMPOSIZIONE_GRUPPOResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "upsertCOMPOSIZIONEGRUPPOResult"
})
@XmlRootElement(name = "UpsertCOMPOSIZIONE_GRUPPOResponse")
public class UpsertCOMPOSIZIONEGRUPPOResponse {

    @XmlElement(name = "UpsertCOMPOSIZIONE_GRUPPOResult")
    protected String upsertCOMPOSIZIONEGRUPPOResult;

    /**
     * Gets the value of the upsertCOMPOSIZIONEGRUPPOResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpsertCOMPOSIZIONEGRUPPOResult() {
        return upsertCOMPOSIZIONEGRUPPOResult;
    }

    /**
     * Sets the value of the upsertCOMPOSIZIONEGRUPPOResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpsertCOMPOSIZIONEGRUPPOResult(String value) {
        this.upsertCOMPOSIZIONEGRUPPOResult = value;
    }

}
