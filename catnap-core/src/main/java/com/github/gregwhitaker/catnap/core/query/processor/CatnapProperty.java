/*
 * Copyright 2016 Greg Whitaker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.gregwhitaker.catnap.core.query.processor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gregwhitaker.catnap.core.exception.CatnapException;
import com.github.gregwhitaker.catnap.core.util.ClassUtil;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * @param <T>
 */
public class CatnapProperty<T> implements Property<T> {
    private String name;
    private String renderName;
    private Object value;
    private boolean primitive = false;
    private Method readMethod;

    /**
     * @param instance
     * @param descriptor
     */
    public CatnapProperty(T instance, PropertyDescriptor descriptor) {
        this.name = descriptor.getName();
        this.renderName = renderName(descriptor);
        this.readMethod = descriptor.getReadMethod();

        try {
            this.value = descriptor.getReadMethod().invoke(instance);

            if (this.value != null) {
                // Need to defensively copy any iterable types since we are removing items when querying
                if (Iterable.class.isAssignableFrom(value.getClass())) {
                    ArrayList<Object> wrapper = new ArrayList<>();

                    Iterable<?> iterable = (Iterable<?>) this.value;
                    iterable.forEach(wrapper::add);

                    this.value = wrapper;
                }
            }

            if (this.value != null) {
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

    private String renderName(PropertyDescriptor descriptor) {
        if (descriptor.getReadMethod().isAnnotationPresent(JsonProperty.class)) {
            JsonProperty annotation = descriptor.getReadMethod().getAnnotation(JsonProperty.class);

            if (!JsonProperty.USE_DEFAULT_NAME.equalsIgnoreCase(annotation.value())) {
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
    public String getRenderName() {
        return renderName;
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
