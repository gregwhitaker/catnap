package catnap.examples.springmvc.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProductImage", propOrder = {
        "sortOrder",
        "url",
        "alt",
        "type"
})
public class WidgetImage {

    @XmlElement(name = "SortOrder", required = true)
    private int sortOrder;

    @XmlElement(name = "Url", required = true)
    private String url;

    @XmlElement(name = "Alt", required = true)
    private String alt;

    @XmlElement(name = "Type", required = true)
    private String type;

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
