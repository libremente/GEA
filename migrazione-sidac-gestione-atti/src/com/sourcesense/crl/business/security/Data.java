package com.sourcesense.crl.business.security;


import org.codehaus.jackson.map.annotate.JsonRootName;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
@JsonRootName(value = "data")
public class Data {

	
	
	String ticket ;

	
	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	
}
