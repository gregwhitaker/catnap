package com.github.gregwhitaker.catnap.springmvc.messageconverters;

import com.github.gregwhitaker.catnap.core.view.CatnapView;
import com.github.gregwhitaker.catnap.core.view.JsonCatnapView;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;

public class CatnapJsonMessageConverter extends CatnapMessageConverter<JsonCatnapView> {

    public CatnapJsonMessageConverter(JsonCatnapView view) {
        super(view);
    }
}
