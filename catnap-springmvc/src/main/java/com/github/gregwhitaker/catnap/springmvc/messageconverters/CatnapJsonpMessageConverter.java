package com.github.gregwhitaker.catnap.springmvc.messageconverters;

import com.github.gregwhitaker.catnap.core.view.JsonpCatnapView;

public class CatnapJsonpMessageConverter extends CatnapMessageConverter<JsonpCatnapView> {

    public CatnapJsonpMessageConverter(JsonpCatnapView view) {
        super(view);
    }
}
