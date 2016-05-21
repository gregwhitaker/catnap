package com.github.gregwhitaker.catnap.core.model;

/**
 *
 * @param <T>
 */
public interface MapBackedModel<T> extends Model<T> {

    /**
     *
     * @param name
     * @param result
     */
    void addValue(String name, Object result);

    /**
     *
     * @param name
     * @return
     */
    MapBackedModel<?> createChildMap(String name);

    /**
     *
     * @param name
     * @return
     */
    ListBackedModel<?> createChildList(String name);
}
