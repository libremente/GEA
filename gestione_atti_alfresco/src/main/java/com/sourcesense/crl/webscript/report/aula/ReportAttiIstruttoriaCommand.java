package com.sourcesense.crl.webscript.report.aula;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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

import com.sourcesense.crl.webscript.report.ReportBaseCommand;
import com.sourcesense.crl.webscript.report.util.office.DocxManager;

/**
 * ?- abbinamenti ?- firmatari solo se iniziativa consiliare ?- doppia
 * iniziativa ?- relatore e relazione scritta, da anagrafca?
 * 
 * @author Alessandro Benedetti
 * 
 */
public class ReportAttiIstruttoriaCommand extends ReportBaseCommand {

	@Override
	public byte[] generate(byte[] templateByteArray, String json,
			StoreRef spacesStore) throws IOException {
		ByteArrayOutputStream ostream = null;
		try {
			ByteArrayInputStream is = new ByteArrayInputStream(
					templateByteArray);
			DocxManager docxManager = new DocxManager(is);
			this.initTipiAttoLucene(json);
			this.initDataSedutaDa(json);
			this.initDataSedutaA(json);
			String sortField1 = "{" + CRL_ATTI_MODEL + "}tipoAtto";
			String sortField2 = "{" + CRL_ATTI_MODEL + "}numeroAtto";

			SearchParameters sp = new SearchParameters();
			sp.addStore(spacesStore);
			sp.setLanguage(SearchService.LANGUAGE_LUCENE);
			String query = "TYPE:\""
					+ "crlatti:atto"
					+ "\" AND "
					+ convertListToString("@crlatti\\:tipoAtto",
							this.tipiAttoLucene)
					+ "AND @crlatti\\:dataSedutaAula:[" + this.dataSedutaDa
					+ " TO " + this.dataSedutaA + " ]";
			sp.setQuery(query);
			sp.addSort(sortField1, false);
			sp.addSort(sortField2, false);
			ResultSet attiResults = this.searchService.query(sp);
			// obtain as much table as the results spreaded across the resultSet
			XWPFDocument generatedDocument = docxManager.generateFromTemplate(
					attiResults.length(), 2, false);
			// convert to input stream
			ByteArrayInputStream tempInputStream = saveTemp(generatedDocument);

			XWPFDocument finalDocument = this.fillTemplate(tempInputStream,
					attiResults);
			ostream = new ByteArrayOutputStream();
			finalDocument.write(ostream);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ostream.toByteArray();

	}

	/**
	 * fills the docx template,correctly replicated with the values extracted
	 * from the NodeRef in input (AttoNodeRef- CommissioneNodeRef)
	 * 
	 * @param finalDocStream
	 *            - docx stream
	 * @param commissione2atti
	 *            - String commissione -> list NodeRef type Atto
	 * @param atto2commissione
	 *            - NodeRef type Atto -> NodeRef type Commissione
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
			String statoAtto = (String) this.getNodeRefProperty(attoProperties,
					"statoAtto");
			if (this.checkStatoAtto(statoAtto)) {
				// from Atto
				QName nodeRefType = nodeService.getType(currentAtto);
				String tipoAtto = (String) nodeRefType.getLocalName();
				String abbinamenti = "";
				String numeroAtto = (String) this.getNodeRefProperty(
						attoProperties, "numeroAtto");
				String oggetto = (String) this.getNodeRefProperty(
						attoProperties, "oggetto");
				String iniziativa = (String) this.getNodeRefProperty(
						attoProperties, "descrizioneIniziativa");
				ArrayList<String> firmatariList = (ArrayList<String>) this
						.getNodeRefProperty(attoProperties, "firmatari");
				String firmatari = "";
				for (String firmatario : firmatariList)
					firmatari += firmatario + " ";
				ArrayList<String> commReferenteList = (ArrayList<String>) this
						.getNodeRefProperty(attoProperties, "commReferente");
				String commReferente = "";
				for (String commissioneReferenteMulti : commReferenteList)
					commReferente += commissioneReferenteMulti + " ";
				String relatore = (String) this.getNodeRefProperty(
						attoProperties, "oggetto");
				String relazioneScritta = (String) this.getNodeRefProperty(
						attoProperties, "oggetto");
				String noteGenerali = (String) this.getNodeRefProperty(
						attoProperties, "noteChiusura");
				currentTable
						.getRow(0)
						.getCell(1)
						.setText(
								this.checkStringEmpty(tipoAtto + " "
										+ numeroAtto));
				currentTable.getRow(1).getCell(1)
						.setText(this.checkStringEmpty(abbinamenti));
				currentTable.getRow(2).getCell(1)
						.setText(this.checkStringEmpty(oggetto));
				currentTable.getRow(3).getCell(1)
						.setText(this.checkStringEmpty(iniziativa));
				currentTable.getRow(7).getCell(1)
						.setText(this.checkStringEmpty(firmatari));
				currentTable.getRow(8).getCell(1)
						.setText(this.checkStringEmpty(commReferente));
				currentTable.getRow(9).getCell(1)
						.setText(this.checkStringEmpty(relatore));
				currentTable.getRow(10).getCell(1)
						.setText(this.checkStringEmpty(relazioneScritta));
				currentTable.getRow(11).getCell(1)
						.setText(this.checkStringEmpty(noteGenerali));
				tableIndex++;
			}
		}

		return document;
	}

	/**
	 * Check if the statoAtto is comprehended between "preso in carico e votato"
	 * 
	 * @param statoAtto
	 * @return
	 */
	private boolean checkStatoAtto(String statoAtto) {
		return statoAtto.equals(PRESO_CARICO_AULA);
	}
}
