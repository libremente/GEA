package com.sourcesense.crl.webscript.report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.alfresco.util.ISO9075;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.sourcesense.crl.webscript.report.util.JsonUtils;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

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
    public static final String TRASMESSO_AULA = "Trasmesso ad Aula";
    public static final String PRESO_CARICO_AULA = "Preso in carico da Aula";
    public static final String VOTATO_AULA = "Votato in Aula";
    public static final String PUBBLICATO = "Pubblicato";
    public static final String ASSEGNATO_COMMISSIONE = "Assegnato a Commissione";
    public static final String PRESO_CARICO_COMMISSIONE = "Preso in carico da Commissioni";
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
    protected String legislatura;
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
    protected String dataConsultazioneDa;
    protected String dataConsultazioneA;
    protected static Map<String, String> tipoIniziativaDecode = new HashMap<String, String>();

    static {
        tipoIniziativaDecode.put("01_ATTO DI INIZIATIVA CONSILIARE", "Consiliare");
        tipoIniziativaDecode.put("02_ATTO DI INIZIATIVA DI GIUNTA", "Giunta");
        tipoIniziativaDecode.put("03_ATTO DI INIZIATIVA POPOLARE", "Popolare");
        tipoIniziativaDecode.put("04_ATTO DI INIZIATIVA COMMISSIONI", "Commissioni");
        tipoIniziativaDecode.put("05_ATTO DI INIZIATIVA UFFICIO PRESIDENZA", "Ufficio di Presidenza");
        tipoIniziativaDecode.put("06_ATTO DI INIZIATIVA PRESIDENTE GIUNTA", "Presidente della Giunta");
        tipoIniziativaDecode.put("07_ATTO DI INIZIATIVA AUTONOMIE LOCALI", "Consiglio delle Autonomie locali");
        tipoIniziativaDecode.put("08_ATTO DI ALTRA INIZIATIVA", "Altra Iniziativa");
    }

    @Override
    public abstract byte[] generate(byte[] templateByteArray, String json,
            StoreRef spacesStore) throws IOException;

    /**
     * Extract only valuable commissioni from the ones extracted from the
     * Relatori. For each relatore is analyzed its typer hierarchy, and when a
     * Commissione is found, its name is checked.
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
            if (this.checkCommissione(commissione)) {
                commissioni.add(commissioneNodeRef);
            }

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
     * For each Commissione(String) in input , access to its resultSet and
     * retrieve the list of Atto NodeRef.
     *
     * @param attoChild2results - String commissione -> ResultSet
     * @param spacesStore - current space store of search
     * @param atto2child - NodeRef type Atto -> NodeRef type Commissione
     * @return
     */
    protected LinkedListMultimap<String, NodeRef> retrieveAtti(
            Map<String, ResultSet> attoChild2results, StoreRef spacesStore,
            Map<NodeRef, NodeRef> atto2child) {
        LinkedListMultimap<String, NodeRef> child2atti = LinkedListMultimap.create();
        for (String commissione : attoChild2results.keySet()) {
            ResultSet commissioneResults = attoChild2results.get(commissione);
            List<NodeRef> nodeRefList = commissioneResults.getNodeRefs();

            //ORDER LIST
            sortAttiCommissione(nodeRefList);

            this.retrieveAttiFromList(nodeRefList, spacesStore, atto2child,
                    child2atti, commissione);
        }
        return child2atti;
    }

    protected void sortAttiCommissione(List<NodeRef> nodeRefList) {

        Collections.sort(nodeRefList, new Comparator<NodeRef>() {
            public int compare(NodeRef node1, NodeRef node2) {

                String tipoAtto1 = (String) nodeService.getProperty(node1, QName.createQName(CRL_ATTI_MODEL, "tipoAttoCommissione"));
                String tipoAtto2 = (String) nodeService.getProperty(node2, QName.createQName(CRL_ATTI_MODEL, "tipoAttoCommissione"));

                int tipoResult = tipoAtto1.compareTo(tipoAtto2);

                if (tipoResult != 0) {
                    return tipoResult;
                }

                String numeroAtto1 = (String) nodeService.getProperty(node1, QName.createQName(CRL_ATTI_MODEL, "numeroAttoCommissione"));
                String numeroAtto2 = (String) nodeService.getProperty(node2, QName.createQName(CRL_ATTI_MODEL, "numeroAttoCommissione"));

                Integer numAtto1 = new Integer(numeroAtto1);
                Integer numAtto2 = new Integer(numeroAtto2);

                return numAtto1.compareTo(numAtto2);

            }
        });

    }

    protected ArrayListMultimap<String, NodeRef> retrieveConsultazioni(Map<String, ResultSet> attoChild2results, StoreRef spacesStore, Map<NodeRef, NodeRef> atto2child) {
        ArrayListMultimap<String, NodeRef> commissione2consultazioni = ArrayListMultimap.create();
        for (String commissione : attoChild2results.keySet()) {
            ResultSet commissioneResults = attoChild2results.get(commissione);
            List<NodeRef> consultazioniNodeRefList = commissioneResults.getNodeRefs();

            this.retrieveConsultazioniFromList(consultazioniNodeRefList, spacesStore, atto2child, commissione2consultazioni, commissione);
        }
        return commissione2consultazioni;
    }

    protected void retrieveConsultazioniFromList(List<NodeRef> nodeRefList,
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
            child2atti.put(child, childNodeRef);
            atto2child.put(childNodeRef, attoNodeRef);

        }
    }

    /**
     * Retrieve Atto from a NodeRef list of results from the query. For example
     * a list of Commissione or Parere. Then from each child node, it find the
     * related Atto.
     *
     * @param nodeRefList - the list of Commissione NodeRef
     * @param spacesStore - the current space store workspace://SpacesStore
     * @param atto2child - a map that relates each atto to its specif
     * childNodes( for example Commissione NodeRef)
     * @param child2atti - a map that relates a specific child NodeRef
     * @param child - the String of the child related to different Atto ( for
     * example Commissione I)
     */
    protected void retrieveAttiFromList(List<NodeRef> nodeRefList,
            StoreRef spacesStore, Map<NodeRef, NodeRef> atto2child,
            LinkedListMultimap<String, NodeRef> child2atti, String child) {
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
     * Retrieve Atto using an ordered sequence of node child names ( for example
     * Relatore) and then extract from each relatore the related Atto
     *
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
     * Retrieve Atto from a NodeRef list of results from the query. For example
     * a list of Relatore or Parere. Then from each child node, it find the
     * related Atto. TreeMap is used to gain ordering power.
     *
     * @param nodeRefList - the list of Commissione NodeRef
     * @param spacesStore - the current space store workspace://SpacesStore
     * @param atto2child - a map that relates each atto to its specif
     * childNodes( for example Commissione NodeRef)
     * @param child2atti - a map that relates a specific child NodeRef
     * @param child - the String of the child related to different Atto ( for
     * example Commissione I)
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
     *
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

    protected String decodeTipoIniziativa(String value) {

        value = value.trim();
        String tipoIniziativa = tipoIniziativaDecode.get(value);

        if (tipoIniziativa == null || tipoIniziativa.length() < 1) {
            int idx = value.indexOf('_');
            if (idx >= 0) {
                tipoIniziativa = value.substring(idx + 1);
            } else {
                tipoIniziativa = value;
            }
        }

        return tipoIniziativa;
    }

    /**
     * Given a field and a list of values, this method returns the correct
     * syntax for a Lucene query in OR on that field. for example:
     * (field1,[value1,value2,value3] becomes: (field1:value1 OR field1:value2
     * OR field1:value3)
     *
     * @param field - field name
     * @param valueList - list of field values
     * @param Apex - boolean to make phrase search or not
     * @return
     */
    protected static String convertListToString(String field,
            List<String> valueList, Boolean phraseSearch) {
        String apexString = "";
        if (phraseSearch) {
            apexString = "\"";
        }
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
     * Save temporary on the fileSystem the byteArray. In this way is possible
     * to access to the tables in a right and independent manner.
     *
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
     * Convert the TipoAtto list in input in the json in the Alfresco syntax
     *
     * @param tipiAttoJson
     * @return
     */
    protected List<String> convertToLuceneTipiAtto(List<String> tipiAttoJson) {
        List<String> luceneTipiAttoList = new ArrayList<String>();
        for (String jsonType : tipiAttoJson) {
            String luceneValueType = "{" + CRL_ATTI_MODEL + "}" + jsonType;
            luceneTipiAttoList.add(luceneValueType);
        }
        return luceneTipiAttoList;
    }

    /**
     * Return from a map that relate a specific label to a number of results for
     * this label, the total number of results.
     *
     * @param group2atti
     * @return
     */
    protected int retrieveLenght(ArrayListMultimap<String, NodeRef> group2atti) {
        int count = 0;
        for (String group : group2atti.keySet()) {
            count += group2atti.get(group).size();
        }
        return count;
    }

    /**
     * Return a map that relate a specific label to a number of results for this
     * label
     *
     * @param commissione2atti
     * @return
     */
    protected Map<String, Integer> retrieveLenghtMap(
            Multimap<String, NodeRef> commissione2atti) {
        Map<String, Integer> commissione2count = new LinkedHashMap<String, Integer>();
        for (String commissione : commissione2atti.keySet()) {
            int count = commissione2atti.get(commissione).size();
            commissione2count.put(commissione, count);
        }
        return commissione2count;
    }

    /**
     * Return a map that relate a specific label to a number of results for this
     * label
     *
     * @param commissione2atti
     * @return
     */
    protected Map<String, Integer> retrieveLenghtMap(
            Map<String, List<NodeRef>> commissione2atti) {
        Map<String, Integer> commissione2count = new LinkedHashMap<String, Integer>();
        for (String commissione : commissione2atti.keySet()) {
            int count = commissione2atti.get(commissione).size();
            commissione2count.put(commissione, count);
        }
        return commissione2count;
    }

    /**
     * Return a map that relate a specific label to a number of results for this
     * label. Only elements that match the conditional rule are counted.
     *
     * @param commissione2atti
     * @param doubleCheck TODO
     * @return
     */
    protected Map<String, Integer> retrieveLenghtMapConditional(
            LinkedListMultimap<String, NodeRef> commissione2atti, Boolean doubleCheck) {
        Map<String, Integer> commissione2count = new LinkedHashMap<String, Integer>();
        for (String commissione : commissione2atti.keySet()) {
            int count = 0;
            for (NodeRef currentAtto : commissione2atti.get(commissione)) {
                Map<QName, Serializable> attoProperties = nodeService
                        .getProperties(currentAtto);
                String statoAtto = (String) this.getNodeRefProperty(
                        attoProperties, "statoAtto");
                String tipoChiusura = (String) this.getNodeRefProperty(
                        attoProperties, "tipoChiusura");
                if (!doubleCheck) {
                    if (this.checkStatoAtto(statoAtto)) {
                        count++;
                    }
                } else {
                    if (this.checkStatoAtto(statoAtto, tipoChiusura)) {
                        count++;
                    }
                }

            }
            if (count != 0) {
                commissione2count.put(commissione, count);
            }

        }
        return commissione2count;
    }

    /**
     * Check the validity of the Stato Atto. Implementing classes contains the
     * logic.
     *
     * @param statoAtto
     * @return
     */
    protected boolean checkStatoAtto(String statoAtto) {
        return true;
    }

    /**
     * Check the validity of the Stato Atto. Implementing classes contains the
     * logic.
     *
     * @param statoAtto
     * @return
     */
    protected boolean checkStatoAtto(String statoAtto, String tipoChiusura) {
        return true;
    }

    /**
     * Return last passaggio for a Atto NodeRef
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
     * Return the string with the name of all the Abbinamento, comma separed
     *
     * @param currentAtto
     * @return abbinamento1, abbinamento2, abbinamento3, etc..
     */
    protected String getAbbinamenti(NodeRef currentAtto) {
        String abbinamentiValue = StringUtils.EMPTY;
        NodeRef passaggio = this.getLastPassaggio(currentAtto);
        if (passaggio != null) {
            NodeRef abbinamentiFolderNode = nodeService.getChildByName(passaggio, ContentModel.ASSOC_CONTAINS, ABBINAMENTI_SPACE_NAME);
            Set<QName> childType = new HashSet<QName>();
            childType.add(QName.createQName(CRL_ATTI_MODEL, "abbinamento"));
            List<ChildAssociationRef> abbinamenti = nodeService.getChildAssocs(abbinamentiFolderNode, childType);
            List<String> abbinamentiList = new ArrayList<String>();
            for (Iterator<ChildAssociationRef> iterator = abbinamenti.iterator(); iterator.hasNext();) {
                ChildAssociationRef childAssociationRef = (ChildAssociationRef) iterator.next();
                NodeRef abbinamento = childAssociationRef.getChildRef();
                abbinamentiList.add(((String) nodeService.getProperty(abbinamento, ContentModel.PROP_NAME)).toUpperCase());
            }
            abbinamentiValue = renderList(abbinamentiList);
        }
        return abbinamentiValue;
    }

    protected String getSoggettiInvitati(NodeRef consultazione) {
        String soggettiValue = StringUtils.EMPTY;
        NodeRef soggettiInvitatiFolder = nodeService.getChildByName(consultazione, ContentModel.ASSOC_CONTAINS, "SoggettiInvitati");

        if (soggettiInvitatiFolder != null) {

            Set<QName> childType = new HashSet<QName>();
            childType.add(QName.createQName(CRL_ATTI_MODEL, "soggettoInvitato"));
            List<ChildAssociationRef> soggettiInvitati = nodeService.getChildAssocs(soggettiInvitatiFolder, childType);

            List<String> soggettiList = new ArrayList<String>();
            for (Iterator<ChildAssociationRef> iterator = soggettiInvitati.iterator(); iterator.hasNext();) {
                ChildAssociationRef childAssociationRef = (ChildAssociationRef) iterator.next();
                NodeRef soggetto = childAssociationRef.getChildRef();
                soggettiList.add(((String) nodeService.getProperty(soggetto, QName.createQName(CRL_ATTI_MODEL, "descrizioneSoggettoInvitato"))));
            }
            soggettiValue = renderList(soggettiList);
        }

        return soggettiValue;
    }

    /**
     * Encode a list of values in a single String , comma separed
     *
     * @param stringList
     * @return
     */
    protected String renderList(List<String> stringList) {
        String encodedString = "";
        if (stringList != null) {
            for (String singleValue : stringList) {
                encodedString += singleValue + ", ";
            }
        }
        if (!encodedString.equals("")) {
            encodedString = encodedString.substring(0,
                    encodedString.length() - 2);
        }
        return encodedString;
    }

    /**
     * Return "Si" for True or "No" for False. Useful in front end rendering.
     *
     * @param booleano
     * @return
     */
    protected String processBoolean(String booleano) {
        String result = "";
        if (booleano.equals("true")) {
            result = "Si";
        } else {
            result = "No";
        }
        return result;
    }

    /**
     * Init the common params : List<String> tipiAttoLucene; String
     * ruoloCommissioneLuceneField; List<String> commissioniJson;
     *
     * @throws JSONException
     */
    protected void initCommonParams(String json) throws JSONException {
        JSONObject rootJson = new JSONObject(json);
        initTipiAtto(json);
        // extract the ruoloCommissione element from json
        this.ruoloCommissione = JsonUtils.retieveElementFromJson(rootJson,
                "ruoloCommissione");
        // converts the ruoloCommissione to a lucene field
        // extract the commissioni list from json
        this.commissioniJson = JsonUtils.retieveArrayListFromJson(rootJson,
                "commissioni");

    }

    /**
     * Simple init method.
     *
     * @param json
     * @throws JSONException
     */
    protected void initFirmatario(String json) throws JSONException {
        JSONObject rootJson = new JSONObject(json);
        this.firmatario = JsonUtils.retieveElementFromJson(rootJson,
                "firmatario");
    }

    protected String checkEmptyDate(String date) {
        String result = date;
        if (date == null || date.trim().equals("") || date.trim().equals("null")) {
            result = "*";
        }
        return result;

    }

    protected void initDataSedutaDa(String json) throws JSONException {
        JSONObject rootJson = new JSONObject(json);
        this.dataSedutaDa = checkEmptyDate(JsonUtils.retieveElementFromJson(rootJson,
                "dataSedutaDa"));
    }

    protected void initDataSedutaA(String json) throws JSONException {
        JSONObject rootJson = new JSONObject(json);
        this.dataSedutaA = checkEmptyDate(JsonUtils.retieveElementFromJson(rootJson,
                "dataSedutaA"));
    }

    protected void initDataAssegnazioneParereDa(String json)
            throws JSONException {
        JSONObject rootJson = new JSONObject(json);
        this.dataAssegnazioneParereDa = checkEmptyDate(JsonUtils.retieveElementFromJson(
                rootJson, "dataAssegnazioneParereDa"));
    }

    protected void initDataAssegnazioneParereA(String json)
            throws JSONException {
        JSONObject rootJson = new JSONObject(json);
        this.dataAssegnazioneParereA = checkEmptyDate(JsonUtils.retieveElementFromJson(
                rootJson, "dataAssegnazioneParereA"));
    }

    protected void initDataPresentazioneDa(String json) throws JSONException {
        JSONObject rootJson = new JSONObject(json);
        this.dataPresentazioneDa = checkEmptyDate(JsonUtils.retieveElementFromJson(rootJson,
                "dataPresentazioneDa"));
    }

    protected void initDataPresentazioneA(String json) throws JSONException {
        JSONObject rootJson = new JSONObject(json);
        this.dataPresentazioneA = checkEmptyDate(JsonUtils.retieveElementFromJson(rootJson,
                "dataPresentazioneA"));
    }

    protected void initDataNominaRelatoreDa(String json) throws JSONException {
        JSONObject rootJson = new JSONObject(json);
        this.dataNominaRelatoreDa = checkEmptyDate(JsonUtils.retieveElementFromJson(rootJson,
                "dataNominaRelatoreDa"));
    }

    protected void initDataNominaRelatoreA(String json) throws JSONException {
        JSONObject rootJson = new JSONObject(json);
        this.dataNominaRelatoreA = checkEmptyDate(JsonUtils.retieveElementFromJson(rootJson,
                "dataNominaRelatoreA"));
    }

    protected void initDataRitiroDa(String json) throws JSONException {
        JSONObject rootJson = new JSONObject(json);
        this.dataRitiroDa = checkEmptyDate(JsonUtils.retieveElementFromJson(rootJson,
                "dataRitiroDa"));
    }

    protected void initDataRitiroA(String json) throws JSONException {
        JSONObject rootJson = new JSONObject(json);
        this.dataRitiroA = checkEmptyDate(JsonUtils.retieveElementFromJson(rootJson,
                "dataRitiroA"));
    }

    protected void initDataAssegnazioneCommReferenteDa(String json)
            throws JSONException {
        JSONObject rootJson = new JSONObject(json);
        this.dataAssegnazioneCommReferenteDa = checkEmptyDate(JsonUtils
                .retieveElementFromJson(rootJson, "dataAssegnazioneDa"));
    }

    protected void initLegislatura(String json)
            throws JSONException {
        JSONObject rootJson = new JSONObject(json);
        this.legislatura = checkEmptyDate(JsonUtils
                .retieveElementFromJson(rootJson, "legislatura"));
    }

    protected void initDataAssegnazioneCommReferenteA(String json)
            throws JSONException {
        JSONObject rootJson = new JSONObject(json);
        this.dataAssegnazioneCommReferenteA = checkEmptyDate(JsonUtils.retieveElementFromJson(
                rootJson, "dataAssegnazioneA"));
    }

    protected void initDataVotazioneCommReferenteDa(String json)
            throws JSONException {
        JSONObject rootJson = new JSONObject(json);
        this.dataVotazioneCommReferenteDa = checkEmptyDate(JsonUtils.retieveElementFromJson(
                rootJson, "dataVotazioneCommReferenteDa"));
    }

    protected void initDataVotazioneCommReferenteA(String json)
            throws JSONException {
        JSONObject rootJson = new JSONObject(json);
        this.dataVotazioneCommReferenteA = checkEmptyDate(JsonUtils.retieveElementFromJson(
                rootJson, "dataVotazioneCommReferenteA"));
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

    protected void initDataConsultazioneDa(String json) throws JSONException {
        JSONObject rootJson = new JSONObject(json);
        this.dataConsultazioneDa = checkEmptyDate(JsonUtils.retieveElementFromJson(rootJson,
                "dataConsultazioneDa"));
    }

    protected void initDataConsultazioneA(String json) throws JSONException {
        JSONObject rootJson = new JSONObject(json);
        this.dataConsultazioneA = checkEmptyDate(JsonUtils.retieveElementFromJson(rootJson,
                "dataConsultazioneA"));
    }

    /**
     * Init the list of Tipo Atto , extracting values from the json
     *
     * @param rootJson
     * @throws JSONException
     */
    protected void initTipiAtto(String json) throws JSONException {
        JSONObject rootJson = new JSONObject(json);
        // extract the tipiAtto list from the json string
        List<String> tipiAttoJson = JsonUtils.retieveArrayListFromJson(
                rootJson, "tipiAtto");
        // convert the list in the lucene format
        this.tipiAttoLucene = tipiAttoJson;
    }

    /**
     * Init the list of Tipo Atto, extracting the list of Tipo Atto from the
     * json,inverting case and adding a specific prefix
     *
     * @param json
     * @throws JSONException
     */
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

    /**
     * Reverse the case of the String in input and capitalizes it
     *
     * @param tipoAtto
     * @return
     */
    private String reverseCase(String tipoAtto) {
        String lowercase = tipoAtto.toLowerCase();
        String capitalize = StringUtils.capitalize(lowercase);
        return capitalize;
    }

    /**
     * Check for null dates and format the result
     *
     * @param attributeDate
     * @return
     */
    protected String checkDateEmpty(Date attributeDate) {
        if (attributeDate == null) {
            return "";
        } else {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(DATA_FORMAT);
            return dateFormatter.format(attributeDate);
        }

    }

    /**
     * Check for null text element
     *
     * @param attribute
     * @return
     */
    protected String checkStringEmpty(String attribute) {
        if (attribute == null) {
            return "";
        } else {
            return attribute;
        }

    }

    /**
     * returna specific attribute value in a properties map
     *
     * @param properties
     * @param attribute
     * @return
     */
    protected Serializable getNodeRefProperty(
            Map<QName, Serializable> properties, String attribute) {
        return properties.get(QName.createQName(CRL_ATTI_MODEL, attribute));
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
