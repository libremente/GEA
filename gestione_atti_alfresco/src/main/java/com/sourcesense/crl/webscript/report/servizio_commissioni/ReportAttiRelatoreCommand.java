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
import com.sourcesense.crl.webscript.report.ReportBaseCommand;
import com.sourcesense.crl.webscript.report.util.office.DocxManager;

/**
 * TO DO:
 * - scrivere query relatori
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

			String sortField1 = "{" + CRL_ATTI_MODEL + "}nome";// odine
																// alfabetico

			SearchParameters sp = new SearchParameters();
			sp.addStore(spacesStore);
			sp.setLanguage(SearchService.LANGUAGE_LUCENE);
			String query = "";// query su relatori per nome e data Nomina
			sp.setQuery(query);
			sp.addSort(sortField1, false);
			ResultSet relatoriResults = this.searchService.query(sp);

			List<NodeRef> commissioni = this
					.retrieveCommissioniFromRelatori(relatoriResults);// filtrando i risultati
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
	 * Extract only valuable commissioni from the ones extracted from the
	 * Relatori
	 * 
	 * @param relatoriResults
	 * @return
	 */
	private List<NodeRef> retrieveCommissioniFromRelatori(
			ResultSet relatoriResults) {
		List<NodeRef> commissioni=Lists.newArrayList();
		int resultLength = relatoriResults.length();
		for (int i = 0; i < resultLength; i++) {
			NodeRef relatoreNodeRef = relatoriResults.getNodeRef(i);
			Map<QName, Serializable> relatoreProperties = nodeService
					.getProperties(relatoreNodeRef);
			NodeRef commissioneNodeRef = relatoreNodeRef;
			boolean check = false;
			while (!check) {// look for Atto in type hierarchy
				ChildAssociationRef childAssociationRef = nodeService
						.getPrimaryParent(commissioneNodeRef);
				commissioneNodeRef = childAssociationRef.getParentRef();
				QName nodeRefType = nodeService.getType(commissioneNodeRef);
				QName attoRefType = QName.createQName(CRL_ATTI_MODEL,
						"commissione");
				check = dictionaryService.isSubClass(nodeRefType,
						attoRefType);
			}
			Map<QName, Serializable> commissioneProperties = nodeService
					.getProperties(commissioneNodeRef);
			String commissione = (String) this.getNodeRefProperty(
					commissioneProperties, "name");
			if(this.checkCommissione(commissione))
				commissioni.add(commissioneNodeRef);

		}
		return commissioni;
	}

	/**
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
					//from Atto
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
							.getNodeRefProperty(attoProperties, "commConsultiva");
					String commConsultiva = "";
					for (String commissioneConsultivaMulti : commConsultivaList)
						commConsultiva += commissioneConsultivaMulti + " ";

					currentTable.getRow(0).getCell(1)
							.setText(this.checkStringEmpty(tipoAtto));
					currentTable.getRow(1).getCell(1)
							.setText(this.checkStringEmpty(numeroAtto));
					currentTable.getRow(2).getCell(1)
							.setText(this.checkStringEmpty(oggetto));
					currentTable.getRow(3).getCell(1)
					.setText(this.checkStringEmpty(commReferente));
					currentTable.getRow(4).getCell(1)
					.setText(this.checkStringEmpty(commConsultiva));
					
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
	
	/**
	 * check if the "tipoAtto" in input is good, related to the selection of
	 * tipoAtto good
	 * 
	 * @param tipoAtto
	 * @return
	 */
	private boolean checkCommissione(String commissione) {
		return this.commissioniJson.contains(commissione);
	}
}
