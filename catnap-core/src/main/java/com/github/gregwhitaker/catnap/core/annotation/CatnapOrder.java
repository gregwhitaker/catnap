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
 * Annotation that is added to model types to control the order in which properties are serialized.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@CatnapAnnotation
public @interface CatnapOrder {

    /**
     * Order in which properties of annotated object are to be serialized in.
     */
    public String[] value() default { };

    /**
     * Property that defines what to do regarding ordering of properties
     * not explicitly included in annotation instance. If set to true,
     * they will be alphabetically ordered; if false, order is
     * undefined (default setting)
     */
    public boolean alphabetic() default false;
}
