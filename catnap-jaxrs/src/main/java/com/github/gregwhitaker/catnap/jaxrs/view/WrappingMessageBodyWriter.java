package com.github.gregwhitaker.catnap.jaxrs.view;

import com.github.gregwhitaker.catnap.core.view.CatnapView;

/**
 * Interface that all Catnap JAX-RS MessageBodyWriters must implement.
 */
public interface WrappingMessageBodyWriter<T extends CatnapView> {

    /**
     * @return the underlying {@link CatnapView} implementation wrapped by this MessageBodyWriter.
     */
    public T getWrappedView();

    /**
     * @return the content-type returned by the underlying {@link CatnapView} implementation
     * wrapped by this MessageBodyWriter. (Ex. "application/json" or "application/xml")
     */
    public String getContentType();

    /**
     * @return the character encoding returned by the underlying {@link CatnapView}
     * implementation wrapped by this MessageBodyWriter. (Ex. "UTF-8" or "UTF-16")
     */
    public String getCharacterEncoding();
}
