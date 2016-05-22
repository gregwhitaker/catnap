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
