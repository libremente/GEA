package com.sourcesense.crl.webscript.report.commissioni;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchParameters;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.QName;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.json.JSONException;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.sourcesense.crl.webscript.report.ReportBaseCommand;
import com.sourcesense.crl.webscript.report.util.office.DocxManager;
import java.util.LinkedHashMap;
import org.alfresco.model.ContentModel;

/**
 * V2 - Big Ok
 *
 * @author Alessandro Benedetti
 *
 */
public class ReportAudizioniCommissioniCommand extends ReportBaseCommand {

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
            this.initDataConsultazioneDa(json);
            this.initDataConsultazioneA(json);
            String sortField1 = "@{" + CRL_ATTI_MODEL + "}tipoAttoCommissione";
            String sortField2 = "@{" + CRL_ATTI_MODEL + "}numeroAttoCommissione";

            Map<String, ResultSet> commissione2results = Maps.newLinkedHashMap();

            for (String commissione : this.commissioniJson) {
                SearchParameters sp = new SearchParameters();
                sp.addStore(spacesStore);
                sp.setLanguage(SearchService.LANGUAGE_LUCENE);
                String query = "PATH: \"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti/cm:" + this.legislatura + "//*\""
                        + " AND TYPE:\""
                        + "crlatti:consultazione"
                        + "\" AND @crlatti\\:commissioneConsultazione:\""
                        + commissione + "\"";
                if (!dataConsultazioneDa.equals("*")
                        || !dataConsultazioneA.equals("*")) {
                    query += " AND @crlatti\\:dataConsultazione:["
                            + this.dataConsultazioneDa + " TO "
                            + this.dataConsultazioneA + " ]";
                }
                sp.setQuery(query);
//                sp.addSort(sortField1, true);
//                sp.addSort(sortField2, true);
                ResultSet currentResults = this.searchService.query(sp);
                commissione2results.put(commissione, currentResults);
            }

            Map<NodeRef, NodeRef> atto2commissione = new LinkedHashMap<NodeRef, NodeRef>();
            ArrayListMultimap<String, NodeRef> commissione2atti = this.retrieveConsultazioni(commissione2results, spacesStore, atto2commissione);

            // obtain as much table as the results spreaded across the resultSet
            XWPFDocument generatedDocument = docxManager.generateFromTemplateMap(this.retrieveLenghtMap(commissione2atti), 5, false);
            // convert to input stream
            ByteArrayInputStream tempInputStream = saveTemp(generatedDocument);

            XWPFDocument finalDocument = this.fillTemplate(tempInputStream, commissione2atti, atto2commissione);
            ostream = new ByteArrayOutputStream();
            finalDocument.write(ostream);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ostream.toByteArray();

    }

    /**
     * fills the docx template,correctly replicated with the values extracted
     * from the NodeRef in input (AttoNodeRef- CommissioneNodeRef)
     *
     * @param finalDocStream - docx stream
     * @param commissione2atti - String commissione -> list NodeRef type Atto
     * @param atto2commissione - NodeRef type Atto -> NodeRef type Commissione
     * @return
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public XWPFDocument fillTemplate(ByteArrayInputStream finalDocStream,
            ArrayListMultimap<String, NodeRef> commissione2consultazioni,
            Map<NodeRef, NodeRef> consultazione2atto) throws IOException {
        XWPFDocument document = new XWPFDocument(finalDocStream);
        int tableIndex = 0;
        List<XWPFTable> tables = document.getTables();
        for (String commissione : commissione2consultazioni.keySet()) {
            for (NodeRef consultazioniNode : commissione2consultazioni.get(commissione)) {
                XWPFTable currentTable = tables.get(tableIndex);
                NodeRef currentAtto = consultazione2atto.get(consultazioniNode);
                Map<QName, Serializable> attoProperties = nodeService.getProperties(currentAtto);
                Map<QName, Serializable> consultazioneProperties = nodeService.getProperties(consultazioniNode);

                // from Atto
                QName nodeRefType = nodeService.getType(currentAtto);
                String tipoAtto = (String) nodeRefType.getLocalName();
                if (tipoAtto.length() > 4) {
                    tipoAtto = tipoAtto.substring(4);
                }
                String numeroAtto = "" + (Integer) this.getNodeRefProperty(attoProperties, "numeroAtto");

                String oggetto = (String) this.getNodeRefProperty(attoProperties, "oggetto");

                // from Consultazione
                Date dataConsultazione = (Date) this.getNodeRefProperty(consultazioneProperties, "dataConsultazione");

                //String soggettiInvitati = getSoggettiInvitati(consultazioniNode);
                String soggettiInvitati = (String) consultazioneProperties.get(ContentModel.PROP_NAME);

                StringBuffer sb = new StringBuffer();

                sb.append("Audizione in merito al ");
                sb.append(tipoAtto.toUpperCase());
                sb.append(" ");
                sb.append(numeroAtto);
                sb.append(" (");
                sb.append(oggetto); //oggetto
                sb.append("). Con: ");
                sb.append(soggettiInvitati); //soggetti consultanti
                sb.append(".");

                currentTable.getRow(0).getCell(0).setText(this.checkDateEmpty(dataConsultazione));
                currentTable.getRow(0).getCell(1).setText(this.checkStringEmpty(sb.toString()));

                tableIndex++;
            }
        }

        return document;
    }
}
