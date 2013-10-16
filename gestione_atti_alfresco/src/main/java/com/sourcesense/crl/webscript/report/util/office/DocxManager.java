package com.sourcesense.crl.webscript.report.util.office;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import com.sourcesense.crl.webscript.report.util.lucene.LuceneMockDocument;

/**
 * This class represents a Docx Manager to replicate and fill simple document
 * with table in input
 * 
 * @author Alessandro Benedetti
 * 
 */
public class DocxManager {

	private XWPFDocument document;

	public DocxManager(InputStream is) throws IOException {
		this.document = new XWPFDocument(is);
	}

	/**
	 * Extracts the table from the input Docx ( template) and copy the table n
	 * times ( n is the size of luceneDocs in input)
	 * 
	 * @param breakBetween
	 *            TODO
	 * @param luceneDocs
	 * 
	 * @return
	 */
	public XWPFDocument generateFromTemplateMap( Map<String,Integer> group2count, int kpage,
			boolean breakBetween) {
		XWPFDocument newDoc = new XWPFDocument();
		boolean newPage = false;// new page on title
		List<XWPFTable> tables = document.getTables();
		XWPFTable tableExt = tables.get(0);//template table
		int k = 0;
		/*Copy template paragraphs*/
		List<XWPFParagraph> paragraphs = document.getParagraphs();
		int parCounter=0;
		for (int i = 0; i < paragraphs.size(); i++) {
			XWPFParagraph currentPar = paragraphs.get(i);
			//if(!currentPar.getText().trim().equals("")){
			newDoc.createParagraph();
			newDoc.setParagraph(currentPar, parCounter);
			parCounter++;// }
		}
		for (String key : group2count.keySet()) {
			if ( group2count.get(key)>0) {
				/*create key title*/
				XWPFParagraph keyTitle = newDoc.createParagraph();
				XWPFRun keyRun = keyTitle.createRun();
				keyRun.setText(key.trim());
				if (k % kpage == 0 && k != 0) {
					keyTitle.setPageBreak(true);
					newPage = true;
				}
			}
			for (int j = 0; j < group2count.get(key); j++) {
				XWPFParagraph createParagraph = newDoc.createParagraph();
				if ((k % kpage == 0 && k!=0) && (!newPage)) {
					createParagraph.setPageBreak(true);
					
				}
				if (k % kpage == 0 && k!=0) {
					XWPFRun run = createParagraph.createRun();
					run.addBreak();
				}
				newDoc.createTable();
				newDoc.setTable(k, tableExt);
				// newDoc.setTable(initialTablePos + (k+1), currentTable);
				k++;
				newPage = false;
			}
			XWPFParagraph breakPar = newDoc.createParagraph();
			XWPFRun run = breakPar.createRun();
			run.addBreak();
		}
		return newDoc;
	}
	
	/**
	 * Extracts the table from the input Docx ( template) and copy the table n
	 * times ( n is the size of luceneDocs in input)
	 * 
	 * @param breakBetween
	 *            TODO
	 * @param luceneDocs
	 * 
	 * @return
	 */
	public XWPFDocument generateFromTemplateMapConferenza( Map<String,Integer> group2count) {
		XWPFDocument newDoc = new XWPFDocument();
		List<XWPFTable> tables = document.getTables();
		XWPFTable tableExt = tables.get(0);//template table
		int k = 0;
		/*Copy template paragraphs*/
		List<XWPFParagraph> paragraphs = document.getParagraphs();
		int parCounter=0;
		for (int i = 0; i < paragraphs.size(); i++) {
			XWPFParagraph currentPar = paragraphs.get(i);
			newDoc.createParagraph();
			newDoc.setParagraph(currentPar, parCounter);
			parCounter++;
		}
		String previousKey = StringUtils.EMPTY;
		String currentKey = StringUtils.EMPTY;
		int counter = 0;
		for (String key : group2count.keySet()) {
			if ( group2count.get(key)>0) {
				/*create key title*/
				XWPFParagraph keyTitle = newDoc.createParagraph();
				XWPFRun keyRun = keyTitle.createRun();
				currentKey = key.trim();
				keyRun.setText(key);
				if (!previousKey.equals(key) && counter!=0) {
					keyTitle.setPageBreak(true);
					previousKey = currentKey;
				}
				counter++;
			}
			for (int j = 0; j < group2count.get(key); j++) {
				newDoc.createTable();
				newDoc.setTable(k, tableExt);
				k++;
			}
			XWPFParagraph breakPar = newDoc.createParagraph();
			XWPFRun run = breakPar.createRun();
			run.addBreak();
		}
		return newDoc;
	}

	/**
	 * fill the empty template with the input lucene documents
	 * 
	 * @param luceneDocs
	 * @return
	 */
	public XWPFDocument fillTemplate(List<LuceneMockDocument> luceneDocs,
			boolean onlyValues) {
		List<XWPFTable> tables = document.getTables();
		int tablesNumber = tables.size();
		for (int k = 0; k < tablesNumber; k++) {
			XWPFTable newTable = tables.get(k);
			int numberOfRows = newTable.getNumberOfRows();
			LuceneMockDocument luceneMockDocument = luceneDocs.get(k);
			List<String> fields = luceneMockDocument.getFields();
			List<String> values = luceneMockDocument.getValues();
			XWPFTableRow currentRow;
			String field;
			String value;

			for (int i = 0; i < numberOfRows; i++) {
				field = fields.get(i);
				value = values.get(i);
				currentRow = newTable.getRow(i);
				if (!onlyValues)
					currentRow.getCell(0).setText(field);
				if (currentRow != null && currentRow.getCell(1) != null)
					currentRow.getCell(1).setText(value);
			}
		}
		return document;
	}

	public void insertListInCell(XWPFTableCell currentCell,
			List<String> myStrings) {
		XWPFParagraph para = currentCell.getParagraphs().get(0);
		if(myStrings!=null)
		for (String text : myStrings) {
			XWPFRun run = para.createRun();
			run.setText(text.trim());
			run.addBreak();
		}
	}

	public XWPFDocument generateFromTemplate(int n, int kpage,
			boolean breakBetween) {
		List<XWPFTable> tables = document.getTables();
		XWPFTable tableExt = tables.get(0);
		for (int k = 1; k < n; k++) {
			XWPFParagraph createParagraph = document.createParagraph();
			
			if (k % kpage == 0)
				createParagraph.setPageBreak(true);
				
			if (breakBetween) {
				XWPFRun run = createParagraph.createRun();
				run.addBreak();
			}
			XWPFTable currentTable = document.createTable();
			currentTable = tableExt;
			int initialTablePos = document.getTablePos(0);
			document.setTable(initialTablePos + (k+1), currentTable);
		}

		return document;
	}


}
