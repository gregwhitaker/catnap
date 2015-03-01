package com.catnap.demo.core.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Woody on 12/29/13.
 */
@XmlRootElement(name = "ProductFamily")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "id",
        "products"
})
public class ProductFamily implements Serializable
{
    @XmlElement(name = "Id", required = true)
    private String id;

    @XmlElementWrapper(name = "Products", required = true)
    @XmlElement(name = "Product")
    private List<Product> products;

    public void addProduct(Product product)
    {
        if(products == null)
        {
            products = new ArrayList<Product>(1);
        }

        products.add(product);
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public List<Product> getProducts()
    {
        return products;
    }

    public void setProducts(List<Product> products)
    {
        this.products = products;
    }
}
