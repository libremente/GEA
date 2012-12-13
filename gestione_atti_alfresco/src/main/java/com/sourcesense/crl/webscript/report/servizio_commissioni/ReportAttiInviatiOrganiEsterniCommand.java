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
import com.sourcesense.crl.webscript.report.ReportBaseCommand;
import com.sourcesense.crl.webscript.report.util.office.DocxManager;

/**
 * 
 * TO DO :
 * - ? data assegnazione from atto
 * - elenco altri pareri 
 * - data assegnazione parere
 * 
 * SKIP TEMP
 * @author Alessandro Benedetti
 *
 */
public class ReportAttiInviatiOrganiEsterniCommand extends ReportBaseCommand {

	@Override
	public byte[] generate(byte[] templateByteArray, String json,
			StoreRef spacesStore) throws IOException {
		ByteArrayOutputStream ostream = null;
		try {
			ByteArrayInputStream is = new ByteArrayInputStream(
					templateByteArray);
			DocxManager docxManager = new DocxManager(is);
			this.initOrganismo(json);
			this.initDataAssegnazioneParereDa(json);
			this.initDataAssegnazioneParereA(json);
			String sortField1 = "{" + CRL_ATTI_MODEL
					+ "}organismoStatutarioParere";
			SearchParameters sp = new SearchParameters();
			sp.addStore(spacesStore);
			sp.setLanguage(SearchService.LANGUAGE_LUCENE);
			String query = "TYPE:\"crlatti:parere"
					+ "\" AND @crlatti\\:organismoStatutarioParere:\""
					+ this.organismo
					+ "\" AND @crlatti\\:dataAssegnazioneParere:["
					+ this.dataAssegnazioneParereDa + " TO "
					+ this.dataAssegnazioneParereA + " ]";
			sp.setQuery(query);
			sp.addSort(sortField1, false);
			ResultSet pareriResult = this.searchService.query(sp);
			Map<NodeRef, NodeRef> atto2parere= new HashMap<NodeRef, NodeRef>();
			ArrayListMultimap<String, NodeRef>  tipoAtto2atto=this.retrieveAttiFromPareri(pareriResult,atto2parere);
			// obtain as much table as the results spreaded across the resultSet
			XWPFDocument generatedDocument = docxManager.generateFromTemplate(
					pareriResult.length(), 5, false);
			// convert to input stream
			ByteArrayInputStream tempInputStream = saveTemp(generatedDocument);

			XWPFDocument finalDocument = this.fillTemplate(tempInputStream,
					tipoAtto2atto,atto2parere);
			ostream = new ByteArrayOutputStream();
			finalDocument.write(ostream);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ostream.toByteArray();

	}

	/**
	 * Extract Atto from a list of parere and insert it in a specific "tipoAtto" key
	 * @param pareriResult
	 * @param atto2parere 
	 * @return
	 */
	private ArrayListMultimap<String, NodeRef> retrieveAttiFromPareri(ResultSet pareriResult, Map<NodeRef, NodeRef> atto2parere) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * qui vanno inseriti nella table, presa dal template solo 6: tipo atto-
	 * numero atto- iniziativa - oggetto- commissione referente-data
	 * assegnazione commissione referente- commissione consultiva - elenco altri
	 * pareri - data assegnazione parere
	 * 
	 * @param finalDocStream
	 * @param queryRes
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public XWPFDocument fillTemplate(ByteArrayInputStream finalDocStream,
			ArrayListMultimap<String, NodeRef> commissione2atti,
			Map<NodeRef, NodeRef> atto2parere) throws IOException {
		XWPFDocument document = new XWPFDocument(finalDocStream);
		int tableIndex = 0;
		List<XWPFTable> tables = document.getTables();
		for (String commissione : commissione2atti.keySet()) {
			for (NodeRef currentAtto : commissione2atti.get(commissione)) {
				XWPFTable currentTable = tables.get(tableIndex);
				Map<QName, Serializable> attoProperties = nodeService
						.getProperties(currentAtto);
				Map<QName, Serializable> parereProperties = nodeService
						.getProperties(atto2parere.get(currentAtto));

				// from Atto
				QName nodeRefType = nodeService.getType(currentAtto);
				String tipoAtto = (String)nodeRefType.getLocalName();
				String numeroAtto = (String) this.getNodeRefProperty(
						attoProperties, "numeroAtto");
				String iniziativa = (String) this.getNodeRefProperty(
						attoProperties, "descrizioneIniziativa");
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
				
				//from Atto
				ArrayList<String> pareriList = (ArrayList<String>) this
						.getNodeRefProperty(attoProperties, "organismiStatutari");
				String altriPareri = "";
				for (String parere : pareriList)
					altriPareri += parere + " ";
				
				Date dateAssegnazioneCommissione =null;// ? data assegnazione from atto
				
				currentTable.getRow(0).getCell(1)
				.setText(this.checkStringEmpty(tipoAtto));
				currentTable.getRow(1).getCell(1)
						.setText(this.checkStringEmpty(numeroAtto));
				currentTable.getRow(2).getCell(1)
						.setText(this.checkStringEmpty(iniziativa));
				currentTable.getRow(3).getCell(1)
						.setText(this.checkStringEmpty(oggetto));
				currentTable.getRow(4).getCell(1)
				.setText(this.checkStringEmpty(commReferente));

				currentTable
						.getRow(5)
						.getCell(1)
						.setText(
								this.checkDateEmpty(dateAssegnazioneCommissione));
				
				currentTable.getRow(6).getCell(1)
				.setText(this.checkStringEmpty(commConsultiva));
				
				//elenco altri pareri - data assegnazione parere
				tableIndex++;
			}
		}

		return document;
	}
}
