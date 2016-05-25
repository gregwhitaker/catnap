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

import com.github.gregwhitaker.catnap.core.view.XmlCatnapView;

/**
 * An {@link org.springframework.http.converter.AbstractHttpMessageConverter} implementation that renders XML
 * responses with Catnap.
 */
public class CatnapXmlMessageConverter extends CatnapMessageConverter<XmlCatnapView> {

    /**
     * Initializes this instance of {@link CatnapXmlMessageConverter} with the default {@link XmlCatnapView} configured
     * for rendering responses.
     */
    public CatnapXmlMessageConverter() {
        super(new XmlCatnapView.Builder().build());
    }

    /**
     * Initializes this instance of {@link CatnapXmlMessageConverter}.
     *
     * @param view the {@link XmlCatnapView} to use when rendering responses within this message converter
     */
    public CatnapXmlMessageConverter(XmlCatnapView view) {
        super(view);
    }
}
