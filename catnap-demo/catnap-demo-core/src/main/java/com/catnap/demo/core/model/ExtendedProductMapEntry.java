package com.catnap.demo.core.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 * Created by gwhit7 on 2/12/14.
 */
@XmlRootElement(name = "Value")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "value1",
        "value2"
})
public class ExtendedProductMapEntry implements Serializable
{
    @XmlElement(name = "Value1")
    private String value1 = "value1";

    @XmlElement(name = "Value2")
    private int value2 = 2;

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public int getValue2() {
        return value2;
    }

    public void setValue2(int value2) {
        this.value2 = value2;
    }
}
