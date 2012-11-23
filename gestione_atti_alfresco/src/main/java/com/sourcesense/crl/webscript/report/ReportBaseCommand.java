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

public abstract class ReportBaseCommand implements ReportCommand {
	protected ContentService contentService;
	protected SearchService searchService;
	protected NodeService nodeService;
	protected Map<String,String> json2luceneField;

	protected static final String CRL_ATTI_MODEL = "http://www.regione.lombardia.it/content/model/atti/1.0";
	
	protected static final String RUOLO_COMM_REFERENTE = "Referente";
	protected static final String RUOLO_COMM_COREFERENTE = "Co-Referente";
	protected static final String RUOLO_COMM_CONSULTIVA = "Consultiva";
	protected static final String RUOLO_COMM_REDIGENTE = "Redigente";
	protected static final String RUOLO_COMM_DELIBERANTE = "Deliberante";
	
	/**
	 * init a simple map that link json names to lucene names
	 * */
	public void initJson2lucene(){
		json2luceneField=new HashMap<String,String>();
		json2luceneField.put(RUOLO_COMM_REFERENTE, "crlatti:commReferente");
		json2luceneField.put(RUOLO_COMM_COREFERENTE, "crlatti:commCoreferente");
		json2luceneField.put(RUOLO_COMM_CONSULTIVA, "crlatti:commConsultiva");
		json2luceneField.put(RUOLO_COMM_REDIGENTE, "crlatti:commRedigente");
		json2luceneField.put(RUOLO_COMM_DELIBERANTE, "crlatti:commDeliberante");
		
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
	
	/**
	 * return a simple element for the current JsonObj and elementname
	 * 
	 * @param root
	 * @param arrayName
	 * @return
	 * @throws JSONException
	 */
	protected String retieveElementFromJson(JSONObject root, String elementName)
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
	protected List<String> retieveArrayListFromJson(JSONObject root,
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
	protected String extractJsonSingleValue(String jsonElement) {
		int indexFieldValueSeparator = jsonElement.indexOf(":");
		int indexValueEnd = jsonElement.lastIndexOf("\"");
		return jsonElement.substring(indexFieldValueSeparator + 2,
				indexValueEnd);
	}
	
	protected List<String> convertToLuceneTipiAtto(List<String> tipiAttoJson) {
		List<String> luceneTipiAttoList = new ArrayList<String>();
		for (String jsonType : tipiAttoJson) {
			String luceneValueType = "{" + CRL_ATTI_MODEL + "}"
					+ jsonType;
			luceneTipiAttoList.add(luceneValueType);
		}
		return luceneTipiAttoList;
	}

	@Override
	public abstract byte[] generate(byte[] templateByteArray,String json) throws IOException;
	

}
