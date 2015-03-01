package com.catnap.core.model;

import java.util.Observable;

/**
 * Created with IntelliJ IDEA.
 *
 * @author gwhit7
 */
public interface MapBackedModel<T> extends Model<T>
{
    public void addValue(String name, Object result);

    public MapBackedModel<?> createChildMap(String name);

    public ListBackedModel<?> createChildList(String name);
}
