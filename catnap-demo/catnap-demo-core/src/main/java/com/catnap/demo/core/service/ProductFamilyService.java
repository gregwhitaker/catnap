package com.catnap.demo.core.service;

import com.catnap.demo.core.model.Product;
import com.catnap.demo.core.model.ProductFamily;
import com.catnap.demo.core.model.ProductImage;
import com.catnap.demo.core.model.ProductPrices;

import java.util.Date;
import java.util.Random;

/**
 * Created by Woody on 12/29/13.
 */
public class ProductFamilyService
{
    private ProductImageService imageService = new ProductImageService();

    public ProductFamily getProductFamily(String id)
    {
        ProductFamily family = new ProductFamily();
        family.setId("12345");
        family.addProduct(product1());
        family.addProduct(product2());
        family.addProduct(product3());

        return family;
    }

    private Product product1()
    {
        ProductPrices prices = new ProductPrices();
        prices.setListPrice(120.99);
        prices.setSalePrice(120.99);
        prices.setEmployeePrice(79.99);
        prices.setFormattedListPrice("$120.99");
        prices.setFormattedSalePrice("$120.99");
        prices.setFormattedEmployeePrice("$79.99");

        Product product = new Product();
        product.setId("11111");
        product.setActive(true);
        product.setName1("Product 11111 name1");
        product.setName2("Product 11111 name2");
        product.setStartDate(new Date());
        product.setPrices(prices);
        product.addImage(imageService.createPrimaryImage(1, "11111"));
        product.addImage(imageService.createPrimaryImage(2, "11111"));
        product.addImage(imageService.createPrimaryImage(3, "11111"));
        product.addImage(imageService.createThumbnailImage(1, "11111"));
        product.addImage(imageService.createThumbnailImage(2, "11111"));

        return product;
    }

    private Product product2()
    {
        ProductPrices prices = new ProductPrices();
        prices.setListPrice(79.99);
        prices.setSalePrice(59.99);
        prices.setEmployeePrice(49.99);
        prices.setFormattedListPrice("$79.99");
        prices.setFormattedSalePrice("$59.99");
        prices.setFormattedEmployeePrice("$49.99");

        Product product = new Product();
        product.setId("22222");
        product.setActive(true);
        product.setName1("Product 22222 name1");
        product.setName2(null);
        product.setStartDate(new Date());
        product.setPrices(prices);
        product.addImage(imageService.createPrimaryImage(1, "22222"));
        product.addImage(imageService.createPrimaryImage(2, "22222"));
        product.addImage(imageService.createThumbnailImage(1, "22222"));
        product.addImage(imageService.createThumbnailImage(2, "22222"));

        return product;
    }

    private Product product3()
    {
        ProductPrices prices = new ProductPrices();
        prices.setListPrice(159.99);
        prices.setSalePrice(129.99);
        prices.setEmployeePrice(89.00);
        prices.setFormattedListPrice("$159.99");
        prices.setFormattedSalePrice("$129.99");
        prices.setFormattedEmployeePrice("$89.00");

        Product product = new Product();
        product.setId("33333");
        product.setActive(false);
        product.setName1("Product 33333 name1");
        product.setName2("Product 33333 name2");
        product.setStartDate(new Date());
        product.setPrices(prices);
        product.addImage(imageService.createPrimaryImage(1, "33333"));
        product.addImage(imageService.createPrimaryImage(2, "33333"));
        product.addImage(imageService.createPrimaryImage(3, "33333"));
        product.addImage(imageService.createThumbnailImage(1, "33333"));
        product.addImage(imageService.createThumbnailImage(2, "33333"));
        product.addImage(imageService.createThumbnailImage(3, "33333"));

        return product;
    }
}
