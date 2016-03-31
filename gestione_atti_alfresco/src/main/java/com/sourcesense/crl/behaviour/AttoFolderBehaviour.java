package com.sourcesense.crl.behaviour;

import java.io.StringWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.node.NodeServicePolicies;
import org.alfresco.repo.policy.JavaBehaviour;
import org.alfresco.repo.policy.PolicyComponent;
import org.alfresco.repo.policy.Behaviour.NotificationFrequency;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.model.FileInfo;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.alfresco.web.bean.repository.Repository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class AttoFolderBehaviour implements NodeServicePolicies.BeforeDeleteNodePolicy {

    /* Aspect names */
    public static final QName ATTI_INDIRIZZO_ASPECT = QName.createQName("http://www.regione.lombardia.it/content/model/atti/1.0", "attiIndirizzoAspect");
    private static Log logger = LogFactory.getLog(AttoFolderBehaviour.class);
    private PolicyComponent policyComponent;
    private ContentService contentService;
    private FileFolderService fileFolderService;
    private NodeService nodeService;
    private SearchService searchService;
    private NamespaceService namespaceService;

    public void setPolicyComponent(PolicyComponent policyComponent) {
        this.policyComponent = policyComponent;
    }

    public void setNodeService(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    public void setContentService(ContentService contentService) {
        this.contentService = contentService;
    }

    public void setFileFolderService(FileFolderService fileFolderService) {
        this.fileFolderService = fileFolderService;
    }

    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    public void setNamespaceService(NamespaceService namespaceService) {
        this.namespaceService = namespaceService;
    }

    public void init() {

        this.policyComponent.bindClassBehaviour(NodeServicePolicies.BeforeDeleteNodePolicy.QNAME,
                ATTI_INDIRIZZO_ASPECT,
                new JavaBehaviour(this, "beforeDeleteNode", NotificationFrequency.EVERY_EVENT));
    }

    public void beforeDeleteNode(NodeRef nodeRef) {


        String CRL_ATTI_MODEL = "http://www.regione.lombardia.it/content/model/atti/1.0";
        String TYPE_LEGISLATURA = "legislaturaAnagrafica";

        String PROP_OGGETTO_ATTO = "oggetto";
        String PROP_LEGISLATURA = "legislatura";
        String PROP_ID_ANAGRAFICA = "idAnagrafica";


        String path = nodeService.getPath(nodeRef).toPrefixString(namespaceService);

        if (path == null || (!path.startsWith("/app:company_home/cm:CRL/cm:Gestione_x0020_Atti"))) {
            return;
        }

        String numeroAtto = (String) nodeService.getProperty(nodeRef, ContentModel.PROP_NAME);
        String idAtto = (String) nodeService.getProperty(nodeRef, ContentModel.PROP_NODE_UUID);

        // uuid
        //String idAtto = numeroAtto;

        // id legislatura
        String numeroLegislatura = (String) nodeService.getProperty(nodeRef, QName.createQName(CRL_ATTI_MODEL, PROP_LEGISLATURA));

        // get id della legislatura dell'atto
        NodeRef legislatura = null;
        String idLegislatura = "";

        String legislaturaType = "{" + CRL_ATTI_MODEL + "}" + TYPE_LEGISLATURA;

        StoreRef storeRef = new StoreRef("workspace://SpacesStore");

        // Get legislature
        ResultSet legislatureNodes=null;
        try{
        legislatureNodes = searchService.query(storeRef,
                SearchService.LANGUAGE_LUCENE, "PATH:\"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti/cm:Anagrafica/cm:Legislature/cm:" + numeroLegislatura + "\"");

        if (legislatureNodes.length() > 0) {
            legislatura = legislatureNodes.getNodeRef(0);
            idLegislatura = (String) nodeService.getProperty(legislatura, QName.createQName(CRL_ATTI_MODEL, PROP_ID_ANAGRAFICA)).toString();
        }
        }finally{
        	if (legislatureNodes!=null){
        		legislatureNodes.close();
        	}
        }


        String tipoAtto = nodeService.getType(nodeRef).getLocalName().substring(4).toUpperCase();
        String oggettoAtto = (String) nodeService.getProperty(nodeRef, QName.createQName(CRL_ATTI_MODEL, PROP_OGGETTO_ATTO));


        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        
        
        ResultSet exportFolderNodeResultSet=null;
        try {

            docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("atto");
            doc.appendChild(rootElement);
            doc.getInputEncoding();

            Attr attr1 = doc.createAttribute("id_atto");
            attr1.setValue(idAtto);
            rootElement.setAttributeNode(attr1);

            Attr attr2 = doc.createAttribute("id_legislatura");
            attr2.setValue(idLegislatura);
            rootElement.setAttributeNode(attr2);

            Attr attr3 = doc.createAttribute("tipo_atto");
            attr3.setValue(tipoAtto);
            rootElement.setAttributeNode(attr3);

            Attr attr4 = doc.createAttribute("numero_atto");
            attr4.setValue(numeroAtto);
            rootElement.setAttributeNode(attr4);

            Attr attr5 = doc.createAttribute("oggetto_atto");
            attr5.setValue(oggettoAtto);
            rootElement.setAttributeNode(attr5);

            Attr attr6 = doc.createAttribute("operazione");
            attr6.setValue("DELETE");
            rootElement.setAttributeNode(attr6);

            StringWriter output = new StringWriter();

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.transform(new DOMSource(doc), new StreamResult(output));
            
            String xmlString = output.toString();

            Calendar calendar = new GregorianCalendar();

            int anno = calendar.get(Calendar.YEAR);
            int mese = calendar.get(Calendar.MONTH) + 1;
            int giorno = calendar.get(Calendar.DAY_OF_MONTH);
            int ora = calendar.get(Calendar.HOUR_OF_DAY);
            int minuto = calendar.get(Calendar.MINUTE);
            int secondo = calendar.get(Calendar.SECOND);


            String nomeFileExport = "export_atto_" + numeroAtto + "_" + anno + "-" + mese + "-" + giorno + "_" + ora + "-" + minuto + "-" + secondo + ".xml";

            exportFolderNodeResultSet = searchService.query(Repository.getStoreRef(),
                    SearchService.LANGUAGE_LUCENE, "PATH:\"/app:company_home/cm:Export/cm:Gestione_x0020_Atti/cm:AttiIndirizzo\"");

            FileInfo xmlFileInfo = fileFolderService.create(exportFolderNodeResultSet.getNodeRef(0), nomeFileExport, ContentModel.TYPE_CONTENT);

            ContentWriter contentWriter = contentService.getWriter(xmlFileInfo.getNodeRef(), ContentModel.PROP_CONTENT, true);
            contentWriter.setMimetype("text/plain");
            contentWriter.setEncoding("UTF-8");
            contentWriter.putContent(xmlString);

            logger.info("Creato file export " + nomeFileExport + ": operazione DELETE su atto " + tipoAtto + " " + numeroAtto);



        } catch (Exception e) {
            logger.info("Errore nella creazione del file xml di export: operazione DELETE su atto " + tipoAtto + " " + numeroAtto);
        } finally{
        	if (exportFolderNodeResultSet!=null){
        		exportFolderNodeResultSet.close();
        	}
        }



    }
}
