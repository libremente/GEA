package com.sourcesense.crl.util;

public class ServiceNotAvailableException extends RuntimeException {

	
	private String serviceName;

	public ServiceNotAvailableException (){
		
		
	}
	
    public ServiceNotAvailableException (String serviceName){
		
		setServiceName(serviceName);
	}
	
    public ServiceNotAvailableException (String serviceName, Throwable t){
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
