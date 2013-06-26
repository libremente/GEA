package com.sourcesense.crl.webscript.report.commissioni;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchParameters;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.QName;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.json.JSONException;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sourcesense.crl.webscript.report.ReportBaseCommand;
import com.sourcesense.crl.webscript.report.util.office.DocxManager;
import java.util.LinkedHashMap;

/**
 * V2
 *
 * @author Alessandro Benedetti
 *
 */
public class ReportAttiIstruttoriaCommissioniCommand extends ReportBaseCommand {

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
            this.initDataAssegnazioneCommReferenteDa(json);
            this.initDataAssegnazioneCommReferenteA(json);
            String sortField1 = "@{" + CRL_ATTI_MODEL + "}tipoAttoCommissione";
            String sortField2 = "@{" + CRL_ATTI_MODEL
                    + "}numeroAttoCommissione";
            Map<String, ResultSet> commissione2results = Maps.newLinkedHashMap();
            for (String commissione : this.commissioniJson) {
                SearchParameters sp = new SearchParameters();
                sp.addStore(spacesStore);
                sp.setLanguage(SearchService.LANGUAGE_LUCENE);
                // solo atti da preso in carico a votato dalla commissione
                String query = "PATH: \"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti/cm:" + this.legislatura + "//*\""
                        + " AND TYPE:\""
                        + "crlatti:commissione"
                        + "\" AND "
                        + convertListToString("@crlatti\\:tipoAttoCommissione",
                        this.tipiAttoLucene, true)
                        + " AND @crlatti\\:ruoloCommissione:\""
                        + this.ruoloCommissione
                        + "\" AND @cm\\:name:\""
                        + commissione + "\"";
                if (!dataAssegnazioneCommReferenteDa.equals("*")
                        || !dataAssegnazioneCommReferenteA.equals("*")) {
                    query += " AND @crlatti\\:dataAssegnazioneCommissione:["
                            + this.dataAssegnazioneCommReferenteDa + " TO "
                            + this.dataAssegnazioneCommReferenteA + " ]";
                }
                sp.setQuery(query);
                sp.addSort(sortField1, true);
                sp.addSort(sortField2, true);
                ResultSet currentResults = this.searchService.query(sp);
                commissione2results.put(commissione, currentResults);
            }
            Map<NodeRef, NodeRef> atto2commissione = new LinkedHashMap<NodeRef, NodeRef>();
            LinkedListMultimap<String, NodeRef> commissione2atti = this
                    .retrieveAtti(commissione2results, spacesStore,
                    atto2commissione);

            // obtain as much table as the results spreaded across the resultSet
            XWPFDocument generatedDocument = docxManager
                    .generateFromTemplateMap(
                    this.retrieveLenghtMapConditional(commissione2atti, false),
                    2, false);
            // convert to input stream
            ByteArrayInputStream tempInputStream = saveTemp(generatedDocument);

            XWPFDocument finalDocument = this.fillTemplate(tempInputStream,
                    commissione2atti, atto2commissione, docxManager);
            ostream = new ByteArrayOutputStream();
            finalDocument.write(ostream);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ostream.toByteArray();

    }

    /**
     * qui vanno inseriti nella table, presa dal template solo 6: tipo atto-
     * numero atto- oggetto - iniziativa -firmatari- data assegnazione -
     *
     *
     *
     * @param finalDocStream
     * @param docxManager
     * @param queryRes
     * @return
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public XWPFDocument fillTemplate(ByteArrayInputStream finalDocStream,
            LinkedListMultimap<String, NodeRef> commissione2atti,
            Map<NodeRef, NodeRef> atto2commissione, DocxManager docxManager)
            throws IOException {
        XWPFDocument document = new XWPFDocument(finalDocStream);
        int tableIndex = 0;
        List<XWPFTable> tables = document.getTables();
        for (String commissione : commissione2atti.keySet()) {
            for (NodeRef currentAtto : commissione2atti.get(commissione)) {
                NodeRef currentCommissione = atto2commissione.get(currentAtto);
                ResultSet relatori = this.getRelatori(currentCommissione);
                Map<QName, Serializable> attoProperties = nodeService
                        .getProperties(currentAtto);
                Map<QName, Serializable> commissioneProperties = nodeService
                        .getProperties(currentCommissione);

                // from Atto
                String statoAtto = (String) this.getNodeRefProperty(
                        attoProperties, "statoAtto");
                if (this.checkStatoAtto(statoAtto)) {
                    XWPFTable currentTable = tables.get(tableIndex);
                    String numeroAtto = ""
                            + (Integer) this.getNodeRefProperty(attoProperties,
                            "numeroAtto");
                    String iniziativa = (String) this.getNodeRefProperty(
                            attoProperties, "tipoIniziativa");
                    String oggetto = (String) this.getNodeRefProperty(
                            attoProperties, "oggetto");
                    // from Commissione
                    String tipoAtto = (String) this.getNodeRefProperty(
                            commissioneProperties, "tipoAttoCommissione");
                    Date dateAssegnazioneCommissione = (Date) this
                            .getNodeRefProperty(commissioneProperties,
                            "dataAssegnazioneCommissione");
                    List<String> elencoRelatori = Lists.newArrayList();
                    List<String> elencoDateNomina = Lists.newArrayList();
                    for (int i = 0; i < relatori.length(); i++) {
                        NodeRef relatoreNodeRef = relatori.getNodeRef(i);
                        Map<QName, Serializable> relatoreProperties = nodeService
                                .getProperties(relatoreNodeRef);
                        Date dateNomina = (Date) this.getNodeRefProperty(
                                relatoreProperties, "dataNominaRelatore");
                        String relatore = (String) nodeService.getProperty(
                                relatoreNodeRef, ContentModel.PROP_NAME);
                        String dateNominaString = this
                                .checkDateEmpty(dateNomina);
                        elencoRelatori.add(relatore);
                        elencoDateNomina.add(dateNominaString);
                    }

                    ArrayList<String> commReferenteList = (ArrayList<String>) this
                            .getNodeRefProperty(attoProperties, "commReferente");
                    String commReferente = "";
                    if (commReferenteList != null) {
                        for (String commissioneReferenteMulti : commReferenteList) {
                            commReferente += commissioneReferenteMulti + " ";
                        }
                    }

                    ArrayList<String> commConsultivaList = (ArrayList<String>) this
                            .getNodeRefProperty(attoProperties,
                            "commConsultiva");
                    String commConsultiva = this.renderList(commConsultivaList);

                    currentTable.getRow(0).getCell(1)
                            .setText(this.checkStringEmpty(tipoAtto.toUpperCase()));
                    currentTable.getRow(0).getCell(2)
                            .setText(this.checkStringEmpty(numeroAtto));
                    currentTable.getRow(1).getCell(1)
                            .setText(this.checkStringEmpty(oggetto));
                    currentTable.getRow(2).getCell(1)
                            .setText(this.checkStringEmpty(decodeTipoIniziativa(iniziativa)));
                    currentTable
                            .getRow(3)
                            .getCell(1)
                            .setText(
                            this.checkDateEmpty(dateAssegnazioneCommissione));

                    currentTable.getRow(3).getCell(3)
                            .setText(this.checkStringEmpty(commReferente));
                    currentTable.getRow(4).getCell(1)
                            .setText(this.checkStringEmpty(commConsultiva));
                    docxManager.insertListInCell(currentTable.getRow(5)
                            .getCell(1), elencoRelatori);
                    docxManager.insertListInCell(currentTable.getRow(5)
                            .getCell(3), elencoDateNomina);
                    tableIndex++;
                }
            }
        }

        return document;
    }

    /**
     * Check if the statoAtto is comprehended between "preso in carico e votato"
     *
     * @param statoAtto
     * @return
     */
    protected boolean checkStatoAtto(String statoAtto) {
        return statoAtto.equals(PRESO_CARICO_COMMISSIONE)
                || statoAtto.equals(VOTATO_COMMISSIONE)
                || statoAtto.equals(NOMINATO_RELATORE)
                || statoAtto.equals(LAVORI_COMITATO_RISTRETTO);
    }
}
