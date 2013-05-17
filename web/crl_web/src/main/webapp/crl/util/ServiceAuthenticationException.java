package com.sourcesense.crl.util;

public class ServiceAuthenticationException extends RuntimeException {

	
	private String serviceName;

	public ServiceAuthenticationException (){
		
		
	}
	
    public ServiceAuthenticationException (String serviceName){
		
		setServiceName(serviceName);
	}
	
    public ServiceAuthenticationException (String serviceName, Throwable t){
    	super (t);
		setServiceName(serviceName);
		
	}
	
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	
	
	
}