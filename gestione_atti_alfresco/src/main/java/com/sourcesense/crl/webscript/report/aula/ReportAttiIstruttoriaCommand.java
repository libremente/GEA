package com.sourcesense.crl.webscript.report.aula;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchParameters;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.DynamicNamespacePrefixResolver;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.json.JSONException;

import com.google.common.collect.ArrayListMultimap;
import com.sourcesense.crl.webscript.report.ReportBaseCommand;
import com.sourcesense.crl.webscript.report.util.office.DocxManager;

/**
 * TO TEST relazione scritta -> bianca note Generali Aula -> metterlo vuoto
 * 
 * @author Alessandro Benedetti
 * 
 */
public class ReportAttiIstruttoriaCommand extends ReportBaseCommand {

	private static final String SEP = ", ";
	private static final String ABBINAMENTI_SPACE_NAME = "Abbinamenti";

	@Override
	public byte[] generate(byte[] templateByteArray, String json,
			StoreRef spacesStore) throws IOException {
		ByteArrayOutputStream ostream = null;
		try {
			ByteArrayInputStream is = new ByteArrayInputStream(
					templateByteArray);
			DocxManager docxManager = new DocxManager(is);
			this.initTipiAttoLuceneAtto(json);
			this.initDataSedutaDa(json);
			this.initDataSedutaA(json);
			String sortField1 = "@{" + CRL_ATTI_MODEL + "}tipoAtto";
			String sortField2 = "@{" + CRL_ATTI_MODEL + "}numeroAtto";

			SearchParameters sp = new SearchParameters();
			sp.addStore(spacesStore);
			sp.setLanguage(SearchService.LANGUAGE_LUCENE);
			String query = "PATH: \"/app:company_home/cm:CRL/cm:Gestione_x0020_Atti//*\" AND "
					+ convertListToString("TYPE", this.tipiAttoLucene)
					+ " AND @crlatti\\:dataSedutaAula:["
					+ this.dataSedutaDa
					+ " TO " + this.dataSedutaA + " ]";
			sp.setQuery(query);
			sp.addSort(sortField1, true);
			sp.addSort(sortField2, true);
			ResultSet attiResults = this.searchService.query(sp);
			// obtain as much table as the results spreaded across the resultSet
			XWPFDocument generatedDocument = docxManager.generateFromTemplate(
					this.retrieveLenght(attiResults), 2, false);
			// convert to input stream
			ByteArrayInputStream tempInputStream = saveTemp(generatedDocument);

			XWPFDocument finalDocument = this.fillTemplate(tempInputStream,
					attiResults,docxManager);
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
			ResultSet atti,DocxManager docxManager) throws IOException {
		XWPFDocument document = new XWPFDocument(finalDocStream);
		int tableIndex = 0;
		List<XWPFTable> tables = document.getTables();
		for (int i = 0; i < atti.length(); i++) {
			/* Extract values from Alfresco */
			NodeRef currentAtto = atti.getNodeRef(i);
			Map<QName, Serializable> attoProperties = nodeService
					.getProperties(currentAtto);
			String statoAtto = (String) this.getNodeRefProperty(attoProperties,
					"statoAtto");
			if (this.checkStatoAtto(statoAtto)) {
				XWPFTable currentTable = tables.get(tableIndex);
				// from Atto
				QName nodeRefType = nodeService.getType(currentAtto);
				String tipoAtto = (String) nodeRefType.getLocalName();
				String abbinamenti = getAbbinamenti(currentAtto);
				String numeroAtto = ""
						+ (Integer) this.getNodeRefProperty(attoProperties,
								"numeroAtto");
				String oggetto = (String) this.getNodeRefProperty(
						attoProperties, "oggetto");
				String iniziativa = (String) this.getNodeRefProperty(
						attoProperties, "tipoIniziativa");
				String descrizioneIniziativa = (String) this
						.getNodeRefProperty(attoProperties, "descrizioneIniziativa");
				ArrayList<String> firmatariList = (ArrayList<String>) this
						.getNodeRefProperty(attoProperties, "firmatari");
				String firmatari =this.renderList(firmatariList);
				ArrayList<String> commReferenteList = (ArrayList<String>) this
						.getNodeRefProperty(attoProperties, "commReferente");
				String commReferente = "";
				if (commReferenteList != null)
					for (String commissioneReferenteMulti : commReferenteList)
						commReferente += commissioneReferenteMulti + " ";

				ArrayList<String> relatoriList = (ArrayList<String>) this
						.getNodeRefProperty(attoProperties, "relatori");

				String relazioneScritta = "";// to complete
				String noteGenerali = "";// to complete
				/* write values in table */
				currentTable
						.getRow(0)
						.getCell(1)
						.setText(
								this.checkStringEmpty(tipoAtto));
				currentTable
				.getRow(0)
				.getCell(2)
				.setText(
						this.checkStringEmpty(numeroAtto));
				currentTable.getRow(1).getCell(1)
						.setText(this.checkStringEmpty(abbinamenti));
				currentTable.getRow(2).getCell(1)
						.setText(this.checkStringEmpty(oggetto));
				currentTable.getRow(3).getCell(1)
						.setText(this.checkStringEmpty(iniziativa));
				currentTable.getRow(4).getCell(1)
						.setText(this.checkStringEmpty(firmatari));
				currentTable.getRow(5).getCell(1)
						.setText(this.checkStringEmpty(descrizioneIniziativa));
				currentTable.getRow(6).getCell(1)
						.setText(this.checkStringEmpty(commReferente));
				docxManager.insertListInCell(currentTable.getRow(7).getCell(1), relatoriList);
				currentTable.getRow(7).getCell(3)
						.setText(this.checkStringEmpty(relazioneScritta));
				currentTable.getRow(8).getCell(1)
						.setText(this.checkStringEmpty(noteGenerali));
				tableIndex++;
			}
		}

		return document;
	}

	private String getRelazioneScritta(NodeRef currentAtto) {
		/* c'è un qualche problema qui, viene fuori un nodeRef Null */
		NodeRef aulaFolder = nodeService.getChildByName(currentAtto,
				ContentModel.ASSOC_CONTAINS, "Aula");
		return (String) nodeService.getProperty(aulaFolder,
				QName.createQName(CRL_ATTI_MODEL, "relazioneScrittaAula"));
	}

	// Search last "passaggio" function
	public NodeRef getLastPassaggio(NodeRef attoNodeRef) {

		NodeRef passaggio = null;

		DynamicNamespacePrefixResolver namespacePrefixResolver = new DynamicNamespacePrefixResolver(
				null);
		namespacePrefixResolver.registerNamespace(
				NamespaceService.SYSTEM_MODEL_PREFIX,
				NamespaceService.SYSTEM_MODEL_1_0_URI);
		namespacePrefixResolver.registerNamespace(
				NamespaceService.CONTENT_MODEL_PREFIX,
				NamespaceService.CONTENT_MODEL_1_0_URI);
		namespacePrefixResolver.registerNamespace(
				NamespaceService.APP_MODEL_PREFIX,
				NamespaceService.APP_MODEL_1_0_URI);

		String luceneAttoNodePath = nodeService.getPath(attoNodeRef)
				.toPrefixString(namespacePrefixResolver);

		ResultSet passaggiNodes = searchService.query(
				attoNodeRef.getStoreRef(), SearchService.LANGUAGE_LUCENE,
				"PATH:\"" + luceneAttoNodePath + "/cm:Passaggi/*\"");

		int numeroPassaggio = 0;
		String nomePassaggio = "";
		int passaggioMax = 0;
		for (int i = 0; i < passaggiNodes.length(); i++) {

			nomePassaggio = (String) nodeService.getProperty(
					passaggiNodes.getNodeRef(i), ContentModel.PROP_NAME);
			numeroPassaggio = Integer.parseInt(nomePassaggio.substring(9));

			if (numeroPassaggio > passaggioMax) {
				passaggioMax = numeroPassaggio;
				passaggio = passaggiNodes.getNodeRef(i);
			}

		}

		return passaggio;
	}

	/**
	 * Restituisce stringa valorizzata con tutti i nomi degli abbinamenti
	 * separati da virgola
	 * 
	 * @param currentAtto
	 * @return abbinamento1, abbinamento2, abbinamento3, etc..
	 */
	private String getAbbinamenti(NodeRef currentAtto) {
		String abbinamentiValue = StringUtils.EMPTY;
		NodeRef passaggio = getLastPassaggio(currentAtto);
		NodeRef abbinamentiFolderNode = nodeService.getChildByName(passaggio,
				ContentModel.ASSOC_CONTAINS, ABBINAMENTI_SPACE_NAME);
		List<ChildAssociationRef> abbinamenti = nodeService
				.getChildAssocs(abbinamentiFolderNode);
		for (Iterator<ChildAssociationRef> iterator = abbinamenti.iterator(); iterator
				.hasNext();) {
			ChildAssociationRef childAssociationRef = (ChildAssociationRef) iterator
					.next();
			NodeRef abbinamento = childAssociationRef.getChildRef();
			abbinamentiValue += (String) nodeService.getProperty(abbinamento,
					ContentModel.PROP_NAME) + SEP;
		}
		return abbinamentiValue;
	}
	
	protected int retrieveLenght(
			ResultSet attiResults) {
		int count = 0;
			for (NodeRef currentAtto : attiResults.getNodeRefs()) {
				Map<QName, Serializable> attoProperties = nodeService
						.getProperties(currentAtto);
				String statoAtto = (String) this.getNodeRefProperty(
						attoProperties, "statoAtto");
				if (this.checkStatoAtto(statoAtto)) {
					count++;
				}
			}
		return count;
	}
	/**
	 * Check if the statoAtto is comprehended between "preso in carico e votato"
	 * 
	 * @param statoAtto
	 * @return
	 */
	protected boolean checkStatoAtto(String statoAtto) {
		return statoAtto.equals(PRESO_CARICO_AULA);
	}
}
