package com.github.gregwhitaker.catnap.core.model;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class DefaultListBackedModel implements ListBackedModel {
    private List<Object> values = new LinkedList<>();

    @Override
    public void addValue(Object value) {
        values.add(value);
    }

    @Override
    public MapBackedModel<?> createChildMap() {
        MapBackedModel child = new DefaultMapBackedModel();
        addValue(child.getResult());
        return child;
    }

    @Override
    public List<?> getResult() {
        return values;
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }
}
