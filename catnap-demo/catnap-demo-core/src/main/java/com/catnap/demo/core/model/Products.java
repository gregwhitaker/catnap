package com.catnap.demo.core.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 10/31/14.
 */
@XmlRootElement(name = "Products")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "country",
        "locale",
        "products"
})
public class Products {
    @XmlElement
    private String country;

    @XmlElement
    private String locale;

    @XmlElementWrapper(name = "Products", required = true)
    @XmlElement(name = "Product")
    private List<Product> products;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        if (this.products == null) {
            this.products = new ArrayList<Product>();
        }

        this.products.add(product);
    }
}
