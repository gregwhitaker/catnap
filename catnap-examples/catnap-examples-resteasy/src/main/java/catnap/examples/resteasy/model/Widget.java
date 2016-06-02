/*
 * Copyright 2016 Greg Whitaker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package catnap.examples.resteasy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;
import java.util.List;

@XmlRootElement(name = "Widget")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "id",
        "name",
        "longName",
        "active",
        "date",
        "prices",
        "images"
})
@JsonPropertyOrder(value = {
        "id",
        "name",
        "longName",
        "active",
        "date",
        "prices",
        "images"
})
public class Widget {

    @XmlElement(name = "Id", required = true)
    private String id;

    @XmlElement(name = "Name", required = true)
    private String name;

    @XmlElement(name = "LongName", required = true)
    private String longName;

    @XmlElement(name = "Active", required = true)
    private boolean active;

    @XmlElement(name = "ManufactureDate", required = true)
    private Date date;

    @XmlElement(name = "Prices", required = true)
    private WidgetPrices prices;

    @XmlElementWrapper(name = "Images", required = true)
    @XmlElement(name = "Image")
    private List<WidgetImage> images;

    @XmlTransient
    private String ignoredField1 = "This field should never be rendered as it is annotated with @JsonIgnore";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    // This demonstrates matching a property that has a getter with a different name.
    @JsonProperty("isActive")
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @JsonProperty("manufactureDate")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public WidgetPrices getPrices() {
        return prices;
    }

    public void setPrices(WidgetPrices prices) {
        this.prices = prices;
    }

    public List<WidgetImage> getImages() {
        return images;
    }

    public void setImages(List<WidgetImage> images) {
        this.images = images;
    }

    // This demonstrates that Catnap honors Jackson annotations and will exclude the annotated
    // field from Catnap rendering.
    @JsonIgnore
    public String getIgnoredField1() {
        return ignoredField1;
    }

    public void setIgnoredField1(String ignoredField1) {
        this.ignoredField1 = ignoredField1;
    }
}
