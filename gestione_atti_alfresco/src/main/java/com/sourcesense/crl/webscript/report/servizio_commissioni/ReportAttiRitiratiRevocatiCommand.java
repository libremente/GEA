package com.sourcesense.crl.webscript.report.servizio_commissioni;

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
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.json.JSONException;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.sourcesense.crl.webscript.report.ReportBaseCommand;
import com.sourcesense.crl.webscript.report.util.office.DocxManager;

/**
 * V2 - misterioso errore di security causato dalla query
 * 
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
			/* init json params */
			this.initTipiAttoLuceneAtto(json);
			this.initDataRitiroDa(json);
			this.initDataRitiroA(json);

			/* sorting field */
			String sortField1 = "@{" + CRL_ATTI_MODEL + "}numeroAtto";

			List<ResultSet> tipiAtto = Lists.newArrayList();
			for (String tipoAtto : this.tipiAttoLucene) {
				SearchParameters sp = new SearchParameters();
				sp.addStore(spacesStore);
				sp.setLanguage(SearchService.LANGUAGE_LUCENE);
				String query = "PATH: \"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti//*\""
						+ " AND TYPE:\""
						+ tipoAtto
						+ "\" AND @crlatti\\:dataChiusura:["
						+ this.dataRitiroDa + " TO " + this.dataRitiroA + " ]";
				sp.setQuery(query);
				sp.addSort(sortField1, true);
				ResultSet attiResult = this.searchService.query(sp);
				tipiAtto.add(attiResult);
			}

			// obtain as much table as the results spreaded across the resultSet
			XWPFDocument generatedDocument = docxManager.generateFromTemplate(
					this.retrieveLenght(tipiAtto), 3, false);
			// convert to input stream
			ByteArrayInputStream tempInputStream = saveTemp(generatedDocument);

			XWPFDocument finalDocument = this.fillTemplate(tempInputStream,
					tipiAtto);
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
			List<ResultSet> tipiAtto) throws IOException {
		XWPFDocument document = new XWPFDocument(finalDocStream);
		int tableIndex = 0;
		List<XWPFTable> tables = document.getTables();
		for (ResultSet atti : tipiAtto) {
			for (int i = 0; i < atti.length(); i++) {
				NodeRef currentAtto = atti.getNodeRef(i);
				Map<QName, Serializable> attoProperties = nodeService
						.getProperties(currentAtto);
				String statoAtto = (String) this.getNodeRefProperty(
						attoProperties, "statoAtto");
				String tipoChiusura = (String) this.getNodeRefProperty(
						attoProperties, "tipoChiusura");
				if (this.checkStatoAtto(statoAtto, tipoChiusura)) {
					QName nodeRefType = nodeService.getType(currentAtto);
					String tipoAtto = (String) nodeRefType.getLocalName();
					XWPFTable currentTable = tables.get(tableIndex);
					// from Atto
					String numeroAtto = ""
							+ (Integer) this.getNodeRefProperty(attoProperties,
									"numeroAtto");
					String iniziativa = (String) this.getNodeRefProperty(
							attoProperties, "tipoIniziativa");
					String oggetto = (String) this.getNodeRefProperty(
							attoProperties, "oggetto");
					Date datePresentazione = (Date) this.getNodeRefProperty(
							attoProperties, "dataIniziativa");
					Date dateRevoca = (Date) this.getNodeRefProperty(
							attoProperties, "dataChiusura");
					// child of Atto
					ArrayList<String> firmatariList = (ArrayList<String>) this
							.getNodeRefProperty(attoProperties, "firmatari");
					String firmatari = "";
					if (firmatariList != null)
						for (String firmatario : firmatariList)
							firmatari += firmatario + ", ";
					if(!firmatari.equals(""))
						firmatari=firmatari.substring(0,firmatari.length()-2);
					currentTable
							.getRow(0)
							.getCell(1)
							.setText(
									this.checkStringEmpty(tipoAtto + " "
											+ numeroAtto));
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
		}

		return document;
	}

	protected int retrieveLenght(
			List<ResultSet> atti) {
		int count = 0;
		for (ResultSet listaAtti : atti) {
			for (NodeRef currentAtto : listaAtti.getNodeRefs()) {
				Map<QName, Serializable> attoProperties = nodeService
						.getProperties(currentAtto);
				String statoAtto = (String) this.getNodeRefProperty(
						attoProperties, "statoAtto");
				String tipoChiusura = (String) this.getNodeRefProperty(
						attoProperties, "tipoChiusura");
				if (this.checkStatoAtto(statoAtto, tipoChiusura)) {
					count++;
				}
			}

		}
		return count;
	}

	/**
	 * Check if the statoAtto is "Chiuso" and tipoChiusura is
	 * "Ritirato dai promotori"
	 * 
	 * @param statoAtto
	 * @return
	 */
	protected boolean checkStatoAtto(String statoAtto, String tipoChiusura) {
		return statoAtto.equals(CHIUSO) && tipoChiusura.equals(RITIRATO);
	}

}
