package com.sourcesense.crl.webscript.report.servizio_commissioni;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

/**
 * TO TEST
 * @author Alessandro Benedetti
 *
 */
public class ReportAttiRitiratiRevocatiCommand extends ReportBaseCommand {

	@Override
	public byte[] generate(byte[] templateByteArray, String json,
			StoreRef spacesStore) throws IOException {
		ByteArrayOutputStream ostream = null;
		try {
			ByteArrayInputStream is = new ByteArrayInputStream(
					templateByteArray);
			DocxManager docxManager = new DocxManager(is);
			/* Init and sorting */
			this.initTipiAttoLucene(json);
			this.initDataRitiroDa(json);
			this.initDataRitiroA(json);

			String sortField1 = "{" + CRL_ATTI_MODEL + "}numeroAtto";

			Map<String, ResultSet> tipoAtto2results = Maps.newHashMap();
			for (String tipoAtto : this.tipiAttoLucene) {
				SearchParameters sp = new SearchParameters();
				sp.addStore(spacesStore);
				sp.setLanguage(SearchService.LANGUAGE_LUCENE);
				// statoAtto = Chiuso
				// tipoChiusura = Ritirato dai promotori
				String query = "PATH: \"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti//*\"" +
						" AND TYPE:\"" + "crlatti:commissione"
						+ "\" AND @crlatti\\:tipoAttoCommissione:\"" + tipoAtto
						+ "\" AND @crlatti\\:dataChiusura:[" + this.dataRitiroDa
						+ " TO " + this.dataRitiroA + " ]";
				sp.setQuery(query);
				sp.addSort(sortField1, false);
				ResultSet currentResults = this.searchService.query(sp);
				tipoAtto2results.put(tipoAtto, currentResults);
			}
			Map<NodeRef, NodeRef> atto2commissione = new HashMap<NodeRef, NodeRef>();
			ArrayListMultimap<String, NodeRef> commissione2atti = this
					.retrieveAtti(tipoAtto2results, spacesStore,
							atto2commissione);

			// obtain as much table as the results spreaded across the resultSet
			XWPFDocument generatedDocument = docxManager.generateFromTemplate(
					this.retrieveLenght(commissione2atti), 2, false);
			// convert to input stream
			ByteArrayInputStream tempInputStream = saveTemp(generatedDocument);

			XWPFDocument finalDocument = this.fillTemplate(tempInputStream,
					commissione2atti, atto2commissione);
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
	 * numero atto- iniziativa -firmatari- oggetto-data presentazione- data
	 * revoca
	 * 
	 * 
	 * 
	 * @param finalDocStream
	 * @param queryRes
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public XWPFDocument fillTemplate(ByteArrayInputStream finalDocStream,
			ArrayListMultimap<String, NodeRef> commissione2atti,
			Map<NodeRef, NodeRef> atto2commissione) throws IOException {
		XWPFDocument document = new XWPFDocument(finalDocStream);
		int tableIndex = 0;
		List<XWPFTable> tables = document.getTables();
		for (String commissione : commissione2atti.keySet()) {
			for (NodeRef currentAtto : commissione2atti.get(commissione)) {
				XWPFTable currentTable = tables.get(tableIndex);
				Map<QName, Serializable> attoProperties = nodeService
						.getProperties(currentAtto);
				Map<QName, Serializable> commissioneProperties = nodeService
						.getProperties(atto2commissione.get(currentAtto));

				// from Atto
				String numeroAtto = (String) this.getNodeRefProperty(
						attoProperties, "numeroAtto");
				String iniziativa = (String) this.getNodeRefProperty(
						attoProperties, "descrizioneIniziativa");
				String oggetto = (String) this.getNodeRefProperty(
						attoProperties, "oggetto");
				// from Commissione
				String tipoAtto = (String) this.getNodeRefProperty(
						commissioneProperties, "tipoAttoCommissione");
				Date datePresentazione = (Date) this.getNodeRefProperty(
						attoProperties, "dataIniziativa");
				Date dateRevoca = (Date) this.getNodeRefProperty(
						attoProperties,"dataChiusura");
				//child of Atto
				ArrayList<String> firmatariList = (ArrayList<String>) this
						.getNodeRefProperty(attoProperties, "firmatari");
				String firmatari = "";
				for (String firmatario : firmatariList)
					firmatari += firmatario + " ";

				currentTable.getRow(0).getCell(1)
						.setText(this.checkStringEmpty(tipoAtto+" "+numeroAtto));
				currentTable.getRow(1).getCell(1)
						.setText(this.checkStringEmpty(iniziativa));
				currentTable.getRow(2).getCell(1)
						.setText(this.checkStringEmpty(firmatari));
				currentTable.getRow(3).getCell(1)
						.setText(this.checkStringEmpty(oggetto));
				currentTable.getRow(4).getCell(1)
						.setText(this.checkDateEmpty(datePresentazione));
				currentTable.getRow(5).getCell(1)
						.setText(this.checkDateEmpty(dateRevoca));
				tableIndex++;
			}
		}

		return document;
	}
}
