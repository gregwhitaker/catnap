package com.catnap.demo.core.model;

import com.catnap.core.annotation.CatnapIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author gwhit7
 */
@JsonPropertyOrder({
        "id",
        "name1",
        "name2",
        "active",
        "startDate",
        "prices",
        "images",
        "anotherRandomField",
        "extendedProductField",
        "extendedProductMapField"
})
@XmlRootElement(name = "Product")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExtendedProduct extends Product
{
    @XmlElement(name = "ExtendedProductField", required = true)
    private String extendedProductField = "This is a field on ExtendedProduct";

    @XmlElementWrapper(name = "ExtendedProductMap")
    @XmlElement(name = "Element")
    private List<Element> extendedProductMapElements;

    @XmlTransient
    private Map<String, Object> extendedProductMapField;

    public String getExtendedProductField()
    {
        return extendedProductField;
    }

    public void setExtendedProductField(String extendedProductField)
    {
        this.extendedProductField = extendedProductField;
    }

    public Map<String, Object> getExtendedProductMapField()
    {
        return extendedProductMapField;
    }

    public void setExtendedProductMapField(Map<String, Object> value)
    {
        this.extendedProductMapField = value;

        this.extendedProductMapElements = new ArrayList<Element>();

        if(value != null)
        {
            Iterator<String> iter = value.keySet().iterator();

            while(iter.hasNext())
            {
                String key = iter.next();

                Element element = new Element();
                element.setKey(key);
                element.setValue(value.get(key));

                this.extendedProductMapElements.add(element);
            }
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "Element", propOrder = {
            "key",
            "value"
    })
    @XmlSeeAlso(ExtendedProductMapEntry.class)
    static class Element
    {
        @XmlElement(name = "Key")
        private String key;

        @XmlAnyElement
        private Object value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }
}
