package com.github.gregwhitaker.catnap.core.query.model;

import java.util.List;
import java.util.Map;

public class CatnapCachedQuery implements Query {

    @Override
    public void addParameter(String name) {

    }

    @Override
    public void addParameter(String name, Query subquery) {

    }

    @Override
    public void addParameter(String name, Expression expression) {

    }

    @Override
    public void addParameter(String name, Query subquery, Expression expression) {

    }

    @Override
    public Map getParameters() {
        return null;
    }

    @Override
    public List<String> getParameterNames() {
        return null;
    }

    @Override
    public int getParameterCount() {
        return 0;
    }

    @Override
    public boolean containsParameter(String name) {
        return false;
    }

    @Override
    public boolean containsExpression(String name) {
        return false;
    }

    @Override
    public boolean containsSubquery(String name) {
        return false;
    }

    @Override
    public Query getSubquery(String name) {
        return null;
    }

    @Override
    public Expression getExpression(String name) {
        return null;
    }
}
