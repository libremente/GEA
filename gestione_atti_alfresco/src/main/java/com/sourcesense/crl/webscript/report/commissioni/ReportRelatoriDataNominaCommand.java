package com.sourcesense.crl.webscript.report.commissioni;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchParameters;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.QName;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.json.JSONException;

import com.google.common.collect.Maps;
import com.google.common.collect.TreeMultimap;
import com.sourcesense.crl.webscript.report.ReportBaseCommand;
import com.sourcesense.crl.webscript.report.model.Atto;
import com.sourcesense.crl.webscript.report.util.office.DocxManager;

/**
 * V2 - Big Ok
 *
 * @author Alessandro Benedetti
 *
 */
public class ReportRelatoriDataNominaCommand extends ReportBaseCommand {

    @Override
    public byte[] generate(byte[] templateByteArray, String json,
            StoreRef spacesStore) throws IOException {
        ByteArrayOutputStream ostream = null;
        try {
            ByteArrayInputStream is = new ByteArrayInputStream(
                    templateByteArray);
            DocxManager docxManager = new DocxManager(is);
            this.initLegislatura(json);
            this.initCommonParams(json);
            this.initRelatori(json);
            this.initDataNominaRelatoreDa(json);
            this.initDataNominaRelatoreA(json);


            Map<String, ResultSet> relatore2results = Maps.newHashMap();
            /* execute guery grouped by Relatore */
            for (String relatore : this.relatoriJson) {
                SearchParameters sp = new SearchParameters();
                sp.addStore(spacesStore);
                sp.setLanguage(SearchService.LANGUAGE_FTS_ALFRESCO);
                String query = "PATH: \"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti/cm:" + this.legislatura + "//*\""
                        + " AND TYPE:\""
                        + "crlatti:relatore\" AND "
                        + convertListToString("@crlatti\\:tipoAttoRelatore",
                        this.tipiAttoLucene, true)
                        + " AND "
                        + convertListToString("@crlatti\\:commissioneRelatore",
                        this.commissioniJson, false)
                        + " AND =@cm\\:name:\"" + relatore + "\"";

                if (!dataNominaRelatoreDa.equals("*")
                        || !dataNominaRelatoreA.equals("*")) {
                    query += " AND @crlatti\\:dataNominaRelatore:["
                            + this.dataNominaRelatoreDa + " TO "
                            + this.dataNominaRelatoreA + " ]";
                }
                sp.setQuery(query);
                ResultSet currentResults = this.searchService.query(sp);
                relatore2results.put(relatore, currentResults);
            }

            Map<NodeRef, NodeRef> atto2relatore = new HashMap<NodeRef, NodeRef>();
            TreeMap<String, List<NodeRef>> relatore2atti = this
                    .retrieveAttiOrdered(relatore2results, spacesStore,
                    atto2relatore);
            TreeMultimap<String, Atto> commissione2atti = this.initCommissione2AttiMap(relatore2atti, atto2relatore);

            // obtain as much table as the results spreaded across the resultSet
            XWPFDocument generatedDocument = docxManager
                    .generateFromTemplateMap(
                    this.retrieveLenghtMap(commissione2atti), 4, false);
            // convert to input stream
            ByteArrayInputStream tempInputStream = saveTemp(generatedDocument);

            XWPFDocument finalDocument = this.fillTemplate(tempInputStream,
                    commissione2atti);
            ostream = new ByteArrayOutputStream();
            finalDocument.write(ostream);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ostream.toByteArray();

    }

    private Map<String, Integer> retrieveLenghtMap(
            TreeMultimap<String, Atto> commissione2atti) {
        Map<String, Integer> commissione2length = Maps.newTreeMap();
        for (String commissione : commissione2atti.keySet()) {
            SortedSet<Atto> sortedAtto = commissione2atti.get(commissione);
            commissione2length.put(commissione, sortedAtto.size());
        }
        return commissione2length;
    }

    private TreeMultimap<String, Atto> initCommissione2AttiMap(
            TreeMap<String, List<NodeRef>> relatore2atti, Map<NodeRef, NodeRef> atto2relatore) {
        TreeMultimap<String, Atto> commissione2Atti = TreeMultimap.create();
        for (String relatore : relatore2atti.keySet()) {
            for (NodeRef attoNode : relatore2atti.get(relatore)) {
                Atto currentAtto = new Atto();
                Map<QName, Serializable> attoProperties = nodeService
                        .getProperties(attoNode);
                Map<QName, Serializable> relatoreProperties = nodeService
                        .getProperties(atto2relatore.get(attoNode));
                QName nodeRefType = nodeService.getType(attoNode);
                String tipoAtto = (String) nodeRefType.getLocalName();
                Date dataNomina = (Date) this
                        .getNodeRefProperty(relatoreProperties,
                        "dataNominaRelatore");
                String commissione = (String) this.getNodeRefProperty(
                        relatoreProperties, "commissioneRelatore");
                String numeroAtto = ""
                        + (Integer) this.getNodeRefProperty(attoProperties,
                        "numeroAtto");
                currentAtto.setAttoNodeRef(attoNode);
                currentAtto.setConsigliere(relatore);
                currentAtto.setDataNomina(dataNomina);
                currentAtto.setNumeroAtto(numeroAtto);
                currentAtto.setTipoAtto(tipoAtto);
                commissione2Atti.put(commissione, currentAtto);
            }
        }

        return commissione2Atti;

    }

    /**
     *
     *
     * @param finalDocStream
     * @param atto2relatore
     * @param queryRes
     * @return
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public XWPFDocument fillTemplate(ByteArrayInputStream finalDocStream,
            TreeMultimap<String, Atto> commissione2atti) throws IOException {
        XWPFDocument document = new XWPFDocument(finalDocStream);
        int tableIndex = 0;
        List<XWPFTable> tables = document.getTables();
        for (String commissione : commissione2atti.keySet()) {
            for (Atto currentAtto : commissione2atti.get(commissione)) {
                XWPFTable currentTable = tables.get(tableIndex);
                Map<QName, Serializable> attoProperties = nodeService
                        .getProperties(currentAtto.getAttoNodeRef());
                String tipoAtto = currentAtto.getTipoAtto();
                if (tipoAtto.length() > 4) {
                    tipoAtto = tipoAtto.substring(4);
                }
                Date dataNomina = currentAtto.getDataNomina();
                String numeroAtto = currentAtto.getNumeroAtto();
                String oggetto = (String) this.getNodeRefProperty(
                        attoProperties, "oggetto");
                String consigliere = currentAtto.getConsigliere();

                ArrayList<String> commReferenteList = (ArrayList<String>) this
                        .getNodeRefProperty(attoProperties, "commReferente");
                String commReferente = "";
                for (String commissioneReferenteMulti : commReferenteList) {
                    commReferente += commissioneReferenteMulti + " ";
                }

                ArrayList<String> commConsultivaList = (ArrayList<String>) this
                        .getNodeRefProperty(attoProperties, "commConsultiva");
                String commConsultiva = this.renderList(commConsultivaList);


                currentTable
                        .getRow(0)
                        .getCell(0)
                        .setText(
                        this.checkStringEmpty(consigliere));
                currentTable.getRow(0).getCell(1)
                        .setText("Nominato il " + this.checkDateEmpty(dataNomina) + " - " + this.checkStringEmpty(tipoAtto.toUpperCase() + " "
                        + numeroAtto + " - " + oggetto));
                currentTable.getRow(1).getCell(2)
                        .setText(this.checkStringEmpty(commReferente));
                currentTable.getRow(2).getCell(2)
                        .setText(this.checkStringEmpty(commConsultiva));

                tableIndex++;
            }
        }

        return document;
    }
}
