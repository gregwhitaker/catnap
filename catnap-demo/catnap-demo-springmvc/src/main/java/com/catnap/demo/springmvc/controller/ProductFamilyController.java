package com.catnap.demo.springmvc.controller;

import com.catnap.demo.core.model.Product;
import com.catnap.demo.core.model.ProductFamily;
import com.catnap.demo.core.service.ProductFamilyService;
import com.catnap.springmvc.annotation.CatnapResponseBody;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * Created by Woody on 12/29/13.
 */
@Controller
@RequestMapping(value = "/{country}/{language}_{dialect}/family")
public class ProductFamilyController
{
    @Resource
    private ProductFamilyService productFamilyService;

    @RequestMapping(method = RequestMethod.GET, value = "{id}/products")
    @CatnapResponseBody
    private ProductFamily getProducts(@PathVariable("id") String id)
    {
        return productFamilyService.getProductFamily(id);
    }
}
