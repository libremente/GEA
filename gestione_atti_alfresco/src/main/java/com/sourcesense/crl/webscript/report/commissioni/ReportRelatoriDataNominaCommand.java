package com.sourcesense.crl.webscript.report.commissioni;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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
import com.sourcesense.crl.webscript.report.ReportBaseCommand;
import com.sourcesense.crl.webscript.report.util.office.DocxManager;

/**
 * TO DO:
 * -? scrivere query relatori ?
 * SKIP TEMP
 * 
 * abbiamo una table per ogni data : 
 * Per ogni data abbiamo n relatori e per ciascuno n atti :
 * |data:| dataValue|
 * |RelatoreA|Atti con a capo|
 * |RelatoreB|Atti con a capo|
 * 
 * Quindi per ognuno creiamo riga in più.
 *
 * @author Alessandro Benedetti
 *
 */
public class ReportRelatoriDataNominaCommand extends ReportBaseCommand {

	public byte[] generate(byte[] templateByteArray, String json,
			StoreRef spacesStore) throws IOException {
		ByteArrayOutputStream ostream = null;
		try {
			ByteArrayInputStream is = new ByteArrayInputStream(
					templateByteArray);
			DocxManager docxManager = new DocxManager(is);
			this.initCommonParams(json);
			this.initRelatori(json);
			this.initDataNominaRelatoreDa(json);
			this.initDataNominaRelatoreA(json);

			String sortField1 = "@{" + CRL_ATTI_MODEL + "}nome";// odine
																// alfabetico

			SearchParameters sp = new SearchParameters();
			sp.addStore(spacesStore);
			sp.setLanguage(SearchService.LANGUAGE_LUCENE);
			String query = "";// query su relatori per nome e data Nomina
			sp.setQuery(query);
			sp.addSort(sortField1, false);
			ResultSet relatoriResults = this.searchService.query(sp);

			List<NodeRef> commissioni = this
					.retrieveCommissioniFromRelatori(relatoriResults);// filtrando
																		// i
																		// risultati
			Map<NodeRef, NodeRef> atto2commissione = new HashMap<NodeRef, NodeRef>();
			ArrayListMultimap<String, NodeRef> commissione2atti = ArrayListMultimap
					.create();
			this.retrieveAttiFromList(commissioni, spacesStore,
					atto2commissione, commissione2atti);

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
	 * ipotizzo field già presenti nella table, quindi inseriamo solo value qui
	 * vanno inseriti nella table, presa dal template solo 8: tipo atto-
	 * commissione - data nomina - consigliere - tipo atto - numero atto -
	 * oggetto elenco relatori
	 * 
	 * @param finalDocStream
	 * @param queryRes
	 * @return
	 * @throws IOException
	 */
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

				// from Commissione
				String tipoAtto = (String) this.getNodeRefProperty(
						commissioneProperties, "tipoAttoCommissione");
				if (this.checkTipoAtto(tipoAtto)) {
					// from Atto
					String numeroAtto = (String) this.getNodeRefProperty(
							attoProperties, "numeroAtto");
					String oggetto = (String) this.getNodeRefProperty(
							attoProperties, "oggetto");

					ArrayList<String> commReferenteList = (ArrayList<String>) this
							.getNodeRefProperty(attoProperties, "commReferente");
					String commReferente = "";
					for (String commissioneReferenteMulti : commReferenteList)
						commReferente += commissioneReferenteMulti + " ";

					ArrayList<String> commConsultivaList = (ArrayList<String>) this
							.getNodeRefProperty(attoProperties,
									"commConsultiva");
					String commConsultiva = "";
					for (String commissioneConsultivaMulti : commConsultivaList)
						commConsultiva += commissioneConsultivaMulti + ",";
					currentTable.getRow(0).getCell(0)
							.setText(this.checkStringEmpty(numeroAtto));
					currentTable.getRow(0).getCell(1)
							.setText(this.checkStringEmpty(oggetto));
					currentTable.getRow(1).getCell(2)
							.setText(this.checkStringEmpty(commReferente));
					currentTable
							.getRow(2)
							.getCell(2)
							.setText(
									this.checkStringEmpty(commConsultiva
											.substring(0,
													commConsultiva.length() - 1)));

					tableIndex++;
				}
			}
		}

		return document;
	}

	/**
	 * check if the "tipoAtto" in input is good, related to the selection of
	 * tipoAtto good
	 * 
	 * @param tipoAtto
	 * @return
	 */
	private boolean checkTipoAtto(String tipoAtto) {
		return this.tipiAttoLucene.contains(tipoAtto);
	}
}
