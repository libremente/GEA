package com.sourcesense.crl.webscript.report.servizio_commissioni;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.web.bean.repository.Repository;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.json.JSONException;

import com.sourcesense.crl.webscript.report.ReportBaseCommand;
import com.sourcesense.crl.webscript.report.util.office.DocxManager;

public class ReportAttiRitiratiRevocatiCommand extends ReportBaseCommand {

	@Override
	public byte[] generate(byte[] templateByteArray, String json)
			throws IOException {
		ByteArrayOutputStream ostream = null;
		try {
			ByteArrayInputStream is = new ByteArrayInputStream(
					templateByteArray);
			DocxManager docxManager = new DocxManager(is);
			this.initTipiAttoLucene(json);
			// data ritiro
			ResultSet queryRes = null;
	
			for (int i = 0; i < tipiAttoLucene.size(); i++) {
				queryRes = searchService.query(Repository.getStoreRef(),
						SearchService.LANGUAGE_LUCENE, "TYPE:\""
								+ tipiAttoLucene.get(i));
			}

			// obtain resultSet Length and cycle on it to repeat template
			XWPFDocument generatedDocument = docxManager.generateFromTemplate(
					queryRes.length(), 5);
			// convert to input stream
			ByteArrayInputStream tempInputStream = saveTemp(generatedDocument);

			XWPFDocument finalDocument = this.fillTemplate(tempInputStream,
					queryRes);
			ostream = new ByteArrayOutputStream();
			finalDocument.write(ostream);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ostream.toByteArray();

	}

	/**
	 * qui vanno inseriti nella table, presa dal template solo 6: tipo atto-
	 * numero atto- iniziativa -firmatari- oggetto-data presentazione- data revoca
	 * 
	 * 
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
			XWPFTable newTable = tables.get(k);
			XWPFTableRow firstRow = newTable.getRow(0);
			firstRow.getCell(0).setText("1x1");
			firstRow.getCell(0).setText("1x2");

			XWPFTableRow secondRow = newTable.getRow(0);
			secondRow.getCell(0).setText("1x1");
			secondRow.getCell(0).setText("1x2");
		}
		return document;
	}
}
