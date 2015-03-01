package com.catnap.core.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author gwhit7
 */
public class DefaultListBackedModel implements ListBackedModel<List<?>>
{
    private List<Object> values = new LinkedList<Object>();

    @Override
    public void addValue(Object value)
    {
        values.add(value);
    }

    @Override
    public MapBackedModel<?> createChildMap()
    {
        MapBackedModel child = new DefaultMapBackedModel();
        addValue(child.getResult());

        return child;
    }

    @Override
    public List<?> getResult()
    {
        return values;
    }

    @Override
    public boolean isEmpty()
    {
        return values.isEmpty();
    }
}
