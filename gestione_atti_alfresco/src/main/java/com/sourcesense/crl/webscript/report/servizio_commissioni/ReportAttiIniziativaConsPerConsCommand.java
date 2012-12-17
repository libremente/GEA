package com.sourcesense.crl.webscript.report.servizio_commissioni;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.ResultSetRow;
import org.alfresco.service.cmr.search.SearchParameters;
import org.alfresco.service.cmr.search.SearchService;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.json.JSONException;

import com.sourcesense.crl.webscript.report.ReportBaseCommand;
import com.sourcesense.crl.webscript.report.util.office.DocxManager;


/**
 * TO DO:
 * - model change? firmatario-tipologia firma ...
 * data di presentazione
 * SKIP TEMP
 * @author Alessandro Benedetti
 *
 */
public class ReportAttiIniziativaConsPerConsCommand extends ReportBaseCommand {

	@Override
	public byte[] generate(byte[] templateByteArray, String json, StoreRef spacesStore)
			throws IOException {
		ByteArrayOutputStream ostream = null;
		try {
			ByteArrayInputStream is = new ByteArrayInputStream(
					templateByteArray);
			DocxManager docxManager = new DocxManager(is);
			this.initFirmatario(json);
			this.initTipoFirma(json);
			this.initDataAssegnazioneCommReferenteDa(json);
			this.initDataAssegnazioneCommReferenteA(json);
			this.initDataPresentazioneDa(json);
			this.initDataPresentazioneA(json);

			 String sortField1 = "{"+CRL_ATTI_MODEL+"}tipoAttoCommissione";
			 String sortField2 = "{"+CRL_ATTI_MODEL+"}numeroAttoCommissione";
			 List<ResultSet> allSearches=new LinkedList<ResultSet>();
			 //gruppo per consigliere?
			for (String commissione:this.commissioniJson) {
				SearchParameters sp = new SearchParameters();
				sp.addStore(spacesStore);
				sp.setLanguage(SearchService.LANGUAGE_LUCENE);
				String query="PATH: \"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti//*\"" +
						" AND TYPE:\""
								+ "crlatti:commissione" + "\" AND "+convertListToString("@crlatti\\:tipoAtto:", this.tipiAttoLucene)  + " AND @crlatti\\:ruoloCommissione:\""
								+ this.ruoloCommissione  +"\" AND @cm\\:name:\""
								+ commissione+"\" AND @crlatti\\:dataAssegnazioneCommissione:["
								+this.dataAssegnazioneCommReferenteDa+" TO "+
								this.dataAssegnazioneCommReferenteA+" ] AND @crlatti\\:dataPresentazione:["
										+this.dataPresentazioneDa+" TO "+
										this.dataPresentazioneA+" ]";
				sp.setQuery(query);
				sp.addSort(sortField1, false);
				sp.addSort(sortField2, false);
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
	 * numero atto - iniziativa - firmatari - data assegnazione - commissione referente -
	 * commissioni consultive - abbinamenti - data votazione - numero LCR - numero LR - data LR
	 * 
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
