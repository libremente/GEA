package com.sourcesense.crl.webscript.report.office;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import com.sourcesense.crl.webscript.report.lucene.LuceneMockDocument;

/**
 * This class represents a Docx Manager to replicate and fill simple document with table in input
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
	 * @param luceneDocs
	 * @return
	 */
	public XWPFDocument generateFromTemplate(int n,int kpage) {
		List<XWPFTable> tables = document.getTables();
		XWPFTable tableExt = tables.get(0);
		for (int k = 1; k < n; k++) {
			XWPFParagraph createParagraph = document.createParagraph();
			if(k%kpage==0)
				createParagraph.setPageBreak(true);
			XWPFRun run = createParagraph.createRun();
			run.addBreak();
			XWPFTable currentTable = document.createTable();
			currentTable = tableExt;
			document.setTable(document.getTablePos(0) + k, currentTable);
		}
	
	return document;
	}
	
	/**
	 * fill the empty template with the input lucene documents
	 * @param luceneDocs
	 * @return
	 */
	public XWPFDocument fillTemplate(List<LuceneMockDocument> luceneDocs,boolean onlyValues){
		List<XWPFTable> tables = document.getTables();
		for (int k = 0; k < luceneDocs.size(); k++) {
			XWPFTable newTable = tables.get(k);
			LuceneMockDocument luceneMockDocument = luceneDocs.get(k);
			List<String> fields = luceneMockDocument.getFields();
			List<String> values = luceneMockDocument.getValues();
			XWPFTableRow currentRow;
			String field;
			String value;
			for (int i = 0; i < fields.size(); i++) {
				field = fields.get(i);
				value = values.get(i);
				currentRow = newTable.getRow(i);
				if(!onlyValues)
					currentRow.getCell(0).setText(field);
				currentRow.getCell(1).setText(value);
			}
		}
		return document;
	}
	
}
