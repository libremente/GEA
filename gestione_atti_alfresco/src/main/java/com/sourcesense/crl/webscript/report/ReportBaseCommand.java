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
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
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
	protected String ruoloCommissione;
	protected String organismo;
	protected List<String> commissioniJson;
	
	protected String dataAssegnazioneCommReferenteDa;
	protected String dataAssegnazioneCommReferenteA;
	
	protected String dataVotazioneCommReferenteDa;
	protected String dataVotazioneCommReferenteA;
	
	protected String dataRitiroDa;
	protected String dataRitiroA;
	
	protected String dataNominaRelatoreDa;
	protected String dataNominaRelatoreA;
	
	protected String dataPresentazioneDa;
	protected String dataPresentazioneA;
	
	protected String dataAssegnazioneParereDa;
	protected String dataAssegnazioneParereA;
	
	
	
	
	protected List<String> relatoriJson;
	protected String firmatario;
	protected String tipologiaFirma;



	

	
	@Override
	public abstract byte[] generate(byte[] templateByteArray, String json, StoreRef spacesStore)
			throws IOException;
	
	/**
	 * return the right syntax for Alfresco lucene query on list
	 * @param field
	 * @param list
	 * @return
	 */
	protected static String convertListToString(String field,List<String> list){
		String stringSpaced = list.toString().replaceAll(", ", ",");
		String stringReplaced = stringSpaced.replaceAll(",","\" OR "+field+":\"");
		return "("+field+":\""+stringReplaced.substring(1, stringReplaced.length()-1)+"\")";
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
	
	protected int retrieveLenght( List<ResultSet> allSearches) {
		int count=0;
		for(ResultSet currentRes:allSearches){
			count+=currentRes.length();
		}
		return count;
	}
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
		this.ruoloCommissione = JsonUtils.retieveElementFromJson(
				rootJson, "ruoloCommissione");
		// converts the ruoloCommissione to a lucene field
		// extract the commissioni list from json
		this.commissioniJson = JsonUtils.retieveArrayListFromJson(rootJson,
				"commissioni");
		
	}

	protected void initFirmatario(String json) throws JSONException{
		JSONObject rootJson = new JSONObject(json);
		this.firmatario=JsonUtils.retieveElementFromJson(rootJson, "firmatario");
	}
	
	protected void initDataAssegnazioneParereDa(String json) throws JSONException{
		JSONObject rootJson = new JSONObject(json);
		this.dataAssegnazioneParereDa=JsonUtils.retieveElementFromJson(rootJson, "dataAssegnazioneParereDa");
	}
	
	protected void initDataAssegnazioneParereA(String json) throws JSONException{
		JSONObject rootJson = new JSONObject(json);
		this.dataAssegnazioneParereA=JsonUtils.retieveElementFromJson(rootJson, "dataAssegnazioneParereA");
	}
	
	protected void initDataPresentazioneDa(String json) throws JSONException{
		JSONObject rootJson = new JSONObject(json);
		this.dataPresentazioneDa=JsonUtils.retieveElementFromJson(rootJson, "dataPresentazioneDa");
	}
	
	protected void initDataPresentazioneA(String json) throws JSONException{
		JSONObject rootJson = new JSONObject(json);
		this.dataPresentazioneA=JsonUtils.retieveElementFromJson(rootJson, "dataPresentazioneA");
	}
	
	protected void initDataNominaRelatoreDa(String json) throws JSONException{
		JSONObject rootJson = new JSONObject(json);
		this.dataNominaRelatoreDa=JsonUtils.retieveElementFromJson(rootJson, "dataNominaRelatoreDa");
	}
	
	protected void initDataNominaRelatoreA(String json) throws JSONException{
		JSONObject rootJson = new JSONObject(json);
		this.dataNominaRelatoreA=JsonUtils.retieveElementFromJson(rootJson, "dataNominaRelatoreA");
	}
	
	protected void initDataRitiroDa(String json) throws JSONException{
		JSONObject rootJson = new JSONObject(json);
		this.dataRitiroDa=JsonUtils.retieveElementFromJson(rootJson, "dataRitiroDa");
	}
	
	protected void initDataRitiroA(String json) throws JSONException{
		JSONObject rootJson = new JSONObject(json);
		this.dataRitiroA=JsonUtils.retieveElementFromJson(rootJson, "dataRitiroA");
	}
	
	protected void initDataAssegnazioneCommReferenteDa(String json) throws JSONException{
		JSONObject rootJson = new JSONObject(json);
		this.dataAssegnazioneCommReferenteDa=JsonUtils.retieveElementFromJson(rootJson, "dataAssegnazioneCommReferenteDa");
	}
	
	protected void initDataAssegnazioneCommReferenteA(String json) throws JSONException{
		JSONObject rootJson = new JSONObject(json);
		this.dataAssegnazioneCommReferenteA=JsonUtils.retieveElementFromJson(rootJson, "dataAssegnazioneCommReferenteA");
	}
	
	protected void initDataVotazioneCommReferenteDa(String json) throws JSONException{
		JSONObject rootJson = new JSONObject(json);
		this.dataVotazioneCommReferenteDa=JsonUtils.retieveElementFromJson(rootJson, "dataVotazioneCommReferenteDa");
	}
	
	protected void initDataVotazioneCommReferenteA(String json) throws JSONException{
		JSONObject rootJson = new JSONObject(json);
		this.dataVotazioneCommReferenteA=JsonUtils.retieveElementFromJson(rootJson, "dataVotazioneCommReferenteA");
	}
	
	protected void initTipoFirma(String json) throws JSONException{
		JSONObject rootJson = new JSONObject(json);
		this.tipologiaFirma=JsonUtils.retieveElementFromJson(rootJson, "tipologiaFirma");
	}
	protected void initOrganismo(String json) throws JSONException{
		JSONObject rootJson = new JSONObject(json);
		this.organismo=JsonUtils.retieveElementFromJson(rootJson, "organismo");
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
	 * deprecated
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

}
