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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gregwhitaker.catnap.core.exception.ViewRenderException;
import com.github.gregwhitaker.catnap.core.util.ClassUtil;
import org.apache.commons.lang.StringUtils;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * @param <T>
 */
public class FieldDeclarationComparator<T> implements Comparator<Property<T>>, Serializable {
    private static final long serialVersionUID = -3691113740286842797L;
    private final Map<String, Integer> fieldRanking;

    /**
     * @param instanceClazz
     */
    public FieldDeclarationComparator(Class<T> instanceClazz) {
        fieldRanking = new HashMap<String, Integer>();
        int rank = 0;

        for (PropertyDescriptor descriptor : ClassUtil.getReadableProperties(instanceClazz)) {
            if (descriptor.getReadMethod().isAnnotationPresent(JsonIgnore.class)) {
                JsonIgnore annotation = descriptor.getReadMethod().getAnnotation(JsonIgnore.class);
                if (annotation.value()) {
                    continue;
                }
            }

            //Catnap Support
            if (descriptor.getReadMethod().isAnnotationPresent(JsonProperty.class)) {
                JsonProperty annotation = descriptor.getReadMethod().getAnnotation(JsonProperty.class);

                if (!StringUtils.equalsIgnoreCase(annotation.value(), JsonProperty.USE_DEFAULT_NAME)) {
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
        if (!fieldRanking.containsKey(prop1.getName())) {
            throw new ViewRenderException("Field [" + prop1.getName() + "] cannot be found");
        }

        if (!fieldRanking.containsKey(prop2.getName())) {
            throw new ViewRenderException("Field [" + prop2.getName() + "] cannot be found");
        }

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
}
