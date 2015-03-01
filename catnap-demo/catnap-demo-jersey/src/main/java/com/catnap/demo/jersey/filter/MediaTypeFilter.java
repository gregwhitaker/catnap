package com.catnap.demo.jersey.filter;

import com.sun.jersey.api.container.filter.UriConnegFilter;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

/**
 * Custom content negotiation filter that maps an href suffix to a requested content type.
 * <p>
 * For example if your url ends with ".json" it would tell Jersey that you are requesting the content type of the data
 * to be returned in the response to be "application/json".
 * </p>
 * @author gwhit7
 */
public class MediaTypeFilter extends UriConnegFilter
{
    private static final Map<String, MediaType> mediaExtensions = new HashMap<String, MediaType>(3);

    static
    {
        mediaExtensions.put("json", MediaType.APPLICATION_JSON_TYPE);
        mediaExtensions.put("jsonp", new MediaType("application", "x-javascript"));
        mediaExtensions.put("xml", MediaType.APPLICATION_XML_TYPE);
    }

    public MediaTypeFilter()
    {
        super(mediaExtensions);
    }
}
