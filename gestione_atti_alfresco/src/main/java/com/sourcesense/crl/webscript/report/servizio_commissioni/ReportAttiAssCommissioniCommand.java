package com.sourcesense.crl.webscript.report.servizio_commissioni;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchParameters;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.web.bean.repository.Repository;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.json.JSONException;

import com.sourcesense.crl.webscript.report.ReportBaseCommand;
import com.sourcesense.crl.webscript.report.util.office.DocxManager;

public class ReportAttiAssCommissioniCommand extends ReportBaseCommand {

	@Override
	public byte[] generate(byte[] templateByteArray, String json)
			throws IOException {
		ByteArrayOutputStream ostream = null;
		try {
			ByteArrayInputStream is = new ByteArrayInputStream(
					templateByteArray);
			DocxManager docxManager = new DocxManager(is);
			this.initCommonParams(json);
			// data di assegnazione in intervallo
			ResultSet queryRes = null;
			for (String commissione:this.commissioniJson) {
				queryRes = searchService.query(Repository.getStoreRef(),
						SearchService.LANGUAGE_LUCENE, "TYPE:\""
								+ "crlattI:commissione" + "\" AND @crlatti\\:tipoAtto:"
								+ this.tipiAttoLucene   + "\" AND @crlatti\\:ruoloCommissione:"
								+ this.ruoloCommissione  +"\" AND @cm\\:name:"
								+ commissione+"\" AND @crlatti\\:dataAssegnazioneCommissione:["
								+this.dataAssegnazioneCommReferenteDa+" TO "+
								this.dataAssegnazioneCommReferenteA+" ]\""
								);
			}
			/*
			SearchParameters sp = new SearchParameters();
	        sp.addStore(attoNodeRef.getStoreRef());
	        sp.setLanguage(SearchService.LANGUAGE_LUCENE);
	        sp.setQuery("PATH:\""+luceneAttoNodePath+"/cm:Pareri/*\" AND TYPE:\""+parereType+"\"");
	        String field = "{"+CRL_ATTI_MODEL+"}"+PROP_DATA_ASSEGNAZIONE_PARERE;
	        sp.addSort(field, false);*/

			// obtain resultSet Length and cycle on it to repeat template
			XWPFDocument generatedDocument = docxManager.generateFromTemplate(
					queryRes.length(), 5, false);
			// convert to input stream
			ByteArrayInputStream tempInputStream = saveTemp(generatedDocument);

			XWPFDocument finalDocument = this.fillTemplate(tempInputStream,
					queryRes);
			ostream = new ByteArrayOutputStream();
			finalDocument.write(ostream);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ostream.toByteArray();

	}

	/**
	 * ipotizzo field già presenti nella table, quindi inseriamo solo value
	 * qui vanno inseriti nella table, presa dal template solo 8: tipo atto-
	 * numero atto- competenza - iniziativa- oggetto - data assegnazione - data
	 * valutazione - commissione referente
	 * 
	 * @param finalDocStream
	 * @param queryRes
	 * @return
	 * @throws IOException
	 */
	public XWPFDocument fillTemplate(ByteArrayInputStream finalDocStream,
			ResultSet queryRes) throws IOException {
		XWPFDocument document = new XWPFDocument(finalDocStream);
		List<XWPFTable> tables = document.getTables();
		for (int k = 0; k < queryRes.length(); k++) {
			NodeRef currentNodeRef = queryRes.getNodeRef(k);
			XWPFTable newTable = tables.get(k);
			XWPFTableRow firstRow = newTable.getRow(0);

			firstRow.getCell(0).setText("1x2");

			XWPFTableRow secondRow = newTable.getRow(0);
			secondRow.getCell(0).setText("1x1");
			secondRow.getCell(0).setText("1x2");
		}
		return document;
	}
}
