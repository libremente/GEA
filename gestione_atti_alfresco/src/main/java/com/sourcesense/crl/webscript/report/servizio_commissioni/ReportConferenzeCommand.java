package com.sourcesense.crl.webscript.report.servizio_commissioni;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchParameters;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.json.JSONException;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Maps;
import com.sourcesense.crl.util.AttoUtil;
import com.sourcesense.crl.webscript.report.ReportBaseCommand;
import com.sourcesense.crl.webscript.report.util.office.DocxManager;

/**
 * V2 - Big Ok
 *
 * @author Alessandro Benedetti
 *
 */
public class ReportConferenzeCommand extends ReportBaseCommand {

    @Override
    public byte[] generate(byte[] templateByteArray, String json,
            StoreRef spacesStore) throws IOException {
        ByteArrayOutputStream ostream = null;
        try {
            ByteArrayInputStream is = new ByteArrayInputStream(
                    templateByteArray);
            DocxManager docxManager = new DocxManager(is);
            docxManager.setNodeService(nodeService);
            docxManager.setSearchService(searchService);
            
            /* init json params */
            this.initLegislatura(json);
            this.initCommonParams(json);
            this.initDataAssegnazioneCommReferenteDa(json);
            this.initDataAssegnazioneCommReferenteA(json);
            /* sorting fields */
            String sortField1 = "@{" + CRL_ATTI_MODEL + "}tipoAttoCommissione";
            String sortField2 = "@{" + CRL_ATTI_MODEL + "}numeroAttoCommissione";
            /* query grouped by commissione */
            Map<String, ResultSet> commissione2results = Maps.newLinkedHashMap();
            for (String commissione : this.commissioniJson) {
                SearchParameters sp = new SearchParameters();
                sp.addStore(spacesStore);
                sp.setLanguage(SearchService.LANGUAGE_LUCENE);
                String query = "PATH: \"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti/cm:" + this.legislatura + "//*\""
                        + " AND TYPE:\""
                        + "crlatti:commissione"
                        + "\" AND NOT @crlatti\\:statoCommissione:\"Annullato\" AND "
                        + convertListToString("@crlatti\\:tipoAttoCommissione",
                        this.tipiAttoLucene, true)
                        + " AND @crlatti\\:ruoloCommissione:\""
                        + this.ruoloCommissione
                        + "\" AND @cm\\:name:\""
                        + commissione   
                        + "\"";
                if (!dataAssegnazioneCommReferenteDa.equals("*")
                        || !dataAssegnazioneCommReferenteA.equals("*")) {
                    query += " AND @crlatti\\:dataAssegnazioneCommissione:["
                            + this.dataAssegnazioneCommReferenteDa + " TO "
                            + this.dataAssegnazioneCommReferenteA + " ]";
                }
                sp.setQuery(query);
                
                
                ///// MODIFICATO IN TRUE
                
                sp.addSort(sortField1, true);
                sp.addSort(sortField2, true);
                ResultSet currentResults = this.searchService.query(sp);
                commissione2results.put(commissione, currentResults);
            }
            Map<NodeRef, NodeRef> atto2commissione = new LinkedHashMap<NodeRef, NodeRef>();
            LinkedListMultimap<String, NodeRef> commissione2atti = this.retrieveAtti(commissione2results, spacesStore, atto2commissione);

            // obtain as much table as the results spreaded across the resultSet
            Map<String, Integer> group2count = this.retrieveLenghtMapConditional(commissione2atti, false);
            
            XWPFDocument generatedDocument = docxManager.generateFromTemplateMapConferenza2(group2count, commissione2atti, 3, atto2commissione);
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
     * @param finalDocStream
     * @param queryRes
     * @return
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public XWPFDocument fillTemplate(ByteArrayInputStream finalDocStream,
            LinkedListMultimap<String, NodeRef> commissione2atti,
            Map<NodeRef, NodeRef> atto2commissione) throws IOException {
        XWPFDocument document = new XWPFDocument(finalDocStream);
        int tableIndex = 0;
        List<XWPFTable> tables = document.getTables();
        for (String commissione : commissione2atti.keySet()) {
            for (NodeRef currentAtto : commissione2atti.get(commissione)) {

                Map<QName, Serializable> attoProperties = nodeService.getProperties(currentAtto);
                Map<QName, Serializable> commissioneProperties = nodeService.getProperties(atto2commissione.get(currentAtto));
                /* extracting values from Alfresco */
                // from Atto
                String statoAtto = (String) this.getNodeRefProperty(attoProperties, "statoAtto");
                if (this.checkStatoAtto(statoAtto)) {
                    XWPFTable currentTable = tables.get(tableIndex);
                    String numeroAtto = StringUtils.EMPTY + (Integer) this.getNodeRefProperty(attoProperties, "numeroAtto");
                    String iniziativa = (String) this.getNodeRefProperty(attoProperties, "tipoIniziativa");
                    String oggetto = (String) this.getNodeRefProperty(attoProperties, "oggetto");
                    // from Commissione
                    String tipoAtto = (String) this.getNodeRefProperty(commissioneProperties, "tipoAttoCommissione");
                    Date dateAssegnazioneCommissione = (Date) this.getNodeRefProperty(commissioneProperties, "dataAssegnazioneCommissione");
                    // child of Atto
                    ArrayList<String> firmatariList = (ArrayList<String>) this.getNodeRefProperty(attoProperties, "firmatari");
                    String firmatari = this.renderFirmatariConGruppoList(firmatariList);
                    
                    
                    /* writing values in the table */
                    currentTable.getRow(0).getCell(1).setText(this.checkStringEmpty(tipoAtto.toUpperCase() + " " + numeroAtto));
                    currentTable.getRow(1).getCell(1).setText(this.checkStringEmpty(oggetto));
                    currentTable.getRow(2).getCell(1).setText(this.checkStringEmpty(decodeTipoIniziativa(iniziativa)));
                    currentTable.getRow(3).getCell(1).setText(this.checkStringEmpty(firmatari));
                    currentTable.getRow(4).getCell(1).setText(this.checkDateEmpty(dateAssegnazioneCommissione));
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
        return statoAtto.equals(ASSEGNATO_COMMISSIONE)||statoAtto.equals(PRESO_CARICO_COMMISSIONE)
                || statoAtto.equals(VOTATO_COMMISSIONE)
                || statoAtto.equals(NOMINATO_RELATORE)
                || statoAtto.equals(LAVORI_COMITATO_RISTRETTO);
    }
    
    
    
    
    @Override
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
                    }else {
                    	commissione2atti.get(commissione).remove(currentAtto);
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
    
}
