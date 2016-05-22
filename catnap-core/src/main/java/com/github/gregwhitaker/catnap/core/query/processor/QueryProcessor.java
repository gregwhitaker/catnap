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
import com.github.gregwhitaker.catnap.core.annotation.CatnapIgnore;
import com.github.gregwhitaker.catnap.core.query.model.Query;

import java.beans.PropertyDescriptor;
import java.util.List;

/**
 *
 */
public abstract class QueryProcessor {

    /**
     * @param query
     * @return
     */
    public abstract boolean supports(Class<? extends Query<?>> query);

    /**
     * @param query
     * @param instance
     * @param instanceClazz
     * @param <T>
     * @return
     */
    protected abstract <T> List<Property<T>> processInternal(Query query, T instance, Class<T> instanceClazz);

    /**
     * @param query
     * @param instance
     * @param instanceClazz
     * @param <T>
     */
    protected <T> void preProcess(Query query, T instance, Class<T> instanceClazz) {
        //Noop
    }

    /**
     * @param query
     * @param instance
     * @param instanceClazz
     * @param <T>
     * @return
     */
    public <T> List<Property<T>> process(Query query, T instance, Class<T> instanceClazz) {
        preProcess(query, instance, instanceClazz);
        List<Property<T>> properties = processInternal(query, instance, instanceClazz);
        postProcess(properties, query, instance, instanceClazz);
        return properties;
    }

    /**
     * @param properties
     * @param query
     * @param instance
     * @param instanceClazz
     * @param <T>
     */
    protected <T> void postProcess(List<Property<T>> properties, Query query, T instance, Class<T> instanceClazz) {
        //Noop
    }

    /**
     * @param descriptor
     * @return
     */
    protected boolean ignoreProperty(PropertyDescriptor descriptor) {
        if (descriptor != null && descriptor.getReadMethod() != null) {
            //Catnap Support
            if (descriptor.getReadMethod().isAnnotationPresent(CatnapIgnore.class)) {
                return descriptor.getReadMethod().getAnnotation(CatnapIgnore.class).value();
            }

            //Jackson Support
            if (descriptor.getReadMethod().isAnnotationPresent(JsonIgnore.class)) {
                return descriptor.getReadMethod().getAnnotation(JsonIgnore.class).value();
            }

            return false;
        }

        return true;
    }
}
