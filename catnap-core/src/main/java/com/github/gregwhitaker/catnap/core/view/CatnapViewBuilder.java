package com.github.gregwhitaker.catnap.core.view;

/**
 * Interface that all Catnap View Builders must implement.
 */
public interface CatnapViewBuilder<T extends CatnapView> {

    /**
     * @return fully configured Catnap view
     */
    T build();
}
