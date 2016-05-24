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

import com.github.gregwhitaker.catnap.core.view.XmlCatnapView;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

/**
 * MessageBodyWriter responsible for creating XML responses.
 */
@Provider
@Produces(MediaType.APPLICATION_XML)
public class XmlCatnapMessageBodyWriter extends CatnapMessageBodyWriter {

    /**
     * Initializes this MessageBodyWriter with the default configuration of
     * the {@link com.github.gregwhitaker.catnap.core.view.XmlCatnapView}.
     */
    public XmlCatnapMessageBodyWriter() {
        super(new XmlCatnapView.Builder().build());
    }

    /**
     * Initializes this MessageBodyWriter with the supplied {@link XmlCatnapView}.
     *
     * @param view view that will be used by this MessageBodyWriter to render XML responses
     */
    public XmlCatnapMessageBodyWriter(XmlCatnapView view) {
        super(view);
    }
}
