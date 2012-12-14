package com.sourcesense.crl.webscript.report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.service.cmr.dictionary.DictionaryService;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.DynamicNamespacePrefixResolver;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.sourcesense.crl.webscript.report.util.JsonUtils;

public abstract class ReportBaseCommand implements ReportCommand {
	protected ContentService contentService;
	protected SearchService searchService;
	protected NodeService nodeService;
	protected DictionaryService dictionaryService;
	protected Map<String, String> json2luceneField;

	protected static final String CRL_ATTI_MODEL = "http://www.regione.lombardia.it/content/model/atti/1.0";
	protected final static String DATA_FORMAT = "dd/MM/yyyy";
	protected static final String RUOLO_COMM_REFERENTE = "Referente";
	protected static final String RUOLO_COMM_COREFERENTE = "Co-Referente";
	protected static final String RUOLO_COMM_CONSULTIVA = "Consultiva";
	protected static final String RUOLO_COMM_REDIGENTE = "Redigente";
	protected static final String RUOLO_COMM_DELIBERANTE = "Deliberante";

	public static final String PRESO_CARICO_COMMISSIONE = "Preso in carico da Commissioni";
	public static final String NOMINATO_RELATORE = "Nominato Relatore";
	public static final String VOTATO_COMMISSIONE = "Votato in Commissione";
	public static final String LAVORI_COMITATO_RISTRETTO = "Lavori Comitato Ristretto";

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
	
	protected String dataSedutaDa;
	protected String dataSedutaA;

	protected List<String> relatoriJson;
	protected String firmatario;
	protected String tipologiaFirma;

	@Override
	public abstract byte[] generate(byte[] templateByteArray, String json,
			StoreRef spacesStore) throws IOException;

	
	/**
	 * Extract only valuable commissioni from the ones extracted from the
	 * Relatori
	 * 
	 * @param relatoriResults
	 * @return
	 */
	protected List<NodeRef> retrieveCommissioniFromRelatori(
			ResultSet relatoriResults) {
		List<NodeRef> commissioni=Lists.newArrayList();
		int resultLength = relatoriResults.length();
		for (int i = 0; i < resultLength; i++) {
			NodeRef relatoreNodeRef = relatoriResults.getNodeRef(i);
			Map<QName, Serializable> relatoreProperties = nodeService
					.getProperties(relatoreNodeRef);
			NodeRef commissioneNodeRef = relatoreNodeRef;
			boolean check = false;
			while (!check) {// look for Atto in type hierarchy
				ChildAssociationRef childAssociationRef = nodeService
						.getPrimaryParent(commissioneNodeRef);
				commissioneNodeRef = childAssociationRef.getParentRef();
				QName nodeRefType = nodeService.getType(commissioneNodeRef);
				QName attoRefType = QName.createQName(CRL_ATTI_MODEL,
						"commissione");
				check = dictionaryService.isSubClass(nodeRefType,
						attoRefType);
			}
			Map<QName, Serializable> commissioneProperties = nodeService
					.getProperties(commissioneNodeRef);
			String commissione = (String) this.getNodeRefProperty(
					commissioneProperties, "name");
			if(this.checkCommissione(commissione))
				commissioni.add(commissioneNodeRef);

		}
		return commissioni;
	}
	

	/**
	 * check if the "tipoAtto" in input is good, related to the selection of
	 * tipoAtto good
	 * 
	 * @param tipoAtto
	 * @return
	 */
	private boolean checkCommissione(String commissione) {
		return this.commissioniJson.contains(commissione);
	}
	/**
	 * Extract the information from the result set, retrieving Atti
	 * 
	 * @param commissione2results
	 *            - String commissione -> ResultSet
	 * @param spacesStore
	 *            - current space store of search
	 * @param atto2commissione
	 *            - NodeRef type Atto -> NodeRef type Commissione
	 * @return
	 */
	protected ArrayListMultimap<String, NodeRef> retrieveAtti(
			Map<String, ResultSet> commissione2results, StoreRef spacesStore,
			Map<NodeRef, NodeRef> atto2commissione) {
		ArrayListMultimap<String, NodeRef> commissione2atti = ArrayListMultimap
				.create();
		for (String commissione : commissione2results.keySet()) {
			ResultSet commissioneResults = commissione2results.get(commissione);
			List<NodeRef> nodeRefList = commissioneResults.getNodeRefs();
			this.retrieveAttiFromList(nodeRefList, spacesStore,
					atto2commissione, commissione2atti);
		}
		return commissione2atti;
	}

	public ResultSet getRelatori(NodeRef commissioneNodeRef) {
		DynamicNamespacePrefixResolver namespacePrefixResolver = new DynamicNamespacePrefixResolver(
				null);
		namespacePrefixResolver.registerNamespace(
				NamespaceService.SYSTEM_MODEL_PREFIX,
				NamespaceService.SYSTEM_MODEL_1_0_URI);
		namespacePrefixResolver.registerNamespace(
				NamespaceService.CONTENT_MODEL_PREFIX,
				NamespaceService.CONTENT_MODEL_1_0_URI);
		namespacePrefixResolver.registerNamespace(
				NamespaceService.APP_MODEL_PREFIX,
				NamespaceService.APP_MODEL_1_0_URI);

		String luceneCommissioneNodePath = nodeService.getPath(
				commissioneNodeRef).toPrefixString(namespacePrefixResolver);

		String relatoreType = "{" + CRL_ATTI_MODEL + "}" + "relatore";

		// Get relatore
		ResultSet relatoreNodes = searchService.query(
				commissioneNodeRef.getStoreRef(),
				SearchService.LANGUAGE_LUCENE, "PATH:\""
						+ luceneCommissioneNodePath
						+ "/cm:Relatori/*\" AND TYPE:\"" + relatoreType + "\"");

		return relatoreNodes;
	}

	protected void retrieveAttiFromList(List<NodeRef> nodeRefList,
			StoreRef spacesStore, Map<NodeRef, NodeRef> atto2commissione,
			ArrayListMultimap<String, NodeRef> commissione2atti) {
		int resultLength = nodeRefList.size();
		for (int i = 0; i < resultLength; i++) {
			NodeRef commissioneNodeRef = nodeRefList.get(i);
			Map<QName, Serializable> commissioneProperties = nodeService
					.getProperties(commissioneNodeRef);
			String commissione = (String) this.getNodeRefProperty(
					commissioneProperties, "name");
			NodeRef attoNodeRef = commissioneNodeRef;
			boolean check = false;
			while (!check) {// look for Atto in type hierarchy
				ChildAssociationRef childAssociationRef = nodeService
						.getPrimaryParent(attoNodeRef);
				attoNodeRef = childAssociationRef.getParentRef();
				QName nodeRefType = nodeService.getType(attoNodeRef);
				QName attoRefType = QName.createQName(CRL_ATTI_MODEL, "atto");
				check = dictionaryService.isSubClass(nodeRefType, attoRefType);
			}
			commissione2atti.put(commissione, attoNodeRef);
			atto2commissione.put(attoNodeRef, commissioneNodeRef);

		}
	}

	/**
	 * return the right syntax for Alfresco lucene query on list
	 * 
	 * @param field
	 * @param list
	 * @return
	 */
	protected static String convertListToString(String field, List<String> list) {
		String stringSpaced = list.toString().replaceAll(", ", ",");
		String stringReplaced = stringSpaced.replaceAll(",",
				"\" OR " + field.replaceFirst("\\:", "\\\\:") + ":\"");
		return "(" + field + ":\""
				+ stringReplaced.substring(1, stringReplaced.length() - 1)
				+ "\")";
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

	protected int retrieveLenght(ArrayListMultimap<String, NodeRef> group2atti) {
		int count = 0;
		for (String group : group2atti.keySet()) {
			count += group2atti.get(group).size();
		}
		return count;
	}

	protected int retrieveLenght(List<ResultSet> allSearches) {
		// TODO Auto-generated method stub
		return 0;
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
		this.ruoloCommissione = JsonUtils.retieveElementFromJson(rootJson,
				"ruoloCommissione");
		// converts the ruoloCommissione to a lucene field
		// extract the commissioni list from json
		this.commissioniJson = JsonUtils.retieveArrayListFromJson(rootJson,
				"commissioni");

	}

	protected void initFirmatario(String json) throws JSONException {
		JSONObject rootJson = new JSONObject(json);
		this.firmatario = JsonUtils.retieveElementFromJson(rootJson,
				"firmatario");
	}
	protected void initDataSedutaDa(String json)
			throws JSONException {
		JSONObject rootJson = new JSONObject(json);
		this.dataSedutaDa = JsonUtils.retieveElementFromJson(
				rootJson, "dataSedutaDa");
	}
	protected void initDataSedutaA(String json)
			throws JSONException {
		JSONObject rootJson = new JSONObject(json);
		this.dataSedutaA = JsonUtils.retieveElementFromJson(
				rootJson, "dataSedutaA");
	}

	protected void initDataAssegnazioneParereDa(String json)
			throws JSONException {
		JSONObject rootJson = new JSONObject(json);
		this.dataAssegnazioneParereDa = JsonUtils.retieveElementFromJson(
				rootJson, "dataAssegnazioneParereDa");
	}

	protected void initDataAssegnazioneParereA(String json)
			throws JSONException {
		JSONObject rootJson = new JSONObject(json);
		this.dataAssegnazioneParereA = JsonUtils.retieveElementFromJson(
				rootJson, "dataAssegnazioneParereA");
	}

	protected void initDataPresentazioneDa(String json) throws JSONException {
		JSONObject rootJson = new JSONObject(json);
		this.dataPresentazioneDa = JsonUtils.retieveElementFromJson(rootJson,
				"dataPresentazioneDa");
	}

	protected void initDataPresentazioneA(String json) throws JSONException {
		JSONObject rootJson = new JSONObject(json);
		this.dataPresentazioneA = JsonUtils.retieveElementFromJson(rootJson,
				"dataPresentazioneA");
	}

	protected void initDataNominaRelatoreDa(String json) throws JSONException {
		JSONObject rootJson = new JSONObject(json);
		this.dataNominaRelatoreDa = JsonUtils.retieveElementFromJson(rootJson,
				"dataNominaRelatoreDa");
	}

	protected void initDataNominaRelatoreA(String json) throws JSONException {
		JSONObject rootJson = new JSONObject(json);
		this.dataNominaRelatoreA = JsonUtils.retieveElementFromJson(rootJson,
				"dataNominaRelatoreA");
	}

	protected void initDataRitiroDa(String json) throws JSONException {
		JSONObject rootJson = new JSONObject(json);
		this.dataRitiroDa = JsonUtils.retieveElementFromJson(rootJson,
				"dataRitiroDa");
	}

	protected void initDataRitiroA(String json) throws JSONException {
		JSONObject rootJson = new JSONObject(json);
		this.dataRitiroA = JsonUtils.retieveElementFromJson(rootJson,
				"dataRitiroA");
	}

	protected void initDataAssegnazioneCommReferenteDa(String json)
			throws JSONException {
		JSONObject rootJson = new JSONObject(json);
		this.dataAssegnazioneCommReferenteDa = JsonUtils
				.retieveElementFromJson(rootJson,
						"dataAssegnazioneCommReferenteDa");
	}

	protected void initDataAssegnazioneCommReferenteA(String json)
			throws JSONException {
		JSONObject rootJson = new JSONObject(json);
		this.dataAssegnazioneCommReferenteA = JsonUtils.retieveElementFromJson(
				rootJson, "dataAssegnazioneCommReferenteA");
	}

	protected void initDataVotazioneCommReferenteDa(String json)
			throws JSONException {
		JSONObject rootJson = new JSONObject(json);
		this.dataVotazioneCommReferenteDa = JsonUtils.retieveElementFromJson(
				rootJson, "dataVotazioneCommReferenteDa");
	}

	protected void initDataVotazioneCommReferenteA(String json)
			throws JSONException {
		JSONObject rootJson = new JSONObject(json);
		this.dataVotazioneCommReferenteA = JsonUtils.retieveElementFromJson(
				rootJson, "dataVotazioneCommReferenteA");
	}

	protected void initTipoFirma(String json) throws JSONException {
		JSONObject rootJson = new JSONObject(json);
		this.tipologiaFirma = JsonUtils.retieveElementFromJson(rootJson,
				"tipologiaFirma");
	}

	protected void initOrganismo(String json) throws JSONException {
		JSONObject rootJson = new JSONObject(json);
		this.organismo = JsonUtils
				.retieveElementFromJson(rootJson, "organismo");
	}

	protected void initRelatori(String json) throws JSONException {
		JSONObject rootJson = new JSONObject(json);
		this.relatoriJson = JsonUtils.retieveArrayListFromJson(rootJson,
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
		this.tipiAttoLucene = tipiAttoJson;
	}

	protected String checkDateEmpty(Date attributeDate) {
		if (attributeDate == null)
			return "";
		else {
			SimpleDateFormat dateFormatter = new SimpleDateFormat(DATA_FORMAT);
			return dateFormatter.format(attributeDate);
		}

	}

	protected String checkStringEmpty(String attribute) {
		if (attribute == null)
			return "";
		else
			return attribute;

	}

	protected Serializable getNodeRefProperty(
			Map<QName, Serializable> properties, String attribute) {
		return properties.get(QName.createQName(CRL_ATTI_MODEL, attribute));
	}

	/**
	 * init a simple map that link json names to lucene names deprecated
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

	public DictionaryService getDictionaryService() {
		return dictionaryService;
	}

	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

}
