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

package catnap.examples.springboot.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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
@JsonPropertyOrder(value = {
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
