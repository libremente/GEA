
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
 *         &lt;element name="DeleteATTO_SIResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "deleteATTOSIResult"
})
@XmlRootElement(name = "DeleteATTO_SIResponse")
public class DeleteATTOSIResponse {

    @XmlElement(name = "DeleteATTO_SIResult")
    protected String deleteATTOSIResult;

    /**
     * Gets the value of the deleteATTOSIResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeleteATTOSIResult() {
        return deleteATTOSIResult;
    }

    /**
     * Sets the value of the deleteATTOSIResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeleteATTOSIResult(String value) {
        this.deleteATTOSIResult = value;
    }

}