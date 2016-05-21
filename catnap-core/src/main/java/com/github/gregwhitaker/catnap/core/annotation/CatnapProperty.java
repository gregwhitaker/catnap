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

package com.github.gregwhitaker.catnap.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@CatnapAnnotation
public @interface CatnapProperty {

    /**
     * Special value that indicates that handlers should use the default
     * name (derived from method or field name) for property.
     */
    public final static String USE_DEFAULT_NAME = "";

    /**
     * Defines name of the logical property, i.e. JSON object field
     * name to use for the property. If value is empty String (which is the
     * default), will try to use name of the field that is annotated.
     * Note that there is
     * <b>no default name available for constructor arguments</b>,
     * meaning that
     * <b>Empty String is not a valid value for constructor arguments</b>.
     */
    String value() default USE_DEFAULT_NAME;

    /**
     * Property that indicates whether a value (which may be explicit
     * null) is expected for property during deserialization or not.
     * If expected, <code>BeanDeserialized</code> should indicate
     * this as a validity problem (usually by throwing an exception,
     * but this may be sent via problem handlers that can try to
     * rectify the problem, for example, by supplying a default
     * value).
     *<p>
     * Note that as of 2.0, this property is NOT used by
     * <code>BeanDeserializer</code>: support is expected to be
     * added for a later minor version.
     *
     * @since 2.0
     */
    boolean required() default false;
}
