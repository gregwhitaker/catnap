package com.github.gregwhitaker.catnap.jaxrs.view;

import com.github.gregwhitaker.catnap.core.view.JsonpCatnapView;

import javax.ws.rs.Produces;
import javax.ws.rs.ext.Provider;

/**
 * MessageBodyWriter responsible for creating JSONP responses.
 */
@Provider
@Produces("application/x-javascript")
public class JsonpCatnapMessageBodyWriter extends CatnapMessageBodyWriter<JsonpCatnapView> {
    
    /**
     * Initializes this MessageBodyWriter with the default configuration of
     * the {@link JsonpCatnapView}.
     */
    public JsonpCatnapMessageBodyWriter() {
        super(new JsonpCatnapView.Builder().build());
    }

    /**
     * Initializes this MessageBodyWriter with the supplied {@link JsonpCatnapView}.
     *
     * @param view view that will be used by this MessageBodyWriter to render JSONP responses
     */
    public JsonpCatnapMessageBodyWriter(JsonpCatnapView view) {
        super(view);
    }
}
