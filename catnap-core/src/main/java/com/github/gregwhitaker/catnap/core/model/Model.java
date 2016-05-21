package com.github.gregwhitaker.catnap.core.model;

/**
 *
 * @param <T>
 */
public interface Model<T> {

    /**
     *
     * @return
     */
    T getResult();

    /**
     *
     * @return
     */
    boolean isEmpty();
}
