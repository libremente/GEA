package com.sourcesense.crl.webscript.report.servizio_commissioni;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.service.cmr.repository.ChildAssociationRef;
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
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sourcesense.crl.webscript.report.ReportBaseCommand;
import com.sourcesense.crl.webscript.report.util.office.DocxManager;

/**
 * TO TEST
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
			this.initCommonParams(json);
			this.initRelatori(json);
			this.initDataNominaRelatoreDa(json);
			this.initDataNominaRelatoreA(json);

			String sortField1 = "@{" + CRL_ATTI_MODEL + "}tipoAttoRelatore";// odine
			String sortField2 = "@{" + CRL_ATTI_MODEL + "}numeroAttoRelatore"; // alfabetico

			Map<String, ResultSet> relatore2results = Maps.newHashMap();
			/* execute guery grouped by Commissione */
			for (String relatore : this.relatoriJson) {
				SearchParameters sp = new SearchParameters();
				sp.addStore(spacesStore);
				sp.setLanguage(SearchService.LANGUAGE_LUCENE);
				String query = "PATH: \"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti//*\""
						+ " AND TYPE:\""
						+ "crlatti:relatore"
						+ "\" AND "
						+ convertListToString("@crlatti\\:tipoAttoRelatore",
								this.tipiAttoLucene)
						+ " AND "
						+ convertListToString("@crlatti\\:commissioneRelatore",
								this.commissioniJson)
						+ "\" AND @cm\\:name:\""
						+ relatore
						+ "\" AND @crlatti\\:dataNominaRelatore:["
						+ this.dataNominaRelatoreDa
						+ " TO "
						+ this.dataNominaRelatoreA + " ]";
				sp.setQuery(query);
				sp.addSort(sortField1, true);
				sp.addSort(sortField2, true);
				ResultSet currentResults = this.searchService.query(sp);
				relatore2results.put(relatore, currentResults);
			}

			Map<NodeRef, NodeRef> atto2commissione = new HashMap<NodeRef, NodeRef>();
			ArrayListMultimap<String, NodeRef> relatore2atti = this
					.retrieveAtti(relatore2results, spacesStore,
							atto2commissione);

			// obtain as much table as the results spreaded across the resultSet
			XWPFDocument generatedDocument = docxManager
					.generateFromTemplateMap(
							this.retrieveLenghtMap(relatore2atti), 2, false);
			// convert to input stream
			ByteArrayInputStream tempInputStream = saveTemp(generatedDocument);

			XWPFDocument finalDocument = this.fillTemplate(tempInputStream,
					relatore2atti, atto2commissione);
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
					if (commConsultivaList != null)
						for (String commissioneConsultivaMulti : commConsultivaList)
							commConsultiva += commissioneConsultivaMulti + ",";

					currentTable
							.getRow(0)
							.getCell(0)
							.setText(
									this.checkStringEmpty(tipoAtto + " "
											+ numeroAtto));
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
