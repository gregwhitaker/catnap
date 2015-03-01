package com.catnap.core.query.processor;

import com.catnap.core.query.model.Query;
import com.catnap.core.util.ClassUtil;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author gwhit7
 */
public class NullQueryProcessor extends SortableQueryProcessor
{
    @Override
    public boolean supports(Class<? extends Query<?>> query)
    {
        return (query == null) ? true : false;
    }

    @Override
    public <T> List<Property<T>> processInternal(Query query, T instance, Class<T> instanceClazz)
    {
        List<Property<T>> properties = new ArrayList<Property<T>>();

        //No query specified so we need to return all fields
        for(PropertyDescriptor descriptor : ClassUtil.getReadableProperties(instanceClazz))
        {
            if(!ignoreProperty(descriptor))
            {
                properties.add(new SimpleProperty<T>(instance, descriptor));
            }
        }

        return properties;
    }
}
