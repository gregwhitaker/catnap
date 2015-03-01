package com.catnap.demo.core.service;

import com.catnap.demo.core.model.ProductImage;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created with IntelliJ IDEA.
 *
 * @author gwhit7
 */
public class ProductImageService
{
    public ProductImage createThumbnailImage(int sortOrder, String productId)
    {
        ProductImage image = new ProductImage();
        image.setSortOrder(sortOrder);
        image.setUrl(url(sortOrder, productId, "thumbnail"));
        image.setAlt("Product " + productId);
        image.setSizeDescription("thumbnail");

        return image;
    }

    public ProductImage createPrimaryImage(int sortOrder, String productId)
    {
        ProductImage image = new ProductImage();
        image.setSortOrder(sortOrder);
        image.setUrl(url(sortOrder, productId, "primary"));
        image.setAlt("Product " + productId);
        image.setSizeDescription("primary");

        return image;
    }

    private String url(int sortOrder, String productId, String imageType)
    {
        try
        {
            return String.format("http://www.test.com/images/product/%s/%s%s.png",
                    URLEncoder.encode(productId, "UTF-8"),
                    imageType,
                    sortOrder);
        }
        catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException("Unable to encode parameter [productId] with value [" + productId + "]");
        }
    }
}
