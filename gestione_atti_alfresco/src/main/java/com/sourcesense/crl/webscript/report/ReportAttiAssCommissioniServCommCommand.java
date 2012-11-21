package com.sourcesense.crl.webscript.report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import com.sourcesense.crl.webscript.report.office.DocxManager;

public class ReportAttiAssCommissioniServCommCommand extends ReportBaseCommand {

	public ReportAttiAssCommissioniServCommCommand(
			ContentService contentService, SearchService searchService,
			NodeService nodeService) {
		super(contentService, searchService, nodeService);
		// TODO Auto-generated constructor stub
	}

	@Override
	public byte[] generate(byte[] templateByteArray, String json)
			throws IOException {
		ByteArrayInputStream  is = new ByteArrayInputStream( templateByteArray);
		DocxManager docxManager=new DocxManager(is);
		ResultSet queryRes=null;// mock, here Alfresco queries
		// obtain resultSet Length and cycle on it to repeat template
		XWPFDocument generatedDocument = docxManager.generateFromTemplate(queryRes.length(), 5);
		// convert to input stream
		ByteArrayInputStream tempInputStream = saveTemp(generatedDocument);
		
		XWPFDocument finalDocument = this.fillTemplate(tempInputStream, queryRes);
		ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		finalDocument.write(ostream);
		return ostream.toByteArray();
		
		
	}

	/**
	 * @param generatedDocument
	 * @return 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private ByteArrayInputStream saveTemp(XWPFDocument generatedDocument) throws IOException,
			FileNotFoundException {
		ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		generatedDocument.write(ostream);
		return new ByteArrayInputStream(ostream.toByteArray());
		/*
		 * usually duplicated template became useful only after disk save and re-import
		URL testTableOutput = getClass().getResource("");
		String path = testTableOutput.getPath();
		File out = new File(path + "/attiTemp.docx");
		out.createNewFile();
		FileOutputStream fos = new FileOutputStream(out);
		generatedDocument.write(fos);
		fos.flush();
		fos.close();
		FileInputStream reader=new FileInputStream(out);
		return reader;*/
	}

	public XWPFDocument fillTemplate(ByteArrayInputStream finalDocStream,ResultSet queryRes) throws IOException{
		XWPFDocument document=new XWPFDocument(finalDocStream);
		List<XWPFTable> tables = document.getTables();
		for (int k = 0; k < queryRes.length(); k++) {
			XWPFTable newTable = tables.get(k);
			XWPFTableRow firstRow = newTable.getRow(0);
			firstRow.getCell(0).setText("1x1");
			firstRow.getCell(0).setText("1x2");
		}
		return document;
	}
}
