package com.sourcesense.crl.business.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sourcesense.crl.business.model.Lettera;
import com.sourcesense.crl.business.model.Report;
import com.sourcesense.crl.business.service.rest.LettereNotificheService;
import com.sourcesense.crl.business.service.rest.ReportService;
import com.sourcesense.crl.util.URLBuilder;


@Service("reportServiceManager")
public class ReportServiceManager implements ServiceManager {

	
	@Autowired
	private URLBuilder urlBuilder;
	
	
	@Autowired
	private ReportService reportService;
	
	
	
	
	public InputStream getReportFile(Report report ) {
        //alf_retrieve_report_bin=crl/template/report/creareport?json={0}&tipoTemplate={1} 
		return reportService.getFile(urlBuilder.buildAlfrescoURL(
				"alfresco_context_url", "alf_retrieve_report_bin", null),report);
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
