package com.catnap.springmvc.view;

import com.catnap.core.view.CatnapView;

/**
 * Interface that links a {@link com.catnap.core.view.CatnapView} and a Spring {@link org.springframework.web.servlet.View}
 * implementation.
 *
 * @author gwhit7
 */
public interface WrappingView<T extends CatnapView>
{
    /**
     * @return the underlying {@link com.catnap.core.view.CatnapView} implementation wrapped by this view.
     */
    public T getWrappedView();

    /**
     * @return the character encoding returned by the underlying {@link com.catnap.core.view.CatnapView}
     * implementation wrapped by this view. (Ex. "UTF-8" or "UTF-16")
     */
    public String getCharacterEncoding();

    /**
     * @return name of the object in the {@link org.springframework.ui.Model} to render with Catnap.
     */
    public String getModelName();
}
