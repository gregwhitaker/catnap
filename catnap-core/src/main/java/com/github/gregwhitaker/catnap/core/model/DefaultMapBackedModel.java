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

package com.github.gregwhitaker.catnap.core.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 */
public class DefaultMapBackedModel implements MapBackedModel {
    private Map<String, Object> values = new LinkedHashMap<>();

    @Override
    public void addValue(String name, Object result) {
        values.put(name, result);
    }

    @Override
    public MapBackedModel<?> createChildMap(String name) {
        MapBackedModel child = new DefaultMapBackedModel();
        values.put(name, child.getResult());
        return child;
    }

    @Override
    public ListBackedModel<?> createChildList(String name) {
        ListBackedModel child = new DefaultListBackedModel();
        values.put(name, child.getResult());
        return child;
    }

    @Override
    public Map<String, Object> getResult() {
        return values;
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }
}
