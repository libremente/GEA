package com.sourcesense.crl.util;

import java.io.IOException;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

public class JsonIntegerDeserializer extends JsonDeserializer<Integer>{

	@Override
	public Integer deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		
		// TODO Auto-generated method stub
		Integer value =0;
		
		if(jp.getText()!=null && jp.getTextLength() > 0){
		  
			if (jp.getText().contains(",")){
			
				value = Integer.valueOf(jp.getText().replace(",",""));
			}
			else if (jp.getText().contains(".")){
			
				value = Integer.valueOf(jp.getText().replace(".",""));
			}
			else{
			
				value = Integer.valueOf(jp.getText());
			}
		}
		
		return value;
		
	}

}
