package com.sourcesense.crl.webscript.report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.web.bean.repository.Repository;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sourcesense.crl.webscript.report.office.DocxManager;

public class ReportAttiAssCommissioniServCommCommand extends ReportBaseCommand {

	@Override
	public byte[] generate(byte[] templateByteArray, String json)
			throws IOException {
		ByteArrayOutputStream ostream = null;
		try {
			ByteArrayInputStream is = new ByteArrayInputStream(
					templateByteArray);
			DocxManager docxManager = new DocxManager(is);
			JSONObject rootJson = new JSONObject(json);
			// extract the tipiAtto list from the json string
			List<String> tipiAttoJson = this.retieveArrayListFromJson(rootJson,
					"tipiAtto");
			// convert the list in the lucene format
			List<String> tipiAttoLucene = this
					.convertToLuceneTipiAtto(tipiAttoJson);
			//extract the ruoloCommissione element from json
			String ruoloCommissioneJson=this.retieveElementFromJson(rootJson,"ruoloCommissione");
			//converts the ruoloCommissione to a lucene field
			String ruoloCommissioneLuceneField=super.json2luceneField.get(ruoloCommissioneJson);
			//extract the commissioni list from json
			List<String> commissioniJson = this.retieveArrayListFromJson(rootJson,
					"commissioni");
			ResultSet queryRes=null;
			// costruire n query quanti i tipi di atto? vanno messi nel path?
			//per il momento per commissioni ho semplicemente messo il toString della lista che si traduce in valori separati da spazio
			//per aggiungere gli OR basta creare un metodo che prendendo in input una lista di stringhe
			//riporta una stringa con la concat e gli OR in mezzo
			// va valutato come gestire la data
			for(int i=0;i<tipiAttoLucene.size();i++){
			queryRes = searchService.query(Repository.getStoreRef(), 
					SearchService.LANGUAGE_LUCENE, "TYPE:\""+tipiAttoLucene.get(i)+"\" AND "+ruoloCommissioneLuceneField+":\""+commissioniJson+"\"");
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
	 * qui vanno inseriti nella table, presa dal template solo 8:
	 * tipo atto- numero atto- competenza - iniziativa- oggetto - data assegnazione - data valutazione - commissione referente
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
