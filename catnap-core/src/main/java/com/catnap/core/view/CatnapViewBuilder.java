package com.catnap.core.view;

/**
 * Interface that all Catnap View Builders must implement.
 * @author gwhit7
 */
public interface CatnapViewBuilder<T extends CatnapView>
{
    /**
     * @return fully configured Catnap view
     */
    public T build();
}
