package com.sourcesense.crl.webscript;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;

public class TemplateFiller {

	
	 public static byte[] searchAndReplace(byte[] documentByteArray , HashMap<String, String> replacements) throws IOException {
		  		
			HWPFDocument document = new HWPFDocument(new ByteArrayInputStream(documentByteArray));

            // Range and Paragraph of Document
            Range docRange = document.getRange();
			int numParagraphs = docRange.numParagraphs();

			// Recover a Set of the keys in the HashMap
			Set<String> keySet = replacements.keySet();
			
			Paragraph paragraph = null;
			CharacterRun charRun = null;
			Iterator<String> keySetIterator = null;
			int numCharRuns = 0;
			String text = null;
			String key = null;
			String value = null;
		
			// Step through each Paragraph
			for(int i = 0; i < numParagraphs; i++) {
				paragraph = docRange.getParagraph(i);
				
				// Get the number of CharacterRuns in the Paragraph
				numCharRuns = paragraph.numCharacterRuns();
				
				for(int j = 0; j < numCharRuns; j++) {
					
					charRun = paragraph.getCharacterRun(j);
					
					// Get the text from the CharacterRun 
					text = charRun.text();
					
					// KeySet Iterator
					keySetIterator = keySet.iterator();
					while(keySetIterator.hasNext()) {
		
						// check the key in CharacterRuns text
					    key = keySetIterator.next();
					    if(text.contains(key)) {
					    	
					    	// replace term
					        value = replacements.get(key);
					        int start = text.indexOf(key);
					        charRun.replaceText(key, value, start);

					    }
					}
				}
			}

			ByteArrayOutputStream ostream = new ByteArrayOutputStream();
			document.write(ostream);
			return ostream.toByteArray();

	 }

}
