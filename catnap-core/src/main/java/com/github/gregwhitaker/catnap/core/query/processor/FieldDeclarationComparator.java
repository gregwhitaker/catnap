package com.github.gregwhitaker.catnap.core.query.processor;

import com.github.gregwhitaker.catnap.core.annotation.CatnapIgnore;
import com.github.gregwhitaker.catnap.core.annotation.CatnapProperty;
import com.github.gregwhitaker.catnap.core.exception.ViewRenderException;
import com.github.gregwhitaker.catnap.core.util.ClassUtil;
import org.apache.commons.lang.StringUtils;

import java.beans.PropertyDescriptor;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @param <T>
 */
public class FieldDeclarationComparator<T> implements Comparator<Property<T>> {
    private final Map<String, Integer> fieldRanking;

    /**
     *
     * @param instanceClazz
     */
    public FieldDeclarationComparator(Class<T> instanceClazz) {
        fieldRanking = new HashMap<String, Integer>();
        int rank = 0;

        for (PropertyDescriptor descriptor : ClassUtil.getReadableProperties(instanceClazz)) {
            if (descriptor.getReadMethod().isAnnotationPresent(CatnapIgnore.class)) {
                CatnapIgnore annotation = descriptor.getReadMethod().getAnnotation(CatnapIgnore.class);
                if (annotation.value()) {
                    continue;
                }
            }

            //Catnap Support
            if (descriptor.getReadMethod().isAnnotationPresent(CatnapProperty.class)) {
                CatnapProperty annotation = descriptor.getReadMethod().getAnnotation(CatnapProperty.class);

                if (!StringUtils.equalsIgnoreCase(annotation.value(), CatnapProperty.USE_DEFAULT_NAME)) {
                    fieldRanking.put(annotation.value(), rank);
                } else {
                    fieldRanking.put(descriptor.getName(), rank);
                }
            } else {
                fieldRanking.put(descriptor.getName(), rank);
            }

            rank++;
        }
    }

    @Override
    public int compare(Property<T> prop1, Property<T> prop2) {
        if(!fieldRanking.containsKey(prop1.getName())) {
            throw new ViewRenderException("Field [" + prop1.getName() + "] cannot be found");
        }

        if(!fieldRanking.containsKey(prop2.getName())) {
            throw new ViewRenderException("Field [" + prop2.getName() + "] cannot be found");
        }

        int prop1Rank = fieldRanking.get(prop1.getName());
        int prop2Rank = fieldRanking.get(prop2.getName());

        if(prop1Rank < prop2Rank) {
            return -1;
        } else if(prop1Rank == prop2Rank) {
            return 0;
        } else {
            return 1;
        }
    }
}
