
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
 *         &lt;element name="ODCalendario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "odCalendario",
    "privatetoken"
})
@XmlRootElement(name = "UpsertCALENDARIO")
public class UpsertCALENDARIO {

    @XmlElement(name = "ODCalendario")
    protected String odCalendario;
    @XmlElement(name = "PRIVATE_TOKEN")
    protected String privatetoken;

    /**
     * Gets the value of the odCalendario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getODCalendario() {
        return odCalendario;
    }

    /**
     * Sets the value of the odCalendario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setODCalendario(String value) {
        this.odCalendario = value;
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
