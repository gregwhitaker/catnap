package com.catnap.core.query.processor;

import java.lang.reflect.Method;

/**
 * Interface used by Catnap during introspection to represent a field
 * and it's associated "getter" method.
 *
 * @author gwhit7
 */
public interface Property<T>
{
    /**
     * @return field name of the property
     */
    public String getName();

    /**
     * @return value returned when calling the property's "getter"
     */
    public Object getValue();

    /**
     * @return property's "getter" method
     */
    Method getReadMethod();

    /**
     * @return 'true' if the return type of the property's getter method is a primitive type
     */
    boolean isPrimitive();
}
