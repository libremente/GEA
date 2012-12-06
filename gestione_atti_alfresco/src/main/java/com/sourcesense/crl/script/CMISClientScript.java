package com.sourcesense.crl.script;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.jscript.BaseScopableProcessorExtension;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Repository;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.client.util.FileUtils;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;


public class CMISClientScript extends BaseScopableProcessorExtension {
	
	private String attiIspettiviAlfrescoURL;
	private String attiIspettiviAlfrescoUser;
	private String attiIspettiviAlfrescoPsw;
	private String attiIspettiviAlfrescoPath;
	
	private ServiceRegistry serviceRegistry;
	
	public boolean sendToAttiIspettiviRepository(String contentId) {
       
		try{
			
			SessionFactory factory = SessionFactoryImpl.newInstance();
			IdentityHashMap<String, String> param = new IdentityHashMap<String, String>();
			
			param.put(SessionParameter.USER, attiIspettiviAlfrescoUser);
			param.put(SessionParameter.PASSWORD, attiIspettiviAlfrescoPsw);
			param.put(SessionParameter.ATOMPUB_URL,  attiIspettiviAlfrescoURL+"/s/cmis");
			param.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
			
			List<Repository> repositories = factory.getRepositories(param);
			Session session = repositories.get(0).createSession();		
			Folder importFolder = (Folder) session.getObjectByPath(attiIspettiviAlfrescoPath);
			
			NodeRef contentNodeRef = new NodeRef(contentId);	
			String fileName = (String) serviceRegistry.getNodeService().getProperty(contentNodeRef, ContentModel.PROP_NAME);
			
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
			properties.put(PropertyIds.NAME, fileName);
			
			ContentReader reader = serviceRegistry.getContentService().getReader(contentNodeRef, ContentModel.PROP_CONTENT);
			ContentStream contentStream = new ContentStreamImpl(fileName, BigInteger.valueOf(reader.getSize()), "text/plain", reader.getContentInputStream());
		
			// create a major version
			Document newDoc = importFolder.createDocument(properties, contentStream, VersioningState.MAJOR);
			
			return true; 
		    
		}catch (Exception e) {
			return false;
		}
    }

	public String getAttiIspettiviAlfrescoURL() {
		return attiIspettiviAlfrescoURL;
	}


	public void setAttiIspettiviAlfrescoURL(String attiIspettiviAlfrescoURL) {
		this.attiIspettiviAlfrescoURL = attiIspettiviAlfrescoURL;
	}


	public String getAttiIspettiviAlfrescoUser() {
		return attiIspettiviAlfrescoUser;
	}


	public void setAttiIspettiviAlfrescoUser(String attiIspettiviAlfrescoUser) {
		this.attiIspettiviAlfrescoUser = attiIspettiviAlfrescoUser;
	}


	public String getAttiIspettiviAlfrescoPsw() {
		return attiIspettiviAlfrescoPsw;
	}


	public void setAttiIspettiviAlfrescoPsw(String attiIspettiviAlfrescoPsw) {
		this.attiIspettiviAlfrescoPsw = attiIspettiviAlfrescoPsw;
	}

	public String getAttiIspettiviAlfrescoPath() {
		return attiIspettiviAlfrescoPath;
	}

	public void setAttiIspettiviAlfrescoPath(String attiIspettiviAlfrescoPath) {
		this.attiIspettiviAlfrescoPath = attiIspettiviAlfrescoPath;
	}

	public ServiceRegistry getServiceRegistry() {
		return serviceRegistry;
	}

	public void setServiceRegistry(ServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}
		
}
