/*
 * Copyright 2016 Greg Whitaker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.gregwhitaker.catnap.core.query.processor;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.gregwhitaker.catnap.core.exception.CatnapException;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotationComparator<T> implements Comparator<Property<T>>, Serializable {
    private static final long serialVersionUID = -6376402718949965299L;
    private final Map<String, Integer> fieldRanking;

    public AnnotationComparator(Class<T> instanceClazz) {
        fieldRanking = new HashMap<String, Integer>();
        List<String> clazzFields = classFields(instanceClazz);
        List<String> annotationFields = annotationFields(instanceClazz);

        for (int rank = 0; rank < annotationFields.size(); rank++) {
            fieldRanking.put(annotationFields.get(rank), rank);
        }

        int rank = 0;

        for (String field : annotationFields) {
            fieldRanking.put(field, rank);
            rank++;
        }

        //Add any fields not specified by the annotation
        //at the end of the defined fields
        clazzFields.removeAll(annotationFields);

        //Orphan fields are alphabetized by default in the
        //annotation, but that can be disabled.  In which case,
        //orphaned field ordering is indeterminate.
        if (alphabetizeOrphans(instanceClazz)) {
            Collections.sort(clazzFields);
        }

        for (String field : clazzFields) {
            fieldRanking.put(field, rank);
            rank++;
        }
    }

    @Override
    public int compare(Property<T> prop1, Property<T> prop2) {
        int prop1Rank = fieldRanking.get(prop1.getName());
        int prop2Rank = fieldRanking.get(prop2.getName());

        if (prop1Rank < prop2Rank) {
            return -1;
        } else if (prop1Rank == prop2Rank) {
            return 0;
        } else {
            return 1;
        }
    }

    private List<String> classFields(Class<T> instanceClazz) {
        Field[] fields = instanceClazz.getDeclaredFields();
        List<String> fieldNames = new ArrayList<>(fields.length);

        for (Field field : fields) {
            fieldNames.add(field.getName());
        }

        return fieldNames;
    }

    private List<String> annotationFields(Class<T> instanceClazz) {
        if (instanceClazz.isAnnotationPresent(JsonPropertyOrder.class)) {
            return Arrays.asList(instanceClazz.getAnnotation(JsonPropertyOrder.class).value());
        }

        throw new CatnapException("Missing CatnapOrder or JsonPropertyOrder annotation");
    }

    private boolean alphabetizeOrphans(Class<T> instanceClazz) {
        if (instanceClazz.isAnnotationPresent(JsonPropertyOrder.class)) {
            return instanceClazz.getAnnotation(JsonPropertyOrder.class).alphabetic();
        }

        throw new CatnapException("Missing CatnapOrder or JsonPropertyOrder annotation");
    }
}
