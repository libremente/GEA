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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.alfresco.model.ContentModel;
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
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
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
	public static final String PRESO_CARICO_AULA = "Preso in carico da Aula";
	public static final String NOMINATO_RELATORE = "Nominato Relatore";
	public static final String VOTATO_COMMISSIONE = "Votato in Commissione";
	public static final String LAVORI_COMITATO_RISTRETTO = "Lavori Comitato Ristretto";
	public static final String CHIUSO = "Chiuso";
	public static final String RITIRATO = "Ritirato dai promotori";

	private static final String SEP = ", ";
	private static final String ABBINAMENTI_SPACE_NAME = "Abbinamenti";

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
	 * Relatori.
	 * For each relatore is analyzed its typer hierarchy, and when a Commissione is found, its name is checked.
	 * 
	 * @param relatoriResults
	 * @return List of Commissioni NodeRef
	 */
	protected List<NodeRef> retrieveCommissioniFromRelatori(
			ResultSet relatoriResults) {
		List<NodeRef> commissioni = Lists.newArrayList();
		int resultLength = relatoriResults.length();
		for (int i = 0; i < resultLength; i++) {
			NodeRef relatoreNodeRef = relatoriResults.getNodeRef(i);
			Map<QName, Serializable> relatoreProperties = nodeService
					.getProperties(relatoreNodeRef);
			NodeRef commissioneNodeRef = relatoreNodeRef;
			boolean check = false;
			while (!check) {// look for Commissione in type hierarchy
				ChildAssociationRef childAssociationRef = nodeService
						.getPrimaryParent(commissioneNodeRef);
				commissioneNodeRef = childAssociationRef.getParentRef();
				QName nodeRefType = nodeService.getType(commissioneNodeRef);
				QName attoRefType = QName.createQName(CRL_ATTI_MODEL,
						"commissione");
				check = dictionaryService.isSubClass(nodeRefType, attoRefType);
			}
			Map<QName, Serializable> commissioneProperties = nodeService
					.getProperties(commissioneNodeRef);
			String commissione = (String) this.getNodeRefProperty(
					commissioneProperties, "name");
			if (this.checkCommissione(commissione))
				commissioni.add(commissioneNodeRef);

		}
		return commissioni;
	}

	/**
	 * check if the Commissione in input is good, related to the selection of
	 * Commissione good
	 * 
	 * @param tipoAtto
	 * @return
	 */
	private boolean checkCommissione(String commissione) {
		return this.commissioniJson.contains(commissione);
	}

	/**
	 * For each Commissione(String) in input , access to its resultSet and retrieve 
	 * the list of Atto NodeRef.
	 * 
	 * @param attoChild2results
	 *            - String commissione -> ResultSet
	 * @param spacesStore
	 *            - current space store of search
	 * @param atto2child
	 *            - NodeRef type Atto -> NodeRef type Commissione
	 * @return
	 */
	protected ArrayListMultimap<String, NodeRef> retrieveAtti(
			Map<String, ResultSet> attoChild2results, StoreRef spacesStore,
			Map<NodeRef, NodeRef> atto2child) {
		ArrayListMultimap<String, NodeRef> child2atti = ArrayListMultimap
				.create();
		for (String commissione : attoChild2results.keySet()) {
			ResultSet commissioneResults = attoChild2results.get(commissione);
			List<NodeRef> nodeRefList = commissioneResults.getNodeRefs();
			this.retrieveAttiFromList(nodeRefList, spacesStore, atto2child,
					child2atti, commissione);
		}
		return child2atti;
	}

	/**
	 * Retrieve Atto from a NodeRef list of results from the query.
	 * For example a list of Commissione or Parere.
	 * Then from each child node, it find the related Atto.
	 * @param nodeRefList - the list of Commissione NodeRef
	 * @param spacesStore - the current space store  workspace://SpacesStore
	 * @param atto2child - a map that relates each atto to its specif childNodes( for example Commissione NodeRef)
	 * @param child2atti - a map that relates a specific child NodeRef
	 * @param child - the String of the child related to different Atto ( for example Commissione I)
	 */
	protected void retrieveAttiFromList(List<NodeRef> nodeRefList,
			StoreRef spacesStore, Map<NodeRef, NodeRef> atto2child,
			ArrayListMultimap<String, NodeRef> child2atti, String child) {
		int resultLength = nodeRefList.size();
		for (int i = 0; i < resultLength; i++) {
			NodeRef childNodeRef = nodeRefList.get(i);
			NodeRef attoNodeRef = childNodeRef;
			boolean check = false;
			while (!check) {// look for Atto in type hierarchy
				ChildAssociationRef childAssociationRef = nodeService
						.getPrimaryParent(attoNodeRef);
				attoNodeRef = childAssociationRef.getParentRef();
				QName nodeRefType = nodeService.getType(attoNodeRef);
				QName attoRefType = QName.createQName(CRL_ATTI_MODEL, "atto");
				check = dictionaryService.isSubClass(nodeRefType, attoRefType);
			}
			child2atti.put(child, attoNodeRef);
			atto2child.put(attoNodeRef, childNodeRef);

		}
	}

	/**
	 * Retrieve Atto using an ordered sequence of node child names ( for example Relatore)
	 * and then extract from each relatore the related Atto
	 * @param relatore2results
	 * @param spacesStore
	 * @param atto2commissione
	 * @return
	 */
	protected TreeMap<String, List<NodeRef>> retrieveAttiOrdered(
			Map<String, ResultSet> relatore2results, StoreRef spacesStore,
			Map<NodeRef, NodeRef> atto2commissione) {
		TreeMap<String, List<NodeRef>> relatore2atti = Maps.newTreeMap();
		Set<String> relatori = relatore2results.keySet();
		for (String relatore : relatori) {
			ResultSet commissioneResults = relatore2results.get(relatore);
			List<NodeRef> nodeRefList = commissioneResults.getNodeRefs();
			this.retrieveAttiFromListOrdered(nodeRefList, spacesStore,
					atto2commissione, relatore2atti, relatore);
		}
		return relatore2atti;
	}

	/**
	 * Retrieve Atto from a NodeRef list of results from the query.
	 * For example a list of Relatore or Parere.
	 * Then from each child node, it find the related Atto.
	 * TreeMap is used to gain ordering power.
	 * 
	 * @param nodeRefList - the list of Commissione NodeRef
	 * @param spacesStore - the current space store  workspace://SpacesStore
	 * @param atto2child - a map that relates each atto to its specif childNodes( for example Commissione NodeRef)
	 * @param child2atti - a map that relates a specific child NodeRef
	 * @param child - the String of the child related to different Atto ( for example Commissione I)
	 */
	protected void retrieveAttiFromListOrdered(List<NodeRef> nodeRefList,
			StoreRef spacesStore, Map<NodeRef, NodeRef> atto2child,
			TreeMap<String, List<NodeRef>> child2atti, String child) {
		int resultLength = nodeRefList.size();
		for (int i = 0; i < resultLength; i++) {
			NodeRef childNodeRef = nodeRefList.get(i);
			NodeRef attoNodeRef = childNodeRef;
			boolean check = false;
			while (!check) {// look for Atto in type hierarchy
				ChildAssociationRef childAssociationRef = nodeService
						.getPrimaryParent(attoNodeRef);
				attoNodeRef = childAssociationRef.getParentRef();
				QName nodeRefType = nodeService.getType(attoNodeRef);
				QName attoRefType = QName.createQName(CRL_ATTI_MODEL, "atto");
				check = dictionaryService.isSubClass(nodeRefType, attoRefType);
			}
			if (child2atti.get(child) != null) {
				child2atti.get(child).add(attoNodeRef);
			} else {
				List<NodeRef> earlyCreatedList = new LinkedList<NodeRef>();
				earlyCreatedList.add(attoNodeRef);
				child2atti.put(child, earlyCreatedList);
			}

			atto2child.put(attoNodeRef, childNodeRef);

		}
	}
	
/**
 * Return the list of Relatore for a specific Commissione
 * @param commissioneNodeRef
 * @return
 */
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

	/**
	 * Given a field and a list of values, this method returns the correct syntax for 
	 * a Lucene query in OR on that field.
	 * for example:
	 * (field1,[value1,value2,value3] becomes:
	 * (field1:value1 OR field1:value2 OR field1:value3)
	 * 
	 * @param field - field name
	 * @param valueList - list of field values
	 * @param Apex - boolean to make phrase search or not
	 * @return
	 */
	protected static String convertListToString(String field,
			List<String> valueList, Boolean apex) {
		String apexString = "";
		if (apex)
			apexString = "\"";
		String stringSpaced = valueList.toString().replaceAll(", ", ",");
		String stringReplaced = stringSpaced.replaceAll(",", apexString
				+ " OR " + field.replaceFirst("\\:", "\\\\:") + ":"
				+ apexString);
		String resultQueryString = "(" + field + ":" + apexString
				+ stringReplaced.substring(1, stringReplaced.length() - 1)
				+ apexString + ")";
		return resultQueryString;
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

	protected Map<String, Integer> retrieveLenghtMap(
			Multimap<String, NodeRef> commissione2atti) {
		Map<String, Integer> commissione2count = new HashMap<String, Integer>();
		for (String commissione : commissione2atti.keySet()) {
			int count = commissione2atti.get(commissione).size();
			commissione2count.put(commissione, count);
		}
		return commissione2count;
	}

	protected Map<String, Integer> retrieveLenghtMap(
			Map<String, List<NodeRef>> commissione2atti) {
		Map<String, Integer> commissione2count = new HashMap<String, Integer>();
		for (String commissione : commissione2atti.keySet()) {
			int count = commissione2atti.get(commissione).size();
			commissione2count.put(commissione, count);
		}
		return commissione2count;
	}

	protected Map<String, Integer> retrieveLenghtMapConditional(
			ArrayListMultimap<String, NodeRef> commissione2atti) {
		Map<String, Integer> commissione2count = new HashMap<String, Integer>();
		for (String commissione : commissione2atti.keySet()) {
			int count = 0;
			for (NodeRef currentAtto : commissione2atti.get(commissione)) {
				Map<QName, Serializable> attoProperties = nodeService
						.getProperties(currentAtto);
				String statoAtto = (String) this.getNodeRefProperty(
						attoProperties, "statoAtto");
				if (this.checkStatoAtto(statoAtto)) {
					count++;
				}
			}
			commissione2count.put(commissione, count);

		}
		return commissione2count;
	}

	protected boolean checkStatoAtto(String statoAtto) {
		return true;
	}

	protected int retrieveLenght(List<ResultSet> allSearches) {
		// TODO Auto-generated method stub
		return allSearches.size();
	}

	/**
	 * Return last passaggio
	 * 
	 * @param attoNodeRef
	 * @return
	 */
	protected NodeRef getLastPassaggio(NodeRef attoNodeRef) {

		NodeRef passaggio = null;

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

		String luceneAttoNodePath = nodeService.getPath(attoNodeRef)
				.toPrefixString(namespacePrefixResolver);

		ResultSet passaggiNodes = searchService.query(
				attoNodeRef.getStoreRef(), SearchService.LANGUAGE_LUCENE,
				"PATH:\"" + luceneAttoNodePath + "/cm:Passaggi/*\"");

		int numeroPassaggio = 0;
		String nomePassaggio = "";
		int passaggioMax = 0;
		for (int i = 0; i < passaggiNodes.length(); i++) {

			nomePassaggio = (String) nodeService.getProperty(
					passaggiNodes.getNodeRef(i), ContentModel.PROP_NAME);
			numeroPassaggio = Integer.parseInt(nomePassaggio.substring(9));

			if (numeroPassaggio > passaggioMax) {
				passaggioMax = numeroPassaggio;
				passaggio = passaggiNodes.getNodeRef(i);
			}

		}

		return passaggio;
	}

	/**
	 * Restituisce stringa valorizzata con tutti i nomi degli abbinamenti
	 * separati da virgola
	 * 
	 * @param currentAtto
	 * @return abbinamento1, abbinamento2, abbinamento3, etc..
	 */
	protected String getAbbinamenti(NodeRef currentAtto) {
		String abbinamentiValue = StringUtils.EMPTY;
		NodeRef passaggio = this.getLastPassaggio(currentAtto);
		NodeRef abbinamentiFolderNode = nodeService.getChildByName(passaggio,
				ContentModel.ASSOC_CONTAINS, ABBINAMENTI_SPACE_NAME);
		List<ChildAssociationRef> abbinamenti = nodeService
				.getChildAssocs(abbinamentiFolderNode);
		for (Iterator<ChildAssociationRef> iterator = abbinamenti.iterator(); iterator
				.hasNext();) {
			ChildAssociationRef childAssociationRef = (ChildAssociationRef) iterator
					.next();
			NodeRef abbinamento = childAssociationRef.getChildRef();
			abbinamentiValue += (String) nodeService.getProperty(abbinamento,
					ContentModel.PROP_NAME) + SEP;
		}
		return abbinamentiValue;
	}

	protected String renderList(List<String> stringList) {
		String encodedString = "";
		if (stringList != null)
			for (String singleValue : stringList)
				encodedString += singleValue + ", ";
		if (!encodedString.equals(""))
			encodedString = encodedString.substring(0,
					encodedString.length() - 2);
		return encodedString;
	}

	/**
	 * Return "Si" for True or "No" for False
	 * 
	 * @param booleano
	 * @return
	 */
	protected String processBoolean(String booleano) {
		String result = "";
		if (booleano.equals("true"))
			result = "Si";
		else
			result = "No";
		return result;
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

	protected void initDataSedutaDa(String json) throws JSONException {
		JSONObject rootJson = new JSONObject(json);
		this.dataSedutaDa = JsonUtils.retieveElementFromJson(rootJson,
				"dataSedutaDa");
	}

	protected void initDataSedutaA(String json) throws JSONException {
		JSONObject rootJson = new JSONObject(json);
		this.dataSedutaA = JsonUtils.retieveElementFromJson(rootJson,
				"dataSedutaA");
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
				.retieveElementFromJson(rootJson, "dataAssegnazioneDa");
	}

	protected void initDataAssegnazioneCommReferenteA(String json)
			throws JSONException {
		JSONObject rootJson = new JSONObject(json);
		this.dataAssegnazioneCommReferenteA = JsonUtils.retieveElementFromJson(
				rootJson, "dataAssegnazioneA");
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

	protected void initTipiAttoLuceneAtto(String json) throws JSONException {
		JSONObject rootJson = new JSONObject(json);
		this.tipiAttoLucene = Lists.newArrayList();
		// extract the tipiAtto list from the json string
		List<String> tipiAttoJson = JsonUtils.retieveArrayListFromJson(
				rootJson, "tipiAtto");
		for (String tipoAtto : tipiAttoJson) {
			String caseSensAtto = this.reverseCase(tipoAtto);
			this.tipiAttoLucene.add("crlatti:atto" + caseSensAtto);
		}
	}

	private String reverseCase(String tipoAtto) {
		String lowercase = tipoAtto.toLowerCase();
		String capitalize = StringUtils.capitalize(lowercase);
		return capitalize;
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
