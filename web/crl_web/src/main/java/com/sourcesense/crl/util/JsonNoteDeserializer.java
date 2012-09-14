package com.sourcesense.crl.util;

import java.io.IOException;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

public class JsonNoteDeserializer extends JsonDeserializer<String>{

	@Override
	public String deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		
		// TODO Auto-generated method stub
		String value ="";
		
		if(jp.getText()!=null){
		  value = jp.getText().replaceAll("\"","\"");	
		}
		
		return value;
		
	}

}
