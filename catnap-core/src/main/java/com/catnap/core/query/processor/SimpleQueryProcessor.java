package com.catnap.core.query.processor;

import com.catnap.core.annotation.CatnapProperty;
import com.catnap.core.query.model.Query;
import com.catnap.core.query.model.SimpleQuery;
import com.catnap.core.util.ClassUtil;

import java.beans.PropertyDescriptor;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author gwhit7
 */
public class SimpleQueryProcessor extends QueryProcessor
{
    @Override
    public boolean supports(Class<? extends Query<?>> query)
    {
        return query.isAssignableFrom(SimpleQuery.class);
    }

    @Override
    public <T> List<Property<T>> processInternal(Query query, T instance, Class<T> instanceClazz)
    {
        List<Property<T>> properties = new ArrayList<Property<T>>();

        //Only return fields specified in the query
        Map<String, PropertyDescriptor> descriptors = new HashMap<String, PropertyDescriptor>(query.getParameterCount());

        for(PropertyDescriptor descriptor : ClassUtil.getReadableProperties(instanceClazz))
        {
            //Catnap Support
            if(descriptor.getReadMethod().isAnnotationPresent(CatnapProperty.class))
            {
                CatnapProperty annotation = descriptor.getReadMethod().getAnnotation(CatnapProperty.class);

                if(!CatnapProperty.USE_DEFAULT_NAME.equalsIgnoreCase(annotation.value()))
                {
                    descriptors.put(annotation.value(), descriptor);
                }
                else
                {
                    descriptors.put(descriptor.getName(), descriptor);
                }
            }
            else
            {
                descriptors.put(descriptor.getName(), descriptor);
            }
        }

        for(String name : (List<String>) query.getParameterNames())
        {
            //Only attempt to resolve a queried field to an object property
            //if the object contains a field with a matching name
            if(descriptors.containsKey(name))
            {
                if(query.containsExpression(name))
                {
                    if(Iterable.class.isAssignableFrom(descriptors.get(name).getPropertyType()))
                    {
                        SimpleProperty iterableProperty = new SimpleProperty(instance, descriptors.get(name));

                        Iterator iter = ((Iterable<?>) iterableProperty.getValue()).iterator();

                        while(iter.hasNext())
                        {
                            Object item = iter.next();

                            if(!ClassUtil.isPrimitiveType(item.getClass()))
                            {
                                Map<String, PropertyDescriptor> itemDescriptors = ClassUtil.getReadablePropertiesAsMap(item.getClass());
                                PropertyDescriptor itemDescriptor = itemDescriptors.get(query.getExpression(name).getField());

                                //Only process the expression if the field name in the query matches a field
                                //on the item to be queried.
                                if(itemDescriptor != null)
                                {
                                    if(query.getExpression(name).evaluate(new SimpleProperty(item, itemDescriptor)) == false)
                                    {
                                        iter.remove();
                                    }
                                }
                            }
                        }

                        properties.add(new SimpleProperty<T>(instance, descriptors.get(name)));
                    }
                    else
                    {
                        Map<String, PropertyDescriptor> itemDescriptors = ClassUtil.getReadablePropertiesAsMap(instanceClazz);
                        PropertyDescriptor itemDescriptor = itemDescriptors.get(query.getExpression(name).getField());

                        if(itemDescriptor != null)
                        {
                            if(query.getExpression(name).evaluate(new SimpleProperty(instance, itemDescriptor)) == true)
                            {
                                properties.add(new SimpleProperty<T>(instance, descriptors.get(name)));
                            }
                        }
                    }
                }
                else
                {
                    properties.add(new SimpleProperty<T>(instance, descriptors.get(name)));
                }
            }
        }

        return properties;
    }
}
