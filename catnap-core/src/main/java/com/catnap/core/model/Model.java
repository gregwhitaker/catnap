package com.catnap.core.model;

/**
 * Created with IntelliJ IDEA.
 *
 * @author gwhit7
 */
public interface Model<T>
{
    T getResult();

    boolean isEmpty();
}
