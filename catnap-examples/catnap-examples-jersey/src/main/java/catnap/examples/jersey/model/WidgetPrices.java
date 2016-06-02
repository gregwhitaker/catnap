package catnap.examples.jersey.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder(value = {
        "list",
        "sale",
        "msrp",
        "formattedList",
        "formattedSale",
        "formattedMSRP"
})
public class WidgetPrices {

    private Double list;
    private Double sale;
    private Double msrp;
    private String formattedList;
    private String formattedSale;
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

