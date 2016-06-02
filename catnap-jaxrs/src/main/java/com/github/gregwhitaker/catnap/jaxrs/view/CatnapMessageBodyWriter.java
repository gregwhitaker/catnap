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

package com.github.gregwhitaker.catnap.jaxrs.view;

import com.github.gregwhitaker.catnap.core.annotation.CatnapDisabled;
import com.github.gregwhitaker.catnap.core.util.RequestUtil;
import com.github.gregwhitaker.catnap.core.view.CatnapView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.MessageBodyWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Base Catnap specific JAX-RS MessageBodyWriter that delegates rendering of the response to the embedded
 * {@link com.github.gregwhitaker.catnap.core.view.CatnapView}.
 */
public abstract class CatnapMessageBodyWriter<T extends CatnapView> implements MessageBodyWriter<Object>, WrappingMessageBodyWriter<CatnapView> {
    private static final Logger logger = LoggerFactory.getLogger(CatnapMessageBodyWriter.class);

    @Context
    protected HttpServletRequest request;

    @Context
    protected HttpServletResponse response;

    protected final CatnapView view;

    public CatnapMessageBodyWriter(final CatnapView view) {
        this.view = view;
    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        //Check to see if Catnap processing has been disabled for this method
        for (Annotation annotation : annotations) {
            if (annotation instanceof CatnapDisabled) {
                RequestUtil.disableCatnap(request);
                return false;
            }
        }

        //Check to see if the Catnap query parameter is specified.  If it is not specified assume
        //that Catnap processing is disabled for this request
        if (!RequestUtil.containsParameter(request, view.getQueryBuilder().getQueryParameter())) {
            return false;
        }

        return Object.class.isAssignableFrom(type);
    }

    @Override
    public long getSize(Object obj, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        //Just let the container do its thing and figure out the content length on its own.
        return -1;
    }

    @Override
    public void writeTo(Object obj, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
                        MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        //Check to see if Catnap processing has been disabled for this method
        for (Annotation annotation : annotations) {
            if (annotation instanceof CatnapDisabled) {
                RequestUtil.disableCatnap(request);
                break;
            }
        }

        try {
            //Transfer headers onto the response object that will be processed by Catnap
            if (httpHeaders != null) {
                for (Map.Entry<String, List<Object>> entry : httpHeaders.entrySet()) {
                    for (Object value : entry.getValue()) {
                        response.addHeader(entry.getKey(), value.toString());
                    }
                }
            }

            response.setContentType(getContentType());
            response.setCharacterEncoding(getCharacterEncoding());

            view.render(request, response, obj);
        } catch (Exception e) {
            logger.error("Exception encountered during view rendering!", e);
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public CatnapView getWrappedView() {
        return view;
    }

    @Override
    public String getContentType() {
        return view.getContentType();
    }

    @Override
    public String getCharacterEncoding() {
        return view.getEncoding();
    }
}
