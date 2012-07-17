package com.sourcesense.crl.util;

import java.util.Comparator;

import org.primefaces.model.SortOrder;

import com.sourcesense.crl.business.model.Atto;

public class LazySorter implements Comparator<Atto> {

    private String sortField;
   
    private SortOrder sortOrder;
   
    public LazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    public int compare(Atto atto1, Atto atto2) {
        try {
            Object value1 = Atto.class.getField(this.sortField).get(atto1);
            Object value2 = Atto.class.getField(this.sortField).get(atto2);

            int value = ((Comparable)value1).compareTo(value2);
           
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }
}


