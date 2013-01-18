package com.sourcesense.crl.webscript.template;

public class GenerateDocumentException extends RuntimeException {

	private String className;

	
    public GenerateDocumentException(String className){
		setClassName(className);
	}
	
    public GenerateDocumentException(String className, Throwable t){
    	super (t);
		setClassName(className);
		
	}
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
}
