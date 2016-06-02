package catnap.examples.jersey.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;
import java.util.List;

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

    private String id;

    private String name;

    private String longName;

    private boolean active;

    private Date date;

    private WidgetPrices prices;

    private List<WidgetImage> images;

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
