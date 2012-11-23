package com.sourcesense.crl.webscript.report.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {
	/**
	 * return a simple element for the current JsonObj and elementname
	 * 
	 * @param root
	 * @param arrayName
	 * @return
	 * @throws JSONException
	 */
	public static String retieveElementFromJson(JSONObject root, String elementName)
			throws JSONException {
		String elementValue = root.getString(elementName);
		return elementValue;
	}

	/**
	 * return the ArrayList of elements for the current JsonObj and arrayName
	 * 
	 * @param root
	 * @param arrayName
	 * @return
	 * @throws JSONException
	 */
	public static List<String> retieveArrayListFromJson(JSONObject root,
			String arrayName) throws JSONException {
		JSONArray jsonArray = root.getJSONArray(arrayName);
		List<String> tipiAttoList = new ArrayList<String>();
		for (int i = 0; i < jsonArray.length(); i++) {
			String jsonElement = jsonArray.getString(i).trim();
			if (!jsonElement.equals("")&&jsonElement.contains(":\""))
				tipiAttoList.add(extractJsonSingleValue(jsonElement));
			else
				tipiAttoList.add(jsonElement);
		}
		return tipiAttoList;
	}

	/**
	 * Returns the value for this single json element in this form :
	 * "tipoAtto":"PDL"
	 * 
	 * @param tipoAtto
	 * @return
	 */
	public static String extractJsonSingleValue(String jsonElement) {
		int indexFieldValueSeparator = jsonElement.indexOf(":");
		int indexValueEnd = jsonElement.lastIndexOf("\"");
		return jsonElement.substring(indexFieldValueSeparator + 2,
				indexValueEnd);
	}
}
