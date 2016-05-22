package com.github.gregwhitaker.catnap.springmvc.view;

import com.github.gregwhitaker.catnap.core.view.CatnapView;

/**
 * Interface that links a {@link CatnapView} and a Spring {@link org.springframework.web.servlet.View}
 * implementation.
 */
public interface WrappingView<T extends CatnapView>
{
    /**
     * @return the underlying {@link CatnapView} implementation wrapped by this view.
     */
    T getWrappedView();

    /**
     * @return the character encoding returned by the underlying {@link CatnapView}
     * implementation wrapped by this view. (Ex. "UTF-8" or "UTF-16")
     */
    String getCharacterEncoding();

    /**
     * @return name of the object in the {@link org.springframework.ui.Model} to render with Catnap.
     */
    String getModelName();
}
