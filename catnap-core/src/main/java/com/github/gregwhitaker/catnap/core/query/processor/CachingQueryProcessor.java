package com.github.gregwhitaker.catnap.core.query.processor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gregwhitaker.catnap.core.query.model.CatnapQuery;
import com.github.gregwhitaker.catnap.core.query.model.Query;
import com.github.gregwhitaker.catnap.core.util.ClassUtil;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CachingQueryProcessor extends QueryProcessor {
    private Map<Class<?>, List<PropertyDescriptor>> PROPERTIES_CACHE = new ConcurrentHashMap<>();
    private Map<Class<?>, Map<String, Field>> FIELDS_CACHE = new ConcurrentHashMap<>();
    private Map<Class<?>, Map<String, PropertyDescriptor>> PROPERTIES_AS_MAP_CACHE = new ConcurrentHashMap<>();

    @Override
    public boolean supports(Class<? extends Query<?>> query) {
        return query.isAssignableFrom(CatnapQuery.class);
    }

    @Override
    public <T> List<Property<T>> processInternal(Query query, T instance, Class<T> instanceClazz) {
        List<Property<T>> properties = new ArrayList<>();
        Map<String, PropertyDescriptor> descriptors = new HashMap<>(query.getParameterCount());
        List<PropertyDescriptor> readableProperties = getReadableProperties(instanceClazz);
        Map<String, Field> fields = getFields(instanceClazz);
        for (PropertyDescriptor descriptor : readableProperties) {
            String name = descriptor.getName();
            if (super.ignoreProperty(descriptor) || super.ignoreFiled(fields.get(name))) {
                continue;
            }
            if (descriptor.getReadMethod().isAnnotationPresent(JsonProperty.class)) {
                JsonProperty annotation = descriptor.getReadMethod().getAnnotation(JsonProperty.class);
                if (!JsonProperty.USE_DEFAULT_NAME.equalsIgnoreCase(annotation.value())) {
                    descriptors.put(annotation.value(), descriptor);
                } else {
                    descriptors.put(name, descriptor);
                }
            } else {
                descriptors.put(name, descriptor);
            }
        }
        for (String name : (List<String>) query.getParameterNames()) {
            //Only attempt to resolve a queried field to an object property
            //if the object contains a field with a matching name
            if (descriptors.containsKey(name)) {
                if (query.containsExpression(name)) {
                    if (Iterable.class.isAssignableFrom(descriptors.get(name).getPropertyType())) {
                        CatnapProperty iterableProperty = new CatnapProperty(instance, descriptors.get(name));
                        Iterator iter = ((Iterable<?>) iterableProperty.getValue()).iterator();
                        while (iter.hasNext()) {
                            Object item = iter.next();
                            if (!ClassUtil.isPrimitiveType(item.getClass())) {
                                Map<String, PropertyDescriptor> itemDescriptors = this.getReadablePropertiesAsMap(item.getClass());
                                PropertyDescriptor itemDescriptor = itemDescriptors.get(query.getExpression(name).getField());
                                //Only process the expression if the field name in the query matches a field
                                //on the item to be queried.
                                if (itemDescriptor != null) {
                                    if (query.getExpression(name).evaluate(new CatnapProperty(item, itemDescriptor)) == false) {
                                        iter.remove();
                                    }
                                }
                            }
                        }
                        properties.add(iterableProperty);
                    } else {
                        Map<String, PropertyDescriptor> itemDescriptors = this.getReadablePropertiesAsMap(instanceClazz);
                        PropertyDescriptor itemDescriptor = itemDescriptors.get(query.getExpression(name).getField());
                        if (itemDescriptor != null) {
                            if (query.getExpression(name).evaluate(new CatnapProperty(instance, itemDescriptor)) == true) {
                                properties.add(new CatnapProperty<T>(instance, descriptors.get(name)));
                            }
                        }
                    }
                } else {
                    properties.add(new CatnapProperty<>(instance, descriptors.get(name)));
                }
            }
        }

        return properties;
    }

    private <T> Map<String, Field> getFields(Class<T> instanceClazz) {
        if (!FIELDS_CACHE.containsKey(instanceClazz)) {
            FIELDS_CACHE.put(instanceClazz, Arrays.stream(instanceClazz.getDeclaredFields()).collect(Collectors.toMap(Field::getName, Function.identity())));
        }
        return FIELDS_CACHE.get(instanceClazz);
    }

    private <T> Map<String, PropertyDescriptor> getReadablePropertiesAsMap(Class<T> instanceClazz) {
        if (!PROPERTIES_AS_MAP_CACHE.containsKey(instanceClazz)) {
            PROPERTIES_AS_MAP_CACHE.put(instanceClazz, ClassUtil.getReadablePropertiesAsMap(instanceClazz));
        }
        return PROPERTIES_AS_MAP_CACHE.get(instanceClazz);
    }

    private <T> List<PropertyDescriptor> getReadableProperties(Class<T> instanceClazz) {
        if (!PROPERTIES_CACHE.containsKey(instanceClazz)) {
            PROPERTIES_CACHE.put(instanceClazz, ClassUtil.getReadableProperties(instanceClazz));
        }
        return PROPERTIES_CACHE.get(instanceClazz);
    }
}