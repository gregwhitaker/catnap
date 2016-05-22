package catnap.examples.springmvc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.gregwhitaker.catnap.core.annotation.CatnapIgnore;
import com.github.gregwhitaker.catnap.core.annotation.CatnapProperty;

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

    @XmlTransient
    private String getIgnoredField2 = "This field should never be rendered as it is annotated with @CatnapIgnore";

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

    // This demonstrates that you can set a false value on the @CatnapIgnore annotation to negate it and show
    // the value of the annotated property in a Catnap rendered response.
    @CatnapIgnore(false)
    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    // This demonstrates matching a property that has a getter with a different name.
    @CatnapProperty(value = "isActive")
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    // This demonstrates renaming a property when it is rendered by Catnap.
    @CatnapProperty("manufactureDate")
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

    // This demonstrates that fields you wish to have excluded from Catnap rendering can be annotated with
    // the @CatnapIgnore annotation.
    @CatnapIgnore
    public String getGetIgnoredField2() {
        return getIgnoredField2;
    }

    public void setGetIgnoredField2(String getIgnoredField2) {
        this.getIgnoredField2 = getIgnoredField2;
    }
}
