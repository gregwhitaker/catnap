package com.catnap.demo.springmvc.controller;

import com.catnap.core.annotation.CatnapDisabled;
import com.catnap.demo.core.exception.ProductNotFoundException;
import com.catnap.demo.core.model.ExtendedProduct;
import com.catnap.demo.core.model.Product;
import com.catnap.demo.core.service.ProductService;
import com.catnap.springmvc.annotation.CatnapResponseBody;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Woody on 12/29/13.
 */
@Controller
@RequestMapping(value = "/{country}/{language}_{dialect}/product")
public class ProductController
{
    @Resource
    private ProductService productService;

    @RequestMapping(method = RequestMethod.GET, value = "{id}/details")
    public ModelAndView getDetails(@PathVariable("id") String id, HttpServletRequest request)
    {
        return new ModelAndView(request.getPathInfo(), "result", productService.getProduct(id));
    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}/details2")
    @CatnapResponseBody
    public Product getDetails2(@PathVariable("id") String id)
    {
        return productService.getProduct(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}/details3")
    @CatnapResponseBody
    public Product getDetails3(@PathVariable("id") String id)
    {
        throw new ProductNotFoundException(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}/details4")
    @CatnapResponseBody
    public ExtendedProduct getDetails4(@PathVariable("id") String id)
    {
        return productService.getExtendedProduct(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}/details5")
    @CatnapDisabled
    @CatnapResponseBody
    public Product getDetails5(@PathVariable("id") String id)
    {
        return productService.getProduct(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}/details6")
    @CatnapDisabled
    public ModelAndView getDetails6(@PathVariable("id") String id, HttpServletRequest request)
    {
        return new ModelAndView(request.getPathInfo(), "result", productService.getProduct(id));
    }
}
