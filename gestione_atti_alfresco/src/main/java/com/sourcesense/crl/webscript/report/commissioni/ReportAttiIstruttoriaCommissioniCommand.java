package com.sourcesense.crl.webscript.report.commissioni;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.web.bean.repository.Repository;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.json.JSONException;

import com.sourcesense.crl.webscript.report.ReportBaseCommand;
import com.sourcesense.crl.webscript.report.util.office.DocxManager;

public class ReportAttiIstruttoriaCommissioniCommand extends ReportBaseCommand {

	@Override
	public byte[] generate(byte[] templateByteArray, String json)
			throws IOException {
		ByteArrayOutputStream ostream = null;
		try {
			ByteArrayInputStream is = new ByteArrayInputStream(
					templateByteArray);
			DocxManager docxManager = new DocxManager(is);
			this.initCommonParams(json);
			// data di assegnazione in intervallo
			ResultSet queryRes = null;
			// costruire n query quanti i tipi di atto? vanno messi nel path?
			// per il momento per commissioni ho semplicemente messo il toString
			// della lista che si traduce in valori separati da spazio
			// per aggiungere gli OR basta creare un metodo che prendendo in
			// input una lista di stringhe
			// riporta una stringa con la concat e gli OR in mezzo
			// va valutato come gestire la data
			for (int i = 0; i < tipiAttoLucene.size(); i++) {
				queryRes = searchService.query(Repository.getStoreRef(),
						SearchService.LANGUAGE_LUCENE, "TYPE:\""
								+ tipiAttoLucene.get(i) + "\" AND "
								+ ruoloCommissioneLuceneField + ":\""
								+ commissioniJson + "\"");
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
	 * ipotizzo field già presenti nella table, quindi inseriamo solo value
	 * qui vanno inseriti nella table, presa dal template solo 8: tipo atto-
	 * numero atto - iniziativa - data assegnazione - commissione referente - commissioni consultive -
	 *  elenco relatori - data nomina relatori
	 * valutazione - commissione referente
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
