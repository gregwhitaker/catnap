package catnap.examples.springmvc.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Prices", propOrder = {
        "list",
        "sale",
        "msrp",
        "formattedList",
        "formattedSale",
        "formattedMSRP"
})
public class WidgetPrices {

    @XmlElement(name = "List", required = true)
    private Double list;

    @XmlElement(name = "Sale")
    private Double sale;

    @XmlElement(name = "MSRP", required = true)
    private Double msrp;

    @XmlElement(name = "FormattedList", required = true)
    private String formattedList;

    @XmlElement(name = "FormattedSale")
    private String formattedSale;

    @XmlElement(name = "FormattedMSRP", required = true)
    private String formattedMSRP;

    public Double getList() {
        return list;
    }

    public void setList(Double list) {
        this.list = list;
    }

    public Double getSale() {
        return sale;
    }

    public void setSale(Double sale) {
        this.sale = sale;
    }

    public Double getMsrp() {
        return msrp;
    }

    public void setMsrp(Double msrp) {
        this.msrp = msrp;
    }

    public String getFormattedList() {
        return formattedList;
    }

    public void setFormattedList(String formattedList) {
        this.formattedList = formattedList;
    }

    public String getFormattedSale() {
        return formattedSale;
    }

    public void setFormattedSale(String formattedSale) {
        this.formattedSale = formattedSale;
    }

    public String getFormattedMSRP() {
        return formattedMSRP;
    }

    public void setFormattedMSRP(String formattedMSRP) {
        this.formattedMSRP = formattedMSRP;
    }
}
