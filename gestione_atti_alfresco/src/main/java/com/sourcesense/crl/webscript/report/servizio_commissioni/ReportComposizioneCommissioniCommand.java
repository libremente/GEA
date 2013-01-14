package com.sourcesense.crl.webscript.report.servizio_commissioni;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchParameters;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.QName;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import com.sourcesense.crl.webscript.report.ReportBaseCommand;
import com.sourcesense.crl.webscript.report.util.office.DocxManager;

/**
 * V2
 * 
 * @author Alessandro Benedetti
 * 
 */
public class ReportComposizioneCommissioniCommand extends ReportBaseCommand {
	@Override
	public byte[] generate(byte[] templateByteArray, String json,
			StoreRef spacesStore) throws IOException {
		ByteArrayOutputStream ostream = null;
		ByteArrayInputStream is = new ByteArrayInputStream(templateByteArray);
		DocxManager docxManager = new DocxManager(is);

		SearchParameters sp = new SearchParameters();
		sp.addStore(spacesStore);
		sp.setLanguage(SearchService.LANGUAGE_LUCENE);
		/* query from anagrafica */
		String query = "PATH: \"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti/cm:Anagrafica/cm:Commissioni/*\"";
		sp.setQuery(query);
		ResultSet commissioniResult = this.searchService.query(sp);

		// obtain as much table as the results spreaded across the resultSet
		XWPFDocument generatedDocument = docxManager.generateFromTemplate(
				commissioniResult.length(), 2, false);
		// convert to input stream
		ByteArrayInputStream tempInputStream = saveTemp(generatedDocument);

		XWPFDocument finalDocument = this.fillTemplate(tempInputStream,
				commissioniResult, docxManager);
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
			ResultSet commissioniResult, DocxManager docxManager)
			throws IOException {
		XWPFDocument document = new XWPFDocument(finalDocStream);
		int tableIndex = 0;
		List<XWPFTable> tables = document.getTables();
		for (NodeRef currentCommissione : commissioniResult.getNodeRefs()) {
			XWPFTable currentTable = tables.get(tableIndex);
			// from Commissione
			String nomeCommissione = (String) nodeService.getProperty(
					currentCommissione, ContentModel.PROP_NAME);
			List<ChildAssociationRef> consiglieriAssList = nodeService
					.getChildAssocs(currentCommissione);
			List<String> consiglieri = new ArrayList<String>();
			List<String> partiti = new ArrayList<String>();
			String currentConsigliere = "";
			for (ChildAssociationRef consigliereAss : consiglieriAssList) {
				NodeRef consigliereRef = consigliereAss.getChildRef();
				Map<QName, Serializable> consigliereProperties = nodeService
						.getProperties(consigliereRef);
				String nomeConsigliere = (String) this.getNodeRefProperty(
						consigliereProperties, "nomeConsigliereAnagrafica");
				String cognomeConsigliere = (String) this.getNodeRefProperty(
						consigliereProperties, "cognomeConsigliereAnagrafica");
				String codice = (String) this.getNodeRefProperty(
						consigliereProperties,
						"codiceGruppoConsigliereAnagrafica");
				currentConsigliere = cognomeConsigliere + " " + nomeConsigliere;
				consiglieri.add(currentConsigliere);
				partiti.add(codice);

			}

			currentTable.getRow(0).getCell(0)
					.setText(this.checkStringEmpty(nomeCommissione));
			docxManager.insertListInCell(currentTable.getRow(1).getCell(1),
					consiglieri);
			docxManager.insertListInCell(currentTable.getRow(1).getCell(2),
					partiti);

			tableIndex++;
		}

		return document;
	}
}
