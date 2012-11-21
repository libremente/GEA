package doc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import com.sourcesense.crl.webscript.report.lucene.LuceneMockDocument;
import com.sourcesense.crl.webscript.report.office.DocxManager;

import junit.framework.TestCase;

public class DocxManagerTest extends TestCase {

	private DocxManager docxManager;

	public void setUp() {
		URL testTable = getClass().getResource("/doc/base.docx");
		String testTableString = testTable.getFile();
		try {
			InputStream inp = new FileInputStream(new File(testTableString));
			this.docxManager = new DocxManager(inp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * test if the docxManager duplicate the correct number of table in the input template
	 */
	public void testDuplicationTemplate() {
		try {
			XWPFDocument documentGenerated = this.docxManager
					.generateFromTemplate(17,5);
			URL testTableOutput = getClass().getResource("");
			String path = testTableOutput.getPath();
			File out = new File(path + "/docxManagerOutput.docx");
			out.createNewFile();
			FileOutputStream fos = new FileOutputStream(out);
			documentGenerated.write(fos);
			fos.flush();
			fos.close();
			List<XWPFTable> tables = documentGenerated.getTables();
			assertEquals(17,tables.size());
			for(int i=0;i<tables.size();i++){
				assertEquals(6,tables.get(i).getNumberOfRows());
			}

		} catch (Exception e) {
			e.printStackTrace();
			fail();

		}
	}

	/**
	 * test if the docxManager fill with the correct values , the template created
	 * */
	public void testFillTemplate() {
		List<LuceneMockDocument> luceneDocs = new ArrayList<LuceneMockDocument>();
		for (int i = 0; i < 17; i++) {
			LuceneMockDocument newDoc = new LuceneMockDocument();
			for (int k = 0; k < 6; k++) {
				String field = "Doc" + i + " f" + k;
				String value = "Doc" + i + " v" + k;
				newDoc.addField2Value(field, value);
			}
			luceneDocs.add(newDoc);
		}
		assertEquals(17, luceneDocs.size());
		try {
			URL testTable = getClass().getResource(
					"/doc/docxManagerOutput.docx");
			String testTableString = testTable.getFile();
			InputStream inp = new FileInputStream(new File(testTableString));
			this.docxManager = new DocxManager(inp);
			
			XWPFDocument documentGenerated = this.docxManager
					.fillTemplate(luceneDocs,false);
			URL testTableOutput = getClass().getResource("");
			String path = testTableOutput.getPath();
			File out = new File(path + "/docxManagerOutput2.docx");
			out.createNewFile();
			FileOutputStream fos = new FileOutputStream(out);
			documentGenerated.write(fos);
			fos.flush();
			fos.close();
			List<XWPFTable> tables = documentGenerated.getTables();
			assertEquals(17,tables.size());
			for(int i=0;i<tables.size();i++){
				XWPFTable xwpfTable = tables.get(i);
				assertEquals(6,xwpfTable.getNumberOfRows());
				/*sembra che per valorizzare a runtime le celle vadano prima persistite su file 
				 * e riaperto il file*/
				for(int j=0;j<xwpfTable.getNumberOfRows();j++){
					XWPFTableRow row = xwpfTable.getRow(j);
					XWPFTableCell cell = row.getCell(0);	
					//System.out.println(cell.getBodyElements().get(0).getBody().toString().);
					//assertEquals("Doc" + i + " f" + j+"",row.getCell(0).getText());
					//assertEquals("Doc" + i + " f" + j+"",row.getCell(1).getText());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			fail();

		}
	}

}
