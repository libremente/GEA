package com.sourcesense.crl.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


public class CloningLists {
    
   
    public static <T extends Cloneable> List<T> cloneMe(List<T> list) {
        final List<T> list2;
        if (list instanceof Cloneable) {
            list2 = forceClone(list);
        } else {
            list2 = new ArrayList<T>();
        }

        list2.clear();
        for (T t : list) {
            list2.add(forceClone(t));
        }
        return list2;
    }
    

    private static <T> T forceClone(T o) {
        final Class<?> klass = o.getClass();

        final Method cloneMethod;
        try {
            cloneMethod = klass.getMethod("clone");
        } catch (NoSuchMethodException e) {
            throw new AssertionError(e);
        }

        cloneMethod.setAccessible(true);
        try {
            @SuppressWarnings("unchecked")
            final T result = (T) cloneMethod.invoke(o);
            return result;
        } catch (ClassCastException e) {
            throw new AssertionError(e);
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

  
}
