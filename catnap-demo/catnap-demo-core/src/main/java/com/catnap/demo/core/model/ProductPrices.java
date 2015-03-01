package com.catnap.demo.core.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Created by Woody on 12/29/13.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Prices", propOrder = {
        "listPrice",
        "salePrice",
        "employeePrice",
        "formattedListPrice",
        "formattedSalePrice",
        "formattedEmployeePrice"
})
@JsonPropertyOrder(alphabetic = true, value = {
        "listPrice",
        "salePrice",
        "employeePrice"
})
public class ProductPrices implements Serializable
{
    @XmlElement(name = "ListPrice", required = true)
    private Double listPrice;

    @XmlElement(name = "SalePrice")
    private Double salePrice;

    @XmlElement(name = "EmployeePrice", required = true)
    private Double employeePrice;

    @XmlElement(name = "FormattedListPrice", required = true)
    private String formattedListPrice;

    @XmlElement(name = "FormattedSalePrice")
    private String formattedSalePrice;

    @XmlElement(name = "FormattedEmployeePrice", required = true)
    private String formattedEmployeePrice;

    public Double getListPrice()
    {
        return listPrice;
    }

    public void setListPrice(Double listPrice)
    {
        this.listPrice = listPrice;
    }

    public Double getSalePrice()
    {
        return salePrice;
    }

    public void setSalePrice(Double salePrice)
    {
        this.salePrice = salePrice;
    }

    public Double getEmployeePrice()
    {
        return employeePrice;
    }

    public void setEmployeePrice(Double employeePrice)
    {
        this.employeePrice = employeePrice;
    }

    public String getFormattedListPrice()
    {
        return formattedListPrice;
    }

    public void setFormattedListPrice(String formattedListPrice)
    {
        this.formattedListPrice = formattedListPrice;
    }

    public String getFormattedSalePrice()
    {
        return formattedSalePrice;
    }

    public void setFormattedSalePrice(String formattedSalePrice)
    {
        this.formattedSalePrice = formattedSalePrice;
    }

    public String getFormattedEmployeePrice()
    {
        return formattedEmployeePrice;
    }

    public void setFormattedEmployeePrice(String formattedEmployeePrice)
    {
        this.formattedEmployeePrice = formattedEmployeePrice;
    }
}
