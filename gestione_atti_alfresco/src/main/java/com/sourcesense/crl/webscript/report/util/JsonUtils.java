/*
 *  * #%L
 *  * Alfresco Repository
 *  * %%
 *  * Copyright (C) 2005 - 2016 Alfresco Software Limited
 *  * %%
 *  * This file is part of the Alfresco software. 
 *  * If the software was purchased under a paid Alfresco license, the terms of 
 *  * the paid license agreement will prevail.  Otherwise, the software is 
 *  * provided under the following open source license terms:
 *  * 
 *  * Alfresco is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU Lesser General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  * 
 *  * Alfresco is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU Lesser General Public License for more details.
 *  * 
 *  * You should have received a copy of the GNU Lesser General Public License
 *  * along with Alfresco. If not, see <http://www.gnu.org/licenses/>.
 *  * #L%
 */
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
