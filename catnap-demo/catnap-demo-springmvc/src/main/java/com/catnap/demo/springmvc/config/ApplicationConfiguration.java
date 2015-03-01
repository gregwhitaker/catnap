package com.catnap.demo.springmvc.config;

import com.catnap.demo.core.service.ProductFamilyService;
import com.catnap.demo.core.service.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Woody on 12/29/13.
 */
@Configuration
public class ApplicationConfiguration
{
    @Bean
    public ProductService productService()
    {
        return new ProductService();
    }

    @Bean
    public ProductFamilyService productFamilyService()
    {
        return new ProductFamilyService();
    }
}
