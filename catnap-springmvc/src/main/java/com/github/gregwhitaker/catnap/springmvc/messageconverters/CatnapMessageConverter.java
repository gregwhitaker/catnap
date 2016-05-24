package com.github.gregwhitaker.catnap.springmvc.messageconverters;

import com.github.gregwhitaker.catnap.core.view.CatnapView;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;

public abstract class CatnapMessageConverter<T extends CatnapView> extends AbstractHttpMessageConverter<Object> {

    private final T view;

    public CatnapMessageConverter(final T view) {
        super(MediaType.valueOf(view.getContentType()));
        this.view = view;
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return Object.class.isAssignableFrom(clazz);
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

    }
}
