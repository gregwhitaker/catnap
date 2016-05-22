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

import com.github.gregwhitaker.catnap.core.query.model.Query;
import com.github.gregwhitaker.catnap.core.util.ClassUtil;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

public class NullQueryProcessor extends SortableQueryProcessor {

    @Override
    public boolean supports(Class<? extends Query<?>> query) {
        return (query == null);
    }

    @Override
    public <T> List<Property<T>> processInternal(Query query, T instance, Class<T> instanceClazz) {
        List<Property<T>> properties = new ArrayList<Property<T>>();

        //No query specified so we need to return all fields
        for(PropertyDescriptor descriptor : ClassUtil.getReadableProperties(instanceClazz)) {
            if(!ignoreProperty(descriptor)) {
                properties.add(new SimpleProperty<T>(instance, descriptor));
            }
        }

        return properties;
    }
}
