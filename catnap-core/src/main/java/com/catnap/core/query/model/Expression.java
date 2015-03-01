package com.catnap.core.query.model;

import com.catnap.core.query.processor.Property;

/**
 * Created by Woody on 1/11/14.
 */
public interface Expression<T>
{
    public boolean evaluate(Property leftOperand);

    public String getField();

    public Operator getOperator();

    public T getOperand();
}
