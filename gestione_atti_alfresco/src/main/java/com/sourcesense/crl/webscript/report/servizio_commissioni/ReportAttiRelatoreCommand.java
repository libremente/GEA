package com.sourcesense.crl.webscript.report.servizio_commissioni;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.alfresco.model.ContentModel;
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

import com.google.common.collect.Maps;
import com.sourcesense.crl.webscript.report.ReportBaseCommand;
import com.sourcesense.crl.webscript.report.util.office.DocxManager;

/**
 * V2 - Big OK ( mancano dati tipoAttoRelatore e commissioneRelatore nei dati)
 *
 * bug ordinamento relatori
 *
 * @author Alessandro Benedetti
 *
 */
public class ReportAttiRelatoreCommand extends ReportBaseCommand {

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

            String sortField1 = "@{" + CRL_ATTI_MODEL + "}tipoAttoRelatore";// odine
            String sortField2 = "@{" + CRL_ATTI_MODEL + "}numeroAttoRelatore"; // alfabetico

            Map<String, ResultSet> relatore2results = Maps.newHashMap();
            /* execute guery grouped by Relatore */
            for (String relatore : this.relatoriJson) {
                SearchParameters sp = new SearchParameters();
                sp.addStore(spacesStore);
                sp.setLanguage(SearchService.LANGUAGE_FTS_ALFRESCO);
                String query = "PATH: \"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti/cm:" + this.legislatura + "//*\""
                        + " AND TYPE:\"crlatti:relatore\" ";
                
                if(!this.tipiAttoLucene.isEmpty()){
                	query += "AND " + convertListToString("@crlatti\\:tipoAttoRelatore",
                            this.tipiAttoLucene, true);
                }
                        
                if(!this.commissioniJson.isEmpty()){
                	query += " AND "
                            + convertListToString("@crlatti\\:commissioneRelatore",
                            this.commissioniJson, true);
                }
                
                query += " AND =@cm\\:name:\"" + relatore + "\"";
                        

                if (!dataNominaRelatoreDa.equals("*")
                        || !dataNominaRelatoreA.equals("*")) {
                    query += " AND @crlatti\\:dataNominaRelatore:["
                            + this.dataNominaRelatoreDa + " TO "
                            + this.dataNominaRelatoreA + " ]";
                }
                sp.setQuery(query);
                sp.addSort(sortField1, true);
                sp.addSort(sortField2, true);
                ResultSet currentResults = this.searchService.query(sp);
                relatore2results.put(relatore, currentResults);
            }
            
            //Se non viene selezionato nessun relatore deve considerarli tutti
            if(relatoriJson.isEmpty()){
            	SearchParameters sp = new SearchParameters();
                sp.addStore(spacesStore);
                sp.setLanguage(SearchService.LANGUAGE_FTS_ALFRESCO);
                String query = 
                		"PATH: \"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti/cm:" + this.legislatura + "//*\""
                        + " AND TYPE:\"crlatti:relatore\" ";
                sp.setQuery(query);
                sp.addSort(sortField1, true);
                sp.addSort(sortField2, true);
                ResultSet relatoriLegislaturaCorrente = this.searchService.query(sp);
                List<NodeRef> listaNodeRefRelatori = relatoriLegislaturaCorrente.getNodeRefs();
                Iterator<NodeRef> iteratorRelatori = listaNodeRefRelatori.iterator();
                while(iteratorRelatori.hasNext()){
                	NodeRef relatoreNodeRef = iteratorRelatori.next();
                	String nomeRelatore = (String) nodeService.getProperty(relatoreNodeRef, ContentModel.PROP_NAME);
                	relatore2results.put(nomeRelatore, relatoriLegislaturaCorrente);
                }
            }
            
            Map<NodeRef, NodeRef> atto2commissione = new HashMap<NodeRef, NodeRef>();
            TreeMap<String, List<NodeRef>> relatore2atti = this
                    .retrieveAttiOrdered(relatore2results, spacesStore,
                    atto2commissione);

            // obtain as much table as the results spreaded across the resultSet
            XWPFDocument generatedDocument = docxManager
                    .generateFromTemplateMap(
                    this.retrieveLenghtMap(relatore2atti), 4, false);
            // convert to input stream
            ByteArrayInputStream tempInputStream = saveTemp(generatedDocument);

            XWPFDocument finalDocument = this.fillTemplate(tempInputStream,
                    relatore2atti);
            ostream = new ByteArrayOutputStream();
            finalDocument.write(ostream);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ostream.toByteArray();

    }

    /**
     *
     *
     * @param finalDocStream
     * @param queryRes
     * @return
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public XWPFDocument fillTemplate(ByteArrayInputStream finalDocStream,
            Map<String, List<NodeRef>> relatore2atti) throws IOException {
        XWPFDocument document = new XWPFDocument(finalDocStream);
        int tableIndex = 0;
        List<XWPFTable> tables = document.getTables();
        for (String relatore : relatore2atti.keySet()) {
            for (NodeRef currentAtto : relatore2atti.get(relatore)) {
                XWPFTable currentTable = tables.get(tableIndex);
                Map<QName, Serializable> attoProperties = nodeService
                        .getProperties(currentAtto);

                QName nodeRefType = nodeService.getType(currentAtto);
                String tipoAtto = (String) nodeRefType.getLocalName();
                if (tipoAtto.length() > 4) {
                    tipoAtto = tipoAtto.substring(4);
                }
                // from Atto
                String numeroAtto = StringUtils.EMPTY
                        + (Integer) this.getNodeRefProperty(attoProperties,
                        "numeroAtto");
                String oggetto = (String) this.getNodeRefProperty(
                        attoProperties, "oggetto");

                ArrayList<String> commReferenteList = (ArrayList<String>) this
                        .getNodeRefProperty(attoProperties, "commReferente");
                
                String commReferente = StringUtils.EMPTY;
                if(commReferenteList!=null && !commReferenteList.isEmpty()){
	                commReferente = this.renderList(commReferenteList);
                }

                ArrayList<String> commConsultivaList = (ArrayList<String>) this
                        .getNodeRefProperty(attoProperties, "commConsultiva");
                
                String commConsultiva = StringUtils.EMPTY;
                if(commConsultivaList!=null && !commConsultivaList.isEmpty()){
                	commConsultiva = this.renderList(commConsultivaList);
                }

                currentTable
                        .getRow(0)
                        .getCell(0)
                        .setText(
                        this.checkStringEmpty(tipoAtto.toUpperCase() + " "
                        + numeroAtto));
                currentTable.getRow(0).getCell(1)
                        .setText(this.checkStringEmpty(oggetto));
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
