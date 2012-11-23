package com.sourcesense.crl.webscript.report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.search.SearchService;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sourcesense.crl.webscript.report.util.JsonUtils;

public abstract class ReportBaseCommand implements ReportCommand {
	protected ContentService contentService;
	protected SearchService searchService;
	protected NodeService nodeService;
	protected Map<String, String> json2luceneField;

	protected static final String CRL_ATTI_MODEL = "http://www.regione.lombardia.it/content/model/atti/1.0";

	protected static final String RUOLO_COMM_REFERENTE = "Referente";
	protected static final String RUOLO_COMM_COREFERENTE = "Co-Referente";
	protected static final String RUOLO_COMM_CONSULTIVA = "Consultiva";
	protected static final String RUOLO_COMM_REDIGENTE = "Redigente";
	protected static final String RUOLO_COMM_DELIBERANTE = "Deliberante";

	protected List<String> tipiAttoLucene;
	protected String ruoloCommissioneLuceneField;
	protected List<String> commissioniJson;
	protected List<String> relatoriJson;

	/**
	 * init the common params : List<String> tipiAttoLucene; String
	 * ruoloCommissioneLuceneField; List<String> commissioniJson;
	 * 
	 * @throws JSONException
	 */
	protected void initCommonParams(String json) throws JSONException {
		JSONObject rootJson = new JSONObject(json);
		initTipiAttoLucene(json);
		// extract the ruoloCommissione element from json
		String ruoloCommissioneJson = JsonUtils.retieveElementFromJson(
				rootJson, "ruoloCommissione");
		// converts the ruoloCommissione to a lucene field
		this.ruoloCommissioneLuceneField = this.json2luceneField
				.get(ruoloCommissioneJson);
		// extract the commissioni list from json
		this.commissioniJson = JsonUtils.retieveArrayListFromJson(rootJson,
				"commissioni");
	}

	protected void initRelatori(String json) throws JSONException{
		JSONObject rootJson = new JSONObject(json);
		this.relatoriJson=JsonUtils.retieveArrayListFromJson(rootJson,
				"relatori");
	}
	/**
	 * @param rootJson
	 * @throws JSONException
	 */
	protected void initTipiAttoLucene(String json) throws JSONException {
		JSONObject rootJson = new JSONObject(json);
		// extract the tipiAtto list from the json string
		List<String> tipiAttoJson = JsonUtils.retieveArrayListFromJson(
				rootJson, "tipiAtto");
		// convert the list in the lucene format
		this.tipiAttoLucene = this.convertToLuceneTipiAtto(tipiAttoJson);
	}

	/**
	 * init a simple map that link json names to lucene names
	 * */
	public void initJson2lucene() {
		json2luceneField = new HashMap<String, String>();
		json2luceneField.put(RUOLO_COMM_REFERENTE, "crlatti:commReferente");
		json2luceneField.put(RUOLO_COMM_COREFERENTE, "crlatti:commCoreferente");
		json2luceneField.put(RUOLO_COMM_CONSULTIVA, "crlatti:commConsultiva");
		json2luceneField.put(RUOLO_COMM_REDIGENTE, "crlatti:commRedigente");

	}

	public Map<String, String> getJson2luceneField() {
		return json2luceneField;
	}

	public void setJson2luceneField(Map<String, String> json2luceneField) {
		this.json2luceneField = json2luceneField;
	}

	public ContentService getContentService() {
		return contentService;
	}

	public void setContentService(ContentService contentService) {
		this.contentService = contentService;
	}

	public SearchService getSearchService() {
		return searchService;
	}

	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}

	public NodeService getNodeService() {
		return nodeService;
	}

	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	/**
	 * @param generatedDocument
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	protected ByteArrayInputStream saveTemp(XWPFDocument generatedDocument)
			throws IOException, FileNotFoundException {
		ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		generatedDocument.write(ostream);
		return new ByteArrayInputStream(ostream.toByteArray());
	}

	protected List<String> convertToLuceneTipiAtto(List<String> tipiAttoJson) {
		List<String> luceneTipiAttoList = new ArrayList<String>();
		for (String jsonType : tipiAttoJson) {
			String luceneValueType = "{" + CRL_ATTI_MODEL + "}" + jsonType;
			luceneTipiAttoList.add(luceneValueType);
		}
		return luceneTipiAttoList;
	}

	@Override
	public abstract byte[] generate(byte[] templateByteArray, String json)
			throws IOException;

}
