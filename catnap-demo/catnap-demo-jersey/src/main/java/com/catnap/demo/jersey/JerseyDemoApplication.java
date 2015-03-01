package com.catnap.demo.jersey;

import com.catnap.demo.jersey.resource.ProductFamilyResource;
import com.catnap.demo.jersey.resource.ProductResource;
import com.catnap.jaxrs.view.JsonCatnapMessageBodyWriter;
import com.catnap.jaxrs.view.JsonpCatnapMessageBodyWriter;
import com.catnap.jaxrs.view.XmlCatnapMessageBodyWriter;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Bootstraps Jersey Demo Application
 * @author gwhit7
 */
public class JerseyDemoApplication extends Application
{
    private Set<Object> singletons = new HashSet<Object>();

    public JerseyDemoApplication()
    {
        //Add Resources
        singletons.add(new ProductResource());
        singletons.add(new ProductFamilyResource());

        //Add Catnap MessageBodyWriters (Note: These can also be auto-detected via their @Provider annotations)
        singletons.add(new JsonCatnapMessageBodyWriter());
        singletons.add(new JsonpCatnapMessageBodyWriter());
        singletons.add(new XmlCatnapMessageBodyWriter());
    }

    @Override
    public Set<Object> getSingletons()
    {
        return singletons;
    }
}
