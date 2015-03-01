package com.catnap.core.model;

/**
 * Created with IntelliJ IDEA.
 *
 * @author gwhit7
 */
public interface ListBackedModel<T> extends Model<T>
{
    public void addValue(Object value);

    public MapBackedModel<?> createChildMap();
}
