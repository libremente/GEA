
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
 *         &lt;element name="UpsertLINK_ATTIResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "upsertLINKATTIResult"
})
@XmlRootElement(name = "UpsertLINK_ATTIResponse")
public class UpsertLINKATTIResponse {

    @XmlElement(name = "UpsertLINK_ATTIResult")
    protected String upsertLINKATTIResult;

    /**
     * Gets the value of the upsertLINKATTIResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpsertLINKATTIResult() {
        return upsertLINKATTIResult;
    }

    /**
     * Sets the value of the upsertLINKATTIResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpsertLINKATTIResult(String value) {
        this.upsertLINKATTIResult = value;
    }

}
