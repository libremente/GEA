package com.sourcesense.crl.webscript.report.commissioni;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.ResultSetRow;
import org.alfresco.service.cmr.search.SearchParameters;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.web.bean.repository.Repository;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.json.JSONException;

import com.sourcesense.crl.webscript.report.ReportBaseCommand;
import com.sourcesense.crl.webscript.report.util.office.DocxManager;

public class ReportRelatoriDataNominaCommand extends ReportBaseCommand {

	@Override
	public byte[] generate(byte[] templateByteArray, String json, StoreRef spacesStore)
			throws IOException {
		ByteArrayOutputStream ostream = null;
		try {
			ByteArrayInputStream is = new ByteArrayInputStream(
					templateByteArray);
			DocxManager docxManager = new DocxManager(is);
			this.initCommonParams(json);
			this.initRelatori(json);
			this.initDataNominaRelatoreDa(json);
			this.initDataNominaRelatoreA(json);
			
			//sorting gruppo per relatore - ordine alfabetico - sottogruppo per tipo atto - ordinato per numero
			 String sortField1 = "{"+CRL_ATTI_MODEL+"}numeroAtto";
			 List<ResultSet> allSearches=new LinkedList<ResultSet>();
			 for (String tipoAtto:this.tipiAttoLucene) {
				SearchParameters sp = new SearchParameters();
				sp.addStore(spacesStore);
				sp.setLanguage(SearchService.LANGUAGE_LUCENE);
				String query="TYPE:\""
						+ "crlattI:atto" + "\" AND @crlatti\\:tipoAtto:"
						+ tipoAtto   + "\" AND @crlatti\\:dataRitiro:["
						+this.dataRitiroDa+" TO "+
						this.dataRitiroA+" ]\"";
				sp.setQuery(query);
				sp.addSort(sortField1, false);
				ResultSet currentResults = this.searchService.query(sp);
				allSearches.add(currentResults);
			}
			// obtain as much table as the results spreaded across the resultSet
				XWPFDocument generatedDocument = docxManager.generateFromTemplate(
						this.retrieveLenght(allSearches), 5, false);
				// convert to input stream
				ByteArrayInputStream tempInputStream = saveTemp(generatedDocument);

				XWPFDocument finalDocument = this.fillTemplate(tempInputStream,
						allSearches);
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
	 * commissione - data nomina - consigliere - tipo atto - numero atto - oggetto
	 * elenco relatori
	 * @param finalDocStream
	 * @param queryRes
	 * @return
	 * @throws IOException
	 */
	public XWPFDocument fillTemplate(ByteArrayInputStream finalDocStream,
			List<ResultSet> allSearches) throws IOException {
		XWPFDocument document = new XWPFDocument(finalDocStream);
		for(ResultSet resultSet:allSearches){
			for(int i=0;i<resultSet.length();i++){
				ResultSetRow row = resultSet.getRow(i);
						System.out.println("ID " + i+" "+row.getNodeRef());
			}
			}
		
			
		
		/*
		List<XWPFTable> tables = document.getTables();
		for (int k = 0; k < allSearches.length(); k++) {
			NodeRef currentNodeRef = allSearches.getNodeRef(k);
			XWPFTable newTable = tables.get(k);
			XWPFTableRow firstRow = newTable.getRow(0);

			firstRow.getCell(0).setText("1x2");

			XWPFTableRow secondRow = newTable.getRow(0);
			secondRow.getCell(0).setText("1x1");
			secondRow.getCell(0).setText("1x2");
		}*/
		return document;
	}
}
