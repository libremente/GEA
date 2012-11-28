package com.sourcesense.crl.webscript.report.servizio_commissioni;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.web.bean.repository.Repository;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import com.sourcesense.crl.webscript.report.ReportBaseCommand;
import com.sourcesense.crl.webscript.report.util.office.DocxManager;

public class ReportComposizioneCommissioniCommand extends ReportBaseCommand {

	@Override
	public byte[] generate(byte[] templateByteArray, String json, StoreRef attoNodeRef)
			throws IOException {
		ByteArrayOutputStream ostream = null;
		ByteArrayInputStream is = new ByteArrayInputStream(templateByteArray);
		DocxManager docxManager = new DocxManager(is);
		// data assegnazione
		ResultSet queryRes = null;

		// qua interroghiamo tutte le commissioni
		for (int i = 0; i < tipiAttoLucene.size(); i++) {
			queryRes = searchService.query(Repository.getStoreRef(),
					SearchService.LANGUAGE_LUCENE, "crlatti:commissione:*");
		}

		// obtain resultSet Length and cycle on it to repeat template
		XWPFDocument generatedDocument = docxManager.generateFromTemplate(
				queryRes.length(), 5, false);
		// convert to input stream
		ByteArrayInputStream tempInputStream = saveTemp(generatedDocument);

		XWPFDocument finalDocument = this.fillTemplate(tempInputStream,
				queryRes);
		ostream = new ByteArrayOutputStream();
		finalDocument.write(ostream);
		return ostream.toByteArray();

	}

	/**
	 * qui vanno inseriti nella table, presa dal template solo 3: nome e cognome
	 * consigliere gruppo carica
	 * 
	 * @param finalDocStream
	 * @param queryRes
	 * @return
	 * @throws IOException
	 */
	public XWPFDocument fillTemplate(ByteArrayInputStream finalDocStream,
			ResultSet queryRes) throws IOException {
		XWPFDocument document = new XWPFDocument(finalDocStream);
		List<XWPFTable> tables = document.getTables();
		for (int k = 0; k < queryRes.length(); k++) {
			NodeRef currentNodeRef = queryRes.getNodeRef(k);
			XWPFTable newTable = tables.get(k);
			XWPFTableRow firstRow = newTable.getRow(0);
			firstRow.getCell(0).setText("Consigliere");
			firstRow.getCell(0).setText("1x2");

			XWPFTableRow secondRow = newTable.getRow(0);
			secondRow.getCell(0).setText("2x1");
			secondRow.getCell(0).setText("2x2");
		}
		return document;
	}
}
