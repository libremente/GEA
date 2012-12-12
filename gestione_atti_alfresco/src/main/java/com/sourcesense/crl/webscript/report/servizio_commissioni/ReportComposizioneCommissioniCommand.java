package com.sourcesense.crl.webscript.report.servizio_commissioni;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.ResultSetRow;
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
 * TO DO:
 * - Consiglieri from Commissione?
 * 
 * @author Alessandro Benedetti
 *
 */
public class ReportComposizioneCommissioniCommand extends ReportBaseCommand {
	@Override
	public byte[] generate(byte[] templateByteArray, String json,
			StoreRef spacesStore) throws IOException {
		ByteArrayOutputStream ostream = null;
			ByteArrayInputStream is = new ByteArrayInputStream(
					templateByteArray);
			DocxManager docxManager = new DocxManager(is);

			Map<String, ResultSet> commissione2results = Maps.newHashMap();
		
				SearchParameters sp = new SearchParameters();
				sp.addStore(spacesStore);
				sp.setLanguage(SearchService.LANGUAGE_LUCENE);
				String query = "TYPE:\""
						+ "crlatti:commissione\"";
				sp.setQuery(query);
				ResultSet commissioniResult = this.searchService.query(sp);
		

			// obtain as much table as the results spreaded across the resultSet
			XWPFDocument generatedDocument = docxManager.generateFromTemplate(commissioniResult.length(), 2, false);
			// convert to input stream
			ByteArrayInputStream tempInputStream = saveTemp(generatedDocument);

			XWPFDocument finalDocument = this.fillTemplate(tempInputStream,
					commissioniResult);
			ostream = new ByteArrayOutputStream();
			finalDocument.write(ostream);


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
	public XWPFDocument fillTemplate(ByteArrayInputStream finalDocStream,
			ResultSet commissioniResult) throws IOException {
		XWPFDocument document = new XWPFDocument(finalDocStream);
		int tableIndex = 0;
		List<XWPFTable> tables = document.getTables();
			for (NodeRef currentCommissione : commissioniResult.getNodeRefs()) {
				XWPFTable currentTable = tables.get(tableIndex);
				Map<QName, Serializable> commissioneProperties = nodeService
						.getProperties(currentCommissione);

				// from Commissione
				String nomeCommissione = (String) this.getNodeRefProperty(
						commissioneProperties, "name");
				
				
				ArrayList<String> consigliereList = (ArrayList<String>) this
						.getNodeRefProperty(commissioneProperties, "consiglieri");
				String consigliere = "";
				for (String consigliereListElem : consigliereList)
					consigliere += consigliereListElem + " ";
				
				currentTable.getRow(0).getCell(1)
						.setText(this.checkStringEmpty(nomeCommissione));
				currentTable.getRow(1).getCell(1)
				.setText(this.checkStringEmpty(consigliere));
				tableIndex++;
			}
		

		return document;
	}
}
