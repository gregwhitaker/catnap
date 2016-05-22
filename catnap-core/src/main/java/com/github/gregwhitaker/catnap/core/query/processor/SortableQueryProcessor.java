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

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.gregwhitaker.catnap.core.annotation.CatnapOrder;
import com.github.gregwhitaker.catnap.core.query.model.Query;

import java.util.Collections;
import java.util.List;

/**
 *
 */
public abstract class SortableQueryProcessor extends QueryProcessor {

    private enum SortMethod {
        FIELD_DECLARATION,
        ANNOTATION,
        ALPHABETICAL
    }

    @Override
    protected <T> void postProcess(List<Property<T>> properties, Query query, T instance, Class<T> instanceClazz) {
        super.postProcess(properties, query, instance, instanceClazz);

        switch(sortMethod(instanceClazz)) {
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

    private <T> SortMethod sortMethod(Class<T> instanceClazz) {
        if(instanceClazz != null) {
            //Catnap Support
            if(instanceClazz.isAnnotationPresent(CatnapOrder.class)) {
                String[] value = instanceClazz.getAnnotation(CatnapOrder.class).value();
                return (value != null && value.length > 0) ? SortMethod.ANNOTATION : SortMethod.ALPHABETICAL;
            }

            //Jackson Support
            if(instanceClazz.isAnnotationPresent(JsonPropertyOrder.class)) {
                String[] value = instanceClazz.getAnnotation(JsonPropertyOrder.class).value();
                return (value != null && value.length > 0) ? SortMethod.ANNOTATION : SortMethod.ALPHABETICAL;
            }
        }

        return SortMethod.FIELD_DECLARATION;
    }
}
