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

package com.github.gregwhitaker.catnap.core.model.builder;

import com.github.gregwhitaker.catnap.core.context.CatnapContext;
import com.github.gregwhitaker.catnap.core.model.DefaultListBackedModel;
import com.github.gregwhitaker.catnap.core.model.DefaultMapBackedModel;
import com.github.gregwhitaker.catnap.core.model.ListBackedModel;
import com.github.gregwhitaker.catnap.core.model.MapBackedModel;
import com.github.gregwhitaker.catnap.core.model.Model;
import com.github.gregwhitaker.catnap.core.query.model.Query;
import com.github.gregwhitaker.catnap.core.query.processor.Property;
import com.github.gregwhitaker.catnap.core.query.processor.QueryProcessor;
import com.github.gregwhitaker.catnap.core.query.processor.QueryProcessorFactory;
import com.github.gregwhitaker.catnap.core.util.ClassUtil;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class DefaultModelBuilder implements ModelBuilder {

    @Override
    public Model<?> build(Object instance, CatnapContext context) {
        Model<?> result = null;

        if (instance instanceof Iterable<?>) {
            result = new DefaultListBackedModel();
            buildList((Iterable<?>) instance, context.getQuery(), (ListBackedModel<?>) result, context);
        } else if (instance instanceof Map<?, ?>) {
            result = new DefaultMapBackedModel();
            buildMap((Map<?, ?>) instance, context.getQuery(), (MapBackedModel<?>) result, context);
        } else {
            result = new DefaultMapBackedModel();
            buildObject(instance, context.getQuery(), (MapBackedModel<?>) result, context);
        }

        return result;
    }

    /**
     * @param instance
     * @param query
     * @param result
     * @param context
     */
    private void buildList(Iterable<?> instance, Query query, ListBackedModel<?> result, CatnapContext context) {
        if (instance != null) {
            for (Object item : instance) {
                if (item == null || ClassUtil.isPrimitiveType(item.getClass())) {
                    result.addValue(item);
                } else {
                    //Complex type - We need to go deeper!
                    buildObject(item, query, result.createChildMap(), context);
                }
            }
        }
    }

    /**
     * @param instance
     * @param query
     * @param result
     * @param context
     */
    private void buildMap(Map<?, ?> instance, Query query, MapBackedModel<?> result, CatnapContext context) {
        if (instance != null) {
            for (Map.Entry entry : instance.entrySet()) {
                if (entry.getValue() == null || ClassUtil.isPrimitiveType(entry.getValue().getClass())) {
                    result.addValue(entry.getKey().toString(), entry.getValue());
                } else {
                    buildObject(entry.getValue(), query, result.createChildMap(entry.getKey().toString()), context);
                }
            }
        }
    }

    /**
     * @param instance
     * @param query
     * @param result
     * @param context
     * @param <T>
     */
    private <T> void buildObject(T instance, Query query, MapBackedModel<?> result, CatnapContext context) {
        if (instance != null) {
            Class<T> instanceClazz = ClassUtil.loadClass(instance);
            filterObject(instance, instanceClazz, query, result, context);
        }
    }

    /**
     * @param instance
     * @param instanceClazz
     * @param query
     * @param result
     * @param context
     * @param <T>
     */
    private <T> void filterObject(T instance, Class<T> instanceClazz, Query query, MapBackedModel<?> result, CatnapContext context) {
        QueryProcessor queryProcessor = QueryProcessorFactory.createQueryProcessor(query, instanceClazz);
        List<Property<T>> properties = queryProcessor.process(query, instance, instanceClazz);

        for (Property<T> property : properties) {
            Object value = property.getValue();

            if (value == null) {
                continue;
            }

            String name = property.getName();

            if (property.isPrimitive()) {
                result.addValue(name, value);
            } else {
                //Recursively filtering nested subqueries
                Query subQuery = null;

                if (query != null) {
                    subQuery = query.getSubquery(name);
                }

                if (Iterable.class.isAssignableFrom(value.getClass())) {
                    buildList((Iterable<?>) value, subQuery, result.createChildList(name), context);
                } else if (Map.class.isAssignableFrom(value.getClass())) {
                    buildMap((Map<?, ?>) value, subQuery, result.createChildMap(name), context);
                } else {
                    buildObject(value, subQuery, result.createChildMap(name), context);
                }
            }
        }
    }
}
