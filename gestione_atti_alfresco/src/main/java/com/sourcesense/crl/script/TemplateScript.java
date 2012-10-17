package com.sourcesense.crl.script;

import org.alfresco.repo.jscript.BaseScopableProcessorExtension;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.namespace.QName;
import org.alfresco.service.cmr.dictionary.DictionaryService;
import org.alfresco.service.cmr.dictionary.TypeDefinition;

public class TemplateScript extends BaseScopableProcessorExtension {

	
	private ServiceRegistry serviceRegistry;
	
	// Get title form node-type definition
	public String getTypeDescription(String type) {
        
		String typeDescription = "";
		
		DictionaryService dictionaryService = serviceRegistry.getDictionaryService();		
		TypeDefinition typeDef = dictionaryService.getType(QName.createQName(type));
		typeDescription = typeDef.getTitle();
				
		return typeDescription;
        
    }

	public ServiceRegistry getServiceRegistry() {
		return serviceRegistry;
	}

	public void setServiceRegistry(ServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}
	
	
	
	
}
