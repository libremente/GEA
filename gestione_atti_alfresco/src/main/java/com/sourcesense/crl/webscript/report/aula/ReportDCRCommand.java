package com.sourcesense.crl.webscript.report.aula;

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

public class ReportDCRCommand extends ReportBaseCommand {

	@Override
	public byte[] generate(byte[] templateByteArray, String json, StoreRef attoNodeRef)
			throws IOException {
		ByteArrayOutputStream ostream = null;
		ByteArrayInputStream is = new ByteArrayInputStream(templateByteArray);
		DocxManager docxManager = new DocxManager(is);
		// data seduta
		String tipoAtto = "{" + CRL_ATTI_MODEL + "}" + "PDL";
		ResultSet queryRes = null;

		queryRes = searchService.query(Repository.getStoreRef(),
				SearchService.LANGUAGE_LUCENE, "TYPE:\"" + tipoAtto + "\"");

		// obtain resultSet Length and cycle on it to repeat template
		XWPFDocument generatedDocument = docxManager.generateFromTemplate(
				queryRes.length(), 4, false);
		// convert to input stream
		ByteArrayInputStream tempInputStream = saveTemp(generatedDocument);

		XWPFDocument finalDocument = this.fillTemplate(tempInputStream,
				queryRes);
		ostream = new ByteArrayOutputStream();
		finalDocument.write(ostream);
		return ostream.toByteArray();

	}

	/**
	 * ipotizzo field già presenti nella table, quindi inseriamo solo value qui
	 * vanno inseriti nella table, presa dal template solo 8: tipo atto- numero
	 * 
	 * numero LCR - numero DCR - data seduta - oggetto atto - tipi atto - numero atto - 
	 * emendata - commissione referente - numero LR - data LR - numero BURL - data BURL - note generali aula
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

			firstRow.getCell(0).setText("1x2");

			XWPFTableRow secondRow = newTable.getRow(0);
			secondRow.getCell(0).setText("1x1");
			secondRow.getCell(0).setText("1x2");
		}
		return document;
	}
}
