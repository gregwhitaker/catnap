package com.github.gregwhitaker.catnap.core.query.model;

import com.github.gregwhitaker.catnap.core.query.processor.Property;

/**
 *
 * @param <T>
 */
public interface Expression<T> {

    /**
     *
     * @param leftOperand
     * @return
     */
    boolean evaluate(Property leftOperand);

    /**
     *
     * @return
     */
    String getField();

    /**
     *
     * @return
     */
    Operator getOperator();

    /**
     *
     * @return
     */
    T getOperand();
}
