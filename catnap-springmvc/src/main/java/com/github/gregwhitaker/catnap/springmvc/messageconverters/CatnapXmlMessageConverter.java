package com.github.gregwhitaker.catnap.springmvc.messageconverters;

import com.github.gregwhitaker.catnap.core.view.XmlCatnapView;

public class CatnapXmlMessageConverter extends CatnapMessageConverter<XmlCatnapView> {

    public CatnapXmlMessageConverter(XmlCatnapView view) {
        super(view);
    }
}
