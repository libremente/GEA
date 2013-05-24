package com.sourcesense.crl.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.springframework.stereotype.Component;



	
	@Component
	public class JsonHourSerializer extends JsonSerializer<Date>{

		private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

		@Override
		public void serialize(Date date, JsonGenerator gen, SerializerProvider provider)
				throws IOException, JsonProcessingException {

			if(date!=null) {
				String formattedDate = dateFormat.format(date);
				

				gen.writeString(formattedDate);
			}
			else {
				gen.writeString("");
			}
		}

	}
	
