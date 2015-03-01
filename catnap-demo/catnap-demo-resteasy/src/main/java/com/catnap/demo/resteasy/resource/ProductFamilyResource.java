package com.catnap.demo.resteasy.resource;

import com.catnap.demo.core.model.ProductFamily;
import com.catnap.demo.core.service.ProductFamilyService;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * Created with IntelliJ IDEA.
 *
 * @author gwhit7
 */
@Path("/{country}/{language}_{dialect}/family")
@Produces({"application/json", "application/x-javascript", "application/xml"})
public class ProductFamilyResource
{
    private ProductFamilyService productFamilyService = new ProductFamilyService();

    @GET
    @Path("/{id}/products")
    public ProductFamily getProducts(@PathParam("id") String id)
    {
        return productFamilyService.getProductFamily(id);
    }
}
