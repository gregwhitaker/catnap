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

import com.github.gregwhitaker.catnap.core.annotation.CatnapProperty;
import com.github.gregwhitaker.catnap.core.query.model.Query;
import com.github.gregwhitaker.catnap.core.query.model.SimpleQuery;
import com.github.gregwhitaker.catnap.core.util.ClassUtil;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SimpleQueryProcessor extends QueryProcessor {
    @Override
    public boolean supports(Class<? extends Query<?>> query) {
        return query.isAssignableFrom(SimpleQuery.class);
    }

    @Override
    public <T> List<Property<T>> processInternal(Query query, T instance, Class<T> instanceClazz) {
        List<Property<T>> properties = new ArrayList<Property<T>>();

        //Only return fields specified in the query
        Map<String, PropertyDescriptor> descriptors = new HashMap<String, PropertyDescriptor>(query.getParameterCount());

        for (PropertyDescriptor descriptor : ClassUtil.getReadableProperties(instanceClazz)) {
            //Catnap Support
            if (descriptor.getReadMethod().isAnnotationPresent(CatnapProperty.class)) {
                CatnapProperty annotation = descriptor.getReadMethod().getAnnotation(CatnapProperty.class);

                if (!CatnapProperty.USE_DEFAULT_NAME.equalsIgnoreCase(annotation.value())) {
                    descriptors.put(annotation.value(), descriptor);
                } else {
                    descriptors.put(descriptor.getName(), descriptor);
                }
            } else {
                descriptors.put(descriptor.getName(), descriptor);
            }
        }

        for (String name : (List<String>) query.getParameterNames()) {
            //Only attempt to resolve a queried field to an object property
            //if the object contains a field with a matching name
            if (descriptors.containsKey(name)) {
                if (query.containsExpression(name)) {
                    if (Iterable.class.isAssignableFrom(descriptors.get(name).getPropertyType())) {
                        SimpleProperty iterableProperty = new SimpleProperty(instance, descriptors.get(name));

                        Iterator iter = ((Iterable<?>) iterableProperty.getValue()).iterator();

                        while (iter.hasNext()) {
                            Object item = iter.next();

                            if (!ClassUtil.isPrimitiveType(item.getClass())) {
                                Map<String, PropertyDescriptor> itemDescriptors = ClassUtil.getReadablePropertiesAsMap(item.getClass());
                                PropertyDescriptor itemDescriptor = itemDescriptors.get(query.getExpression(name).getField());

                                //Only process the expression if the field name in the query matches a field
                                //on the item to be queried.
                                if (itemDescriptor != null) {
                                    if (query.getExpression(name).evaluate(new SimpleProperty(item, itemDescriptor)) == false) {
                                        iter.remove();
                                    }
                                }
                            }
                        }

                        properties.add(new SimpleProperty<T>(instance, descriptors.get(name)));
                    } else {
                        Map<String, PropertyDescriptor> itemDescriptors = ClassUtil.getReadablePropertiesAsMap(instanceClazz);
                        PropertyDescriptor itemDescriptor = itemDescriptors.get(query.getExpression(name).getField());

                        if (itemDescriptor != null) {
                            if (query.getExpression(name).evaluate(new SimpleProperty(instance, itemDescriptor)) == true) {
                                properties.add(new SimpleProperty<T>(instance, descriptors.get(name)));
                            }
                        }
                    }
                } else {
                    properties.add(new SimpleProperty<T>(instance, descriptors.get(name)));
                }
            }
        }

        return properties;
    }
}
