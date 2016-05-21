package com.github.gregwhitaker.catnap.core.model;

/**
 *
 * @param <T>
 */
public interface ListBackedModel<T> extends Model<T> {

    /**
     *
     * @param value
     */
    void addValue(Object value);

    /**
     *
     * @return
     */
    MapBackedModel<?> createChildMap();
}
