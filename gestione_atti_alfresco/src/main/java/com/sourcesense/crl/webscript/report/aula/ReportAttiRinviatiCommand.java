package com.sourcesense.crl.webscript.report.aula;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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

import com.sourcesense.crl.webscript.report.ReportBaseCommand;
import com.sourcesense.crl.webscript.report.util.office.DocxManager;

/**
 * V2 Date dateRinvio =null; //(Date)
 * this.getNodeRefProperty(attoProperties,"dataRinvio"); Date dateTermine
 * =null;// (Date) this.getNodeRefProperty(attoProperties,"dataChiusura");
 * String noteGenerali =""; String motivazioneRinvio ="";
 *
 * @author Alessandro Benedetti
 *
 */
public class ReportAttiRinviatiCommand extends ReportBaseCommand {

    @Override
    public byte[] generate(byte[] templateByteArray, String json,
            StoreRef spacesStore) throws IOException {
        ByteArrayOutputStream ostream = null;
        ResultSet attiResults = null;
        try {
            ByteArrayInputStream is = new ByteArrayInputStream(
                    templateByteArray);
            DocxManager docxManager = new DocxManager(is);
            this.initLegislatura(json);
            this.initTipiAttoLuceneAtto(json);
            String sortField1 = "@{" + CRL_ATTI_MODEL + "}tipoAtto";
            String sortField2 = "@{" + CRL_ATTI_MODEL + "}numeroAtto";

            SearchParameters sp = new SearchParameters();
            sp.addStore(spacesStore);
            sp.setLanguage(SearchService.LANGUAGE_LUCENE);
            String query = "PATH: \"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti/cm:" + this.legislatura + "//*\" AND "
                    + convertListToString("TYPE", this.tipiAttoLucene, true)
                    + "AND @crlatti\\:rinviato:\"true\"";
            sp.setQuery(query);
            sp.addSort(sortField1, true);
            sp.addSort(sortField2, true);
            attiResults = this.searchService.query(sp);
            // obtain as much table as the results spreaded across the resultSet
            XWPFDocument generatedDocument = docxManager.generateFromTemplate(
                    attiResults.length(), 4, false);
            // convert to input stream
            ByteArrayInputStream tempInputStream = saveTemp(generatedDocument);

            XWPFDocument finalDocument = this.fillTemplate(tempInputStream,
                    attiResults);
            ostream = new ByteArrayOutputStream();
            finalDocument.write(ostream);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
        	if (attiResults!=null){
        		attiResults.close();
        	}
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
            ResultSet atti) throws IOException {
        XWPFDocument document = new XWPFDocument(finalDocStream);
        int tableIndex = 0;
        List<XWPFTable> tables = document.getTables();
        for (int i = 0; i < atti.length(); i++) {
            NodeRef currentAtto = atti.getNodeRef(i);
            XWPFTable currentTable = tables.get(tableIndex);
            Map<QName, Serializable> attoProperties = nodeService
                    .getProperties(currentAtto);
            QName nodeRefType = nodeService.getType(currentAtto);
            String tipoAtto = (String) nodeRefType.getLocalName();
            if (tipoAtto.length() > 4) {
                tipoAtto = tipoAtto.substring(4);
            }
            String numeroAtto = StringUtils.EMPTY + (Integer) this.getNodeRefProperty(attoProperties, "numeroAtto")+(String) this.getNodeRefProperty(attoProperties,"estensioneAtto");
            String oggetto = (String) this.getNodeRefProperty(attoProperties,
                    "oggetto");
            ArrayList<String> commReferenteList = (ArrayList<String>) this
                    .getNodeRefProperty(attoProperties, "commReferente");
            String commReferente = "";
            if (commReferenteList != null) {
                for (String commissioneReferenteMulti : commReferenteList) {
                    commReferente += commissioneReferenteMulti + " ";
                }
            }
            Date dateRinvio = null; //(Date) this.getNodeRefProperty(attoProperties,"dataRinvio");
            Date dateTermine = null;// (Date) this.getNodeRefProperty(attoProperties,"dataChiusura");
            String noteGenerali = "";
            String motivazioneRinvio = "";
            ArrayList<String> relatoriList = (ArrayList<String>) this
                    .getNodeRefProperty(attoProperties, "relatori");
            String relatori = this.renderFirmatariConGruppoList(relatoriList);

            currentTable
                    .getRow(0)
                    .getCell(1)
                    .setText(this.checkStringEmpty(tipoAtto.toUpperCase() + " " + numeroAtto));
            currentTable.getRow(1).getCell(1)
                    .setText(this.checkStringEmpty(oggetto));
            currentTable.getRow(2).getCell(1)
                    .setText(this.checkDateEmpty(dateRinvio));
            currentTable.getRow(3).getCell(1)
                    .setText(this.checkStringEmpty(relatori));
            currentTable.getRow(4).getCell(1)
                    .setText(this.checkStringEmpty(commReferente));
            currentTable.getRow(5).getCell(1)
                    .setText(this.checkDateEmpty(dateTermine));
            currentTable.getRow(6).getCell(1)
                    .setText(this.checkStringEmpty(motivazioneRinvio));
            currentTable.getRow(7).getCell(1)
                    .setText(this.checkStringEmpty(noteGenerali));
            tableIndex++;
        }

        return document;
    }
}
