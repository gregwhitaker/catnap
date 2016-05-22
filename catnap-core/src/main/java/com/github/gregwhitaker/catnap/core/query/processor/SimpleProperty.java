package com.github.gregwhitaker.catnap.core.query.processor;

import com.github.gregwhitaker.catnap.core.annotation.CatnapProperty;
import com.github.gregwhitaker.catnap.core.exception.CatnapException;
import com.github.gregwhitaker.catnap.core.util.ClassUtil;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 *
 * @param <T>
 */
public class SimpleProperty<T> implements Property<T> {
    private String name;
    private Object value;
    private boolean primitive = false;
    private Method readMethod;

    /**
     *
     * @param instance
     * @param descriptor
     */
    public SimpleProperty(T instance, PropertyDescriptor descriptor) {
        this.name = name(descriptor);
        this.readMethod = descriptor.getReadMethod();

        try {
            this.value = descriptor.getReadMethod().invoke(instance);

            if(this.value != null) {
                //This check allows Catnap to determine the real return type in cases where
                //a method's return signature is Object.
                this.primitive = ClassUtil.isPrimitiveType(this.value.getClass());
            } else {
                this.primitive = ClassUtil.isPrimitiveType(descriptor.getReadMethod().getReturnType());
            }
        } catch (Exception e) {
            throw new CatnapException(String.format("Unable to invoke read method [%s]", name), e);
        }
    }

    private String name(PropertyDescriptor descriptor) {
        //Catnap Support
        if(descriptor.getReadMethod().isAnnotationPresent(CatnapProperty.class)) {
            CatnapProperty annotation = descriptor.getReadMethod().getAnnotation(CatnapProperty.class);

            if(!CatnapProperty.USE_DEFAULT_NAME.equalsIgnoreCase(annotation.value())) {
                return annotation.value();
            }
        }

        return descriptor.getName();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public Method getReadMethod() {
        return readMethod;
    }

    @Override
    public boolean isPrimitive() {
        return primitive;
    }
}
