
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
 *         &lt;element name="DeleteLINK_CONS_LEGResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "deleteLINKCONSLEGResult"
})
@XmlRootElement(name = "DeleteLINK_CONS_LEGResponse")
public class DeleteLINKCONSLEGResponse {

    @XmlElement(name = "DeleteLINK_CONS_LEGResult")
    protected String deleteLINKCONSLEGResult;

    /**
     * Gets the value of the deleteLINKCONSLEGResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeleteLINKCONSLEGResult() {
        return deleteLINKCONSLEGResult;
    }

    /**
     * Sets the value of the deleteLINKCONSLEGResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeleteLINKCONSLEGResult(String value) {
        this.deleteLINKCONSLEGResult = value;
    }

}
