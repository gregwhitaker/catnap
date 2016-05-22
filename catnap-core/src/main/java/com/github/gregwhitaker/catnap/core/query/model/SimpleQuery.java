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

package com.github.gregwhitaker.catnap.core.query.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class SimpleQuery implements Query<SimpleQuery> {
    protected Map<String, SimpleQuery> parameters = new LinkedHashMap<>();
    protected Map<String, Expression> expressions = new HashMap<>();

    @Override
    public void addParameter(String name) {
        parameters.put(name, null);
    }

    @Override
    public void addParameter(String name, Query subquery) {
        parameters.put(name, (SimpleQuery) subquery);
    }

    @Override
    public void addParameter(String name, Expression expression) {
        parameters.put(name, null);
        expressions.put(name, expression);
    }

    @Override
    public void addParameter(String name, Query subquery, Expression expression) {
        parameters.put(name, (SimpleQuery) subquery);
        expressions.put(name, expression);
    }

    @Override
    public Map<String, SimpleQuery> getParameters() {
        return parameters;
    }

    @Override
    public List<String> getParameterNames() {
        List<String> paramNames = new ArrayList<>(parameters.keySet().size());
        paramNames.addAll(parameters.keySet());
        return paramNames;
    }

    @Override
    public int getParameterCount() {
        return parameters.size();
    }

    @Override
    public boolean containsParameter(String name) {
        return parameters.containsKey(name);
    }

    @Override
    public boolean containsExpression(String name) {
        return expressions.containsKey(name);
    }

    @Override
    public boolean containsSubquery(String name) {
        return (getSubquery(name) != null);
    }

    @Override
    public Query getSubquery(String name) {
        return parameters.get(name);
    }

    @Override
    public Expression getExpression(String name) {
        return expressions.get(name);
    }
}
