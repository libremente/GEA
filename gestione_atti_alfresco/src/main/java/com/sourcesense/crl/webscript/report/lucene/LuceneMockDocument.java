package com.sourcesense.crl.webscript.report.lucene;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a Lucene Mock Document with:
 * - Ordered list of String fields
 * - Ordered list of String values
 * @author Alessandro Benedetti
 *
 */
public class LuceneMockDocument {
	
	private List<String> fields;
	private List<String> values;
	
	public LuceneMockDocument(){
		this.fields=new ArrayList<String>();
		this.values=new ArrayList<String>();
	}
	
	public List<String> getFields() {
		return fields;
	}
	public void setFields(List<String> fields) {
		this.fields = fields;
	}
	
	public List<String> getValues() {
		return values;
	}
	public void setValues(List<String> values) {
		this.values = values;
	}
	
	public void addField2Value(String field,String value) {
		this.fields.add(field);
		this.values.add(value);	
		}
	
	

}
