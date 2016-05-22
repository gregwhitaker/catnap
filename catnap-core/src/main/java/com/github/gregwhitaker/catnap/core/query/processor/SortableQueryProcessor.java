package com.github.gregwhitaker.catnap.core.query.processor;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.gregwhitaker.catnap.core.annotation.CatnapOrder;
import com.github.gregwhitaker.catnap.core.query.model.Query;

import java.util.Collections;
import java.util.List;

/**
 *
 */
public abstract class SortableQueryProcessor extends QueryProcessor {

    private enum SortMethod {
        FIELD_DECLARATION,
        ANNOTATION,
        ALPHABETICAL
    }

    @Override
    protected <T> void postProcess(List<Property<T>> properties, Query query, T instance, Class<T> instanceClazz) {
        super.postProcess(properties, query, instance, instanceClazz);

        switch(sortMethod(instanceClazz)) {
            case FIELD_DECLARATION:
                Collections.sort(properties, new FieldDeclarationComparator<T>(instanceClazz));
                break;
            case ANNOTATION:
                Collections.sort(properties, new AnnotationComparator<T>(instanceClazz));
                break;
            case ALPHABETICAL:
                //Default returned from process is alphabetical
                break;
            default:
                Collections.sort(properties, new FieldDeclarationComparator<T>(instanceClazz));
                break;
        }
    }

    private <T> SortMethod sortMethod(Class<T> instanceClazz) {
        if(instanceClazz != null) {
            //Catnap Support
            if(instanceClazz.isAnnotationPresent(CatnapOrder.class)) {
                String[] value = instanceClazz.getAnnotation(CatnapOrder.class).value();
                return (value != null && value.length > 0) ? SortMethod.ANNOTATION : SortMethod.ALPHABETICAL;
            }

            //Jackson Support
            if(instanceClazz.isAnnotationPresent(JsonPropertyOrder.class)) {
                String[] value = instanceClazz.getAnnotation(JsonPropertyOrder.class).value();
                return (value != null && value.length > 0) ? SortMethod.ANNOTATION : SortMethod.ALPHABETICAL;
            }
        }

        return SortMethod.FIELD_DECLARATION;
    }
}
