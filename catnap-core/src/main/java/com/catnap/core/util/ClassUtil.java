package com.catnap.core.util;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author gwhit7
 */
public class ClassUtil
{
    private ClassUtil()
    {
        //Hiding default constructor
    }

    public static Iterable<Field> getFieldsUpTo(Class<?> startClass, Class<?> exclusiveParent)
    {
        List<Field> currentClassFields = new ArrayList<Field>(Arrays.asList(startClass.getDeclaredFields()));
        Class<?> parentClass = startClass.getSuperclass();

        if (parentClass != null && (exclusiveParent == null || !(parentClass.equals(exclusiveParent))))
        {
            List<Field> parentClassFields = (List<Field>) getFieldsUpTo(parentClass, exclusiveParent);
            currentClassFields.addAll(parentClassFields);
        }

        return currentClassFields;
    }

    public static boolean isPrimitiveType(Class<?> clazz)
    {
        if(clazz != null)
        {
            return clazz.isPrimitive() ||
                   clazz.isEnum() ||
                   Number.class.isAssignableFrom(clazz) ||
                   String.class.isAssignableFrom(clazz) ||
                   Boolean.class.isAssignableFrom(clazz) ||
                   Character.class.isAssignableFrom(clazz) ||
                   Date.class.isAssignableFrom(clazz);
        }

        return false;
    }

    public static List<PropertyDescriptor> getReadableProperties(Class<?> instanceType)
    {
        List<PropertyDescriptor> result = new ArrayList<PropertyDescriptor>();
        for(PropertyDescriptor descriptor : PropertyUtils.getPropertyDescriptors(instanceType))
        {
            if(descriptor.getReadMethod() != null && !descriptor.getName().equals("class"))
            {
                result.add(descriptor);
            }
        }

        return result;
    }

    public static Map<String, PropertyDescriptor> getReadablePropertiesAsMap(Class<?> instanceType)
    {
        Map<String, PropertyDescriptor> result = new HashMap<String, PropertyDescriptor>();
        for(PropertyDescriptor descriptor : PropertyUtils.getPropertyDescriptors(instanceType))
        {
            if(descriptor.getReadMethod() != null && !descriptor.getName().equals("class"))
            {
                result.put(descriptor.getName(), descriptor);
            }
        }

        return result;
    }

    public static PropertyDescriptor getReadableProperty(String name, Class<?> instanceType)
    {
        for(PropertyDescriptor descriptor : getReadableProperties(instanceType))
        {
            if(descriptor.getName().equals(name) && descriptor.getReadMethod() != null && !descriptor.getName().equals("class"))
            {
                return descriptor;
            }
        }

        return null;
    }

    public static <T> Class<T> loadClass(T instance)
    {
        if(instance != null)
        {
            return (Class<T>) instance.getClass();
        }

        return null;
    }
}
