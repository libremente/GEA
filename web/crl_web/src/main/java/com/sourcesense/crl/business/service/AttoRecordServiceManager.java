package com.sourcesense.crl.business.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sourcesense.crl.business.model.Allegato;
import com.sourcesense.crl.business.model.Atto;
import com.sourcesense.crl.business.model.TestoAtto;
import com.sourcesense.crl.business.service.rest.AttoRecordService;
import com.sourcesense.crl.util.URLBuilder;

///alfresco/d/a/workspace/SpacesStore/0000-0000-0000-0000/myfile.pdf?ticket=hu
@Service("attoRecordServiceManager")
public class AttoRecordServiceManager implements ServiceManager {
	
	@Autowired
	private  URLBuilder urlBuilder;

	@Autowired
	private AttoRecordService attoRecordService;
	
	public List <TestoAtto> testiAttoByAtto(Atto atto) {

		return attoRecordService.retrieveTestiAtto(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_list_testi_atto", new String[] { atto.getId() }));
	}

	public List <Allegato> allAllegatiAttoByAtto(Atto atto) {

		return attoRecordService.retrieveAllegati(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_list_allegati_atto", new String[] { atto.getId() ,""}));
	}
	
	public InputStream getFileById(String fileToDownload) {

		return attoRecordService.getFile(urlBuilder.buildAlfrescoDownloadURL(
				"alfresco_dwnl_direct_context_url", fileToDownload, null));
	}

	
	public Allegato updateAllegato(Atto atto, Allegato allegato) {

		return attoRecordService.updateAllegato(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_update_allegato", null),atto, allegato);
	}
	
	public TestoAtto updateTestoAtto(Atto atto, TestoAtto testoAtto) {

		return attoRecordService.updateTestoAtto(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_update_testo", null),atto, testoAtto);
	}
	
	public void deleteFile(String idFile) {
		
		 attoRecordService.deleteFile(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_delete_file", new String[] {idFile}));
		
		
	}
	
	

	@Override
	public Object persist(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object merge(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Object> retrieveAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
