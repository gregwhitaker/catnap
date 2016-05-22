package com.github.gregwhitaker.catnap.core.query.processor;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.gregwhitaker.catnap.core.annotation.CatnapOrder;
import com.github.gregwhitaker.catnap.core.exception.CatnapException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotationComparator<T> implements Comparator<Property<T>> {
    private final Map<String, Integer> fieldRanking;

    public AnnotationComparator(Class<T> instanceClazz) {
        fieldRanking = new HashMap<String, Integer>();
        List<String> clazzFields = classFields(instanceClazz);
        List<String> annotationFields = annotationFields(instanceClazz);

        for(int rank = 0; rank < annotationFields.size(); rank++) {
            fieldRanking.put(annotationFields.get(rank), rank);
        }

        int rank = 0;

        for(String field : annotationFields) {
            fieldRanking.put(field, rank);
            rank++;
        }

        //Add any fields not specified by the annotation
        //at the end of the defined fields
        clazzFields.removeAll(annotationFields);

        //Orphan fields are alphabetized by default in the
        //annotation, but that can be disabled.  In which case,
        //oprhaned field ordering is indeterminate.
        if(alphabetizeOrphans(instanceClazz)) {
            Collections.sort(clazzFields);
        }

        for(String field : clazzFields) {
            fieldRanking.put(field, rank);
            rank++;
        }
    }

    @Override
    public int compare(Property<T> prop1, Property<T> prop2) {
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

    private List<String> classFields(Class<T> instanceClazz) {
        Field[] fields = instanceClazz.getDeclaredFields();
        List<String> fieldNames = new ArrayList<>(fields.length);

        for(Field field : fields) {
            fieldNames.add(field.getName());
        }

        return fieldNames;
    }

    private List<String> annotationFields(Class<T> instanceClazz) {
        if(instanceClazz.isAnnotationPresent(CatnapOrder.class)) {
            return Arrays.asList(instanceClazz.getAnnotation(CatnapOrder.class).value());
        }

        if(instanceClazz.isAnnotationPresent(JsonPropertyOrder.class)) {
            return Arrays.asList(instanceClazz.getAnnotation(JsonPropertyOrder.class).value());
        }

        throw new CatnapException("Missing CatnapOrder or JsonPropertyOrder annotation");
    }

    private boolean alphabetizeOrphans(Class<T> instanceClazz) {
        if(instanceClazz.isAnnotationPresent(CatnapOrder.class)) {
            return instanceClazz.getAnnotation(CatnapOrder.class).alphabetic();
        }

        if(instanceClazz.isAnnotationPresent(JsonPropertyOrder.class)) {
            return instanceClazz.getAnnotation(JsonPropertyOrder.class).alphabetic();
        }

        throw new CatnapException("Missing CatnapOrder or JsonPropertyOrder annotation");
    }
}
