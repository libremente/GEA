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
			//riporta una stringa con la concat e gli OR in mezzo, non facevo in tempo prima di pranzo
			for(int i=0;i<tipiAttoLucene.size();i++){
			queryRes = searchService.query(Repository.getStoreRef(), 
					SearchService.LANGUAGE_LUCENE, "PATH:\""+tipiAttoLucene.get(i)+"\" AND "+ruoloCommissioneLuceneField+":\""+commissioniJson+"\"");
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

	private List<String> convertToLuceneTipiAtto(List<String> tipiAttoJson) {
		List<String> luceneTipiAttoList = new ArrayList<String>();
		for (String jsonType : tipiAttoJson) {
			String luceneValueType = "{" + super.CRL_ATTI_MODEL + "}"
					+ jsonType;
			luceneTipiAttoList.add(luceneValueType);
		}
		return luceneTipiAttoList;
	}

	/**
	 * return a simple element for the current JsonObj and elementname
	 * 
	 * @param root
	 * @param arrayName
	 * @return
	 * @throws JSONException
	 */
	public String retieveElementFromJson(JSONObject root, String elementName)
			throws JSONException {
		String elementValue = root.getString(elementName);
		return elementValue;
	}

	/**
	 * return the ArrayList of elements for the current JsonObj and arrayName
	 * 
	 * @param root
	 * @param arrayName
	 * @return
	 * @throws JSONException
	 */
	public List<String> retieveArrayListFromJson(JSONObject root,
			String arrayName) throws JSONException {
		JSONArray jsonArray = root.getJSONArray(arrayName);
		List<String> tipiAttoList = new ArrayList<String>();
		for (int i = 0; i < jsonArray.length(); i++) {
			String jsonElement = jsonArray.getString(i).trim();
			if (!jsonElement.equals(""))
				tipiAttoList.add(this.extractJsonSingleValue(jsonElement));
		}
		return tipiAttoList;
	}

	/**
	 * Returns the value for this single json element in this form :
	 * "tipoAtto":"PDL"
	 * 
	 * @param tipoAtto
	 * @return
	 */
	private String extractJsonSingleValue(String jsonElement) {
		int indexFieldValueSeparator = jsonElement.indexOf(":");
		int indexValueEnd = jsonElement.lastIndexOf("\"");
		return jsonElement.substring(indexFieldValueSeparator + 2,
				indexValueEnd);
	}

	/**
	 * @param generatedDocument
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private ByteArrayInputStream saveTemp(XWPFDocument generatedDocument)
			throws IOException, FileNotFoundException {
		ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		generatedDocument.write(ostream);
		return new ByteArrayInputStream(ostream.toByteArray());
		/*
		 * usually duplicated template became useful only after disk save and
		 * re-import URL testTableOutput = getClass().getResource(""); String
		 * path = testTableOutput.getPath(); File out = new File(path +
		 * "/attiTemp.docx"); out.createNewFile(); FileOutputStream fos = new
		 * FileOutputStream(out); generatedDocument.write(fos); fos.flush();
		 * fos.close(); FileInputStream reader=new FileInputStream(out); return
		 * reader;
		 */
	}

	public XWPFDocument fillTemplate(ByteArrayInputStream finalDocStream,
			ResultSet queryRes) throws IOException {
		XWPFDocument document = new XWPFDocument(finalDocStream);
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
