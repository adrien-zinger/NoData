package com.android.osloh.nodata.ui.utils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Charles on 13/10/2015.
 */
public class Filter {

    public interface Predicate<T> { boolean apply(T type); }

    public static <T> Collection<T> filter(Collection<T> col, Predicate<T> predicate) {
        Collection<T> result = new ArrayList<T>();
        for (T element: col) {
            if (predicate.apply(element)) {
                result.add(element);
            }
        }
        return result;
    }
}
