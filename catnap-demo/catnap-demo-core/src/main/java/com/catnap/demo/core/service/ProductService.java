package com.catnap.demo.core.service;

import com.catnap.demo.core.model.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Woody on 12/29/13.
 */
public class ProductService
{
    private ProductImageService imageService = new ProductImageService();

    public Product getProduct(String id)
    {
        Product product = new Product();
        product.setId(id);
        product.setName1(String.format("Product %s Name1", id));
        product.setName2(null);
        product.setActive(true);
        product.setPrices(getProductPrices(id));
        product.setImages(getProductImages(id));

        return product;
    }

    public ExtendedProduct getExtendedProduct(String id)
    {
        ExtendedProduct product = new ExtendedProduct();
        product.setId(id);
        product.setName1(String.format("Product %s Name1", id));
        product.setName2(null);
        product.setActive(true);
        product.setPrices(getProductPrices(id));
        product.setImages(getProductImages(id));

        Map<String, Object> extendedProductMap = new HashMap<String, Object>();
        extendedProductMap.put("key1", new ExtendedProductMapEntry());
        extendedProductMap.put("key2", new ExtendedProductMapEntry());

        product.setExtendedProductMapField(extendedProductMap);

        return product;
    }

    private ProductPrices getProductPrices(String id)
    {
        ProductPrices prices = new ProductPrices();
        prices.setListPrice(112.54);
        prices.setSalePrice(89.99);
        prices.setEmployeePrice(65.00);
        prices.setFormattedListPrice("$112.54");
        prices.setFormattedSalePrice("$89.99");
        prices.setFormattedEmployeePrice("$65.00");

        return prices;
    }

    private List<ProductImage> getProductImages(String id)
    {
        List<ProductImage> images = new ArrayList<ProductImage>(2);
        images.add(imageService.createPrimaryImage(1, id));
        images.add(imageService.createPrimaryImage(2, id));
        images.add(imageService.createPrimaryImage(3, id));
        images.add(imageService.createThumbnailImage(1, id));

        return images;
    }
}
