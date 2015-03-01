package com.catnap.demo.core.model;

import com.catnap.core.annotation.CatnapIgnore;
import com.catnap.core.annotation.CatnapProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Woody on 12/29/13.
 */
@XmlRootElement(name = "Product")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "id",
        "name1",
        "name2",
        "active",
        "startDate",
        "prices",
        "images",
        "anotherRandomField"
})
public class Product implements Serializable
{
    @XmlElement(name = "Id", required = true)
    private String id;

    @XmlElement(name = "Name1", required = true)
    private String name1;

    @XmlElement(name = "Name2", required = true)
    private String name2;

    @XmlElement(name = "Active", required = true)
    private boolean active = true;

    @XmlElement(name = "StartDate", required = true)
    private Date startDate = new Date();

    @XmlElement(name = "Prices", required = true)
    private ProductPrices prices;

    @XmlElementWrapper(name = "Images", required = true)
    @XmlElement(name = "Image")
    private List<ProductImage> images;

    @XmlTransient
    private String ignoredField = "This should be ignored";

    @XmlTransient
    private String ignoredField2 = "This should also be ignored";

    @XmlElement(name = "AnotherRandomField")
    private String anotherRandomField = "This is a field with a different name";

    public void addImage(ProductImage image)
    {
        if(images == null)
        {
            images = new ArrayList<ProductImage>(1);
        }

        images.add(image);
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    @CatnapIgnore(false)
    public String getName1()
    {
        return name1;
    }

    public void setName1(String name1)
    {
        this.name1 = name1;
    }

    public String getName2()
    {
        return name2;
    }

    public void setName2(String name2)
    {
        this.name2 = name2;
    }

    @CatnapProperty(value = "isActive")
    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public ProductPrices getPrices()
    {
        return prices;
    }

    public void setPrices(ProductPrices prices)
    {
        this.prices = prices;
    }

    public List<ProductImage> getImages()
    {
        return images;
    }

    public void setImages(List<ProductImage> images)
    {
        this.images = images;
    }

    @JsonIgnore
    public String getIgnoredField()
    {
        return ignoredField;
    }

    public void setIgnoredField(String ignoredField)
    {
        this.ignoredField = ignoredField;
    }

    @CatnapIgnore
    public String getIgnoredField2() {
        return ignoredField2;
    }

    public void setIgnoredField2(String ignoredField2) {
        this.ignoredField2 = ignoredField2;
    }

    @CatnapProperty("anotherRandomField")
    public String getSomeField() {
        return anotherRandomField;
    }

    public void setSomeField(String value) {
        this.anotherRandomField = value;
    }
}
