package com.sourcesense.crl.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Clonator {
	public static List cloneList(List list) {
		List newList = new ArrayList();
		Iterator it = list.iterator();
		
		while(it.hasNext()) {
			newList.add(ReflectionUtils.cloneObject(it.next()));
		}
		
		return newList;
	}

}
