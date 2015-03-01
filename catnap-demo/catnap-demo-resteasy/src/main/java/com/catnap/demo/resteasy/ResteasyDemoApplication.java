package com.catnap.demo.resteasy;

import com.catnap.demo.resteasy.resource.ProductFamilyResource;
import com.catnap.demo.resteasy.resource.ProductResource;
import com.catnap.jaxrs.view.JsonCatnapMessageBodyWriter;
import com.catnap.jaxrs.view.JsonpCatnapMessageBodyWriter;
import com.catnap.jaxrs.view.XmlCatnapMessageBodyWriter;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Bootstraps Resteasy Demo Application
 * @author gwhit7
 */
public class ResteasyDemoApplication extends Application
{
    private Set<Object> singletons = new HashSet<Object>();

    public ResteasyDemoApplication()
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
