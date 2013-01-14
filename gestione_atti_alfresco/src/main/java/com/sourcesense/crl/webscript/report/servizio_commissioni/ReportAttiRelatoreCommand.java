package com.sourcesense.crl.webscript.report.servizio_commissioni;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchParameters;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.QName;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.json.JSONException;

import com.google.common.collect.Maps;
import com.sourcesense.crl.webscript.report.ReportBaseCommand;
import com.sourcesense.crl.webscript.report.util.office.DocxManager;

/**
 * V2
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
				String query = "PATH: \"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti//*\""
						+ " AND TYPE:\""
						+ "crlatti:relatore\" AND "
						+ convertListToString("@crlatti\\:tipoAttoRelatore",
								this.tipiAttoLucene, true)
						+ " AND "
						+ convertListToString("@crlatti\\:commissioneRelatore",
								this.commissioniJson, false)
						+ " AND =@cm\\:name:\""
						+ relatore
						+ "\""
						+ " AND @crlatti\\:dataNominaRelatore:["
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
	@SuppressWarnings("unchecked")
	public XWPFDocument fillTemplate(ByteArrayInputStream finalDocStream,
			Map<String, List<NodeRef>> commissione2atti,
			Map<NodeRef, NodeRef> atto2commissione) throws IOException {
		XWPFDocument document = new XWPFDocument(finalDocStream);
		int tableIndex = 0;
		List<XWPFTable> tables = document.getTables();
		for (String commissione : commissione2atti.keySet()) {
			for (NodeRef currentAtto : commissione2atti.get(commissione)) {
				XWPFTable currentTable = tables.get(tableIndex);
				Map<QName, Serializable> attoProperties = nodeService
						.getProperties(currentAtto);

				QName nodeRefType = nodeService.getType(currentAtto);
				String tipoAtto = (String) nodeRefType.getLocalName();
				// from Atto
				String numeroAtto = ""
						+ (Integer) this.getNodeRefProperty(attoProperties,
								"numeroAtto");
				String oggetto = (String) this.getNodeRefProperty(
						attoProperties, "oggetto");

				ArrayList<String> commReferenteList = (ArrayList<String>) this
						.getNodeRefProperty(attoProperties, "commReferente");
				String commReferente = "";
				for (String commissioneReferenteMulti : commReferenteList)
					commReferente += commissioneReferenteMulti + " ";

				ArrayList<String> commConsultivaList = (ArrayList<String>) this
						.getNodeRefProperty(attoProperties, "commConsultiva");
				String commConsultiva = this.renderList(commConsultivaList);

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
				currentTable.getRow(2).getCell(2)
						.setText(this.checkStringEmpty(commConsultiva));

				tableIndex++;
			}
		}

		return document;
	}

}
