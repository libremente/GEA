package com.sourcesense.crl.webscript.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.web.bean.repository.Repository;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.extensions.webscripts.AbstractWebScript;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;

import com.sourcesense.crl.webscript.report.office.DocxManager;

public class ReportWebScript extends AbstractWebScript {

	private static Log logger = LogFactory.getLog(ReportWebScript.class);
	private DocxManager docxManager;

	@Override
	public void execute(WebScriptRequest req, WebScriptResponse res)
			throws IOException {

		OutputStream responseOutputStream = null;
		InputStream templateInputStream = null;

		DocxManager docxManager;
		Map<String, String> filter2value = this.retrieveFilters(req); // luceneField ->value
		List<String> metaField = this.retrieveMetaField(req);// list of luceneFields
		String sortField = this.retrieveSortingField(req); //lucene field
		String templateName = this.retrieveTemplateName();

		URL templateUrl = getClass().getResource(
				"/docxTemplates/" + templateName + ".docx");
		String templateUrlString = templateUrl.getFile();
		InputStream inp = new FileInputStream(new File(templateUrlString));
		docxManager = new DocxManager(inp);
		// query Lucene using filter2value map and sorting using sortField
		// obtain result set and build a LuceneMockDocumentList with the
		// interesting fields
		// docxManager.generateFromTemplate(n)
		// docxManager.fillTemplate(luceneDocs, onlyValues)
		String nomeFile = templateName;
		XWPFDocument document = null;
		// Set response
		res.setContentType("application/ms-word");
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		res.setHeader("Content-Disposition", "attachment; filename=\""
				+ nomeFile + "_" + sdf.format(gc.getTime()) + ".docx\"");
		responseOutputStream = res.getOutputStream();
		document.write(responseOutputStream);
		responseOutputStream.close();

	}

	private String retrieveTemplateName() {
		// TODO Auto-generated method stub
		return null;
	}

	private String retrieveSortingField(WebScriptRequest req) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<String> retrieveMetaField(WebScriptRequest req) {
		// TODO Auto-generated method stub
		return null;
	}

	private Map<String, String> retrieveFilters(WebScriptRequest req) {
		// TODO Auto-generated method stub
		return null;
	}
}
