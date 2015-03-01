package com.catnap.demo.core.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Created by Woody on 12/29/13.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProductImage", propOrder = {
        "sortOrder",
        "url",
        "alt",
        "sizeDescription"
})
public class ProductImage implements Serializable
{
    @XmlElement(name = "SortOrder", required = true)
    private int sortOrder;

    @XmlElement(name = "Url", required = true)
    private String url;

    @XmlElement(name = "Alt", required = true)
    private String alt;

    @XmlElement(name = "SizeDescription", required = true)
    private String sizeDescription;

    public int getSortOrder()
    {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder)
    {
        this.sortOrder = sortOrder;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getAlt()
    {
        return alt;
    }

    public void setAlt(String alt)
    {
        this.alt = alt;
    }

    public String getSizeDescription()
    {
        return sizeDescription;
    }

    public void setSizeDescription(String sizeDescription)
    {
        this.sizeDescription = sizeDescription;
    }
}
