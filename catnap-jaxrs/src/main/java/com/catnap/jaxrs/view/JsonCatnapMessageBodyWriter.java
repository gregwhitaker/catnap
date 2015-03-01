package com.catnap.jaxrs.view;

import com.catnap.core.view.CatnapView;
import com.catnap.core.view.JsonCatnapView;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

/**
 * MessageBodyWriter responsible for creating JSON responses.
 * @author gwhit7
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JsonCatnapMessageBodyWriter extends CatnapMessageBodyWriter<JsonCatnapView>
{
    /**
     * Initializes this MessageBodyWriter with the default configuration of
     * the {@link com.catnap.core.view.JsonCatnapView}.
     */
    public JsonCatnapMessageBodyWriter()
    {
        super(new JsonCatnapView.Builder().build());
    }

    /**
     * Initializes this MessageBodyWriter with the supplied {@link com.catnap.core.view.JsonCatnapView}.
     * @param view view that will be used by this MessageBodyWriter to render JSON responses
     */
    public JsonCatnapMessageBodyWriter(JsonCatnapView view)
    {
        super(view);
    }
}
