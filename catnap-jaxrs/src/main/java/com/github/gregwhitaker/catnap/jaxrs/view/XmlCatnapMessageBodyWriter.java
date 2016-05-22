package com.github.gregwhitaker.catnap.jaxrs.view;

import com.github.gregwhitaker.catnap.core.view.XmlCatnapView;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

/**
 * MessageBodyWriter responsible for creating XML responses.
 */
@Provider
@Produces(MediaType.APPLICATION_XML)
public class XmlCatnapMessageBodyWriter extends CatnapMessageBodyWriter {

    /**
     * Initializes this MessageBodyWriter with the default configuration of
     * the {@link com.github.gregwhitaker.catnap.core.view.XmlCatnapView}.
     */
    public XmlCatnapMessageBodyWriter() {
        super(new XmlCatnapView.Builder().build());
    }

    /**
     * Initializes this MessageBodyWriter with the supplied {@link XmlCatnapView}.
     *
     * @param view view that will be used by this MessageBodyWriter to render XML responses
     */
    public XmlCatnapMessageBodyWriter(XmlCatnapView view) {
        super(view);
    }
}
