package com.sourcesense.crl.web.ui.controller;

import java.io.InputStream;
import java.net.URLEncoder;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.sourcesense.crl.business.model.Allegato;
import com.sourcesense.crl.business.model.TestoAtto;
import com.sourcesense.crl.business.service.AttoRecordServiceManager;
import com.sourcesense.crl.web.ui.beans.AttoBean;


@ManagedBean(name = "fileDownloadController")
@ViewScoped
public class FileDownloadController {

	
	@ManagedProperty(value = "#{attoRecordServiceManager}")
	private AttoRecordServiceManager attoRecordServiceManager;
	
	private String fileId;
	private String fileName;
	private String fileMimetype;
	private StreamedContent file;

	
	
	public void fileUpdate(RowEditEvent event){
	    
	    if (event.getObject() instanceof Allegato) {
	    	
	    	attoRecordServiceManager.updateAllegato((Allegato) event.getObject());
	    	
	    } else if (event.getObject() instanceof TestoAtto) {
	    	
	    	attoRecordServiceManager.updateTestoAtto((TestoAtto) event.getObject());
	    	
	    }
	    
	}
	
	
	
	

	public StreamedContent getFile() {
		String fileToDownload = fileId.replaceAll(":/","") +"/"+ fileName.replaceAll(" ", "%20");
		InputStream stream = attoRecordServiceManager.getFileById(fileToDownload);
		file = new DefaultStreamedContent(stream , fileMimetype ,fileName);
		return file;
	}

	

	public AttoRecordServiceManager getAttoRecordServiceManager() {
		return attoRecordServiceManager;
	}

	public void setAttoRecordServiceManager(
			AttoRecordServiceManager attoRecordServiceManager) {
		this.attoRecordServiceManager = attoRecordServiceManager;
	}

	public void setFile(StreamedContent file) {
		this.file = file;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileMimetype() {
		return fileMimetype;
	}

	public void setFileMimetype(String fileMimetype) {
		this.fileMimetype = fileMimetype;
	}
	
	
	
	

}
