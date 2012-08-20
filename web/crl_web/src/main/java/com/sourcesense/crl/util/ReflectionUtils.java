package com.sourcesense.crl.util;

import java.lang.reflect.Method;

public class ReflectionUtils {

	public static Object cloneObject(Object target) {
		Object ritorno = new Object();
		Method metodo = null;
		try {
			metodo = target.getClass().getMethod("clone", null);
		}
		catch (NoSuchMethodException exc) { }
		if (metodo != null)
			try {
				ritorno = metodo.invoke(target, new Object[0]);
			}
		catch (Exception ecc) { }
		return ritorno;
	}
}
