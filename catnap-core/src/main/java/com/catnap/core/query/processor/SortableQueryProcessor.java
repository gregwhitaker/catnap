package com.catnap.core.query.processor;

import com.catnap.core.annotation.CatnapOrder;
import com.catnap.core.query.model.Query;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author gwhit7
 */
public abstract class SortableQueryProcessor extends QueryProcessor
{
    private enum SortMethod
    {
        FIELD_DECLARATION,
        ANNOTATION,
        ALPHABETICAL
    }

    @Override
    protected <T> void postProcess(List<Property<T>> properties, Query query, T instance, Class<T> instanceClazz)
    {
        super.postProcess(properties, query, instance, instanceClazz);

        switch(sortMethod(instanceClazz))
        {
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

    private <T> SortMethod sortMethod(Class<T> instanceClazz)
    {
        if(instanceClazz != null)
        {
            //Catnap Support
            if(instanceClazz.isAnnotationPresent(CatnapOrder.class))
            {
                String[] value = instanceClazz.getAnnotation(CatnapOrder.class).value();

                return (value != null && value.length > 0) ? SortMethod.ANNOTATION : SortMethod.ALPHABETICAL;
            }

            //Jackson Support
            if(instanceClazz.isAnnotationPresent(JsonPropertyOrder.class))
            {
                String[] value = instanceClazz.getAnnotation(JsonPropertyOrder.class).value();

                return (value != null && value.length > 0) ? SortMethod.ANNOTATION : SortMethod.ALPHABETICAL;
            }
        }

        return SortMethod.FIELD_DECLARATION;
    }
}
