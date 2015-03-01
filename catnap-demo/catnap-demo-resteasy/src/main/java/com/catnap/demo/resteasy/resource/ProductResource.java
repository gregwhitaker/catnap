package com.catnap.demo.resteasy.resource;

import com.catnap.core.annotation.CatnapDisabled;
import com.catnap.demo.core.exception.ProductNotFoundException;
import com.catnap.demo.core.model.ExtendedProduct;
import com.catnap.demo.core.model.Product;
import com.catnap.demo.core.service.ProductService;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created with IntelliJ IDEA.
 *
 * @author gwhit7
 */
@Path("/{country}/{language}_{dialect}/product")
@Produces({"application/json", "application/x-javascript", "application/xml"})
public class ProductResource
{
    private ProductService productService = new ProductService();

    @GET
    @Path("/{id}/details")
    public Product getDetails(@PathParam("id") String id)
    {
        return productService.getProduct(id);
    }

    @GET
    @Path("/{id}/details2")
    public Product getDetails2(@PathParam("id") String id)
    {
        throw new ProductNotFoundException(id);
    }

    @GET
    @Path("/{id}/details3")
    public ExtendedProduct getDetails3(@PathParam("id") String id)
    {
        return productService.getExtendedProduct(id);
    }

    @GET
    @Path("/{id}/details4")
    @CatnapDisabled
    public Product getDetails4(@PathParam("id") String id)
    {
        return productService.getProduct(id);
    }
}
