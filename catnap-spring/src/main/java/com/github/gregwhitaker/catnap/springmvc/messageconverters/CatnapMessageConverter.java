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

package com.github.gregwhitaker.catnap.springmvc.messageconverters;

import com.github.gregwhitaker.catnap.core.exception.ViewRenderException;
import com.github.gregwhitaker.catnap.core.view.CatnapView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @param <T>
 */
public abstract class CatnapMessageConverter<T extends CatnapView> extends AbstractHttpMessageConverter<Object> {
    private static final Logger LOG = LoggerFactory.getLogger(CatnapMessageConverter.class);

    private final T view;

    public CatnapMessageConverter(final T view) {
        super(MediaType.valueOf(view.getContentType()));
        this.view = view;
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        HttpServletRequest httpRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        if (httpRequest.getParameter("fields") != null) {
            return Object.class.isAssignableFrom(clazz);
        }

        return false;
    }

    @Override
    protected boolean canRead(MediaType mediaType) {
        // Catnap message converters are write-only
        return false;
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        // Catnap message converters are write-only
        return null;
    }

    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        HttpServletRequest httpRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpServletResponse httpResponse = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();

        httpResponse.setContentType(view.getContentType());
        httpResponse.setCharacterEncoding(view.getEncoding());

        try {
            view.render(httpRequest, httpResponse, obj);
        } catch (Exception e) {
            LOG.error("Exception encountered during view rendering!", e);
            throw new ViewRenderException("Exception encountered during view rendering!", e);
        }
    }
}
