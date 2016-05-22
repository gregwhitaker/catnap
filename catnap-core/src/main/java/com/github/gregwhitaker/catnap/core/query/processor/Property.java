package com.github.gregwhitaker.catnap.core.query.processor;

import java.lang.reflect.Method;

/**
 * Interface used by Catnap during introspection to represent a field
 * and it's associated "getter" method.
 */
public interface Property<T> {

    /**
     * @return field name of the property
     */
    String getName();

    /**
     * @return value returned when calling the property's "getter"
     */
    Object getValue();

    /**
     * @return property's "getter" method
     */
    Method getReadMethod();

    /**
     * @return 'true' if the return type of the property's getter method is a primitive type
     */
    boolean isPrimitive();
}
