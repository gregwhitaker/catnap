package com.github.gregwhitaker.catnap.core.query.processor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.gregwhitaker.catnap.core.annotation.CatnapIgnore;
import com.github.gregwhitaker.catnap.core.query.model.Query;

import java.beans.PropertyDescriptor;
import java.util.List;

/**
 *
 */
public abstract class QueryProcessor
{
    /**
     *
     * @param query
     * @return
     */
    public abstract boolean supports(Class<? extends Query<?>> query);

    /**
     *
     * @param query
     * @param instance
     * @param instanceClazz
     * @param <T>
     * @return
     */
    protected abstract <T> List<Property<T>> processInternal(Query query, T instance, Class<T> instanceClazz);

    /**
     *
     * @param query
     * @param instance
     * @param instanceClazz
     * @param <T>
     */
    protected <T> void preProcess(Query query, T instance, Class<T> instanceClazz) {
        //Noop
    }

    /**
     *
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
     *
     * @param properties
     * @param query
     * @param instance
     * @param instanceClazz
     * @param <T>
     */
    protected  <T> void postProcess(List<Property<T>> properties, Query query, T instance, Class<T> instanceClazz) {
        //Noop
    }

    /**
     *
     * @param descriptor
     * @return
     */
    protected boolean ignoreProperty(PropertyDescriptor descriptor) {
        if(descriptor != null && descriptor.getReadMethod() != null) {
            //Catnap Support
            if(descriptor.getReadMethod().isAnnotationPresent(CatnapIgnore.class)) {
                return descriptor.getReadMethod().getAnnotation(CatnapIgnore.class).value();
            }

            //Jackson Support
            if(descriptor.getReadMethod().isAnnotationPresent(JsonIgnore.class)) {
                return descriptor.getReadMethod().getAnnotation(JsonIgnore.class).value();
            }

            return false;
        }

        return true;
    }
}
