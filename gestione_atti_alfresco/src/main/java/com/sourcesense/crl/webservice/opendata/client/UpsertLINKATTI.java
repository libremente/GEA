
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
 *         &lt;element name="ODLinkAtti" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "odLinkAtti",
    "privatetoken"
})
@XmlRootElement(name = "UpsertLINK_ATTI")
public class UpsertLINKATTI {

    @XmlElement(name = "ODLinkAtti")
    protected String odLinkAtti;
    @XmlElement(name = "PRIVATE_TOKEN")
    protected String privatetoken;

    /**
     * Gets the value of the odLinkAtti property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getODLinkAtti() {
        return odLinkAtti;
    }

    /**
     * Sets the value of the odLinkAtti property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setODLinkAtti(String value) {
        this.odLinkAtti = value;
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
