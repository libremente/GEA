package com.sourcesense.crl.business.security;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonRootName;
import org.springframework.beans.factory.annotation.Configurable;

/*
 * { "data": {
 * "ticket":"TICKET_da34b98e4a5ac05368a347b3a706246baf3e5659" } }
 */


@JsonRootName(value = "data")
public class AlfrescoSessionTicket {
	
	
	String ticket ;

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	
	
	

}
